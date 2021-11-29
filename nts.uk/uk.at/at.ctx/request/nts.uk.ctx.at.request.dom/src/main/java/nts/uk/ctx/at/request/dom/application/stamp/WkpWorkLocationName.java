package nts.uk.ctx.at.request.dom.application.stamp;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Workplace and work location name
 * 
 * @author LienPTK
 */
@Data
@NoArgsConstructor
public class WkpWorkLocationName {

	/** The work location names. */
	private List<String> workLocationNames;

	/** The workplace names. */
	private List<String> workplaceNames;

}
