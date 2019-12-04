package nts.uk.ctx.at.record.infra.entity.reservation.bento;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KRCDT_RESERVATION")
@AllArgsConstructor
public class KrcdtReservation extends UkJpaEntity {
	
	@EmbeddedId
	public KrcdtReservationPK pk;

	@Column(name = "RESERVATION_YMD")
	public GeneralDate date;
	
	@Column(name = "RESERVATION_FRAME")
	public Integer frameNo;
	
	@Column(name = "CARD_NO")
	public String cardNo;
	
	@Column(name = "ORDERED")
	public boolean ordered;
	
	@Override
	protected Object getKey() {
		return pk;
	}
	
}
