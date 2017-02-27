package nts.uk.ctx.pr.core.infra.entity.personalinfo.holiday;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.type.LocalDateToDBConverter;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
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
	@Convert(converter = LocalDateToDBConverter.class)
	public LocalDate grantDate;

}
