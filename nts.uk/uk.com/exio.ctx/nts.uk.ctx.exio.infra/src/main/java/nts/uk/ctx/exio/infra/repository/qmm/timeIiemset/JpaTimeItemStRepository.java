package nts.uk.ctx.exio.infra.repository.qmm.timeIiemset;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.qmm.timeIiemset.TimeItemSt;
import nts.uk.ctx.exio.dom.qmm.timeIiemset.TimeItemStRepository;
import nts.uk.ctx.exio.infra.entity.qmm.timeIiemset.QpbmtTimeItemSt;
import nts.uk.ctx.exio.infra.entity.qmm.timeIiemset.QpbmtTimeItemStPk;

@Stateless
public class JpaTimeItemStRepository extends JpaRepository implements TimeItemStRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtTimeItemSt f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.timeItemStPk.cid =:cid AND  f.timeItemStPk.salaryItemId =:salaryItemId ";

	@Override
	public List<TimeItemSt> getAllTimeItemSt() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtTimeItemSt.class).getList(item -> item.toDomain());
	}

	@Override
	public Optional<TimeItemSt> getTimeItemStById(String cid, String salaryItemId) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtTimeItemSt.class).setParameter("cid", cid)
				.setParameter("salaryItemId", salaryItemId).getSingle(c -> c.toDomain());
	}

	@Override
	public void add(TimeItemSt domain) {
		this.commandProxy().insert(QpbmtTimeItemSt.toEntity(domain));
	}

	@Override
	public void update(TimeItemSt domain) {
		this.commandProxy().update(QpbmtTimeItemSt.toEntity(domain));
	}

	@Override
	public void remove(String cid, String salaryItemId) {
		this.commandProxy().remove(QpbmtTimeItemSt.class, new QpbmtTimeItemStPk(cid, salaryItemId));
	}
}
