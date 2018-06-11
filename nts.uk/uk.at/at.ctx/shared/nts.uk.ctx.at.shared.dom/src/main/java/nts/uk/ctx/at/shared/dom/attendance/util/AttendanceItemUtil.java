package nts.uk.ctx.at.shared.dom.attendance.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import nts.gul.reflection.ReflectionUtil;
import nts.gul.reflection.ReflectionUtil.Condition;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;

public class AttendanceItemUtil {

	public static <T extends ConvertibleAttendanceItem> List<ItemValue> toItemValues(T attendanceItems) {
		return toItemValues(attendanceItems, AttendanceItemType.DAILY_ITEM);
	}

	public static <T extends ConvertibleAttendanceItem> T fromItemValues(Class<T> classType,
			Collection<ItemValue> attendanceItems) {
		return fromItemValues(classType, attendanceItems, AttendanceItemType.DAILY_ITEM);
	}
	
	public static <T extends ConvertibleAttendanceItem> List<ItemValue> toItemValues(T attendanceItems, AttendanceItemType type) {
		return toItemValues(attendanceItems, Collections.emptyList());
	}

	public static <T extends ConvertibleAttendanceItem> T fromItemValues(Class<T> classType,
			Collection<ItemValue> attendanceItems, AttendanceItemType type) {
		T newObject = ReflectionUtil.newInstance(classType);
		return fromItemValues(newObject, attendanceItems);
	}

	public static <T extends ConvertibleAttendanceItem> List<ItemValue> toItemValues(T attendanceItems,
			Collection<Integer> itemIds) {
		return toItemValues(attendanceItems, itemIds, AttendanceItemType.DAILY_ITEM);
	}

	public static <T> T fromItemValues(T attendanceItems, Collection<ItemValue> itemValues) {
		return fromItemValues(attendanceItems, itemValues, AttendanceItemType.DAILY_ITEM);
	}
	
	public static <T extends ConvertibleAttendanceItem> List<ItemValue> toItemValues(T attendanceItems,
			Collection<Integer> itemIds, AttendanceItemType type) {
		// return toItemValues(attendanceItems, "", itemIds, 0);
		AttendanceItemRoot root = attendanceItems.getClass().getAnnotation(AttendanceItemRoot.class);
		if(root == null){
			return new ArrayList<>();
		}
		int layout = root.isContainer() ? 0 : 1;
		return getItemValues(attendanceItems, layout, "", root.isContainer() ? "" : root.rootName(), "", 0, getItemMap(type, itemIds, null, layout));
	}

	public static <T> T fromItemValues(T attendanceItems, Collection<ItemValue> itemValues, AttendanceItemType type) {
		// return toItemValues(attendanceItems, "", itemIds, 0);
		AttendanceItemRoot root = attendanceItems.getClass().getAnnotation(AttendanceItemRoot.class);
		if(root == null){
			return attendanceItems;
		}
		int layout = root.isContainer() ? 0 : 1;
		Map<Integer, ItemValue> itemMap = itemValues.stream().collect(Collectors.toMap(c -> c.itemId(), c -> c));
		return fromItemValues(attendanceItems, layout, "", root.isContainer() ? "" : root.rootName(), 0, false,
				getItemMap(type, itemMap.keySet(), c -> itemMap.get(c.itemId()).withPath(c.path()), layout));
	}

