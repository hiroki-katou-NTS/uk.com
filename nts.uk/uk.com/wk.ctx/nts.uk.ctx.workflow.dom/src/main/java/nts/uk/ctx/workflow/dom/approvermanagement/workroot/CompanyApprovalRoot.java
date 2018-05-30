package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.layer.dom.event.DomainEvent;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.history.strategic.UnduplicatableHistory;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
/**
 * 会社別就業承認ルート
 * @author hoatt
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class CompanyApprovalRoot extends AggregateRoot implements UnduplicatableHistory<EmploymentAppHistoryItem, DatePeriod, GeneralDate> {
	/**会社ID*/
	private String companyId;
	/**承認ID*/
	public String approvalId;
	/**申請種類*/
	private ApplicationType applicationType;
	/**分岐ID*/
	private String branchId;
	/**任意項目申請ID*/
	private String anyItemApplicationId;
	/**確認ルート種類*/
	private ConfirmationRootType confirmationRootType;
	/**就業ルート区分*/
	private EmploymentRootAtr employmentRootAtr;
	/** 就業承認ルート履歴*/
	private List<EmploymentAppHistoryItem>  employmentAppHistoryItems;
	
	public static CompanyApprovalRoot createSimpleFromJavaType(String companyId,
			String approvalId,
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
		return new CompanyApprovalRoot(companyId,
			approvalId,
			applicationType == null ? null : EnumAdaptor.valueOf(applicationType, ApplicationType.class), 
			branchId,
			anyItemApplicationId,
			confirmationRootType == null ? null : EnumAdaptor.valueOf(confirmationRootType, ConfirmationRootType.class),
			EnumAdaptor.valueOf(employmentRootAtr, EmploymentRootAtr.class),employmentAppHistorys);
	}
	public static CompanyApprovalRoot convert(String companyId,
			String approvalId,
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
		return new CompanyApprovalRoot(companyId,
			approvalId,
			applicationType == null ? null : EnumAdaptor.valueOf(applicationType, ApplicationType.class),
			branchId,
			anyItemApplicationId,
			confirmationRootType == null ? null : EnumAdaptor.valueOf(confirmationRootType, ConfirmationRootType.class),
			EnumAdaptor.valueOf(employmentRootAtr, EmploymentRootAtr.class),
			employmentAppHistorys);
	}
	public static CompanyApprovalRoot updateEdate(CompanyApprovalRoot comApprovalRoot, String eDate){
		CompanyApprovalRoot com = comApprovalRoot;
		//com.period.updateEndate(eDate);
		com.employmentAppHistoryItems.get(0).changeSpan(new DatePeriod(com.employmentAppHistoryItems.get(0).start(), GeneralDate.fromString(eDate, "yyyy-MM-dd")));
		return com;
	}
	public static CompanyApprovalRoot updateSdate(CompanyApprovalRoot comApprovalRoot, String startDate){
		CompanyApprovalRoot com = comApprovalRoot;
		//com.period.updateStrartDate(startDate);
		com.employmentAppHistoryItems.get(0).changeSpan(new DatePeriod(GeneralDate.fromString(startDate, "yyyy-MM-dd"), com.employmentAppHistoryItems.get(0).end()));
		return com;
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
