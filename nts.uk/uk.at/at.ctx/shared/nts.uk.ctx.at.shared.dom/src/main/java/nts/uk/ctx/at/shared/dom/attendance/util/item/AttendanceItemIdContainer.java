package nts.uk.ctx.at.shared.dom.attendance.util.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceItemUtil.AttendanceItemType;
import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceItemUtilRes;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.enu.DailyDomainGroup;
import nts.uk.ctx.at.shared.dom.attendance.util.enu.MonthlyDomainGroup;

public class AttendanceItemIdContainer implements ItemConst {

	private final static Map<String, Integer> ENUM_CONTAINER;
	
	static {
		ENUM_CONTAINER = new HashMap<>();
		ENUM_CONTAINER.put(E_WORK_REF, 0);
		ENUM_CONTAINER.put(E_SCHEDULE_REF, 1);
		ENUM_CONTAINER.put(E_CHILD_CARE, 0);
		ENUM_CONTAINER.put(E_CARE, 1);
		ENUM_CONTAINER.put(E_DAY_WORK, 0);
		ENUM_CONTAINER.put(E_NIGHT_WORK, 1);
		ENUM_CONTAINER.put(joinNS(LEGAL, HOLIDAY_WORK), 0);
		ENUM_CONTAINER.put(joinNS(ILLEGAL, HOLIDAY_WORK), 1);
		ENUM_CONTAINER.put(joinNS(PUBLIC_HOLIDAY, HOLIDAY_WORK), 0);
		ENUM_CONTAINER.put(E_SUPPORT, 0);
		ENUM_CONTAINER.put(E_UNION, 1);
		ENUM_CONTAINER.put(E_CHARGE, 2);
		ENUM_CONTAINER.put(E_OFFICAL, 3);
		ENUM_CONTAINER.put(E_OFF_BEFORE_BIRTH, 0);
		ENUM_CONTAINER.put(E_OFF_AFTER_BIRTH, 1);
		ENUM_CONTAINER.put(E_OFF_CHILD_CARE, 2);
		ENUM_CONTAINER.put(E_OFF_CARE, 3);
		ENUM_CONTAINER.put(E_OFF_INJURY, 4);
	}
	public static <V> Map<String, List<V>> groupItemByDomain(Collection<V> itemIds, Function<V, 
			Integer> getId, boolean monthly) {
		Map<String, List<V>> groups = new HashMap<>();
		
		Map<Integer, ItemValue> container = monthly ? getMonthlyItems() : getDailyItems();
		
		itemIds.stream().forEach(i -> {
			String itemPath = container.get(getId.apply(i)).path();
			if (itemPath != null) {
				String[] group = itemPath.split(Pattern.quote(DEFAULT_SEPERATOR));
				if (groups.get(group[0]) == null) {
					List<V> ids = new ArrayList<>();
					ids.add(i);
					groups.put(group[0], ids);
				} else {
					groups.get(group[0]).add(i);
				}
			}
		});
		
		return groups;
	}
	
	public static List<Integer> getItemIdByDailyDomains(DailyDomainGroup... domains){
		return Arrays.stream(domains).map(e -> {
			return getDailyItems().entrySet().stream()
										.filter(en -> en.getValue().path().indexOf(e.name) == 0)
										.map(en -> en.getKey()).collect(Collectors.toList());
		}).flatMap(List::stream).collect(Collectors.toList());
	}
	
	public static List<Integer> getItemIdByDailyDomains(Collection<DailyDomainGroup> domains){
		return domains.stream().map(e -> {
			return getDailyItems().entrySet().stream()
										.filter(en -> en.getValue().path().indexOf(e.name) == 0)
										.map(en -> en.getKey()).collect(Collectors.toList());
		}).flatMap(List::stream).collect(Collectors.toList());
	}
	
	public static List<Integer> getItemIdByDailyDomains(Collection<DailyDomainGroup> domains, 
			BiFunction<DailyDomainGroup, String, Boolean> customCondition){
		return domains.stream().map(e -> {
			return getDailyItems().entrySet().stream()
										.filter(en -> en.getValue().path().indexOf(e.name) == 0)
										.filter(en -> customCondition.apply(e, en.getValue().path()))
										.map(en -> en.getKey()).collect(Collectors.toList());
		}).flatMap(List::stream).collect(Collectors.toList());
	}
	
	public static List<Integer> getItemIdByDailyDomains(DailyDomainGroup domain, 
			BiFunction<DailyDomainGroup, String, Boolean> customCondition){
		
		return getDailyItems().entrySet().stream()
									.filter(en -> en.getValue().path().indexOf(domain.name) == 0)
									.filter(en -> customCondition.apply(domain, en.getValue().path()))
									.map(en -> en.getKey()).collect(Collectors.toList());
	}
	
	public static List<Integer> getItemIdByMonthlyDomains(MonthlyDomainGroup... domains){
		return Arrays.stream(domains).map(e -> {
			return getMonthlyItems().entrySet().stream()
											.filter(en -> en.getValue().path().indexOf(e.name) == 0)
											.map(en -> en.getKey()).collect(Collectors.toList());
		}).flatMap(List::stream).collect(Collectors.toList());
	}
	
