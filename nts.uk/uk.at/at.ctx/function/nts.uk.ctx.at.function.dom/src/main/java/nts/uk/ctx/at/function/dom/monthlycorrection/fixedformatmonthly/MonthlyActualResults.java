package nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
/**
 * 月別実績の修正の表示項目
 * @author tutk
 *
 */
@Getter
public class MonthlyActualResults extends DomainObject   {
	/**月次表示項目シート一覧*/
	private List<SheetCorrectedMonthly> listSheetCorrectedMonthly;

	public MonthlyActualResults(List<SheetCorrectedMonthly> listSheetCorrectedMonthly) {
		super();
		this.listSheetCorrectedMonthly = listSheetCorrectedMonthly;
	}

	public void setListSheetCorrectedMonthly(List<SheetCorrectedMonthly> listSheetCorrectedMonthly) {
		this.listSheetCorrectedMonthly = listSheetCorrectedMonthly;
	}
	
	public MonthlyActualResults clone() {
		return new MonthlyActualResults(listSheetCorrectedMonthly.stream().map(c->c.clone()).collect(Collectors.toList()));
	}

	

}
