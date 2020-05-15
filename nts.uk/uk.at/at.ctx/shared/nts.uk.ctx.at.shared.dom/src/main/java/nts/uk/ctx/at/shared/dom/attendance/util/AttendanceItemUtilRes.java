package nts.uk.ctx.at.shared.dom.attendance.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import nts.gul.text.StringUtil;
import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceItemUtil.AttendanceItemType;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate.PropType;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;

public final class AttendanceItemUtilRes implements ItemConst {
	
	private AttendanceItemUtilRes () {}
	
	public static <T extends AttendanceItemDataGate> List<ItemValue> collect(T sources, 
			Collection<Integer> itemIds, AttendanceItemType type) {
		
		if (sources == null) {
			return new ArrayList<>();
		}
		
		List<ItemValue> result = new ArrayList<>();
		
		int layout = sources.isContainer() ? DEFAULT_IDX : DEFAULT_NEXT_IDX;
		
		Map<String, List<ItemValue>> items = AttendanceItemIdContainer.getIdMapStream(itemIds, type)
				.collect(Collectors.groupingBy(c -> 
									AttendanceItemUtil.getCurrentPath(layout, c.path(), false)));
		
		collect(result, sources, items, layout, Optional.empty(), Optional.empty());
		
		return result;
	}
	
	public static <T extends AttendanceItemDataGate> T merge(T sources, Collection<ItemValue> itemIds,
			AttendanceItemType type) {
		
		if (sources == null) {
			return sources;
		}
		
		int layout = sources.isContainer() ? DEFAULT_IDX : DEFAULT_NEXT_IDX;
		
		Map<String, List<ItemValue>> items = itemIds.stream().collect(Collectors.groupingBy(c -> {
											if (StringUtil.isNullOrEmpty(c.path(), false)) {
												c.withPath(AttendanceItemIdContainer.getPath(c.itemId(), type));
											}
											return AttendanceItemUtil.getCurrentPath(layout, c.path(), false);
										}));
		
		merge(sources, items, layout, Optional.empty(), Optional.empty());
		
		return sources;
	}
	
	private static <T extends AttendanceItemDataGate> void collect(List<ItemValue> result, T source, 
			Map<String, List<ItemValue>> items, int layout, 
			Optional<String> enumPlus, Optional<Integer> idxPlus) {
		
		items.entrySet().forEach(i -> onePropMerge(result, source, layout, enumPlus, idxPlus, i));
	}

	private static void onePropMerge(List<ItemValue> result, AttendanceItemDataGate source, int layout,
			Optional<String> enumPlus, Optional<Integer> idxPlus, Entry<String, List<ItemValue>> i) {
		String prop = i.getKey();
		PropType ct = source.typeOf(i.getKey());
		
		if (ct == PropType.VALUE) {
//			String vProp = buildPath(prop, enumPlus, idxPlus);
			source.valueOf(prop).ifPresent(v -> {
				ItemValue iv = i.getValue().get(0);
				iv.value(v.valueAsObjet()).valueType(v.type());
				result.add(iv);
			});
		} else {
			int nextLayout = layout + 1;
			
			if (ct == PropType.OBJECT) {
				AttendanceItemDataGate current = source.get(prop).orElse(null);

				if (current == null) {
					current = source.newInstanceOf(prop);
				} 

				collect(result, current, 
						groupNext(nextLayout, i.getValue()), 
						nextLayout, enumPlus, idxPlus);
			} else {
				List<AttendanceItemDataGate> listV = source.gets(prop);
				if (listV == null) {
					listV = new ArrayList<>();
				}
				Supplier<AttendanceItemDataGate> defaultGetter = () -> source.newInstanceOf(prop);
				switch (ct) {
				case IDX_LIST:
					processListIdx(result, listV, i.getValue(), nextLayout, enumPlus, defaultGetter);
					break;
				case ENUM_LIST:
					processListEnum(result, listV, i.getValue(), nextLayout, idxPlus, defaultGetter);
					break;
				case IDX_ENUM_LIST:
					processListAll(result, listV, i.getValue(), nextLayout, defaultGetter);
					break;
				case ENUM_HAVE_IDX:
					processEnumBeforeIdx(result, listV, i.getValue(), nextLayout, defaultGetter);
					break;
				case IDX_IN_ENUM:
					processIdxAfterEnum(result, listV, i.getValue(), nextLayout, defaultGetter);
					break;
				default:
					break;
				}
			}
		}
	}
	
