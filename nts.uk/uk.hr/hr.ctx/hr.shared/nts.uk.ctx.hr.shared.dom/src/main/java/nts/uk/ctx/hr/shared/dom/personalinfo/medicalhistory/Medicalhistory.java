/**
 * 
 */
package nts.uk.ctx.hr.shared.dom.personalinfo.medicalhistory;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.history.History;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * @author laitv
 * 
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Medicalhistory extends AggregateRoot implements History<HistoryItems, DatePeriod, GeneralDate>{

	/** 会社ID */
	private String cId;
	
	/** 個人ID */
	private String sId;

	/** 年月日期間の汎用履歴項目 */
	private List<HistoryItems> lstHistoryItemsForDatePeriod;


	@Override 
	public List<HistoryItems> items() {
		return lstHistoryItemsForDatePeriod;
	}

}
