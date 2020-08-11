package nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.history.strategic.PersistentHistory;
/**
 * 社員の医療勤務形態履歴
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.社員情報.スケジュールチーム
 * @author HieuLt
 *
 */
@AllArgsConstructor
@Getter

public class EmpMedicalWorkStyleHistory  implements DomainAggregate ,PersistentHistory<DateHistoryItem, DatePeriod, GeneralDate> {
	/**社員ID **/
	private final String empID;
	
	/**List<年月日期間の汎用履歴項目> 履歴 **/
	
	private List<DateHistoryItem> listDateHistoryItem;
	
	/**
	 * 	[C-1] 社員の医療勤務形態履歴						
	 * @param empID
	 * @param listDateHistoryItem
	 * @return
	 */
	public static EmpMedicalWorkStyleHistory get(String empID , List<DateHistoryItem> listDateHistoryItem){
		//inv-1		履歴.size () > 0			
		if(!(listDateHistoryItem.size() >0)){
			throw new RuntimeException("SystemError");
		}
		return new EmpMedicalWorkStyleHistory(empID, listDateHistoryItem);
	}

	@Override
	public List<DateHistoryItem> items() {
		return this.listDateHistoryItem;
	}
}
