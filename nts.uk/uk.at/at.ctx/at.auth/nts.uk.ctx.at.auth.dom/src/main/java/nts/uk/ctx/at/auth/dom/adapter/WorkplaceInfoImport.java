package nts.uk.ctx.at.auth.dom.adapter;

import java.util.List;

import lombok.Getter;

@Getter
public class WorkplaceInfoImport {

	/** The work place ID. */
	public List<String> lstWorkPlaceID;

	/** The employee range. */
	public Integer employeeRange;

	public WorkplaceInfoImport(List<String> lstWorkPlaceID, Integer employeeRange) {
		super();
		this.lstWorkPlaceID = lstWorkPlaceID;
		this.employeeRange = employeeRange;
	}

	/**
	 * Instantiates a new workplace info export.
	 */
	
}
