package nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.algorithm;

import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import nts.uk.ctx.at.record.dom.adapter.workschedule.WorkScheduleWorkInforImport;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.ExtractionCondScheduleDay;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.FixedExtractionSDailyCon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.schedule.daily.FixedExtractionSDailyItems;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * Schedule Daily: Output of チェックする前にデータ準備
 *
 */
@Getter
@Builder
public class ScheDailyPrepareData {
	/**
	 * List<勤怠項目コード、勤怠項目名称＞
	 */
	private Map<Integer, String> attendanceItems;
	
	/**
	 * List＜勤務種類＞
	 */
	private List<WorkType> listWorkType;
	
	/**
	 * List＜就業時間帯＞
	 */
	private List<WorkTimeSetting> listWorktime;
	
	/**
	 * List＜勤務予定＞
	 */
	private List<WorkScheduleWorkInforImport> workScheduleWorkInfos;
	
	/**
	 * List＜スケジュール日次の任意抽出条件＞
	 */
	private List<ExtractionCondScheduleDay> scheCondItems;
	
	/**
	 * List＜スケジュール日次の固有抽出条件＞
	 */
	private List<FixedExtractionSDailyCon> fixedScheCondItems;
	
	/**
	 * List＜スケジュール日次の固有抽出項目＞
	 */
	private List<FixedExtractionSDailyItems> fixedItems;
	
	private List<IntegrationOfDaily> listIntegrationDai;
}
