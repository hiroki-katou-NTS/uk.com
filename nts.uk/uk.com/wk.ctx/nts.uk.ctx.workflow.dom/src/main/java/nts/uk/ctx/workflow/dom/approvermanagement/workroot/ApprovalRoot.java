package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.com.history.strategic.UnduplicatableHistory;
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
	/**履歴*/
	private List<EmploymentAppHistoryItem> historyItems;
	/**申請種類*/
	private Optional<ApplicationType> applicationType;
	/**確認ルート種類*/
	private Optional<ConfirmationRootType> confirmationRootType;
	/**届出ID*/
	private Optional<Integer> noticeId;
	/**各業務エベントID*/
	private Optional<String> busEventId;
	
	/**
	 * [C-1] 就業システムで 期間と種類により作成する
	 * @param datePeriod 期間
	 * @param employmentRootAtr 承認ルート区分
	 * @param applicationType 申請種類
	 * @param confirmationRootType 確認ルート種類
	 */
	public ApprovalRoot(DatePeriod datePeriod,
			EmploymentRootAtr employmentRootAtr,
			Optional<ApplicationType> applicationType,
			Optional<ConfirmationRootType> confirmationRootType) {
		EmploymentAppHistoryItem historyItem = new EmploymentAppHistoryItem(datePeriod);
		
		this.sysAtr = SystemAtr.WORK;
		this.employmentRootAtr = employmentRootAtr;
		this.applicationType = applicationType;
		this.confirmationRootType = confirmationRootType;
		this.noticeId = Optional.empty();
		this.busEventId = Optional.empty();
		this.historyItems = Arrays.asList(historyItem);
	}
	
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
