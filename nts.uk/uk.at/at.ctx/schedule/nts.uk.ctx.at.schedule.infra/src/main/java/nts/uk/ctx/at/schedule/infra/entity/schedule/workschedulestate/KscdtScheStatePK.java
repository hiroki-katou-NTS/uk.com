package nts.uk.ctx.at.schedule.infra.entity.schedule.workschedulestate;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author sonnh1
 *
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KscdtScheStatePK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "SID")
	public String employeeId;

	@Column(name = "SCHE_ITEM_ID")
	public int scheduleItemId;
	
	@Column(name = "YMD")
	public GeneralDate date;
}
