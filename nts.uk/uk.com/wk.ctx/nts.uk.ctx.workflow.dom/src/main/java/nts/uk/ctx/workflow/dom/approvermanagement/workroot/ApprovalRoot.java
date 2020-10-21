package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.history.strategic.UnduplicatableHistory;
import nts.arc.time.calendar.period.DatePeriod;
/**
 * 承認ルート
 * @author hoatt
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class ApprovalRoot implements UnduplicatableHistory<EmploymentAppHistoryItem, DatePeriod, GeneralDate> {
	
	/**システム区分*/
	private SystemAtr sysAtr;
	/**承認ルート区分*/
	private EmploymentRootAtr employmentRootAtr;
//	/**分岐ID*/
//	private String branchId;
	/**履歴*/
	private List<EmploymentAppHistoryItem> historyItems;
	/**申請種類*/
	private ApplicationType applicationType;
	/**確認ルート種類*/
	private ConfirmationRootType confirmationRootType;
//	/**任意項目申請ID*/
//	private String anyItemApplicationId;
	/**届出ID*/
	private Integer noticeId;
	/**各業務エベントID*/
	private String busEventId;
	
	public static boolean checkValidate(String startDate, String endDate){
		if(startDate.compareTo(endDate) <= 0){
			return true;
		}
		return false;
	}
	@Override
	public List<EmploymentAppHistoryItem> items() {
		return historyItems;
	}
}
