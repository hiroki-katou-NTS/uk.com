package nts.uk.ctx.at.schedule.infra.entity.calendar;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KsmmtCalendarWorkplacePK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "WKPID")
	public String workPlaceId;

	
	@Column(name = "YMD_K")
	public BigDecimal dateId;

}
