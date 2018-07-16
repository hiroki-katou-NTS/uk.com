package nts.uk.ctx.exio.infra.repository.exo.execlog;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.exo.execlog.ExternalOutLog;
import nts.uk.ctx.exio.dom.exo.execlog.ExternalOutLogRepository;
import nts.uk.ctx.exio.infra.entity.exo.execlog.OiomtExternalOutLog;

@Stateless
public class JpaExternalOutLogRepository extends JpaRepository implements ExternalOutLogRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtExternalOutLog f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.externalOutLogPk.cid =:cid AND  f.externalOutLogPk.outProcessId =:outProcessId AND f.processContent = :processContent";

	@Override
	public List<ExternalOutLog> getAllExternalOutLog() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtExternalOutLog.class)
				.getList(item -> item.toDomain());
	}

	@Override
	public List<ExternalOutLog> getExternalOutLogById(String cid, String outProcessId, int processContent) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, OiomtExternalOutLog.class).setParameter("cid", cid)
				.setParameter("outProcessId", outProcessId).setParameter("processContent", processContent)
				.getList(c -> c.toDomain());
	}

	@Override
	public void add(ExternalOutLog domain) {
		this.commandProxy().insert(OiomtExternalOutLog.toEntity(domain));
	}

	@Override
	public void update(ExternalOutLog domain) {
		this.commandProxy().update(OiomtExternalOutLog.toEntity(domain));
	}
}