	@SuppressWarnings("unchecked")
	private static <T> List<ItemValue> getItemValues(T attendanceItems, int layoutIdx, String layoutCode, String path,
			String extraCondition, int index, Map<String, List<ItemValue>> groups) {
		Map<String, Field> fields = getFieldMap(attendanceItems, groups);
		return groups.entrySet().stream().map(c -> {
			Field field = fields.get(c.getKey());
			if (field == null) {
				return new ArrayList<ItemValue>();
			}
			AttendanceItemLayout layout = getLayoutAnnotation(field);
			boolean isList = layout.listMaxLength() > 0;
			Class<T> className = layout.isOptional() || isList ? getGenericType(field) : (Class<T>) field.getType();
			String pathName = getPath(path, layout, getRootAnnotation(field)),
					currentLayout = mergeLayout(layoutCode, layout.layout()),
					exCon = getExCondition(extraCondition, attendanceItems, layout);
			if (isList) {
				List<T> list = getListAndSort(attendanceItems, field, className, layout);
				return mapByPath(c.getValue(),
								x -> layout.listNoIndex() ? getEValAsIdxPlus(x.path()) : getIdxInText(x.path())
							).entrySet().stream().map(idx -> {
									boolean isNotHaveData = list.isEmpty() || list.size() < idx.getKey();
         								T idxValue = isNotHaveData ? null : list.get(idx.getKey() - 1);
									return getItemValues(
												fieldValue(className, idxValue),  layoutIdx + 1,
												layout.listNoIndex() ? currentLayout : currentLayout + idx.getKey(), 
												pathName, exCon,  idx.getKey(),
												mapByPath(idx.getValue(), id -> getCurrentPath(layoutIdx + 1, id.path(), false)));
								}).flatMap(List::stream).collect(Collectors.toList());
			} 
			T value = getOptionalFieldValue(attendanceItems, field, layout.isOptional());
			AttendanceItemValue valueAnno = getItemValueAnnotation(field);
			if (valueAnno == null) {
				return getItemValues(
						fieldValue(className, value),  layoutIdx + 1, currentLayout, pathName, exCon, index,
						mapByPath(c.getValue(), id -> getCurrentPath(layoutIdx + 1, id.path(), false)));
				
			} 
			String currentPath = getKey(pathName, "", false, index);
			String currentFullPath = getKey(pathName, exCon, index > 0, index);
			return filterAndMap(
						c.getValue(), 
						id -> getTextWithNoCondition(id.path()).equals(currentPath),
						item -> {
							if(item.path().equals(currentFullPath)){
								return item.value(value).valueType(valueAnno.type())
											.layout(currentLayout + getTextWithCondition(item.path()))
											.completed();
							} else {
								return item.layout(currentLayout + getTextWithCondition(item.path()))
											.valueType(valueAnno.type()).completed();
							}
						});
			
		}).flatMap(List::stream).collect(Collectors.toList());
	}

	@SuppressWarnings("unchecked")
	private static <T> T fromItemValues(T attendanceItems, int layoutIdx, String layoutCode, String path, int index,
			boolean needCheckWithIdx, Map<String, List<ItemValue>> groups) {
		if(attendanceItems.getClass().getAnnotation(AttendanceItemRoot.class) != null){
			ReflectionUtil.invoke(attendanceItems.getClass(), attendanceItems, "exsistData");
		}
		Map<String, Field> fields = getFieldMap(attendanceItems, groups);
		groups.entrySet().stream().forEach(c -> {
			Field field = fields.get(c.getKey());
			if (field == null) {
				return;
			}
			AttendanceItemLayout layout = getLayoutAnnotation(field);
			boolean isList = layout.listMaxLength() > 0;
			Class<T> className = layout.isOptional() || isList ? getGenericType(field) : (Class<T>) field.getType();
			String pathName = getPath(path, layout, getRootAnnotation(field)),
					currentLayout = mergeLayout(layoutCode, layout.layout());
			if (isList) {
				boolean listNoIdx = layout.listNoIndex();
				Map<Integer, List<ItemValue>> itemsForIdx = mapByPath(c.getValue(), 
						x -> listNoIdx ? getEValAsIdxPlus(x.path()) : getIdxInText(x.path()));
				List<T> list = processListToMax(
									ReflectionUtil.getFieldValue(field, attendanceItems),
									layout, 
									className, 
									c.getValue().isEmpty() ? "" : c.getValue().get(0).path(),
									itemsForIdx.keySet());
				String idxFieldName = listNoIdx ? layout.enumField() : layout.indexField();
				Field idxField = idxFieldName.isEmpty() ? null : getField(idxFieldName, className);
				
				list.stream().forEach(eVal -> {
					Integer idx = idxField == null ? null : ReflectionUtil.getFieldValue(idxField, eVal);
					List<ItemValue> subList = idx == null ? null : itemsForIdx.get(listNoIdx ? idx + 1 : idx);
					if (subList != null) {
						fromItemValues(
								eVal, 
								layoutIdx + 1,
								listNoIdx ? currentLayout : currentLayout + (idx == null ? "" : idx), 
								pathName,
								idx, 
								needCheckWithIdx || (isList && !listNoIdx),
								mapByPath(subList, id -> getCurrentPath(layoutIdx + 1, id.path(), false)));

						setValueEnumField(layout, className, eVal, subList);
					}
				});
				ReflectionUtil.setFieldValue(field, attendanceItems, list);
				return;
			} 
			T value = getOptionalFieldValue(attendanceItems, field, layout.isOptional());
			AttendanceItemValue valueAnno = getItemValueAnnotation(field);
			if (valueAnno != null) {
				String enumText = getEnumTextFromList(c.getValue()), 
						currentPath = getKey(pathName, enumText == null ? "" : enumText, needCheckWithIdx, index);
				ItemValue itemValue = c.getValue().stream()
												.filter(id -> id.path().equals(currentPath))
												.findFirst().orElse(null);
				if (itemValue != null) {
					ReflectionUtil.setFieldValue(field, attendanceItems, itemValue.value());
				}
				return;
			} 
			T nVal = fromItemValues(
							value == null ? ReflectionUtil.newInstance(className) : value,
							layoutIdx + 1, 
							currentLayout, 
							pathName, 
							index, 
							needCheckWithIdx,
							mapByPath(c.getValue(), id -> getCurrentPath(layoutIdx + 1, id.path(), false)));
			setValueEnumField(layout, className, nVal, c.getValue());
			ReflectionUtil.setFieldValue(field, attendanceItems, layout.isOptional() ? Optional.of(nVal) : nVal);
		});

		return attendanceItems;
	}
	
