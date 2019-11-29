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
    private static final String SELECT_BY_CID_AND_CODE = SELECT_QUERY_STRING + " WHERE r.qqsmtRetiReaClsInfoPk.cId =:cid AND r.qqsmtRetiReaClsInfoPk.retirementReasonClsCode = :code";
    private static final String SELECT_BY_CID_AND_CODES = SELECT_QUERY_STRING + " WHERE r.qqsmtRetiReaClsInfoPk.cId =:cid AND r.qqsmtRetiReaClsInfoPk.retirementReasonClsCode IN :codes";

    @Override
    public List<RetirementReasonClsInfo> getRetirementReasonClsInfoById(String cId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QqsmtRetiReaClsInfo.class)
                .setParameter("cId", cId)
                .getList(e -> e.toDomain());
    }

    @Override
    public Optional<RetirementReasonClsInfo> getByCidAndReasonCode(String cid, String code){
       return this.queryProxy().query(SELECT_BY_CID_AND_CODE,QqsmtRetiReaClsInfo.class)
                .setParameter("cid", cid)
                .setParameter("code", code)
               .getSingle(e -> e.toDomain());
    }

    @Override
    public List<RetirementReasonClsInfo> getByCidAndCodes(String cid, List<String> codes){
        return this.queryProxy().query(SELECT_BY_CID_AND_CODES, QqsmtRetiReaClsInfo.class)
                .setParameter("cid", cid).setParameter("codes", codes).getList(e -> e.toDomain());
    }
}
