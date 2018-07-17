package com.rabobank.statement.validator;

import com.rabobank.statement.constants.CustomerStatementConstant;
import com.rabobank.statement.model.CustomerStatementModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


/*
 * Checks for invalid transaction from the list of customer statement model list
 */
@Component
public class CustomerStatementValidator implements CustomerStatementConstant {

    public List<CustomerStatementModel> checkForInvalidTransaction(List<CustomerStatementModel> customerStatementModelList) {

        customerStatementModelList.stream().map(checkbalance)
                .collect(Collectors.groupingBy(CustomerStatementModel::getRefernceNo)).values().stream()
                .filter(id -> id.size() > 1)
                .forEach(id -> id.forEach(c -> c.setFailedRecord(true)));

        return customerStatementModelList;
    }

    private Function<CustomerStatementModel, CustomerStatementModel> checkbalance = (e) ->
    {
        if (!(Math.round((e.getStartBalance() + e.getMutation()) *
                ROUND_TWO_DECIMAL) / ROUND_TWO_DECIMAL == e.getEndBalance()))
            e.setFailedRecord(true);
        return e;
    };


}
