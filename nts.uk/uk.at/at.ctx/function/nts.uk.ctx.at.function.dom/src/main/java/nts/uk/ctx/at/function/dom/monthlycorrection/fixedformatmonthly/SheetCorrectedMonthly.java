package nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatName;

/**
 * 月別実績の修正のシート
 * @author tutk
 *
 */
@Getter
public class SheetCorrectedMonthly extends DomainObject {
	/**ID*/
	private String monthlyActualID;
	/**並び順*/
	private int sheetNo;
	/**名称*/
	private DailyPerformanceFormatName sheetName;
	/**月次表示項目一覧*/
	private List<DisplayTimeItem> listDisplayTimeItem;
	public SheetCorrectedMonthly(String monthlyActualID, int sheetNo, DailyPerformanceFormatName sheetName, List<DisplayTimeItem> listDisplayTimeItem) {
		super();
		this.monthlyActualID = monthlyActualID;
		this.sheetNo = sheetNo;
		this.sheetName = sheetName;
		this.listDisplayTimeItem = listDisplayTimeItem;
	}


	
	
}
