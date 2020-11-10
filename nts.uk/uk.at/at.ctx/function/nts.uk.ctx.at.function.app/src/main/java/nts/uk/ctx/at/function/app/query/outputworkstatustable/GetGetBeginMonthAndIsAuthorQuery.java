package nts.uk.ctx.at.function.app.query.outputworkstatustable;

import lombok.val;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.function.dom.adapter.dailyperform.DailyPerformanceAuthorityAdapter;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyAdapter;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyDto;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.math.BigDecimal;

/**
 * Get the beginning month of the company
 *  会社の期首月を取得する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class GetGetBeginMonthAndIsAuthorQuery {
    @Inject
    private CompanyAdapter companyAdapter;

    @Inject
    private DailyPerformanceAuthorityAdapter authorityAdapter;
    private final BigDecimal FUNCTION_NO = BigDecimal.valueOf(51);

    public BeginMonthAndIsAuthor getBeginMonthAndIsAuthor(String cid, String roleID){
        if (StringUtil.isNullOrEmpty(roleID, true)) {
            roleID = AppContexts.user().roles().forAttendance();
        }
        val rs = authorityAdapter.get(roleID);

        val checkAuthor=  !rs.isEmpty()
                && rs.stream().anyMatch(e -> e.getFunctionNo()
                .equals(FUNCTION_NO) && e.isAvailability());
        if (StringUtil.isNullOrEmpty(cid, true)) {
            cid = AppContexts.user().companyId();
        }
        // 会社の期首月を取得する (RequetsList108)
        val dto = companyAdapter.getFirstMonth(cid);
        Integer startMont = null;
        if(dto!=null){
            startMont = dto.getStartMonth();
        }
        return new BeginMonthAndIsAuthor(
                startMont,
                checkAuthor
        );
    }
}
