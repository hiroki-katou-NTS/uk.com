package nts.uk.ctx.at.record.infra.entity.reservation.bento;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;

@Embeddable
@AllArgsConstructor
public class KrcdtReservationPK {
	
	@Column(name = "CID")
	public String companyID;
	
	@Column(name = "RESERVATION_ID")
	public String reservationID;
	
}
