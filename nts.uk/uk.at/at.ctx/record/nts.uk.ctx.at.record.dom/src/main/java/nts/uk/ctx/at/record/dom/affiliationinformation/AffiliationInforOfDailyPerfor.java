package nts.uk.ctx.at.record.dom.affiliationinformation;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.LicenseClassification;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.ClassificationCode;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.dom.workrule.businesstype.BusinessTypeCode;

/**
 * 
 * @author nampt
 * 日別実績の所属情報 - root
 *
 */
@Getter
@NoArgsConstructor
public class AffiliationInforOfDailyPerfor extends AggregateRoot {
	//社員ID
	private String employeeId;
	//年月日
	private GeneralDate ymd;
	//所属情報
	private AffiliationInforOfDailyAttd  affiliationInfor;

	public AffiliationInforOfDailyPerfor(EmploymentCode employmentCode, String employeeId, String jobTitleID,
			String wplID, GeneralDate ymd, ClassificationCode clsCode, BonusPaySettingCode bonusPaySettingCode,BusinessTypeCode businessTypeCode,
			String workplaceGroupId,
			LicenseClassification nursingLicenseClass,
			Boolean isNursingManager) {
		super();
		this.employeeId = employeeId;
		this.ymd = ymd;
		this.affiliationInfor = new AffiliationInforOfDailyAttd(
				employmentCode, 
				jobTitleID, 
				wplID, 
				clsCode,
				Optional.ofNullable(businessTypeCode),
				Optional.ofNullable(bonusPaySettingCode),
				Optional.ofNullable(workplaceGroupId), 
				EnumAdaptor.optionalOf(nursingLicenseClass == null ? null : nursingLicenseClass.value , LicenseClassification.class),
				Optional.ofNullable(isNursingManager));
	}

	public AffiliationInforOfDailyPerfor(String employeeId, GeneralDate ymd,
			AffiliationInforOfDailyAttd affiliationInfor) {
		super();
		this.employeeId = employeeId;
		this.ymd = ymd;
		this.affiliationInfor = affiliationInfor;
	}


}
