package nts.uk.ctx.at.record.infra.repository.monthly.agreement.monthlyresult.specialprovision;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.query.QueryProxy;
import nts.arc.layer.infra.data.query.TypedQueryWrapper;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.Year;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.ApprovalStatus;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.SpecialProvisionsOfAgreement;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.SpecialProvisionsOfAgreementRepo;
import nts.uk.ctx.at.record.infra.entity.monthly.agreement.monthlyresult.specialprovision.Krcdt36AgrApp;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetYearProcessAndPeriodDto;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class SpecialProvisionsOfAgreementRepoImpl extends JpaRepository implements SpecialProvisionsOfAgreementRepo {

    private static final String FIND_BY_APPREOVER;
    private static final String FIND_BY_CONFIRMER;
    private static final String FIND_BY_YEARMONTH;
    private static final String FIND_BY_YEAR;
    private static final String FIND_BY_PERSIONSID;
    private static final String FIND_BY_EMPLOYEEID;
    private static final String FIND_BY_EMPLOYEEID_AND_PERIOD;

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
        
		builderString = new StringBuilder();
		builderString.append(SELECT);
		builderString.append(" WHERE ((s.approvalStatus = 0 AND (s.approveSID1 = :employeeId OR  s.approveSID2 = :employeeId OR  s.approveSID3 = :employeeId OR  s.approveSID4 = :employeeId OR  s.approveSID5 = :employeeId) ) ");
		builderString.append(" OR (s.confirmationStatus1 = 0 AND s.confirmationStatus2 = 0 AND s.confirmationStatus3 = 0 AND s.confirmationStatus4 = 0 AND s.confirmationStatus5 = 0  ");
		builderString.append(" AND (s.confirmerSID1 = :employeeId OR  s.confirmerSID2 = :employeeId OR  s.confirmerSID3 = :employeeId OR  s.confirmerSID4 = :employeeId OR  s.confirmerSID5 = :employeeId))) ");
		builderString.append(" AND ((s.typeAgreement = 0 AND s.yearMonth >=  :closureStartDate  AND s.yearMonth <= :closureEndDate ) ");
		builderString.append(" OR (s.typeAgreement = 1 AND s.year >=  :stDateMinus AND s.year <= :stDateAdd ))  ");
        FIND_BY_EMPLOYEEID_AND_PERIOD = builderString.toString();
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
        String query = FIND_BY_APPREOVER;
        List<Integer> lstApprove = new ArrayList<>();
        if (listApprove.size() > 0) {
            listApprove.forEach(x -> lstApprove.add(x.value));
            query += " AND s.approvalStatus IN :lstApprove ";
        }

        TypedQueryWrapper<Krcdt36AgrApp> typedQuery = this.queryProxy()
                .query(query, Krcdt36AgrApp.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("approverSID", approverSID);

        if (listApprove.size() > 0) {
            typedQuery.setParameter("lstApprove", lstApprove);
        }
        return typedQuery.getList(Krcdt36AgrApp::toDomain);
    }

    @Override
    public List<SpecialProvisionsOfAgreement> getByConfirmerSID(String confirmerSID, GeneralDate startDate, GeneralDate endDate, List<ApprovalStatus> listApprove) {
        String query = FIND_BY_CONFIRMER;
        List<Integer> lstApprove = new ArrayList<>();
        if (listApprove.size() > 0) {
            listApprove.forEach(x -> lstApprove.add(x.value));
            query += "AND s.approvalStatus IN :lstApprove";
        }

        TypedQueryWrapper<Krcdt36AgrApp> typedQuery = this.queryProxy()
                .query(query, Krcdt36AgrApp.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("confirmerSID", confirmerSID);

        if (listApprove.size() > 0) {
            typedQuery.setParameter("lstApprove", lstApprove);
        }

        return typedQuery.getList(Krcdt36AgrApp::toDomain);
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
        String query = FIND_BY_PERSIONSID;
        List<Integer> lstApprove = new ArrayList<>();
        if (listApprove.size() > 0) {
            listApprove.forEach(x -> lstApprove.add(x.value));
            query += " AND s.approvalStatus IN :lstApprove ";
        }

        TypedQueryWrapper<Krcdt36AgrApp> typedQuery = this.queryProxy()
                .query(query, Krcdt36AgrApp.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("enteredPersonSID", enteredPersonSID);

        if (listApprove.size() > 0) {
            typedQuery.setParameter("lstApprove", lstApprove);
        }

        return typedQuery.getList(Krcdt36AgrApp::toDomain);
    }

    @Override
    public List<SpecialProvisionsOfAgreement> getBySID(String employeeId, GeneralDate startDate, GeneralDate endDate, List<ApprovalStatus> listApprove) {
        String query = FIND_BY_EMPLOYEEID;
        List<Integer> lstApprove = new ArrayList<>();
        if (listApprove.size() > 0) {
            listApprove.forEach(x -> lstApprove.add(x.value));
            query += " AND s.approvalStatus IN :lstApprove ";
        }

        TypedQueryWrapper<Krcdt36AgrApp> typedQuery = this.queryProxy()
                .query(query, Krcdt36AgrApp.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("employeeId", employeeId);

        if (listApprove.size() > 0) {
            typedQuery.setParameter("lstApprove", lstApprove);
        }

        return typedQuery.getList(Krcdt36AgrApp::toDomain);
    }

    /**
     * ドメインモデル「36協定特別条項の適用申請」を取得する
     */
	@Override
	public List<SpecialProvisionsOfAgreement> getByEmployeeId(String employeeId, GeneralDate closureStartDate, GeneralDate closureEndDate) {
		   String query = FIND_BY_EMPLOYEEID_AND_PERIOD;
		   
		   TypedQueryWrapper<Krcdt36AgrApp> typedQuery = this.queryProxy()
	                .query(query, Krcdt36AgrApp.class)
	                .setParameter("employeeId", employeeId)
	                .setParameter("closureStartDate", Integer.parseInt(closureStartDate.toString("YYYYMM")))
	                .setParameter("closureEndDate", Integer.parseInt(closureEndDate.toString("YYYYMM")))
	                .setParameter("stDateMinus", Integer.parseInt(closureStartDate.addYears(-1).toString("YYYY")))
	                .setParameter("stDateAdd", Integer.parseInt(closureStartDate.addYears(1).toString("YYYY")));
	   
		return typedQuery.getList(Krcdt36AgrApp::toDomain);
	}
}
