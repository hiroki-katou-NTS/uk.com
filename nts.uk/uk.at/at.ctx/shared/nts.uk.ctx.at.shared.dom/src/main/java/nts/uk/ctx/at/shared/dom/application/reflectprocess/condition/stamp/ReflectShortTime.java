package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.stamp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.DailyRecordOfApplication;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.UpdateEditSttCreateBeforeAppReflect;
import nts.uk.ctx.at.shared.dom.application.stamp.TimeStampAppOtherShare;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ChildCareAttribute;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortWorkTimFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortWorkingTimeSheet;

/**
 * @author thanh_nx
 *
 *         短時間勤務時間帯の反映
 */
public class ReflectShortTime {

	public static List<Integer> reflect(DailyRecordOfApplication dailyApp,
			List<TimeStampAppOtherShare> listTimeStampAppOther) {

		List<Integer> lstItemId = new ArrayList<>();

		// input. 短時間勤務時間帯（List）でループする
		listTimeStampAppOther.stream().forEach(data -> {

			if (dailyApp.getShortTime().isPresent()) {
				Optional<ShortWorkingTimeSheet> shortTimeOpt = dailyApp
						.getShortTime().get().getShortWorkingTimeSheets().stream().filter(x -> x
								.getShortWorkTimeFrameNo().v() == data.getDestinationTimeZoneApp().getEngraveFrameNo())
						.findFirst();
				if (shortTimeOpt.isPresent()) {
					//
					shortTimeOpt.get().setStartTime(data.getTimeZone().getStartTime());
					shortTimeOpt.get().setEndTime(data.getTimeZone().getEndTime());
					shortTimeOpt.get()
							.setChildCareAttr(EnumAdaptor.valueOf(
									data.getDestinationTimeZoneApp().getTimeZoneStampClassification().value,
									ChildCareAttribute.class));
					lstItemId.addAll(createItemId(shortTimeOpt.get()));
				} else {
					// 該当の打刻枠NOをキーに[短時間勤務時間帯]を作成する
					ShortWorkingTimeSheet shortTimeCreate = createShortTime(data);
					dailyApp.getShortTime().get().getShortWorkingTimeSheets().add(createShortTime(data));
					lstItemId.addAll(createItemId(shortTimeCreate));
				}
			} else {
				List<ShortWorkingTimeSheet> sheet = new ArrayList<>();
				ShortWorkingTimeSheet shortTimeCreate = createShortTime(data);
				sheet.add(shortTimeCreate);
				dailyApp.setShortTime(Optional.of(new ShortTimeOfDailyAttd(sheet)));
				lstItemId.addAll(createItemId(shortTimeCreate));
			}
		});

		//申請反映状態にする
		UpdateEditSttCreateBeforeAppReflect.update(dailyApp, lstItemId);
		return lstItemId;
	}

	private static ShortWorkingTimeSheet createShortTime(TimeStampAppOtherShare data) {

		return new ShortWorkingTimeSheet(new ShortWorkTimFrameNo(data.getDestinationTimeZoneApp().getEngraveFrameNo()),
				EnumAdaptor.valueOf(data.getDestinationTimeZoneApp().getTimeZoneStampClassification().value,
						ChildCareAttribute.class),
				data.getTimeZone().getStartTime(), data.getTimeZone().getEndTime());

	}

	private static List<Integer> createItemId(ShortWorkingTimeSheet data) {
		List<Integer> lstItemId = new ArrayList<>();
		if (data.getChildCareAttr() == ChildCareAttribute.CARE) {
			lstItemId.addAll(Arrays.asList(CancelAppStamp.createItemId(759, data.getShortWorkTimeFrameNo().v(), 2),
					CancelAppStamp.createItemId(760, data.getShortWorkTimeFrameNo().v(), 2)));
		} else {
			lstItemId.addAll(Arrays.asList(CancelAppStamp.createItemId(763, data.getShortWorkTimeFrameNo().v(), 2),
					CancelAppStamp.createItemId(764, data.getShortWorkTimeFrameNo().v(), 2)));
		}
		return lstItemId;
	}
}
