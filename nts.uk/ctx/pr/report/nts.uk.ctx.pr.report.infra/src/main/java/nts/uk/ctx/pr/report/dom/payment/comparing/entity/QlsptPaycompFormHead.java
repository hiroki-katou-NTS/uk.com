package nts.uk.ctx.pr.report.dom.payment.comparing.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "QLSPT_PAYCOMP_FORM_HEAD")
public class QlsptPaycompFormHead implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public QlsptPaycompFormHeadPK paycompFormHeadPK;
	
	@Basic(optional = false)
	@Column(name = "FORM_NAME")
	public String formName;

}
