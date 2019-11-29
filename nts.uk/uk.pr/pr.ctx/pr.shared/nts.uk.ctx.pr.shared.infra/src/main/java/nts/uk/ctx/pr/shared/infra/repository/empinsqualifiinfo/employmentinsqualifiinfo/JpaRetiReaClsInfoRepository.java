package nts.uk.ctx.pr.shared.infra.repository.empinsqualifiinfo.employmentinsqualifiinfo;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.RetirementReasonClsInfo;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.RetirementReasonClsInfoRepository;
import nts.uk.ctx.pr.shared.infra.entity.empinsqualifiinfo.employmentinsqualifiinfo.QqsmtRetiReaClsInfo;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaRetiReaClsInfoRepository extends JpaRepository implements RetirementReasonClsInfoRepository{
    private static final String SELECT_QUERY_STRING = "SELECT r FROM QqsmtRetiReaClsInfo r";
    private static final String SELECT_BY_KEY_STRING = SELECT_QUERY_STRING + " WHERE r.qqsmtRetiReaClsInfoPk.cId =:cId";

    @Override
    public List<RetirementReasonClsInfo> getRetirementReasonClsInfoById(String cId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QqsmtRetiReaClsInfo.class)
                .setParameter("cId", cId)
                .getList(e -> e.toDomain());
    }
}
