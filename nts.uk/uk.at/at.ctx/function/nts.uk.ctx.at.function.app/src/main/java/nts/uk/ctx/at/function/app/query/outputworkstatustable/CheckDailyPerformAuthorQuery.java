package nts.uk.ctx.at.function.app.query.outputworkstatustable;


import lombok.val;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.function.dom.adapter.dailyperform.DailyPerformanceAuthorityAdapter;
import nts.uk.ctx.at.function.dom.adapter.dailyperform.DailyPerformanceAuthorityDto;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.stream.Collector;

/**
 * ログイン社員の就業帳票の権限を取得する
 */
@Stateless
public class CheckDailyPerformAuthorQuery {
    @Inject
    private DailyPerformanceAuthorityAdapter authorityAdapter;
    private final BigDecimal FUNCTION_NO = BigDecimal.valueOf(51);

    public boolean checkDailyPerformAuthor(String roleID) {
        if (StringUtil.isNullOrEmpty(roleID, true)) {
            roleID = AppContexts.user().roles().forAttendance();
        }
        val rs = authorityAdapter.get(roleID);
        return !rs.isEmpty()
                && rs.stream().anyMatch(e -> e.getFunctionNo()
                .equals(FUNCTION_NO) && e.isAvailability());
    }
}
