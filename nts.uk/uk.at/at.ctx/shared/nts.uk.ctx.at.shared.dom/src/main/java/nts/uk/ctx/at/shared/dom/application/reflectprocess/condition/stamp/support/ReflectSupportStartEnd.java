package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.stamp.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import lombok.val;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.UpdateEditSttCreateBeforeAppReflect;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.stamp.CancelAppStamp;
import nts.uk.ctx.at.shared.dom.application.stamp.StartEndClassificationShare;
import nts.uk.ctx.at.shared.dom.application.stamp.TimeStampAppShare;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailywork.worktime.empwork.EmployeeWorkDataSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.ReasonTimeChange;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkTimeInformation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.TimeSheetOfAttendanceEachOuenSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.WorkContent;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.record.WorkplaceOfWorkEachOuen;

/**
 * @author thanh_nx
 *
 *         応援開始・終了の反映
 */
public class ReflectSupportStartEnd {

	public static List<Integer> reflect(Require require, DailyRecordOfApplication dailyApp,
			List<TimeStampAppShare> listTimeStampApp) {
		List<Integer> lstItemId = new ArrayList<>();
		Optional<EmployeeWorkDataSetting> empSetOpt = require.getEmpWorkDataSetting(dailyApp.getEmployeeId());
		listTimeStampApp.stream().forEach(data -> {

			Optional<OuenWorkTimeSheetOfDailyAttendance> ouenOpt = dailyApp.getOuenTimeSheet().stream().filter(
					x -> x.getWorkNo() == data.getDestinationTimeApp().getSupportWork().orElse(Integer.MAX_VALUE))
					.findFirst();

			if (ouenOpt.isPresent()) {
				val result = update(require, ouenOpt.get(), data);
				OuenWorkTimeSheetOfDailyAttendance update = result.getLeft();

				dailyApp.getOuenTimeSheet().remove(ouenOpt.get());
				dailyApp.getOuenTimeSheet().add(update);
				lstItemId.addAll(result.getRight());
			} else {

				val result = create(require, empSetOpt, data);
				dailyApp.getOuenTimeSheet().add(result.getLeft());
				lstItemId.addAll(result.getRight());
			}
		});

		// 申請反映状態にする
		UpdateEditSttCreateBeforeAppReflect.update(dailyApp, lstItemId);
		return lstItemId;
	}

	private static Pair<OuenWorkTimeSheetOfDailyAttendance, List<Integer>> create(Require require,
			Optional<EmployeeWorkDataSetting> empSetOpt, TimeStampAppShare data) {

		List<Integer> lstItemId = new ArrayList<>();
		TimeSheetOfAttendanceEachOuenSheet sheet = null;
		if (data.getDestinationTimeApp().getStartEndClassification() == StartEndClassificationShare.START) {
			sheet = TimeSheetOfAttendanceEachOuenSheet.create(
					new WorkNo(data.getDestinationTimeApp().getSupportWork().orElse(null)),
					Optional.of(new WorkTimeInformation(new ReasonTimeChange(TimeChangeMeans.APPLICATION, null),
							data.getTimeOfDay())),
					Optional.empty());
			lstItemId.add(CancelAppStamp.createItemId(929, data.getDestinationTimeApp().getEngraveFrameNo(), 10));
		} else {
			sheet = TimeSheetOfAttendanceEachOuenSheet.create(
					new WorkNo(data.getDestinationTimeApp().getSupportWork().orElse(null)), Optional.empty(),
					Optional.of(new WorkTimeInformation(new ReasonTimeChange(TimeChangeMeans.APPLICATION, null),
							data.getTimeOfDay())));
			lstItemId.add(CancelAppStamp.createItemId(930, data.getDestinationTimeApp().getEngraveFrameNo(), 10));
		}

		WorkplaceOfWorkEachOuen workplace = null;
		if (empSetOpt.isPresent()) {
			workplace = WorkplaceOfWorkEachOuen.create(empSetOpt.get().getWorkplaceId(),
					empSetOpt.get().getWorkLocationCD());
			lstItemId.add(CancelAppStamp.createItemId(921, data.getDestinationTimeApp().getEngraveFrameNo(), 10));
		}

		WorkContent workContent = WorkContent.create(require.getCId(), workplace, Optional.empty());
		return Pair.of(
				OuenWorkTimeSheetOfDailyAttendance.create(
						data.getDestinationTimeApp().getSupportWork().orElse(Integer.MAX_VALUE), workContent, sheet),
				lstItemId);

	}

	private static Pair<OuenWorkTimeSheetOfDailyAttendance, List<Integer>> update(Require require, 
			OuenWorkTimeSheetOfDailyAttendance old,
			TimeStampAppShare data) {
		List<Integer> lstItemId = new ArrayList<>();
		TimeSheetOfAttendanceEachOuenSheet sheet = null;
		if (data.getDestinationTimeApp().getStartEndClassification() == StartEndClassificationShare.START) {
			sheet = TimeSheetOfAttendanceEachOuenSheet.create(old.getTimeSheet().getWorkNo(),
					Optional.of(new WorkTimeInformation(
							new ReasonTimeChange(TimeChangeMeans.APPLICATION, old.getTimeSheet().getStart()
									.map(x -> x.getReasonTimeChange().getEngravingMethod()).orElse(null)),
							data.getTimeOfDay())),
					old.getTimeSheet().getEnd());
			lstItemId.add(CancelAppStamp.createItemId(929, data.getDestinationTimeApp().getEngraveFrameNo(), 10));
		} else {
			sheet = TimeSheetOfAttendanceEachOuenSheet.create(
					new WorkNo(data.getDestinationTimeApp().getEngraveFrameNo()), old.getTimeSheet().getStart(),
					Optional.of(new WorkTimeInformation(
							new ReasonTimeChange(TimeChangeMeans.APPLICATION, old.getTimeSheet().getEnd()
									.map(x -> x.getReasonTimeChange().getEngravingMethod()).orElse(null)),
							data.getTimeOfDay())));
			lstItemId.add(CancelAppStamp.createItemId(930, data.getDestinationTimeApp().getEngraveFrameNo(), 10));
		}

		WorkplaceOfWorkEachOuen workplace = null;
		if (data.getWorkLocationCd().isPresent()) {
			workplace = WorkplaceOfWorkEachOuen.create(old.getWorkContent().getWorkplace().getWorkplaceId(),
					data.getWorkLocationCd().get());
			lstItemId.add(CancelAppStamp.createItemId(921, data.getDestinationTimeApp().getEngraveFrameNo(), 10));
		} else {
			workplace = old.getWorkContent().getWorkplace();
		}

		WorkContent workContent = WorkContent.create(require.getCId(), workplace,
				old.getWorkContent().getWork());
		return Pair.of(OuenWorkTimeSheetOfDailyAttendance.create(old.getWorkNo(), workContent, sheet), lstItemId);

	}

	public static interface Require {
		
		public String getCId();
		/**
		 * 
		 * require{ 社員の作業データ設定を取得する(社員ID） }
		 * 
		 */
		public Optional<EmployeeWorkDataSetting> getEmpWorkDataSetting(String employeeId);
	}
}
