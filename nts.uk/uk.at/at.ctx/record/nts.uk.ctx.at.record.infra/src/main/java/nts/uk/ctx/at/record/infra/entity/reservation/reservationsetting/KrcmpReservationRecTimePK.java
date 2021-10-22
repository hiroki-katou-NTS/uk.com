package nts.uk.ctx.at.record.infra.entity.reservation.reservationsetting;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class KrcmpReservationRecTimePK {
	
	@Column(name = "CID")
    public String companyID;
	
	@Column(name = "TIME_NO")
    public int timeNo;
}
