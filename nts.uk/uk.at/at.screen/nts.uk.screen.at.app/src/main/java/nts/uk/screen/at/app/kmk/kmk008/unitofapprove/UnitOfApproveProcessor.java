package nts.uk.screen.at.app.kmk.kmk008.unitofapprove;

import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.approveregister.UnitOfApprover;
import nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.approveregister.UnitOfApproverRepo;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Screen kmk008 I : 初期表示を行う
 */
@Stateless
public class UnitOfApproveProcessor {

    @Inject
    private UnitOfApproverRepo unitOfApproverRepo;

    public UnitOfApproveDto find() {

        UnitOfApprover data = unitOfApproverRepo.getByCompanyId(AppContexts.user().companyId());

        return UnitOfApproveDto.setData(data);
    }

}
