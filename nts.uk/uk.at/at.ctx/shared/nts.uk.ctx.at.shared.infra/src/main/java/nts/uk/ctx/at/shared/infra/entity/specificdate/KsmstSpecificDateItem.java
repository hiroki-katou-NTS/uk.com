package nts.uk.ctx.at.shared.infra.entity.specificdate;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSMST_SPECIFIC_DATE_ITEM")
public class KsmstSpecificDateItem extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KsmstSpecificDateItemPK ksmstSpecificDateItemPK;

	@Column(name = "USE_ATR")
	public BigDecimal useAtr;

	@Column(name = "SPECIFIC_DATE_ITEM_NO")
	public BigDecimal itemNo;

	@Column(name = "NAME")
	public String name;
	
	@Override
	protected Object getKey() {
		return ksmstSpecificDateItemPK;
	}
}
