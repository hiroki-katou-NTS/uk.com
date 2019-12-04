package nts.uk.ctx.at.record.infra.entity.reservation.bentomenu;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KRCMT_BENTO_MENU")
@AllArgsConstructor
public class KrcmtBentoMenu extends UkJpaEntity {
	
	@EmbeddedId
	public KrcmtBentoMenuPK pk;
	
	@Column(name = "RESERVATION_FRAME1_NAME")
	public String reservationFrameName1;
	
	@Column(name = "RESERVATION_FRAME1_START_TIME")
	public Integer reservationStartTime1;
	
	@Column(name = "RESERVATION_FRAME1_END_TIME")
	public Integer reservationEndTime1;
	
	@Column(name = "RESERVATION_FRAME2_NAME")
	public String reservationFrameName2;
	
	@Column(name = "RESERVATION_FRAME2_START_TIME")
	public Integer reservationStartTime2;
	
	@Column(name = "RESERVATION_FRAME2_END_TIME")
	public Integer reservationEndTime2;

	@Override
	protected Object getKey() {
		return pk;
	}
	
}
