package nts.uk.ctx.pr.report.dom.payment.refundsetting.refundlayout.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QrfdtRefundLayoutSetPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = "CCD")
	@NotNull
	public String companyCode;

	@Basic(optional = false)
	@Column(name = "PRINT_TYPE")
	public int printType;
}
