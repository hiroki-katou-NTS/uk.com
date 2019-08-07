package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.empbenepenpeninfor;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.WelfPenNumInformation;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.WelfPenNumInformationRepository;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empbenepenpeninfor.QqsmtWelPenNumInfo;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empbenepenpeninfor.QqsmtWelPenNumInfoPk;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;

@Stateless
public class JpaWelfPenNumInformationRepository extends JpaRepository implements WelfPenNumInformationRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtWelPenNumInfo f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.welPenNumInfoPk.affMourPeriodHisid =:affMourPeriodHisid ";

    @Override
    public List<WelfPenNumInformation> getAllWelfPenNumInformation(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QqsmtWelPenNumInfo.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<WelfPenNumInformation> getWelfPenNumInformationById(String affMourPeriodHisid){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QqsmtWelPenNumInfo.class)
        .setParameter("affMourPeriodHisid", affMourPeriodHisid)
        .getSingle(c->c.toDomain());
    }

    @Override
    public void add(WelfPenNumInformation domain){
        this.commandProxy().insert(QqsmtWelPenNumInfo.toEntity(domain));
    }

    @Override
    public void update(WelfPenNumInformation domain){
        this.commandProxy().update(QqsmtWelPenNumInfo.toEntity(domain));
    }

    @Override
    public void remove(String affMourPeriodHisid){
        this.commandProxy().remove(QqsmtWelPenNumInfo.class, new QqsmtWelPenNumInfoPk(affMourPeriodHisid));
    }
}
