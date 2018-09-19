package nts.uk.ctx.at.shared.dom.attendance.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;

import nts.gul.collection.CollectionUtil;
import nts.gul.reflection.ReflectionUtil;
import nts.gul.reflection.ReflectionUtil.Condition;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

public class AttendanceItemUtil implements ItemConst {

	public static <T extends ConvertibleAttendanceItem> List<ItemValue> toItemValues(T attendanceItems) {

		return toItemValues(attendanceItems, AttendanceItemType.DAILY_ITEM);
	}
	
	public static <T extends ConvertibleAttendanceItem> List<ItemValue> toItemValues(T attendanceItems, AttendanceItemType type) {

		return toItemValues(attendanceItems, Collections.emptyList());
	}

	public static <T extends ConvertibleAttendanceItem> List<ItemValue> toItemValues(T attendanceItems, Collection<Integer> itemIds) {

		return toItemValues(attendanceItems, itemIds, AttendanceItemType.DAILY_ITEM);
	}

	public static <T extends ConvertibleAttendanceItem> Map<T, List<ItemValue>> toItemValues(List<T> attendanceItems) {

		return toItemValues(attendanceItems, AttendanceItemType.DAILY_ITEM);
	}

	public static <T extends ConvertibleAttendanceItem> Map<T, List<ItemValue>> toItemValues(List<T> attendanceItems, AttendanceItemType type) {

		return toItemValues(attendanceItems, Collections.emptyList());
	}
	
	public static <T extends ConvertibleAttendanceItem> Map<T, List<ItemValue>> toItemValues(List<T> attendanceItems, Collection<Integer> itemIds) {

		return toItemValues(attendanceItems, itemIds, AttendanceItemType.DAILY_ITEM);
	}
	
	public static <T extends ConvertibleAttendanceItem> List<ItemValue> toItemValues(T attendanceItems, Collection<Integer> itemIds, AttendanceItemType type) {

		AttendanceItemRoot root = attendanceItems.getClass().getAnnotation(AttendanceItemRoot.class);

		if (root == null) {
			return new ArrayList<>();
		}

		return toItemValues(Arrays.asList(attendanceItems), itemIds, type).get(attendanceItems);
	}

	public static <T extends ConvertibleAttendanceItem> Map<T, List<ItemValue>> toItemValues(List<T> attendanceItems, 
			Collection<Integer> itemIds, AttendanceItemType type) {

		if(CollectionUtil.isEmpty(attendanceItems)){
			return new HashMap<>();
		}
		
		AttendanceItemRoot root = attendanceItems.get(0).getClass().getAnnotation(AttendanceItemRoot.class);

		if (root == null) {
			return new HashMap<>();
		}

		int layout = root.isContainer() ? DEFAULT_IDX : DEFAULT_NEXT_IDX;

		return getItemValues(attendanceItems, layout, EMPTY_STRING, root.isContainer() ? EMPTY_STRING : root.rootName(), 
					EMPTY_STRING, DEFAULT_IDX, getItemMap(type, itemIds, null, layout));
	}
	
	public static <T extends ConvertibleAttendanceItem> T fromItemValues(Class<T> classType, Collection<ItemValue> attendanceItems) {

		return fromItemValues(classType, attendanceItems, AttendanceItemType.DAILY_ITEM);
	}

	public static <T extends ConvertibleAttendanceItem> T fromItemValues(Class<T> classType, Collection<ItemValue> attendanceItems, AttendanceItemType type) {

		return fromItemValues(ReflectionUtil.newInstance(classType), attendanceItems);
	}

	public static <T> T fromItemValues(T attendanceItems, Collection<ItemValue> itemValues) {

		return fromItemValues(attendanceItems, itemValues, AttendanceItemType.DAILY_ITEM);
	}

