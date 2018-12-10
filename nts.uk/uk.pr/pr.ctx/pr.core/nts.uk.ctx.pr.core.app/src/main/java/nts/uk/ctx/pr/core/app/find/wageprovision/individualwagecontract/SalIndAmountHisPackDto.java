package nts.uk.ctx.pr.core.app.find.wageprovision.individualwagecontract;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.GenericHistYMPeriod;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmount;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmountHis;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalaryIndividualAmountHistory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
public class SalIndAmountHisPackDto {

    /**
     * 個人金額コード
     */
    private String perValCode;


    private String perValName;

    /**
     * 社員ID
     */
    private String empId;

    /**
     * カテゴリ区分
     */
    private int cateIndicator;

    /**
     * 期間
     */
    private List<GenericHistYMPeriodDto> period;

    /**
     * 給与賞与区分
     */
    private int salBonusCate;

    private List<SalIndAmountDto> salIndAmountList;


    public static SalIndAmountHisPackDto fromSalIndAmountHisDomain(List<SalaryIndividualAmountHistory> domains) {
        List<GenericHistYMPeriodDto> period = domains.stream().map(e -> new GenericHistYMPeriodDto(e.getHistoryId(), e.getPeriodStartYm(), e.getPeriodEndYm())).collect(Collectors.toList());
        List<SalIndAmountDto> amounts = domains.stream().map(e -> new SalIndAmountDto(e.getHistoryId(), e.getAmountOfMoney())).collect(Collectors.toList());
        return new SalIndAmountHisPackDto(domains.get(0).getPerValCode(), null, domains.get(0).getEmpId(), domains.get(0).getCateIndicator(), period, domains.get(0).getSalBonusCate(), amounts);
    }
}
