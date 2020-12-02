package nts.uk.ctx.sys.auth.pub.wkpmanager;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class WorkplaceManagerExport {

	/** The work place ID. */
	public List<String> lstWorkPlaceID;

	/** The employee range. */
	public Integer employeeRange;

	/**
	 * Instantiates a new workplace info export.
	 */
	public WorkplaceManagerExport() {
		super();
		this.lstWorkPlaceID = new ArrayList<>();
	}

}
