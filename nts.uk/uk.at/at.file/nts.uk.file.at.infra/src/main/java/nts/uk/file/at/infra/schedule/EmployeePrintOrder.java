package nts.uk.file.at.infra.schedule;

import java.util.Optional;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.adapter.executionlog.dto.EmployeeDto;

/**
 * Instantiates a new employee print order.
 * @author HoangNDH
 */
@Data
@NoArgsConstructor
public class EmployeePrintOrder {
	
	/** The print order. */
	private int printOrder;
	
	/** The String. */
	private String employeeId;
	
	/** The employee dto. */
	private Optional<EmployeeDto> employeeDto;

	/**
	 * Instantiates a new employee print order.
	 *
	 * @param printOrder the print order
	 * @param employeeId the employee id
	 */
	public EmployeePrintOrder(int printOrder, String employeeId) {
		super();
		this.printOrder = printOrder;
		this.employeeId = employeeId;
		this.employeeDto = Optional.empty();
	}
}
