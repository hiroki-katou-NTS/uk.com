package nts.uk.ctx.pr.report.dom.payment.comparing.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QlsptPaycompFormHeadPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "CCD")
	@NotNull
	public String companyCode;

	@Column(name = "FORM_CD")
	@NotNull
	public String formCode;

}
