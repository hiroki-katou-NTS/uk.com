package nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings;

import java.util.Optional;

import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApplicationType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmationRootType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.EmploymentRootAtr;
import nts.uk.shr.com.enumcommon.NotUseAtr;

public class SettingTypeUsedTestHelper {
	
	public static SettingTypeUsed createDefault() {
		EmploymentRootAtr employmentRootAtr = EmploymentRootAtr.COMMON;
		Optional<ApplicationType> applicationType = Optional.empty();
		Optional<ConfirmationRootType> confirmRootType = Optional.empty();
		NotUseAtr notUseAtr = NotUseAtr.NOT_USE;
		return new SettingTypeUsed(employmentRootAtr, applicationType, confirmRootType, notUseAtr);
	}
	
	/**
	 * 承認ルート区分＝＝「申請」 
	 * 利用する == する
	 */
	public static SettingTypeUsed createWithApplicationAndUse(ApplicationType applicationType) {
		EmploymentRootAtr employmentRootAtr = EmploymentRootAtr.APPLICATION;
		Optional<ConfirmationRootType> confirmRootType = Optional.empty();
		NotUseAtr notUseAtr = NotUseAtr.USE;
		return new SettingTypeUsed(employmentRootAtr, Optional.ofNullable(applicationType), confirmRootType, notUseAtr);
	}
	
	/**
	 * 承認ルート区分＝＝「申請」 
	 * 利用する == しない
	 */
	public static SettingTypeUsed createWithApplicationAndNotUse(ApplicationType applicationType) {
		EmploymentRootAtr employmentRootAtr = EmploymentRootAtr.APPLICATION;
		Optional<ConfirmationRootType> confirmRootType = Optional.empty();
		NotUseAtr notUseAtr = NotUseAtr.NOT_USE;
		return new SettingTypeUsed(employmentRootAtr, Optional.ofNullable(applicationType), confirmRootType, notUseAtr);
	}
	
	/**
	 * 承認ルート区分!＝「申請」 
	 * 利用する == しない
	 */
	public static SettingTypeUsed createWithAnyAndNotUse(ApplicationType applicationType) {
		EmploymentRootAtr employmentRootAtr = EmploymentRootAtr.ANYITEM;
		Optional<ConfirmationRootType> confirmRootType = Optional.empty();
		NotUseAtr notUseAtr = NotUseAtr.NOT_USE;
		return new SettingTypeUsed(employmentRootAtr, Optional.ofNullable(applicationType), confirmRootType, notUseAtr);
	}
	
	/**
	 * 承認ルート区分!＝「申請」 
	 * 利用する == ない
	 */
	public static SettingTypeUsed createWithAnyAndUse(ApplicationType applicationType) {
		EmploymentRootAtr employmentRootAtr = EmploymentRootAtr.ANYITEM;
		Optional<ConfirmationRootType> confirmRootType = Optional.empty();
		NotUseAtr notUseAtr = NotUseAtr.USE;
		return new SettingTypeUsed(employmentRootAtr, Optional.ofNullable(applicationType), confirmRootType, notUseAtr);
	}
	
	/**
	 * 承認ルート区分＝＝「確認」 
	 * 利用する == する
	 */
	public static SettingTypeUsed createWithConfirmAndUse(ConfirmationRootType confirmRootType) {
		EmploymentRootAtr employmentRootAtr = EmploymentRootAtr.CONFIRMATION;
		Optional<ApplicationType> applicationType = Optional.empty();
		NotUseAtr notUseAtr = NotUseAtr.USE;
		return new SettingTypeUsed(employmentRootAtr, applicationType, Optional.ofNullable(confirmRootType), notUseAtr);
	}
	
	/**
	 * 承認ルート区分＝＝「確認」 
	 * 利用する == しない
	 */
	public static SettingTypeUsed createWithConfirmAndNotUse(ConfirmationRootType confirmRootType) {
		EmploymentRootAtr employmentRootAtr = EmploymentRootAtr.CONFIRMATION;
		Optional<ApplicationType> applicationType = Optional.empty();
		NotUseAtr notUseAtr = NotUseAtr.NOT_USE;
		return new SettingTypeUsed(employmentRootAtr, applicationType, Optional.ofNullable(confirmRootType), notUseAtr);
	}
	
	/**
	 * 承認ルート区分!＝「確認」 
	 * 利用する == しない
	 */
	public static SettingTypeUsed createWithAnyAndNotUse(ConfirmationRootType confirmType) {
		EmploymentRootAtr employmentRootAtr = EmploymentRootAtr.ANYITEM;
		Optional<ConfirmationRootType> confirmRootType = Optional.ofNullable(confirmType);
		NotUseAtr notUseAtr = NotUseAtr.NOT_USE;
		return new SettingTypeUsed(employmentRootAtr, Optional.empty(), confirmRootType, notUseAtr);
	}
	
	/**
	 * 承認ルート区分!＝「確認」 
	 * 利用する == ない
	 */
	public static SettingTypeUsed createWithAnyAndUse(ConfirmationRootType confirmType) {
		EmploymentRootAtr employmentRootAtr = EmploymentRootAtr.ANYITEM;
		Optional<ConfirmationRootType> confirmRootType = Optional.ofNullable(confirmType);
		NotUseAtr notUseAtr = NotUseAtr.USE;
		return new SettingTypeUsed(employmentRootAtr, Optional.empty(), confirmRootType, notUseAtr);
	}
	
}
