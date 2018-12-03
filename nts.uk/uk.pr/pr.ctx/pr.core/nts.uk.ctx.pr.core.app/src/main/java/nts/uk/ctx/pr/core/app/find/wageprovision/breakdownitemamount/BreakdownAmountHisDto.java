package nts.uk.ctx.pr.core.app.find.wageprovision.breakdownitemamount;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.breakdownitemamount.BreakdownAmountHis;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Value
public class BreakdownAmountHisDto {

    /**
     * 会社ID
     */
    private String cid;

    /**
     * カテゴリ区分
     */
    private int categoryAtr;

    /**
     * 項目名コード
     */
    private String itemNameCd;

    /**
     * 社員ID
     */
    private String employeeId;

    /**
     * 期間
     */
    List<YearMonthHistoryItemDto> yearMonthHistory;

    /**
     * 給与賞与区分
     */
    private int salaryBonusAtr;

    public static BreakdownAmountHisDto fromDomain(BreakdownAmountHis domain) {
        return new BreakdownAmountHisDto(
                domain.getCid(),
                domain.getCategoryAtr().value,
                domain.getItemNameCd().v(),
                domain.getEmployeeId(),
                domain.getPeriod().stream().map(i -> new YearMonthHistoryItemDto(i.identifier(), i.start().v(), i.end().v())).collect(Collectors.toList()),
                domain.getSalaryBonusAtr().value);
    }

}
