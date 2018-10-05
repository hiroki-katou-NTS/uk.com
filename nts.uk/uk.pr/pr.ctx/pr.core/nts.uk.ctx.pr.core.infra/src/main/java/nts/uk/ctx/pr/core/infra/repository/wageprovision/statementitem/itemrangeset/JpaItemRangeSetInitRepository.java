package nts.uk.ctx.pr.core.infra.repository.wageprovision.statementitem.itemrangeset;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.itemrangeset.ItemRangeSet;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.itemrangeset.ItemRangeSetRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementitem.itemrangeset.QpbmtItemRangeSetInit;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.statementitem.itemrangeset.QpbmtItemRangeSetInitPk;

@Stateless
public class JpaItemRangeSetInitRepository extends JpaRepository implements ItemRangeSetRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtItemRangeSetInit f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.itemRangeSetInitPk.cid =:cid AND  f.itemRangeSetInitPk.salaryItemId =:salaryItemId ";

	@Override
	public List<ItemRangeSet> getAllItemRangeSetInit() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtItemRangeSetInit.class)
				.getList(item -> item.toDomain());
	}

	@Override
	public Optional<ItemRangeSet> getItemRangeSetInitById(String cid, String salaryItemId) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtItemRangeSetInit.class).setParameter("cid", cid)
				.setParameter("salaryItemId", salaryItemId).getSingle(c -> c.toDomain());
	}

	@Override
	public void add(ItemRangeSet domain) {
		this.commandProxy().insert(QpbmtItemRangeSetInit.toEntity(domain));
	}

	@Override
	public void update(ItemRangeSet domain) {
		this.commandProxy().update(QpbmtItemRangeSetInit.toEntity(domain));
	}

	@Override
	public void remove(String cid, String salaryItemId) {
		if (this.getItemRangeSetInitById(cid, salaryItemId).isPresent()) {
			this.commandProxy().remove(QpbmtItemRangeSetInit.class, new QpbmtItemRangeSetInitPk(cid, salaryItemId));
		}
	}
}