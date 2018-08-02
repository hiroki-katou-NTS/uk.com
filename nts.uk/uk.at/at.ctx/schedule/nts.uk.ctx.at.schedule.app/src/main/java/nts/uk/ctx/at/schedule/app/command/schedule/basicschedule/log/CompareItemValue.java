package nts.uk.ctx.at.schedule.app.command.schedule.basicschedule.log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.tuple.Pair;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.app.command.schedule.basicschedule.log.BasicScheduleCorrectionParameter.ScheduleCorrectedItem;
import nts.uk.ctx.at.schedule.app.command.schedule.basicschedule.log.BasicScheduleCorrectionParameter.ScheduleCorrectionTarget;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.DailyAttendanceItemNameAdapter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.DailyAttendanceItemNameAdapterDto;
import nts.uk.shr.com.security.audittrail.correction.content.CorrectionAttr;
import nts.uk.shr.com.security.audittrail.correction.content.DataValueAttribute;

@Stateless
public class CompareItemValue {
	
	@Inject
	private DailyAttendanceItemNameAdapter dailyAttendanceItemNameAdapter;

	public List<ScheduleCorrectionTarget> compare(List<BasicSchedule> listBefore,
			List<BasicSchedule> listAfter) {
		List<ScheduleCorrectionTarget> targets = new ArrayList<>();

		// Get all attendanceItemId from domain BasicSchedule
		List<Integer> attItemIds = this.convertToItemValue(listBefore.get(0))
				.stream().map(item -> item.getItemId()).collect(Collectors.toList());
		
		// Get Name of attendanceItemId
		Map<Integer, String> itemNameMap = dailyAttendanceItemNameAdapter.getDailyAttendanceItemName(attItemIds)
				.stream().collect(Collectors.toMap(DailyAttendanceItemNameAdapterDto::getAttendanceItemId,
						x -> x.getAttendanceItemName()));
		
		targets = mapToScheduleCorrection(convertToItemValue(listAfter),
				convertToItemValue(listBefore), new ArrayList<>(), itemNameMap);

		return targets;
	}
	
	/**
	 * convert to Map ItemValue
	 * @param domains
	 * @return Map <Pair<employeeId, Date>, Map<AttendanceItemId, ItemValue>>
	 */
	private Map<Pair<String, GeneralDate>, Map<Integer, ItemValue>> convertToItemValue(List<BasicSchedule> domains){
		Map<Pair<String, GeneralDate>, Map<Integer, ItemValue>> result = new HashMap<>();
		for (BasicSchedule daily : domains) {
			 List<ItemValue> values = this.convertToItemValue(daily);
			 Map<Integer, ItemValue> map = values.stream().collect(Collectors.toMap(x -> x.getItemId(), x -> x));
			 result.put(Pair.of(daily.getEmployeeId(), daily.getDate()), map);
		}
		return result;
	}
	
	private List<ScheduleCorrectionTarget> mapToScheduleCorrection(
			Map<Pair<String, GeneralDate>, Map<Integer, ItemValue>> itemNewMap,
			Map<Pair<String, GeneralDate>, Map<Integer, ItemValue>> itemOldMap, List<Integer> itemEdit,
			Map<Integer, String> itemNameMap) {
		List<ScheduleCorrectionTarget> targets = new ArrayList<>();
		itemNewMap.forEach((key, value) -> {
			val itemOldValueMap = itemOldMap.get(key);
			val daiTarget = new ScheduleCorrectionTarget(key.getLeft(), key.getRight());
			value.forEach((valueItemKey, valueItemNew) -> {
				val itemOld = itemOldValueMap.get(valueItemKey);
				if (valueItemNew.getValue() != null && itemOld.getValue() != null
						&& !valueItemNew.getValue().equals(itemOld.getValue())) {
					ScheduleCorrectedItem item = new ScheduleCorrectedItem(itemNameMap.get(valueItemKey),
							valueItemNew.getItemId(), itemOld.getValue(), valueItemNew.getValue(),
							// TODO : convert Type ???
							convertType(valueItemNew.getValueType()), null,
							itemEdit.contains(valueItemNew.getItemId())
									? CorrectionAttr.EDIT : CorrectionAttr.CALCULATE);
					daiTarget.getCorrectedItems().add(item);
				}
			});
			targets.add(daiTarget);
		});
		return targets;
	}	
	
