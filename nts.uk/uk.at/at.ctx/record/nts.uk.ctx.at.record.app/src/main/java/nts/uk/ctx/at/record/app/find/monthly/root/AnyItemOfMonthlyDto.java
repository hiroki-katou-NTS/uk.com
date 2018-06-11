package nts.uk.ctx.at.record.app.find.monthly.root;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.app.find.dailyperform.optionalitem.dto.OptionalItemValueDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.ClosureDateDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.MonthlyItemCommon;
import nts.uk.ctx.at.record.dom.monthly.anyitem.AnyItemOfMonthly;
import nts.uk.ctx.at.record.dom.optitem.OptionalItem;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.ItemConst;
import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceItemUtil.AttendanceItemType;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の任意項目 */
@AttendanceItemRoot(rootName = ItemConst.MONTHLY_OPTIONAL_ITEM_NAME, itemType = AttendanceItemType.MONTHLY_ITEM)
public class AnyItemOfMonthlyDto extends MonthlyItemCommon {
	
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
	public List<AnyItemOfMonthly> toDomain() {
		if (this.isHaveData()) {
			return ConvertHelper.mapTo(values, any -> AnyItemOfMonthly.of(employeeId, yearMonth, 
					ConvertHelper.getEnum(closureID, ClosureId.class),
					closureDate == null ? null : closureDate.toDomain(),
					any.getNo(),
					Optional.ofNullable(any.getMonthlyTime()),
					Optional.ofNullable(any.getMonthlyTimes()), 
					Optional.ofNullable(any.getMonthlyAmount())));
		}
		return new ArrayList<>();
	}

	@Override
	public YearMonth yearMonth() {
		return yearMonth;
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
				if(master == null || master.get(d.getAnyItemId()) == null){
					dto.getValues().add(OptionalItemValueDto.from(d, null));
				} else {
					dto.getValues().add(OptionalItemValueDto.from(d, master.get(d.getAnyItemId()).getOptionalItemAtr()));
				}
			});
			dto.exsistData();
		}
		return dto;
	}
}
