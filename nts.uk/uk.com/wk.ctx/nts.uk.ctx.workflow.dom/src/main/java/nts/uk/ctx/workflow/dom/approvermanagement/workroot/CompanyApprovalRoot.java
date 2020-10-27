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
 * 会社別承認ルート
 * @author hoatt
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class CompanyApprovalRoot extends AggregateRoot{
	/**会社ID*/
	private String companyId;
	/**承認ID*/
	public String approvalId;
	/**承認ルート*/
	public ApprovalRoot apprRoot;
	
	public static CompanyApprovalRoot createSimpleFromJavaType(String companyId,
			String approvalId, String historyId, Integer applicationType,
			String startDate, String endDate, 
			Integer confirmationRootType,
			int employmentRootAtr, int sysAtr, Integer noticeId, String busEventId){
		List<EmploymentAppHistoryItem>  employmentAppHistorys = new ArrayList<>();
		EmploymentAppHistoryItem employmentAppHistory = new EmploymentAppHistoryItem(historyId,new DatePeriod(GeneralDate.fromString(startDate, "yyyy-MM-dd"), GeneralDate.fromString(endDate, "yyyy-MM-dd")));
		employmentAppHistorys.add(employmentAppHistory);
		return new CompanyApprovalRoot(companyId,
			approvalId,
			new ApprovalRoot(EnumAdaptor.valueOf(sysAtr, SystemAtr.class),
					EnumAdaptor.valueOf(employmentRootAtr, EmploymentRootAtr.class),
					employmentAppHistorys,
					applicationType == null ? null : EnumAdaptor.valueOf(applicationType, ApplicationType.class), 
					confirmationRootType == null ? null : EnumAdaptor.valueOf(confirmationRootType, ConfirmationRootType.class),
					noticeId, busEventId));
	}
	public static CompanyApprovalRoot convert(String companyId,
			String approvalId, String historyId, Integer applicationType,
			GeneralDate startDate, GeneralDate endDate, 
			Integer confirmationRootType,
			int employmentRootAtr, int sysAtr, Integer noticeId, String busEventId){
		List<EmploymentAppHistoryItem>  employmentAppHistorys = new ArrayList<>();
		EmploymentAppHistoryItem employmentAppHistory = new EmploymentAppHistoryItem(historyId,new DatePeriod(startDate,endDate));
		employmentAppHistorys.add(employmentAppHistory);
		return new CompanyApprovalRoot(companyId,
			approvalId,
			new ApprovalRoot(EnumAdaptor.valueOf(sysAtr, SystemAtr.class),
					EnumAdaptor.valueOf(employmentRootAtr, EmploymentRootAtr.class),
					employmentAppHistorys,
					applicationType == null ? null : EnumAdaptor.valueOf(applicationType, ApplicationType.class), 
					confirmationRootType == null ? null : EnumAdaptor.valueOf(confirmationRootType, ConfirmationRootType.class),
					noticeId, busEventId));
	}
	public static CompanyApprovalRoot updateEdate(CompanyApprovalRoot comApprovalRoot, String eDate){
		CompanyApprovalRoot com = comApprovalRoot;
		com.apprRoot.getHistoryItems().get(0).changeSpan(new DatePeriod(com.apprRoot.getHistoryItems().get(0).start(), GeneralDate.fromString(eDate, "yyyy-MM-dd")));
		return com;
	}
	public static CompanyApprovalRoot updateSdate(CompanyApprovalRoot comApprovalRoot, String startDate){
		CompanyApprovalRoot com = comApprovalRoot;
		com.apprRoot.getHistoryItems().get(0).changeSpan(new DatePeriod(GeneralDate.fromString(startDate, "yyyy-MM-dd"), com.apprRoot.getHistoryItems().get(0).end()));
		return com;
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
