package nts.uk.ctx.at.shared.dom;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 勤務情報と補正済み所定時間帯
 * 
 * @author tutk
 *
 */

public class WorkInfoAndTimeZone {

	/**
	 * 勤務種類
	 */
	@Getter
	private final WorkType workType;

	/**
	 * 就業時間帯
	 */
	@Getter
	private final Optional<WorkTimeSetting> workTime;

	/**
	 * 補正済み所定時間帯
	 */
	@Getter
	private final List<TimeZone> listTimeZone;

	/**
	 * [C-1] 作成する
	 * 
	 * @param workType
	 * @param workTime
	 * @param listTimezoneUse
	 */
	public WorkInfoAndTimeZone(WorkType workType, WorkTimeSetting workTime, List<TimeZone> listTimeZone) {
		super();
		// [inv-1] case @就業時間帯.isEmpty() : @補正済み所定時間帯.size() == 0
		if (workTime == null) {
			this.workTime = Optional.empty();
			this.listTimeZone = new ArrayList<>();
		} else {
			this.workTime = Optional.ofNullable(workTime);
			this.listTimeZone = listTimeZone;
		}
		this.workType = workType;
	}
	
	/**
	 * [C-2] 就業時間帯なしで作成する
	 * @param workType
	 */
	public WorkInfoAndTimeZone(WorkType workType) {
		super();
		this.workType = workType;
		this.workTime = Optional.empty();
		this.listTimeZone = new ArrayList<>();
	}

}
