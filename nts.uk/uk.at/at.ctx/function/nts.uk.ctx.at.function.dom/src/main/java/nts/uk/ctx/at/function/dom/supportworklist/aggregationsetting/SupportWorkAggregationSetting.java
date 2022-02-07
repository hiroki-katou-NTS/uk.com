package nts.uk.ctx.at.function.dom.supportworklist.aggregationsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.primitive.PrimitiveValueBase;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.supportworkdata.AffiliationInforOfDailyPerforImport;
import nts.uk.ctx.at.function.dom.adapter.supportworkdata.OuenWorkTimeOfDailyImport;
import nts.uk.ctx.at.function.dom.adapter.supportworkdata.OuenWorkTimeSheetOfDailyImport;
import nts.uk.ctx.at.function.dom.adapter.supportworkdata.SupportWorkDataImport;
import nts.uk.ctx.at.function.dom.adapter.workplace.WorkPlaceInforExport;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.SupportWorkOutputDataRequire;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.就業機能.応援勤務一覧表.集計設定
 * 応援勤務集計設定
 */

@AllArgsConstructor
@Getter
public class SupportWorkAggregationSetting extends AggregateRoot {
    /**
     * 集計単位
     */
    private SupportAggregationUnit aggregationUnit;

    /**
     * 判断方法
     */
    private Optional<WorkplaceSupportJudgmentMethod> judgmentMethod;

    /**
     * 基準職場階層
     */
    private Optional<StandardWorkplaceHierarchy> standardWorkplaceHierarchy;

    /**
     * 	[1] 応援勤務明細一覧を作成する
     * 	@param require
     * 	@param companyId 会社ID
     * 	@param baseDate 基準日
     * 	@param supportWorkData 応援勤務データ
     * 	@param attendanceItemIds 勤怠項目
     * @return 応援勤務明細一覧
     */
    public List<SupportWorkDetails> createSupportWorkDetails(SupportWorkOutputDataRequire require,
                                                             String companyId,
                                                             Optional<GeneralDate> baseDate,
                                                             SupportWorkDataImport supportWorkData,
                                                             List<Integer> attendanceItemIds) {
        List<SupportWorkDetails> supportWorkDetailsList = this.createSupportWorkDetails(supportWorkData, attendanceItemIds);
        return this.determineSupportWork(require, companyId, baseDate, supportWorkDetailsList);
    }

    /**
     * [prv-1] 応援勤務明細を作成する
     * @param supportWorkData 応援勤務データ
     * @param attendanceItemIds List<勤怠項目ID>
     * @return 応援勤務明細一覧
     */
    private List<SupportWorkDetails> createSupportWorkDetails(SupportWorkDataImport supportWorkData,
                                                              List<Integer> attendanceItemIds) {
        Map<String, List<AffiliationInforOfDailyPerforImport>> affiliationInfoMap = supportWorkData.getAffiliationInforList().stream().collect(Collectors.groupingBy(AffiliationInforOfDailyPerforImport::getEmployeeId));
        Map<String, List<OuenWorkTimeSheetOfDailyImport>> ouenWorkTimeSheetOfDailyMap = supportWorkData.getOuenWorkTimeSheetOfDailyList().stream().collect(Collectors.groupingBy(OuenWorkTimeSheetOfDailyImport::getEmpId));
        Map<String, List<OuenWorkTimeOfDailyImport>> ouenWorkTimeOfDailyMap = supportWorkData.getOuenWorkTimeOfDailyList().stream().collect(Collectors.groupingBy(OuenWorkTimeOfDailyImport::getEmpId));
        List<SupportWorkDetails> result = new ArrayList<>();
        ouenWorkTimeSheetOfDailyMap.entrySet().forEach(e -> {
            result.addAll(this.createSupportWorkDetailsForEachEmployee(
                    affiliationInfoMap.getOrDefault(e.getKey(), new ArrayList<>()),
                    e.getValue(),
                    ouenWorkTimeOfDailyMap.getOrDefault(e.getKey(), new ArrayList<>()),
                    attendanceItemIds
            ));
        });
        return result;
    }

