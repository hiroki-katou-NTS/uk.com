package nts.uk.ctx.at.record.infra.entity.reservation.bentomenu;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KRCMT_BENTO")
@AllArgsConstructor
public class KrcmtBento extends UkJpaEntity {
	
	@EmbeddedId
	public KrcmtBentoPK pk;
	
	@Column(name = "BENTO_NAME")
	public String bentoName;
	
	@Column(name = "UNIT_NAME")
	public String unitName;
	
	@Column(name = "PRICE1")
	public Integer price1;
	
	@Column(name = "PRICE2")
	public Integer price2;
	
	@Column(name = "RESERVATION1_ATR")
	public boolean reservationAtr1;
	
	@Column(name = "RESERVATION2_ATR")
	public boolean reservationAtr2;

	@Override
	protected Object getKey() {
		return pk;
	}
	
}
