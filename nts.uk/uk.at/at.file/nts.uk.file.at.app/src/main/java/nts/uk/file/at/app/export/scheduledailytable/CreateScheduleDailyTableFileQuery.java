package nts.uk.file.at.app.export.scheduledailytable;

import nts.arc.primitive.PrimitiveValueBase;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.aggregation.dom.scheduledailytable.ScheduleDailyTableCode;
import nts.uk.ctx.at.aggregation.dom.scheduledailytable.ScheduleDailyTablePrintSetting;
import nts.uk.ctx.at.aggregation.dom.scheduledailytable.ScheduleDailyTableRepository;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.company.CompanyInfor;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 勤務計画実施表を作成する
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class CreateScheduleDailyTableFileQuery {
    @Inject
    private CompanyAdapter companyAdapter;

    @Inject
    private ScheduleDailyTableRepository repository;

    @Inject
    private GetEachWkpGroupRelatedInfoFileQuery fileQuery;

    /**
     * 取得する
     * @param workplaceGroupIds 職場グループIDリスト
     * @param period 対象期間
     * @param outputItemCode 出力項目コード
     * @param printTarget 印刷対象
     * @return dataSource
     */
    public ScheduleDailyTableDataSource get(List<String> workplaceGroupIds, DatePeriod period, String outputItemCode, int printTarget) {
        String companyId = AppContexts.user().companyId();
        CompanyInfor companyInfo = companyAdapter.getCurrentCompany().orElseGet(() -> {
            throw new RuntimeException("Company Info Not Found!");
        });
        ScheduleDailyTablePrintSetting setting = repository.get(companyId, new ScheduleDailyTableCode(outputItemCode)).orElseGet(() -> {
            throw new RuntimeException("ScheduleDailyTablePrintSetting Not Found!");
        });
        List<WkpGroupRelatedDisplayInfoDto> displayInfos = fileQuery.get(workplaceGroupIds, period, printTarget, setting.getItemSetting().getPersonalCounter(), setting.getItemSetting().getWorkplaceCounter());
        return new ScheduleDailyTableDataSource(
                companyInfo.getCompanyName(),
                setting.getName().v(),
                setting.getItemSetting().getComment().map(PrimitiveValueBase::v).orElse(null),
                setting.getItemSetting().getInkanRow().getTitleList().stream().map(PrimitiveValueBase::v).collect(Collectors.toList()),
                displayInfos
        );
    }
}
