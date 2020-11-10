package nts.uk.ctx.at.function.app.query.outputworkstatustable;

import nts.gul.text.StringUtil;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyAdapter;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyDto;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

/**
 * Get the beginning month of the company
 *  会社の期首月を取得する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class GetBeginningMonthOfCompany {
    @Inject
    private CompanyAdapter companyAdapter;

    public CompanyDto getBeginningMonthOfCompany(String cid){
        if (StringUtil.isNullOrEmpty(cid, true)) {
            cid = AppContexts.user().companyId();
        }
        // 会社の期首月を取得する (RequetsList108)
        return companyAdapter.getFirstMonth(cid);
    }
}
