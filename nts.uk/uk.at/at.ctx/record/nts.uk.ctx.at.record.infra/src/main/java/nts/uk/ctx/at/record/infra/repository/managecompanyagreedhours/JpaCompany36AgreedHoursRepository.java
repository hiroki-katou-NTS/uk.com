package nts.uk.ctx.at.record.infra.repository.managecompanyagreedhours;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.managecompanyagreedhours.Company36AgreedHoursRepository;
import nts.uk.ctx.at.record.infra.entity.managecompanyagreedhours.Ksrmt36AgrMgtCmp;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfCompany;

public class JpaCompany36AgreedHoursRepository extends JpaRepository implements Company36AgreedHoursRepository {
    private static String FIND_BY_CID;
    static {
        StringBuilder builderString = new StringBuilder();
        builderString.append("SELECT");
        builderString.append("FROM Ksrmt36AgrMgtCmp a");
        builderString.append("WHERE a.Ksrmt36AgrMgtCmp.companyID = :cid ");
        FIND_BY_CID = builderString.toString();
    }
    @Override
    public void insert(AgreementTimeOfCompany domain) {
        this.commandProxy().insert(Ksrmt36AgrMgtCmp.toEntity(domain));
        this.getEntityManager().flush();
    }

    @Override
    public void update(AgreementTimeOfCompany domain) {

        this.commandProxy().update(Ksrmt36AgrMgtCmp.toEntity(domain));
    }

    @Override
    public AgreementTimeOfCompany getByCid(String cid) {
        return this.queryProxy().query(FIND_BY_CID, Ksrmt36AgrMgtCmp.class)
                .setParameter("cid",cid)
                .getSingle(Ksrmt36AgrMgtCmp::toDomain).get();
    }

}
