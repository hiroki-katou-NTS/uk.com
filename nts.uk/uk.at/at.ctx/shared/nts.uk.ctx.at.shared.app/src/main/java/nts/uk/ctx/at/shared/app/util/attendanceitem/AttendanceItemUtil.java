package nts.uk.ctx.at.shared.app.util.attendanceitem;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;

import nts.gul.reflection.FieldsWorkerStream;
import nts.gul.reflection.ReflectionUtil;
import nts.gul.reflection.ReflectionUtil.Condition;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ItemValue;

public class AttendanceItemUtil {

	public static List<ItemValue> toItemValues(ConvertibleAttendanceItem attendanceItems) {
		return getItemFromField(attendanceItems, "", 0);
	}

	public static <T extends ConvertibleAttendanceItem> T toConvertibleAttendanceItem(Class<T> classType,
			List<ItemValue> attendanceItems) {
		T newObject = ReflectionUtil.newInstance(classType);
		return toConvertibleAttendanceItem(newObject, attendanceItems);
	}

	public static <T extends ConvertibleAttendanceItem> T toConvertibleAttendanceItem(T item,
			List<ItemValue> attendanceItems) {
		return mergeToObject(item, attendanceItems, 0);
	}

	private static <T> T mergeToObject(T object, List<ItemValue> attendanceItems, int layoutIdx) {
		Map<String, Field> fieldMap = toFieldMapByLayout(object);

		groupMapLayout(attendanceItems, layoutIdx, false).entrySet().stream().forEach(group -> {
			Field field = fieldMap.get(group.getKey());
			if (field != null) {
				processGroup(object, layoutIdx, field, group.getValue());
			}
		});

		return object;
	}

	private static <T> void processGroup(T object, int layoutIdx, Field field, List<ItemValue> values) {
		if (getLayoutAnnotation(field).isList()) {
			validateFieldList(field);
			ReflectionUtil.setFieldValue(field, object, processFieldList(values, layoutIdx, getGenericType(field)));
		} else if (isItemValue(field)) {
			ReflectionUtil.setFieldValue(field, object, getFieldItemValue(values, field));
		} else {
			ReflectionUtil.setFieldValue(field, object, mergeToClass(field.getType(), values, layoutIdx + 1));
		}
	}

	private static <T> T getFieldItemValue(List<ItemValue> attendanceItems, Field field) {
		if (attendanceItems.size() > 1) {
			throw new RuntimeException("Layout Code is not correct");
		}
		List<Integer> itemIds = Arrays.stream(getItemValueAnnotation(field).itemId()).boxed()
				.collect(Collectors.toList());
		
		return attendanceItems.stream().filter(ivl -> itemIds.contains(ivl.getItemId())).findFirst()
				.orElseThrow(() -> new RuntimeException("Item Id is not consistent")).value();
	}

	private static <T> List<T> processFieldList(List<ItemValue> attendanceItems, int layoutIdx, Class<T> classType) {
		Map<String, List<ItemValue>> listGroup = groupMapLayout(attendanceItems, layoutIdx, true);
		if (!listGroup.isEmpty()) {
			int max = listGroup.entrySet().stream().filter(e -> !e.getKey().isEmpty())
					.mapToInt(e -> Integer.parseInt(e.getKey())).max().orElse(-1);
			return IntStream.range(0, max + 1).mapToObj(idx -> {
				List<ItemValue> values = listGroup.get(String.valueOf(idx));
				if (values != null) {
					return mergeToClass(classType, values, layoutIdx + 1);
				}
				return ReflectionUtil.newInstance(classType);
			}).collect(Collectors.toList());
		}
		return Collections.emptyList();

	}

	private static <T> T mergeToClass(Class<T> classType, List<ItemValue> attendanceItems, int layoutIdx) {
		return mergeToObject(ReflectionUtil.newInstance(classType), attendanceItems, layoutIdx);
	}

	private static List<ItemValue> getItemFromField(Object attendanceItems, String currentLayout, int idx) {

		return getItemLayouFields(attendanceItems.getClass()).map(field -> {
			return propertiesToItemValues(attendanceItems, currentLayout, field, idx);
		}).flatMap(List::stream).collect(Collectors.toList());
	}

