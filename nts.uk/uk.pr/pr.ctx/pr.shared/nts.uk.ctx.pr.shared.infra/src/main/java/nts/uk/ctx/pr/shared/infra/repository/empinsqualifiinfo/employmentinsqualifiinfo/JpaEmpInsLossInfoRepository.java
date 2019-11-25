package nts.uk.ctx.pr.shared.infra.repository.empinsqualifiinfo.employmentinsqualifiinfo;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsLossInfo;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsLossInfoRepository;
import nts.uk.ctx.pr.shared.infra.entity.empinsqualifiinfo.employmentinsqualifiinfo.QqsmtEmpInsLossInfo;
import nts.uk.ctx.pr.shared.infra.entity.empinsqualifiinfo.employmentinsqualifiinfo.QqsmtEmpInsLossInfoPk;

@Stateless
public class JpaEmpInsLossInfoRepository extends JpaRepository implements EmpInsLossInfoRepository {
	private static final String SELECT_ALL_QUERY_STRING = "SELECT l FROM QqsmtEmpInsLossInfo l";
	private static final String SELECT_BY_KEY_LIST_SID = SELECT_ALL_QUERY_STRING
			+ " WHERE l.empInsLossInfoPk.cId = :cId AND l.empInsLossInfoPk.sId IN :sIds";

	@Override
	public Optional<EmpInsLossInfo> getOneEmpInsLossInfo(String cId, String sId) {
		return this.queryProxy().find(new QqsmtEmpInsLossInfoPk(cId, sId), QqsmtEmpInsLossInfo.class)
				.map(i -> i.toDomain());
	}

	@Override
	public void add(EmpInsLossInfo domain) {
		this.commandProxy().insert(QqsmtEmpInsLossInfo.toEntity(domain));
	}

	@Override
	public void update(EmpInsLossInfo domain) {
		this.commandProxy().insert(QqsmtEmpInsLossInfo.toEntity(domain));
	}

	private static final String SELECT_BY_ID = SELECT_ALL_QUERY_STRING + " where l.empInsLossInfoPk.sId = :sId";

	@Override
	public Optional<EmpInsLossInfo> getEmpInsLossInfoById(String sid) {
		return this.queryProxy().query(SELECT_BY_ID, QqsmtEmpInsLossInfo.class).setParameter("sId", sid)
				.getSingle(e -> {
					return new EmpInsLossInfo(e.empInsLossInfoPk.cId, e.empInsLossInfoPk.sId, e.causeOfLossAtr,
							e.reqIssuAtr, e.scheReplenAtr, e.causeOfLostEmpIns, e.workingTime);
				});
	}

	@Override
	public List<EmpInsLossInfo> getListEmpInsLossInfo(String companyId, List<String> employeeIds) {
		if (employeeIds == null || employeeIds.isEmpty())
			return Collections.emptyList();
		return this.queryProxy().query(SELECT_BY_KEY_LIST_SID, QqsmtEmpInsLossInfo.class).setParameter("cId", companyId)
				.setParameter("sIds", employeeIds).getList(i -> i.toDomain());
	}
}
