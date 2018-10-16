package nts.uk.ctx.pr.core.app.find.wageprovision.individualwagecontract;


import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.GenericHistYMPeriod;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmount;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmountHis;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Value
public class SalIndAmountHisPackDto {

    /**
     * 個人金額コード
     */
    private String perValCode;

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


    public static SalIndAmountHisPackDto fromSalIndAmountHisDomain(SalIndAmountHis domain) {
        return new SalIndAmountHisPackDto(domain.getPerValCode(), domain.getEmpId(), domain.getCateIndicator().value, domain.getPeriod().stream().map(item -> GenericHistYMPeriodDto.fromDomain(item)).collect(Collectors.toList()), domain.getSalBonusCate().value, new ArrayList<SalIndAmountDto>());
    }
}
