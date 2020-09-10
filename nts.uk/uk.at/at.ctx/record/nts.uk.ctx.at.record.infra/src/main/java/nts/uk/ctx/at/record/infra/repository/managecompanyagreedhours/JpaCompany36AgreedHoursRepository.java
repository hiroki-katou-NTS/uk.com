package nts.uk.ctx.at.record.infra.repository.managecompanyagreedhours;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.managecompanyagreedhours.Company36AgreedHoursRepository;
import nts.uk.ctx.at.record.dom.standardtime.AgreementTimeOfCompany;
import nts.uk.ctx.at.record.infra.entity.managecompanyagreedhours.Ksrmt36AgrMgtCmp;

public class JpaCompany36AgreedHoursRepository extends JpaRepository implements Company36AgreedHoursRepository {
    private static String FIND_BY_CID;

    private static String FIND_BY_CID_AND_CD;

    static {
        StringBuilder builderString = new StringBuilder();
        builderString.append("SELECT");
        builderString.append("FROM Ksrmt36AgrMgtCmp a");
        builderString.append("WHERE a.Ksrmt36AgrMgtCmp.companyID = :cid ");
        FIND_BY_CID = builderString.toString();
    }
    @Override
    public void insert(AgreementTimeOfCompany domain) {

    }

    @Override
    public void update(AgreementTimeOfCompany domain) {

    }

    @Override
    public AgreementTimeOfCompany getByCid(String cid) {
        return this.queryProxy().query(FIND_BY_CID, Ksrmt36AgrMgtCmp.class)
                .setParameter("cid",cid)
                .getSingle(d->convertToDomain(d)).get();
    }
    private AgreementTimeOfCompany convertToDomain(Ksrmt36AgrMgtCmp entity) {
        return new AgreementTimeOfCompany();
    }

}
