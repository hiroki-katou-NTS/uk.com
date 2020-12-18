package nts.uk.ctx.at.function.dom.alarmworkplace.export;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.alarmworkplace.extractresult.AlarmListExtractInfoWorkplace;
import nts.uk.ctx.at.function.dom.alarmworkplace.extractresult.ExtractResult;
import nts.uk.ctx.at.shared.dom.scherec.alarm.alarmlistactractionresult.AlarmValueDate;
import nts.uk.shr.com.i18n.TextResource;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * data export excel : アラームリスト抽出結果（職場別）
 */
@Getter
@AllArgsConstructor
public class AlarmListExtractResultWorkplaceData {

    /**
     * Id
     */
    private String recordId;
    /**
     * アラーム値メッセージ
     */
    private String alarmValueMessage;
    /**
     * アラーム値日付
     */
    private String alarmValueDate;
    /**
     * アラーム項目名
     */
    private String alarmItemName;
    /**
     * カテゴリ名
     */
    private String categoryName;
    /**
     * チェック対象値
     */
    private String checkTargetValue;
    /**
     * 区分
     */
    private int category;
    /**
     * 開始日
     */
    private GeneralDate startTime;
    /**
     * コメント
     */
    private String comment;
    /**
     * 職場ID
     */
    private String workplaceId;

    /**
     * 職場コード
     */
    private String workplaceCode;

    /**
     * 職場名
     */
    private String workplaceName;

    /**
     * 階層コード
     */
    private String hierarchyCode;

    /**
     * 取得した職場情報一覧から「アラーム抽出結果（職場別）」にデータをマッピングする
     */
    public static List<AlarmListExtractResultWorkplaceData> fromDomains(List<AlarmListExtractInfoWorkplace> alExtractInfos) {
        List<AlarmListExtractResultWorkplaceData> extractResultDtos = new ArrayList<>();
        for (AlarmListExtractInfoWorkplace alExtractInfo : alExtractInfos) {
            String categoryName = TextResource.localize(alExtractInfo.getCategory().nameId);
            ExtractResult extractResult = alExtractInfo.getExtractResult();
            AlarmValueDate date = extractResult.getAlarmValueDate();
            String dateResult = convertAlarmValueDate(date.getStartDate());
            if (date.getEndDate().isPresent()) {
                dateResult += "～" + convertAlarmValueDate(date.getEndDate().get());  //TODO Q&A 37860
            }

            AlarmListExtractResultWorkplaceData result = new AlarmListExtractResultWorkplaceData(
                    alExtractInfo.getRecordId(),
                    extractResult.getAlarmValueMessage().v(),
                    dateResult,
                    extractResult.getAlarmItemName().v(),
                    categoryName,
                    extractResult.getCheckTargetValue(),
                    alExtractInfo.getCategory().value,
                    GeneralDate.today(), //TODO Q&A 37860
                    extractResult.getComment().isPresent() ? null : extractResult.getComment().get().v(),
                    extractResult.getWorkplaceId().orElse(null),
                    extractResult.getWorkplaceCode().orElse(null),
                    extractResult.getWorkplaceName().orElse(null),
                    extractResult.getHierarchyCode().orElse(null));
            extractResultDtos.add(result);
        }

        Comparator<AlarmListExtractResultWorkplaceData> compare = Comparator
                .comparing(AlarmListExtractResultWorkplaceData::getHierarchyCode)
                .thenComparing(AlarmListExtractResultWorkplaceData::getWorkplaceCode)
                .thenComparing(AlarmListExtractResultWorkplaceData::getCategory)
                .thenComparing(AlarmListExtractResultWorkplaceData::getStartTime);
        return extractResultDtos.stream().sorted(compare).collect(Collectors.toList());
    }

    private static String convertAlarmValueDate(int date) {
        String dateStr = String.valueOf(date);
        switch (dateStr.length()) {
            case 6:
                return dateStr.substring(0, 3) + "/" + dateStr.substring(4, 5);
            case 8:
                return dateStr.substring(0, 3) + "/" + dateStr.substring(4, 5) + "/" + dateStr.substring(6, 7);
        }
        return null;
    }

}
