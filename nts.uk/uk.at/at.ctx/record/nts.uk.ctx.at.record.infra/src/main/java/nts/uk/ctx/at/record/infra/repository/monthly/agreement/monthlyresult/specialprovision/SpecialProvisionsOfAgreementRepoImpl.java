package nts.uk.ctx.at.record.infra.repository.monthly.agreement.monthlyresult.specialprovision;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.Year;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.ApprovalStatus;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.SpecialProvisionsOfAgreement;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.SpecialProvisionsOfAgreementRepo;
import nts.uk.ctx.at.record.infra.entity.monthly.agreement.monthlyresult.specialprovision.Krcdt36AgrApp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SpecialProvisionsOfAgreementRepoImpl extends JpaRepository implements SpecialProvisionsOfAgreementRepo {

    private static String FIND_BY_APPREOVER;
    private static String FIND_BY_CONFIRMER;
    private static String FIND_BY_YEARMONTH;
    private static String FIND_BY_YEAR;
    private static String FIND_BY_PERSIONSID;
    private static String FIND_BY_EMPLOYEEID;

    static {
        StringBuilder builderString = new StringBuilder();
        builderString.append("SELECT s FROM Krcdt36AgrApp s ");
        String SELECT = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append(" WHERE s.inputDate >= :startDate AND s.inputDate <= :endDate ");
        builderString.append(" AND s.approveSID = :approverSID ");
        FIND_BY_APPREOVER = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append(" WHERE s.inputDate >= :startDate AND s.inputDate <= :endDate ");
        builderString.append(" AND (s.confirmerSID1 = :confirmerSID OR  s.confirmerSID2 = :confirmerSID OR  s.confirmerSID3 = :confirmerSID OR  s.confirmerSID4 = :confirmerSID OR  s.confirmerSID5 = :confirmerSID ) ");
        FIND_BY_CONFIRMER = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append(" WHERE s.applicantsSID = :applicantsSID ");
        builderString.append(" AND s.typeAgreement = 0 ");
        builderString.append(" AND s.yearMonth = :yearMonth ");
        FIND_BY_YEARMONTH = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append(" WHERE s.applicantsSID = :applicantsSID ");
        builderString.append(" AND s.typeAgreement = 1 ");
        builderString.append(" AND s.year = :year ");
        FIND_BY_YEAR = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append(" WHERE s.inputDate >= :startDate AND s.inputDate <= :endDate ");
        builderString.append(" AND s.enteredPersonSID = :enteredPersonSID ");
        FIND_BY_PERSIONSID = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append(" WHERE s.inputDate >= :startDate AND s.inputDate <= :endDate ");
        builderString.append(" AND (s.confirmerSID1 = :employeeId OR  s.confirmerSID2 = :employeeId OR  s.confirmerSID3 = :employeeId OR  s.confirmerSID4 = :employeeId OR  s.confirmerSID5 = :employeeId ");
        builderString.append(" OR s.approveSID1 = :employeeId OR  s.approveSID2 = :employeeId OR  s.approveSID3 = :employeeId OR  s.approveSID4 = :employeeId OR  s.approveSID5 = :employeeId ) ");
        FIND_BY_EMPLOYEEID = builderString.toString();

    }

    @Override
    public void insert(SpecialProvisionsOfAgreement domain) {
        commandProxy().insert(Krcdt36AgrApp.fromDomain(domain));

    }

    @Override
    public void update(SpecialProvisionsOfAgreement domain) {
        commandProxy().update(Krcdt36AgrApp.fromDomain(domain));
    }

    @Override
    public void delete(SpecialProvisionsOfAgreement domain) {
        this.commandProxy().remove(Krcdt36AgrApp.class, domain.getApplicationID());
    }

    @Override
    public List<SpecialProvisionsOfAgreement> getByApproverSID(String approverSID, GeneralDate startDate, GeneralDate endDate, List<ApprovalStatus> listApprove) {
        if (listApprove.size() > 0) {
            val lstApprove = new ArrayList<>();
            listApprove.forEach(x -> lstApprove.add(x.value));
            FIND_BY_APPREOVER += " AND a.approvalStatus IN (:lstApprove) ";
        }
        return this.queryProxy()
                .query(FIND_BY_APPREOVER, Krcdt36AgrApp.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("approverSID", approverSID)
                .setParameter("listApprove", listApprove)
                .getList(Krcdt36AgrApp::toDomain);
    }

    @Override
    public List<SpecialProvisionsOfAgreement> getByConfirmerSID(String confirmerSID, GeneralDate startDate, GeneralDate endDate, List<ApprovalStatus> listApprove) {
        if (listApprove.size() > 0) {
            val lstConfirm = new ArrayList<>();
            listApprove.forEach(x -> lstConfirm.add(x.value));
            FIND_BY_CONFIRMER += "AND a.approvalStatus IN (:lstConfirm)";
        }
        return this.queryProxy()
                .query(FIND_BY_CONFIRMER, Krcdt36AgrApp.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("confirmerSID", confirmerSID)
                .setParameter("listApprove", listApprove)
                .getList(Krcdt36AgrApp::toDomain);
    }

    @Override
    public Optional<SpecialProvisionsOfAgreement> getByAppId(String applicationID) {
        return this.queryProxy().find(applicationID, Krcdt36AgrApp.class).map(x -> x.toDomain(x));
    }

    @Override
    public Optional<SpecialProvisionsOfAgreement> getByYearMonth(String applicantsSID, YearMonth yearMonth) {

        Optional<Krcdt36AgrApp> krcdt36AgrApp = this.queryProxy()
                .query(FIND_BY_YEARMONTH, Krcdt36AgrApp.class)
                .setParameter("applicantsSID", applicantsSID)
                .setParameter("yearMonth", yearMonth).getSingle();

        return krcdt36AgrApp.map(Krcdt36AgrApp::toDomain);

    }

    @Override
    public Optional<SpecialProvisionsOfAgreement> getByYear(String applicantsSID, Year year) {

        Optional<Krcdt36AgrApp> krcdt36AgrApp = this.queryProxy()
                .query(FIND_BY_YEAR, Krcdt36AgrApp.class)
                .setParameter("applicantsSID", applicantsSID)
                .setParameter("year", year).getSingle();

        return krcdt36AgrApp.map(Krcdt36AgrApp::toDomain);

    }

    @Override
    public List<SpecialProvisionsOfAgreement> getByPersonSID(String enteredPersonSID, GeneralDate startDate, GeneralDate endDate, List<ApprovalStatus> listApprove) {
        if (listApprove.size() > 0) {
            val lstConfirm = new ArrayList<>();
            listApprove.forEach(x -> lstConfirm.add(x.value));
            FIND_BY_PERSIONSID += " AND a.approvalStatus IN (:lstConfirm) ";
        }
        return this.queryProxy()
                .query(FIND_BY_PERSIONSID, Krcdt36AgrApp.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("enteredPersonSID", enteredPersonSID)
                .setParameter("listApprove", listApprove)
                .getList(Krcdt36AgrApp::toDomain);
    }

    @Override
    public List<SpecialProvisionsOfAgreement> getBySID(String employeeId, GeneralDate startDate, GeneralDate endDate, List<ApprovalStatus> listApprove) {
        if (listApprove.size() > 0) {
            val lstConfirm = new ArrayList<>();
            listApprove.forEach(x -> lstConfirm.add(x.value));
            FIND_BY_PERSIONSID += " AND a.approvalStatus IN (:lstConfirm) ";
        }
        return this.queryProxy()
                .query(FIND_BY_PERSIONSID, Krcdt36AgrApp.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("employeeId", employeeId)
                .setParameter("listApprove", listApprove)
                .getList(Krcdt36AgrApp::toDomain);
    }
}
