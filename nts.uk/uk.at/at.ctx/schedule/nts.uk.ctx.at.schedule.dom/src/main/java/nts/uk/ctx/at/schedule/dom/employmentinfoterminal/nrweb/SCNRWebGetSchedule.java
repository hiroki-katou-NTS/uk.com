package nts.uk.ctx.at.schedule.dom.employmentinfoterminal.nrweb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.tuple.Pair;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.adapter.dailymonthlyprocessing.master.AllMasterAttItemImport;
import nts.uk.ctx.at.schedule.dom.adapter.dailymonthlyprocessing.master.AllMasterAttItemImport.MasterAttItemDetailmport;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemName;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.TypeOfItemImport;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.DailyAttendanceAtr;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.security.audittrail.correction.content.DataValueAttribute;

/**
 * @author thanh_nx
 *
 *         勤怠項目と社員IDと期間から勤務予定の値を取得
 */
public class SCNRWebGetSchedule {

	static final Integer[] DEVIATION_REASON = { 436, 438, 439, 441, 443, 444, 446, 448, 449, 451, 453, 454, 456, 458,
			459, 799, 801, 802, 804, 806, 807, 809, 811, 812, 814, 816, 817, 819, 821, 822 };

	public static final Map<Integer, Integer> DEVIATION_REASON_MAP = IntStream.range(0, DEVIATION_REASON.length - 1)
			.boxed().collect(Collectors.toMap(x -> DEVIATION_REASON[x], x -> x / 3 + 1));

	private SCNRWebGetSchedule() {
	};

	public static List<SCNRWebScheduleData> getSchedule(Require require, String cid, String employeeId,
			DatePeriod period, List<Integer> attendanceId) {

		// 社員IDと期間から「勤務予定」を取得
		List<WorkSchedule> lstSchedule = require.get(employeeId, period);

		List<IntegrationOfDaily> lstDomainDaily = lstSchedule.stream().map(workSchedule -> {
			return workSchedule.convertToIntegrationOfDaily();
		}).collect(Collectors.toList());

		// DailyRecordToAttendanceItemConverterにセットする
		Map<Pair<String, GeneralDate>, List<ItemValue>> mapDataSchedule = new HashMap<>();
		DailyRecordToAttendanceItemConverter converter = require.createDailyConverter();
		lstDomainDaily.stream().forEach(domainDaily -> {
			converter.setData(domainDaily).employeeId(domainDaily.getEmployeeId()).workingDate(domainDaily.getYmd());
			mapDataSchedule.put(Pair.of(domainDaily.getEmployeeId(), domainDaily.getYmd()),
					converter.convert(attendanceId));
		});

		// 勤怠項目に対応する名称を生成する
		List<AttItemName> lstName = require.getNameOfAttendanceItem(cid, attendanceId, TypeOfItemImport.Daily);

		// 勤怠項目のマスタの取得
		List<AllMasterAttItemImport> allMasterName = require.getAllMaster(cid, attendanceId, period.end());

		// 日次の勤怠項を取得
		List<DailyAttendanceItem> dailyMasterType = require.getListById(cid, attendanceId);

		Map<Pair<String, GeneralDate>, IntegrationOfDaily> mapDomainDaily = lstDomainDaily.stream()
				.collect(Collectors.toMap(x -> Pair.of(x.getEmployeeId(), x.getYmd()), x -> x));
		Map<Integer, AttItemName> mapName = lstName.stream()
				.collect(Collectors.toMap(x -> x.getAttendanceItemId(), x -> x));

		// 勤怠項目IDと値を作る
		List<SCNRWebScheduleData> lstResultData = mapDataSchedule.entrySet().stream().map(x -> {
			IntegrationOfDaily daily = mapDomainDaily.get(x.getKey());
			// daily.getEditState().stream().filter(x -> x.get)
			List<SCAttendanceItemAndValue> value = x.getValue().stream().map(itemDetail -> {

				Optional<EditStateOfDailyAttd> stateOpt = daily.getEditState().stream()
						.filter(y -> y.getAttendanceItemId() == itemDetail.itemId()).findFirst();

				AttItemName itemName = mapName.get(itemDetail.itemId());
				val masterType = dailyMasterType.stream().filter(t -> t.getAttendanceItemId() == itemDetail.getItemId())
						.findFirst();

				return new SCAttendanceItemAndValue(itemName.getAttendanceItemDisplayNumber(), itemDetail.getItemId(), // id
						itemName.getAttendanceItemName(), // name
						getName(allMasterName, masterType, itemDetail.getValue(), itemDetail.getItemId()), // value
						stateOpt.map(s -> s.getEditStateSetting()).orElse(EditStateSetting.IMPRINT));
			}).collect(Collectors.toList());
			return new SCNRWebScheduleData(x.getKey().getLeft(), x.getKey().getRight(), value);
		}).collect(Collectors.toList());

		return lstResultData;
	}

