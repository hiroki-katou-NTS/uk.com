package nts.uk.ctx.pr.report.dom.payment.comparing.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "QCAMT_ITEM")
public class QcamtItem extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public QcamtItemPK qcamtItemPK;

	@Basic(optional = false)
	@Column(name = "ITEM_AB_NAME")
	public BigDecimal itemName;

	@Override
	protected Object getKey() {
		return null;
	}

}
