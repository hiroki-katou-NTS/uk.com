package nts.uk.ctx.at.record.dom.attendanceitem.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceItemUtilRes;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.AttendanceItemIdContainer;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.AttendanceItemUtil.AttendanceItemType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.AttendanceItemCommon;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ConvertibleAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItem;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemRepository;
import nts.uk.shr.com.context.AppContexts;

public abstract class AttendanceItemConverterCommonService implements AttendanceItemConverter {

	protected final Map<String, Object> domainSource;
	protected final Map<String, Object> dtoSource;
	protected final Map<String, Set<ItemValue>> itemValues;
	protected final OptionalItemRepository optionalItem;
	protected Map<Integer, OptionalItem> optionalItems;
	
	private List<ItemValue> needMergeItems = new ArrayList<>();
	private Map<String, List<ItemValue>> mergeGroups = new HashMap<>();
	private boolean clearMergeGroups = false;
	
	protected AttendanceItemConverterCommonService (Map<Integer, OptionalItem> optionalItems,
			OptionalItemRepository optionalItem) {
		this.domainSource = new HashMap<>();
		this.dtoSource = new HashMap<>();
		this.itemValues = new HashMap<>();
		this.optionalItem = optionalItem;
		this.optionalItems = optionalItems;
	}
	
	protected abstract boolean isMonthly();

	protected abstract Object correctOptionalItem(Object dto);
	
	protected abstract boolean isOpyionalItem(String type);
	
	protected abstract void convertDomainToDto(String type);
	
	protected abstract Object toDomain(ConvertibleAttendanceItem dto);
	
	protected void processOnDomain(String type, Function<Object, Object> converter) {
		Object domain = this.domainSource.get(type);
		if (domain != null) {
			this.dtoSource.put(type, converter.apply(domain));
			this.domainSource.remove(type);
		}
	}
	
	protected Map<Integer, OptionalItem>  loadOptionalItemMaster () {
		if (this.optionalItems == null || this.optionalItems.isEmpty()) {
			this.optionalItems = this.optionalItem.findAll(AppContexts.user().companyId()).stream()
					.collect(Collectors.toMap(c -> c.getOptionalItemNo().v(), c -> c));
		}
		
		return this.optionalItems;
	}
	
	public OptionalItemRepository getOptionalItemRepo() {
		return optionalItem;
	}
	
	public Optional<ItemValue> convert(int attendanceItemId) {
		List<ItemValue> items = internalConvert(Arrays.asList(attendanceItemId));
		return items.isEmpty() ? Optional.empty() : Optional.of(items.get(0));
	}

	public List<ItemValue> convert(Collection<Integer> attendanceItemIds) {
		return internalConvert(attendanceItemIds);
	}

	public void merge(ItemValue value) {
//		AttendanceItemUtil.fromItemValues(dailyRecord, Arrays.asList(value));
		needMergeItems.add(value);
		clearMergeGroups = true;
	}

	public void merge(Collection<ItemValue> values) {
//		AttendanceItemUtil.fromItemValues(dailyRecord, values);
		needMergeItems.addAll(values);
		clearMergeGroups = true;
	}
	
	private AttendanceItemCommon internalMerge(String type, AttendanceItemCommon dto) {
		
		if (this.needMergeItems.isEmpty()) {
			return dto;
		}
		
		loadMergeGroup();
		
		if (this.mergeGroups.containsKey(type)) {
			List<ItemValue> values = this.mergeGroups.get(type);
			
			dto = AttendanceItemUtilRes.merge(dto, values, 
					isMonthly() ? AttendanceItemType.MONTHLY_ITEM : AttendanceItemType.DAILY_ITEM);
			
			this.mergeGroups.remove(type);
			this.needMergeItems.removeAll(values);
			this.dtoSource.put(type, dto);
			Set<Integer> ids = values.stream().map(c -> c.itemId()).collect(Collectors.toSet());
			
			this.itemValues.computeIfAbsent(type, k -> new HashSet<>());
			this.itemValues.get(type).removeIf(c -> ids.contains(c.itemId()));
			this.itemValues.get(type).addAll(values);
		}
		
		return dto;
	}

