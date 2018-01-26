package nts.uk.ctx.bs.employee.app.find.holidaysetting.configuration;

import lombok.Data;

/**
 * The Class PublicHolidayManagementUsageUnitFindDto.
 */
@Data
public class PublicHolidayManagementUsageUnitFindDto {
	
	/** The is manage employee public hd. */
	private int isManageEmployeePublicHd;
	
	/** The is manage wkp public hd. */
	private int isManageWkpPublicHd;
	
	/** The is manage emp public hd. */
	private int isManageEmpPublicHd;
	
	/**
	 * Instantiates a new public holiday management usage unit find dto.
	 */
	public PublicHolidayManagementUsageUnitFindDto(){
		super();
	}
}
