package nts.uk.ctx.at.shared.dom.attendance.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
	
	public static <T extends AttendanceItemDataGate> T merge(T sources, Collection<ItemValue> itemIds) {
		
		if (sources == null) {
			return sources;
		}
		
		int layout = sources.isContainer() ? DEFAULT_IDX : DEFAULT_NEXT_IDX;
		
		Map<String, List<ItemValue>> items = itemIds.stream().collect(Collectors.groupingBy(c -> 
										AttendanceItemUtil.getCurrentPath(layout, c.path(), false)));
		
		merge(sources, items, layout, Optional.empty(), Optional.empty());
		
		return sources;
	}
	
	private static <T extends AttendanceItemDataGate> void collect(List<ItemValue> result, T source, 
			Map<String, List<ItemValue>> items, int layout, 
			Optional<String> enumPlus, Optional<Integer> idxPlus) {
		
		items.entrySet().forEach(i -> onePropMerge(result, source, layout, enumPlus, idxPlus, i));
	}

	private static <T extends AttendanceItemDataGate> void onePropMerge(List<ItemValue> result, T source, int layout,
			Optional<String> enumPlus, Optional<Integer> idxPlus, Entry<String, List<ItemValue>> i) {
		String prop = i.getKey();
		PropType ct = source.typeOf(i.getKey());
		
		if (ct == PropType.VALUE) {
			String vProp = buildPath(prop, enumPlus, idxPlus);
			source.valueOf(vProp).ifPresent(v -> {
				ItemValue iv = i.getValue().get(0);
				iv.value(v.valueAsObjet()).valueType(v.getValueType());
				result.add(iv);
			});
		} else {
			int nextLayout = layout + 1;
			
			if (ct == PropType.OBJECT) {
				source.get(prop).ifPresent(v -> 
					
					collect(result, v, 
							groupNext(nextLayout, i.getValue()), 
							nextLayout, enumPlus, idxPlus)
				);
			} else {
				List<AttendanceItemDataGate> listV = source.gets(prop);
				switch (ct) {
				case IDX_LIST:
					processListIdx(result, listV, i.getValue(), nextLayout, enumPlus);
					break;
				case ENUM_LIST:
					processListEnum(result, listV, i.getValue(), nextLayout, idxPlus);
					break;
				case IDX_ENUM_LIST:
					processListAll(result, listV, i.getValue(), nextLayout);
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
		
		items.entrySet().forEach(i -> {

			String prop = i.getKey();
			PropType ct = source.typeOf(i.getKey());
			
			if (ct == PropType.VALUE) {
				source.set(buildPath(prop, enumPlus, idxPlus), i.getValue().get(0));
				
			} else {
				int nextLayout = layout + 1;
				
				if (ct == PropType.OBJECT) {
					AttendanceItemDataGate v = source.get(prop).orElseGet(() -> source.newInstanceOf(prop));
					
					if (v != null) {
						merge(v, groupNext(nextLayout, i.getValue()), nextLayout, enumPlus, idxPlus);
						
						source.set(prop, v);
					}
				} else {
					List<AttendanceItemDataGate> listV = source.gets(prop);
					switch (ct) {
					case IDX_LIST:
						mergeListIdx(source, prop, listV, i.getValue(), nextLayout, enumPlus);
						break;
					case ENUM_LIST:
						mergeListEnum(source, prop, listV, i.getValue(), nextLayout, idxPlus);
						break;
					case IDX_ENUM_LIST:
						mergeListAll(source, prop, listV, i.getValue(), nextLayout);
						break;
					default:
						break;
					}
					
					source.set(prop, listV);
				}
			}
			
			enumPlus.ifPresent(source::setEnum);
			idxPlus.ifPresent(source::setNo);
		});
	}
	
	private static void processListIdx(List<ItemValue> result, List<AttendanceItemDataGate> listV, 
			List<ItemValue> items, int layout, Optional<String> enumPlus) {
		
		groupIdx(items).entrySet().forEach(g -> {
			
			int idx = g.getKey();
			
			listV.stream().filter(l -> l.isNo(idx)).findFirst().ifPresent(c -> 
				collect(result, c, groupNext(layout, g.getValue()), layout, enumPlus, Optional.of(idx))
			);
		});
	} 
	
	private static void processListEnum(List<ItemValue> result, List<AttendanceItemDataGate> listV, 
			List<ItemValue> items, int layout, Optional<Integer> idxPlus) {
		
		groupEnum(items).entrySet().forEach(g -> {
			
			String enumT = g.getKey();
			
			listV.stream().filter(l -> l.isEnum(enumT)).findFirst().ifPresent(c -> 
				collect(result, c, groupNext(layout, g.getValue()), layout, Optional.of(enumT), idxPlus)
			);
		});
	} 
	
	private static void processListAll(List<ItemValue> result, List<AttendanceItemDataGate> listV, 
			List<ItemValue> items, int layout) {
		
		groupEnum(items).entrySet().forEach(g -> {
			
			String enumT = g.getKey().replaceAll(DEFAULT_NUMBER_REGEX, EMPTY_STRING);
			int idx = getIdx(g.getKey());
			
			listV.stream().filter(l -> l.isEnum(enumT) && l.isNo(idx)).findFirst().ifPresent(c -> 
				collect(result, c, 
						groupNext(layout, g.getValue()), 
						layout, Optional.of(enumT), Optional.of(idx))
			);
		});
	} 
	
	private static void mergeListIdx(AttendanceItemDataGate source, String prop,
			List<AttendanceItemDataGate> listV, List<ItemValue> items, 
			int layout, Optional<String> enumPlus) {
		
		groupIdx(items).entrySet().forEach(g -> {
			
			int idx = g.getKey();
			
			internalMergeList(source, layout, prop, listV, g.getValue(), 
								enumPlus, Optional.of(idx), v -> v.isNo(idx));
		});
	} 
	
	private static void mergeListEnum(AttendanceItemDataGate source, String prop, List<AttendanceItemDataGate> listV, 
			List<ItemValue> items, int layout, Optional<Integer> idxPlus) {
		
		groupEnum(items).entrySet().forEach(g -> {
			
			String enumT = g.getKey();
			
			internalMergeList(source, layout, prop, listV, g.getValue(), 
								Optional.of(enumT), idxPlus, v -> v.isEnum(enumT));
		});
	} 
	
	private static void mergeListAll(AttendanceItemDataGate source, String prop, List<AttendanceItemDataGate> listV, 
			List<ItemValue> items, int layout) {
		
		groupEnum(items).entrySet().forEach(g -> {
			
			String enumT = g.getKey().replaceAll(DEFAULT_NUMBER_REGEX, EMPTY_STRING);
			int idx = getIdx(g.getKey());
			
			internalMergeList(source, layout, prop, listV, g.getValue(), 
								Optional.of(enumT), Optional.of(idx), 
								v -> v.isNo(idx) && v.isEnum(enumT));
		});
	} 
	
	private static void internalMergeList(AttendanceItemDataGate source, int layout, String prop,
			List<AttendanceItemDataGate> listV, List<ItemValue> values,
			Optional<String> enumPlus, Optional<Integer> idxPlus, 
			Predicate<AttendanceItemDataGate> checker) {
		
		AttendanceItemDataGate val = listV.stream().filter(checker::test)
				.findFirst().orElseGet(() -> source.newInstanceOf(prop));

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
							AttendanceItemUtil.getCurrentPath(layout, c.path(), false)));
	}
	
	private static Map<Integer, List<ItemValue>> groupIdx(List<ItemValue> items) {
		return items.stream().collect(Collectors.groupingBy(c -> getIdx(c.path())));
	}

	private static int getIdx(String text) {
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
