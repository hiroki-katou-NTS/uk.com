package nts.uk.ctx.pr.shared.infra.repository.empinsqualifiinfo.employmentinsqualifiinfo;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsGetInfo;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsGetInfoRepository;
import nts.uk.ctx.pr.shared.infra.entity.empinsqualifiinfo.employmentinsqualifiinfo.QqsmtEmpInsGetInfo;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaEmpInsGetInfoRepository extends JpaRepository implements EmpInsGetInfoRepository{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT e FROM QqsmtEmpInsGetInfo e";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE e.empInsGetInfoPk.sid =:sid";

    @Override
    public List<EmpInsGetInfo> getAllEmpInsGetInfo(){
        return null;
    }

    @Override
    public Optional<EmpInsGetInfo> getEmpInsGetInfo(String sid){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QqsmtEmpInsGetInfo.class)
                .setParameter("sid", sid)
                .getSingle(e ->{
                    return new EmpInsGetInfo(
                            e.empInsGetInfoPk.sid,
                            e.workingTime,
                            e.acquiAtr,
                            e.contrPeriPrintAtr,
                            e.jobPath,
                            e.payWage,
                            e.jobAtr,
                            e.insCauseAtr,
                            e.wagePaymentMode,
                            e.employmentStatus);
                });
    }

    @Override
    public void add(EmpInsGetInfo domain){
        this.commandProxy().insert(QqsmtEmpInsGetInfo.toEntity(domain));
    }

    @Override
    public void update(EmpInsGetInfo domain){
        this.commandProxy().update(QqsmtEmpInsGetInfo.toEntity(domain));
    }

}
