package com.rabobank.statement.validator;

import com.rabobank.statement.constants.CustomerStatementConstant;
import com.rabobank.statement.model.CustomerStatementModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;


/*
 * Checks for invalid transaction from the list of customer statement model list
 */
@Component
public class CustomerStatementValidator implements CustomerStatementConstant {

    private static final double MAX_MUTATION_THRESHOLD = 10000.0;
    private static final double MIN_MUTATION_THRESHOLD = 0.05;

    public List<CustomerStatementModel> checkForInvalidTransaction(List<CustomerStatementModel> customerStatementModelList, Set<Integer> processedReferenceNumbers) {

        for (CustomerStatementModel stmtModel : customerStatementModelList) {
            if (processedReferenceNumbers.contains(stmtModel.getRefernceNo())) {
                stmtModel.setFailedRecord(true);
            }
        }

        customerStatementModelList.stream().map(checkbalance)
                .collect(Collectors.groupingBy(CustomerStatementModel::getRefernceNo)).values().stream()
                .filter(id -> id.size() > 1)
                .forEach(id -> id.forEach(c -> c.setFailedRecord(true)));

        return customerStatementModelList;
    }

    private Function<CustomerStatementModel, CustomerStatementModel> checkbalance = (e) ->
    {
        // Balance check (existing logic)
        if (!(Math.round((e.getStartBalance() + e.getMutation()) *
                ROUND_TWO_DECIMAL) / ROUND_TWO_DECIMAL == e.getEndBalance())) {
            e.setFailedRecord(true);
        }

        // Anomaly detection for mutation amount
        double mutation = e.getMutation();
        if (mutation != 0.0 && Math.abs(mutation) < MIN_MUTATION_THRESHOLD) {
            e.setFailedRecord(true);
        } else if (Math.abs(mutation) > MAX_MUTATION_THRESHOLD) {
            e.setFailedRecord(true);
        }
        return e;
    };


}
