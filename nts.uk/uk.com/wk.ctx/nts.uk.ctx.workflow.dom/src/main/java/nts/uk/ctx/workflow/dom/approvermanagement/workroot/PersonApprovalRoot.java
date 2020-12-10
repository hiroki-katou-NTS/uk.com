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
 * 個人別承認ルート
 * @author hoatt
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class PersonApprovalRoot extends AggregateRoot{
	/**会社ID*/
	private String companyId;
	/**承認ID*/
	public String approvalId;
	/**社員ID*/
	private String employeeId;
	/**承認ルート*/
	public ApprovalRoot apprRoot;
	
	public static PersonApprovalRoot createSimpleFromJavaType(String companyId,
			String approvalId,
			String employeeId,
			String historyId,
			Integer applicationType,
			String startDate,
			String endDate,
			// String branchId,
			// String anyItemApplicationId,
			Integer confirmationRootType,
			int employmentRootAtr, int sysAtr, Integer noticeId, String busEventId){
		List<EmploymentAppHistoryItem>  employmentAppHistorys = new ArrayList<>();
		EmploymentAppHistoryItem employmentAppHistory = new EmploymentAppHistoryItem(historyId,new DatePeriod(GeneralDate.fromString(startDate, "yyyy-MM-dd"), GeneralDate.fromString(endDate, "yyyy-MM-dd")));
		employmentAppHistorys.add(employmentAppHistory);
		return new PersonApprovalRoot(companyId,
			approvalId,
			employeeId,
			new ApprovalRoot(EnumAdaptor.valueOf(sysAtr, SystemAtr.class),
					EnumAdaptor.valueOf(employmentRootAtr, EmploymentRootAtr.class),
					// branchId, 
					employmentAppHistorys,
					applicationType == null ? null : EnumAdaptor.valueOf(applicationType, ApplicationType.class), 
					confirmationRootType == null ? null : EnumAdaptor.valueOf(confirmationRootType, ConfirmationRootType.class),
					// anyItemApplicationId,
					noticeId, busEventId));
	}
	public static PersonApprovalRoot convert(String companyId,
			String approvalId,
			String employeeId,
			String historyId,
			Integer applicationType,
			GeneralDate startDate,
			GeneralDate endDate,
			// String branchId,
			// String anyItemApplicationId,
			Integer confirmationRootType,
			int employmentRootAtr, int sysAtr, Integer noticeId, String busEventId){
		List<EmploymentAppHistoryItem>  employmentAppHistorys = new ArrayList<>();
		EmploymentAppHistoryItem employmentAppHistory = new EmploymentAppHistoryItem(historyId,new DatePeriod(startDate,endDate));
		employmentAppHistorys.add(employmentAppHistory);
		return new PersonApprovalRoot(companyId,
			approvalId,
			employeeId,
			new ApprovalRoot(EnumAdaptor.valueOf(sysAtr, SystemAtr.class),
					EnumAdaptor.valueOf(employmentRootAtr, EmploymentRootAtr.class),
					// branchId, 
					employmentAppHistorys,
					applicationType == null ? null : EnumAdaptor.valueOf(applicationType, ApplicationType.class), 
					confirmationRootType == null ? null : EnumAdaptor.valueOf(confirmationRootType, ConfirmationRootType.class),
					// anyItemApplicationId,
					noticeId, busEventId));
	}
	public static PersonApprovalRoot updateEdate(PersonApprovalRoot psApprovalRoot, String eDate){
		PersonApprovalRoot ps = psApprovalRoot;
		ps.apprRoot.getHistoryItems().get(0).changeSpan(new DatePeriod(ps.apprRoot.getHistoryItems().get(0).start(), GeneralDate.fromString(eDate, "yyyy-MM-dd")));
		return ps;
	}
	public static PersonApprovalRoot updateSdate(PersonApprovalRoot psApprovalRoot, String sDate){
		PersonApprovalRoot ps = psApprovalRoot;
		ps.apprRoot.getHistoryItems().get(0).changeSpan(new DatePeriod( GeneralDate.fromString(sDate, "yyyy-MM-dd"),ps.apprRoot.getHistoryItems().get(0).end()));
		return ps;
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
