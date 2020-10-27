package nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.approveregister;

/**
 *承認者（36協定）の利用単位Repository
 */
public interface UnitOfApproverRepo {

    /**
     *[1] Insert(承認者（36協定）の利用単位)
     */
    void insert(UnitOfApprover domain);

    /**
     * 	[2] Update(承認者（36協定）の利用単位)
     */
    void update(UnitOfApprover domain);

    /**
     * 	[3] get by companyId
     */
    UnitOfApprover getByCompanyId(String companyId);

}
