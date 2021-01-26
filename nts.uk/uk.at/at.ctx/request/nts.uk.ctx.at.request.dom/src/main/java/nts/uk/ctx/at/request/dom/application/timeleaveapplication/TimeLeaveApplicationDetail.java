package nts.uk.ctx.at.request.dom.application.timeleaveapplication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.AppTimeType;
import nts.uk.ctx.at.request.dom.application.appabsence.apptimedigest.TimeDigestApplication;

import java.util.List;

/**
 * 時間休暇申請詳細
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimeLeaveApplicationDetail {

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
	private TimeDigestApplication timeDigestApplication;

}
