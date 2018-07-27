package nts.uk.ctx.at.schedule.dom.adapter.employmentstatus;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 
 * @author sonnh1
 *
 */
@AllArgsConstructor
@Getter
public class EmploymentStatusImported {

	private String employeeId;

	private List<EmploymentInfoImported> employmentInfo;
}
