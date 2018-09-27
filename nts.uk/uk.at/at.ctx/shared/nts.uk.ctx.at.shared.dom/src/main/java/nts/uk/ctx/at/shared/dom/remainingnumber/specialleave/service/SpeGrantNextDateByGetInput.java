package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveAppSetting;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SpeGrantNextDateByGetInput {
	/** 会社ID */
	private String cid;
	/** 特別休暇コード */
	private int specialCode;
	/** 期間	 */
	private DatePeriod datePeriod;
	/**	特休付与基準日 */
	private GeneralDate specialDate;
	/**	適用設定 */
	private SpecialLeaveAppSetting specialSetting;
	/**	特休付与テーブルコード */
	private Optional<String> speGrantDataCode;
	/**	付与日数 */
	private Optional<Double> granDays;
	/**	入社年月日 */
	private GeneralDate inputDate;
	/**	年休付与基準日 */
	private GeneralDate AnnualHolidayDate;
}
