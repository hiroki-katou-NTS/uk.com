package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.stamp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.tuple.Pair;

import lombok.val;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.stamp.StartEndClassificationShare;
import nts.uk.ctx.at.shared.dom.application.stamp.TimeStampAppShare;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.GoingOutReason;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.ReasonTimeChange;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkTimeInformation;

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
						x -> x.getOutingFrameNo().v() == data.getDestinationTimeApp().getEngraveFrameNo().intValue())
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

		return lstItemId;
	}

	private static Pair<OutingTimeSheet, List<Integer>> create(TimeStampAppShare data) {

		List<Integer> lstItemId = new ArrayList<>();
		if (data.getDestinationTimeApp().getStartEndClassification() == StartEndClassificationShare.START) {
			lstItemId.addAll(
					Arrays.asList(CancelAppStamp.createItemId(88, data.getDestinationTimeApp().getEngraveFrameNo(), 7),
							CancelAppStamp.createItemId(87, data.getDestinationTimeApp().getEngraveFrameNo(), 7)));
			return Pair.of(
					new OutingTimeSheet(new OutingFrameNo(data.getDestinationTimeApp().getEngraveFrameNo().intValue()),
							Optional.of(new TimeActualStamp(null, new WorkStamp(
									new WorkTimeInformation(new ReasonTimeChange(TimeChangeMeans.APPLICATION, null),
											data.getTimeOfDay()),
									data.getWorkLocationCd()), 0)),
							null, null,
							data.getAppStampGoOutAtr().map(x -> GoingOutReason.valueOf(x.value)).orElse(null),
							Optional.empty()),
					lstItemId);
		} else {
			lstItemId.addAll(
					Arrays.asList(CancelAppStamp.createItemId(91, data.getDestinationTimeApp().getEngraveFrameNo(), 7),
							CancelAppStamp.createItemId(90, data.getDestinationTimeApp().getEngraveFrameNo(), 7)));
			return Pair.of(new OutingTimeSheet(
					new OutingFrameNo(data.getDestinationTimeApp().getEngraveFrameNo().intValue()), Optional.empty(),
					null, null, data.getAppStampGoOutAtr().map(x -> GoingOutReason.valueOf(x.value)).orElse(null),
					Optional.of(new TimeActualStamp(null,
							new WorkStamp(
									new WorkTimeInformation(new ReasonTimeChange(TimeChangeMeans.APPLICATION, null),
											data.getTimeOfDay()),
									data.getWorkLocationCd()),
							0))),
					lstItemId);
		}
	}

	private static List<Integer> updateDomain(OutingTimeSheet sheet, TimeStampAppShare data) {
		List<Integer> lstItemId = new ArrayList<>();
		if (data.getDestinationTimeApp().getStartEndClassification() == StartEndClassificationShare.START) {
			sheet.setReasonForGoOut(data.getAppStampGoOutAtr().map(x -> GoingOutReason.valueOf(x.value)).orElse(null));
			sheet.getGoOut().ifPresent(x -> x.getStamp().ifPresent(y -> {
				data.getWorkLocationCd().ifPresent(code -> {
					y.setLocationCode(Optional.of(code));
					lstItemId.add(CancelAppStamp.createItemId(87, data.getDestinationTimeApp().getEngraveFrameNo(), 7));
				});
				y.getTimeDay().setTimeWithDay(Optional.of(data.getTimeOfDay()));
				y.getTimeDay().getReasonTimeChange().setTimeChangeMeans(TimeChangeMeans.APPLICATION);
			}));
			lstItemId.add(CancelAppStamp.createItemId(88, data.getDestinationTimeApp().getEngraveFrameNo(), 7));
		} else {
			lstItemId.add(CancelAppStamp.createItemId(91, data.getDestinationTimeApp().getEngraveFrameNo(), 7));

			sheet.setReasonForGoOut(data.getAppStampGoOutAtr().map(x -> GoingOutReason.valueOf(x.value)).orElse(null));
			sheet.getComeBack().ifPresent(x -> x.getStamp().ifPresent(y -> {
				data.getWorkLocationCd().ifPresent(code -> {
					y.setLocationCode(Optional.of(code));
					lstItemId.add(CancelAppStamp.createItemId(90, data.getDestinationTimeApp().getEngraveFrameNo(), 7));
				});
				y.getTimeDay().setTimeWithDay(Optional.of(data.getTimeOfDay()));
				y.getTimeDay().getReasonTimeChange().setTimeChangeMeans(TimeChangeMeans.APPLICATION);
			}));
		}
		return lstItemId;
	}
}
