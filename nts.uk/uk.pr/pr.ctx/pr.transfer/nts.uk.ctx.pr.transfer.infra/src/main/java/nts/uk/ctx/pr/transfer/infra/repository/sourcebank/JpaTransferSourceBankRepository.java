package nts.uk.ctx.pr.transfer.infra.repository.sourcebank;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.transfer.dom.sourcebank.TransferSourceBank;
import nts.uk.ctx.pr.transfer.dom.sourcebank.TransferSourceBankRepository;
import nts.uk.ctx.pr.transfer.infra.entity.sourcebank.QbtmtTrfSrcBank;
import nts.uk.ctx.pr.transfer.infra.entity.sourcebank.QbtmtTrfSrcBankPk;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class JpaTransferSourceBankRepository extends JpaRepository implements TransferSourceBankRepository {

	@Override
	public void addSourceBank(TransferSourceBank sourceBank) {
		this.commandProxy().insert(new QbtmtTrfSrcBank(sourceBank));
	}

	@Override
	public List<TransferSourceBank> getAllSourceBank(String companyId) {
		String query = "SELECT b FROM QbtmtTrfSrcBank b WHERE b.pk.companyId = :companyId ORDER BY b.pk.code";
		return this.queryProxy().query(query, QbtmtTrfSrcBank.class).setParameter("companyId", companyId)
				.getList(b -> b.toDomain());
	}

	@Override
	public Optional<TransferSourceBank> getSourceBank(String companyId, String code) {
		Optional<QbtmtTrfSrcBank> optEntity = this.queryProxy().find(new QbtmtTrfSrcBankPk(companyId, code),
				QbtmtTrfSrcBank.class);
		return optEntity.isPresent() ? Optional.of(optEntity.get().toDomain()) : Optional.empty();
	}

	@Override
	public void updateSourceBank(TransferSourceBank sourceBank) {
		this.commandProxy().update(new QbtmtTrfSrcBank(sourceBank));
	}

	@Override
	public void removeSourceBank(String companyId, String code) {
		this.commandProxy().remove(QbtmtTrfSrcBank.class, new QbtmtTrfSrcBankPk(companyId, code));
	}

	@Override
	public List<TransferSourceBank> getSourceBankByBranchId(String companyId, List<String> branchIds) {
		if (branchIds.isEmpty()) return Collections.emptyList();
		String query = "SELECT b FROM QbtmtTrfSrcBank b WHERE b.pk.companyId = :companyId AND b.branchId IN :branchIds ORDER BY b.pk.code";
		return this.queryProxy().query(query, QbtmtTrfSrcBank.class).setParameter("companyId", companyId)
				.setParameter("branchIds", branchIds).getList(b -> b.toDomain());
	}

}
