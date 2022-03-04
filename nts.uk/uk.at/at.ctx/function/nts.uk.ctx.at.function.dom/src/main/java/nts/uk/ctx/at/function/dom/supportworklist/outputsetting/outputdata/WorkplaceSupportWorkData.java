package nts.uk.ctx.at.function.dom.supportworklist.outputsetting.outputdata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.supportworklist.aggregationsetting.SupportWorkDetails;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.EmployeeExtractCondition;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.SupportWorkOutputDataRequire;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.WorkplaceTotalDisplaySetting;
import nts.uk.shr.com.enumcommon.NotUseAtr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 勤務先応援勤務データ
 */
@AllArgsConstructor
@Getter
public class WorkplaceSupportWorkData {
    /**
     * 勤務先
     */
    private String workplace;

    /**
     * 応援作業詳細
     */
    private List<SupportWorkDataOfDay> supportWorkDetails;

    /**
     * 所属合計
     */
    private Optional<TotalValueDetail> totalAffiliation = Optional.empty();

    /**
     * 応援合計
     */
    private Optional<TotalValueDetail> totalSupport = Optional.empty();

    /**
     * 応援内訳
     */
    private List<SupportDetail> supportDetails = new ArrayList<>();

    /**
     * 職場計
     */
    private Optional<TotalValueDetail> totalWorkplace = Optional.empty();

    /**
     * [C-1] 新規作成
     *
     * @param require
     * @param companyId          会社ID
     * @param workplace          勤務先
     * @param supportWorkDetails 応援明細一覧
     * @param displaySetting     職場合計の表示設定
     */
    public WorkplaceSupportWorkData(SupportWorkOutputDataRequire require,
                                    String companyId,
                                    String workplace,
                                    List<SupportWorkDetails> supportWorkDetails,
                                    WorkplaceTotalDisplaySetting displaySetting,
                                    EmployeeExtractCondition extractCondition) {
        this.workplace = workplace;

        Map<GeneralDate, List<SupportWorkDetails>> dateData = supportWorkDetails.stream().collect(Collectors.groupingBy(SupportWorkDetails::getDate));
        this.supportWorkDetails = dateData.entrySet().stream().map(e -> {
            return SupportWorkDataOfDay.create(require, companyId, e.getKey(), e.getValue(), displaySetting.getDisplayOneDayTotal());
        }).collect(Collectors.toList());

        if (displaySetting.getDisplayWorkplaceSupportMeter() == NotUseAtr.USE) {
            this.calculateWorkplaceSupportMeter(require, companyId, supportWorkDetails);
        }

        if (displaySetting.getDisplaySupportDetail() == NotUseAtr.USE) {
            this.calculateSupportDetail(require, companyId, supportWorkDetails, extractCondition);
        }

        if (displaySetting.getDisplayWorkplaceTotal() == NotUseAtr.USE) {
            this.calculateWorkplaceTotal(require, companyId, supportWorkDetails);
        }
    }

    /**
     * [prv-1] 職場・応援計を計算する
     *
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
     * [prv-2] 応援内訳を計算する
     *
     * @param require
     * @param companyId          会社ID
     * @param supportWorkDetails 応援明細一覧
     */
    private void calculateSupportDetail(SupportWorkOutputDataRequire require,
                                        String companyId,
                                        List<SupportWorkDetails> supportWorkDetails,
                                        EmployeeExtractCondition extractCondition) {
        this.supportDetails = supportWorkDetails.stream().filter(i -> i.isSupportWork())
                .collect(Collectors.groupingBy(i -> {
                    return extractCondition == EmployeeExtractCondition.EXTRACT_EMPLOYEES_GO_TO_SUPPORT
                            ? i.getAffiliationInfo()
                            : i.getWorkInfo();
                }))
                .entrySet().stream().map(e -> {
                    return SupportDetail.create(require, companyId, e.getKey(), e.getValue());
                }).collect(Collectors.toList());
    }

    /**
     * [prv-3] 職場計を計算する
     *
     * @param require
     * @param companyId          会社ID
     * @param supportWorkDetails 応援明細一覧
     */
    private void calculateWorkplaceTotal(SupportWorkOutputDataRequire require,
                                         String companyId,
                                         List<SupportWorkDetails> supportWorkDetails) {
        this.totalWorkplace = Optional.of(TotalValueDetail.create(require, companyId, supportWorkDetails));
    }
}
