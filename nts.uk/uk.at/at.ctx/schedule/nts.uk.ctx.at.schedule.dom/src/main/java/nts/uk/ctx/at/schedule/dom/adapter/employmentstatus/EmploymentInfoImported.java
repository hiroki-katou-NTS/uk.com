package nts.uk.ctx.at.schedule.dom.adapter.employmentstatus;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
/**
 * 
 * @author sonnh1
 *
 */
@AllArgsConstructor
@Getter
public class EmploymentInfoImported {
	
	private GeneralDate standardDate;

	private int employmentState;

	private Optional<Integer> tempAbsenceFrNo;
}
