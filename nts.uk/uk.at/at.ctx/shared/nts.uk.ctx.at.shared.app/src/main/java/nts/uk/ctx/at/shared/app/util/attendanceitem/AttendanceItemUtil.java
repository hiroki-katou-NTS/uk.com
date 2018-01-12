package nts.uk.ctx.at.shared.app.util.attendanceitem;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;

import nts.gul.reflection.FieldsWorkerStream;
import nts.gul.reflection.ReflectionUtil;
import nts.gul.reflection.ReflectionUtil.Condition;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemRoot;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ItemValue;

public class AttendanceItemUtil {

	public static <T extends ConvertibleAttendanceItem> List<ItemValue> toItemValues(T attendanceItems) {
		return toItemValues(attendanceItems, "", Collections.emptyList(), 0);
	}

	public static <T extends ConvertibleAttendanceItem> List<ItemValue> toItemValues(T attendanceItems,
			List<Integer> itemIds) {
		return toItemValues(attendanceItems, "", itemIds, 0);
	}

	private static <T extends ConvertibleAttendanceItem> List<ItemValue> toItemValues(T attendanceItems,
			String rootLayout, List<Integer> itemIds, int idx) {
		AttendanceItemRoot rootAnno = getRootAnnotation(attendanceItems);
		String rootName = getRootName(rootAnno);
		if (rootAnno.isContainer()) {
			return fromContainer(attendanceItems, itemIds);
		}
		return getItemFromField(attendanceItems, rootLayout, idx, rootName, "", false, itemIds);
	}

	@SuppressWarnings("unchecked")
	private static <T extends ConvertibleAttendanceItem> List<ItemValue> fromContainer(T attendanceItems,
			List<Integer> itemIds) {
		return getItemLayouFields(attendanceItems.getClass()).map(f -> {
			T fieldValue;
			AttendanceItemLayout layout = f.getAnnotation(AttendanceItemLayout.class);

			if (layout.isList()) {
				List<T> listValue = (List<T>) ReflectionUtil.getFieldValue(f, attendanceItems);
				initNewList(f, layout, listValue);
				List<ItemValue> result = new ArrayList<>();
				for (int x = 0; x < listValue.size(); x++) {
					result.addAll(toItemValues(listValue.get(x), layout == null ? "" : layout.layout() + x, itemIds, 0));
				}
				return result;
			}
			if (layout.isOptional()) {
				Optional<T> fieldValueOpt = ((Optional<T>) ReflectionUtil.getFieldValue(f, attendanceItems));
				if (fieldValueOpt != null) {
					fieldValue = fieldValueOpt.orElse(ReflectionUtil.newInstance(getGenericType(f)));
				} else {
					fieldValue = ReflectionUtil.newInstance(getGenericType(f));
				}

			} else {
				fieldValue = (T) ReflectionUtil.getFieldValue(f, attendanceItems);
			}
			if (fieldValue == null) {
				fieldValue = ReflectionUtil.newInstance(f.getType());
			}
			return toItemValues(fieldValue, layout == null ? "" : layout.layout(), itemIds, 0);
		}).flatMap(List::stream).collect(Collectors.toList());
	}

	private static <T extends ConvertibleAttendanceItem> List<T> initNewList(Field f, AttendanceItemLayout layout, List<T> oldList) {
		if(oldList == null){
			oldList = new ArrayList<>();
		}
		Class<T> listGenericType = getGenericType(f);
		int start = (oldList == null || oldList.isEmpty()) ? 0 : oldList.size(); 
		for (int i = start; i < layout.listMaxLength(); i++) {
			T newValue = ReflectionUtil.newInstance(listGenericType);
			for(String fieldName : layout.setFieldWithIndex()){
				if(!fieldName.isEmpty()){
					ReflectionUtil.setFieldValue(getField(fieldName, listGenericType), newValue, i);
				}
			}
			oldList.add(newValue);
		}
		return oldList;
	}

