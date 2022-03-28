package nts.uk.ctx.at.function.dom.supportworklist.outputsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.supportworkdata.SupportWorkDataImport;
import nts.uk.ctx.at.function.dom.supportworklist.aggregationsetting.SupportWorkAggregationSetting;
import nts.uk.ctx.at.function.dom.supportworklist.aggregationsetting.SupportWorkDetails;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.outputdata.SupportWorkOutputData;
import nts.uk.shr.com.enumcommon.NotUseAtr;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * レイアウト詳細設定
 */
@AllArgsConstructor
@Getter
public class DetailLayoutSetting {
    /**
     * 社員抽出条件
     */
    private EmployeeExtractCondition extractCondition;

    /**
     * 明細表示設定
     */
    private DetailDisplaySetting detailDisplaySetting;

    /**
     * 総合計の表示設定
     */
    private GrandTotalDisplaySetting grandTotalDisplaySetting;

    /**
     * 職場合計の表示設定
     */
    private WorkplaceTotalDisplaySetting workplaceTotalDisplaySetting;

    /**
     * 改ページする
     */
    private NotUseAtr pageBreak;

    /**
     * [1] 職場別の応援勤務出力データを取得する
     * @param require
     * @param companyId
     * @param period
     * @param baseDate
     * @param workplaceIds
     * @return
     */
    public SupportWorkOutputData getOutputDataByWorkplace(SupportWorkOutputDataRequire require, String companyId, DatePeriod period, GeneralDate baseDate, List<String> workplaceIds) {
        SupportWorkDataImport supportWorkData = extractCondition.getSupportWorkDataByWorkplace(
                require,
                companyId,
                period,
                workplaceIds
        );
        return this.createSupportWorkOutputData(require, companyId, Optional.of(baseDate), supportWorkData);
    }

    /**
     * [2] 場所別の応援勤務出力データを取得する
     * @param require
     * @param companyId
     * @param period
     * @param workLocationCodes
     * @return
     */
    public SupportWorkOutputData getOutputDataByWorkLocation(SupportWorkOutputDataRequire require, String companyId, DatePeriod period, List<String> workLocationCodes) {
        SupportWorkDataImport supportWorkData = extractCondition.getSupportWorkDataByWorkLocations(
                require,
                companyId,
                period,
                workLocationCodes
        );
        return this.createSupportWorkOutputData(require, companyId, Optional.empty(), supportWorkData);
    }

    /**
     * [prv-1] 応援勤務明細を作成する
     * @param require
     * @param companyId 会社ID
     * @param baseDate Optional<基準日>
     * @param supportWorkData 応援勤務データ
     * @return 応援勤務出力データ
     */
    private SupportWorkOutputData createSupportWorkOutputData(SupportWorkOutputDataRequire require, String companyId, Optional<GeneralDate> baseDate, SupportWorkDataImport supportWorkData) {
        Optional<SupportWorkAggregationSetting> setting = require.getSetting(companyId);
        if (!setting.isPresent()) throw new BusinessException("Msg_3263");
        List<Integer> attendanceItemIds = this.detailDisplaySetting.getOutputItems().stream().map(OutputItem::getAttendanceItemId).collect(Collectors.toList());
        List<SupportWorkDetails> supportWorkDetailsList = setting.get().createSupportWorkDetails(
                require,
                companyId,
                baseDate,
                supportWorkData,
                attendanceItemIds
        );
        return new SupportWorkOutputData(
                require,
                companyId,
                supportWorkDetailsList,
                workplaceTotalDisplaySetting,
                grandTotalDisplaySetting,
                extractCondition
        );
    }

}
