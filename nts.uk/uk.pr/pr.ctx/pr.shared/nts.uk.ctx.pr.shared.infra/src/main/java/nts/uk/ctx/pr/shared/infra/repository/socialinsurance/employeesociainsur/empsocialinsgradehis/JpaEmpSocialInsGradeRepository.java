package nts.uk.ctx.pr.shared.infra.repository.socialinsurance.employeesociainsur.empsocialinsgradehis;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGrade;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGradeHis;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGradeInfo;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empsocialinsgradehis.EmpSocialInsGradeRepository;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empsocialinsgradehis.QqsmtSyahoGraHist;
import nts.uk.ctx.pr.shared.infra.entity.socialinsurance.employeesociainsur.empsocialinsgradehis.QqsmtSyahoGraHistPk;
import nts.uk.shr.com.history.YearMonthHistoryItem;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

import javax.ejb.Stateless;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class JpaEmpSocialInsGradeRepository extends JpaRepository implements EmpSocialInsGradeRepository {

    private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM QqsmtSyahoGraHist f";
    private static final String SELECT_BY_EMP_ID = SELECT_ALL_QUERY_STRING + " WHERE f.syahoGraHistPk.cid =:cid AND f.syahoGraHistPk.sid =:sid ORDER BY f.startYm DESC";
    private static final String SELECT_BY_SID_AND_BASE_DATE = SELECT_ALL_QUERY_STRING + " WHERE f.syahoGraHistPk.cid =:cid AND f.syahoGraHistPk.sid =:sid AND f.startYm <= :standardYm AND f.endYm >= :standardYm ";

    @Override
    public void add(EmpSocialInsGradeHis history, EmpSocialInsGradeInfo info) {
        this.commandProxy().insert(QqsmtSyahoGraHist.toEntity(history, info));
    }

    @Override
    public void update(EmpSocialInsGradeHis history, EmpSocialInsGradeInfo info) {
        this.commandProxy().update(QqsmtSyahoGraHist.toEntity(history, info));
    }

    @Override
    public void delete(String cId, String sId, String histId) {
        this.commandProxy().remove(QqsmtSyahoGraHist.class, new QqsmtSyahoGraHistPk(cId, sId, histId));
    }

    @Override
    public Optional<EmpSocialInsGrade> getByEmpIdAndBaseDate(String companyId, String employeeId, GeneralDate standardDate) {
        if (standardDate == null) {
            return Optional.empty();
        }

        List<QqsmtSyahoGraHist> entities = this.queryProxy()
                .query(SELECT_BY_SID_AND_BASE_DATE, QqsmtSyahoGraHist.class)
                .setParameter("cid", companyId)
                .setParameter("sid", employeeId)
                .setParameter("standardYm", standardDate.yearMonth().v())
                .getList();

        if (!entities.isEmpty()) {
            return Optional.of(toDomainObject(entities));
        }

        return Optional.empty();
    }

    @Override
    public Optional<EmpSocialInsGrade> getByKey(String companyId, String employeeId, String historyId) {
        return this.queryProxy()
                .find(new QqsmtSyahoGraHistPk(companyId, employeeId, historyId), QqsmtSyahoGraHist.class)
                .map(this::toDomainObject);
    }

    @Override
    public Optional<EmpSocialInsGrade> getByEmpId(String companyId, String employeeId) {
        List<QqsmtSyahoGraHist> entities = this.queryProxy()
                .query(SELECT_BY_EMP_ID, QqsmtSyahoGraHist.class)
                .setParameter("cid", companyId)
                .setParameter("sid", employeeId)
                .getList();

        if (!entities.isEmpty()) {
            return Optional.of(toDomainObject(entities));
        }

        return Optional.empty();
    }

    private EmpSocialInsGradeHis toHistoryDomain(List<QqsmtSyahoGraHist> entities) {
        if (entities == null || entities.isEmpty()) {
            return null;
        }
        return new EmpSocialInsGradeHis(
                entities.get(0).syahoGraHistPk.cid,
                entities.get(0).syahoGraHistPk.sid,
                entities.stream()
                        .map(e -> new YearMonthHistoryItem(e.syahoGraHistPk.histId, new YearMonthPeriod(new YearMonth(e.startYm), new YearMonth(e.endYm))))
                        .collect(Collectors.toList())
        );
    }

    private List<EmpSocialInsGradeInfo> toInfoDomains(List<QqsmtSyahoGraHist> entities) {
        return entities.stream()
                .map(e -> new EmpSocialInsGradeInfo(
                        e.syahoGraHistPk.histId,
                        e.syahoHosyuReal,
                        e.calAtr,
                        e.kenhoHosyu,
                        e.kenhoToq,
                        e.kouhoHosyu,
                        e.kouhoToq))
                .collect(Collectors.toList());
    }

    private EmpSocialInsGrade toDomainObject(List<QqsmtSyahoGraHist> entities) {
        return new EmpSocialInsGrade(toHistoryDomain(entities), toInfoDomains(entities));
    }

    private EmpSocialInsGrade toDomainObject(QqsmtSyahoGraHist entity) {
        return toDomainObject(Arrays.asList(entity));
    }
}
