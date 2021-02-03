package nts.uk.ctx.at.function.app.find.processexecution.dto;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeDto;

@Data
public class ProcessStartupDto {
	
	/**
	 * 承認中間データエラーメッセージ情報（月別実績）
	 */
	private List<AppDataInfoMonthlyDto> monthlyDtos;
	
	/**
	 * 承認中間データエラーメッセージ情報（日別実績）
	 */
	private List<AppDataInfoDailyDto> dailyDtos;
	
	/**
	 * 社員
	 */
	private List<EmployeeDto> employees;
}
