package nts.uk.ctx.pr.proto.infra.entity.personalinfo.holiday;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PhldtHolidayPaidPK implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "CCD")
	public String ccd;
	
	@Column(name = "PID")
	public String pId;
	
	@Column(name = "GRANT_DATE")
	public LocalDate grantDate;

}
