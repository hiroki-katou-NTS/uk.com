package nts.uk.ctx.pr.transfer.infra.repository.sourcebank;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.transfer.dom.sourcebank.TransferSourceBank;
import nts.uk.ctx.pr.transfer.dom.sourcebank.TransferSourceBankRepository;
import nts.uk.ctx.pr.transfer.infra.entity.sourcebank.QxxmtTrfSrcBank;
import nts.uk.ctx.pr.transfer.infra.entity.sourcebank.QxxmtTrfSrcBankPk;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class JpaTransferSourceBankRepository extends JpaRepository implements TransferSourceBankRepository {

	@Override
	public void addSourceBank(TransferSourceBank sourceBank) {
		this.commandProxy().insert(new QxxmtTrfSrcBank(sourceBank));
	}

	@Override
	public List<TransferSourceBank> getAllSourceBank(String companyId) {
		String query = "SELECT b FROM QxxmtTrfSrcBank b WHERE b.pk.companyId = :companyId ORDER BY b.pk.code";
		return this.queryProxy().query(query, QxxmtTrfSrcBank.class).setParameter("companyId", companyId)
				.getList(b -> b.toDomain());
	}

	@Override
	public Optional<TransferSourceBank> getSourceBank(String companyId, String code) {
		Optional<QxxmtTrfSrcBank> optEntity = this.queryProxy().find(new QxxmtTrfSrcBankPk(companyId, code),
				QxxmtTrfSrcBank.class);
		return optEntity.isPresent() ? Optional.of(optEntity.get().toDomain()) : Optional.empty();
	}

	@Override
	public void updateSourceBank(TransferSourceBank sourceBank) {
		this.commandProxy().update(new QxxmtTrfSrcBank(sourceBank));
	}

	@Override
	public void removeSourceBank(String companyId, String code) {
		this.commandProxy().remove(QxxmtTrfSrcBank.class, new QxxmtTrfSrcBankPk(companyId, code));
	}

}
