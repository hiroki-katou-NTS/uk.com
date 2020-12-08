package nts.uk.ctx.at.record.dom.employmentinfoterminal.nrweb.month;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrweb.master.GetMasterMonthProcess.AllMasterAttItemMonth;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrweb.month.algorithm.MonthlyRecordValues;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItem;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItemAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemName;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.TypeOfItemImport;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.PrimitiveValueOfAttendanceItem;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.security.audittrail.correction.content.DataValueAttribute;

/**
 * @author thanh_nx
 *
 *
 *         勤怠項目と社員IDと年月から月別実績の値を取得
 */
public class RCNRWebGetMonth {

	public static Optional<RCNRWebMonthData> getDataMonthData(Require require, String cid, String employeeId,
			List<Integer> attendanceId, YearMonth ym) {

		// [No.495]勤怠項目IDを指定して月別実績の値を取得（複数レコードは合算）
		Map<String, List<MonthlyRecordValues>> mapRecordValues = require.getRecordValues(Arrays.asList(employeeId),
				new YearMonthPeriod(ym, ym), attendanceId);

		// 勤怠項目に対応する名称を生成する
		List<AttItemName> attName = require.getNameOfAttendanceItem(cid, attendanceId, TypeOfItemImport.Monthly);

		// 月次の勤怠項目を取得取得
		List<MonthlyAttendanceItem> allMaster = require.findByAttendanceItemId(cid, attendanceId);

		// 勤怠項目のマスタの取得
		List<AllMasterAttItemMonth> allMasterCodeName = require.getMasterCodeName(cid, GeneralDate.today(),
				allMaster.stream().filter(x -> x.getPrimitiveValue().isPresent()).map(x -> x.getPrimitiveValue().get())
						.distinct().collect(Collectors.toList()));

		// 勤怠項目IDと値を作る
		Map<Integer, AttItemName> mapName = attName.stream()
				.collect(Collectors.toMap(x -> x.getAttendanceItemId(), x -> x));
		if (mapRecordValues.containsKey(employeeId) && !mapRecordValues.get(employeeId).isEmpty()) {

			mapRecordValues.get(employeeId).get(0).getItemValues().stream().map(x -> {
				Optional<MonthlyAttendanceItem> attendanceItem = allMaster.stream()
						.filter(y -> y.getAttendanceItemId() == x.getItemId()).findFirst();

				AttItemName itemName = mapName.get(x.getItemId());
				return new RCAttendanceItemValueMonth(itemName.getAttendanceItemDisplayNumber(),
						itemName.getAttendanceItemId(), itemName.getAttendanceItemName(),
						createValue(attendanceItem, allMasterCodeName, x.getValue()));
			}).collect(Collectors.toList());

		}
		return Optional.empty();
	}

	// 値はデータタイプに従ってフォーマットされます
	private static String createValue(Optional<MonthlyAttendanceItem> attendanceItem,
			List<AllMasterAttItemMonth> allMasterCodeName, String value) {

		if (value == null || value.isEmpty()) {
			return "";
		}
		
		if (!attendanceItem.isPresent()) {
			return value;
		} else if (attendanceItem.isPresent() && attendanceItem.get().getPrimitiveValue().isPresent()) {
			String valueFormat = createValueFormat(value, attendanceItem.get().getMonthlyAttendanceAtr());

			return getNameFromCode(attendanceItem.get().getPrimitiveValue().get(), allMasterCodeName, valueFormat);
		}

		return value;
	}

	private static String createValueFormat(String value, MonthlyAttendanceItemAtr attendanceAtr) {
		if(value == null || value.equals("null")) 
			return "";
		switch (attendanceAtr) {
		case AMOUNT:
			return DataValueAttribute.MONEY.format(Double.valueOf(value));
		case TIME:
			return DataValueAttribute.TIME.format(Integer.parseInt(value));
		default:
			return value;
		}
	}

	private static String getNameFromCode(PrimitiveValueOfAttendanceItem primitive,
			List<AllMasterAttItemMonth> allMasterCodeName, String valueFormat) {

		switch (primitive) {
		// 職場
		case WORKPLACE_CD:
		case POSITION_CD:
			return allMasterCodeName.stream().filter(x -> x.getType() == primitive)
					.flatMap(x -> x.getLstDetail().stream()).filter(x -> x.getId().equals(valueFormat)).findFirst()
					.map(x -> x.getName()).orElse(TextResource.localize("KDW003_81"));

		case CLASSIFICATION_CD:
		case EMP_CTG_CD:
		case WORK_TYPE_DIFFERENT_CD:
			return allMasterCodeName.stream().filter(x -> x.getType() == primitive)
					.flatMap(x -> x.getLstDetail().stream()).filter(x -> x.getCode().equals(valueFormat)).findFirst()
					.map(x -> x.getName()).orElse(TextResource.localize("KDW003_81"));
		default:
			return valueFormat;
		}
	}

	public static interface Require {

		// RCGetMonthlyRecord
		public Map<String, List<MonthlyRecordValues>> getRecordValues(List<String> employeeIds, YearMonthPeriod period,
				List<Integer> itemIds);

		// AtItemNameAdapter
		public List<AttItemName> getNameOfAttendanceItem(String cid, List<Integer> attendanceItemIds,
				TypeOfItemImport type);

		// MonthlyAttendanceItemRepository
		public List<MonthlyAttendanceItem> findByAttendanceItemId(String companyId, List<Integer> attendanceItemIds);

		// GetMasterMonthProcess
		public List<AllMasterAttItemMonth> getMasterCodeName(String companyId, GeneralDate baseDate,
				List<PrimitiveValueOfAttendanceItem> itemTypes);

	}
}
