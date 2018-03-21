package nts.uk.ctx.at.record.infra.entity.remainingnumber;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KRcmtAnnLeaRemainPK {
	
	@Column(name = "EMPLOYEE_ID")
    public String employeeId;
	
	@Column(name = "GRANT_DATE")
    public GeneralDate grantDate;

}
