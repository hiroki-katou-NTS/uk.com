package nts.uk.ctx.at.schedule.infra.entity.shift.management.workexpect;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class KscdtAvailabilityPk {
	
	@Column(name = "SID")
	public String employeeID;
	
	@Column(name = "YMD")
	public GeneralDate expectingDate;

}