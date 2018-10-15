package nts.uk.ctx.pr.core.app.find.wageprovision.individualwagecontract;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;
import java.util.Objects;

@Value
@AllArgsConstructor
public class SalIndAmountByPerValCode {
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
    private List<PeriodAndAmountDto> periodAndAmount;

    /**
     * 給与賞与区分
     */
    private int salBonusCate;

    public static SalIndAmountByPerValCode fromDto(SalIndAmountHissDto salIndAmountHissDto,List<PeriodAndAmountDto> periodAndAmountDto){
        if(Objects.isNull(salIndAmountHissDto)) return null;
        return new SalIndAmountByPerValCode(
                salIndAmountHissDto.getPerValCode(),
                salIndAmountHissDto.getEmpId(),
                salIndAmountHissDto.getCateIndicator(),
                periodAndAmountDto,
                salIndAmountHissDto.getSalBonusCate()
        );
    }




}
