package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.emphealinsurassocinfor;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurassocinfor.HealInsurPortPerIntell;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurassocinfor.HealInsurPortPerIntellRepository;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurassocinfor.HealthCarePortInfor;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.emphealinsurassocinfor.HealthCarePortInforRepository;
import nts.uk.ctx.pr.shared.infra.entity.familyinfo.empfamilysocialins.QqsmtEmpFamilyInsHis;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.emphealinsurassocinfor.QqsmtHealInsurPortInt;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.emphealinsurassocinfor.QqsmtHealInsurPortIntPk;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;


@Stateless
public class JpaHealInsurPortPerIntellRepository extends JpaRepository implements HealInsurPortPerIntellRepository, HealthCarePortInforRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtHealInsurPortInt f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.healInsurPortIntPk.employeeId =:employeeId AND  f.healInsurPortIntPk.hisId =:hisId ";
    private static final String SELECT_BY_EMPID = SELECT_ALL_QUERY_STRING + " WHERE  f.healInsurPortIntPk.employeeId =:employeeId ";
    private static final String SELECT_BY_ID = SELECT_ALL_QUERY_STRING + " WHERE  f.healInsurPortIntPk.cid =:cid AND f.healInsurPortIntPk.employeeId =:employeeId AND f.startDate <= :baseDate AND f.endDate >= :baseDate";
    private static final String SELECT_BY_EMPIC_CID = SELECT_ALL_QUERY_STRING + " WHERE  f.healInsurPortIntPk.employeeId =:employeeId AND  f.healInsurPortIntPk.cid =:cid ";

    @Override
    public List<HealInsurPortPerIntell> getAllHealInsurPortPerIntell(){
        /*return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QqsmtHealInsurPortInt.class)
                .getList(item -> item.toDomain());*/
        return null;
    }

    @Override
    public Optional<HealthCarePortInfor> getHealthCarePortInforById(String hisId, String empId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QqsmtHealInsurPortInt.class)
                .setParameter("hisId", hisId)
                .setParameter("employeeId", empId)
                .getSingle(c->c.toDomainHealthCare());
    }

    @Override
    public List<HealthCarePortInfor> getAllHealthCarePortInfor(){
        return this.queryProxy().query(SELECT_ALL_QUERY_STRING, QqsmtHealInsurPortInt.class)
                .getList(item -> item.toDomainHealthCare());
    }

    @Override
    public Optional<HealInsurPortPerIntell> getHealInsurPortPerIntellById(String employeeId, String hisId){
        /*return this.queryProxy().query(SELECT_BY_KEY_STRING, QqsmtHealInsurPortInt.class)
        .setParameter("employeeId", employeeId)
        .setParameter("hisId", hisId)
        .getSingle(c->c.toDomain());*/
        return null;
    }

    @Override
    public Optional<HealInsurPortPerIntell> getHealInsurPortPerIntellById(String employeeId) {
        List<QqsmtHealInsurPortInt> history = this.queryProxy().query(SELECT_BY_EMPIC_CID, QqsmtHealInsurPortInt.class)
                .setParameter("employeeId", employeeId)
                .setParameter("cid", AppContexts.user().companyId())
                .getList();

        return  history.isEmpty() ?  Optional.empty() : Optional.of(QqsmtHealInsurPortInt.toDomain(history)) ;
    }

    @Override
    public Optional<HealthCarePortInfor> getHealInsurPortPerIntellById(String cid, String employeeId, GeneralDate baseDate) {
        return this.queryProxy().query(SELECT_BY_ID, QqsmtHealInsurPortInt.class)
                .setParameter("cid",cid)
                .setParameter("employeeId", employeeId)
                .setParameter("baseDate",baseDate)
                .getSingle(x -> x.toDomainHealthCare());
    }

    @Override
    public void add(HealInsurPortPerIntell domain){
        this.commandProxy().insert(QqsmtHealInsurPortInt.toEntity(domain));
    }

    @Override
    public void update(HealInsurPortPerIntell domain){
        this.commandProxy().update(QqsmtHealInsurPortInt.toEntity(domain));
    }

    @Override
    public void remove(String employeeId, String hisId, String cid){
        this.commandProxy().remove(QqsmtHealInsurPortInt.class, new QqsmtHealInsurPortIntPk(employeeId, hisId, cid));
    }
}
