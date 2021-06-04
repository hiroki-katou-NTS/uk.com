package nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceItemUtilRes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;

public class AttendanceItemUtil implements ItemConst {

	public static <T extends ConvertibleAttendanceItem> List<ItemValue> toItemValues(T attendanceItems,
			Collection<Integer> itemIds) {

		return toItemValues(attendanceItems, itemIds, AttendanceItemType.DAILY_ITEM);
	}

	public static <T extends ConvertibleAttendanceItem> Map<T, List<ItemValue>> toItemValues(List<T> attendanceItems) {

		return toItemValues(attendanceItems, AttendanceItemType.DAILY_ITEM);
	}

	public static <T extends ConvertibleAttendanceItem> Map<T, List<ItemValue>> toItemValues(List<T> attendanceItems,
			AttendanceItemType type) {

		return toItemValues(attendanceItems, Collections.emptyList(), type);
	}

	public static <T extends ConvertibleAttendanceItem> Map<T, List<ItemValue>> toItemValues(List<T> attendanceItems,
			Collection<Integer> itemIds) {

		return toItemValues(attendanceItems, itemIds, AttendanceItemType.DAILY_ITEM);
	}

	public static <T extends ConvertibleAttendanceItem> List<ItemValue> toItemValues(T attendanceItems,
			Collection<Integer> itemIds, AttendanceItemType type) {

		AttendanceItemRoot root = attendanceItems.getClass().getAnnotation(AttendanceItemRoot.class);

		if (root == null) {
			return new ArrayList<>();
		}

		return toItemValues(Arrays.asList(attendanceItems), itemIds, type).get(attendanceItems);
	}

	public static <T extends ConvertibleAttendanceItem> Map<T, List<ItemValue>> toItemValues(List<T> attendanceItems,
			Collection<Integer> itemIds, AttendanceItemType type) {

		Map<T, List<ItemValue>> result = new HashMap<>();
		
		for (T at : attendanceItems) {
			result.put(at,  AttendanceItemUtilRes.collect(at, itemIds, type));
		}
		
		return result;
	}

	public static <T extends ConvertibleAttendanceItem> T fromItemValues(T attendanceItems, Collection<ItemValue> itemValues) {

		return fromItemValues(attendanceItems, itemValues, AttendanceItemType.DAILY_ITEM);
	}

	public static <T extends ConvertibleAttendanceItem> T fromItemValues(T attendanceItems, Collection<ItemValue> itemValues, AttendanceItemType type) {

		return AttendanceItemUtilRes.merge(attendanceItems, itemValues, type);
	}

	public enum AttendanceItemType {

		DAILY_ITEM(DEFAULT_IDX, DAILY),

		MONTHLY_ITEM(DEFAULT_NEXT_IDX, MONTHLY),

		ANY_PERIOD_ITEM(DEFAULT_NEXT_IDX + 1, ANY_PERIOD),

		WEEKLY_ITEM(DEFAULT_NEXT_IDX + 2, WEEKLY);

		public final int value;

		public final String descript;

		private AttendanceItemType(int value, String descript) {

			this.value = value;
			this.descript = descript;
		}

	}
}
