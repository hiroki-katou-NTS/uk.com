package nts.uk.ctx.at.function.dom.commonform;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 基準日で社員の雇用と締め日を取得する
 */
@Stateless
public class GetClosureDateEmploymentDomainService {
    /**
     *
     * @param require
     * @param baseDate 基準日：年月日
     * @param listSid List<社員ID>：社員ID
     * @return
     */
    public static List<ClosureDateEmployment> getByDate(Require require, GeneralDate baseDate, List<String> listSid) {
        val companyId = AppContexts.user().companyId();

        // 雇用を取得する
        val employmentInfors = require.getEmploymentInfor(companyId, listSid, baseDate);

        return employmentInfors.values().stream().map(i -> {
            // Call 社員に対応する処理締めを取得する
            // TODO how to get closure Date
            val closure = require.getClosureDataByEmployee(i.getEmployeeId(), baseDate);
            return new ClosureDateEmployment(
                    i.getEmployeeId(),
                    i.getEmploymentCode(),
                    i.getEmploymentName(),
                    null
            );
        }).collect(Collectors.toList());
    }

    public static interface Require {
        /**
         * nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter.findEmpHistoryVer2(String companyId, List<String> lstSID, GeneralDate baseDate)
         *
         * @param companyId
         * @param listSid
         * @param baseDate
         * @return
         */
        Map<String, BsEmploymentHistoryImport> getEmploymentInfor(String companyId, List<String> listSid, GeneralDate baseDate);

        /**
         * nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService.getClosureDataByEmployee(RequireM3 require, CacheCarrier cacheCarrier, String employeeId, GeneralDate baseDate)
         *
         * @param employeeId
         * @param baseDate
         * @return
         */
        Closure getClosureDataByEmployee(String employeeId, GeneralDate baseDate);
    }
}
