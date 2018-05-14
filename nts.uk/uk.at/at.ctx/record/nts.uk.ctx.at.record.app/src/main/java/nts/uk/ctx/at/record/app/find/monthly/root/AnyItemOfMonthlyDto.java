package nts.uk.ctx.at.record.app.find.monthly.root;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.app.find.dailyperform.optionalitem.dto.OptionalItemValueDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.MonthlyItemCommon;
import nts.uk.ctx.at.record.app.find.monthly.root.dto.ClosureDateDto;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の任意項目 */
public class AnyItemOfMonthlyDto extends MonthlyItemCommon {
	
	/** 社員ID: 社員ID */
	private String employeeId;

	/** 締めID: 締めID */
	private int closureId = 1;

	/** 締め日: 日付 */
	private ClosureDateDto closureDate;
	
	/** 年月: 年月 */
	private YearMonth yearMonth;
	
	/** 任意項目値: 集計任意項目 */
	@AttendanceItemLayout(layout = "A", jpPropertyName = "任意項目値", listMaxLength = 200, indexField = "itemNo")
	private List<OptionalItemValueDto> values;
	@Override
	public String employeeId() {
		return this.employeeId;
	}

	@Override
	public Object toDomain() {
		if (this.isHaveData()) {
//			return AnyItemOfMonthly.of(employeeId, yearMonth, 
//					ConvertHelper.getEnum(closureId, ClosureId.class),
//					closureDate == null ? null : closureDate.toDomain(),
//					startMonthInfo == null ? null : startMonthInfo.toDomain(),
//					endMonthInfo == null ? null : endMonthInfo.toDomain());
		}
		return null;
	}

	@Override
	public YearMonth yearMonth() {
		return yearMonth;
	}

	public static AnyItemOfMonthlyDto from(Object domain) {
		AnyItemOfMonthlyDto dto = new AnyItemOfMonthlyDto();
		if (domain != null) {
//			dto.setClosureDate(ClosureDateDto.from(domain.getClosureDate()));
//			dto.setClosureId(domain.getClosureId() == null ? 1 : domain.getClosureId().value);
//			dto.setEmployeeId(domain.getEmployeeId());
//			dto.setYearMonth(domain.getYearMonth());
			dto.exsistData();
		}
		return dto;
	}
}