	public static <T extends ConvertibleAttendanceItem> T toConvertibleAttendanceItem(Class<T> classType,
			List<ItemValue> attendanceItems) {
		T newObject = ReflectionUtil.newInstance(classType);
		return toConvertibleAttendanceItem(newObject, attendanceItems);
	}

	public static <T extends ConvertibleAttendanceItem> T toConvertibleAttendanceItem(T object,
			List<ItemValue> attendanceItems) {
		return toConvertibleAttendanceItem(object, attendanceItems, 0);
	}

	public static <T extends ConvertibleAttendanceItem, R extends ConvertibleAttendanceItem> T toConvertibleAttendanceItem(
			T object, List<ItemValue> attendanceItems, int layoutIdx) {
		AttendanceItemRoot rootAnno = getRootAnnotation(object);
		String rootName = getRootName(rootAnno);
		if (rootAnno.isContainer()) {
			return processContainer(object, attendanceItems);
		}

		return mergeToObject(object, attendanceItems, layoutIdx, 0, rootName, "", false);
	}

	private static <T extends ConvertibleAttendanceItem, R extends ConvertibleAttendanceItem> T processContainer(
			T object, List<ItemValue> attendanceItems) {
		Map<String, Field> fieldMap = toFieldMapByLayout(object);

		groupMapLayout(attendanceItems, 0, false).entrySet().stream().forEach(group -> {
			Field field = fieldMap.get(group.getKey());
			if (field != null) {
				AttendanceItemLayout layout = getLayoutAnnotation(field);
				if (layout.isList()) {
					processListProperty(object, group, field);
				} else {
					R fieldInstance = toConvertibleAttendanceItem(getInstance(object, field, layout.isOptional()), group.getValue(), 1);
					ReflectionUtil.setFieldValue(field, object,
							layout.isOptional() ? Optional.of(fieldInstance) : fieldInstance);
				}
			}
		});
		return object;
	}

	private static <R extends ConvertibleAttendanceItem, T extends ConvertibleAttendanceItem> R getInstance(T object,
			Field field, boolean isOptional) {
		if(isOptional){
			Optional<R> optionalR = ReflectionUtil.getFieldValue(field, object);
			return optionalR == null ? ReflectionUtil.newInstance(getGenericType(field)) : optionalR.get();
		} else {
			R fieldInstance = ReflectionUtil.getFieldValue(field, object);
			return fieldInstance == null ? ReflectionUtil.newInstance(field.getType()) : fieldInstance;
		}
		
	}

	private static <R extends ConvertibleAttendanceItem, T extends ConvertibleAttendanceItem> void processListProperty(
			T object, Entry<String, List<ItemValue>> group, Field field) {
		Map<String, List<ItemValue>> listGroup = groupMapLayout(group.getValue(), 0, true);
//		if (!listGroup.isEmpty()) {
		AttendanceItemLayout layout = getLayoutAnnotation(field);
		List<R> value = ReflectionUtil.getFieldValue(field, object); 
		initNewList(field, layout, value);
		ReflectionUtil.setFieldValue(field, object, getList(value, listGroup, layout.listMaxLength()));
//		}
	}

	private static <R extends ConvertibleAttendanceItem> List<R> getList(List<R> list,
			Map<String, List<ItemValue>> listGroup, int max) {
		return IntStream.range(0, max).mapToObj(idx -> {
			R oldValue = list.get(idx);
			List<ItemValue> values = listGroup.get(String.valueOf(idx));
			if (values != null) {
				return toConvertibleAttendanceItem(oldValue, values, 1);
			}
			return oldValue;
		}).filter(c -> c != null).collect(Collectors.toList());
	}

	private static <T> T mergeToObject(T object, List<ItemValue> attendanceItems, int layoutIdx, int idx,
			String rootName, String extraCondition, boolean needCheckWithIdx) {
		Map<String, Field> fieldMap = toFieldMapByLayout(object);

		groupMapLayout(attendanceItems, layoutIdx, false).entrySet().stream().forEach(group -> {
			Field field = fieldMap.get(group.getKey());
			if (field != null) {
				processGroup(object, layoutIdx, field, group.getValue(), rootName, idx, extraCondition,
						needCheckWithIdx);
			}
		});

		return object;
	}

