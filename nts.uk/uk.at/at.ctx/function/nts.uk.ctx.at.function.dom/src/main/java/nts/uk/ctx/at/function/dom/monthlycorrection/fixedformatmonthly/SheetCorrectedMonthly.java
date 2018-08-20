package nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatName;

/**
 * 月別実績の修正のシート
 * @author tutk
 *
 */
@Getter
@NoArgsConstructor
public class SheetCorrectedMonthly extends DomainObject {
	/**並び順*/
	private int sheetNo;
	/**名称*/
	private DailyPerformanceFormatName sheetName;
	/**月次表示項目一覧*/
	private List<DisplayTimeItem> listDisplayTimeItem;
	public SheetCorrectedMonthly(int sheetNo, DailyPerformanceFormatName sheetName, List<DisplayTimeItem> listDisplayTimeItem) {
		super();
		this.sheetNo = sheetNo;
		this.sheetName = sheetName;
		this.listDisplayTimeItem = listDisplayTimeItem;
	}
	public void setSheetNo(int sheetNo) {
		this.sheetNo = sheetNo;
	}
	public void setListDisplayTimeItem(List<DisplayTimeItem> listDisplayTimeItem) {
		this.listDisplayTimeItem = listDisplayTimeItem;
	}
	
	public SheetCorrectedMonthly clone() {
		return new SheetCorrectedMonthly(sheetNo,sheetName,listDisplayTimeItem.stream().map(c->c.clone()).collect(Collectors.toList()));
	}
	
}
