package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.empfunmeminfor;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empfunmeminfor.EmPensionFundPartiPeriodInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empfunmeminfor.EmPensionFundPartiPeriodInforRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empfunmeminfor.FundMembership;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empfunmeminfor.QqsmtTemPenPartInfo;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empfunmeminfor.QqsmtTemPenPartInfoPk;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;


@Stateless
public class JpaEmPensionFundPartiPeriodInforRepository extends JpaRepository implements EmPensionFundPartiPeriodInforRepository
{

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtTemPenPartInfo f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.temPenPartInfoPk.employeeId =:employeeId AND  f.temPenPartInfoPk.cid =:cid ";
    private static final String SELECT_BY_KEY_STRING_BY_EMPID = SELECT_ALL_QUERY_STRING + " WHERE  f.temPenPartInfoPk.employeeId =:employeeId ";
    private static final String SELECT_BY_ID = SELECT_ALL_QUERY_STRING + " WHERE  f.temPenPartInfoPk.cid = :cid AND f.temPenPartInfoPk.employeeId =:employeeId AND f.startDate <= :baseDate AND f.endDate >= :baseDate";
    private static final String SELECT_BY_KEYID = SELECT_ALL_QUERY_STRING + " WHERE  f.temPenPartInfoPk.cid =:cid AND f.temPenPartInfoPk.employeeId =:employeeId AND  f.temPenPartInfoPk.historyId =:historyId ";

    @Override
    public List<EmPensionFundPartiPeriodInfor> getAllEmPensionFundPartiPeriodInfor(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QqsmtTemPenPartInfo.class)
                .getList(item -> item.toDomain());
    }

    @Override
    public Optional<EmPensionFundPartiPeriodInfor> getEmPensionFundPartiPeriodInforById(String employeeId, String cid){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QqsmtTemPenPartInfo.class)
        .setParameter("employeeId", employeeId)
        .setParameter("cid", cid)
        .getSingle(c->c.toDomain());
    }

    @Override
    public List<EmPensionFundPartiPeriodInfor> getEmPensionFundPartiPeriodInforByEmpId(String employeeId) {
        return this.queryProxy().query(SELECT_BY_KEY_STRING_BY_EMPID, QqsmtTemPenPartInfo.class)
                .setParameter("employeeId", employeeId)
                .getList(x -> x.toDomain());

    }

    @Override
    public Optional<FundMembership> getEmPensionFundPartiPeriodInfor(String cid, String employeeId, GeneralDate baseDate) {
        return this.queryProxy().query(SELECT_BY_ID, QqsmtTemPenPartInfo.class)
                .setParameter("cid",cid)
                .setParameter("employeeId", employeeId)
                .setParameter("baseDate",baseDate)
                .getSingle(x -> x.toFundMembership());
    }

    @Override
    public Optional<FundMembership> getFundMembershipByEmpId(String employeeId, String hisId) {
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QqsmtTemPenPartInfo.class)
                .setParameter("employeeId", employeeId)
                .setParameter("historyId", hisId)
                .getSingle(x -> x.toFundMembership());

    }

    @Override
    public Optional<FundMembership> getFundMembershipByEmpId(String cid, String employeeId, String hisId) {
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QqsmtTemPenPartInfo.class)
                .setParameter("cid",cid)
                .setParameter("employeeId", employeeId)
                .setParameter("historyId", hisId)
                .getSingle(x -> x.toFundMembership());
    }

    @Override
    public void add(EmPensionFundPartiPeriodInfor domain){
        this.commandProxy().insert(QqsmtTemPenPartInfo.toEntity(domain));
    }

    @Override
    public void update(EmPensionFundPartiPeriodInfor domain){
        this.commandProxy().update(QqsmtTemPenPartInfo.toEntity(domain));
    }

    @Override
    public void remove(String employeeId, String historyId){
        this.commandProxy().remove(QqsmtTemPenPartInfo.class, new QqsmtTemPenPartInfoPk(employeeId, historyId, AppContexts.user().companyId()));
    }
}