	private static <T extends AttendanceItemDataGate> void merge(T source, 
			Map<String, List<ItemValue>> items, int layout, 
			Optional<String> enumPlus, Optional<Integer> idxPlus) {
		
		items.entrySet().forEach(i -> internalMerge(source, layout, enumPlus, idxPlus, i));
	}

	private static <T extends AttendanceItemDataGate> void internalMerge(T source, int layout,
			Optional<String> enumPlus, Optional<Integer> idxPlus, Entry<String, List<ItemValue>> i) {
		String prop = i.getKey();
		PropType ct = source.typeOf(i.getKey());
		
		if (ct == PropType.VALUE) {
			ItemValue iv = i.getValue().get(0);
			if (iv.type() == null) {
				iv.valueType(source.valueOf(prop).orElseGet(ItemValue::builder).type());
			}
			source.set(prop, iv);
			
			source.exsistData();
			
		} else {
			int nextLayout = layout + 1;
			
			if (ct == PropType.OBJECT) {
				AttendanceItemDataGate v = source.get(prop).orElseGet(() -> source.newInstanceOf(prop));
				
				if (v != null) {
					merge(v, groupNext(nextLayout, i.getValue()), nextLayout, enumPlus, idxPlus);
					
					source.set(prop, v);
					
					source.exsistData();
				}
			} else {
				List<AttendanceItemDataGate> listV = source.gets(prop);
				if (listV == null) {
					listV = new ArrayList<>();
				}
				
				Supplier<AttendanceItemDataGate> defaultGetter = () -> source.newInstanceOf(prop);
				switch (ct) {
				case IDX_LIST:
					mergeListIdx(defaultGetter, listV, i.getValue(), nextLayout, enumPlus);
					break;
				case ENUM_LIST:
					mergeListEnum(defaultGetter, listV, i.getValue(), nextLayout, idxPlus);
					break;
				case IDX_ENUM_LIST:
					mergeListAll(defaultGetter, listV, i.getValue(), nextLayout);
					break;
				case ENUM_HAVE_IDX:
					mergeEnumBeforeIdx(defaultGetter, listV, i.getValue(), nextLayout);
					break;
				case IDX_IN_ENUM:
					mergeIdxAfterEnum(defaultGetter, listV, i.getValue(), nextLayout);
					break;
				default:
					break;
				}
				
				source.set(prop, listV);
				
				source.exsistData();
			}
		}
		
		enumPlus.ifPresent(source::setEnum);
		idxPlus.ifPresent(source::setNo);
	}
	
	private static void processListIdx(List<ItemValue> result, List<AttendanceItemDataGate> listV, 
			List<ItemValue> items, int layout, Optional<String> enumPlus,
			Supplier<AttendanceItemDataGate> defaultGetter) {
		
		groupIdx(items).entrySet().forEach(g -> {
			
			int idx = g.getKey();
			
			internalProcess(result, listV, layout, g.getValue(), 
					enumPlus, Optional.of(idx), l -> l.isNo(idx), defaultGetter);
		});
	} 
	
	private static void processListEnum(List<ItemValue> result, List<AttendanceItemDataGate> listV, 
			List<ItemValue> items, int layout, Optional<Integer> idxPlus, 
			Supplier<AttendanceItemDataGate> defaultGetter) {
		
		groupEnum(items).entrySet().forEach(g -> {
			
			String enumT = g.getKey();

			internalProcess(result, listV, layout, g.getValue(), 
					Optional.of(enumT), idxPlus, l -> l.isEnum(enumT), defaultGetter);
		});
	} 
	
