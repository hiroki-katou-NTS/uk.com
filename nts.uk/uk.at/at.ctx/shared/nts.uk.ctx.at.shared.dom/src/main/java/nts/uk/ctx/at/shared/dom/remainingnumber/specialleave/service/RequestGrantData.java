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
@Setter
@Getter
@NoArgsConstructor
public class RequestGrantData {
	/**	期間 */
	private DatePeriod datePeriod;
	/** 付与基準日	 */
	private GeneralDate grantDate;
	/**	単一フラグ */
	private boolean signFlg;
	/**	特別休暇適用設定 */
	private SpecialLeaveAppSetting specialSetting;
	/**	固定付与日数 */
	private Optional<Double> fixedGrantDays;
	/**	会社ID */
	private String cid;
	/** 付与テーブルコード	 */
	private Optional<String> grantTblCd; 
	/**	特別休暇コード */
	private int speHolidayCd;
}
