package nts.uk.ctx.pr.core.app.find.wageprovision.individualwagecontract;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.SalIndAmountHis;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 給与個人別金額履歴: DTO
 */
@AllArgsConstructor
@Value
public class SalIndAmountHissDto {

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
    private List<PeriodDto> period;

    /**
     * 給与賞与区分
     */
    private int salBonusCate;


    public static SalIndAmountHissDto fromDomain(SalIndAmountHis domain) {
        if(Objects.isNull(domain)) return null;

        SalIndAmountHissDto salIndAmountHissDto = new SalIndAmountHissDto(
                domain.getPerValCode(),
                domain.getEmpId(),
                domain.getCateIndicator().value,
                domain.getPeriod().stream().map(v->PeriodDto.fromDomain(v)).collect(Collectors.toList()),
                domain.getSalBonusCate().value
        );

        return salIndAmountHissDto;
    }


}