	private void loadMergeGroup() {
		if (clearMergeGroups || this.mergeGroups.isEmpty()) {
			this.mergeGroups = AttendanceItemIdContainer.groupItemByDomain(this.needMergeItems, ItemValue::itemId, isMonthly());
		}
	}

	protected Object getDomain(String type) {
		
		Object merged = mergeAndGet(type);
		if (merged != null) {
			return merged;
		}
		
		if (this.domainSource.get(type) != null) {
			convertDomainToDto(type);
//			return this.domainSource.get(type);
		}
		
		if (this.dtoSource.get(type) != null) {
			if (isOpyionalItem(type)) {
				
				return correctOptionalItem(this.dtoSource.get(type));
			}

			return toDomain((AttendanceItemCommon) this.dtoSource.get(type));
		}
		return null;
	}

	protected Object getDomains(String type) {
		
		Object merged = mergeAndGet(type);
		if (merged != null) {
			return merged;
		}
		
		if (this.domainSource.get(type) != null) {
			return this.domainSource.get(type);
		}
		
		if (this.dtoSource.get(type) != null) {
			return toDomain((AttendanceItemCommon) this.dtoSource.get(type));
		}
		
		return new ArrayList<>();
	}
	
	private Object mergeAndGet(String type) {
		loadMergeGroup();
		
		if (this.mergeGroups.containsKey(type)) {
			convertDomainToDto(type);
			
			Object dto = this.dtoSource.get(type);
			
			AttendanceItemCommon merged = internalMerge(type, (AttendanceItemCommon) dto);
			if (isOpyionalItem(type)) {
				
				return correctOptionalItem(this.dtoSource.get(type));
			}
			
			return toDomain(merged);
		}
		
		return null;
	}

	private List<ItemValue> internalConvert(Collection<Integer> attendanceItemIds) {
		Map<String, List<Integer>> groups = AttendanceItemIdContainer.groupItemByDomain(
													attendanceItemIds, id -> id, isMonthly());
		
//		List<ItemValue> converted = Collections.synchronizedList(new ArrayList<>());
		List<ItemValue> converted = new ArrayList<>();

		groups.entrySet().stream().forEach(es -> {
//			groups.entrySet().stream().parallel().forEach(es -> {
			List<Integer> needConvert = new ArrayList<>();
			Set<ItemValue> cached = this.itemValues.get(es.getKey());
			if (cached == null) {
				needConvert.addAll(es.getValue());
			} else {
				es.getValue().forEach(id -> {
					Optional<ItemValue> ca = cached.stream().filter(c -> c.itemId() == id).findFirst();
					if (ca.isPresent()) {
						converted.add(ca.get());
					} else {
						needConvert.add(id);
					}
				});
			}

			if (!needConvert.isEmpty()) {
				List<ItemValue> convertR = convert(es.getKey(), needConvert);
				converted.addAll(convertR);
				
				if (cached == null) {
					this.itemValues.put(es.getKey(), new HashSet<>(convertR));
				} else {
					this.itemValues.get(es.getKey()).addAll(convertR);
				}
			}
		});
		
		return converted;
	}

	private List<ItemValue> convert(String type, List<Integer> itemIds) {
//		if (this.dtoSource.get(type) == null) {
		convertDomainToDto(type);
//		} 
		
		Object dto = this.dtoSource.get(type);
		
		if (dto == null) {
			return new ArrayList<>();
		} else {
			
			return AttendanceItemUtilRes.collect((AttendanceItemDataGate) dto, itemIds,
					isMonthly() ? AttendanceItemType.MONTHLY_ITEM : AttendanceItemType.DAILY_ITEM);
		}
	}
}
