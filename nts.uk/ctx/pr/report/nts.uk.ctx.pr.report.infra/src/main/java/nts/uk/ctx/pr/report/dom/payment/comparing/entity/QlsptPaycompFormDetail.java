package nts.uk.ctx.pr.report.dom.payment.comparing.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "QLSPT_PAYCOMP_FORM_DETAIL")
public class QlsptPaycompFormDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public QlsptPaycompFormDetailPK paycompFormDetailPK;

	@Basic(optional = false)
	@Column(name = "DISP_ORDER")
	public BigDecimal dispOrder;

}
