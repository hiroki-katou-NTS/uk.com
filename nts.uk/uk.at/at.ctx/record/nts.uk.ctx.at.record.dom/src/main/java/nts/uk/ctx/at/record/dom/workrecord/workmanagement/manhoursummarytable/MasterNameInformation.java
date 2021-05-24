package nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.adapter.function.alarmworkplace.EmployeeInfoImport;
import nts.uk.ctx.at.shared.dom.workplace.WorkplaceInformation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * TP: マスタ名称情報
 */
@AllArgsConstructor
@Getter
public class MasterNameInformation {
    /** 所属職場情報リスト */
    private List<WorkplaceInformation> affWorkplaceInfoList;
    /** 勤務職場情報リスト */
    private List<String> workPlaceInfoList;    //TODO: ĐANG QA
    /** 社員情報リスト */
    private List<EmployeeInfoImport> employeeInfoList;
    /**	作業1リスト */
    private List<String> work1List;
    /** 作業2リスト */
    private List<String> work2List;
    /** 作業3リスト */
    private List<String> work3List;
    /** 作業4リスト */
    private List<String> work4List;
    /** 作業5リスト */
    private List<String> work5List;

    /**
     * 	[1] 表示情報を取得する
     * @param code コード
     * @param summaryItemType 集計項目種類
     * @return 表示情報
     */
    public DisplayInformation getDisplayInfo(String code, SummaryItemType summaryItemType) {
        DisplayInformation dispInfo = null;
        switch (summaryItemType) {
            case AFFILIATION_WORKPLACE:
                val wkplInfo = affWorkplaceInfoList.stream().filter(x -> x.getWorkplaceId().equals(code)).findFirst();
                dispInfo = createDisplayInfo(Optional.of(wkplInfo.get().getWorkplaceCode().v()), Optional.of(wkplInfo.get().getWorkplaceDisplayName().v()));
                break;
            case WORKPLACE:
                // TODO: ĐANG QA
                break;
            case EMPLOYEE:
                val empInfo = employeeInfoList.stream().filter(x -> x.getSid().equals(code)).findFirst();
                dispInfo = createDisplayInfo(Optional.of(empInfo.get().getEmployeeCode()), Optional.of(empInfo.get().getEmployeeName()));
                break;
            case JOB1:
                // TODO: ĐANG QA
                break;
            case JOB2:
                // TODO: ĐANG QA
                break;
            case JOB3:
                // TODO: ĐANG QA
                break;
            case JOB4:
                // TODO: ĐANG QA
                break;
            case JOB5:
                // TODO: ĐANG QA
                break;
            default:
                break;
        }
        return dispInfo;
    }

    /**
     * [2] 作業詳細データを絞り込む
     * @param workDetailList List<作業詳細データ>
     * @return List<作業詳細データ>
     */
    public List<WorkDetailData> filterWorkDetailData(List<WorkDetailData> workDetailList) {
//        val wkplIds = //TODO
        val empIds = employeeInfoList.stream().map(EmployeeInfoImport::getSid).collect(Collectors.toList());
        val affWkplIds = affWorkplaceInfoList.stream().map(WorkplaceInformation::getWorkplaceId).collect(Collectors.toList());

        return workDetailList.stream().filter(x -> (!empIds.isEmpty() && empIds.contains(x.getEmployeeId()))
                && (!affWkplIds.isEmpty() && affWkplIds.contains(x.getAffWorkplaceId()))
//        && 	filter @勤務職場情報リスト.含む($.所属職場ID) //TODO
//                        && (!wkplIds.isEmpty() && wkplIds.contains(x.getWorkplaceId())) //TODO
                && (!work1List.isEmpty() && work1List.contains(x.getWorkCode1()))
                && (!work2List.isEmpty() && work2List.contains(x.getWorkCode2()))
                && (!work3List.isEmpty() && work3List.contains(x.getWorkCode3()))
                && (!work4List.isEmpty() && work4List.contains(x.getWorkCode4()))
                && (!work5List.isEmpty() && work5List.contains(x.getWorkCode5())))
                .collect(Collectors.toList());
    }

    /**
     * [prv-1] 表示情報を作成する
     * @param code 	コード
     * @param name 	名称
     * @return 	表示情報
     */
    private DisplayInformation createDisplayInfo(Optional<String> code, Optional<String> name) {
        if (!code.isPresent())
            return DisplayInformation.deletedMaster();
        if (!name.isPresent())
            return DisplayInformation.cannnotGetName(code.get());
        return new DisplayInformation(code.get(), name.get());
    }
}
