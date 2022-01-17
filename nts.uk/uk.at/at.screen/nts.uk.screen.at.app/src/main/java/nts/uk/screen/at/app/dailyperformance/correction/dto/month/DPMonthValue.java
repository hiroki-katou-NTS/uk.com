package nts.uk.screen.at.app.dailyperformance.correction.dto.month;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.app.command.dailyperform.month.UpdateMonthDailyParam;
import nts.uk.ctx.at.record.app.find.monthly.root.MonthlyRecordWorkDto;
import nts.uk.ctx.at.record.app.find.monthly.root.common.ClosureDateDto;
import nts.uk.ctx.at.shared.dom.scherec.attendanceitem.converter.util.AttendanceItemUtil;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.IntegrationOfMonthly;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPItemValue;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DateRange;
import nts.uk.screen.at.app.monthlyperformance.correction.query.MonthlyModifyQuery;

@NoArgsConstructor
@Getter
public class DPMonthValue extends DPMonthParent {
	private List<DPItemValue> items;
	private String redConditionMessage;
	private Boolean hasFlex;
	private Boolean needCallCalc;

	public DPMonthValue(String employeeId, int yearMonth, int closureId, ClosureDateDto closureDate,
			List<DPItemValue> items) {
		super(employeeId, yearMonth, closureId, closureDate);
		this.items = items;
	}

	public UpdateMonthDailyParam createUpdateMonthDailyParam(Optional<MonthlyRecordWorkDto> dataParentDomainMonthOpt, DateRange dateRange){
		Optional<IntegrationOfMonthly> domainMonthOpt = Optional.empty();
		if (this.items != null && !this.items.isEmpty() && dataParentDomainMonthOpt.isPresent()) {
			MonthlyRecordWorkDto monthDto = dataParentDomainMonthOpt.get();
			MonthlyModifyQuery monthQuery = new MonthlyModifyQuery(this.items.stream().map(x -> {
				return ItemValue.builder().itemId(x.getItemId()).layout(x.getLayoutCode()).value(x.getValue())
						.valueType(ValueType.valueOf(x.getValueType())).withPath("");
			}).collect(Collectors.toList()), this.getYearMonth(), this.getEmployeeId(), this.getClosureId(),
					this.getClosureDate());
			monthDto = AttendanceItemUtil.fromItemValues(monthDto, monthQuery.getItems(), AttendanceItemUtil.AttendanceItemType.MONTHLY_ITEM);
			IntegrationOfMonthly domainMonth = monthDto.toDomain(monthDto.getEmployeeId(),
					monthDto.getYearMonth(), monthDto.getClosureID(), monthDto.getClosureDate());
			domainMonth.getAffiliationInfo().ifPresent(d -> {
				d.setVersion(this.getVersion());
			});
			domainMonth.getAttendanceTime().ifPresent(d -> {
				d.setVersion(this.getVersion());
			});
			domainMonthOpt = Optional.of(domainMonth);
		}

		return new UpdateMonthDailyParam(this.getYearMonth(), this.getEmployeeId(),
				this.getClosureId(), this.getClosureDate(), domainMonthOpt,
				new DatePeriod(dateRange.getStartDate(),
						dateRange.getEndDate()),
				this.redConditionMessage, this.hasFlex, this.needCallCalc,
				this.getVersion());
	}
}
