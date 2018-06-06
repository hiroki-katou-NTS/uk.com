package nts.uk.ctx.sys.assist.infra.repository.deletedata;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.assist.dom.deletedata.ResultLogDeletion;
import nts.uk.ctx.sys.assist.dom.deletedata.ResultLogDeletionRepository;
import nts.uk.ctx.sys.assist.infra.entity.deletedata.SspdtResultLogDeletion;

@Stateless
public class JpaResultLogDeletionRepository extends JpaRepository implements ResultLogDeletionRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM SspdtResultLogDeletion f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.sspdtResultLogDeletionPK.delId = :delId ";

	@Override
	public List<ResultLogDeletion> getAllResultLogDeletion() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, SspdtResultLogDeletion.class)
				.getList(item -> item.toDomain());
	}

	@Override
	public Optional<ResultLogDeletion> getResultLogDeletionById(String delId) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, SspdtResultLogDeletion.class)
				.setParameter("delId", delId).getSingle(c -> c.toDomain());
	}

	@Override
	public void add(ResultLogDeletion data) {
		this.commandProxy().insert(SspdtResultLogDeletion.toEntity(data));

	}
}
