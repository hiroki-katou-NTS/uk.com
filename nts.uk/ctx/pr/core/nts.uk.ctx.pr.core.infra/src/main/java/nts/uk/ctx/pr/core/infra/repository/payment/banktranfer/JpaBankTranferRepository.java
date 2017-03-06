package nts.uk.ctx.pr.core.infra.repository.payment.banktranfer;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.payment.banktranfer.BankTranfer;
import nts.uk.ctx.pr.core.dom.payment.banktranfer.BankTranferRepository;
import nts.uk.ctx.pr.core.infra.entity.payment.banktranfer.QbkdtBankTransfer;
import nts.uk.ctx.pr.core.infra.entity.payment.banktranfer.QbkdtBankTransferPK;

@RequestScoped
public class JpaBankTranferRepository extends JpaRepository implements BankTranferRepository {

	@Override
	public void add(BankTranfer root) {
		QbkdtBankTransferPK key = toPK(root);
		
		this.commandProxy().insert(toEntity(root));
	}
	
	public Optional<BankTranfer> find(String ccd, String pid, String fromBankCd, String fromBranchCd, int fromAccountAtr, String fromAccountNo, String toBankCd, String toBranchCd, int toAccountAtr, String toAccountNo, int payBonusAtr, GeneralDate payDate, int sparePayAtr) {
		QbkdtBankTransferPK key = new QbkdtBankTransferPK(ccd, pid, fromBankCd, fromBranchCd, fromAccountAtr, fromAccountNo, toBankCd, toBranchCd, toAccountAtr, toAccountNo, payBonusAtr, payDate, sparePayAtr);
		
		Optional<BankTranfer> entityOpt = this.queryProxy().find(key, QbkdtBankTransfer.class)
				.map(x -> toDomain(x));
		
		return entityOpt;
	}
	
	private BankTranfer toDomain(QbkdtBankTransfer x) {
		BankTranfer bankTranfer = BankTranfer.createFromJavaType(
				x.qbkdtBankTransferPK.pid, 
				x.qbkdtBankTransferPK.ccd, 
				x.cnameKana, 
				x.depcd,
				x.qbkdtBankTransferPK.payDate, 
				x.qbkdtBankTransferPK.payBonusAtr, 
				x.paymentMny,
				x.processingNo, 
				x.processingYm,
				x.qbkdtBankTransferPK.sparePayAtr);
		
		bankTranfer.fromBank(x.qbkdtBankTransferPK.fromAccountAtr, x.toAccountKnName, x.qbkdtBankTransferPK.fromAccountNo, x.qbkdtBankTransferPK.fromBankCd, x.fromBankKnName, x.qbkdtBankTransferPK.fromBranchCd, x.fromBranchKnName);
		bankTranfer.toBank(x.qbkdtBankTransferPK.toAccountAtr, x.toAccountKnName, x.qbkdtBankTransferPK.toAccountNo, x.qbkdtBankTransferPK.toBankCd, x.toBankKnName, x.qbkdtBankTransferPK.toBranchCd, x.toBranchKnName);
		
		return bankTranfer;
	}
	
	private QbkdtBankTransfer toEntity(BankTranfer root) {
		QbkdtBankTransferPK key = toPK(root);
		
		QbkdtBankTransfer entity = new QbkdtBankTransfer(key);
		
		return entity;
	}

	private QbkdtBankTransferPK toPK(BankTranfer root) {
		return new QbkdtBankTransferPK(
				root.getCompanyCode().v(), 
				root.getPersonId().v(), 
				root.getFromBank().getBankCode(), 
				root.getFromBank().getBranchCode(), 
				root.getFromBank().getAccountAtr(), 
				root.getFromBank().getAccountNo(), 
				root.getToBank().getBankCode(), 
				root.getToBank().getBranchCode(), 
				root.getToBank().getAccountAtr(), 
				root.getToBank().getAccountNo(), 
				root.getPaymentBonusAtr().value,
				root.getPaymentDate(), 
				root.getSparePaymentAtr().value);
	}
}
