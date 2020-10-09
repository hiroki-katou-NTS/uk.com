package nts.uk.ctx.at.record.app.command.dailyperform.checkdata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.tuple.Pair;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.app.command.dailyperform.DailyRecordWorkCommand;
import nts.uk.ctx.at.record.app.command.divergence.time.algorithm.DetermineLeakage;
//import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordWorkFinder;
import nts.uk.ctx.at.record.dom.daily.itemvalue.DailyItemValue;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeRepository;
import nts.uk.ctx.at.record.dom.divergence.time.JudgmentResult;
import nts.uk.ctx.at.record.dom.divergence.time.reason.DivergenceReason;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerErrorRepository;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.deviationtime.DiverdenceReasonCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeRoot;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.arc.time.calendar.period.DatePeriod;

@Stateless
public class CheckPairDeviationReason {

//	@Inject
//	private DailyRecordWorkFinder fullFinder;

	@Inject
	private DivergenceTimeRepository divergenceTimeRepository;

	@Inject
	private DetermineLeakage determineLeakage;

	@Inject
	private EmployeeDailyPerErrorRepository employeeDailyPerErrorRepository;

	private static final Integer[] DEVIATION_REASON = { 436, 438, 439, 441, 443, 444, 446, 448, 449, 451, 453, 454, 456,
			458, 459, 799, 801, 802, 804, 806, 807, 809, 811, 812, 814, 816, 817, 819, 821, 822 };
	static final Map<Integer, Integer> DEVIATION_REASON_MAP = IntStream.range(0, DEVIATION_REASON.length - 1).boxed()
			.collect(Collectors.toMap(x -> DEVIATION_REASON[x], x -> x / 3 + 1));
	private static String ERROR = "D0";
	private static Map<String, Integer> ERROR_NO_MAP = IntStream.range(0, 19).boxed()
			.collect(Collectors.toMap(x -> ERROR + String.format("%03d", (x + 1)), x -> x / 2 + 1));

	// 乖離理由が選択、入力されているかチェックする
	public List<DPItemValueRC> checkInputDeviationReason(List<DailyRecordWorkCommand> commands, List<DailyItemValue> itemValues) {
		String textResource = TextResource.localize("Msg_1298");
		List<DPItemValueRC> resultErrorAll = new ArrayList<>();
		String companyId = AppContexts.user().companyId();
		List<String> employeeIds = commands.stream().map(x -> x.getEmployeeId()).collect(Collectors.toList());
		List<GeneralDate> dates = commands.stream().map(x -> x.getWorkDate()).sorted((x, y) -> x.compareTo(y))
				.collect(Collectors.toList());
		if (employeeIds.isEmpty())
			return Collections.emptyList();
		List<EmployeeDailyPerError> employeeError = employeeDailyPerErrorRepository.finds(employeeIds,
				new DatePeriod(dates.get(0), dates.get(dates.size() - 1)));
		// if(!employeeError.isEmpty()) return Collections.emptyList();

		List<DivergenceTimeRoot> divergenceTime = divergenceTimeRepository.getDivTimeListByUseSet(companyId);
		Map<Pair<String, GeneralDate>, List<ItemValue>> mapValueOld = itemValues.stream().collect(Collectors.toMap(x -> Pair.of(x.getEmployeeId(), x.getDate()), x -> x.getItems()));
		for (DailyRecordWorkCommand command : commands) {
			resultErrorAll.addAll(checkReasonInput(command, employeeError, divergenceTime, textResource, mapValueOld));
		}
		return resultErrorAll;
	}

