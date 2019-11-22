package nts.uk.ctx.pr.shared.infra.repository.empinsqualifiinfo.employmentinsqualifiinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsLossInfo;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsLossInfoRepository;
import nts.uk.ctx.pr.shared.infra.entity.empinsqualifiinfo.employmentinsqualifiinfo.QqsmtEmpInsLossInfo;

@Stateless
public class JpaEmpInsLossInfoRepository extends JpaRepository implements EmpInsLossInfoRepository{

	private static final String SELECT_ALL_QUERY_STRING = "SELECT e FROM QqsmtEmpInsLossInfo e";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE e.empInsLossInfoPk.cId =:cId AND e.empInsLossInfoPk.sId =:sId";
    private static final String SELECT_BY_EMP_IDS = SELECT_ALL_QUERY_STRING + " WHERE e.empInsLossInfoPk.cId = :cid AND e.empInsLossInfoPk.sId IN :sids";
    
	@Override
	public Optional<EmpInsLossInfo> getEmpInsGetInfoById(String cId, String sId) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, QqsmtEmpInsLossInfo.class)
                .setParameter("cId", cId)
                .setParameter("sId", sId)
                .getSingle(e -> e.toDomain());
	}

	@Override
	public List<EmpInsLossInfo> getByEmpIds(String cid, List<String> empIds) {
		List<EmpInsLossInfo> result = new ArrayList<>();
        CollectionUtil.split(empIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitIdList ->
            this.queryProxy().query(SELECT_BY_EMP_IDS, QqsmtEmpInsLossInfo.class)
                    .setParameter("sids", empIds)
                    .setParameter("cid", cid)
                    .getList(QqsmtEmpInsLossInfo::toDomain)
        );
        return result;
	}
}
