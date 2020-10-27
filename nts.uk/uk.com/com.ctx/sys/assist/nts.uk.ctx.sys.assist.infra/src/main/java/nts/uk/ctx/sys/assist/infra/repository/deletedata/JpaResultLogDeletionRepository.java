package nts.uk.ctx.sys.assist.infra.repository.deletedata;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.assist.dom.deletedata.ResultLogDeletion;
import nts.uk.ctx.sys.assist.dom.deletedata.ResultLogDeletionRepository;
import nts.uk.ctx.sys.assist.infra.entity.deletedata.SspdtDeletionResultLog;

@Stateless
public class JpaResultLogDeletionRepository extends JpaRepository implements ResultLogDeletionRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM SspdtDeletionResultLog f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.sspdtDeletionResultLogPK.delId = :delId ";
	
	private static final String GET_MAX = "SELECT MAX(f.sspdtDeletionResultLogPK.seqId) "
			+ " FROM SspdtDeletionResultLog f WHERE f.sspdtDeletionResultLogPK.delId = :delId ";
	 
	@Override
	public int getMaxSeqId(String delId) {
		 Object max = this.queryProxy().query(GET_MAX, Object.class)
					.setParameter("delId", delId).getSingleOrNull();
		 if(max == null) {
			 return 0;
		 }else {
			 return (int)max;
		 }
	 }

	@Override
	public List<ResultLogDeletion> getAllResultLogDeletion() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, SspdtDeletionResultLog.class)
				.getList(item -> item.toDomain());
	}

	@Override
	public Optional<ResultLogDeletion> getResultLogDeletionById(String delId) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, SspdtDeletionResultLog.class)
				.setParameter("delId", delId).getSingle(c -> c.toDomain());
	}

	@Override
	public void add(ResultLogDeletion data) {
		this.commandProxy().insert(SspdtDeletionResultLog.toEntity(data));
//		this.getEntityManager().flush();
	}
}
