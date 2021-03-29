package nts.uk.ctx.at.record.infra.repository.monthly.agreement.monthlyresult.approveregister;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.approveregister.UnitOfApprover;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.approveregister.UnitOfApproverRepo;
import nts.uk.ctx.at.record.infra.entity.monthly.agreement.monthlyresult.approveregister.Krcmt36ArgApvUnit;

import javax.ejb.Stateless;

@Stateless
public class UnitOfApproverRepoImpl extends JpaRepository implements UnitOfApproverRepo {

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
        Krcmt36ArgApvUnit krcmt36ArgApvUnit = this.queryProxy().find(companyId, Krcmt36ArgApvUnit.class).orElse(null);
        if (krcmt36ArgApvUnit == null) return null;
        return Krcmt36ArgApvUnit.toDomain(krcmt36ArgApvUnit);
    }
}
