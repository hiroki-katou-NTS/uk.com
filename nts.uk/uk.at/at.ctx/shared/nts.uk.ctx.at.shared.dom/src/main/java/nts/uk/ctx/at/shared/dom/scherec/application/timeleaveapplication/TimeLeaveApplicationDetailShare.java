package nts.uk.ctx.at.shared.dom.scherec.application.timeleaveapplication;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.AppTimeType;

/**
 * 時間休暇申請詳細
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimeLeaveApplicationDetailShare {

	/**
	 * 時間休種類
	 */
	private AppTimeType appTimeType;

	/**
	 * 時間帯
	 */
	private List<TimeZoneWithWorkNo> timeZoneWithWorkNoLst;

	/**
	 * 申請時間
	 */
	private TimeDigestApplicationShare timeDigestApplication;

}