	private static <T> void clearConflictEnumsInList(AttendanceItemLayout layout, Class<T> className, List<T> value,
			String path) {
		if (layout.enumField().isEmpty() || !layout.removeConflictEnum() || path.isEmpty()) {
			return;
		}
		String enumText = getExConditionFromString(path);
		if(enumText.isEmpty()){
			return;
		}
		Field field = getField(layout.enumField(), className);
		int eVal = AttendanceItemIdContainer.getEnumValue(enumText);
		value.removeIf(c -> {
			Integer currentEval = ReflectionUtil.getFieldValue(field, c); 
			return currentEval != null && currentEval != eVal; 
		});
	}

	private static Integer getEValAsIdxPlus(String path) {
		Integer enumValue = AttendanceItemIdContainer.getEnumValue(getExConditionFromString(path));
		return enumValue == null ? 1 : enumValue + 1;
	}

	private static String getEnumTextFromList(List<ItemValue> c) {
		return c.stream().filter(em -> em.path().indexOf("-") > 0).map(em -> getExConditionFromString(em.path()))
				.findFirst().orElse(null);
	}

	private static <T> void setValueEnumField(AttendanceItemLayout layout, Class<T> className, T value,
			List<ItemValue> items) {
		if (layout.enumField().isEmpty()) {
			return;
		}
		String enumText = items.stream().filter(em -> em.path().indexOf("-") > 0)
				.map(em -> getExConditionFromString(em.path())).findFirst().orElse(null);
		ReflectionUtil.setFieldValue(getField(layout.enumField(), className), value,
				AttendanceItemIdContainer.getEnumValue(enumText));
	}

	private static String getPath(String path, AttendanceItemLayout layout, AttendanceItemRoot root) {
		if (root != null && !root.rootName().isEmpty()) {
			return root.rootName();
		}
		return path.isEmpty() ? layout.jpPropertyName() : StringUtils.join(path, ".", layout.jpPropertyName());
	}

	private static <T> List<T> filterAndMap(List<T> ids, Predicate<T> filter, Function<T, T> mapper) {
		return ids.stream().filter(filter).map(mapper).collect(Collectors.toList());
	}

	private static <T> T getOptionalFieldValue(T attendanceItems, Field field, boolean isOptional) {
		if (isOptional) {
			Optional<T> optional = ReflectionUtil.getFieldValue(field, attendanceItems);
			return optional == null ? null : optional.orElse(null);
		}
		return ReflectionUtil.getFieldValue(field, attendanceItems);
	}