	private static <T> void processGroup(T object, int layoutIdx, Field field, List<ItemValue> values, String path,
			int idx, String extraCondition, boolean checkWithIdx) {
		AttendanceItemLayout layout = getLayoutAnnotation(field);
		if (layout != null) {
			String newPathName = StringUtils.join(path, ".", layout.jpPropertyName());
			String newExCondition = getExCondition(extraCondition, object, layout);
			boolean needCheckWIthIdx = checkWithIdx || layout.needCheckIDWithIndex();
			if (layout.isList()) {
				validateFieldList(field);
				ReflectionUtil.setFieldValue(field, object, processFieldList(field, object, layout, values, layoutIdx,
						getGenericType(field), newPathName, newExCondition, needCheckWIthIdx));
			} else if (isItemValue(field)) {
				ReflectionUtil.setFieldValue(field, object,
						getFieldItemValue(values, field, newPathName, newExCondition, needCheckWIthIdx, idx));
			} else {
				T value = ReflectionUtil.getFieldValue(field, object);
				if(value == null){
					ReflectionUtil.setFieldValue(field, object, mergeToClass(field.getType(), values, layoutIdx + 1, idx,
							newPathName, newExCondition, needCheckWIthIdx));
				} else {
					ReflectionUtil.setFieldValue(field, object, mergeToObject(value, values, layoutIdx + 1, idx, newPathName, newExCondition, needCheckWIthIdx));
				}
			}
		}
	}
	
	private static <T> List<T> processFieldList(Field f, T object, AttendanceItemLayout layout, List<ItemValue> attendanceItems,
			int layoutIdx, Class<T> classType, String pathName, String extraCondition, boolean needCheckWithIdx) {
		List<T> value = getNotNullListValue(f, object);
		Map<String, List<ItemValue>> listGroup = groupMapLayout(attendanceItems, layoutIdx, true);
//		String newPathName = StringUtils.join(pathName, ".", layout.jpPropertyName());
//		String newExCondition = getExCondition(extraCondition, object, layout);
//		boolean newNeedCheckWithIdx = needCheckWithIdx || layout.needCheckIDWithIndex();
		String idxFieldName = layout.setFieldWithIndex()[0];
		boolean isIndexField = !idxFieldName.isEmpty();
		processListToMax(value, layout.listMaxLength(), classType, idxFieldName);
		Field idxField = isIndexField ? getField(idxFieldName, classType) : null;
		return IntStream.range(0, layout.listMaxLength()).mapToObj(idx -> {
			List<ItemValue> values = listGroup.get(String.valueOf(idx));
			T v = value.get(idx);
			if(isIndexField){
				ReflectionUtil.setFieldValue(idxField, v, idx + 1);
			}
			if (values != null) {
				// TODO: for multiple index (current, not need)
				return mergeToObject(v, values, layoutIdx + 1, idx, pathName,
						extraCondition, needCheckWithIdx);
			}
			return v;
		}).collect(Collectors.toList());

	}

