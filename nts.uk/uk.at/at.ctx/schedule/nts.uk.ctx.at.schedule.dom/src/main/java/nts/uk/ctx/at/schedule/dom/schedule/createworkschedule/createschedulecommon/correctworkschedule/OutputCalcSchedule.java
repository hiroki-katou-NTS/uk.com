package nts.uk.ctx.at.schedule.dom.schedule.createworkschedule.createschedulecommon.correctworkschedule;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLog;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class OutputCalcSchedule {
	/**
	 * 勤務予定一覧
	 */
	List<WorkSchedule> listWorkSchedule = new ArrayList<>();
	/**
	 * エラー一覧
	 */
	List<ScheduleErrorLog> listError = new ArrayList<>();
}