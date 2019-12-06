package nts.uk.ctx.at.record.infra.entity.reservation.bento;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationCount;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationDetail;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KRCDT_RESERVATION_DETAIL")
@AllArgsConstructor
@NoArgsConstructor
public class KrcdtReservationDetail extends UkJpaEntity {
	
	@EmbeddedId
	public KrcdtReservationDetailPK pk;
	
	@Column(name = "CONTRACT_CD")
	public String contractCD;
	
	@Column(name = "QUANTITY")
	public Integer quantity;
	
	@Column(name = "AUTO_RESERVATION_ATR")
	public boolean autoReservation;
	
	@ManyToOne
    @PrimaryKeyJoinColumns({
    	@PrimaryKeyJoinColumn(name = "CID", referencedColumnName = "CID"),
    	@PrimaryKeyJoinColumn(name = "RESERVATION_ID", referencedColumnName = "RESERVATION_ID")
    })
	public KrcdtReservation reservation;

	@Override
	protected Object getKey() {
		return pk;
	}
	
	public BentoReservationDetail toDomain() {
		return new BentoReservationDetail(
				pk.frameNo, 
				pk.registerDate, 
				autoReservation, 
				new BentoReservationCount(quantity));
	}
	
}
