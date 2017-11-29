package nts.uk.screen.at.app.dailyperformance.correction.dto;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workinformation.enums.CalculationState;


/** @author lamdt */
@Getter
public class WorkInfoOfDailyPerformanceDto {
	
	private String employeeId;

	private int calculationState;

	private GeneralDate ymd;

	public WorkInfoOfDailyPerformanceDto(String employeeId, int calculationState, GeneralDate ymd) {
		super();
		this.employeeId = employeeId;
		this.calculationState = calculationState;
		this.ymd = ymd;
	}

	public CalculationState getState() {
		return EnumAdaptor.valueOf(this.calculationState, CalculationState.class);
	}
	
}