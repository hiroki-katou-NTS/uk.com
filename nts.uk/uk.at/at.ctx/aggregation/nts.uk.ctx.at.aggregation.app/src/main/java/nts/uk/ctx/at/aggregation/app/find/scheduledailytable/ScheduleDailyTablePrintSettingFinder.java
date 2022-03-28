package nts.uk.ctx.at.aggregation.app.find.scheduledailytable;

import nts.uk.ctx.at.aggregation.dom.scheduledailytable.ScheduleDailyTableCode;
import nts.uk.ctx.at.aggregation.dom.scheduledailytable.ScheduleDailyTableRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class ScheduleDailyTablePrintSettingFinder {
    @Inject
    private ScheduleDailyTableRepository repository;

    /**
     * 出力項目設定一覧情報を取得する
     * @return list dto
     */
    public List<ScheduleDailyTablePrintSettingDto> getAll() {
        return repository.getList(AppContexts.user().companyId())
                .stream().map(ScheduleDailyTablePrintSettingDto::fromDomain)
                .collect(Collectors.toList());
    }

    /**
     * 出力項目設定の表示情報を取得する
     * @param code 出力項目コード
     * @return dto
     */
    public ScheduleDailyTablePrintSettingDto get(String code) {
        return repository.get(AppContexts.user().companyId(), new ScheduleDailyTableCode(code))
                .map(ScheduleDailyTablePrintSettingDto::fromDomain).orElse(null);
    }
}
