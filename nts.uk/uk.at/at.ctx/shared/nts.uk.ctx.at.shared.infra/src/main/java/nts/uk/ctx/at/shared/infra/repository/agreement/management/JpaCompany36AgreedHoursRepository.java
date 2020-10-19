package nts.uk.ctx.at.shared.infra.repository.agreement.management;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfCompany;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.Company36AgreedHoursRepository;
import nts.uk.ctx.at.shared.infra.entity.agreement.management.Ksrmt36AgrMgtCmp;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class JpaCompany36AgreedHoursRepository extends JpaRepository implements Company36AgreedHoursRepository {
    private static String FIND_BY_CID;
    static {
        StringBuilder builderString = new StringBuilder();
        builderString.append("SELECT");
        builderString.append("FROM Ksrmt36AgrMgtCmp a");
        builderString.append("WHERE a.ksrmt36AgrMgtCmpPk.companyID = :cid ");
        FIND_BY_CID = builderString.toString();
    }
    @Override
    public void insert(AgreementTimeOfCompany domain) {
        this.commandProxy().insert(Ksrmt36AgrMgtCmp.toEntity(domain));
    }

    @Override
    public void update(AgreementTimeOfCompany domain) {

        this.commandProxy().update(Ksrmt36AgrMgtCmp.toEntity(domain));
    }

    @Override
    public Optional<AgreementTimeOfCompany> getByCid(String cid) {
        return this.queryProxy().query(FIND_BY_CID, Ksrmt36AgrMgtCmp.class)
                .setParameter("cid",cid)
                .getSingle(Ksrmt36AgrMgtCmp::toDomain);

    }

}
