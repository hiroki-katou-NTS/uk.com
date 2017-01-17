package nts.uk.ctx.pr.core.infra.repository.payment.banktranfer;

import java.util.Optional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.payment.banktranfer.BankTranfer;
import nts.uk.ctx.pr.core.dom.payment.banktranfer.BankTranferRepository;
import nts.uk.ctx.pr.core.infra.entity.payment.banktranfer.QbkdtBankTransfer;
import nts.uk.ctx.pr.core.infra.entity.payment.banktranfer.QbkdtBankTransferPk;

public class JpaBankTranferRepository extends JpaRepository implements BankTranferRepository {

	@Override
	public void add(BankTranfer root) {
//		QbkdtBankTransferPk key = new QbkdtBankTransferPk(root.getCompanyCode());
//		
//		QbkdtBankTransfer entity = new QbkdtBankTransfer(key);
//		// TODO Auto-generated method stub
//		this.commandProxy().insert(entity);
//		this.queryProxy().find(primaryKey, entityClass);
//		this.queryProxy().query("SELECT FORM...");
	}
	
//	public Optional<BankTranfer> find(String companyCode) {
//		QbkdtBankTransferPk key = new QbkdtBankTransferPk(root.getCompanyCode());
//		
//		Optional<QbkdtBankTransfer> entityOpt = this.queryProxy().find(key, QbkdtBankTransfer.class);
//		
//		QbkdtBankTransfer entity = entityOpt.get();
//		
//		
//		return entityOpt;
//	}

}