	public List<DPItemValueRC> checkReasonInput(DailyRecordWorkCommand command,
			List<EmployeeDailyPerError> employeeError, List<DivergenceTimeRoot> divergenceTime, String textResource, Map<Pair<String, GeneralDate>, List<ItemValue>> mapValueOld) {
		Map<Integer, String> itemCommonMap = new HashMap<>();
		List<EmployeeDailyPerError> errorRow = employeeError.stream()
				.filter(x -> x.getEmployeeID().equals(command.getEmployeeId())
						&& x.getDate().equals(command.getWorkDate())
						&& ERROR_NO_MAP.containsKey(x.getErrorAlarmWorkRecordCode().v()))
				.collect(Collectors.toList());
		List<ItemValue> items = command.itemValues();
		List<DPItemValueRC> resultError = new ArrayList<>();
		if (CollectionUtil.isEmpty(items))
			return resultError;
		String sid = command.getEmployeeId();
		GeneralDate date = command.getWorkDate();

		// 乖離時間をチェックする

		Map<Integer, DivergenceTimeRoot> divergenceTimeNoMap = divergenceTime.stream()
				.collect(Collectors.toMap(x -> x.getDivergenceTimeNo(), x -> x));
		if (divergenceTimeNoMap.isEmpty())
			return resultError;

		Map<Integer, String> itemReasonUiMap = items.stream()
				.filter(x -> DEVIATION_REASON_MAP.containsKey(x.getItemId())
						&& divergenceTimeNoMap.containsKey(DEVIATION_REASON_MAP.get(x.getItemId())))
				.collect(Collectors.toMap(x -> x.getItemId(), x -> x.getValue()));
		itemCommonMap.putAll(itemReasonUiMap);

		List<Integer> nos = IntStream.range(0, DEVIATION_REASON.length).boxed()
				.filter(x -> itemReasonUiMap.containsKey(DEVIATION_REASON[x]))
				.map(x -> DEVIATION_REASON_MAP.get(DEVIATION_REASON[x])).collect(Collectors.toList());

		List<Integer> itemReasonServer = DEVIATION_REASON_MAP.entrySet().stream()
				.filter(x -> !itemReasonUiMap.containsKey(x.getKey()) && nos.contains(x.getValue()))
				.map(x -> x.getKey()).collect(Collectors.toList());
		// get data from server
		if (!itemReasonServer.isEmpty()) {
			List<ItemValue> itemValues = mapValueOld.get(Pair.of(sid, command.getWorkDate())).stream().filter(x -> itemReasonServer.contains(x.getItemId())).collect(Collectors.toList());
//					this.fullFinder
//					.find(Arrays.asList(sid), new DatePeriod(command.getWorkDate(), command.getWorkDate())).stream()
//					.map(c -> DailyModifyRCResult.builder().items(AttendanceItemUtil.toItemValues(c, itemReasonServer))
//							.workingDate(c.workingDate()).employeeId(c.employeeId()).completed())
//					.collect(Collectors.toList());
			// merge itemServer and itemUI
			if (!itemValues.isEmpty()) {
				itemValues.forEach(x -> {
					itemCommonMap.put(x.getItemId(), x.getValue());
				});
			}

		}

		Map<Integer, Map<Integer, String>> groupNoAll = new HashMap<>();
		for (Map.Entry<Integer, String> m : itemCommonMap.entrySet()) {
			Integer key = m.getKey();
			String value = m.getValue();
			Map<Integer, String> group = new HashMap<>();
			if (groupNoAll.containsKey(DEVIATION_REASON_MAP.get(key))) {
				group = groupNoAll.get(DEVIATION_REASON_MAP.get(key));
				group.put(key, value);
				groupNoAll.put(DEVIATION_REASON_MAP.get(key), group);
			} else {
				group.put(key, value);
				groupNoAll.put(DEVIATION_REASON_MAP.get(key), group);
			}
		}

		// check
		groupNoAll.forEach((key, value) -> {
			if (value != null && !value.isEmpty()) {
				List<Integer> keyValueSort = value.keySet().stream().sorted((x, y) -> x - y)
						.collect(Collectors.toList());
				int keyCode = keyValueSort.get(1);
				int keyReason = keyValueSort.get(2);
				DiverdenceReasonCode dCode = new DiverdenceReasonCode(value.get(keyCode));
				DivergenceReason dReason = new DivergenceReason(value.get(keyReason));

				List<String> errorCodes = errorRow.stream().filter(x -> ERROR_NO_MAP.get(x) == key)
						.map(x -> x.getErrorAlarmWorkRecordCode().v()).collect(Collectors.toList());
				if (errorCodes.isEmpty()) {
					JudgmentResult result = determineLeakage.checkInputMissing(sid, date, key, dCode, dReason,
							errorCodes);
					if (result != null && result.value == JudgmentResult.ERROR.value) {
						if (itemReasonUiMap.containsKey(keyCode)) {
							resultError.add(new DPItemValueRC("", sid, date, keyCode, "乖離時間" + key, textResource));
						}
						if (itemReasonUiMap.containsKey(keyReason)) {
							resultError.add(new DPItemValueRC("", sid, date, keyReason, "乖離時間" + key, textResource));
						}
					}
				}
			}
		});

		return resultError;
	}
}
