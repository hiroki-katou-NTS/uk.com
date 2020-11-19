package nts.uk.ctx.at.shared.dom;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 勤務情報と補正済み所定時間帯
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.就業規則.勤務情報と補正済み所定時間帯
 * @author tutk
 *
 */
@Value
public class WorkInfoAndTimeZone {

	/** 勤務種類 **/
	private final WorkType workType;

	/** 就業時間帯**/
	private final Optional<WorkTimeSetting> workTime;

	/** 補正済み所定時間帯 **/
	private final List<TimeZone> timeZones;


	/**
	 * 作成する
	 * @param workType 勤務種類コード
	 * @param workTime 就業時間帯コード
	 * @param listTimezoneUse
	 */
	public static WorkInfoAndTimeZone create(WorkType workType, WorkTimeSetting workTime, List<TimeZone> timeZones) {
		return new WorkInfoAndTimeZone( workType, Optional.of(workTime), timeZones );
	}

	/**
	 * 就業時間帯なしで作成する
	 * @param workType 勤務種類コード
	 */
	public static WorkInfoAndTimeZone createWithoutWorkTime(WorkType workType) {
		return new WorkInfoAndTimeZone( workType, Optional.empty(), Collections.emptyList() );
	}

}
