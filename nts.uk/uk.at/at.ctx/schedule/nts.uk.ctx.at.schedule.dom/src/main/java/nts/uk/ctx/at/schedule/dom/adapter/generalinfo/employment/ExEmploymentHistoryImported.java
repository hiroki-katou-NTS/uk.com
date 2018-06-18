package nts.uk.ctx.at.schedule.dom.adapter.generalinfo.employment;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * @author sonnh1
 *
 */
@AllArgsConstructor
@Data
public class ExEmploymentHistoryImported {

	private String employeeId;

	private List<ExEmploymentHistItemImported> employmentItems;

}