    /**
     * [prv-2] 社員ごとの応援勤務明細を作成する
     * @param affiliationInforList 	所属情報
     * @param ouenWorkTimeOfDailyList 応援作業時間
     * @param ouenWorkTimeSheetOfDailyList 応援作業時間帯
     * @param attendanceItemIds 勤怠項目
     * @return 応援勤務明細一覧
     */
    private List<SupportWorkDetails> createSupportWorkDetailsForEachEmployee(
            List<AffiliationInforOfDailyPerforImport> affiliationInforList,
            List<OuenWorkTimeSheetOfDailyImport> ouenWorkTimeSheetOfDailyList,
            List<OuenWorkTimeOfDailyImport> ouenWorkTimeOfDailyList,
            List<Integer> attendanceItemIds) {
        List<SupportWorkDetails> result = new ArrayList<>();
        ouenWorkTimeSheetOfDailyList.forEach(i -> {
            result.addAll(this.createOneDaySupportWorkDetails(
                    affiliationInforList.stream().filter(a -> a.getYmd().equals(i.getYmd())).findFirst().orElse(null),
                    i,
                    ouenWorkTimeOfDailyList.stream().filter(o -> o.getYmd().equals(i.getYmd())).findFirst().orElse(null),
                    attendanceItemIds
            ));
        });
        return result;
    }

    /**
     * [prv-3] 1日ごとの応援勤務明細を作成する
     * @param affiliationInfor 	所属情報
     * @param ouenWorkTimeSheet 応援作業時間
     * @param ouenWorkTime 応援作業時間帯
     * @param attendanceItemIds 勤怠項目
     * @return 応援勤務明細一覧
     */
    private List<SupportWorkDetails> createOneDaySupportWorkDetails(
            AffiliationInforOfDailyPerforImport affiliationInfor,
            OuenWorkTimeSheetOfDailyImport ouenWorkTimeSheet,
            OuenWorkTimeOfDailyImport ouenWorkTime,
            List<Integer> attendanceItemIds
    ) {
        String employeeId = ouenWorkTimeSheet.getEmpId();
        GeneralDate date = ouenWorkTimeSheet.getYmd();
        return ouenWorkTimeSheet.getOuenTimeSheet().stream().map(i -> this.createSupportWorkDetailForEachFrame(
                employeeId,
                date,
                affiliationInfor.getAffiliationInfor(),
                i,
                ouenWorkTime != null ? ouenWorkTime.getOuenTimes().stream().filter(j -> j.getWorkNo().v().intValue() == i.getWorkNo().v().intValue()).findFirst().orElse(null) : null,
                attendanceItemIds
        )).collect(Collectors.toList());
    }

    /**
     * [prv-4] 応援勤務枠ごとの応援勤務明細を作成する
     * @param employeeId
     * @param date
     * @param affiliationInfor
     * @param ouenWorkTimeSheet
     * @param ouenWorkTime
     * @param attendanceItemIds
     * @return 応援勤務明細
     */
    private SupportWorkDetails createSupportWorkDetailForEachFrame(
            String employeeId,
            GeneralDate date,
            AffiliationInforOfDailyAttd affiliationInfor,
            OuenWorkTimeSheetOfDailyAttendance ouenWorkTimeSheet,
            OuenWorkTimeOfDailyAttendance ouenWorkTime,
            List<Integer> attendanceItemIds
    ) {
        String affiliationInformation;
        String workInfor;
        if (aggregationUnit == SupportAggregationUnit.WORK_LOCATION) {
            affiliationInformation = affiliationInfor.getWplID(); // TODO: $所属先情報 = 所属情報.勤務場所 but doesn't have it yet
            workInfor = ouenWorkTimeSheet.getWorkContent().getWorkplace().getWorkLocationCD().map(PrimitiveValueBase::v).orElse(null);
        } else {
            affiliationInformation = affiliationInfor.getWplID();
            workInfor = ouenWorkTimeSheet.getWorkContent().getWorkplace().getWorkplaceId().v();
        }
        return SupportWorkDetails.create(
                employeeId,
                date,
                affiliationInformation,
                workInfor,
                attendanceItemIds,
                ouenWorkTimeSheet,
                ouenWorkTime
        );
    }

