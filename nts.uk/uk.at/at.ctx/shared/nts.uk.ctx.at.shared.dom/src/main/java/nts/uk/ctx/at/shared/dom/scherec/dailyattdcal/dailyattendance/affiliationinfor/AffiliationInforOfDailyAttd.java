package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.objecttype.DomainObject;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.primitives.BonusPaySettingCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.primitivevalue.BusinessTypeCode;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * 日別勤怠の所属情報
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).日の勤怠計算.日別勤怠.所属情報.日別勤怠の所属情報
 * @author tutk
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class AffiliationInforOfDailyAttd implements DomainObject  {
	//雇用コード
		private EmploymentCode employmentCode;
		//職位ID
		private String jobTitleID;
		//職場ID
		private String wplID;
		//分類コード
		private ClassificationCode clsCode;
		//勤務種別コード
		private Optional<BusinessTypeCode> businessTypeCode;
		//加給コード : optional
		private Optional<BonusPaySettingCode> bonusPaySettingCode;

		public AffiliationInforOfDailyAttd(EmploymentCode employmentCode, String jobTitleID, String wplID,
				ClassificationCode clsCode, Optional<BusinessTypeCode> businessTypeCode,
				Optional<BonusPaySettingCode> bonusPaySettingCode) {
			super();
			this.employmentCode = employmentCode;
			this.jobTitleID = jobTitleID;
			this.wplID = wplID;
			this.clsCode = clsCode;
			this.businessTypeCode = businessTypeCode;
			this.bonusPaySettingCode = bonusPaySettingCode;
		}
}
