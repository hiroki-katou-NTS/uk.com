package nts.uk.screen.at.app.kha003.d;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable.ManHourSummaryTableFormatRepository;
import nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable.MasterNameInformation;
import nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable.WorkDetailData;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * ＜＜ScreenQuery＞＞ 集計結果を作成する
 */
@Stateless
public class CreateAggregationManHourResult {
    @Inject
    private ManHourSummaryTableFormatRepository manHourSummaryRepo;

    /**
     * @param code           工数集計表コード
     * @param masterNameInfo マスタ名称情報　（C画面で指定したマスタ）
     * @param workDetailList 工数集計データ．作業詳細データ
     * @param dateList       年月日<List>
     * @param yearMonthList  年月<List>
     */
    public AggregationResultDto get(String code, MasterNameInformation masterNameInfo, List<WorkDetailData> workDetailList,
                                    List<GeneralDate> dateList, List<YearMonth> yearMonthList) {
        val optManHour = this.manHourSummaryRepo.get(AppContexts.user().companyId(), code);
        if (!optManHour.isPresent()) return null;

        val manHourOutputContent = optManHour.get().createOutputContent(dateList, yearMonthList, workDetailList, masterNameInfo);
        if (manHourOutputContent == null)
            throw new BusinessException("Msg_2171");

        return new AggregationResultDto(optManHour.get(), manHourOutputContent);
    }
}
