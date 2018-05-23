package nts.uk.ctx.at.record.app.find.monthly.root;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.app.find.dailyperform.optionalitem.dto.OptionalItemValueDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.ClosureDateDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.MonthlyItemCommon;
import nts.uk.ctx.at.record.dom.monthly.anyitem.AnyItemOfMonthly;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;

@Data
@NoArgsConstructor
@AllArgsConstructor
/** 月別実績の任意項目 */
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
	@AttendanceItemLayout(layout = "A", jpPropertyName = "任意項目値", listMaxLength = 200, indexField = "itemNo")
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
					any.getItemNo(),
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
		AnyItemOfMonthlyDto dto = new AnyItemOfMonthlyDto();
		if (domain != null) {
			dto.setClosureDate(ClosureDateDto.from(domain.getClosureDate()));
			dto.setClosureID(domain.getClosureId() == null ? 1 : domain.getClosureId().value);
			dto.setEmployeeId(domain.getEmployeeId());
			dto.setYearMonth(domain.getYearMonth());
			dto.getValues().add(OptionalItemValueDto.from(domain));
			dto.exsistData();
		}
		return dto;
	}
	
	public static AnyItemOfMonthlyDto from(List<AnyItemOfMonthly> domain) {
		AnyItemOfMonthlyDto dto = new AnyItemOfMonthlyDto();
		if (domain != null && !domain.isEmpty()) {
			dto.setClosureDate(ClosureDateDto.from(domain.get(0).getClosureDate()));
			dto.setClosureID(domain.get(0).getClosureId() == null ? 1 : domain.get(0).getClosureId().value);
			dto.setEmployeeId(domain.get(0).getEmployeeId());
			dto.setYearMonth(domain.get(0).getYearMonth());
			domain.stream().forEach(d -> {
				dto.getValues().add(OptionalItemValueDto.from(d));
			});
			dto.exsistData();
		}
		return dto;
	}
}
