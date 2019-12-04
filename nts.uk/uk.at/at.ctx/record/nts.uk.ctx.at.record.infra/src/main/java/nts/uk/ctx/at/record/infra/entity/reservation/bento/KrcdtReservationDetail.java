package nts.uk.ctx.at.record.infra.entity.reservation.bento;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KRCDT_RESERVATION_DETAIL")
@AllArgsConstructor
public class KrcdtReservationDetail extends UkJpaEntity {
	
	@EmbeddedId
	public KrcdtReservationDetailPK pk;
	
	@Column(name = "QUANTITY")
	public Integer quantity;
	
	@Column(name = "AUTO_RESERVATION_ATR")
	public boolean autoReservation;

	@Override
	protected Object getKey() {
		return pk;
	}
	
}
