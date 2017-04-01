package nts.uk.ctx.pr.core.infra.repository.payment.banktranfer;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.payment.banktranfer.BankTranfer;
import nts.uk.ctx.pr.core.dom.payment.banktranfer.BankTranferParam;
import nts.uk.ctx.pr.core.dom.payment.banktranfer.BankTranferRepository;
import nts.uk.ctx.pr.core.infra.entity.payment.banktranfer.QbkdtBankTransfer;
import nts.uk.ctx.pr.core.infra.entity.payment.banktranfer.QbkdtBankTransferPK;

@Stateless
public class JpaBankTranferRepository extends JpaRepository implements BankTranferRepository {

	private final String SEL = "SELECT b FROM QbkdtBankTransfer b ";
	
	private final String SEL_1 = SEL + "WHERE b.qbkdtBankTransferPK.ccd = :companyCode "
								 + "AND b.qbkdtBankTransferPK.fromBranchId = :fromBranchId "
								 + "AND b.processingNo = :processingNo "
								 + "AND b.processingYm = :processingYm "
								 + "AND b.qbkdtBankTransferPK.payDate = :payDate "
								 + "AND b.qbkdtBankTransferPK.sparePayAtr = :sparePayAtr";
	
	private final String SEL_2 = SEL + "WHERE b.qbkdtBankTransferPK.ccd = :companyCode "
								+ "AND b.qbkdtBankTransferPK.pid = :personId "
								+ "AND b.qbkdtBankTransferPK.fromBranchId = :fromBranchId "
								+ "AND b.qbkdtBankTransferPK.fromAccountAtr = :fromAccountAtr "
								+ "AND b.qbkdtBankTransferPK.fromAccountNo = :fromAccountNo "
								+ "AND b.qbkdtBankTransferPK.toBranchId = :toBranchId "
								+ "AND b.qbkdtBankTransferPK.toAccountAtr = :toAccountAtr "
								+ "AND b.qbkdtBankTransferPK.toAccountNo = :toAccountNo "
								+ "AND b.qbkdtBankTransferPK.payBonusAtr = :payBonusAtr "
								+ "AND b.qbkdtBankTransferPK.payDate = :payDate "
								+ "AND b.qbkdtBankTransferPK.sparePayAtr = :sparePayAtr";
	
	@Override
	public void add(BankTranfer root) {		
		this.commandProxy().insert(toEntity(root));
	}
	
	@Override
	public void update(BankTranfer root) {
		this.commandProxy().update(toEntity(root));
	}
	
	@Override
	public Optional<BankTranfer> find(BankTranferParam param) {
		QbkdtBankTransferPK key = new QbkdtBankTransferPK(
							param.getCompanyCode(),
							param.getPersonId(), 
							param.getFromBranchId(), 
							param.getFromAccountAtr(), 
							param.getFromAccountNo(),
							param.getToBranchId(),
							param.getToAccountAtr(), 
							param.getToAccountNo(), 
							param.getPayBonusAtr(), 
							param.getProcessNo(),
							param.getPayDate(),
							param.getSparePayAtr());
		
		Optional<BankTranfer> entityOpt = this.queryProxy().find(key, QbkdtBankTransfer.class)
				.map(x -> toDomain(x));
		
		return entityOpt;
	}
	
	@Override
	public List<BankTranfer> findBySEL1(BankTranferParam param) {
		return this.queryProxy().query(SEL_1, QbkdtBankTransfer.class)
				.setParameter("companyCode", param.getCompanyCode())
				.setParameter("fromBranchId", param.getFromBranchId())
				.setParameter("processingNo", param.getProcessNo())
				.setParameter("processingYm", param.getProcessYearMonth())
				.setParameter("payDate", param.getPayDate())
				.setParameter("sparePayAtr", param.getSparePayAtr())
				.getList(x -> toDomain(x));
	}
	
	@Override
	public List<BankTranfer> findBySEL2(BankTranferParam param) {
		return this.queryProxy().query(SEL_2, QbkdtBankTransfer.class)
				.setParameter("companyCode", param.getCompanyCode())
				.setParameter("personId", param.getPersonId())
				.setParameter("fromBranchId", param.getFromBranchId())
				.setParameter("fromAccountAtr", param.getFromAccountAtr())
				.setParameter("fromAccountNo", param.getFromAccountNo())
				.setParameter("toBranchId", param.getToBranchId())
				.setParameter("toAccountAtr", param.getToAccountAtr())
				.setParameter("toAccountNo", param.getToAccountNo())
				.setParameter("payBonusAtr", param.getPayBonusAtr())
				.setParameter("payDate", param.getPayDate())
				.setParameter("sparePayAtr", param.getSparePayAtr())
				.getList(x -> toDomain(x));
	}
	
	/**
	 * Convert to domain
	 * @param x entity
	 * @return BankTranfer
	 */
	private BankTranfer toDomain(QbkdtBankTransfer x) {
		BankTranfer bankTranfer = BankTranfer.createFromJavaType(
				x.qbkdtBankTransferPK.ccd, 
				x.cnameKana, 
				x.qbkdtBankTransferPK.pid, 
				x.depcd,
				x.qbkdtBankTransferPK.payDate, 
				x.qbkdtBankTransferPK.payBonusAtr, 
				x.paymentMny,
				x.qbkdtBankTransferPK.processingNo, 
				x.processingYm,
				x.qbkdtBankTransferPK.sparePayAtr);
		
		bankTranfer.fromBank(x.qbkdtBankTransferPK.fromBranchId, x.fromBankKnName, x.fromBranchKnName, x.qbkdtBankTransferPK.fromAccountAtr, x.qbkdtBankTransferPK.fromAccountNo);
		bankTranfer.toBank(x.qbkdtBankTransferPK.toBranchId, x.toBankKnName, x.toBranchKnName, x.qbkdtBankTransferPK.toAccountAtr, x.toAccountKnName, x.qbkdtBankTransferPK.toAccountNo);
		
		return bankTranfer;
	}
	
	/**
	 * Convert to entity
	 * @param root
	 * @return QbkdtBankTransfer
	 */
	private QbkdtBankTransfer toEntity(BankTranfer root) {
		QbkdtBankTransferPK key = toPK(root);
		
		QbkdtBankTransfer entity = new QbkdtBankTransfer(key);
		entity.cnameKana = root.getCompanyNameKana();
		entity.fromBankKnName = root.getFromBank().getBankNameKana();
		entity.fromBranchKnName = root.getFromBank().getBranchNameKana();
		entity.toBankKnName = root.getToBank().getBankNameKana();
		entity.toBranchKnName = root.getToBank().getBranchNameKana();
		entity.toAccountKnName = root.getToBank().getAccountNameKana();
		entity.depcd = root.getDepartmentCode();
		entity.paymentMny = root.getPaymentMoney().v();
		entity.processingYm = root.getProcessingYM().v();
		
		return entity;
	}

	/**
	 * Convert to pk object
	 * @param root
	 * @return QbkdtBankTransferPK
	 */
	private QbkdtBankTransferPK toPK(BankTranfer root) {
		return new QbkdtBankTransferPK(
				root.getCompanyCode(), 
				root.getPersonId().v(), 
				root.getFromBank().getBranchId(), 
				root.getFromBank().getAccountAtr(), 
				root.getFromBank().getAccountNo(), 
				root.getToBank().getBranchId(), 
				root.getToBank().getAccountAtr(), 
				root.getToBank().getAccountNo(), 
				root.getPaymentBonusAtr().value,
				root.getProcessingNo(),
				root.getPaymentDate(), 
				root.getSparePaymentAtr().value);
	}
}
