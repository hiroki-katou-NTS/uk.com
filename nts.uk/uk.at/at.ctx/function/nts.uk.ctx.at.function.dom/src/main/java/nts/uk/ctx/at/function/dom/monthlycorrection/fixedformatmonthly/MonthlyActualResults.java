package nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
/**
 * 月別実績の修正の表示項目
 * @author tutk
 *
 */
@Getter
public class MonthlyActualResults extends DomainObject {
	/**ID*/
	private String monthlyActualID;
	/**月次表示項目シート一覧*/
	private List<SheetCorrectedMonthly> listSheetCorrectedMonthly;
	public MonthlyActualResults(String monthlyActualID, List<SheetCorrectedMonthly> listSheetCorrectedMonthly) {
		super();
		this.monthlyActualID = monthlyActualID;
		this.listSheetCorrectedMonthly = listSheetCorrectedMonthly;
	}

}
