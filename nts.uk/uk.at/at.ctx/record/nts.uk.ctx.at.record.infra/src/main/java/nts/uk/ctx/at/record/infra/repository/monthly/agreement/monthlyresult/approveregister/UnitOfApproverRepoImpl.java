package nts.uk.ctx.at.record.infra.repository.monthly.agreement.monthlyresult.approveregister;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.approveregister.UnitOfApprover;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.approveregister.UnitOfApproverRepo;
import nts.uk.ctx.at.record.infra.entity.monthly.agreement.monthlyresult.approveregister.Krcmt36ArgApvUnit;

import javax.ejb.Stateless;

@Stateless
public class UnitOfApproverRepoImpl extends JpaRepository implements UnitOfApproverRepo {

    private static String FIND_BY_APPID;

    static {
        StringBuilder builderString = new StringBuilder();
        builderString.append(" SELECT s FROM Krcmt36ArgApvUnit s ");
        String SELECT = builderString.toString();

        builderString = new StringBuilder();
        builderString.append(SELECT);
        builderString.append(" WHERE s.companyID = :companyId ");
        FIND_BY_APPID = builderString.toString();
    }

    @Override
    public void insert(UnitOfApprover domain) {
        commandProxy().insert(Krcmt36ArgApvUnit.fromDomain(domain));

    }

    @Override
    public void update(UnitOfApprover domain) {
        commandProxy().update(Krcmt36ArgApvUnit.fromDomain(domain));
    }

    @Override
    public UnitOfApprover getByCompanyId(String companyId) {
        Krcmt36ArgApvUnit krcmt36ArgApvUnit = this.queryProxy()
                .query(FIND_BY_APPID, Krcmt36ArgApvUnit.class)
                .setParameter("companyId", companyId)
                .getSingleOrNull();

        return Krcmt36ArgApvUnit.toDomain(krcmt36ArgApvUnit);
    }
}
