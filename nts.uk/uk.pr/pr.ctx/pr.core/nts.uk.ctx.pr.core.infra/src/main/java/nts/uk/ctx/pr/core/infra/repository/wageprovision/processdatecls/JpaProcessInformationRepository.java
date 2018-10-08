package nts.uk.ctx.pr.core.infra.repository.wageprovision.processdatecls;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.ProcessInformation;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.ProcessInformationRepository;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.processdatecls.QpbmtProcessInformation;
import nts.uk.ctx.pr.core.infra.entity.wageprovision.processdatecls.QpbmtProcessInformationPk;

@Stateless
public class JpaProcessInformationRepository extends JpaRepository implements ProcessInformationRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QpbmtProcessInformation f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.processInformationPk.cid =:cid AND  f.processInformationPk.processCateNo =:processCateNo ";
	private static final String SELECT_BY_DEPRECATED_CATEGORY = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.processInformationPk.cid =:cid AND  f.deprecatCate =:deprecatedCategory ";

	private static final String SELECT_BY_COMPANY_ID = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.processInformationPk.cid =:cid ORDER BY f.processInformationPk.processCateNo ASC";

	@Override
	public List<ProcessInformation> getAllProcessInformation() {
		return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QpbmtProcessInformation.class)
				.getList(item -> item.toDomain());
	}

	@Override
	public Optional<ProcessInformation> getProcessInformationById(String cid, int processCateNo) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, QpbmtProcessInformation.class).setParameter("cid", cid)
				.setParameter("processCateNo", processCateNo).getSingle(c -> c.toDomain());
	}

	@Override
	public void add(ProcessInformation domain) {
		this.commandProxy().insert(QpbmtProcessInformation.toEntity(domain));
	}

	@Override
	public void update(ProcessInformation domain) {
		this.commandProxy().update(QpbmtProcessInformation.toEntity(domain));
	}

	@Override
	public void remove(String cid, int processCateNo) {
		this.commandProxy().remove(QpbmtProcessInformation.class, new QpbmtProcessInformationPk(cid, processCateNo));
	}

	@Override
	public List<ProcessInformation> getProcessInformationByDeprecatedCategory(String cid, int deprecatedCategory) {
		// TODO Auto-generated method stub SELECT_BY_DEPRECATED_CATEGORY
		return this.queryProxy().query(SELECT_BY_DEPRECATED_CATEGORY, QpbmtProcessInformation.class)
				.setParameter("cid", cid).setParameter("deprecatedCategory", deprecatedCategory)
				.getList(c -> c.toDomain());
	}

	@Override
	public List<ProcessInformation> getProcessInformationByCid(String cid) {
		return this.queryProxy().query(SELECT_BY_COMPANY_ID, QpbmtProcessInformation.class).setParameter("cid", cid)
				.getList(c -> c.toDomain());
	}
}