	public static <T> T fromItemValues(T attendanceItems, Collection<ItemValue> itemValues, AttendanceItemType type) {
		
		if(CollectionUtil.isEmpty(itemValues)){
			return attendanceItems;
		}
		
		AttendanceItemRoot root = attendanceItems.getClass().getAnnotation(AttendanceItemRoot.class);

		if (root == null) {
			return attendanceItems;
		}

		int layout = root.isContainer() ? DEFAULT_IDX : DEFAULT_NEXT_IDX;

		Map<Integer, ItemValue> itemMap = itemValues.stream().collect(Collectors.toMap(c -> c.itemId(), c -> c));

		return fromItemValues(attendanceItems, layout, EMPTY_STRING,root.isContainer() ? EMPTY_STRING : root.rootName(), DEFAULT_IDX, false,
								getItemMap(type, itemMap.keySet(), c -> itemMap.get(c.itemId()).withPath(c.path()), layout));
	}

	private static <T> Map<T, List<ItemValue>> getItemValues(List<T> attendanceItems, int layoutIdx, String layoutCode, String path,
			String extraCondition, int index, Map<String, List<ItemValue>> groups){
		Map<Integer, T> atMap = attendanceItems.stream().collect(HashMap::new,
																(m,v)->m.put(v == null ? 0 : v.hashCode(), v), 
																HashMap::putAll);
		Map<Integer, List<ItemValue>> result = atMap.entrySet().stream().collect(Collectors.toMap(t -> t.getKey(), t -> new ArrayList<>()));
		
		getItemValues(atMap, layoutIdx, layoutCode, path, extraCondition, index, groups, result);
		
		return result.entrySet().stream().collect(HashMap::new,
													(m,v)->m.put(atMap.get(v.getKey()), v.getValue()), 
													HashMap::putAll);
	}

	@SuppressWarnings("unchecked")
	private static <T> void getItemValues(Map<Integer, T> attendanceItems, int layoutIdx, String layoutCode, String path,
														String extraCondition, int index, Map<String, List<ItemValue>> groups,
														Map<Integer, List<ItemValue>> result) {
		if(attendanceItems.isEmpty()){
			return;
		}
		T oneV = attendanceItems.values().iterator().next();
		Map<String, Field> fields = getFieldMap(oneV.getClass(), groups);

		groups.entrySet().stream().forEach(c -> {

			Field field = fields.get(c.getKey());

			if (field == null) {
				return;
			}

			AttendanceItemLayout layout = getLayoutAnnotation(field);

			boolean isList = layout.listMaxLength() > DEFAULT_IDX;

			Class<T> className = layout.isOptional() || isList ? getGenericType(field) : (Class<T>) field.getType();

			String pathName = getPath(path, layout, getRootAnnotation(field)),
					currentLayout = mergeLayout(layoutCode, layout.layout()),
					exCon = getExCondition(extraCondition, oneV, layout);

			if (isList) {
				Map<Integer, List<T>> list = getListAndSort(attendanceItems, field, className, layout);

				mapByPath(c.getValue(),
						x -> layout.listNoIndex() ? getEValAsIdxPlus(x.path()) : getIdxInText(x.path())
				).entrySet().stream().forEach(idx -> {

//					boolean isNotHaveData = list.isEmpty() || list.values().size() < idx.getKey();

					Map<Integer, T> idxValue = getItemWith(list, layout, idx.getKey(), className);

					getItemValues(fieldValue(className, idxValue, attendanceItems), layoutIdx + DEFAULT_NEXT_IDX,
										layout.listNoIndex() ? currentLayout : currentLayout + idx.getKey(),
										pathName, exCon, layout.listNoIndex() ? -1 : idx.getKey(),
										mapByPath(idx.getValue(),
												id -> getCurrentPath(layoutIdx + DEFAULT_NEXT_IDX, id.path(), false)), 
										result);
							});
				return;
			}
			Map<Integer, T> value = getOptionalFieldValue(attendanceItems, field, layout.isOptional());

			AttendanceItemValue valueAnno = getItemValueAnnotation(field);

			if (valueAnno == null) {
				getItemValues(fieldValue(className, value, attendanceItems), layoutIdx + DEFAULT_NEXT_IDX, currentLayout, pathName, exCon, index,
							mapByPath(c.getValue(), id -> getCurrentPath(layoutIdx + DEFAULT_NEXT_IDX, id.path(), false)),
							result);
				return;
			}

			String currentPath = getKey(pathName, EMPTY_STRING, false, index);

			String currentFullPath = getKey(pathName, exCon, index > DEFAULT_IDX, index);

			c.getValue().stream().filter(id -> getTextWithNoCondition(id.path()).equals(currentPath)).forEach(item -> {
				String fLayout = currentLayout + getTextWithCondition(item.path());
				if (item.path().equals(currentFullPath)) {
					result.entrySet().stream().forEach(r -> {
						r.getValue().add(ItemValue.build(item.path(), item.itemId())
													.value(value.get(r.getKey()))
													.layout(fLayout)
													.valueType(getItemValueType(attendanceItems.get(r.getKey()), valueAnno))
													.completed());
					});

				} else {
					result.entrySet().stream().forEach(r -> {
						r.getValue().add(ItemValue.build(item.path(), item.itemId())
													.layout(fLayout)
													.valueType(getItemValueType(attendanceItems.get(r.getKey()), valueAnno))
													.completed());
					});
				}
			});
		});
	}
	
