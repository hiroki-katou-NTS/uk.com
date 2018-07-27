package nts.uk.ctx.sys.assist.infra.repository.deletedata;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.assist.dom.deletedata.ResultDeletion;
import nts.uk.ctx.sys.assist.dom.deletedata.ResultDeletionRepository;
import nts.uk.ctx.sys.assist.infra.entity.deletedata.SspdtResultDeletion;

@Stateless
public class JpaResultDeletionRepository extends JpaRepository implements ResultDeletionRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM SspdtResultDeletion f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.sspdtResultDeletionPK.delId = :delId ";

	@Override
	public List<ResultDeletion> getAllResultDeletion() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, SspdtResultDeletion.class)
				.getList(item -> item.toDomain());
	}

	@Override
	public Optional<ResultDeletion> getResultDeletionById(String delId) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, SspdtResultDeletion.class)
				.setParameter("delId", delId).getSingle(c -> c.toDomain());
	}

	@Override
	public void add(ResultDeletion data) {
		this.commandProxy().insert(SspdtResultDeletion.toEntity(data));

	}
	
	@Override
	public void update(ResultDeletion data) {
		 this.commandProxy().update(SspdtResultDeletion.toEntity(data));
	}
}
