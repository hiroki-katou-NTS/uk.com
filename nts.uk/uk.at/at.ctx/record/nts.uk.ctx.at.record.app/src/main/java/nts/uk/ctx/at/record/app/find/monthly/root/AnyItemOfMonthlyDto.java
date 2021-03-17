package nts.uk.ctx.at.record.app.find.monthly.root;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.app.find.dailyperform.optionalitem.dto.OptionalItemValueDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.ClosureDateDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.MonthlyItemCommon;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemDataGate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.ItemConst;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.AttendanceItemUtil.AttendanceItemType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.anyitem.AnyItemOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItem;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemAtr;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の任意項目 */
@AttendanceItemRoot(rootName = ItemConst.MONTHLY_OPTIONAL_ITEM_NAME, itemType = AttendanceItemType.MONTHLY_ITEM)
public class AnyItemOfMonthlyDto extends MonthlyItemCommon {

	/***/
	private static final long serialVersionUID = 1L;
	
	/** 社員ID: 社員ID */
	private String employeeId;

	/** 締めID: 締めID */
	private int closureID = 1;

	/** 締め日: 日付 */
	private ClosureDateDto closureDate;
	
	/** 年月: 年月 */
	private YearMonth yearMonth;
	
	/** 任意項目値: 集計任意項目 */
	@AttendanceItemLayout(layout = LAYOUT_A, jpPropertyName = OPTIONAL_ITEM_VALUE, 
			listMaxLength = 200, indexField = DEFAULT_INDEX_FIELD_NAME)
	private List<OptionalItemValueDto> values = new ArrayList<>();
	@Override
	public String employeeId() {
		return this.employeeId;
	}

	@Override
	public List<AnyItemOfMonthly> toDomain(String employeeId, YearMonth ym, int closureID, ClosureDateDto closureDate) {
		if(!this.isHaveData()) {
			return new ArrayList<>();
		}
		
		values.removeIf(item -> !item.isHaveData());
		
		return ConvertHelper.mapTo(values, any -> AnyItemOfMonthly.of(employeeId == null ? this.employeeId : employeeId, 
				ym == null ? yearMonth : ym, 
				ConvertHelper.getEnum(closureID, ClosureId.class),
				closureDate == null ? this.closureDate == null ? null : this.closureDate.toDomain() : closureDate.toDomain(),
				any.getNo(),
				Optional.of(any.getMonthlyTimeOrDefault()),
				Optional.of(any.getMonthlyTimesOrDefault()), 
				Optional.of(any.getMonthlyAmountOrDefault())));
		
	}

	@Override
	public YearMonth yearMonth() {
		return yearMonth;
	}
	
	public void correctItems(Map<Integer, OptionalItem> optionalMaster) {
		values.stream().filter(item -> item != null).forEach(item -> {
//			if(item.isNeedCorrect()) {
				item.correctItem(getAttrFromMaster(optionalMaster, item.getNo()));
//			}
		});
		values.removeIf(item -> item == null || !item.isHaveData());
	}

	public static AnyItemOfMonthlyDto from(AnyItemOfMonthly domain) {
		return from(domain, null);
	}
	
	public static AnyItemOfMonthlyDto from(AnyItemOfMonthly domain, Map<Integer, OptionalItem> master) {
		return from(Arrays.asList(domain), master);
	}
	
	public static AnyItemOfMonthlyDto from(List<AnyItemOfMonthly> domain) {
		return from(domain, null);
	}
	
	public static AnyItemOfMonthlyDto from(List<AnyItemOfMonthly> domain, Map<Integer, OptionalItem> master) {
		AnyItemOfMonthlyDto dto = new AnyItemOfMonthlyDto();
		if (domain != null && !domain.isEmpty()) {
			dto.setClosureDate(ClosureDateDto.from(domain.get(0).getClosureDate()));
			dto.setClosureID(domain.get(0).getClosureId() == null ? 1 : domain.get(0).getClosureId().value);
			dto.setEmployeeId(domain.get(0).getEmployeeId());
			dto.setYearMonth(domain.get(0).getYearMonth());
			domain.stream().forEach(d -> {
				dto.getValues().add(OptionalItemValueDto.from(d, getAttrFromMaster(master, d.getAnyItemId())));
			});
			dto.exsistData();
		}
		return dto;
	}
	
	public static AnyItemOfMonthlyDto fromWith(List<AnyItemOfMonthly> domain, Map<Integer, OptionalItemAtr> master) {
		AnyItemOfMonthlyDto dto = new AnyItemOfMonthlyDto();
		if (domain != null && !domain.isEmpty()) {
			dto.setClosureDate(ClosureDateDto.from(domain.get(0).getClosureDate()));
			dto.setClosureID(domain.get(0).getClosureId() == null ? 1 : domain.get(0).getClosureId().value);
			dto.setEmployeeId(domain.get(0).getEmployeeId());
			dto.setYearMonth(domain.get(0).getYearMonth());
			domain.stream().forEach(d -> {
				dto.getValues().add(OptionalItemValueDto.from(d, getAttrFromMasterWith(master, d)));
			});
			dto.exsistData();
		}
		return dto;
	}

	private static OptionalItemAtr getAttrFromMaster(Map<Integer, OptionalItem> master, int itemNo) {
		OptionalItem optItem = master == null ? null : master.get(itemNo);
		OptionalItemAtr attr = null;
		if(optItem != null){
			attr = optItem.getOptionalItemAtr();
		}
		return attr;
	}
	
	private static OptionalItemAtr getAttrFromMasterWith(Map<Integer, OptionalItemAtr> master, AnyItemOfMonthly c) {
		return master == null ? null : master.get(c.getAnyItemId());
	}

	@Override
	public AttendanceItemDataGate newInstanceOf(String path) {
		if (OPTIONAL_ITEM_VALUE.equals(path)) {
			return new OptionalItemValueDto();
		}
		return super.newInstanceOf(path);
	}

	@Override
	public int size(String path) {
		if (OPTIONAL_ITEM_VALUE.equals(path)) {
			return 200;
		}
		return super.size(path);
	}

	@Override
	public PropType typeOf(String path) {
		if (OPTIONAL_ITEM_VALUE.equals(path)) {
			return PropType.IDX_LIST;
		}
		return super.typeOf(path);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends AttendanceItemDataGate> List<T> gets(String path) {
		if (OPTIONAL_ITEM_VALUE.equals(path)) {
			return (List<T>) values;
		}
		return super.gets(path);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends AttendanceItemDataGate> void set(String path, List<T> value) {
		if (OPTIONAL_ITEM_VALUE.equals(path)) {
			values = (List<OptionalItemValueDto>) value;
		}
	}
	
	@Override
	public boolean isRoot() {
		return true;
	}

	@Override
	public String rootName() {
		return MONTHLY_OPTIONAL_ITEM_NAME;
	}
}
