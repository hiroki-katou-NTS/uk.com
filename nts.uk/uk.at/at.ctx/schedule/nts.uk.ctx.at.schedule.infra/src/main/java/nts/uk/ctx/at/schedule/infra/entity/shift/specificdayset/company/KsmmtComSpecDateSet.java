package nts.uk.ctx.at.schedule.infra.entity.shift.specificdayset.company;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSMMT_COM_SPEC_DATE_SET")
public class KsmmtComSpecDateSet extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "CID")
	public String companyId;

	@Column(name = "SPECIFIC_DATE")
	public BigDecimal specificDate;

	@Column(name = "SPECIFIC_DATE_ITEM_NO")
	public BigDecimal specificDateItemNo;

	@Override
	protected Object getKey() {
		return null;
	}

}
