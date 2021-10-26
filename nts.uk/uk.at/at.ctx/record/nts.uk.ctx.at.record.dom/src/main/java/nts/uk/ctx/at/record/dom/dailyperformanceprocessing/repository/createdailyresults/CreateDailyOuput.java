package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults.reflectedperiod.ReflectedAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.ChangeDailyAttendance;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrorMessageInfo;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateDailyOuput {
	/**
	 * 日別実績一覧
	 */
	private List<IntegrationOfDaily> listIntegrationOfDaily = new ArrayList<>();

	/**
	 * 終了状態
	 */
	private ReflectedAtr reflectedPeriod;

	/**
	 * エラー一覧（Map＜年月日、エラー＞）
	 */
	private List<ErrorMessageInfo> listError = new ArrayList<>();

	/**
	 * 日別勤怠の何が変更されたか一覧（Map＜年月日、日別勤怠の何が変更されたか＞）
	 */
	private Map<GeneralDate, ChangeDailyAttendance> changedDailyAttendance = new HashMap<>();

}
