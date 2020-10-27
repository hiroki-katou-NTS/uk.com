package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
/**
 * 職場別承認ルート
 * @author hoatt
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class WorkplaceApprovalRoot extends AggregateRoot {
	/**会社ID*/
	private String companyId;
	/**承認ID*/
	public String approvalId;
	/**履歴ID*/
	private String workplaceId;
	/**承認ルート*/
	public ApprovalRoot apprRoot;
	
	public static WorkplaceApprovalRoot createSimpleFromJavaType(String companyId,
			String approvalId, String workplaceId, String historyId, Integer applicationType,
			String startDate, String endDate, 
			// String branchId,
			// String anyItemApplicationId,
			Integer confirmationRootType,
			int employmentRootAtr, int sysAtr, Integer noticeId, String busEventId){
		List<EmploymentAppHistoryItem>  employmentAppHistorys = new ArrayList<>();
		EmploymentAppHistoryItem employmentAppHistory = new EmploymentAppHistoryItem(historyId,new DatePeriod(GeneralDate.fromString(startDate, "yyyy-MM-dd"), GeneralDate.fromString(endDate, "yyyy-MM-dd")));
		employmentAppHistorys.add(employmentAppHistory);
		return new WorkplaceApprovalRoot(companyId,
			approvalId,
			workplaceId,
			new ApprovalRoot(EnumAdaptor.valueOf(sysAtr, SystemAtr.class),
					EnumAdaptor.valueOf(employmentRootAtr, EmploymentRootAtr.class),
					// branchId, 
					employmentAppHistorys,
					applicationType == null ? null : EnumAdaptor.valueOf(applicationType, ApplicationType.class), 
					confirmationRootType == null ? null : EnumAdaptor.valueOf(confirmationRootType, ConfirmationRootType.class),
					// anyItemApplicationId,
					noticeId, busEventId));
	}
	public static WorkplaceApprovalRoot convert(String companyId,
			String approvalId, String workplaceId, String historyId, Integer applicationType,
			GeneralDate startDate, GeneralDate endDate, 
			// String branchId,
			// String anyItemApplicationId,
			Integer confirmationRootType,
			int employmentRootAtr, int sysAtr, Integer noticeId, String busEventId){
		List<EmploymentAppHistoryItem>  employmentAppHistorys = new ArrayList<>();
		EmploymentAppHistoryItem employmentAppHistory = new EmploymentAppHistoryItem(historyId,new DatePeriod(startDate,endDate));
		employmentAppHistorys.add(employmentAppHistory);
		return new WorkplaceApprovalRoot(companyId,
			approvalId,
			workplaceId,
			new ApprovalRoot(EnumAdaptor.valueOf(sysAtr, SystemAtr.class),
					EnumAdaptor.valueOf(employmentRootAtr, EmploymentRootAtr.class),
					// branchId, 
					employmentAppHistorys,
					applicationType == null ? null : EnumAdaptor.valueOf(applicationType, ApplicationType.class), 
					confirmationRootType == null ? null : EnumAdaptor.valueOf(confirmationRootType, ConfirmationRootType.class),
					// anyItemApplicationId,
					noticeId, busEventId));
	}
	public static WorkplaceApprovalRoot updateEdate(WorkplaceApprovalRoot wpApprovalRoot, String eDate){
		WorkplaceApprovalRoot wp = wpApprovalRoot;
		wp.apprRoot.getHistoryItems().get(0).changeSpan(new DatePeriod(wp.apprRoot.getHistoryItems().get(0).start(), GeneralDate.fromString(eDate, "yyyy-MM-dd")));
		return wp;
	}
	public static WorkplaceApprovalRoot updateSdate(WorkplaceApprovalRoot wpApprovalRoot, String sDate){
		WorkplaceApprovalRoot wp = wpApprovalRoot;
		wp.apprRoot.getHistoryItems().get(0).changeSpan(new DatePeriod( GeneralDate.fromString(sDate, "yyyy-MM-dd"),wp.apprRoot.getHistoryItems().get(0).end()));
		return wp;
	}
	
	public boolean isCommon(){
		return this.apprRoot.getEmploymentRootAtr() == EmploymentRootAtr.COMMON;
	}
	public boolean isConfirm(){
		return this.apprRoot.getEmploymentRootAtr()  == EmploymentRootAtr.CONFIRMATION;
	}
	public boolean isApplication(){
		return this.apprRoot.getEmploymentRootAtr()  == EmploymentRootAtr.APPLICATION;
	}
	public boolean isNotice(){
		return this.apprRoot.getEmploymentRootAtr()  == EmploymentRootAtr.NOTICE;
	}
	public boolean isBusEvent(){
		return this.apprRoot.getEmploymentRootAtr()  == EmploymentRootAtr.BUS_EVENT;
	}
}