	private Integer convertType(ValueType valueType) {
		switch (valueType.value) {

		case 1:
		case 2:
			return DataValueAttribute.TIME.value;

		case 13:
			return DataValueAttribute.MONEY.value;

		default:
			return DataValueAttribute.STRING.value;
		}
	}

	/**
	 * 
	 * @param listBefore
	 * @param listAfter
	 * @return Map<Pair<EmployeeId, date>, List<ScheLogDto>>
	 */
	public Map<Pair<String, GeneralDate>, List<ScheLogDto>> compareValue(List<BasicScheItemValueDto> listBefore,
			List<BasicScheItemValueDto> listAfter) {
		Map<Pair<String, GeneralDate>, List<ScheLogDto>> scheLogDtos = new HashMap<>();
		List<ScheLogDto> logDtos = new ArrayList<>();

		// create Map <Pair<employeeId, date> , List<BasicScheItemValueDto>>
		// List before
		Map<Pair<String, GeneralDate>, List<BasicScheItemValueDto>> mapSidDateBefore = listBefore.stream()
				.collect(Collectors.groupingBy(x -> Pair.of(x.getEmployeeId(), x.getDate())));

		// create Map <Pair<employeeId, date> , List<BasicScheItemValueDto>>
		// List after
		Map<Pair<String, GeneralDate>, List<BasicScheItemValueDto>> mapSidDateAfter = listAfter.stream()
				.collect(Collectors.groupingBy(x -> Pair.of(x.getEmployeeId(), x.getDate())));

		// key : Pair<employeeId, date>
		// value : List<BasicScheItemValueDto>
		mapSidDateAfter.forEach((key, value) -> {
			if (mapSidDateBefore.containsKey(key)) {
				// create Map<attendanceItemId, value> from listAfter
				Map<Integer, String> before = mapSidDateBefore.get(key).stream()
						.collect(Collectors.toMap(x -> x.getAttendanceItemId(), x -> x.getValue()));
				for (BasicScheItemValueDto dto : value) {
					// check value : before and after
					if (!before.get(dto.getAttendanceItemId()).equals(dto.getValue())) {
						ScheLogDto logDto = new ScheLogDto();
						// set data
						logDto.setAfter(dto.getValue());
						logDto.setAttendanceItemId(dto.getAttendanceItemId());
						logDto.setAttendanceItemName(dto.getAttendanceItemName());
						logDto.setAttr(dto.getAttr());
						logDto.setBefore(before.get(dto.getAttendanceItemId()));
						logDto.setValueType(dto.getValueType());

						logDtos.add(logDto);
					}
				}
				if (!logDtos.isEmpty()) {
					scheLogDtos.put(key, logDtos);
				}
			}
		});

		return scheLogDtos;
	}

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
		itemValueWork.value(basicSchedule.getWorkTimeCode() == null ? null : basicSchedule.getWorkTimeCode());
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

		// 育児介護時間 - childCareTime
		ItemValue itemValueChildCareTime = new ItemValue();
		itemValueChildCareTime.itemId(38);
		itemValueChildCareTime.value(basicSchedule.getWorkScheduleTime().isPresent()
				? basicSchedule.getWorkScheduleTime().get().getChildCareTime().valueAsMinutes() : null);
		itemValueChildCareTime.valueType(ValueType.TIME);
		itemValues.add(itemValueChildCareTime);

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
			itemValueChildCareSchedule.valueType(ValueType.TIME_WITH_DAY);
			itemValues.add(itemValueChildCareSchedule);
			
			itemValueChildCareSchedule = new ItemValue();
			itemValueChildCareSchedule.itemId(itemIdChildCareScheduleEnd);
			itemValueChildCareSchedule.value(basicSchedule.getChildCareSchedules().size() > n
					? basicSchedule.getChildCareSchedules().get(n).getChildCareScheduleEnd().v() : null);
			itemValueChildCareSchedule.valueType(ValueType.TIME_WITH_DAY);
			itemValues.add(itemValueChildCareSchedule);
			
			itemValueChildCareSchedule = new ItemValue();
			itemValueChildCareSchedule.itemId(itemIdChildCareScheduleAttr);
			itemValueChildCareSchedule.value(basicSchedule.getChildCareSchedules().size() > n
					? basicSchedule.getChildCareSchedules().get(n).getChildCareAtr().value : null);
			itemValueChildCareSchedule.valueType(ValueType.TIME_WITH_DAY);
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
