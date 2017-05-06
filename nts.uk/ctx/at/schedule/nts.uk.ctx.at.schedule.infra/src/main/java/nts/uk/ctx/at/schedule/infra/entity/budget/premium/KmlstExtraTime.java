package nts.uk.ctx.at.schedule.infra.entity.budget.premium;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="KMLST_EXTRA_ITEM")
public class KmlstExtraTime extends UkJpaEntity{
	@EmbeddedId
	public KmlspExtraTimePK extraItemPK;
	
	@Column(name="PREMIUM_NAME")
	public String premiumName;
	
	@Column(name="TIME_ITEM_CD")
	public String timeItemCD;
	
	@Column(name="USE_ATR")
	public int useAtr;

	@Override
	protected Object getKey() {
		return extraItemPK;
	}
}