	private static <T> Field getField(String fieldName, Class<T> classType) {
		try {
			return classType.getDeclaredField(fieldName);
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static <T> T getFieldItemValue(List<ItemValue> attendanceItems, Field field, String pathName,
			String extraCondition, boolean needCheckWithIdx, int idx) {
		if (attendanceItems.size() > 1) {
			throw new RuntimeException("Layout Code is not correct");
		}
		List<Integer> itemIds = getItemIds(getItemValueAnnotation(field), pathName, extraCondition, needCheckWithIdx, idx);
		if (!itemIds.isEmpty()) {
			return attendanceItems.stream().filter(ivl -> itemIds.contains(ivl.getItemId())).findFirst()
					.orElseThrow(() -> new RuntimeException("Item Id is not consistent")).value();
		}
		return null;
	}

	private static List<Integer> getItemIds(AttendanceItemValue itemValueAnno, String pathName, String extraCondition,
			boolean needCheckWithIdx, int idx) {
		if (itemValueAnno.getIdFromUtil()) {
			String key = StringUtils.join(pathName,
					extraCondition.isEmpty() ? "" : StringUtils.join("-", extraCondition),
					(needCheckWithIdx ? String.valueOf(idx+1) : ""));
			Integer itemId = AttendanceItemIdContainer.getId(key);
			if (itemId != null && itemId >= 0) {
				return Arrays.asList(itemId);
			}
		} else {
			return Arrays.stream(itemValueAnno.itemId()).filter(id -> id >= 0).boxed().collect(Collectors.toList());
		}

		return Collections.emptyList();
	}
	
	private static <T> void processListToMax(List<T> list, int max, Class<T> targetClass, String idxFieldName){
		//TODO: check result list
		for(int x = list.size(); x < max; x++){
			list.add(ReflectionUtil.newInstance(targetClass));
		}
		if(!idxFieldName.isEmpty()){
			Field idxField = getField(idxFieldName, targetClass);
			Collections.sort(list, new Comparator<T>() {
			    @Override
			    public int compare(T c1, T c2) {
			    	Integer idx1 = ReflectionUtil.getFieldValue(idxField, c1);
					Integer idx2 = ReflectionUtil.getFieldValue(idxField, c2);
					if(idx1 == null && idx2 == null){
						return 0;
					}
					if(idx1 == null) {
						return 1;
					}
					if(idx2 == null) {
						return -1;
					}
					return idx1.compareTo(idx2);
			    }
			});
		}
	}
	
	private static <T> List<T> getNotNullListValue(Field f, T object){
		List<T> value = ReflectionUtil.getFieldValue(f, object);
		if(value == null){
			value = new ArrayList<>();
		}
		return value;
	}

	private static <T> T mergeToClass(Class<T> classType, List<ItemValue> attendanceItems, int layoutIdx, int idx,
			String pathName, String extraCondition, boolean needCheckWithIdx) {
		return mergeToObject(ReflectionUtil.newInstance(classType), attendanceItems, layoutIdx, idx, pathName,
				extraCondition, needCheckWithIdx);
	}

	private static List<ItemValue> getItemFromField(Object attendanceItems, String currentLayout, int idx,
			String rootName, String extraCondition, boolean needCheckWithIdx, List<Integer> itemIds) {

		return getItemLayouFields(attendanceItems.getClass()).map(field -> {
			return processProperty(field, attendanceItems, currentLayout, idx, rootName, extraCondition,
					needCheckWithIdx, itemIds);
		}).flatMap(List::stream).collect(Collectors.toList());
	}

	@SuppressWarnings("unchecked")
	private static <T> List<ItemValue> processProperty(Field field, Object attendanceItems, String currentLayout,
			int idx, String pathName, String extraCondition, boolean needCheckWithIdx, List<Integer> itemIds) {
		T value = ReflectionUtil.getFieldValue(field, attendanceItems);
		AttendanceItemLayout layout = getLayoutAnnotation(field);
		String newPathName = StringUtils.join(pathName, ".", layout.jpPropertyName());
		String newExCondition = getExCondition(extraCondition, attendanceItems, layout);
		needCheckWithIdx = needCheckWithIdx || layout.needCheckIDWithIndex();
		if (layout.isList()) {
			if (value == null) {
				List<T> values = new ArrayList<>();
				Class<T> className = getGenericType(field);
				IntStream.range(0, layout.listMaxLength()).forEach(c -> {
					if (!className.equals(Integer.class)) {
						T newInstance = ReflectionUtil.newInstance(className);
						for (String fieldName : layout.setFieldWithIndex()) {
							if(!fieldName.isEmpty()){
								ReflectionUtil.setFieldValue(getField(fieldName, className), newInstance, c);
							}
						}
						values.add(newInstance);
					} else {
						values.add(null);
					}
				});
				return processList(currentLayout, layout.layout(), values, newPathName, newExCondition,
						needCheckWithIdx, itemIds);
			}
			return processList(currentLayout, layout.layout(), (List<T>) value, newPathName, newExCondition,
					needCheckWithIdx, itemIds);
		}
		if (value == null) {
			if (!field.getType().equals(Integer.class)) {
				value = ReflectionUtil.newInstance(field.getType());
			}
		}
		return processOne(currentLayout, field, layout.layout(), value, idx, newPathName, newExCondition,
				needCheckWithIdx, itemIds);
	}

	private static <T> List<ItemValue> processOne(String currentLayout, Field field, String layoutCode, T value,
			int idx, String pathName, String extraCondition, boolean needCheckWithIdx, List<Integer> itemIds) {
		if (isItemValue(field)) {
			return newItemValue(value, currentLayout, field, layoutCode, idx, pathName, extraCondition,
					needCheckWithIdx, itemIds);
		} else {
			return getItemFromField(value, mergeLayout(currentLayout, layoutCode), idx, pathName, extraCondition,
					needCheckWithIdx, itemIds);
		}
	}

	private static <T> List<ItemValue> processList(String currentLayout, String layoutCode, List<T> values,
			String pathName, String extraCondition, boolean needCheckWithIdx, List<Integer> itemIds) {
		return IntStream.range(0, values.size()).mapToObj(idx -> {
			return getItemFromField(values.get(idx), mergeLayout(currentLayout, layoutCode + idx), idx, pathName,
					extraCondition, needCheckWithIdx, itemIds);
		}).flatMap(List::stream).collect(Collectors.toList());
	}

	private static <T> List<ItemValue> newItemValue(T value, String currentLayout, Field field, String layoutCode,
			int idx, String pathName, String extraCondition, boolean needCheckWithIdx, List<Integer> onNeedItemIds) {
		AttendanceItemValue valueType = getItemValueAnnotation(field);
		List<Integer> itemIds = getItemIds(valueType, pathName, extraCondition, needCheckWithIdx, idx);
		if (itemIds.isEmpty()) {
			// TODO: after uncomment when item id is full list
			return Collections.emptyList();
			// throw new NotFoundException("Item id not found exception!!!");
		}
		int itemId = valueType.getIdFromUtil() ? itemIds.get(0) : itemIds.get(idx);
		//System.out.println("id:" + itemId);
		if (onNeedItemIds.isEmpty() || onNeedItemIds.contains(itemId)) {
//			Logger.getLogger(AttendanceItemUtil.class.getName()).info(pathName + " - " + itemId);
			ItemValue itemValue = new ItemValue(valueType.type(), mergeLayout(currentLayout, layoutCode), itemId);
			itemValue.value(value);
			return Arrays.asList(itemValue);
		}
		return Collections.emptyList();
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

	private static AttendanceItemRoot getRootAnnotation(Object object) {
		return object.getClass().getAnnotation(AttendanceItemRoot.class);
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

	private static <T> String getExCondition(String exCondition, T object, AttendanceItemLayout layout) {
		String fieldExCondition = getExConditionField(object, layout);
		if (!exCondition.isEmpty() && !fieldExCondition.isEmpty()) {
			return StringUtils.join(exCondition, "-", fieldExCondition);
		}
		if(fieldExCondition.isEmpty()){
			return exCondition;
		}
		return fieldExCondition;
	}

	private static String getRootName(AttendanceItemRoot rootAnno) {
		return rootAnno == null ? "" : rootAnno.rootName();
	}

	private static <T> String getExConditionField(T object, AttendanceItemLayout layout) {
		if (!layout.needCheckIDWithMethod().isEmpty()) {
			return ReflectionUtil.invoke(object.getClass(), object, layout.needCheckIDWithMethod()).toString();
		}
		if (!layout.needCheckIDWithField().isEmpty()) {
			Field refeField;
			try {
				refeField = object.getClass().getField(layout.needCheckIDWithField());
				return ReflectionUtil.getFieldValue(refeField, object).toString();
			} catch (NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
			}
		}

		return "";
	}
}
