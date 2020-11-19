package nts.uk.ctx.at.record.dom.affiliationinformation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.ClassificationCode;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AffiliationInforOfDailyTempo {
		//社員ID
		private String employeeId;
		//年月日
		private GeneralDate ymd;
		private EmploymentCode employmentCode;
		//職位ID
		private String jobTitleID;
		//職場ID
		private String wplID;
		//分類コード
		private ClassificationCode clsCode;
		//勤務種別コード
		private BonusPaySettingCode bonusPaySettingCode;
}