	@SuppressWarnings("unchecked")
	private static <T> List<ItemValue> getItemValues(T attendanceItems, int layoutIdx, String layoutCode, String path,
														String extraCondition, int index, Map<String, List<ItemValue>> groups) {
		Map<String, Field> fields = getFieldMap(attendanceItems.getClass(), groups);

		return groups.entrySet().stream().map(c -> {

			Field field = fields.get(c.getKey());

			if (field == null) {
				return new ArrayList<ItemValue>();
			}

			AttendanceItemLayout layout = getLayoutAnnotation(field);

			boolean isList = layout.listMaxLength() > DEFAULT_IDX;

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

					T idxValue = isNotHaveData ? null : getItemWith(list, layout, idx.getKey(), className);

					return getItemValues(fieldValue(className, idxValue), layoutIdx + DEFAULT_NEXT_IDX,
										layout.listNoIndex() ? currentLayout : currentLayout + idx.getKey(),
										pathName, exCon, layout.listNoIndex() ? -1 : idx.getKey(),
										mapByPath(idx.getValue(),
												id -> getCurrentPath(layoutIdx + DEFAULT_NEXT_IDX, id.path(), false)));
							}).flatMap(List::stream).collect(Collectors.toList());
			}

			T value = getOptionalFieldValue(attendanceItems, field, layout.isOptional());

			AttendanceItemValue valueAnno = getItemValueAnnotation(field);

			if (valueAnno == null) {
				return getItemValues(
							fieldValue(className, value), layoutIdx + DEFAULT_NEXT_IDX, currentLayout, pathName, exCon, index,
							mapByPath(c.getValue(), id -> getCurrentPath(layoutIdx + DEFAULT_NEXT_IDX, id.path(), false)));
			}

			String currentPath = getKey(pathName, EMPTY_STRING, false, index);

			String currentFullPath = getKey(pathName, exCon, index > DEFAULT_IDX, index);

