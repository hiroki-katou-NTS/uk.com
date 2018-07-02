package nts.uk.ctx.exio.infra.repository.exo.execlog;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.exo.execlog.ExterOutExecLog;
import nts.uk.ctx.exio.dom.exo.execlog.ExterOutExecLogRepository;
import nts.uk.ctx.exio.infra.entity.exo.execlog.OiomtExterOutExecLog;
import nts.uk.ctx.exio.infra.entity.exo.execlog.OiomtExterOutExecLogPk;

@Stateless
public class JpaExterOutExecLogRepository extends JpaRepository implements ExterOutExecLogRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM OiomtExterOutExecLog f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.exterOutExecLogPk.cid =:cid AND  f.exterOutExecLogPk.outProcessId =:outProcessId ";

	@Override
	public List<ExterOutExecLog> getAllExterOutExecLog() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, OiomtExterOutExecLog.class)
				.getList(item -> item.toDomain());
	}

	@Override
	public Optional<ExterOutExecLog> getExterOutExecLogById(String cid, String outProcessId) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, OiomtExterOutExecLog.class).setParameter("cid", cid)
				.setParameter("outProcessId", outProcessId).getSingle(c -> c.toDomain());
	}

	@Override
	public void add(ExterOutExecLog domain) {
		this.commandProxy().insert(OiomtExterOutExecLog.toEntity(domain));
	}

	@Override
	public void update(ExterOutExecLog domain) {
		this.commandProxy().update(OiomtExterOutExecLog.toEntity(domain));
	}

	@Override
	public void remove(String cid, String outProcessId) {
		this.commandProxy().remove(OiomtExterOutExecLog.class, new OiomtExterOutExecLogPk(cid, outProcessId));
	}
}