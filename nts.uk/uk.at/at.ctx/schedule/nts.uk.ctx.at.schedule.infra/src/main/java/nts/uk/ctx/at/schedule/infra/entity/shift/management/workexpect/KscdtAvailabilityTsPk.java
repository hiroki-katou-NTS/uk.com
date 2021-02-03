package nts.uk.ctx.at.schedule.infra.entity.shift.management.workexpect;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class KscdtAvailabilityTsPk {
	
	@Column(name = "SID")
	public String employeeID;

	@Column(name = "YMD")
	public GeneralDate expectingDate;
	
	@Column(name = "START_CLOCK")
	public int startClock;

	@Column(name = "END_CLOCK")
	public int endClock;

}
