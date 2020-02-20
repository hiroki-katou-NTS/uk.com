package nts.uk.ctx.at.record.infra.entity.reservation.bento;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class KrcdtReservationPK {
	
	@Column(name = "CID")
	public String companyID;
	
	@Column(name = "RESERVATION_ID")
	public String reservationID;
	
}