	private static String getName(List<AllMasterAttItemImport> allMasterName, Optional<DailyAttendanceItem> type,
			String codeOrId, int itemId) {

		if (!type.isPresent())
			return codeOrId;
		
		if (codeOrId == null || codeOrId.isEmpty())
			return "";

		if (type.isPresent() && !type.get().getMasterType().isPresent())
			return createValueFormat(codeOrId, type.get().getDailyAttendanceAtr());

		List<MasterAttItemDetailmport> lstDetail = allMasterName.stream()
				.filter(x -> x.getType().value == type.get().getMasterType().get().value)
				.flatMap(x -> x.getLstDetail().stream()).collect(Collectors.toList());

		switch (type.get().getMasterType().get()) {

		case REASON_DIVERGENCE:
			int group = DEVIATION_REASON_MAP.get(itemId);
			return lstDetail.stream()
					.filter(x -> x.getId().equals(String.valueOf(group)) && x.getCode().equals(codeOrId)).findFirst()
					.map(x -> x.getName()).orElse(TextResource.localize("KDW003_81"));

		case WORK_PLACE:
		case POSITION:
			return lstDetail.stream().filter(x -> x.getId().equals(codeOrId)).findFirst().map(x -> x.getName())
					.orElse(TextResource.localize("KDW003_81"));

		default:
			return lstDetail.stream().filter(x -> x.getCode().equals(codeOrId)).findFirst().map(x -> x.getName())
					.orElse(TextResource.localize("KDW003_81"));
		}
	}

	private static String createValueFormat(String value, DailyAttendanceAtr dailyAttendanceAtr) {
		if(value == null || value.equals("null")) 
			return "";
		switch (dailyAttendanceAtr) {
		case Code:
		case ReferToMaster:
		case NumberOfTime:
		case Classification:
		case Charater:
			return DataValueAttribute.STRING.format(value);
		case AmountOfMoney:
			return DataValueAttribute.MONEY.format(Double.valueOf(value));
		case Time:
			return DataValueAttribute.TIME.format(Integer.parseInt(value));
		case TimeOfDay:
			return DataValueAttribute.CLOCK.format(Integer.parseInt(value));
		default:
			break;
		}
		return value;
	}

	public static interface Require {

		// WorkScheduleRepository.getList
		public List<WorkSchedule> get(String employeeID, DatePeriod period);

		// ConvertDailyRecordToAd
		public DailyRecordToAttendanceItemConverter createDailyConverter();

		// AtItemNameAdapter
		public List<AttItemName> getNameOfAttendanceItem(String cid, List<Integer> attendanceItemIds,
				TypeOfItemImport type);

		// GetMasterAttendanceItemAdapter
		public List<AllMasterAttItemImport> getAllMaster(String companyId, List<Integer> lstAtt, GeneralDate baseDate);

		// DailyAttendanceItemRepository
		public List<DailyAttendanceItem> getListById(String companyId, List<Integer> dailyAttendanceItemIds);

	}
}
