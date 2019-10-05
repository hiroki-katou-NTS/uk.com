package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.empbenepenpeninfor;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empbenepenpeninfor.*;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empbenepenpeninfor.QqsmtEmpWelfInsQcIf;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;


@Stateless
public class JpaEmpWelfarePenInsQualiInforRepository extends JpaRepository implements EmpWelfarePenInsQualiInforRepository, WelfPenNumInformationRepository, WelfarePenTypeInforRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtEmpWelfInsQcIf f";
    private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING + " WHERE  f.empWelfInsQcIfPk.historyId =:historyId ";
    private static final String SELECT_BY_LIST_EMP = SELECT_ALL_QUERY_STRING + " WHERE  f.empWelfInsQcIfPk.employeeId IN :employeeIds  AND f.endDate >= :startDate AND f.endDate <= :endDate";
    private static final String SELECT_BY_EMPID = SELECT_ALL_QUERY_STRING + " WHERE  f.empWelfInsQcIfPk.employeeId =:employeeId";
    private static final String SELECT_BY_LIST_EMP_START = SELECT_ALL_QUERY_STRING + " WHERE  f.empWelfInsQcIfPk.employeeId IN :employeeIds  AND f.startDate >= :startDate AND f.startDate <= :endDate";
    private static final String SELECT_BY_KEY_ID = SELECT_ALL_QUERY_STRING + " WHERE  f.empWelfInsQcIfPk.cid = :cid AND f.empWelfInsQcIfPk.historyId =:historyId AND f.empWelfInsQcIfPk.employeeId = :employeeId";
    private static final String SELECT_BY_ID = SELECT_ALL_QUERY_STRING + " WHERE  f.empWelfInsQcIfPk.cid = :cid AND f.empWelfInsQcIfPk.employeeId =:employeeId";
    private static final String SELECT_BY_HISID = SELECT_ALL_QUERY_STRING + " WHERE  f.empWelfInsQcIfPk.cid = :cid AND  f.empWelfInsQcIfPk.historyId =:historyId ";
    private static final String SELECT_BY_ID_AND_DATE = "SELECT f FROM QqsmtEmpWelfInsQcIf f  WHERE  f.empWelfInsQcIfPk.cid = :cid AND   f.startDate <= :date AND f.endDate >= :date AND f.empWelfInsQcIfPk.employeeId IN :sids";

    @Override
    public boolean checkEmpWelfarePenInsQualiInforEnd(GeneralDate start, GeneralDate end, List<String> empIds) {
        List<QqsmtEmpWelfInsQcIf> qqsmtEmpWelfInsQcIf =  this.queryProxy().query(SELECT_BY_LIST_EMP, QqsmtEmpWelfInsQcIf.class)
                .setParameter("employeeIds", empIds)
                .setParameter("startDate", start)
                .setParameter("endDate", end)
                .getList();
        return qqsmtEmpWelfInsQcIf.isEmpty();
    }

    @Override
    public boolean checkEmpWelfarePenInsQualiInforStart(GeneralDate start, GeneralDate end, List<String> empIds) {
        List<QqsmtEmpWelfInsQcIf> qqsmtEmpWelfInsQcIf =  this.queryProxy().query(SELECT_BY_LIST_EMP_START, QqsmtEmpWelfInsQcIf.class)
                .setParameter("employeeIds", empIds)
                .setParameter("startDate", start)
                .setParameter("endDate", end)
                .getList();
        return qqsmtEmpWelfInsQcIf.isEmpty();
    }

    @Override
    public Optional<EmpWelfarePenInsQualiInfor> getEmpWelfarePenInsQualiInforByEmpId(String cid, String employeeId) {
        List<QqsmtEmpWelfInsQcIf> qqsmtEmpWelfInsQcIf =  this.queryProxy().query(SELECT_BY_ID, QqsmtEmpWelfInsQcIf.class)
                .setParameter("cid",cid)
                .setParameter("employeeId", employeeId)
                .getList();

        return Optional.ofNullable(QqsmtEmpWelfInsQcIf.toDomain(qqsmtEmpWelfInsQcIf));
    }

    @Override
    public List<EmpWelfarePenInsQualiInfor> getEmpWelfarePenInsQualiInfor(String cid, GeneralDate date, List<String> sIds) {
        return this.queryProxy().query(SELECT_BY_ID_AND_DATE, QqsmtEmpWelfInsQcIf.class)
                .setParameter("cid",cid)
                .setParameter("date", date)
                .setParameter("sids", sIds)
                .getList(c -> c.toDomain());
    }

    @Override
    public Optional<WelfPenNumInformation> getWelfPenNumInformationById(String historyId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QqsmtEmpWelfInsQcIf.class)
                .setParameter("historyId", historyId)
                .getSingle(c->c.toWelfPenNumInformation());
    }

    @Override
    public Optional<WelfPenNumInformation> getWelfPenNumInformationById(String cid, String affMourPeriodHisid, String empId) {
        return this.queryProxy().query(SELECT_BY_KEY_ID, QqsmtEmpWelfInsQcIf.class)
                .setParameter("cid",cid)
                .setParameter("historyId", affMourPeriodHisid)
                .setParameter("employeeId", empId)
                .getSingle(c->c.toWelfPenNumInformation());
    }

    @Override
    public Optional<WelfarePenTypeInfor> getWelfarePenTypeInforById(String historyId){
        return this.queryProxy().query(SELECT_BY_KEY_STRING, QqsmtEmpWelfInsQcIf.class)
                .setParameter("historyId", historyId)
                .getSingle(c->c.toWelfarePenTypeI());
    }

    @Override
    public Optional<WelfarePenTypeInfor> getWelfarePenTypeInforById(String cid,String empID, String historyId) {
        return this.queryProxy().query(SELECT_BY_KEY_ID, QqsmtEmpWelfInsQcIf.class)
                .setParameter("cid",cid)
                .setParameter("historyId", historyId)
                .setParameter("employeeId",empID)
                .getSingle(c->c.toWelfarePenTypeI());
    }
}
