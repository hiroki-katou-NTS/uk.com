package nts.uk.ctx.at.schedule.dom.adapter.generalinfo.workplace;

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
public class ExWorkPlaceHistoryImported {

	private String employeeId;

	private List<ExWorkplaceHistItemImported> workplaceItems;
}
