package nts.uk.ctx.at.function.dom.supportworklist.outputsetting.outputdata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.function.dom.supportworklist.aggregationsetting.SupportWorkDetails;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.EmployeeExtractCondition;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.GrandTotalDisplaySetting;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.SupportWorkOutputDataRequire;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.WorkplaceTotalDisplaySetting;
import nts.uk.shr.com.enumcommon.NotUseAtr;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 応援勤務出力データ
 */
@AllArgsConstructor
@Getter
public class SupportWorkOutputData {
    /**
     * 応援勤務データ一覧
     */
    private List<WorkplaceSupportWorkData> supportWorkDataList;

    /**
     * 所属合計
     */
    private Optional<TotalValueDetail> totalAffiliation = Optional.empty();

    /**
     * 応援合計
     */
    private Optional<TotalValueDetail> totalSupport = Optional.empty();

    /**
     * 	総合計
     */
    private Optional<TotalValueDetail> grandTotal = Optional.empty();


    /**
     * [C-1] 出力データ作成する
     * @param require
     * @param companyId
     * @param supportWorkDetails
     * @param wkpTotalDisplaySetting
     * @param grandTotalDisplaySetting
     * @param extractCondition
     */
    public SupportWorkOutputData(SupportWorkOutputDataRequire require,
                                 String companyId,
                                 List<SupportWorkDetails> supportWorkDetails,
                                 WorkplaceTotalDisplaySetting wkpTotalDisplaySetting,
                                 GrandTotalDisplaySetting grandTotalDisplaySetting,
                                 EmployeeExtractCondition extractCondition) {
        Map<String, List<SupportWorkDetails>> dataMap = this.seperateDetailsByWorkplace(supportWorkDetails, extractCondition);
        this.supportWorkDataList = dataMap.entrySet().stream()
                .map(e -> new WorkplaceSupportWorkData(require, companyId, e.getKey(), e.getValue(), wkpTotalDisplaySetting, extractCondition))
                .collect(Collectors.toList());

        if (grandTotalDisplaySetting.getDisplayGrandTotal() == NotUseAtr.USE) {
            this.calculateGrandTotal(require, companyId, supportWorkDetails);
        }

        if (grandTotalDisplaySetting.getDisplayWorkplaceSupportMeter() == NotUseAtr.USE) {
            this.calculateWorkplaceSupportMeter(require, companyId, supportWorkDetails);
        }
    }

    /**
     * [prv-1] 職場・応援計を計算する
     * @param require
     * @param companyId          会社ID
     * @param supportWorkDetails 応援明細一覧
     */
    private void calculateWorkplaceSupportMeter(SupportWorkOutputDataRequire require,
                                                String companyId,
                                                List<SupportWorkDetails> supportWorkDetails) {
        List<SupportWorkDetails> affiliationWork = supportWorkDetails.stream().filter(i -> !i.isSupportWork()).collect(Collectors.toList());
        List<SupportWorkDetails> supportWork = supportWorkDetails.stream().filter(i -> i.isSupportWork()).collect(Collectors.toList());
        this.totalAffiliation = Optional.of(TotalValueDetail.create(require, companyId, affiliationWork));
        this.totalSupport = Optional.of(TotalValueDetail.create(require, companyId, supportWork));
    }

    /**
     * [prv-2] 総合計を計算する
     * @param require
     * @param companyId          会社ID
     * @param supportWorkDetails 応援明細一覧
     */
    private void calculateGrandTotal(SupportWorkOutputDataRequire require,
                                     String companyId,
                                     List<SupportWorkDetails> supportWorkDetails) {
        this.grandTotal = Optional.of(TotalValueDetail.create(require, companyId, supportWorkDetails));
    }

    /**
     * [prv-3] 応援勤務明細を勤務先別に切り分ける
     * @param supportWorkDetails List<応援勤務明細>
     * @param extractCondition 社員抽出条件
     * @return 勤務先別応援明細
     */
    private Map<String, List<SupportWorkDetails>> seperateDetailsByWorkplace(List<SupportWorkDetails> supportWorkDetails, EmployeeExtractCondition extractCondition) {
        if (extractCondition == EmployeeExtractCondition.EXTRACT_EMPLOYEES_GO_TO_SUPPORT)
            return supportWorkDetails.stream().collect(Collectors.groupingBy(SupportWorkDetails::getAffiliationInfo));

        return supportWorkDetails.stream().collect(Collectors.groupingBy(SupportWorkDetails::getWorkInfo));
    }
}
