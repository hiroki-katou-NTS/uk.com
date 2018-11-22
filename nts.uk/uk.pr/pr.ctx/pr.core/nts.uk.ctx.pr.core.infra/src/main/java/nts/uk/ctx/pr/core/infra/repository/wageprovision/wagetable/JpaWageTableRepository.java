package nts.uk.ctx.pr.core.infra.repository.wageprovision.wagetable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTable;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.wagetable.QpbmtWageTable;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.wagetable.QpbmtWageTablePk;

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
	public Optional<WageTable> getWageTableById(String companyId, String code) {
		// TODO Auto-generated method stub
		return null;
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
		this.commandProxy().update(QpbmtWageTable.fromDomain(domain));
	}

	@Override
	public void remove(String cid, String code) {
		this.commandProxy().remove(QpbmtWageTable.class, new QpbmtWageTablePk(cid, code));
	}

}