	private static <T> T fieldValue(Class<T> className, T idxValue) {
		return idxValue == null ? ReflectionUtil.newInstance(className) : idxValue;
	}

	private static <T> Map<T, List<ItemValue>> mapByPath(List<ItemValue> ids, Function<ItemValue, T> grouping) {
		return ids.stream().collect(Collectors.groupingBy(grouping));
	}

	private static Map<String, List<ItemValue>> getItemMap(AttendanceItemType type, Collection<Integer> itemIds,
			Function<ItemValue, ItemValue> mapper, int layout) {
		Stream<ItemValue> stream = AttendanceItemIdContainer.getIdMapStream(itemIds, type);
		if (mapper != null) {
			stream = stream.map(mapper);
		}
		return stream.collect(Collectors.groupingBy(c -> getCurrentPath(layout, c.path(), false)));
	}

	public static <T> Map<String, Field> getFieldMap(T attendanceItems, Map<String, List<ItemValue>> groups) {
		return ReflectionUtil
				.getStreamOfFieldsAnnotated(attendanceItems.getClass(), Condition.ALL, AttendanceItemLayout.class)
				.filter(c -> groups.get(c.getAnnotation(AttendanceItemLayout.class).jpPropertyName()) != null)
				.collect(Collectors.toMap(c -> c.getAnnotation(AttendanceItemLayout.class).jpPropertyName(), c -> c));
	}

