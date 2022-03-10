package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.stampapplication.algorithm.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import lombok.val;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.scherec.application.stamp.StartEndClassificationShare;
import nts.uk.ctx.at.shared.dom.scherec.application.stamp.TimeStampAppShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.UpdateEditSttCreateBeforeAppReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.stampapplication.algorithm.CancelAppStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.ReasonTimeChange;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkTimeInformation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.SupportFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.TimeSheetOfAttendanceEachOuenSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.WorkContent;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.record.WorkplaceOfWorkEachOuen;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;

/**
 * @author thanh_nx
 *
 *         応援開始・終了の反映
 */
public class ReflectSupportStartEnd {

	public static List<Integer> reflect(Require require, DailyRecordOfApplication dailyApp,
			List<TimeStampAppShare> listTimeStampApp) {
		List<Integer> lstItemId = new ArrayList<>();
		//input.応援(List）でループ
		listTimeStampApp.stream().forEach(data -> {

			Optional<OuenWorkTimeSheetOfDailyAttendance> ouenOpt = dailyApp.getOuenTimeSheet().stream().filter(
					x -> x.getWorkNo().v() == data.getDestinationTimeApp().getStampNo().intValue())
					.findFirst();

			if (ouenOpt.isPresent()) {
				val result = update(require, ouenOpt.get(), data);
				OuenWorkTimeSheetOfDailyAttendance update = result.getLeft();

				dailyApp.getOuenTimeSheet().remove(ouenOpt.get());
				dailyApp.getOuenTimeSheet().add(update);
				lstItemId.addAll(result.getRight());
			} else {
				//処理中の応援枠NOをキーに[日別勤怠の応援作業時間帯]を作成する
				val result = create(require, dailyApp, data);
				dailyApp.getOuenTimeSheet().add(result.getLeft());
				lstItemId.addAll(result.getRight());
			}
		});

		// 申請反映状態にする
		UpdateEditSttCreateBeforeAppReflect.update(dailyApp, lstItemId.stream().distinct().collect(Collectors.toList()));
		return lstItemId.stream().distinct().collect(Collectors.toList());
	}

	private static Pair<OuenWorkTimeSheetOfDailyAttendance, List<Integer>> create(Require require,
			DailyRecordOfApplication dailyApp, TimeStampAppShare data) {

		List<Integer> lstItemId = new ArrayList<>();
		TimeSheetOfAttendanceEachOuenSheet sheet = null;
		if (data.getDestinationTimeApp().getStartEndClassification() == StartEndClassificationShare.START) {
			sheet = TimeSheetOfAttendanceEachOuenSheet.create(
					new WorkNo(data.getDestinationTimeApp().getWorkNo().orElse(null)),
					Optional.of(new WorkTimeInformation(new ReasonTimeChange(TimeChangeMeans.APPLICATION, Optional.empty()),
							data.getTimeOfDay())),
					Optional.empty());
			lstItemId.add(CancelAppStamp.createItemId(929, data.getDestinationTimeApp().getStampNo().intValue(), 10));
		} else {
			sheet = TimeSheetOfAttendanceEachOuenSheet.create(
					new WorkNo(data.getDestinationTimeApp().getWorkNo().orElse(null)), Optional.empty(),
					Optional.of(new WorkTimeInformation(new ReasonTimeChange(TimeChangeMeans.APPLICATION, Optional.empty()),
							data.getTimeOfDay())));
			lstItemId.add(CancelAppStamp.createItemId(930, data.getDestinationTimeApp().getStampNo().intValue(), 10));
		}

		WorkplaceOfWorkEachOuen workplace = WorkplaceOfWorkEachOuen.create(
				new WorkplaceId(data.getWorkPlaceId().map(x -> x.v()).orElse(dailyApp.getAffiliationInfor().getWplID())),
				data.getWorkLocationCd().orElse(null));
		lstItemId.add(CancelAppStamp.createItemId(921, data.getDestinationTimeApp().getStampNo().intValue(), 10));
		lstItemId.add(CancelAppStamp.createItemId(922, data.getDestinationTimeApp().getStampNo().intValue(), 10));

		WorkContent workContent = WorkContent.create(workplace, Optional.empty(), Optional.empty());
		return Pair.of(
				OuenWorkTimeSheetOfDailyAttendance.create(
						SupportFrameNo.of(data.getDestinationTimeApp().getStampNo().intValue()), workContent, sheet, Optional.empty()),
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
									.flatMap(x -> x.getReasonTimeChange().getEngravingMethod())),
							data.getTimeOfDay())),
					old.getTimeSheet().getEnd());
			lstItemId.add(CancelAppStamp.createItemId(929, data.getDestinationTimeApp().getStampNo().intValue(), 10));
		} else {
			sheet = TimeSheetOfAttendanceEachOuenSheet.create(
					new WorkNo(data.getDestinationTimeApp().getWorkNo().orElse(null)), old.getTimeSheet().getStart(),
					Optional.of(new WorkTimeInformation(
							new ReasonTimeChange(TimeChangeMeans.APPLICATION, old.getTimeSheet().getEnd()
									.flatMap(x -> x.getReasonTimeChange().getEngravingMethod())),
							data.getTimeOfDay())));
			lstItemId.add(CancelAppStamp.createItemId(930, data.getDestinationTimeApp().getStampNo().intValue(), 10));
		}

		WorkplaceOfWorkEachOuen workplace = null;
		workplace = WorkplaceOfWorkEachOuen.create(data.getWorkPlaceId().map(x -> new WorkplaceId(x.v()))
				.orElse(old.getWorkContent().getWorkplace().getWorkplaceId()), data.getWorkLocationCd().orElse(null));
		lstItemId.add(CancelAppStamp.createItemId(922, data.getDestinationTimeApp().getStampNo().intValue(), 10));
		lstItemId.add(CancelAppStamp.createItemId(921, data.getDestinationTimeApp().getStampNo().intValue(), 10));

		WorkContent workContent = WorkContent.create(workplace, old.getWorkContent().getWork(), Optional.empty());
		return Pair.of(OuenWorkTimeSheetOfDailyAttendance.create(old.getWorkNo(), workContent, sheet, Optional.empty()), lstItemId);

	}

	public static interface Require {
	}
}
