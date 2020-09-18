package nts.uk.ctx.at.schedule.infra.entity.shift.management.workexpect;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class KscdtAvailabilityShiftPk {
	
	@Column(name = "SID")
	public String employeeID;

	@Getter
	@Column(name = "YMD")
	public GeneralDate expectingDate;
	
	@Column(name = "SHIFT_MASTER_CD")
	public String shiftMasterCode;
	

}
