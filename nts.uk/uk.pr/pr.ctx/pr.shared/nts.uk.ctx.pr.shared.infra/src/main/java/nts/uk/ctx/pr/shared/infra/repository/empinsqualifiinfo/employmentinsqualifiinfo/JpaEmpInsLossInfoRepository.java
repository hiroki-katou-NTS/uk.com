package nts.uk.ctx.pr.shared.infra.repository.empinsqualifiinfo.employmentinsqualifiinfo;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsLossInfo;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsHist;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsLossInfo;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsLossInfoRepository;
import nts.uk.ctx.pr.shared.infra.entity.empinsqualifiinfo.employmentinsqualifiinfo.QqsmtEmpInsLossInfo;

import javax.ejb.Stateless;
import java.util.Optional;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaEmpInsLossInfoRepository extends JpaRepository implements EmpInsLossInfoRepository{
    private static final String SELECT_ALL_QUERY_STRING = "SELECT l FROM QqsmtEmpInsLossInfo l";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE l.empInsLossInfoPk.cId =:cId AND l.empInsLossInfoPk.sId =:sId";

    @Override
    public Optional<EmpInsLossInfo> getOneEmpInsLossInfo(String cId, String sId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QqsmtEmpInsLossInfo.class)
                .setParameter("cId", cId)
                .setParameter("sId", sId)
                .getSingle(e -> e.toDomain());
    }

    @Override
    public void add(EmpInsLossInfo domain){
        this.commandProxy().insert(QqsmtEmpInsLossInfo.toEntity(domain));
    }

    @Override
    public void update(EmpInsLossInfo domain){
        this.commandProxy().update(QqsmtEmpInsLossInfo.toEntity(domain));
    }

    private static final String SELECT_ALL = "SELECT f FROM QqsmtEmpInsLossInfo f";
    private static final String SELECT_BY_ID = SELECT_ALL + " where f.empInsLossInfoPk.sId =:sId";

    @Override
    public List<EmpInsHist> getAllEmpInsLossInfo() {
        return null;
    }

    @Override
    public Optional<EmpInsLossInfo> getEmpInsLossInfoById(String sid) {
        return this.queryProxy().query(SELECT_BY_ID, QqsmtEmpInsLossInfo.class)
                .setParameter("sId", sid)
                .getSingle( e-> {
                    return new EmpInsLossInfo(
                            e.empInsLossInfoPk.cId,
                            e.empInsLossInfoPk.sId,
                            e.causeOfLossAtr,
                            e.requestForIssuance,
                            e.scheReplenAtr,
                            e.causeOfLostEmpIns,
                            e.workingTime
                    );
                });
    }
}
