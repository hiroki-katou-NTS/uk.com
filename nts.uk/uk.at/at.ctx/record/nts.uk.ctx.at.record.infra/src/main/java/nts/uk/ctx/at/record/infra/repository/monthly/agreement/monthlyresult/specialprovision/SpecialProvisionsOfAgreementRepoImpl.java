package nts.uk.ctx.at.record.infra.repository.monthly.agreement.monthlyresult.specialprovision;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.ApprovalStatus;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.SpecialProvisionsOfAgreement;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision.SpecialProvisionsOfAgreementRepo;
import nts.uk.ctx.at.record.infra.entity.monthly.agreement.monthlyresult.specialprovision.Krcdt36AgrApp;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Stateless
public class SpecialProvisionsOfAgreementRepoImpl extends JpaRepository implements SpecialProvisionsOfAgreementRepo {

    private static String FIND_BY_APPREOVER;
    private static String FIND_BY_CONFIRMER;

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
            FIND_BY_APPREOVER += "AND a.approvalStatus IN (:lstApprove)";
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
        return this.queryProxy().find(applicationID,Krcdt36AgrApp.class).map(x -> x.toDomain(x));
    }
}
