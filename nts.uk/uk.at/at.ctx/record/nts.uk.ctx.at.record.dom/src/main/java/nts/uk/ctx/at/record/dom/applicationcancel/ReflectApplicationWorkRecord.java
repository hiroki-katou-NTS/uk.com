package nts.uk.ctx.at.record.dom.applicationcancel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import lombok.val;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.shared.dom.application.common.ApplicationShare;
import nts.uk.ctx.at.shared.dom.application.common.ReflectedStateShare;
import nts.uk.ctx.at.shared.dom.application.common.StampRequestModeShare;
import nts.uk.ctx.at.shared.dom.application.reflect.ReflectStatusResultShare;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.cancellation.CreateApplicationReflectionHist;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.RCCreateDailyAfterApplicationeReflect;
import nts.uk.ctx.at.shared.dom.application.stamp.AppRecordImageShare;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.CorrectDailyAttendanceService;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailywork.worktime.empwork.EmployeeWorkDataSetting;
import nts.uk.ctx.at.shared.dom.dailyprocess.calc.CalculateOption;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.ChangeDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerCompanySet;

/**
 * @author thanh_nx
 *
 *         [RQ665]申請を勤務実績へ反映する
 */
public class ReflectApplicationWorkRecord {

	public static Pair<ReflectStatusResultShare, Optional<AtomTask>> process(Require require,
			ApplicationShare application, GeneralDate date, ReflectStatusResultShare reflectStatus) {

		// [input.申請.打刻申請モード]をチェック
		GeneralDate dateTarget = date;
		Optional<Stamp> stamp = Optional.empty();
		if (application.getOpStampRequestMode().isPresent()
				&& application.getOpStampRequestMode().get() == StampRequestModeShare.STAMP_ONLINE_RECORD) {
			// レコーダイメージ申請の対象日を取得する
			Pair<Optional<GeneralDate>, Optional<Stamp>> dateOpt = GetTargetDateRecordApplication.getTargetDate(require,
					(AppRecordImageShare) application);
			if (dateOpt.getLeft().isPresent()) {
				dateTarget = dateOpt.getLeft().get();
				stamp = dateOpt.getRight();
			}
		}

		// 勤務実績から日別実績(work）を取得する
		IntegrationOfDaily domainDaily = require.findDaily(application.getEmployeeID(), dateTarget);
		if (domainDaily == null)
			return Pair.of(reflectStatus, Optional.empty());

		// input.日別勤怠(work）を[反映前の日別勤怠(work)]へコピーして保持する
		IntegrationOfDaily domainBeforeReflect = createDailyDomain(require, domainDaily);

		// 日別勤怠(申請取消用Work）を作成して、日別勤怠(work）をコピーする
		DailyRecordOfApplication dailyRecordApp = new DailyRecordOfApplication(new ArrayList<>(),
				ScheduleRecordClassifi.RECORD, createDailyDomain(require, domainDaily));

		// 申請.打刻申請モードをチェック
		ChangeDailyAttendance changeAtt;
		if (application.getOpStampRequestMode().isPresent()
				&& application.getOpStampRequestMode().get() == StampRequestModeShare.STAMP_ONLINE_RECORD) {
			changeAtt = new ChangeDailyAttendance(true, true, true, false, false);
			/// 打刻申請（NRモード）を反映する -- itemId
			TimeStampApplicationNRMode.process(require, dateTarget,
					(AppRecordImageShare) application, dailyRecordApp, stamp, changeAtt);
		} else {
			/// 申請の反映（勤務実績） in process
			val affterReflect = RCCreateDailyAfterApplicationeReflect.process(require, application, dailyRecordApp, dateTarget);

			changeAtt = createChangeDailyAtt(affterReflect.getLstItemId());
		}

		// 日別実績の補正処理 --- create default ???? sau xu ly phan anh check lai
		IntegrationOfDaily domainCorrect = CorrectDailyAttendanceService.processAttendanceRule(require,
				dailyRecordApp.getDomain(), changeAtt);

		// 振休振出として扱う日数を補正する
		WorkInfoOfDailyAttendance workInfoAfter = CorrectDailyAttendanceService.correctFurikyu(require,
				domainBeforeReflect.getWorkInformation(), domainCorrect.getWorkInformation());
		domainCorrect.setWorkInformation(workInfoAfter);
		dailyRecordApp.setDomain(domainCorrect);

		// 日別実績の修正からの計算 -- co xu ly tinh toan khac ko hay cua lich
		List<IntegrationOfDaily> lstAfterCalc = require.calculateForSchedule(CalculateOption.asDefault(),
				Arrays.asList(domainCorrect), Optional.empty());
		if (!lstAfterCalc.isEmpty()) {
			dailyRecordApp.setDomain(lstAfterCalc.get(0));
		}

		AtomTask task = AtomTask.of(() -> {
			// 勤務実績の更新
			require.addAllDomain(dailyRecordApp.getDomain());

			// 申請反映履歴を作成する
			CreateApplicationReflectionHist.create(require, application.getAppID(), ScheduleRecordClassifi.SCHEDULE,
					dailyRecordApp, domainBeforeReflect);

		});
		// [input.勤務実績の反映状態]を「反映済み」に更新する
		reflectStatus.setReflectStatus(ReflectedStateShare.REFLECTED);

		return Pair.of(reflectStatus, Optional.of(task));
	}
	
	private static ChangeDailyAttendance createChangeDailyAtt(List<Integer> lstItemId) {

		boolean workInfo = lstItemId.stream().filter(x -> x.intValue() == 28 || x.intValue() == 29).findFirst()
				.isPresent();
		boolean scheduleWorkInfo = lstItemId.stream().filter(x -> x.intValue() == 1 || x.intValue() == 2).findFirst()
				.isPresent();
		boolean attendance = lstItemId.stream()
				.filter(x -> x.intValue() == 31 || x.intValue() == 34 || x.intValue() == 41 || x.intValue() == 44)
				.findFirst().isPresent();
		return new ChangeDailyAttendance(workInfo, scheduleWorkInfo, attendance, false, workInfo);
	}

	private static IntegrationOfDaily createDailyDomain(Require require, IntegrationOfDaily domainDaily) {
		DailyRecordToAttendanceItemConverter converter = require.createDailyConverter();
		converter.setData(domainDaily).employeeId(domainDaily.getEmployeeId()).workingDate(domainDaily.getYmd());
		return converter.toDomain();
	}

	public static interface Require extends GetTargetDateRecordApplication.Require,
			CorrectDailyAttendanceService.Require, CreateApplicationReflectionHist.Require,
			TimeStampApplicationNRMode.Require, RCCreateDailyAfterApplicationeReflect.Require {
		/**
		 * 
		 * require{ 社員の作業データ設定を取得する(社員ID） }
		 * 
		 */
		public Optional<EmployeeWorkDataSetting> getEmpWorkDataSetting(String employeeId);

		// DailyRecordShareFinder
		public IntegrationOfDaily findDaily(String employeeId, GeneralDate date);

		// ConvertDailyRecordToAd
		public DailyRecordToAttendanceItemConverter createDailyConverter();

		// CalculateDailyRecordServiceCenter
		public List<IntegrationOfDaily> calculateForSchedule(CalculateOption calcOption,
				List<IntegrationOfDaily> integrationOfDaily, Optional<ManagePerCompanySet> companySet);

		// DailyRecordAdUpService
		public void addAllDomain(IntegrationOfDaily domain);
	}
}