	private static <T> Field getField(String fieldName, Class<T> classType) {
		try {
			return classType.getDeclaredField(fieldName);
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static String getKey(String pathName, String extraCondition, boolean needCheckWithIdx, int idx) {
		return StringUtils.join(pathName, extraCondition.isEmpty() ? "" : StringUtils.join("-", extraCondition),
				(needCheckWithIdx ? String.valueOf(idx) : ""));
	}

	private static <T> List<T> getListAndSort(T attendanceItems, Field field, Class<T> className, AttendanceItemLayout layout) {
		List<T> list = ReflectionUtil.getFieldValue(field, attendanceItems);
		if(list == null){
			return new ArrayList<>();
		}
		if(!layout.indexField().isEmpty()){
			processAndSort(list, layout.listMaxLength(), className, layout.indexField());
		}
		return list;
	}

	private static <T> List<T> processListToMax(List<T> list, AttendanceItemLayout layout, Class<T> targetClass, String path, Set<Integer> keySet) {
		list = list == null ? new ArrayList<>() : new ArrayList<>(list);
		if(layout.listNoIndex()){
			Field enumField = getField(layout.enumField(), targetClass);
			if(enumField != null) {
				Set<Integer> notExistEnums = notExistEnum(list, keySet, enumField);
				for(Integer e : notExistEnums) {
					T nIns = ReflectionUtil.newInstance(targetClass); 
					ReflectionUtil.setFieldValue(enumField, nIns, e - 1);
					list.add(nIns);
				}
			}
			return list;
		}
		clearConflictEnumsInList(layout, targetClass, list, path);
		if (!layout.indexField().isEmpty()) {
			processAndSort(list, layout.listMaxLength(), targetClass, layout.indexField());
		} else {
			for (int x = list.size(); x < layout.listMaxLength(); x++) {
				list.add(ReflectionUtil.newInstance(targetClass));
			}
		}
		return list;
	}

	private static <T> Set<Integer> notExistEnum(List<T> list, Set<Integer> keySet, Field enumField) {
		return keySet.stream().filter(c -> {
			return !list.stream().filter(l -> ((Integer) ReflectionUtil.getFieldValue(enumField, l)) == (c - 1)).findFirst().isPresent();
		}).collect(Collectors.toSet());
	}

	private static <T> void processAndSort(List<T> list, int max, Class<T> targetClass, String idxFieldName) {
		Field idxField = getField(idxFieldName, targetClass);
		if(list.size() < max){
			for (int x = 0; x < max; x++) {
				int index = x;
				Optional<T> idxValue = list.stream().filter(c -> {
					Integer idx = ReflectionUtil.getFieldValue(idxField, c);
					return idx == null ? false : idx == (index + 1);
				}).findFirst();
				if (!idxValue.isPresent()) {
					list.add(createIdxFieldValue(targetClass, idxField, index + 1));
				}
			}
		}
		Collections.sort(list, new Comparator<T>() {
			@Override
			public int compare(T c1, T c2) {
				Integer idx1 = ReflectionUtil.getFieldValue(idxField, c1);
				Integer idx2 = ReflectionUtil.getFieldValue(idxField, c2);
				if (idx1 == null && idx2 == null) {
					return 0;
				}
				if (idx1 == null) {
					return 1;
				}
				if (idx2 == null) {
					return -1;
				}
				return idx1.compareTo(idx2);
			}
		});
	}

	public static <T> T createIdxFieldValue(Class<T> targetClass, Field idxField, int index) {
		T newValue = ReflectionUtil.newInstance(targetClass);
		ReflectionUtil.setFieldValue(idxField, newValue, index);
		return newValue;
	}

	private static String getCurrentPath(int layoutIdx, String text, boolean isList) {
		String[] layouts = text.split("\\.");
		if (layouts.length <= layoutIdx) {
			return "";
		}
		String path = layouts[layoutIdx];
		if (isList) {
			return getIdx(path);
		}
		return getTextWithNoCondition(path);
	}

	private static String getIdx(String path) {
		String notIdx = getTextWithNoIdx(path);
		return path.replaceAll(notIdx, "");
	}

	@SuppressWarnings("unchecked")
	private static <T> Class<T> getGenericType(Field field) {
		ParameterizedType type = (ParameterizedType) field.getGenericType();
		return (Class<T>) type.getActualTypeArguments()[0];
	}

	private static AttendanceItemLayout getLayoutAnnotation(Field field) {
		return field.getAnnotation(AttendanceItemLayout.class);
	}

	private static AttendanceItemValue getItemValueAnnotation(Field field) {
		return field.getAnnotation(AttendanceItemValue.class);
	}

	private static AttendanceItemRoot getRootAnnotation(Field field) {
		return field.getAnnotation(AttendanceItemRoot.class);
	}

	private static int getIdxInText(String text) {
		String index = getIdx(text);
		if (index.isEmpty()) {
			return 0;
		}
		return Integer.valueOf(index);
	}

	private static String getTextWithNoIdx(String text) {
		return text.replaceAll("[0-9]+$", "");
	}

	private static String getTextWithNoCondition(String text) {
		return getTextWithNoIdx(text).split("-")[0];
	}

	private static String getTextWithCondition(String text) {
		String[] cons = text.split("-");
		return cons.length >= 2 ? "-" + getTextWithNoIdx(cons[1]) : "";
	}

	private static String getExConditionFromString(String text) {
		String[] notIdxText = text.replaceAll("[0-9]+$", "").split("-");
		if (notIdxText.length < 2) {
			return "";
		}
		return notIdxText[1];
	}

	private static String mergeLayout(String currentLayout, String fieldLayout) {
		if (currentLayout.isEmpty()) {
			return fieldLayout;
		}
		return StringUtils.join(currentLayout, "_", fieldLayout);
	}

	private static <T> String getExCondition(String exCondition, T object, AttendanceItemLayout layout) {
		String fieldExCondition = getExConditionField(object, layout);
		if (!exCondition.isEmpty() && !fieldExCondition.isEmpty()) {
			return StringUtils.join(exCondition, "-", fieldExCondition);
		}
		if (fieldExCondition.isEmpty()) {
			return exCondition;
		}
		return fieldExCondition;
	}

	private static <T> String getExConditionField(T object, AttendanceItemLayout layout) {
		if (!layout.needCheckIDWithMethod().isEmpty()) {
			return ReflectionUtil.invoke(object.getClass(), object, layout.needCheckIDWithMethod());
		}

		return "";
	}
	
	public enum AttendanceItemType{
		DAILY_ITEM(0, "日次項目"),
		MONTHLY_ITEM(1, "月次項目");
		
		public final int value;
		public final String descript;
		
		private AttendanceItemType(int value, String descript) {
			this.value = value;
			this.descript = descript;
		}
	}
}
