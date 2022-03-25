package nts.uk.ctx.at.request.dom.application.stamp;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.WorkLocationNameImported;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.WorkplaceNameImported;

/**
 * Workplace and work location name
 * 
 * @author LienPTK
 */
@Data
@NoArgsConstructor
public class WkpWorkLocationName {

	/** The work location names. */
	private List<WorkLocationNameImported> workLocationNames;

	/** The workplace names. */
	private List<WorkplaceNameImported> workplaceNames;

}
