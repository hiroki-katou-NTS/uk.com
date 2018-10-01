package nts.uk.ctx.at.schedule.app.command.schedule.basicschedule.log;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

@Stateless
public class CompareItemValue {

	/**
	 * Get all ItemValue of domain BasicSchedule
	 * @param basicSchedule
	 * @return list ItemValue
	 */
	public List<ItemValue> convertToItemValue(BasicSchedule basicSchedule) {
		List<ItemValue> itemValues = new ArrayList<>();

		// 勤務種類 - workTypeCode
		ItemValue itemValueWork = new ItemValue();
		itemValueWork.itemId(1);
		itemValueWork.value(basicSchedule.getWorkTypeCode());
		itemValueWork.valueType(ValueType.TEXT); 
		itemValues.add(itemValueWork);

		// 就業時間帯 - workTimeCode
		itemValueWork = new ItemValue();
		itemValueWork.itemId(2);
		itemValueWork.value(basicSchedule.getWorkTimeCode());
		itemValueWork.valueType(ValueType.TEXT);
		itemValues.add(itemValueWork);

		// 予定確定区分 - confirmedAtr
		itemValueWork = new ItemValue();
		itemValueWork.itemId(40);
		itemValueWork.value(basicSchedule.getConfirmedAtr().value);
		itemValueWork.valueType(ValueType.NUMBER);
		itemValues.add(itemValueWork);

		// 勤務予定時間帯 - workScheduleTimeZones
		// 予定開始時刻 1
		ItemValue itemValueWorkTimeZone = new ItemValue();
		itemValueWorkTimeZone.itemId(3);
		itemValueWorkTimeZone.value(basicSchedule.getWorkScheduleTimeZones().size() > 0
				? basicSchedule.getWorkScheduleTimeZones().get(0).getScheduleStartClock().v() : null);
		itemValueWorkTimeZone.valueType(ValueType.TIME_WITH_DAY);
		itemValues.add(itemValueWorkTimeZone);

		// 予定終了時刻 1
		itemValueWorkTimeZone = new ItemValue();
		itemValueWorkTimeZone.itemId(4);
		itemValueWorkTimeZone.value(basicSchedule.getWorkScheduleTimeZones().size() > 0
				? basicSchedule.getWorkScheduleTimeZones().get(0).getScheduleEndClock().v() : null);
		itemValueWorkTimeZone.valueType(ValueType.TIME_WITH_DAY);
		itemValues.add(itemValueWorkTimeZone);

		// 直行直帰区分 1
		itemValueWorkTimeZone = new ItemValue();
		itemValueWorkTimeZone.itemId(41);
		itemValueWorkTimeZone.value(basicSchedule.getWorkScheduleTimeZones().size() > 0
				? basicSchedule.getWorkScheduleTimeZones().get(0).getBounceAtr().value : null);
		itemValueWorkTimeZone.valueType(ValueType.ATTR);
		itemValues.add(itemValueWorkTimeZone);

		// 予定開始時刻 2
		ItemValue itemValueWorkTimeZone2 = new ItemValue();
		itemValueWorkTimeZone2.itemId(5);
		itemValueWorkTimeZone2.value(basicSchedule.getWorkScheduleTimeZones().size() > 1
				? basicSchedule.getWorkScheduleTimeZones().get(1).getScheduleStartClock().v() : null);
		itemValueWorkTimeZone2.valueType(ValueType.TIME_WITH_DAY);
		itemValues.add(itemValueWorkTimeZone2);

		// 予定終了時刻 2
		itemValueWorkTimeZone2 = new ItemValue();
		itemValueWorkTimeZone2.itemId(6);
		itemValueWorkTimeZone2.value(basicSchedule.getWorkScheduleTimeZones().size() > 1
				? basicSchedule.getWorkScheduleTimeZones().get(1).getScheduleEndClock().v() : null);
		itemValueWorkTimeZone2.valueType(ValueType.TIME_WITH_DAY);
		itemValues.add(itemValueWorkTimeZone2);

		// 直行直帰区分 2
		itemValueWorkTimeZone2 = new ItemValue();
		itemValueWorkTimeZone2.itemId(42);
		itemValueWorkTimeZone2.value(basicSchedule.getWorkScheduleTimeZones().size() > 1
				? basicSchedule.getWorkScheduleTimeZones().get(1).getBounceAtr().value : null);
		itemValueWorkTimeZone2.valueType(ValueType.ATTR);
		itemValues.add(itemValueWorkTimeZone2);

		// 勤務予定休憩 - workScheduleBreaks
		int itemIdWorkScheduleBreakStart = 7;
		int itemIdWorkScheduleBreakEnd = 8;
		for (int i = 0; i < 10; i++) {
			// start
			ItemValue itemValueWorkScheduleBreakStart = new ItemValue();
			itemValueWorkScheduleBreakStart.itemId(itemIdWorkScheduleBreakStart);
			itemValueWorkScheduleBreakStart.value(basicSchedule.getWorkScheduleBreaks().size() > i
					? basicSchedule.getWorkScheduleBreaks().get(i).getScheduledStartClock().v() : null);
			itemValueWorkScheduleBreakStart.valueType(ValueType.TIME_WITH_DAY);
			itemValues.add(itemValueWorkScheduleBreakStart);

			// end
			ItemValue itemValueWorkScheduleBreakEnd = new ItemValue();
			itemValueWorkScheduleBreakEnd.itemId(itemIdWorkScheduleBreakEnd);
			itemValueWorkScheduleBreakEnd.value(basicSchedule.getWorkScheduleBreaks().size() > i
					? basicSchedule.getWorkScheduleBreaks().get(i).getScheduledEndClock().v() : null);
			itemValueWorkScheduleBreakEnd.valueType(ValueType.TIME_WITH_DAY);
			itemValues.add(itemValueWorkScheduleBreakEnd);

			itemIdWorkScheduleBreakStart = itemIdWorkScheduleBreakStart + 2;
			itemIdWorkScheduleBreakEnd = itemIdWorkScheduleBreakEnd + 2;
		}

		// 勤務予定時間 - workScheduleTime
		// 人件費時間 - personFeeTime
		int itemIdWorkScheduleTime = 43;
		for (int j = 0; j < 10; j++) {
			ItemValue itemValuePersonFeeTime = new ItemValue();
			itemValuePersonFeeTime.itemId(itemIdWorkScheduleTime);
			itemValuePersonFeeTime.value(basicSchedule.getWorkScheduleTime().isPresent()
					&& basicSchedule.getWorkScheduleTime().get().getPersonFeeTime().size() > j
							? basicSchedule.getWorkScheduleTime().get().getPersonFeeTime().get(j).getPersonFeeTime()
									.valueAsMinutes()
							: null);
			itemValuePersonFeeTime.valueType(ValueType.TIME);
			itemValues.add(itemValuePersonFeeTime);
			itemIdWorkScheduleTime++;
		}
		// 休憩時間 - breakTime
		ItemValue itemValueBreakTime = new ItemValue();
		itemValueBreakTime.itemId(36);
		itemValueBreakTime.value(basicSchedule.getWorkScheduleTime().isPresent()
				? basicSchedule.getWorkScheduleTime().get().getBreakTime().valueAsMinutes() : null);
		itemValueBreakTime.valueType(ValueType.TIME);
		itemValues.add(itemValueBreakTime);

		// 実働時間 - workingTime
		ItemValue itemValueWorkingTime = new ItemValue();
		itemValueWorkingTime.itemId(34);
		itemValueWorkingTime.value(basicSchedule.getWorkScheduleTime().isPresent()
				? basicSchedule.getWorkScheduleTime().get().getWorkingTime().valueAsMinutes() : null);
		itemValueWorkingTime.valueType(ValueType.TIME);
		itemValues.add(itemValueWorkingTime);

		// 平日時間 - weekdayTime
		ItemValue itemValueWeekdayTime = new ItemValue();
		itemValueWeekdayTime.itemId(37);
		itemValueWeekdayTime.value(basicSchedule.getWorkScheduleTime().isPresent()
				? basicSchedule.getWorkScheduleTime().get().getWeekdayTime().valueAsMinutes() : null);
		itemValueWeekdayTime.valueType(ValueType.TIME);
		itemValues.add(itemValueWeekdayTime);

		// 所定時間 - predetermineTime
		ItemValue itemValuePredetermineTime = new ItemValue();
		itemValuePredetermineTime.itemId(35);
		itemValuePredetermineTime.value(basicSchedule.getWorkScheduleTime().isPresent()
				? basicSchedule.getWorkScheduleTime().get().getPredetermineTime().valueAsMinutes() : null);
		itemValuePredetermineTime.valueType(ValueType.TIME);
		itemValues.add(itemValuePredetermineTime);

		// 総労働時間 - totalLaborTime
		ItemValue itemValueTotalLaborTime = new ItemValue();
		itemValueTotalLaborTime.itemId(33);
		itemValueTotalLaborTime.value(basicSchedule.getWorkScheduleTime().isPresent()
				? basicSchedule.getWorkScheduleTime().get().getTotalLaborTime().valueAsMinutes() : null);
		itemValueTotalLaborTime.valueType(ValueType.TIME);
		itemValues.add(itemValueTotalLaborTime);

		// フレックス超過時間 - flexTime
		ItemValue itemValueFlexTime = new ItemValue();
		itemValueFlexTime.itemId(39);
		itemValueFlexTime.value(basicSchedule.getWorkScheduleTime().isPresent()
				? basicSchedule.getWorkScheduleTime().get().getFlexTime().valueAsMinutes() : null);
		itemValueFlexTime.valueType(ValueType.TIME);
		itemValues.add(itemValueFlexTime);
		
		// 育児時間 - childTime
		ItemValue itemValueChildTime = new ItemValue();
		itemValueChildTime.itemId(102);
		itemValueChildTime.value(basicSchedule.getWorkScheduleTime().isPresent()
				? basicSchedule.getWorkScheduleTime().get().getChildTime().valueAsMinutes() : null);
		itemValueChildTime.valueType(ValueType.TIME);
		itemValues.add(itemValueChildTime);
		
		// 介護時間 - careTime
		ItemValue itemValueCareTime = new ItemValue();
		itemValueCareTime.itemId(103);
		itemValueCareTime.value(basicSchedule.getWorkScheduleTime().isPresent()
				? basicSchedule.getWorkScheduleTime().get().getCareTime().valueAsMinutes() : null);
		itemValueCareTime.valueType(ValueType.TIME);
		itemValues.add(itemValueCareTime);

		// 勤務予定人件費 - workSchedulePersonFees
		int itemIdWorkSchedulePersonFee = 53;
		for (int j = 0; j < 10; j++) {
			ItemValue itemValuePersonFeeTime = new ItemValue();
			itemValuePersonFeeTime.itemId(itemIdWorkSchedulePersonFee);
			itemValuePersonFeeTime.value(basicSchedule.getWorkSchedulePersonFees().size() > j
					? basicSchedule.getWorkSchedulePersonFees().get(j).getPersonalFeeAmount().v() : null);
			itemValuePersonFeeTime.valueType(ValueType.AMOUNT);
			itemValues.add(itemValuePersonFeeTime);
			itemIdWorkSchedulePersonFee++;
		}

		// 勤務予定育児介護時間帯 - childCareSchedules
		int itemIdChildCareScheduleStart = 28;
		int itemIdChildCareScheduleEnd = 29;
		int itemIdChildCareScheduleAttr = 27;
		for (int n = 0; n < 2; n++) {
			ItemValue itemValueChildCareSchedule = new ItemValue();
			itemValueChildCareSchedule.itemId(itemIdChildCareScheduleStart);
			itemValueChildCareSchedule.value(basicSchedule.getChildCareSchedules().size() > n
					? basicSchedule.getChildCareSchedules().get(n).getChildCareScheduleStart().v() : null);
			itemValueChildCareSchedule.valueType(ValueType.TIME);
			itemValues.add(itemValueChildCareSchedule);
			
			itemValueChildCareSchedule = new ItemValue();
			itemValueChildCareSchedule.itemId(itemIdChildCareScheduleEnd);
			itemValueChildCareSchedule.value(basicSchedule.getChildCareSchedules().size() > n
					? basicSchedule.getChildCareSchedules().get(n).getChildCareScheduleEnd().v() : null);
			itemValueChildCareSchedule.valueType(ValueType.TIME);
			itemValues.add(itemValueChildCareSchedule);
			
			itemValueChildCareSchedule = new ItemValue();
			itemValueChildCareSchedule.itemId(itemIdChildCareScheduleAttr);
			itemValueChildCareSchedule.value(basicSchedule.getChildCareSchedules().size() > n
					? basicSchedule.getChildCareSchedules().get(n).getChildCareAtr().value : null);
			itemValueChildCareSchedule.valueType(ValueType.ATTR);
			itemValues.add(itemValueChildCareSchedule);			
			
			itemIdChildCareScheduleStart = itemIdChildCareScheduleStart + 3;
			itemIdChildCareScheduleEnd = itemIdChildCareScheduleEnd + 3;
			itemIdChildCareScheduleAttr = itemIdChildCareScheduleAttr + 3;
		}

		// 勤務予定マスタ情報 - workScheduleMaster
		// 雇用コード
		ItemValue itemValueEmploymentCD = new ItemValue();
		itemValueEmploymentCD.itemId(63);
		itemValueEmploymentCD.value(basicSchedule.getWorkScheduleMaster().getEmploymentCd());
		itemValueEmploymentCD.valueType(ValueType.CODE);
		itemValues.add(itemValueEmploymentCD);
		
		// 分類コード
		ItemValue itemValueClassificationCD = new ItemValue();
		itemValueClassificationCD.itemId(64);
		itemValueClassificationCD.value(basicSchedule.getWorkScheduleMaster().getClassificationCd());
		itemValueClassificationCD.valueType(ValueType.CODE);
		itemValues.add(itemValueClassificationCD);
		
		// 職位ID
		ItemValue itemValueJobId = new ItemValue();
		itemValueJobId.itemId(66);
		itemValueJobId.value(basicSchedule.getWorkScheduleMaster().getJobId());
		itemValueJobId.valueType(ValueType.TEXT);
		itemValues.add(itemValueJobId);
		
		// 職位ID
		ItemValue itemValueWorkPlaceId = new ItemValue();
		itemValueWorkPlaceId.itemId(65);
		itemValueWorkPlaceId.value(basicSchedule.getWorkScheduleMaster().getWorkplaceId());
		itemValueWorkPlaceId.valueType(ValueType.TEXT);
		itemValues.add(itemValueWorkPlaceId);
		
		return itemValues;
	}
}
