package nts.uk.ctx.pr.transfer.infra.repository.bank;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.transfer.dom.bank.Bank;
import nts.uk.ctx.pr.transfer.dom.bank.BankBranch;
import nts.uk.ctx.pr.transfer.dom.bank.BankBranchRepository;
import nts.uk.ctx.pr.transfer.dom.bank.BankRepository;
import nts.uk.ctx.pr.transfer.infra.entity.bank.QxxmtBank;
import nts.uk.ctx.pr.transfer.infra.entity.bank.QxxmtBankBranch;
import nts.uk.ctx.pr.transfer.infra.entity.bank.QxxmtBankPk;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class JpaBankAndBranchRepository extends JpaRepository implements BankRepository, BankBranchRepository {

	@Override
	public Optional<BankBranch> findBranch(String companyId, String branchId) {
		String query = "SELECT b FROM QxxmtBankBranch b WHERE b.companyId = :companyId AND b.branchId = :branchId";
		return this.queryProxy().query(query, QxxmtBankBranch.class).setParameter("companyId", companyId)
				.setParameter("branchId", branchId).getSingle(c -> c.toDomain());
	}

	@Override
	public List<BankBranch> findAllBranch(String companyId, List<String> bankCodes) {
		if (bankCodes.isEmpty())
			return Collections.emptyList();
		String query = "SELECT b FROM QxxmtBankBranch b WHERE b.companyId = :companyId AND b.bankCode IN :bankCodes ORDER BY b.bankCode, b.bankBranchCode";
		return this.queryProxy().query(query, QxxmtBankBranch.class).setParameter("companyId", companyId)
				.setParameter("bankCodes", bankCodes).getList(c -> c.toDomain());
	}

	@Override
	public List<BankBranch> findAllBranchByBank(String companyId, String bankCode) {
		String query = "SELECT b FROM QxxmtBankBranch b WHERE b.companyId = :companyId AND b.bankCode = :bankCode ORDER BY b.bankBranchCode";
		return this.queryProxy().query(query, QxxmtBankBranch.class).setParameter("companyId", companyId)
				.setParameter("bankCode", bankCode).getList(c -> c.toDomain());
	}

	@Override
	public boolean checkExistBranch(String companyCode, String bankCode, String branchCode) {
		String query = "SELECT b FROM QxxmtBankBranch b WHERE b.companyId = :companyId AND b.bankCode = :bankCode AND b.bankBranchCode = : branchCode";
		val result = this.queryProxy().query(query, QxxmtBankBranch.class).setParameter("companyId", companyCode)
				.setParameter("bankCode", bankCode).setParameter("branchCode", branchCode).getList(c -> c.toDomain());
		if (result.isEmpty())
			return false;
		else
			return true;
	}

	@Override
	public List<Bank> findAllBank(String companyId) {
		String query = "SELECT b FROM QxxmtBank b WHERE b.pk.companyId = :companyId  ORDER BY b.pk.bankCode";
		return this.queryProxy().query(query, QxxmtBank.class).setParameter("companyId", companyId)
				.getList(c -> c.toDomain());
	}

	@Override
	public Optional<Bank> findBank(String companyId, String bankCode) {
		val entity = this.queryProxy().find(new QxxmtBankPk(companyId, bankCode), QxxmtBank.class);
		return entity.isPresent() ? Optional.of(entity.get().toDomain()) : Optional.empty();
	}

	@Override
	public void addBranch(BankBranch bank) {
		this.commandProxy().insert(new QxxmtBankBranch(bank));
	}

	@Override
	public void updateBranch(BankBranch bank) {
		this.commandProxy().update(new QxxmtBankBranch(bank));
	}

	@Override
	public void removeBranch(String companyId, String branchId) {
		this.commandProxy().remove(QxxmtBankBranch.class, branchId);
	}

	@Override
	public void removeListBranchFromBank(String companyId, String bankCode) {
		String sql = "DELETE FROM QxxmtBankBranch a WHERE a.companyId = :companyId AND a.bankCode = :bankCode";
		this.getEntityManager().createQuery(sql).setParameter("companyId", companyId).setParameter("bankCode", bankCode)
				.executeUpdate();
	}

	@Override
	public void addBank(Bank bank) {
		this.commandProxy().insert(new QxxmtBank(bank));
	}

	@Override
	public void updateBank(Bank bank) {
		this.commandProxy().update(new QxxmtBank(bank));
	}

	@Override
	public void removeBank(String companyId, String bankCode) {
		this.commandProxy().remove(QxxmtBank.class, new QxxmtBankPk(companyId, bankCode));
	}

	@Override
	public void removeListBank(String companyId, List<String> bankCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean checkExistBank(String companyId, String bankCode) {
		return this.findBank(companyId, bankCode).isPresent();
	}

	@Override
	public void removeListBranch(String companyId, List<String> branchIds) {
		if (!branchIds.isEmpty()) {
			String sql = "DELETE FROM QxxmtBankBranch a WHERE a.companyId = :companyId AND a.branchId IN :branchIds";
			this.getEntityManager().createQuery(sql).setParameter("companyId", companyId)
					.setParameter("branchIds", branchIds).executeUpdate();
		}
	}

}
