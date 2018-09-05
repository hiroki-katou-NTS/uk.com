package nts.uk.ctx.exio.infra.repository.qmm.itemrangeset;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.qmm.itemrangeset.ItemRangeSettingInitialValue;
import nts.uk.ctx.exio.dom.qmm.itemrangeset.ItemRangeSettingInitialValueRepository;
import nts.uk.ctx.exio.infra.entity.qmm.itemrangeset.QpbmtItemRangeSetInit;
import nts.uk.ctx.exio.infra.entity.qmm.itemrangeset.QpbmtItemRangeSetInitPk;

@Stateless
public class JpaItemRangeSetInitRepository extends JpaRepository implements ItemRangeSettingInitialValueRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtItemRangeSetInit f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.itemRangeSetInitPk.cid =:cid AND  f.itemRangeSetInitPk.salaryItemId =:salaryItemId ";

	@Override
	public List<ItemRangeSettingInitialValue> getAllItemRangeSetInit() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtItemRangeSetInit.class)
				.getList(item -> item.toDomain());
	}

	@Override
	public Optional<ItemRangeSettingInitialValue> getItemRangeSetInitById(String cid, String salaryItemId) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtItemRangeSetInit.class).setParameter("cid", cid)
				.setParameter("salaryItemId", salaryItemId).getSingle(c -> c.toDomain());
	}

	@Override
	public void add(ItemRangeSettingInitialValue domain) {
		this.commandProxy().insert(QpbmtItemRangeSetInit.toEntity(domain));
	}

	@Override
	public void update(ItemRangeSettingInitialValue domain) {
		this.commandProxy().update(QpbmtItemRangeSetInit.toEntity(domain));
	}

	@Override
	public void remove(String cid, String salaryItemId) {
		this.commandProxy().remove(QpbmtItemRangeSetInit.class, new QpbmtItemRangeSetInitPk(cid, salaryItemId));
	}
}