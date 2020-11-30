package nts.uk.ctx.at.function.app.query.workledgeroutputitem;


import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyAdapter;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyDto;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

/**
 * KWR005 A：勤務台帳
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class GetBeginMonthOfCompanyQuery {

    @Inject
    private CompanyAdapter companyAdapter;

    public Integer getBeginMonthOfCompany() {
        // 会社の期首月を取得する (Request List108)
        CompanyDto companyDto = companyAdapter.getFirstMonth(AppContexts.user().companyId());
        return companyDto != null ? companyDto.getStartMonth() : null;
    }
}
