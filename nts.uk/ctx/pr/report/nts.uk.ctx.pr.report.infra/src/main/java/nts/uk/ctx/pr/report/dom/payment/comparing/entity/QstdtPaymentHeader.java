package nts.uk.ctx.pr.report.dom.payment.comparing.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "QSTDT_PAYMENT_HEADER")
public class QstdtPaymentHeader extends UkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public QstdtPaymentHeaderPK paymentHeaderPK;
	
	@Basic(optional = false)
	@Column(name = "MAKE_METHOD_FLG")
	public int makeMethodFLG;

	@Override
	protected Object getKey() {
		return paymentHeaderPK;
	}

	

}
