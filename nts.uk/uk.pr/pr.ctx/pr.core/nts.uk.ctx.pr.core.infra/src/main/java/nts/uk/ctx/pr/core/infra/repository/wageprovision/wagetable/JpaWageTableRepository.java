package nts.uk.ctx.pr.core.infra.repository.wageprovision.wagetable;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTable;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.wagetable.QpbmtWageTable;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.wagetable.QpbmtWageTablePk;

import javax.ejb.Stateless;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class JpaWageTableRepository extends JpaRepository implements WageTableRepository {

	@Override
	public List<WageTable> getAllWageTable(String companyId) {
		String query = "SELECT a FROM QpbmtWageTable a WHERE a.pk.companyId = :cid ORDER BY a.pk.code";
		return this.queryProxy().query(query, QpbmtWageTable.class).setParameter("cid", companyId)
				.getList(i -> i.toDomain());
	}

	@Override
	public List<WageTable> getWageTableByYearMonth(String companyId, int yearMonth) {
		String query = "SELECT a FROM QpbmtWageTable a JOIN QpbmtWageTableHistory f ON a.pk.companyId = f.pk.companyId AND a.pk.code = f.pk.code WHERE f.pk.companyId =:cid AND " +
				"f.startYm <= :yearMonth AND f.endYm >= :yearMonth  ORDER BY a.pk.code";
		return this.queryProxy().query(query, QpbmtWageTable.class).setParameter("cid", companyId).setParameter("yearMonth", yearMonth).getList(i -> i.toDomain());
	}

	@Override
	public Optional<WageTable> getWageTableById(String companyId, String code) {
		Optional<QpbmtWageTable> optEntity = this.queryProxy().find(new QpbmtWageTablePk(companyId, code),
				QpbmtWageTable.class);
		return optEntity.isPresent() ? Optional.of(optEntity.get().toDomain()) : Optional.empty();
	}

	@Override
	public List<WageTable> getWageTableByCodes(String cid, List<String> wageTableCodes) {
		if (wageTableCodes == null || wageTableCodes.isEmpty())
			return Collections.emptyList();
		String query = "SELECT f FROM QpbmtWageTable f WHERE  f.pk.companyId = :cid AND f.pk.code IN :wageTableCodes"
				+ " ORDER BY f.pk.code ASC";
		return this.queryProxy().query(query, QpbmtWageTable.class).setParameter("cid", cid)
				.setParameter("wageTableCodes", wageTableCodes).getList(QpbmtWageTable::toDomain);
	}

	@Override
	public void add(WageTable domain) {
		this.commandProxy().insert(QpbmtWageTable.fromDomain(domain));
	}

	@Override
	public void update(WageTable domain) {
		Optional<QpbmtWageTable> optEntity = this.queryProxy().find(new QpbmtWageTablePk(domain.getCid(), domain.getWageTableCode().v()), QpbmtWageTable.class);
		if (optEntity.isPresent()) {
			QpbmtWageTable entity = optEntity.get();
			entity.name = domain.getWageTableName().v();
			entity.memo = domain.getRemarkInformation().isPresent() ? domain.getRemarkInformation().get().v() : null;
			this.commandProxy().update(entity);
		}
	}

	@Override
	public void remove(String cid, String code) {
		this.commandProxy().remove(QpbmtWageTable.class, new QpbmtWageTablePk(cid, code));
	}

}
