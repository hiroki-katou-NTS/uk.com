package nts.uk.ctx.pr.core.infra.entity.payment.banktranfer;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QbkdtBankTransferPk {
	@Column(name="CCD")
	public String companyCode;
	
}
