package nts.uk.ctx.pr.core.infra.entity.payment.banktranfer;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
@Table(name="QBKDT_BANK_TRANSFER")
public class QbkdtBankTransfer {
	
	@EmbeddedId
	public QbkdtBankTransferPk qbkdtBankTransferPk;
	
	
	
}
