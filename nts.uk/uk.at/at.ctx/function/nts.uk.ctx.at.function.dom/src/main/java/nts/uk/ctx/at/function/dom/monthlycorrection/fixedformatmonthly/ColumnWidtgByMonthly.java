package nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
/**
 * 月別実績のグリッドの列幅
 * @author tutk
 *
 */
@Getter
public class ColumnWidtgByMonthly extends AggregateRoot {
	
	/**会社ID*/
	private String companyID;
	/**グリッドの列幅一覧*/
	private List<ColumnWidthOfDisplayItem> listColumnWidthOfDisplayItem;
	
	public ColumnWidtgByMonthly(String companyID, List<ColumnWidthOfDisplayItem> listColumnWidthOfDisplayItem) {
		super();
		this.companyID = companyID;
		this.listColumnWidthOfDisplayItem = listColumnWidthOfDisplayItem;
	}
	
}