    /**
     * [prv-5] 応援勤務か判定する
     * @param require
     * @param companyId 会社ID
     * @param date Optional<基準日>
     * @param supportWorkDetails 応援勤務明細一覧
     * @return 応援勤務明細一覧
     */
    private List<SupportWorkDetails> determineSupportWork(SupportWorkOutputDataRequire require, String companyId, Optional<GeneralDate> date, List<SupportWorkDetails> supportWorkDetails) {
        if (aggregationUnit == SupportAggregationUnit.WORKPLACE && judgmentMethod.isPresent() && judgmentMethod.get() == WorkplaceSupportJudgmentMethod.SUPPORT_JUDGMENT_AT_WORKPLACE_LEVEL) {
            if (!date.isPresent())
                date = Optional.of(GeneralDate.today());
            return this.determineSupportWorkAtWorkplace(require, companyId, date.get(), supportWorkDetails);
        }
        return this.determineSupportWorkAtWorkLocation(supportWorkDetails);
    }

    /**
     * [prv-6] 勤務場所で応援勤務を判定する
     * @param supportWorkDetails 応援勤務明細一覧
     * @return 応援勤務明細一覧
     */
    private List<SupportWorkDetails> determineSupportWorkAtWorkLocation(List<SupportWorkDetails> supportWorkDetails) {
        supportWorkDetails.forEach(i -> {
            if (i.getAffiliationInfo() != null && !i.getAffiliationInfo().equals(i.getWorkInfo())) {
                i.setSupportWork(true);
            }
        });
        return supportWorkDetails;
    }

    /**
     * [prv-7] 勤務職場で応援勤務を判定する
     * @param require
     * @param companyId 会社ID
     * @param date 基準日
     * @param supportWorkDetails 応援勤務明細一覧
     * @return 応援勤務明細一覧
     */
    private List<SupportWorkDetails> determineSupportWorkAtWorkplace(SupportWorkOutputDataRequire require, String companyId, GeneralDate date, List<SupportWorkDetails> supportWorkDetails) {
        List<String> workplaceIds = new ArrayList<>();
        supportWorkDetails.forEach(i -> {
            workplaceIds.add(i.getAffiliationInfo());
            workplaceIds.add(i.getWorkInfo());
        });

        List<WorkPlaceInforExport> workplaceInfos = require.getWorkplaceInfos(companyId, workplaceIds, date);

        int supportJudgmentHierarchy = standardWorkplaceHierarchy.map(PrimitiveValueBase::v).orElse(0) * 3;

        supportWorkDetails.forEach(i -> {
            String affiliationHierarchyCode = workplaceInfos.stream().filter(w -> w.getWorkplaceId().equals(i.getAffiliationInfo())).map(w -> w.getHierarchyCode()).findFirst().orElse("");
            String workHierarchyCode = workplaceInfos.stream().filter(w -> w.getWorkplaceId().equals(i.getWorkInfo())).map(w -> w.getHierarchyCode()).findFirst().orElse("");
            String affiliationHierarchy = affiliationHierarchyCode.isEmpty() ? affiliationHierarchyCode : affiliationHierarchyCode.substring(0, supportJudgmentHierarchy);
            String workHierarchy = workHierarchyCode.isEmpty() ? workHierarchyCode : workHierarchyCode.substring(0, supportJudgmentHierarchy);
            if (!affiliationHierarchy.equals(workHierarchy)) {
                i.setSupportWork(true);
            }
        });

        return supportWorkDetails;
    }

}
