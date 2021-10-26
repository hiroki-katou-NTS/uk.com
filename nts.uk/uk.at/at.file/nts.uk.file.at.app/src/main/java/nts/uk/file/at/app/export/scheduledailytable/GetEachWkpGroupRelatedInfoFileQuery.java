package nts.uk.file.at.app.export.scheduledailytable;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.WorkplaceGroupAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.WorkplaceGroupImport;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * 職場グループごとで関係情報を取得する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class GetEachWkpGroupRelatedInfoFileQuery {
    @Inject
    private WorkplaceGroupAdapter workplaceGroupAdapter;

    @Inject
    private GetWkpGroupRelatedInfoFileQuery fileQuery;

    /**
     * 取得する
     * @param workplaceGroupIds 職場グループIDリスト
     * @param period 対処期間
     * @param printTarget 印刷対象
     * @param personalCounters 個人計
     * @param workplaceCounters 職場計
     * @return List<職場グループに関係する表示情報dto>
     */
    public List<WkpGroupRelatedDisplayInfoDto> get(List<String> workplaceGroupIds, DatePeriod period, int printTarget, List<Integer> personalCounters, List<Integer> workplaceCounters) {
        List<WkpGroupRelatedDisplayInfoDto> result = new ArrayList<>();
        List<WorkplaceGroupImport> workplaceGroups = workplaceGroupAdapter.getbySpecWorkplaceGroupID(workplaceGroupIds);
        for (int i = 0; i < workplaceGroups.size(); i++) {
            WorkplaceGroupImport workplaceGroup = workplaceGroups.get(i);
            result.add(fileQuery.get(workplaceGroup, period, printTarget, personalCounters, workplaceCounters));
        }
        return result;
    }
}