	private static List<ItemValue> propertiesToItemValues(Object attendanceItems, String currentLayout, Field field, int idx) {
		AttendanceItemLayout layoutAnno = getLayoutAnnotation(field);
		return processProperty(field, attendanceItems, currentLayout, layoutAnno.layout(), layoutAnno.isList(), idx);
	}

	@SuppressWarnings("unchecked")
	private static <T> List<ItemValue> processProperty(Field field, Object attendanceItems, String currentLayout,
			String layoutCode, boolean isList, int idx) {
		T values = ReflectionUtil.getFieldValue(field, attendanceItems);
		if (values != null) {
			if (isList) {
				return processList(currentLayout, layoutCode, (List<T>) values);
			}
			return processOne(currentLayout, field, layoutCode, values, idx);
		}
		return Collections.emptyList();
	}

	private static <T> List<ItemValue> processOne(String currentLayout, Field field, String layoutCode, T value, int idx) {
		if (isItemValue(field)) {
			ItemValue itv = newItemValue(currentLayout, field, layoutCode, idx);
			itv.value(value);
			return Arrays.asList(itv);
		} else {
			return getItemFromField(value, mergeLayout(currentLayout, layoutCode), idx);
		}
	}

	private static <T> List<ItemValue> processList(String currentLayout, String layoutCode, List<T> values) {
		return IntStream.range(0, values.size()).mapToObj(idx -> {
			return getItemFromField(values.get(idx), mergeLayout(currentLayout, layoutCode + idx), idx);
		}).flatMap(List::stream).collect(Collectors.toList());
	}

	private static ItemValue newItemValue(String currentLayout, Field field, String layoutCode, int idx) {
		AttendanceItemValue valueType = getItemValueAnnotation(field);
		return new ItemValue(valueType.type(), mergeLayout(currentLayout, layoutCode), valueType.itemId()[idx]);
	}

	@SuppressWarnings("unchecked")
	private static <T> Class<T> getGenericType(Field field) {
		ParameterizedType type = (ParameterizedType) field.getGenericType();
		return (Class<T>) type.getActualTypeArguments()[0];
	}

	private static Map<String, List<ItemValue>> groupMapLayout(List<ItemValue> attendanceItems, int layoutIdx,
			boolean isList) {
		return attendanceItems.stream().collect(Collectors.groupingBy(c -> {
			return getCurrentLayout(layoutIdx, c, isList);
		}, Collectors.toList()));
	}

	private static <T> Map<String, Field> toFieldMapByLayout(T object) {
		return getItemLayouFields(object.getClass())
				.collect(Collectors.toMap(f -> getLayoutAnnotation(f).layout(), f -> f));
	}

	private static <T> FieldsWorkerStream getItemLayouFields(Class<T> classType) {
		return ReflectionUtil.getStreamOfFieldsAnnotated(classType, Condition.ALL, AttendanceItemLayout.class);
	}

	private static AttendanceItemLayout getLayoutAnnotation(Field field) {
		return field.getAnnotation(AttendanceItemLayout.class);
	}

	private static AttendanceItemValue getItemValueAnnotation(Field field) {
		return field.getAnnotation(AttendanceItemValue.class);
	}

	private static boolean isItemValue(Field field) {
		return field.isAnnotationPresent(AttendanceItemValue.class);
	}

	private static String getCurrentLayout(int layoutIdx, ItemValue c, boolean isList) {
		String[] layouts = c.getLayoutCode().split("_");
		if (layouts.length <= layoutIdx) {
			return "";
		}
		String firstlayout = layouts[layoutIdx];
		if (isList) {
			return firstlayout.replaceAll("[^0-9]", "");
		}
		return firstlayout.replaceAll("[0-9]", "");
	}

	private static String mergeLayout(String currentLayout, String fieldLayout) {
		if (currentLayout.isEmpty()) {
			return fieldLayout;
		}
		return StringUtils.join(currentLayout, "_", fieldLayout);
	}

	private static boolean isFieldList(Field field) {
		return List.class.isAssignableFrom(field.getType());
	}

	private static void validateFieldList(Field field) {
		if (!isFieldList(field)) {
			throw new RuntimeException("This Field is not Collection: " + field.getName());
		}
	}
}
