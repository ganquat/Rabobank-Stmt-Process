package com.rabobank.statement.validator;

import com.rabobank.statement.constants.CustStmtConstant;
import com.rabobank.statement.model.CustStmtModel;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


/*
 * Checks for invalid transaction from the list of customer statement model list
 */
@Component
public class CustStmtValidator implements CustStmtConstant {

    public List<CustStmtModel> checkForInvalidTransaction(List<CustStmtModel> custStmtModelList) {

        custStmtModelList = custStmtModelList.stream().map(checkbalance).collect(Collectors.toList());

        custStmtModelList.stream()
                .collect(Collectors.groupingBy(CustStmtModel::getRefernceNo)).values().stream()
                .filter(Id-> Id.size() > 1)
                .forEach(Id -> Id.forEach(c -> c.setFailedRecord(true))                );

        return custStmtModelList;
    }

    private Function<CustStmtModel, CustStmtModel> checkbalance = (e) ->
    {
        if (!(Math.round((e.getStartBalance() + e.getMutation()) *
                ROUND_TWO_DECIMAL) / ROUND_TWO_DECIMAL == e.getEndBalance()))
            e.setFailedRecord(true);
        return e;
    };




}
