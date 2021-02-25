package nts.uk.ctx.at.shared.dom.application.reflectprocess.cancellation;

import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

/**
 * @author thanh_nx
 *
 *         申請反映履歴を作成する
 */
public class CreateApplicationReflectionHist {

	public static void create(Require require, String appId, ScheduleRecordClassifi classifi,
			DailyRecordOfApplication dailyRecordApp, IntegrationOfDaily domainBefore) {

		// 申請反映前のデータを取得
		AddDataBeforeApplicationReflect.process(require, dailyRecordApp.getAttendanceBeforeReflect(), domainBefore);

		// 申請反映履歴を追加する
		require.insertAppReflectHist(new ApplicationReflectHistory(domainBefore.getEmployeeId(), domainBefore.getYmd(), appId,
				GeneralDateTime.now(), classifi, false, dailyRecordApp.getAttendanceBeforeReflect()));
	}

	public static interface Require extends AddDataBeforeApplicationReflect.Require {

		public void insertAppReflectHist(ApplicationReflectHistory hist);
	}

}
