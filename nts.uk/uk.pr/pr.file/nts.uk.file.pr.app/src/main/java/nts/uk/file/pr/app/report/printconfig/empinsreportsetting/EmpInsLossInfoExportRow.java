package nts.uk.file.pr.app.report.printconfig.empinsreportsetting;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.adapter.company.CompanyInfor;
import nts.uk.ctx.pr.core.dom.adapter.employee.employee.EmployeeInfoEx;
import nts.uk.ctx.pr.core.dom.adapter.person.PersonExport;
import nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice.LaborInsuranceOffice;
import nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice.PublicEmploymentSecurityOffice;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.CurrentPersonResidence;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsLossInfo;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsNumInfo;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.WorkingTime;
import nts.uk.shr.com.history.DateHistoryItem;

@Data
public class EmpInsLossInfoExportRow {

	private String employeeId;

	private String employeeCode;

	/**
	 * 雇用保険番号情報.雇用保険番号
	 */
	private String insuranceNumber;

	/**
	 * 労働保険事業所.雇用保険情報.事業所番号1
	 */
	private String laborInsuranceOfficeNumber1;

	/**
	 * 労働保険事業所.雇用保険情報.事業所番号2
	 */
	private String laborInsuranceOfficeNumber2;

	/**
	 * 労働保険事業所.雇用保険情報.事業所番号3
	 */
	private String laborInsuranceOfficeNumber3;

	/**
	 * 社員雇用保険履歴.期間.期間.開始日
	 */
	private GeneralDate employeeInsurancePeriodStart;

	/**
	 * 社員雇用保険履歴.期間.期間.終了日
	 */
	private GeneralDate employeeInsurancePeriodEnd;

	/**
	 * 雇用保険喪失時情報.喪失原因
	 */
	private Integer causeOfLossAtr;

	/**
	 * 雇用保険喪失時情報.補充予定
	 */
	private String scheduleOfReplenishment;

	/**
	 * 個人.個人名グループ.個人名.氏名カナ
	 */
	private String personNameKana;

	/**
	 * 個人.個人名グループ.個人届出名称.氏名カナ
	 */
	private String personReportNameKana;

	/**
	 * 個人.個人名グループ.個人名.氏名
	 */
	private String personName;

	/**
	 * 個人.個人名グループ.個人届出名称.氏名
	 */
	private String personReportName;

	/**
	 * 個人.個人名グループ.個人名ローマ字.氏名
	 */
	private String personNameRomanji;

	/**
	 * 個人.性別
	 */
	private Integer personGender;

	/**
	 * 個人.生年月日
	 */
	private GeneralDate personBirthDay;

	/**
	 * 現住所履歴項目.住所1+住所2
	 */
	private String personCurrentAddress;

	/**
	 * 会社情報.会社名
	 */
	private String companyName;

	/**
	 * 労働保険事業所.名称
	 */
	private String laborInsuranceOfficeName;

	/**
	 * 雇用保険喪失時情報.被保険者でなくなったことの原因
	 */
	private String causeOfLossInsurance;

	/**
	 * 雇用保険喪失時情報.労働時間
	 */
	private WorkingTime scheduleWorkingHourPerWeek;

	/**
	 * 公共職業安定所.名称
	 */
	private String publicEmploymentSecurityOfficeName;

	public EmpInsLossInfoExportRow(String employeeId, EmployeeInfoEx employeeInfo, DateHistoryItem empInsHist,
			CompanyInfor companyInfo, EmpInsNumInfo empInsNumInfo, LaborInsuranceOffice laborInsuranceOffice,
			EmpInsLossInfo empInsLossInfo, PublicEmploymentSecurityOffice pubEmpSecOffice, PersonExport personInfo,
			CurrentPersonResidence currentAddressInfo) {
		super();
		this.employeeId = employeeId;
		this.employeeCode = employeeInfo.getEmployeeCode();
		this.insuranceNumber = empInsNumInfo.getEmpInsNumber().v();
		this.laborInsuranceOfficeNumber1 = laborInsuranceOffice != null ? laborInsuranceOffice.getEmploymentInsuranceInfomation().getOfficeNumber1()
				.map(n -> n + "").orElse("") : "";
		this.laborInsuranceOfficeNumber2 = laborInsuranceOffice != null ? laborInsuranceOffice.getEmploymentInsuranceInfomation().getOfficeNumber2()
				.map(n -> n + "").orElse("") : "";
		this.laborInsuranceOfficeNumber3 = laborInsuranceOffice != null ? laborInsuranceOffice.getEmploymentInsuranceInfomation().getOfficeNumber3()
				.map(n -> n + "").orElse("") : "";
		this.employeeInsurancePeriodStart = empInsHist.start();
		this.employeeInsurancePeriodEnd = empInsHist.end();
		this.causeOfLossAtr = empInsLossInfo != null ? empInsLossInfo.getCauseOfLossAtr().map(c -> c.value).orElse(null) : null;
		this.scheduleOfReplenishment = empInsLossInfo != null ? empInsLossInfo.getScheduleForReplenishment().map(s -> s.value == 1 ? "有" : "無")
				.orElse("") : "";
		this.personNameKana = personInfo.getPersonNameGroup().getPersonName().getFullNameKana();
		this.personReportNameKana = personInfo.getPersonNameGroup().getTodokedeFullName().getFullNameKana();
		this.personName = personInfo.getPersonNameGroup().getPersonName().getFullName();
		this.personReportName = personInfo.getPersonNameGroup().getTodokedeFullName().getFullName();
		this.personNameRomanji = personInfo.getPersonNameGroup().getPersonRomanji().getFullName();
		this.personGender = personInfo.getGender();
		this.personBirthDay = personInfo.getBirthDate();
		this.personCurrentAddress = currentAddressInfo.getAddress1() + currentAddressInfo.getAddress2();
		this.companyName = companyInfo.getCompanyName();
		this.laborInsuranceOfficeName = laborInsuranceOffice != null ? laborInsuranceOffice.getLaborOfficeName().v() : "";
		this.causeOfLossInsurance = empInsLossInfo != null ? empInsLossInfo.getCauseOfLossEmpInsurance().map(c -> c.v()).orElse("") : "";
		this.scheduleWorkingHourPerWeek = empInsLossInfo != null ? empInsLossInfo.getScheduleWorkingHourPerWeek().orElse(null) : null;
		this.publicEmploymentSecurityOfficeName = pubEmpSecOffice != null ? pubEmpSecOffice.getPublicEmploymentSecurityOfficeName().v() : "";
	}
}
