package nts.uk.screen.at.app.dailymodify.command.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.app.command.dailyperform.DailyRecordWorkCommand;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordDto;
import nts.uk.ctx.at.record.app.find.monthly.root.MonthlyRecordWorkDto;
import nts.uk.ctx.at.record.dom.editstate.EditStateOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.AttendanceItemUtil;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateSetting;
import nts.uk.screen.at.app.dailymodify.query.DailyModifyQuery;
import nts.uk.screen.at.app.dailymodify.query.DailyModifyResult;
import nts.uk.screen.at.app.dailyperformance.correction.checkdata.dto.ItemFlex;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPItemValue;
import nts.uk.screen.at.app.dailyperformance.correction.dto.ResultReturnDCUpdateData;
import nts.uk.screen.at.app.dailyperformance.correction.dto.month.DPMonthValue;
import nts.uk.screen.at.app.dailyperformance.correction.text.DPText;
import nts.uk.screen.at.app.monthlyperformance.correction.query.MonthlyModifyResult;

public class ProcessCommonCalc {
	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		final Set<Object> seen = new HashSet<>();
		return t -> seen.add(keyExtractor.apply(t));
	}
	
	public static List<Pair<String, GeneralDate>> itemInGroupChange(List<IntegrationOfDaily> domainDailyNew,
			List<DailyModifyResult> resultOlds) {
		List<DailyRecordDto> dtoNews = domainDailyNew.stream().map(x -> DailyRecordDto.from(x))
				.collect(Collectors.toList());
		// 暫定データを登録する - Register provisional data
		List<DailyModifyResult> resultNews = AttendanceItemUtil
				.toItemValues(dtoNews).entrySet().stream().map(dto -> DailyModifyResult.builder().items(dto.getValue())
						.employeeId(dto.getKey().getEmployeeId()).workingDate(dto.getKey().getDate()).completed())
				.collect(Collectors.toList());
		return checkEditedItems(resultOlds, resultNews);

	}
	
	public static List<Pair<String, GeneralDate>> checkEditedItems(List<DailyModifyResult> resultOlds,
			List<DailyModifyResult> resultNews) {
		List<Pair<String, GeneralDate>> editedDate = new ArrayList<>();
		val old = mapTo(resultOlds);
		val news = mapTo(resultNews);
		old.entrySet().forEach(o -> {
			List<ItemValue> niv = getFrom(news, o.getKey());
			if (!CollectionUtil.isEmpty(niv)) {
				if (niv.stream().anyMatch(c -> o.getValue().stream()
						.filter(oi -> c.valueAsObjet() != null && c.equals(oi)).findFirst().isPresent())) {
					editedDate.add(o.getKey());
				}
			}
		});
		return editedDate;
	}
	
	public static Map<Pair<String, GeneralDate>, List<ItemValue>> mapTo(List<DailyModifyResult> source) {
		return source.stream()
				.collect(Collectors.groupingBy(r -> Pair.of(r.getEmployeeId(), r.getDate()),
						Collectors.collectingAndThen(Collectors.toList(),
								list -> list.stream().map(c -> c.getItems()).flatMap(List::stream).distinct()
										.filter(c -> DPText.TMP_DATA_CHECK_ITEMS.contains(c.getItemId()))
										.collect(Collectors.toList()))));
	}
	
	public static List<ItemValue> getFrom(Map<Pair<String, GeneralDate>, List<ItemValue>> source,
			Pair<String, GeneralDate> key) {
		if (source.containsKey(key)) {
			return source.get(key);
		}
		return null;
	}
	
	public static Map<Integer, List<DPItemValue>> convertErrorToType(
			Map<Pair<String, GeneralDate>, ResultReturnDCUpdateData> lstResultReturnDailyError,
			Map<Integer, List<DPItemValue>> resultErrorMonth) {
		Map<Integer, List<DPItemValue>> mapResult = new HashMap<>();
		lstResultReturnDailyError.forEach((key, value) -> {
			value.getResultError().forEach((keyt, valuet) -> {
				val temp = mapResult.get(keyt);
				List<DPItemValue> lstTemp = new ArrayList<>();
				if (temp != null) {
					lstTemp.addAll(temp);
					lstTemp.addAll(valuet);
				} else {
					lstTemp.addAll(valuet);
				}
				mapResult.put(keyt, lstTemp);
			});
		});

		resultErrorMonth.forEach((keyt, valuet) -> {
			val temp = mapResult.get(keyt);
			List<DPItemValue> lstTemp = new ArrayList<>();
			if (temp != null) {
				lstTemp.addAll(temp);
				lstTemp.addAll(valuet);
			} else {
				lstTemp.addAll(valuet);
			}
			mapResult.put(keyt, lstTemp);
		});

		return mapResult;
	}
	
	public static List<DailyRecordWorkCommand> createCommands(String sid, List<DailyRecordDto> lstDto,
			List<DailyModifyQuery> querys) {
		if (querys.isEmpty())
			return lstDto.stream().map(o -> {
				return createCommand(sid, o, null);
			}).collect(Collectors.toList());

		return lstDto.stream().map(o -> {
			DailyModifyQuery query = querys.stream()
					.filter(q -> q.getBaseDate().equals(o.workingDate()) && q.getEmployeeId().equals(o.employeeId()))
					.findFirst().orElse(null);
			return createCommand(sid, o, query);
		}).collect(Collectors.toList());
	}

	public static DailyRecordWorkCommand createCommand(String sid, DailyRecordDto dto, DailyModifyQuery query) {
		if (query == null) {
			return DailyRecordWorkCommand.open().withData(dto).forEmployeeIdAndDate(dto.employeeId(), dto.getDate())
					.fromItems(Collections.emptyList());
		}
		DailyRecordWorkCommand command = DailyRecordWorkCommand.open().forEmployeeId(query.getEmployeeId())
				.withWokingDate(query.getBaseDate()).withData(dto)
				.fromItems(query == null ? Collections.emptyList() : query.getItemValues());
		command.getEditState().updateDatas(convertTo(sid, query));
		return command;
	}
	
	public static List<EditStateOfDailyAttd> convertTo(String sid, DailyModifyQuery query) {
		List<EditStateOfDailyAttd> editData = query.getItemValues().stream().map(x -> {
			return new EditStateOfDailyPerformance(query.getEmployeeId(), x.getItemId(), query.getBaseDate(),
					sid.equals(query.getEmployeeId()) ? EditStateSetting.HAND_CORRECTION_MYSELF
							: EditStateSetting.HAND_CORRECTION_OTHER).getEditState();
		}).collect(Collectors.toList());
		return editData;
	}
	
	public static ItemFlex convertMonthToItem(MonthlyRecordWorkDto monthDto, DPMonthValue monthValue) {
		ItemFlex itemResult = new ItemFlex();
		MonthlyModifyResult result = MonthlyModifyResult.builder()
				.items(AttendanceItemUtil.toItemValues(monthDto, Arrays.asList(18, 21, 189, 190, 191),
						AttendanceItemUtil.AttendanceItemType.MONTHLY_ITEM))
				.employeeId(monthValue.getEmployeeId()).yearMonth(monthValue.getYearMonth())
				.closureId(monthValue.getClosureId()).closureDate(monthValue.getClosureDate())
				.version(monthValue.getVersion()).completed();
		mapValue(result.getItems(), itemResult);
		return itemResult;
	}

	private static void mapValue(List<ItemValue> items, ItemFlex dataCalc) {
		for (ItemValue item : items) {
			setValueMonth(dataCalc, item);
		}
	}

	private static void setValueMonth(ItemFlex dataCalc, ItemValue item) {
		switch (item.getItemId()) {
		case 18:
			dataCalc.setValue18(item);
			break;
		case 21:
			dataCalc.setValue21(item);
			break;
		case 189:
			dataCalc.setValue189(item);
			break;
		case 190:
			dataCalc.setValue190(item);
			break;
		case 191:
			dataCalc.setValue191(item);
			break;
		default:
			break;
		}
	}


}
