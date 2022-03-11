package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.stampapplication.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import lombok.val;
import nts.uk.ctx.at.shared.dom.scherec.application.stamp.StartEndClassificationShare;
import nts.uk.ctx.at.shared.dom.scherec.application.stamp.TimeStampAppShare;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.condition.UpdateEditSttCreateBeforeAppReflect;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.ReasonTimeChange;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkTimeInformation;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;

/**
 * @author thanh_nx
 *
 *         外出時間帯の反映
 */
public class ReflectOutingTimeZone {

	public static List<Integer> process(DailyRecordOfApplication dailyApp, List<TimeStampAppShare> listTimeStampApp) {

		List<Integer> lstItemId = new ArrayList<>();
		// input. 外出時間帯（List）でループする
		listTimeStampApp.stream().forEach(data -> {
			if (dailyApp.getOutingTime().isPresent()) {

				// 日別勤怠(work）の[外出時間帯]をチェック
				Optional<OutingTimeSheet> outOpt = dailyApp.getOutingTime().get().getOutingTimeSheets().stream().filter(
						x -> x.getOutingFrameNo().v() == data.getDestinationTimeApp().getStampNo().intValue())
						.findFirst();

				if (outOpt.isPresent()) {
					lstItemId.addAll(updateDomain(outOpt.get(), data));
				} else {
					//
					val result = create(data);
					dailyApp.getOutingTime().get().getOutingTimeSheets().add(result.getLeft());
					lstItemId.addAll(result.getRight());
				}

			} else {
				List<OutingTimeSheet> outs = new ArrayList<>();
				val result = create(data);
				outs.add(result.getLeft());
				lstItemId.addAll(result.getRight());
				dailyApp.setOutingTime(Optional.of(new OutingTimeOfDailyAttd(outs)));
			}
		});
		//申請反映状態にする
		UpdateEditSttCreateBeforeAppReflect.update(dailyApp, lstItemId);
		return lstItemId;
	}

	private static Pair<OutingTimeSheet, List<Integer>> create(TimeStampAppShare data) {

		List<Integer> lstItemId = new ArrayList<>();
		if (data.getDestinationTimeApp().getStartEndClassification() == StartEndClassificationShare.START) {
			lstItemId.addAll(
					Arrays.asList(CancelAppStamp.createItemId(88, data.getDestinationTimeApp().getStampNo(), 7),
							CancelAppStamp.createItemId(87, data.getDestinationTimeApp().getStampNo(), 7)));
			return Pair.of(
					new OutingTimeSheet(new OutingFrameNo(data.getDestinationTimeApp().getStampNo().intValue()),
							Optional.of(new WorkStamp(
									new WorkTimeInformation(new ReasonTimeChange(TimeChangeMeans.APPLICATION, Optional.empty()),
											data.getTimeOfDay()),
									data.getWorkLocationCd())),
							data.getAppStampGoOutAtr().map(x -> GoingOutReason.valueOf(x.value)).orElse(null),
							Optional.empty()),
					lstItemId);
		} else {
			lstItemId.addAll(
					Arrays.asList(CancelAppStamp.createItemId(91, data.getDestinationTimeApp().getStampNo(), 7),
							CancelAppStamp.createItemId(90, data.getDestinationTimeApp().getStampNo(), 7)));
			return Pair.of(new OutingTimeSheet(
					new OutingFrameNo(data.getDestinationTimeApp().getStampNo().intValue()), Optional.empty(),
					data.getAppStampGoOutAtr().map(x -> GoingOutReason.valueOf(x.value)).orElse(null),
					Optional.of(new WorkStamp(
									new WorkTimeInformation(new ReasonTimeChange(TimeChangeMeans.APPLICATION, null),
											data.getTimeOfDay()),
									data.getWorkLocationCd()))),
					lstItemId);
		}
	}

	private static List<Integer> updateDomain(OutingTimeSheet sheet, TimeStampAppShare data) {
		List<Integer> lstItemId = new ArrayList<>();
		if (data.getDestinationTimeApp().getStartEndClassification() == StartEndClassificationShare.START) {
			sheet.setReasonForGoOut(data.getAppStampGoOutAtr().map(x -> GoingOutReason.valueOf(x.value)).orElse(null));
			if(!sheet.getGoOut().isPresent()) {
				sheet.setGoOut(Optional.of(WorkStamp.createDefault()));
			}
			sheet.getGoOut().ifPresent(y -> {
				data.getWorkLocationCd().ifPresent(code -> {
					y.setLocationCode(Optional.of(code));
					lstItemId.add(CancelAppStamp.createItemId(87, data.getDestinationTimeApp().getStampNo(), 7));
				});
				y.getTimeDay().setTimeWithDay(Optional.of(data.getTimeOfDay()));
				y.getTimeDay().getReasonTimeChange().setTimeChangeMeans(TimeChangeMeans.APPLICATION);
			});
			lstItemId.add(CancelAppStamp.createItemId(88, data.getDestinationTimeApp().getStampNo(), 7));
		} else {
			lstItemId.add(CancelAppStamp.createItemId(91, data.getDestinationTimeApp().getStampNo(), 7));

			sheet.setReasonForGoOut(data.getAppStampGoOutAtr().map(x -> GoingOutReason.valueOf(x.value)).orElse(null));
			if(!sheet.getComeBack().isPresent()) {
				sheet.setComeBack(Optional.of(WorkStamp.createDefault()));
			}
			sheet.getComeBack().ifPresent(y -> {
				data.getWorkLocationCd().ifPresent(code -> {
					y.setLocationCode(Optional.of(code));
					lstItemId.add(CancelAppStamp.createItemId(90, data.getDestinationTimeApp().getStampNo(), 7));
				});
				y.getTimeDay().setTimeWithDay(Optional.of(data.getTimeOfDay()));
				y.getTimeDay().getReasonTimeChange().setTimeChangeMeans(TimeChangeMeans.APPLICATION);
			});
		}
		return lstItemId;
	}
}
