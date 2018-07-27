package nts.uk.ctx.at.schedule.dom.adapter.generalinfo.classification;

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
public class ExClassificationHistoryImported {
	
	private String employeeId;

	private List<ExClassificationHistItemImported> classificationItems;

}
