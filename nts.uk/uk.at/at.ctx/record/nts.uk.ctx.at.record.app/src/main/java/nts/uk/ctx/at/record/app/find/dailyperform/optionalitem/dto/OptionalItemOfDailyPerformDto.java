package nts.uk.ctx.at.record.app.find.dailyperform.optionalitem.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.customjson.CustomGeneralDateSerializer;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValueOfDaily;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.AttendanceItemCommon;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.optionalitemvalue.AnyItemValueOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItem;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemAtr;

@Data
@EqualsAndHashCode(callSuper = false)
@AttendanceItemRoot(rootName = ItemConst.DAILY_OPTIONAL_ITEM_NAME)
public class OptionalItemOfDailyPerformDto extends AttendanceItemCommon {

	@Override
	public String rootName() { return DAILY_OPTIONAL_ITEM_NAME; }
	/***/
	private static final long serialVersionUID = 1L;
	
	private String employeeId;

	@JsonDeserialize(using = CustomGeneralDateSerializer.class)
	private GeneralDate date;

	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = OPTIONAL_ITEM_VALUE, 
			listMaxLength = 100, indexField = DEFAULT_INDEX_FIELD_NAME)
	private List<OptionalItemValueDto> optionalItems = new ArrayList<>();

	public static OptionalItemOfDailyPerformDto getDto(AnyItemValueOfDaily domain) {
		return getDto(domain, null);
	}
	public static OptionalItemOfDailyPerformDto getDto(String employeeID,GeneralDate ymd,AnyItemValueOfDailyAttd domain) {
		return getDto(employeeID,ymd,domain, null);
	}

	public static OptionalItemOfDailyPerformDto getDto(AnyItemValueOfDaily domain, Map<Integer, OptionalItem> master) {
		OptionalItemOfDailyPerformDto dto = new OptionalItemOfDailyPerformDto();
		if (domain != null) {
			dto.setDate(domain.getYmd());
			dto.setEmployeeId(domain.getEmployeeId());
			dto.setOptionalItems(ConvertHelper.mapTo(domain.getAnyItem().getItems(), (c) -> 
							OptionalItemValueDto.from(c, getAttrFromMaster(master, c))));
			dto.exsistData();
		}
		return dto;
	}
	
	public static OptionalItemOfDailyPerformDto getDto(String employeeID,GeneralDate ymd,AnyItemValueOfDailyAttd domain, Map<Integer, OptionalItem> master) {
		OptionalItemOfDailyPerformDto dto = new OptionalItemOfDailyPerformDto();
		if (domain != null) {
			dto.setDate(ymd);
			dto.setEmployeeId(employeeID);
			dto.setOptionalItems(ConvertHelper.mapTo(domain.getItems(), (c) -> 
							OptionalItemValueDto.from(c, getAttrFromMaster(master, c))));
			dto.exsistData();
		}
		return dto;
	}
	
	public static OptionalItemOfDailyPerformDto getDtoWith(AnyItemValueOfDaily domain, Map<Integer, OptionalItemAtr> master) {
		OptionalItemOfDailyPerformDto dto = new OptionalItemOfDailyPerformDto();
		if (domain != null) {
			dto.setDate(domain.getYmd());
			dto.setEmployeeId(domain.getEmployeeId());
			dto.setOptionalItems(ConvertHelper.mapTo(domain.getAnyItem().getItems(), (c) -> 
							OptionalItemValueDto.from(c, getAttrFromMasterWith(master, c))));
			dto.exsistData();
		}
		return dto;
	}

	@Override
	public OptionalItemOfDailyPerformDto clone() {
		OptionalItemOfDailyPerformDto dto = new OptionalItemOfDailyPerformDto();
			dto.setDate(workingDate());
			dto.setEmployeeId(employeeId());
			dto.setOptionalItems(optionalItems == null ? null : optionalItems.stream().map(t -> t.clone()).collect(Collectors.toList()));
		if (isHaveData()) {
			dto.exsistData();
		}
		return dto;
	}
	
	public void correctItems(Map<Integer, OptionalItem> optionalMaster) {
		optionalItems.stream().filter(item -> item != null).forEach(item -> {
//			if(item.isNeedCorrect()) {
				item.correctItem(getAttrFromMaster(optionalMaster, item));
//			}
		});
		optionalItems.removeIf(item -> item == null || !item.isHaveData());
	}
	
	public void correctItemsWith(Map<Integer, OptionalItemAtr> optionalMaster) {
		optionalItems.stream().filter(item -> item != null).forEach(item -> {
//			if(item.isNeedCorrect()) {
				item.correctItem(getAttrFromMasterWith(optionalMaster, item));
//			}
		});
		optionalItems.removeIf(item -> item == null || !item.isHaveData());
	}

	@Override
	public String employeeId() {
		return this.employeeId;
	}

	@Override
	public GeneralDate workingDate() {
		return this.date;
	}

	@Override
	public AnyItemValueOfDailyAttd toDomain(String employeeId, GeneralDate date) {
		if (!this.isHaveData()) {
			return null;
		}
		if (employeeId == null) {
			employeeId = this.employeeId();
		}
		if (date == null) {
			date = this.workingDate();
		}
		optionalItems.removeIf(item -> item == null || !item.isHaveData());
		AnyItemValueOfDaily domain =  new AnyItemValueOfDaily(employeeId, date,
				ConvertHelper.mapTo(optionalItems, c -> c == null ? null : c.toDomain()));
		return domain.getAnyItem();
	}

	private static OptionalItemAtr getAttrFromMaster(Map<Integer, OptionalItem> master, AnyItemValue c) {
		OptionalItem optItem = master == null ? null : master.get(c.getItemNo().v());
		
		if(optItem != null)
			return optItem.getOptionalItemAtr();
		
		return null;
	}
	
	private static OptionalItemAtr getAttrFromMasterWith(Map<Integer, OptionalItemAtr> master, AnyItemValue c) {
		return master == null ? null : master.get(c.getItemNo().v());
	}
	
	private static OptionalItemAtr getAttrFromMaster(Map<Integer, OptionalItem> master, OptionalItemValueDto c) {
		OptionalItem optItem = master == null ? null : master.get(c.getNo());
		
		if(optItem != null)
			return optItem.getOptionalItemAtr();
		
		return null;
	}
	
	private static OptionalItemAtr getAttrFromMasterWith(Map<Integer, OptionalItemAtr> master, OptionalItemValueDto c) {
		return master == null ? null : master.get(c.getNo());
	}
	
	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		if (OPTIONAL_ITEM_VALUE.equals(path)) {
			return new OptionalItemValueDto();
		}
		return null;
	}

	@Override
	public int size(String path) {
		return 100;
	}

	@Override
	public boolean isRoot() { return true; }
	

	@Override
	public PropType typeOf(String path) {
		if (OPTIONAL_ITEM_VALUE.equals(path)) {
			return PropType.IDX_LIST;
		}
		return super.typeOf(path);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends AttendanceItemDataGate> List<T> gets(String path) {
		if (OPTIONAL_ITEM_VALUE.equals(path)) {
			return (List<T>) this.optionalItems;
		}
		return super.gets(path);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends AttendanceItemDataGate> void set(String path, List<T> value) {
		if (OPTIONAL_ITEM_VALUE.equals(path)) {
			this.optionalItems = (List<OptionalItemValueDto>) value;
		}
	}
}
