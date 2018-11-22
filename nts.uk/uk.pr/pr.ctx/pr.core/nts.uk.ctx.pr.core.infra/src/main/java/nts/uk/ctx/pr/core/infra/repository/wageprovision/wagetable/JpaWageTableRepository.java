package nts.uk.ctx.pr.core.infra.repository.wageprovision.wagetable;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTable;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.wagetable.QpbmtWageTable;

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
	public void add(WageTable domain) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(WageTable domain) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(String companyId, String code) {
		// TODO Auto-generated method stub

	}

}
