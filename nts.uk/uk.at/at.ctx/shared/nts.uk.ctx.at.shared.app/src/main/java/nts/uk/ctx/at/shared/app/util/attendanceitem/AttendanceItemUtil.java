package nts.uk.ctx.at.shared.app.util.attendanceitem;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
import org.apache.log4j.Logger;

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
		return toItemValues(attendanceItems, Collections.emptyList());
	}

	public static <T extends ConvertibleAttendanceItem> List<ItemValue> toItemValues(T attendanceItems,
			List<Integer> itemIds) {
//		return toItemValues(attendanceItems, "", itemIds, 0);
		Map<String, List<Entry<String, Integer>>> groups = AttendanceItemIdContainer.getIdMapStream(itemIds)
				.collect(Collectors.groupingBy(c -> getCurrentPath(0, c.getKey(), false)));
		return getItemValues(attendanceItems, groups, 0, "", "", "", 0);
	}
	
	public static <T extends ConvertibleAttendanceItem> T toConvertibleAttendanceItem(Class<T> classType,
			List<ItemValue> attendanceItems) {
		T newObject = ReflectionUtil.newInstance(classType);
		return toConvertibleAttendanceItem(newObject, attendanceItems);
	}

	public static <T> T toConvertibleAttendanceItem(T object, List<ItemValue> attendanceItems) {
		return fromItemValues(object, attendanceItems);
	}
	
	public static <T> T fromItemValues(T attendanceItems, List<ItemValue> itemValues) {
//		return toItemValues(attendanceItems, "", itemIds, 0);
		Map<Integer, ItemValue> itemMap = itemValues.stream().collect(Collectors.toMap(c -> c.getItemId(), c -> c));
		List<Entry<String, Integer>> itemIds = AttendanceItemIdContainer.getIds(itemMap.keySet());
		Map<String, List<ItemValue>> groups = itemIds.stream().map(c -> {
			ItemValue item = itemMap.get(c.getValue());
			item.withPath(c.getKey());
			return item;
		}).collect(Collectors.groupingBy(c -> getCurrentPath(0, c.path(), false)));
		return fromItemValues(attendanceItems, groups, 0, "", "", 0, false);
	}
	

	@SuppressWarnings("unchecked")
	private static <T> List<ItemValue> getItemValues(T attendanceItems, Map<String, List<Entry<String, Integer>>> groups, 
			int layoutIdx, String layoutCode, String path, String extraCondition, int index){
		List<ItemValue> items = new ArrayList<>();
		Map<String, Field> fields = ReflectionUtil.getStreamOfFieldsAnnotated(attendanceItems.getClass(), Condition.ALL, AttendanceItemLayout.class)
				.filter(c -> groups.get(c.getAnnotation(AttendanceItemLayout.class).jpPropertyName()) != null)
				.collect(Collectors.toMap(c -> c.getAnnotation(AttendanceItemLayout.class).jpPropertyName(), c -> c));
		groups.entrySet().stream().forEach(c -> {
			Field field = fields.get(c.getKey());
			if(field != null){
				List<Entry<String, Integer>> ids = c.getValue();
				AttendanceItemLayout layout = getLayoutAnnotation(field); 
				AttendanceItemValue valueAnno = getItemValueAnnotation(field); 
				String newExCondition = getExCondition(extraCondition, attendanceItems, layout);
				T value;
				Class<T> className;
				boolean isList = layout.listMaxLength() > 0;
				String pathName = path.isEmpty() ? layout.jpPropertyName() : StringUtils.join(path, ".", layout.jpPropertyName());
				String currentLayout = mergeLayout(layoutCode, layout.layout());
				if(isList){
					List<T> list = ReflectionUtil.getFieldValue(field, attendanceItems);
//					value = list == null || list.size() <= index ? null : list.get(index);
					className = getGenericType(field);
					ids.stream().collect(Collectors.groupingBy(x -> getIndexFromString(x.getKey()))).entrySet().stream().forEach(idx -> {
						Map<String, List<Entry<String, Integer>>> subGroups = idx.getValue().stream().collect(Collectors.groupingBy(
								id -> getCurrentPath(layoutIdx + 1, id.getKey(), false)));
						T idxValue = list == null || list.size() < idx.getKey() ? null : list.get(idx.getKey() - 1);
						items.addAll(getItemValues(idxValue == null ? ReflectionUtil.newInstance(className) : idxValue, subGroups, layoutIdx + 1, 
								layout.listNoIndex() ? currentLayout : currentLayout + idx.getKey(), pathName, extraCondition, idx.getKey()));
					});
				} else {
					if(layout.isOptional()){
						Optional<T> optional = ReflectionUtil.getFieldValue(field, attendanceItems);
						value = optional == null ? null : optional.orElse(null);
						className = getGenericType(field);
					} else {
						value = ReflectionUtil.getFieldValue(field, attendanceItems);
						className = (Class<T>) field.getType();
					}
					if(valueAnno != null){
						String currentPath = getKey(pathName, "", false, index);
						List<Entry<String, Integer>> keys =ids.stream().filter(id -> getTextWithNoCondition(id.getKey()).equals(currentPath))
								.collect(Collectors.toList());
						items.addAll(keys.stream().map(id -> {
							ItemValue item = new ItemValue(valueAnno.type(), currentLayout + getTextWithCondition(id.getKey()), id.getValue());
							item.value(value);
							return item;
						}).collect(Collectors.toList()));
					} else {
						Map<String, List<Entry<String, Integer>>> subGroups = ids.stream().collect(Collectors.groupingBy(
								id -> getCurrentPath(layoutIdx + 1, id.getKey(), false)));
						items.addAll(getItemValues(value == null ? ReflectionUtil.newInstance(className) : value, 
								subGroups, layoutIdx + 1, currentLayout, pathName, newExCondition, index));
					}
				}
			}
			
		});
		
		return items;
	}
	
	@SuppressWarnings("unchecked")
	private static <T> T fromItemValues(T attendanceItems, Map<String, List<ItemValue>> groups, int layoutIdx, String layoutCode, String path,
			int index, boolean needCheckWithIdx){
		Map<String, Field> fields = ReflectionUtil.getStreamOfFieldsAnnotated(attendanceItems.getClass(), Condition.ALL, AttendanceItemLayout.class)
				.filter(c -> groups.get(c.getAnnotation(AttendanceItemLayout.class).jpPropertyName()) != null)
				.collect(Collectors.toMap(c -> c.getAnnotation(AttendanceItemLayout.class).jpPropertyName(), c -> c));
		groups.entrySet().stream().forEach(c -> {
			Field field = fields.get(c.getKey());
			if(field != null){
				List<ItemValue> ids = c.getValue();
				AttendanceItemLayout layout = getLayoutAnnotation(field); 
				AttendanceItemValue valueAnno = getItemValueAnnotation(field); 
				T value;
				Class<T> className;
				boolean isList = layout.listMaxLength() > 0;
				String pathName = path.isEmpty() ? layout.jpPropertyName() : StringUtils.join(path, ".", layout.jpPropertyName());
				String currentLayout = mergeLayout(layoutCode, layout.layout());
				if(isList){
					className = getGenericType(field);
					List<T> list = ReflectionUtil.getFieldValue(field, attendanceItems);
					list = processListToMax(list, layout.listMaxLength(), className, layout.indexField());
					Field idxField = layout.indexField().isEmpty() ? null : getField(layout.indexField(), className);
					Map<Integer, List<ItemValue>> itemsForIdx = ids.stream().collect(Collectors.groupingBy(item -> getIndexFromString(item.path())));
					list.stream().forEach(listValue -> {
						Integer idx = idxField == null ? 1 : ReflectionUtil.getFieldValue(idxField, listValue);
						List<ItemValue> subList = layout.listNoIndex() ? ids : itemsForIdx.get(idx);
						if(subList != null){
							Map<String, List<ItemValue>> subGroups = subList.stream().collect(Collectors.groupingBy(
									id -> getCurrentPath(layoutIdx + 1, id.path(), false)));
							fromItemValues(listValue, subGroups, layoutIdx + 1, 
									layout.listNoIndex() ? currentLayout : currentLayout + (idx == null ? "" : idx), pathName, idx - 1, 
											needCheckWithIdx || (isList && !layout.listNoIndex()));
							if(!layout.enumField().isEmpty()){
								String enumText = subList.stream().filter(em -> em.path().indexOf("-") > 0)
										.map(em -> getExConditionFromString(em.path())).findFirst().orElse(null);
								ReflectionUtil.setFieldValue(getField(layout.enumField(), className), 
										listValue, AttendanceItemIdContainer.getEnumValue(enumText));
							}
						}
					});
					ReflectionUtil.setFieldValue(field, attendanceItems, list);
				} else {
					if(layout.isOptional()){
						Optional<T> optional = ReflectionUtil.getFieldValue(field, attendanceItems);
						value = optional == null ? null : optional.orElse(null);
						className = getGenericType(field);
					} else {
						value = ReflectionUtil.getFieldValue(field, attendanceItems);
						className = (Class<T>) field.getType();
					}
					if(valueAnno != null){
						String enumText = ids.stream().filter(em -> em.path().indexOf("-") > 0)
								.map(em -> getExConditionFromString(em.path())).findFirst().orElse(null);
						String currentPath = getKey(pathName, enumText == null ? "" : enumText, needCheckWithIdx, index);
						ItemValue itemValue = ids.stream().filter(id -> id.path().equals(currentPath)).findFirst().orElse(null);
						if(itemValue != null){
							ReflectionUtil.setFieldValue(field, attendanceItems, itemValue.value());
						}
					} else {
						Map<String, List<ItemValue>> subGroups = ids.stream().collect(Collectors.groupingBy(
								id -> getCurrentPath(layoutIdx + 1, id.path(), false)));
						T newValue = fromItemValues(value == null ? ReflectionUtil.newInstance(className) : value, 
								subGroups, layoutIdx + 1, currentLayout, pathName, index, needCheckWithIdx);
						if(!layout.enumField().isEmpty()){
							String enumText = ids.stream().filter(em -> em.path().indexOf("-") > 0)
									.map(em -> getExConditionFromString(em.path())).findFirst().orElse(null);
							ReflectionUtil.setFieldValue(getField(layout.enumField(), className), 
									newValue, AttendanceItemIdContainer.getEnumValue(enumText));
						}
						ReflectionUtil.setFieldValue(field, attendanceItems, layout.isOptional() ? Optional.of(newValue) : newValue);
					}
				}
			}
			
		});
		
		return attendanceItems;
	}

	private static <T extends ConvertibleAttendanceItem> List<ItemValue> toItemValues(T attendanceItems,
			String rootLayout, List<Integer> itemIds, int idx) {
		AttendanceItemRoot rootAnno = getRootAnnotation(attendanceItems);
		String rootName = getRootName(rootAnno);
		if (rootAnno.isContainer()) {
			return fromContainer(attendanceItems, itemIds);
		}
		return getItemFromField(attendanceItems, rootLayout, idx, rootName, "", false, itemIds, new ArrayList<>());
	}

	@SuppressWarnings("unchecked")
	private static <T extends ConvertibleAttendanceItem> List<ItemValue> fromContainer(T attendanceItems,
			List<Integer> itemIds) {
		return getItemLayouFields(attendanceItems.getClass()).map(f -> {
			T fieldValue;
			AttendanceItemLayout layout = f.getAnnotation(AttendanceItemLayout.class);

			if (layout.listMaxLength() > 0) {
				List<T> listValue = (List<T>) ReflectionUtil.getFieldValue(f, attendanceItems);
				initNewList(f, layout, listValue);
				List<ItemValue> result = new ArrayList<>();
				for (int x = 0; x < listValue.size(); x++) {
					result.addAll(
							toItemValues(listValue.get(x), layout == null ? "" : layout.layout() + x, itemIds, 0));
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

	

	private static <T extends ConvertibleAttendanceItem, R extends ConvertibleAttendanceItem> T toConvertibleAttendanceItem(
			T object, List<ItemValue> attendanceItems, int layoutIdx) {
		AttendanceItemRoot rootAnno = getRootAnnotation(object);
		String rootName = getRootName(rootAnno);
		if (rootAnno.isContainer()) {
			return processContainer(object, attendanceItems);
		}

		return mergeToObject(object, attendanceItems, layoutIdx, 0, rootName, "", false, new ArrayList<>());
	}

	private static <T extends ConvertibleAttendanceItem> List<T> initNewList(Field f, AttendanceItemLayout layout,
			List<T> oldList) {
		if (oldList == null) {
			oldList = new ArrayList<>();
		}
		Class<T> listGenericType = getGenericType(f);
		int start = (oldList == null || oldList.isEmpty()) ? 0 : oldList.size();
		for (int i = start; i < layout.listMaxLength(); i++) {
			T newValue = ReflectionUtil.newInstance(listGenericType);
			if (!layout.indexField().isEmpty()) {
				ReflectionUtil.setFieldValue(getField(layout.indexField(), listGenericType), newValue, i);
			}
			oldList.add(newValue);
		}
		return oldList;
	}

	private static <T extends ConvertibleAttendanceItem, R extends ConvertibleAttendanceItem> T processContainer(
			T object, List<ItemValue> attendanceItems) {
		Map<String, Field> fieldMap = toFieldMapByLayout(object);

		groupMapLayout(attendanceItems, 0, false).entrySet().stream().forEach(group -> {
			Field field = fieldMap.get(group.getKey());
			if (field != null) {
				AttendanceItemLayout layout = getLayoutAnnotation(field);
				if (layout.listMaxLength() > 0) {
					processListProperty(object, group, field);
				} else {
					R fieldInstance = toConvertibleAttendanceItem(getInstance(object, field, layout.isOptional()),
							group.getValue(), 1);
					ReflectionUtil.setFieldValue(field, object,
							layout.isOptional() ? Optional.of(fieldInstance) : fieldInstance);
				}
			}
		});
		return object;
	}

	private static <R extends ConvertibleAttendanceItem, T extends ConvertibleAttendanceItem> R getInstance(T object,
			Field field, boolean isOptional) {
		if (isOptional) {
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
		// if (!listGroup.isEmpty()) {
		AttendanceItemLayout layout = getLayoutAnnotation(field);
		List<R> value = ReflectionUtil.getFieldValue(field, object);
		// TODO: xu ly for multi index field
		initNewList(field, layout, value);
		ReflectionUtil.setFieldValue(field, object, getList(value, listGroup, layout.listMaxLength()));
		// }
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
			String rootName, String extraCondition, boolean needCheckWithIdx, List<String> subExConditions) {
		Map<String, Field> fieldMap = toFieldMapByLayout(object);

		groupMapLayout(attendanceItems, layoutIdx, false).entrySet().stream().forEach(group -> {
			Field field = fieldMap.get(group.getKey());
			if (field != null) {
				processGroup(object, layoutIdx, field, group.getValue(), rootName, idx, extraCondition,
						needCheckWithIdx, subExConditions);
			}
		});

		return object;
	}

	private static <T> void processGroup(T object, int layoutIdx, Field field, List<ItemValue> values, String path,
			int idx, String extraCondition, boolean checkWithIdx, List<String> subExConditions) {
		AttendanceItemLayout layout = getLayoutAnnotation(field);
		if (layout != null) {
			String newPathName = StringUtils.join(path, ".", layout.jpPropertyName());
			String newExCondition = getExCondition(extraCondition, object, layout);
			subExConditions.addAll(getSubExCondition(extraCondition, object, layout));
			boolean needCheckWIthIdx = checkWithIdx || layout.needCheckIDWithIndex();
			if (layout.listMaxLength() > 0) {
				validateFieldList(field);
				ReflectionUtil.setFieldValue(field, object, processFieldList(field, object, layout, values, layoutIdx,
						getGenericType(field), newPathName, newExCondition, needCheckWIthIdx, subExConditions));
			} else if (isItemValue(field)) {
				setFieldItemValue(object, values, field, newPathName, newExCondition, needCheckWIthIdx, idx,
						subExConditions, layout.needCheckIDWithMethod());
			} else {
				T value = ReflectionUtil.getFieldValue(field, object);
				if (value == null) {
					ReflectionUtil.setFieldValue(field, object, mergeToClass(field.getType(), values, layoutIdx + 1,
							idx, newPathName, newExCondition, needCheckWIthIdx, subExConditions));
				} else {
					ReflectionUtil.setFieldValue(field, object, mergeToObject(value, values, layoutIdx + 1, idx,
							newPathName, newExCondition, needCheckWIthIdx, subExConditions));
				}
			}
		}
	}

	private static <T> List<T> processFieldList(Field f, T object, AttendanceItemLayout layout,
			List<ItemValue> attendanceItems, int layoutIdx, Class<T> classType, String pathName, String extraCondition,
			boolean needCheckWithIdx, List<String> subExConditions) {
		List<T> value = getNotNullListValue(f, object);
		Map<String, List<ItemValue>> listGroup = groupMapLayout(attendanceItems, layoutIdx, true);
		String idxFieldName = layout.indexField();
		boolean isIndexField = !idxFieldName.isEmpty();
		processListToMax(value, layout.listMaxLength(), classType, idxFieldName);
		Field idxField = isIndexField ? getField(idxFieldName, classType) : null;
		return IntStream.range(0, layout.listMaxLength()).mapToObj(idx -> {
			List<ItemValue> values = listGroup.get(String.valueOf(idx));
			T v = value.get(idx);
			if (isIndexField) {
				ReflectionUtil.setFieldValue(idxField, v, idx + 1);
			}
			if (values != null) {
				// TODO: for multiple index (current, not need)
				return mergeToObject(v, values, layoutIdx + 1, idx, pathName, extraCondition, needCheckWithIdx,
						subExConditions);
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

	private static <T> void setFieldItemValue(T object, List<ItemValue> attendanceItems, Field field, String pathName,
			String extraCondition, boolean needCheckWithIdx, int idx, List<String> subExConditions,
			String enumSetMethod) {
		List<Integer> itemIds = getItemIds(getItemValueAnnotation(field), pathName, extraCondition, needCheckWithIdx,
				idx);
		if (!itemIds.isEmpty()) {
			Object value = attendanceItems.stream().filter(ivl -> itemIds.contains(ivl.getItemId())).findFirst()
					.orElseThrow(() -> new RuntimeException("Item Id is not consistent")).value();
			ReflectionUtil.setFieldValue(field, object, value);
		} else {
			subExConditions.stream().forEach(c -> {
				List<Integer> subItemIds = getItemIds(getItemValueAnnotation(field), pathName, c, needCheckWithIdx,
						idx);
				ItemValue iValue = attendanceItems.stream().filter(ivl -> subItemIds.contains(ivl.getItemId()))
						.findFirst().orElse(null);
				if (iValue != null && iValue.value() != null) {
					setEnumField(object, enumSetMethod, c);
				}
			});
		}

	}

	private static <T> void setEnumField(T object, String enumSetMethod, String c) {
		try {
			Method setEnum = object.getClass().getMethod(enumSetMethod, String.class);
			setEnum.invoke(object, c);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static List<Integer> getItemIds(AttendanceItemValue itemValueAnno, String pathName, String extraCondition,
			boolean needCheckWithIdx, int idx) {
		Integer itemId = AttendanceItemIdContainer.getId(getKey(pathName, extraCondition, needCheckWithIdx, idx));
		if (itemId != null && itemId >= 0) {
			return Arrays.asList(itemId);
		}

		return Collections.emptyList();
	}

	private static String getKey(String pathName, String extraCondition, boolean needCheckWithIdx, int idx) {
		return StringUtils.join(pathName, extraCondition.isEmpty() ? "" : StringUtils.join("-", extraCondition),
				(needCheckWithIdx ? String.valueOf(idx + 1) : ""));
	}

	private static <T> List<T> processListToMax(List<T> list, int max, Class<T> targetClass, String idxFieldName) {
		// TODO: check result list
		list = list == null ? new ArrayList<>() : new ArrayList<>(list);
		if (!idxFieldName.isEmpty()) {
			Field idxField = getField(idxFieldName, targetClass);
			for (int x = 0; x < max; x++) {
				int index = x;
				Optional<T> idxValue = list.stream().filter(c -> {
					Integer idx = ReflectionUtil.getFieldValue(idxField, c);
					return idx == null ? false : idx == (index + 1);
				}).findFirst();
				if(!idxValue.isPresent()){
					T newValue = ReflectionUtil.newInstance(targetClass);
					ReflectionUtil.setFieldValue(idxField, newValue, index + 1);
					list.add(newValue);
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
		} else {
			for (int x = list.size(); x < max; x++) {
				list.add(ReflectionUtil.newInstance(targetClass));
			}
		}
		return list;
	}

	private static <T> List<T> getNotNullListValue(Field f, T object) {
		List<T> value = ReflectionUtil.getFieldValue(f, object);
		if (value == null) {
			value = new ArrayList<>();
		}
		return value;
	}

	private static <T> T mergeToClass(Class<T> classType, List<ItemValue> attendanceItems, int layoutIdx, int idx,
			String pathName, String extraCondition, boolean needCheckWithIdx, List<String> subExConditions) {
		return mergeToObject(ReflectionUtil.newInstance(classType), attendanceItems, layoutIdx, idx, pathName,
				extraCondition, needCheckWithIdx, subExConditions);
	}

	private static List<ItemValue> getItemFromField(Object attendanceItems, String currentLayout, int idx,
			String rootName, String extraCondition, boolean needCheckWithIdx, List<Integer> itemIds,
			List<String> newSubExCondition) {

		return getItemLayouFields(attendanceItems.getClass()).map(field -> {
			return processProperty(field, attendanceItems, currentLayout, idx, rootName, extraCondition,
					needCheckWithIdx, itemIds, newSubExCondition);
		}).flatMap(List::stream).collect(Collectors.toList());
	}

	@SuppressWarnings("unchecked")
	private static <T> List<ItemValue> processProperty(Field field, Object attendanceItems, String currentLayout,
			int idx, String pathName, String extraCondition, boolean needCheckWithIdx, List<Integer> itemIds,
			List<String> newSubExCondition) {
		T value = ReflectionUtil.getFieldValue(field, attendanceItems);
		AttendanceItemLayout layout = getLayoutAnnotation(field);
		String newPathName = StringUtils.join(pathName, ".", layout.jpPropertyName());
		String newExCondition = getExCondition(extraCondition, attendanceItems, layout);
		newSubExCondition.addAll(getSubExCondition(extraCondition, attendanceItems, layout));
		needCheckWithIdx = needCheckWithIdx || layout.needCheckIDWithIndex();
		if (layout.listMaxLength() > 0) {
			List<T> values = value == null ? new ArrayList<>() : (List<T>) value;

			if (values.size() < layout.listMaxLength()) {
				Class<T> className = getGenericType(field);
				Field idxField = layout.indexField().isEmpty() ? null
						: getField(layout.indexField(), className);
				IntStream.range(values.size(), layout.listMaxLength()).forEach(c -> {
					if (!className.equals(Integer.class)) {
						T newInstance = ReflectionUtil.newInstance(className);
						if (idxField != null) {
							ReflectionUtil.setFieldValue(idxField, newInstance, c + 1);
						}
						values.add(newInstance);
					} else {
						values.add(null);
					}
				});
			}
			return processList(currentLayout, layout.layout(), values, newPathName, newExCondition, needCheckWithIdx,
					itemIds, newSubExCondition);
		}
		if (value == null) {
			if (!field.getType().equals(Integer.class)) {
				value = ReflectionUtil.newInstance(field.getType());
			}
		}
		return processOne(currentLayout, field, layout.layout(), value, idx, newPathName, newExCondition,
				needCheckWithIdx, itemIds, newSubExCondition);
	}

	private static <T> List<ItemValue> processOne(String currentLayout, Field field, String layoutCode, T value,
			int idx, String pathName, String extraCondition, boolean needCheckWithIdx, List<Integer> itemIds,
			List<String> newSubExCondition) {
		if (isItemValue(field)) {
			return newItemValue(value, currentLayout, field, layoutCode, idx, pathName, extraCondition,
					needCheckWithIdx, itemIds, newSubExCondition);
		} else {
			return getItemFromField(value, mergeLayout(currentLayout, layoutCode), idx, pathName, extraCondition,
					needCheckWithIdx, itemIds, newSubExCondition);
		}
	}

	private static <T> List<ItemValue> processList(String currentLayout, String layoutCode, List<T> values,
			String pathName, String extraCondition, boolean needCheckWithIdx, List<Integer> itemIds,
			List<String> newSubExCondition) {
		return IntStream.range(0, values.size()).mapToObj(idx -> {
			return getItemFromField(values.get(idx), mergeLayout(currentLayout, layoutCode + idx), idx, pathName,
					extraCondition, needCheckWithIdx, itemIds, newSubExCondition);
		}).flatMap(List::stream).collect(Collectors.toList());
	}

	private static <T> List<ItemValue> newItemValue(T value, String currentLayout, Field field, String layoutCode,
			int idx, String pathName, String extraCondition, boolean needCheckWithIdx, List<Integer> onNeedItemIds,
			List<String> newSubExCondition) {
		AttendanceItemValue valueType = getItemValueAnnotation(field);
		List<Integer> itemIds = getItemIds(valueType, pathName, extraCondition, needCheckWithIdx, idx);
		Map<String, Integer> subItems = newSubExCondition.stream().filter(c -> !c.equals(extraCondition)).distinct()
				.collect(Collectors.toMap(c -> c, c -> {
					List<Integer> x = getItemIds(valueType, pathName, c, needCheckWithIdx, idx);
					return x.isEmpty() ? -1 : x.get(0);
				}));
		List<ItemValue> result = new ArrayList<>();
		if (!itemIds.isEmpty()) {
			int itemId = itemIds.get(0);

			if (onNeedItemIds.isEmpty() || onNeedItemIds.contains(itemId)) {
				Logger.getLogger(AttendanceItemUtil.class)
						.info(pathName + " - " + mergeLayout(currentLayout, layoutCode) + " - " + itemId);
				ItemValue itemValue = new ItemValue(valueType.type(), mergeLayout(currentLayout, layoutCode), itemId);
				itemValue.value(value);
				result.add(itemValue);
			}
		}
		result.addAll(subItems.entrySet().stream().filter(c -> c.getValue() >= 0).map(c -> {
			if (c.getValue() != null && (onNeedItemIds.isEmpty() || onNeedItemIds.contains(c.getValue()))) {
				Logger.getLogger(AttendanceItemUtil.class)
						.info(pathName + " - " + mergeLayout(currentLayout, layoutCode) + " - " + c.getValue());
				return new ItemValue(valueType.type(), mergeLayout(currentLayout, layoutCode + c.getKey()),
						c.getValue());
			}
			return null;
		}).filter(c -> c != null).collect(Collectors.toList()));
		return result.stream().distinct().collect(Collectors.toList());
	}

	@SuppressWarnings("unchecked")
	private static <T> Class<T> getGenericType(Field field) {
		ParameterizedType type = (ParameterizedType) field.getGenericType();
		return (Class<T>) type.getActualTypeArguments()[0];
	}

	private static Map<String, List<ItemValue>> groupMapLayout(List<ItemValue> attendanceItems, int layoutIdx,
			boolean isList) {
		return attendanceItems.stream().collect(Collectors.groupingBy(c -> {
			return getCurrentLayout(layoutIdx, c.getLayoutCode(), isList);
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

	private static String getCurrentLayout(int layoutIdx, String text, boolean isList) {
		String[] layouts = text.split("_");
		if (layouts.length <= layoutIdx) {
			return "";
		}
		String firstlayout = layouts[layoutIdx];
		if (isList) {
			String notIdx = firstlayout.replaceAll("[0-9]+$", "");
			return firstlayout.replaceAll(notIdx, "");
		}
		return firstlayout.replaceAll("[0-9]+$", "");
	}
	
	private static String getCurrentPath(int layoutIdx, String text, boolean isList) {
		String[] layouts = text.split("\\.");
		if (layouts.length <= layoutIdx) {
			return "";
		}
		String firstlayout = layouts[layoutIdx];
		if (isList) {
			String notIdx = firstlayout.replaceAll("[0-9]+$", "");
			return firstlayout.replaceAll(notIdx, "");
		}
		return firstlayout.replaceAll("[0-9]+$", "").split("-")[0];
	}
	
	private static int getIndexFromString(String text) {
		String notIdx = text.replaceAll("[0-9]+$", "");
		String index = text.replaceAll(notIdx, "");
		if(index.isEmpty()){
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
		if(notIdxText.length < 2){
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
		if (fieldExCondition.isEmpty()) {
			return exCondition;
		}
		return fieldExCondition;
	}

	private static <T> List<String> getSubExCondition(String exCondition, T object, AttendanceItemLayout layout) {
		List<String> fieldExCondition = getSubExConditionField(object, layout);
		if (fieldExCondition != null) {
			return fieldExCondition.stream().map(c -> exCondition.isEmpty() ? c : StringUtils.join(exCondition, "-", c))
					.collect(Collectors.toList());
		}
		return new ArrayList<>();
	}

	private static String getRootName(AttendanceItemRoot rootAnno) {
		return rootAnno == null ? "" : rootAnno.rootName();
	}

	private static <T> List<String> getSubExConditionField(T object, AttendanceItemLayout layout) {
		if (!layout.methodForEnumValues().isEmpty()) {
			return ReflectionUtil.invoke(object.getClass(), object, layout.methodForEnumValues());
		}

		return null;
	}

	private static <T> String getExConditionField(T object, AttendanceItemLayout layout) {
		if (!layout.needCheckIDWithMethod().isEmpty()) {
			return ReflectionUtil.invoke(object.getClass(), object, layout.needCheckIDWithMethod()).toString();
		}
//		if (!layout.needCheckIDWithField().isEmpty()) {
//			Field refeField;
//			try {
//				refeField = object.getClass().getField(layout.needCheckIDWithField());
//				return ReflectionUtil.getFieldValue(refeField, object).toString();
//			} catch (NoSuchFieldException | SecurityException e) {
//				e.printStackTrace();
//			}
//		}

		return "";
	}
}