	private static void processListAll(List<ItemValue> result, List<AttendanceItemDataGate> listV, 
			List<ItemValue> items, int layout, Supplier<AttendanceItemDataGate> defaultGetter) {
		
		groupEnum(items).entrySet().forEach(g -> {
			
			String enumT = g.getKey().replaceAll(DEFAULT_NUMBER_REGEX, EMPTY_STRING);
			int idx = getIdx(g.getKey());
			
			internalProcess(result, listV, layout, g.getValue(), 
					Optional.of(enumT), Optional.of(idx),
					l -> l.isEnum(enumT) && l.isNo(idx), defaultGetter);
		});
	} 
	
	private static void processEnumBeforeIdx(List<ItemValue> result, List<AttendanceItemDataGate> listV, 
			List<ItemValue> items, int layout, Supplier<AttendanceItemDataGate> defaultGetter) {
		
		groupEnum(items).entrySet().forEach(g -> {
			
			String enumT = g.getKey().replaceAll(DEFAULT_NUMBER_REGEX, EMPTY_STRING);
			int idx = getIdx(g.getKey());
			
			internalProcess(result, listV, layout, g.getValue(), 
					Optional.of(enumT), Optional.of(idx), 
					l -> l.isEnum(enumT), defaultGetter);
		});
	}
	
	private static void processIdxAfterEnum(List<ItemValue> result, List<AttendanceItemDataGate> listV, 
			List<ItemValue> items, int layout, Supplier<AttendanceItemDataGate> defaultGetter) {
		
		groupEnum(items).entrySet().forEach(g -> {
			
			String enumT = g.getKey().replaceAll(DEFAULT_NUMBER_REGEX, EMPTY_STRING);
			int idx = getIdx(g.getKey());
			
			internalProcess(result, listV, layout, g.getValue(), 
					Optional.of(enumT), Optional.of(idx),
					l -> l.isNo(idx), defaultGetter);
		});
	} 

	private static void internalProcess(List<ItemValue> result, List<AttendanceItemDataGate> listV, 
			int layout, List<ItemValue> groupItems, 
			Optional<String> enumT, Optional<Integer> idx,
			Predicate<AttendanceItemDataGate> checker,
			Supplier<AttendanceItemDataGate> defaultGetter) {
		AttendanceItemDataGate current = listV.stream().filter(checker).findFirst().orElse(null);
		
		if (current == null) { 
			current = defaultGetter.get();
		} 
		collect(result, current, groupNext(layout, groupItems), layout, enumT, idx);
	} 
	
	private static void mergeListIdx(Supplier<AttendanceItemDataGate> defaultGetter,
			List<AttendanceItemDataGate> listV, List<ItemValue> items, 
			int layout, Optional<String> enumPlus) {
		
		groupIdx(items).entrySet().forEach(g -> {
			
			int idx = g.getKey();
			
			internalMergeList(defaultGetter, layout, listV, g.getValue(), 
								enumPlus, Optional.of(idx), v -> v.isNo(idx));
		});
	} 
	
	private static void mergeListEnum(Supplier<AttendanceItemDataGate> defaultGetter, List<AttendanceItemDataGate> listV, 
			List<ItemValue> items, int layout, Optional<Integer> idxPlus) {
		
		groupEnum(items).entrySet().forEach(g -> {
			
			String enumT = g.getKey();
			
			internalMergeList(defaultGetter, layout, listV, g.getValue(), 
								Optional.of(enumT), idxPlus, v -> v.isEnum(enumT));
		});
	} 
	
	private static void mergeListAll(Supplier<AttendanceItemDataGate> defaultGetter, List<AttendanceItemDataGate> listV, 
			List<ItemValue> items, int layout) {
		
		groupEnum(items).entrySet().forEach(g -> {
			
			String enumT = g.getKey().replaceAll(DEFAULT_NUMBER_REGEX, EMPTY_STRING);
			int idx = getIdx(g.getKey());
			
			internalMergeList(defaultGetter, layout, listV, g.getValue(), 
								Optional.of(enumT), Optional.of(idx), 
								v -> v.isNo(idx) && v.isEnum(enumT));
		});
	} 
	
