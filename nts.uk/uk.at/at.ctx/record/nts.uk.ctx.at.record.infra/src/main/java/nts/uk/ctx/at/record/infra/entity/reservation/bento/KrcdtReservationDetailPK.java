package nts.uk.ctx.at.record.infra.entity.reservation.bento;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class KrcdtReservationDetailPK {
	
	@Column(name = "CID")
	public String companyID;
	
	@Column(name = "RESERVATION_ID")
	public String reservationID;
	
	@Column(name = "MANU_FRAME")
	public Integer frameNo;
	
	@Column(name = "REGIST_DATETIME")
	public GeneralDateTime registerDate;
	
}
