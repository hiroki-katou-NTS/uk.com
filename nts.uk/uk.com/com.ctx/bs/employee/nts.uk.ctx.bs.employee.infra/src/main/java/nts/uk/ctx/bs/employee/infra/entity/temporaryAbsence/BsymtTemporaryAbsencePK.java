package nts.uk.ctx.bs.employee.infra.entity.temporaryAbsence;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class BsymtTemporaryAbsencePK implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	
	@Basic(optional = false)
	@Column(name = "LEAVE_HOLIDAY_ID")
	public String leaveHolidayId;
	
}
