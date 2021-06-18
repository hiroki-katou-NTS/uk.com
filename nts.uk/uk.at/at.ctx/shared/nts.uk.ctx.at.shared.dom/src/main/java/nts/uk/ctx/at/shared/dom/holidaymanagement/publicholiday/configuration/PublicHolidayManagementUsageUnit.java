package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * The Class PublicHolidayManagementUsageUnit.
 */
// 利用単位の設定
@Getter
@Setter
public class PublicHolidayManagementUsageUnit extends AggregateRoot{
	
	/** The is manage employee public hd. */
	// 社員の公休管理をする
	private int isManageEmployeePublicHd;
	
	/** The is manage wkp public hd. */
	// 職場の公休管理をする
	private int isManageWkpPublicHd;
	
	/** The is manage emp public hd. */
	// 雇用の公休管理をする
	private int isManageEmpPublicHd;
	
	/**
	 * Instantiates a new public holiday management usage unit.
	 *
	 * @param isManageEmployeePublicHd the is manage employee public hd
	 * @param isManageWkpPublicHd the is manage wkp public hd
	 * @param isManageEmpPublicHd the is manage emp public hd
	 */
	public PublicHolidayManagementUsageUnit(int isManageEmployeePublicHd, int isManageWkpPublicHd, int isManageEmpPublicHd){
		this.isManageEmployeePublicHd = isManageEmployeePublicHd;
		this.isManageWkpPublicHd = isManageWkpPublicHd;
		this.isManageEmpPublicHd = isManageEmpPublicHd;
	}
}
