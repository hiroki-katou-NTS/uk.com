package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor;

import java.util.Optional;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.adapter.employee.SClsHistImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.SharedSyEmploymentImport;
import nts.uk.ctx.at.shared.dom.adapter.jobtitle.SharedAffJobTitleHisImport;
import nts.uk.ctx.at.shared.dom.adapter.workplace.SharedAffWorkPlaceHisImport;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.EmpOrganizationImport;

public class AffiliationInforOfDailyAttdHelper {
	
	public static SharedSyEmploymentImport createEmployment( String employmentCode ) {
		
		return new SharedSyEmploymentImport(
				"empId", 
				employmentCode, 
				"employmentName", 
				new DatePeriod(
						GeneralDate.ymd(2020, 1, 1), 
						GeneralDate.ymd(2020, 2, 1))); 
	}
	
	public static SharedAffJobTitleHisImport createJobTitle(String jobTitleId) {
		
		return new SharedAffJobTitleHisImport(
				"empId", 
				jobTitleId, 
				new DatePeriod(
						GeneralDate.ymd(2020, 1, 1), 
						GeneralDate.ymd(2020, 2, 1)), 
				"jobTitleName",
				"jobtitelCode");
	}
	
	public static SharedAffWorkPlaceHisImport createWorkplace(String workplaceId) {
		
		return new SharedAffWorkPlaceHisImport(
				new DatePeriod(
						GeneralDate.ymd(2020, 1, 1), 
						GeneralDate.ymd(2020, 2, 1)), 
				"empId", 
				workplaceId, 
				"wplCode", 
				"wplName", 
				"wkpDisplayName");
	}
	
	public static SClsHistImport createClassification(String classificationCode) {
		
		return new SClsHistImport(
				new DatePeriod(
						GeneralDate.ymd(2020, 1, 1), 
						GeneralDate.ymd(2020, 2, 1)), 
				"empId", 
				classificationCode, 
				"classificationName");
	}
	
	public static EmpOrganizationImport createEmpOrganizationImport(String workplaceId, String workplaceGroupId) {
		
		return new EmpOrganizationImport(
				new EmployeeId("empId"), 
				Optional.of("empCode"), 
				Optional.of("b-name"), 
				workplaceId, 
				Optional.of(workplaceGroupId));
	}
	
	/**
	 * 日別勤怠の所属情報を作る
	 * @param wplID 職場ID
	 * @param workplaceGroupId 職場グループID
	 * @return
	 */
	public static AffiliationInforOfDailyAttd createAffiliationInforOfDailyAttd( String wplID, Optional<String> workplaceGroupId ) {
		
		val domain = new AffiliationInforOfDailyAttd();
		
		domain.setWplID(wplID);
		domain.setWorkplaceGroupId(workplaceGroupId);
		
		return domain;
	}
}