	public static List<Integer> getItemIdByMonthlyDomains(Collection<MonthlyDomainGroup> domains){
		return domains.stream().map(e -> {
			return getMonthlyItems().entrySet().stream()
											.filter(en -> en.getValue().path().indexOf(e.name) == 0)
											.map(en -> en.getKey()).collect(Collectors.toList());
		}).flatMap(List::stream).collect(Collectors.toList());
	}
	
	public static boolean isHaveOptionalItems(Collection<ItemValue> items) {
		return getOptionalStream(items).findFirst().isPresent();
	}
	
	public static List<ItemValue> filterOptionalItems(Collection<ItemValue> items) {
		return getOptionalStream(items).collect(Collectors.toList());
	}
	
	public static Map<ItemValue, Integer> mapOptionalItemsToNos(Collection<ItemValue> items) {
		return getOptionalStream(items).collect(Collectors.toMap(i -> i, i -> {
			return Integer.parseInt(i.path().replace(i.path().replaceAll(DEFAULT_NUMBER_REGEX, EMPTY_STRING), EMPTY_STRING));
		}));
	}
	
	public static Map<Integer, Integer> mapOptionalItemIdsToNos(Collection<ItemValue> items) {
		return getOptionalStream(items).collect(Collectors.toMap(i -> i.itemId(), i -> {
			return AttendanceItemUtilRes.getIdx(i.path());
		}));
	}
	
	public static Map<Integer, Integer> mapOptionalItemIdsToNos() {
		return mapDailyOptionalItemIdsToNos();
	}
	
	public static Map<Integer, Integer> mapOptionalItemIdsToNos(AttendanceItemType type) {
		if(type == AttendanceItemType.MONTHLY_ITEM){
			return mapMonthlyOptionalItemIdsToNos();
		}
		return mapDailyOptionalItemIdsToNos();
	}
	
	private static Map<Integer, Integer> mapDailyOptionalItemIdsToNos() {
		return getDailyItems().entrySet().stream()
				.filter(en -> en.getValue().path().indexOf(DailyDomainGroup.OPTIONAL_ITEM.name) == 0)
				.collect(Collectors.toMap(i -> i.getKey(), i -> {
			return AttendanceItemUtilRes.getIdx(i.getValue().path());
		}));
	}
	
	private static Map<Integer, Integer> mapMonthlyOptionalItemIdsToNos() {
		return getMonthlyItems().entrySet().stream()
				.filter(en -> en.getValue().path().indexOf(MonthlyDomainGroup.OPTIONAL_ITEM.name) == 0)
				.collect(Collectors.toMap(i -> i.getKey(), i -> {
			return AttendanceItemUtilRes.getIdx(i.getValue().path());
		}));
	}
	
	public static Map<ItemValue, Integer> mapOptionalItemsFromIdToNos(Collection<Integer> items, AttendanceItemType type) {
		return mapOptionalItemsToNos(getIds(items, type));
	}
	
	public static Map<Integer, Integer> optionalItemIdsToNos(Collection<Integer> items, AttendanceItemType type) {
		return mapOptionalItemIdsToNos(getIds(items, type));
	}
	
	public static String getPath(int key, AttendanceItemType type) {
		if (type == AttendanceItemType.MONTHLY_ITEM) {
			return getMonthlyItems().get(key).path();
		}
		return getDailyItems().get(key).path();
	}

	public static Integer getEnumValue(String key) {
		return ENUM_CONTAINER.get(key);
	}

	public static List<ItemValue> getIds(Collection<Integer> values, AttendanceItemType type) {
		return getIdMapStream(values, type).collect(Collectors.toList());
	}

	public static List<ItemValue> getIds(AttendanceItemType type) {
		return getFullPair(type).collect(Collectors.toList());
	}

	public static Stream<ItemValue> getIdMapStream(Collection<Integer> values, AttendanceItemType type) {
		if (values == null || values.isEmpty()) {
			return getFullPair(type);
		}
		Map<Integer, ItemValue> source = type == AttendanceItemType.MONTHLY_ITEM ? getMonthlyItems()
				: getDailyItems();

		return values.stream().filter(c -> source.get(c) != null).map(c -> source.get(c).clone().itemId(c));
	}

	public static Stream<ItemValue> getFullPair(AttendanceItemType type) {
		if (type == AttendanceItemType.MONTHLY_ITEM) {
			return getMonthlyItems().entrySet().stream().map(c -> c.getValue().clone().itemId(c.getKey()));
		}
		return getDailyItems().entrySet().stream().map(c -> c.getValue().clone().itemId(c.getKey()));
	}

	private static Stream<ItemValue> getOptionalStream(Collection<ItemValue> items) {
		return items.stream().filter(i -> isOptionalItem(i));
	}

	public static boolean isOptionalItem(ItemValue i) {
		return getDailyItems().get(i.itemId()).path().contains(OPTIONAL_ITEM_VALUE);
	}
	
	private static String joinNS(String... arrays) {
		return StringUtils.join(arrays);
	}
	
	private static Map<Integer, ItemValue> getDailyItems() {
		return AttendanceItemIdDailyContainer.DAY_ITEM_ID_CONTAINER;
	}
	
	private static Map<Integer, ItemValue> getMonthlyItems() {
		return AttendanceItemIdMonthlyContainer.MONTHLY_ITEM_ID_CONTAINER;
	}
}
