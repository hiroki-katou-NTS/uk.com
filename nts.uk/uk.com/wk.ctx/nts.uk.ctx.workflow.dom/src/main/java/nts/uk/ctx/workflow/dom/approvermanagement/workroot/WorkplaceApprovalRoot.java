package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.history.strategic.UnduplicatableHistory;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
/**
 * 職場別就業承認ルート
 * @author hoatt
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class WorkplaceApprovalRoot extends AggregateRoot implements UnduplicatableHistory<EmploymentAppHistoryItem, DatePeriod, GeneralDate>{
	/**会社ID*/
	private String companyId;
	/**承認ID*/
	public String approvalId;
	/**履歴ID*/
	private String workplaceId;
	/**分岐ID*/
	private String branchId;
	/**任意項目申請ID*/
	private String anyItemApplicationId;
	/**確認ルート種類*/
	private ConfirmationRootType confirmationRootType;
	/**就業ルート区分*/
	private EmploymentRootAtr employmentRootAtr;
	/**申請種類*/
	private ApplicationType applicationType;
	/** 就業承認ルート履歴*/
	private List<EmploymentAppHistoryItem>  employmentAppHistoryItems;
	
	public static WorkplaceApprovalRoot createSimpleFromJavaType(String companyId,
			String approvalId,
			String workplaceId,
			String historyId,
			Integer applicationType,
			String startDate,
			String endDate,
			String branchId,
			String anyItemApplicationId,
			Integer confirmationRootType,
			int employmentRootAtr){
		List<EmploymentAppHistoryItem>  employmentAppHistorys = new ArrayList<>();
		EmploymentAppHistoryItem employmentAppHistory = new EmploymentAppHistoryItem(historyId,new DatePeriod(GeneralDate.fromString(startDate, "yyyy-MM-dd"), GeneralDate.fromString(endDate, "yyyy-MM-dd")));
		employmentAppHistorys.add(employmentAppHistory);
		return new WorkplaceApprovalRoot(companyId,
			approvalId,
			workplaceId,
			branchId,
			anyItemApplicationId,
			confirmationRootType == null ? null : EnumAdaptor.valueOf(confirmationRootType, ConfirmationRootType.class),
			EnumAdaptor.valueOf(employmentRootAtr, EmploymentRootAtr.class),
			applicationType == null ? null: EnumAdaptor.valueOf(applicationType, ApplicationType.class),employmentAppHistorys);
	}
	public static WorkplaceApprovalRoot convert(String companyId,
			String approvalId,
			String workplaceId,
			String historyId,
			Integer applicationType,
			GeneralDate startDate,
			GeneralDate endDate,
			String branchId,
			String anyItemApplicationId,
			Integer confirmationRootType,
			int employmentRootAtr){
		List<EmploymentAppHistoryItem>  employmentAppHistorys = new ArrayList<>();
		EmploymentAppHistoryItem employmentAppHistory = new EmploymentAppHistoryItem(historyId,new DatePeriod(startDate,endDate));
		employmentAppHistorys.add(employmentAppHistory);
		return new WorkplaceApprovalRoot(companyId,
			approvalId,
			workplaceId,
			branchId,
			anyItemApplicationId,
			confirmationRootType == null ? null : EnumAdaptor.valueOf(confirmationRootType, ConfirmationRootType.class),
			EnumAdaptor.valueOf(employmentRootAtr, EmploymentRootAtr.class),
			applicationType == null ? null: EnumAdaptor.valueOf(applicationType, ApplicationType.class),employmentAppHistorys);
	}
	public static WorkplaceApprovalRoot updateEdate(WorkplaceApprovalRoot wpApprovalRoot, String eDate){
		WorkplaceApprovalRoot wp = wpApprovalRoot;
		wp.employmentAppHistoryItems.get(0).changeSpan(new DatePeriod(wp.employmentAppHistoryItems.get(0).start(), GeneralDate.fromString(eDate, "yyyy-MM-dd")));
		return wp;
	}
	public static WorkplaceApprovalRoot updateSdate(WorkplaceApprovalRoot wpApprovalRoot, String sDate){
		WorkplaceApprovalRoot wp = wpApprovalRoot;
		//wp.period.updateStrartDate(sDate);
		wp.employmentAppHistoryItems.get(0).changeSpan(new DatePeriod( GeneralDate.fromString(sDate, "yyyy-MM-dd"),wp.employmentAppHistoryItems.get(0).end()));
		return wp;
	}
	public static boolean checkValidate(String startDate, String endDate){
		if(startDate.compareTo(endDate) <= 0){
			return true;
		}
		return false;
	}
	@Override
	public List<EmploymentAppHistoryItem> items() {
		// TODO Auto-generated method stub
		return employmentAppHistoryItems;
	}
	
	public boolean isCommon(){
		return this.employmentRootAtr  == EmploymentRootAtr.COMMON;
	}
	public boolean isConfirm(){
		return this.employmentRootAtr  == EmploymentRootAtr.CONFIRMATION;
	}
	public boolean isApplication(){
		return this.employmentRootAtr  == EmploymentRootAtr.APPLICATION;
	}
}