			return filterAndMap(
					c.getValue(),
					id -> getTextWithNoCondition(id.path()).equals(currentPath),
					item -> {
				
						if (item.path().equals(currentFullPath)) {

							return item.value(value)
									.valueType(getItemValueType(attendanceItems, valueAnno))
									.layout(currentLayout + getTextWithCondition(item.path()))
									.completed();

						} else {
							return item.layout(currentLayout + getTextWithCondition(item.path()))
									.valueType(getItemValueType(attendanceItems, valueAnno))
									.completed();
						}
					});
		}).flatMap(List::stream).collect(Collectors.toList());

	}

	@SuppressWarnings("unchecked")
	private static <T> T fromItemValues(T attendanceItems, int layoutIdx, String layoutCode, String path, int index,
										boolean needCheckWithIdx, Map<String, List<ItemValue>> groups) {
		if (attendanceItems.getClass().getAnnotation(AttendanceItemRoot.class) != null) {
			ReflectionUtil.invoke(attendanceItems.getClass(), attendanceItems, DEFAULT_MARK_DATA_FIELD);
		}

		Map<String, Field> fields = getFieldMap(attendanceItems.getClass(), groups);

		groups.entrySet().stream().forEach(c -> {
			Field field = fields.get(c.getKey());
			
			if (field == null) {
				return;
			}

			AttendanceItemLayout layout = getLayoutAnnotation(field);

			boolean isList = layout.listMaxLength() > DEFAULT_IDX;

			Class<T> className = layout.isOptional() || isList ? getGenericType(field) : (Class<T>) field.getType();

			String pathName = getPath(path, layout, getRootAnnotation(field)),
					currentLayout = mergeLayout(layoutCode, layout.layout());

			if (isList) {
				boolean listNoIdx = layout.listNoIndex();

				Field idxField = getIdxField(layout, className, listNoIdx);

				Map<Integer, List<ItemValue>> itemsForIdx = mapByPath(c.getValue(),

						x -> listNoIdx ? getEValAsIdxPlus(x.path()) : getIdxInText(x.path()));

				List<T> originalL = getOriginalList(attendanceItems, field);

				List<Integer> originalIdx = getOriginalIdx(idxField, originalL);

				List<T> list = processListToMax(originalL, layout, className,
												c.getValue().isEmpty() ? EMPTY_STRING : c.getValue().get(DEFAULT_IDX).path(),
												itemsForIdx.keySet());

				list.stream().forEach(eVal -> {
					Integer idx = idxField == null ? null : ReflectionUtil.getFieldValue(idxField, eVal);

					List<ItemValue> subList = idx == null ? null : itemsForIdx.get(idx);

					if (subList != null) {
						fromItemValues(eVal, layoutIdx + DEFAULT_NEXT_IDX,
										listNoIdx ? currentLayout : currentLayout + (idx == null ? EMPTY_STRING : idx),
										pathName, idx, needCheckWithIdx || (isList && !listNoIdx),
										mapByPath(subList, id -> getCurrentPath(layoutIdx + DEFAULT_NEXT_IDX, id.path(), false)));
						
						setValueEnumField(layout, className, eVal, subList);
					}
				});

				correctList(idxField, itemsForIdx, originalIdx,  list);

				ReflectionUtil.setFieldValue(field, attendanceItems, list);

				return;
			}

			T value = getOptionalFieldValue(attendanceItems, field, layout.isOptional());

			AttendanceItemValue valueAnno = getItemValueAnnotation(field);

			if (valueAnno != null) {
				String enumText = getEnumTextFromList(c.getValue()),
													currentPath = getKey(pathName, enumText == null ? EMPTY_STRING : enumText, needCheckWithIdx,
													index);

				ItemValue itemValue = c.getValue().stream()
						.filter(id -> id.path().equals(currentPath))
						.findFirst().orElse(null);

				if (itemValue != null) {

					if (!valueAnno.setValueWith().isEmpty()) {
						callSetMethod(attendanceItems, valueAnno, itemValue);

					} else {
						if(field.getType().isPrimitive() && itemValue.value() == null){
							return;
						}
						ReflectionUtil.setFieldValue(field, attendanceItems, itemValue.value());
					}
				}
				return;
			}

			T nVal = fromItemValues(value == null ? ReflectionUtil.newInstance(className) : value,
									layoutIdx + DEFAULT_NEXT_IDX, currentLayout, pathName, index, needCheckWithIdx,
									mapByPath(c.getValue(), id -> getCurrentPath(layoutIdx + DEFAULT_NEXT_IDX, id.path(), false)));

			setValueEnumField(layout, className, nVal, c.getValue());

			ReflectionUtil.setFieldValue(field, attendanceItems, layout.isOptional() ? Optional.of(nVal) : nVal);
		});

		return attendanceItems;
	}

	private static <T> T getItemWith(List<T> list, AttendanceItemLayout layout, int targetIdx, Class<T> className) {

		return list.stream().filter(t -> {
			Field idxField = getIdxField(layout, className, layout.listNoIndex());
			
			Integer currentIdx = ReflectionUtil.getFieldValue(idxField, t);

			return currentIdx == null ? false : currentIdx == targetIdx;
		}).findFirst().orElse(null);

	}
	
	private static <T> Map<Integer, T> getItemWith(Map<Integer, List<T>> list, AttendanceItemLayout layout, int targetIdx, Class<T> className) {

		return list.entrySet().stream().collect(HashMap::new,
												(m,v)->m.put(v.getKey(), getItemWith(v.getValue(), layout, targetIdx, className)), 
												HashMap::putAll);
	}

	private static <T> Field getIdxField(AttendanceItemLayout layout, Class<T> className, boolean listNoIdx) {

		String idxFieldName = listNoIdx ? layout.enumField() : layout.indexField();

		return idxFieldName.isEmpty() ? null : getField(idxFieldName, className);
	}

	private static <T> List<T> getOriginalList(T attendanceItems, Field field) {

		List<T> originalL = ReflectionUtil.getFieldValue(field, attendanceItems);

		return originalL == null ? new ArrayList<>() : originalL;
	}

	private static <T> void correctList(Field idxField, Map<Integer, List<ItemValue>> itemsForIdx,
											List<Integer> originalIdx, List<T> list) {
		if (idxField == null) {
			return;
		}

		list.removeIf(eVal -> {
			Object value = ReflectionUtil.getFieldValue(idxField, eVal);

			if (value == null) {
				return false;
			}

			return !(originalIdx.contains((int) value) || itemsForIdx.containsKey((int) value));
		});

	}

	private static <T> List<Integer> getOriginalIdx(Field idxField, List<T> originalL) {

		if (idxField == null) {
			return new ArrayList<>();
		}

		return originalL.stream().map(eVal -> {
			Object value = ReflectionUtil.getFieldValue(idxField, eVal);

			if (value == null) {
				return null;
			}

			return (int) value;
		}).collect(Collectors.toList());

	}

	private static <T> void callSetMethod(T attendanceItems, AttendanceItemValue valueAnno, ItemValue itemValue) {
		try {

			Method setMethod = attendanceItems.getClass().getMethod(valueAnno.setValueWith(), Object.class);
			
			setMethod.invoke(attendanceItems, itemValue.valueAsObjet());
		} catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException
				| SecurityException | InvocationTargetException e) {
			e.printStackTrace();
		}

	}

	private static <T> void clearConflictEnumsInList(AttendanceItemLayout layout, Class<T> className, List<T> value, String path) {

		if (layout.enumField().isEmpty() || !layout.removeConflictEnum() || path.isEmpty()) {
			return;
		}

		String enumText = getExConditionFromString(path);

		if (enumText.isEmpty()) {
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

		return enumValue == null ? DEFAULT_IDX : enumValue;
	}

	private static String getEnumTextFromList(List<ItemValue> c) {

		return c.stream().filter(em -> em.path().indexOf(DEFAULT_ENUM_SEPERATOR) > DEFAULT_IDX)
				.map(em -> getExConditionFromString(em.path()))
				.findFirst().orElse(null);

	}

	private static <T> void setValueEnumField(AttendanceItemLayout layout, Class<T> className, T value, List<ItemValue> items) {

		if (layout.enumField().isEmpty()) {
			return;
		}

		String enumText = items.stream().filter(em -> em.path().indexOf(DEFAULT_ENUM_SEPERATOR) > DEFAULT_IDX)
				.map(em -> getExConditionFromString(em.path())).findFirst().orElse(null);

		ReflectionUtil.setFieldValue(getField(layout.enumField(), className), value,
				AttendanceItemIdContainer.getEnumValue(enumText));
	}

	private static <T> ValueType getItemValueType(T attendanceItems, AttendanceItemValue valueAnno) {

		ValueType valueType = valueAnno.type();

		if (!valueAnno.getTypeWith().isEmpty()) {
			valueType = ReflectionUtil.invoke(attendanceItems.getClass(), attendanceItems, valueAnno.getTypeWith());
		}

		return valueType;
	}

	private static String getPath(String path, AttendanceItemLayout layout, AttendanceItemRoot root) {

		if (root != null && !root.rootName().isEmpty()) {
			return root.rootName();
		}

		return path.isEmpty() ? layout.jpPropertyName() : StringUtils.join(path, DEFAULT_SEPERATOR, layout.jpPropertyName());
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
	
	private static <T> Map<Integer, T> getOptionalFieldValue(Map<Integer, T> attendanceItems, Field field, boolean isOptional) {
		
		return attendanceItems.entrySet().stream().collect(HashMap::new,
															(m,v)->m.put(v.getKey(), getOptionalFieldValue(v.getValue(), field, isOptional)), 
															HashMap::putAll);
	}

	private static <T> T fieldValue(Class<T> className, T idxValue) {

		return idxValue == null ? ReflectionUtil.newInstance(className) : idxValue;
	}
	
	private static <T> Map<Integer, T> fieldValue(Class<T> className, Map<Integer, T> idxValue, Map<Integer, T> parent) {
		
		return parent.entrySet().stream().collect(HashMap::new,
				(m,v)-> {
					if(idxValue.get(v.getKey()) != null){
						m.put(v.getKey(), idxValue.get(v.getKey()));
					} else {
						m.put(v.getKey(), ReflectionUtil.newInstance(className));
					}
				}, HashMap::putAll);
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

	public static <T> Map<String, Field> getFieldMap(Class<T> attendanceItemClass, Map<String, List<ItemValue>> groups) {

		return ReflectionUtil.getStreamOfFieldsAnnotated(attendanceItemClass, Condition.ALL, AttendanceItemLayout.class)
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

		return StringUtils.join(pathName,
								extraCondition.isEmpty() ? EMPTY_STRING : StringUtils.join(DEFAULT_ENUM_SEPERATOR, extraCondition),
								(needCheckWithIdx ? String.valueOf(idx) : EMPTY_STRING));

	}

	private static <T> List<T> getListAndSort(T attendanceItems, Field field, Class<T> className, AttendanceItemLayout layout) {

		List<T> list = ReflectionUtil.getFieldValue(field, attendanceItems);

		if (list == null) {
			return new ArrayList<>();
		}

		if (!layout.indexField().isEmpty()) {
			return processAndSort(list, layout.listMaxLength(), className, layout.indexField());
		}

		return list;
	}

	private static <T> Map<Integer, List<T>> getListAndSort(Map<Integer, T> attendanceItems, Field field, Class<T> className, AttendanceItemLayout layout) {

		return attendanceItems.entrySet().stream().collect(HashMap::new, 
															(m, v) -> m.put(v.getKey(), getListAndSort(v.getValue(), field, className, layout)), 
															HashMap::putAll);
	}

	private static <T> List<T> processListToMax(List<T> list, AttendanceItemLayout layout, Class<T> targetClass, String path, Set<Integer> keySet) {

		list = list == null ? new ArrayList<>() : new ArrayList<>(list);

		if (layout.listNoIndex()) {

			Field enumField = getField(layout.enumField(), targetClass);

			if (enumField != null) {

				Set<Integer> notExistEnums = notExistEnum(list, keySet, enumField);

				for (Integer e : notExistEnums) {

					T nIns = ReflectionUtil.newInstance(targetClass);

					ReflectionUtil.setFieldValue(enumField, nIns, e);

					list.add(nIns);
				}
			}

			return list;
		}

		clearConflictEnumsInList(layout, targetClass, list, path);

		if (!layout.indexField().isEmpty()) {
			return processAndSort(list, layout.listMaxLength(), targetClass, layout.indexField());
		}

		for (int x = list.size(); x < layout.listMaxLength(); x++) {
			list.add(ReflectionUtil.newInstance(targetClass));
		}

		return list;
	}

	private static <T> Set<Integer> notExistEnum(List<T> list, Set<Integer> keySet, Field enumField) {

		return keySet.stream().filter(c -> !list.stream().filter(l -> c.equals((Integer) ReflectionUtil.getFieldValue(enumField, l)))
														.findFirst().isPresent())
				.collect(Collectors.toSet());
	}

	private static <T> List<T> processAndSort(List<T> list, int max, Class<T> targetClass, String idxFieldName) {

		Field idxField = getField(idxFieldName, targetClass);

		List<T> returnList = new ArrayList<>(list.stream().filter(c -> c != null).collect(Collectors.toList()));

		if (returnList.size() < max) {

			for (int x = DEFAULT_IDX; x < max; x++) {

				int index = x;

				Optional<T> idxValue = returnList.stream().filter(c -> {
					if(c == null){
						return false;
					}
					Integer idx = ReflectionUtil.getFieldValue(idxField, c);

					return idx == null ? false : idx == (index + DEFAULT_NEXT_IDX);
				}).findFirst();

				if (!idxValue.isPresent()) {
					returnList.add(createIdxFieldValue(targetClass, idxField, index + DEFAULT_NEXT_IDX));
				}
			}
		}

		Collections.sort(returnList, new Comparator<T>() {

			@Override
			public int compare(T c1, T c2) {

				Integer idx1 = ReflectionUtil.getFieldValue(idxField, c1);

				Integer idx2 = ReflectionUtil.getFieldValue(idxField, c2);

				if (idx1 == null && idx2 == null) {
					return DEFAULT_IDX;
				}

				if (idx1 == null) {
					return DEFAULT_NEXT_IDX;
				}

				if (idx2 == null) {
					return DEFAULT_MINUS;
				}

				return idx1.compareTo(idx2);
			}
		});
		
		return returnList;
	}

	public static <T> T createIdxFieldValue(Class<T> targetClass, Field idxField, int index) {

		T newValue = ReflectionUtil.newInstance(targetClass);

		ReflectionUtil.setFieldValue(idxField, newValue, index);

		return newValue;
	}

	private static String getCurrentPath(int layoutIdx, String text, boolean isList) {

		String[] layouts = text.split(Pattern.quote(DEFAULT_SEPERATOR));

		if (layouts.length <= layoutIdx) {
			return EMPTY_STRING;
		}

		String path = layouts[layoutIdx];

		if (isList) {
			return getIdx(path);
		}

		return getTextWithNoCondition(path);
	}

	private static String getIdx(String path) {

		String notIdx = getTextWithNoIdx(path);

		return path.replaceAll(notIdx, EMPTY_STRING);
	}

	@SuppressWarnings("unchecked")
	private static <T> Class<T> getGenericType(Field field) {

		ParameterizedType type = (ParameterizedType) field.getGenericType();

		return (Class<T>) type.getActualTypeArguments()[DEFAULT_IDX];
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
			return DEFAULT_IDX;
		}

		return Integer.valueOf(index);
	}

	private static String getTextWithNoIdx(String text) {

		return text.replaceAll(DEFAULT_NUMBER_REGEX, EMPTY_STRING);
	}

	private static String getTextWithNoCondition(String text) {

		return getTextWithNoIdx(text).split(DEFAULT_ENUM_SEPERATOR)[DEFAULT_IDX];
	}

	private static String getTextWithCondition(String text) {

		String[] cons = text.split(DEFAULT_ENUM_SEPERATOR);

		return cons.length > DEFAULT_NEXT_IDX ? DEFAULT_ENUM_SEPERATOR + getTextWithNoIdx(cons[DEFAULT_NEXT_IDX])
				: EMPTY_STRING;
	}

	private static String getExConditionFromString(String text) {

		String[] notIdxText = text.replaceAll(DEFAULT_NUMBER_REGEX, EMPTY_STRING).split(DEFAULT_ENUM_SEPERATOR);

		if (notIdxText.length <= DEFAULT_NEXT_IDX) {
			return EMPTY_STRING;
		}

		return notIdxText[DEFAULT_NEXT_IDX];
	}

	private static String mergeLayout(String currentLayout, String fieldLayout) {

		if (currentLayout.isEmpty()) {
			return fieldLayout;
		}

		return StringUtils.join(currentLayout, DEFAULT_LAYOUT_SEPERATOR, fieldLayout);
	}

	private static <T> String getExCondition(String exCondition, T object, AttendanceItemLayout layout) {

		String fieldExCondition = getExConditionField(object, layout);

		if (!exCondition.isEmpty() && !fieldExCondition.isEmpty()) {
			return StringUtils.join(exCondition, DEFAULT_ENUM_SEPERATOR, fieldExCondition);
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

		return EMPTY_STRING;
	}

	public enum AttendanceItemType {

		DAILY_ITEM(DEFAULT_IDX, DAILY),

		MONTHLY_ITEM(DEFAULT_NEXT_IDX, MONTHLY);

		public final int value;

		public final String descript;

		private AttendanceItemType(int value, String descript) {

			this.value = value;
			this.descript = descript;
		}

	}

}
