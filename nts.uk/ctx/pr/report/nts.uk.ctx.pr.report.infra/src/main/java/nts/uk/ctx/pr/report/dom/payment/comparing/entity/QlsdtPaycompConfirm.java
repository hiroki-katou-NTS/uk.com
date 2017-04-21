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
@Table(name = "QLSDT_PAYCOMP_CONFIRM")
public class QlsdtPaycompConfirm extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public QlsdtPaycompConfirmPK paycompConfirmPK;

	@Basic(optional = false)
	@Column(name = "CONFIRM_STS")
	public int confirmSTS;
	
	@Basic(optional = false)
	@Column(name = "DIFF_AMOUNT")
	public BigDecimal diffAmount;
	
	@Basic(optional = false)
	@Column(name = "DIFF_REASON")
	public String diffReason;
	
	
	@Override
	protected Object getKey() {
		return paycompConfirmPK;
	}

}
