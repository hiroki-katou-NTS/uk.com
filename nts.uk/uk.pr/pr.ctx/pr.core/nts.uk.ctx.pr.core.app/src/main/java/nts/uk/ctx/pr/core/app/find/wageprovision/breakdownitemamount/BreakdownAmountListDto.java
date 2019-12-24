package nts.uk.ctx.pr.core.app.find.wageprovision.breakdownitemamount;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.breakdownitemamount.BreakdownAmountList;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.breakdownitemset.BreakdownItemSet;

import java.util.Optional;

@Value
@AllArgsConstructor
public class BreakdownAmountListDto {

    /**
     * 内訳項目コード
     */
    private String breakdownItemCode;


    /**
     * 内訳項目名称
     */
    private String breakdownItemName;

    /**
     * 金額
     */
    private Long amount;

    public static BreakdownAmountListDto fromDomain(Optional<BreakdownAmountList> domain, BreakdownItemSet domain2) {
        return new BreakdownAmountListDto(
                domain2.getBreakdownItemCode().v(),
                domain2.getBreakdownItemName().v(),
                domain.map(i -> i.getAmount().v()).orElse(0L)
        );
    }
}