	private static void mergeEnumBeforeIdx(Supplier<AttendanceItemDataGate> defaultGetter, List<AttendanceItemDataGate> listV, 
			List<ItemValue> items, int layout) {
		
		groupEnum(items).entrySet().forEach(g -> {
			
			String enumT = g.getKey().replaceAll(DEFAULT_NUMBER_REGEX, EMPTY_STRING);
			int idx = getIdx(g.getKey());
			
			internalMergeList(defaultGetter, layout, listV, g.getValue(), 
								Optional.of(enumT), Optional.of(idx), 
								v -> v.isEnum(enumT));
		});
	} 
	
	private static void mergeIdxAfterEnum(Supplier<AttendanceItemDataGate> defaultGetter, List<AttendanceItemDataGate> listV, 
			List<ItemValue> items, int layout) {
		
		groupEnum(items).entrySet().forEach(g -> {
			
			String enumT = g.getKey().replaceAll(DEFAULT_NUMBER_REGEX, EMPTY_STRING);
			int idx = getIdx(g.getKey());
			
			internalMergeList(defaultGetter, layout, listV, g.getValue(), 
								Optional.of(enumT), Optional.of(idx), 
								v -> v.isNo(idx));
		});
	} 
	
	private static void internalMergeList(Supplier<AttendanceItemDataGate> defaultGetter, 
			int layout, List<AttendanceItemDataGate> listV, List<ItemValue> values,
			Optional<String> enumPlus, Optional<Integer> idxPlus, 
			Predicate<AttendanceItemDataGate> checker) {
		
		AttendanceItemDataGate val = listV.stream().filter(checker::test)
				.findFirst().orElseGet(defaultGetter::get);

		if (val != null) {
			merge(val, groupNext(layout, values), layout, enumPlus, idxPlus);
			
			listV.removeIf(checker::test);
			
			listV.add(val);
			
			listV.sort((v1, v2) -> v1.getNo() - v2.getNo());
		}
	}
	
	private static String buildPath(String path, Optional<String> enumPlus, Optional<Integer> idxPlus) {
		
		return path + enumPlus.map(c -> DEFAULT_ENUM_SEPERATOR + c).orElse("") 
				+ idxPlus.map(c -> c.toString()).orElse("");
	}

	private static Map<String, List<ItemValue>> groupNext(int layout, List<ItemValue> items) {
		return items.stream().collect(Collectors.groupingBy(c -> 
							AttendanceItemUtil.getCurrentPath(layout, c.path(), false), 
							Collectors.toList()));
	}
	
	private static Map<Integer, List<ItemValue>> groupIdx(List<ItemValue> items) {
		return items.stream().collect(Collectors.groupingBy(c -> getIdx(c.path()), Collectors.toList()));
	}

	public static int getIdx(String text) {
		char char1 = text.charAt(text.length() - 1);
		char char2 = text.charAt(text.length() - 2);
		char char3 = text.charAt(text.length() - 3);
		
		if (char3 >= '0' && char3 <= '9') {
			return Character.getNumericValue(char3) * 100 
					+ Character.getNumericValue(char2) * 10 
					+ Character.getNumericValue(char1);
		} else if (char2 >= '0' && char2 <= '9'){
			return Character.getNumericValue(char2) * 10 
					+ Character.getNumericValue(char1);
		} else if (char1 >= '0' && char1 <= '9'){
			return Character.getNumericValue(char1);
		}
		
		return -1;
	}
	
	private static Map<String, List<ItemValue>> groupEnum(List<ItemValue> items) {
		return items.stream().collect(Collectors.groupingBy(c -> {
			String[] part = c.path().split(DEFAULT_ENUM_SEPERATOR);
			return part[part.length - 1];
		}));
	}
}
