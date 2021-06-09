package nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.val;
import nts.uk.ctx.at.record.dom.adapter.function.alarmworkplace.EmployeeInfoImport;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceInfor;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.Task;

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
    private List<WorkplaceInfor> affWorkplaceInfoList;
    /** 勤務職場情報リスト */
    private List<WorkplaceInfor> workPlaceInfoList;
    /** 社員情報リスト */
    private List<EmployeeInfoImport> employeeInfoList;
    /**	作業1リスト */
    private List<TaskImport> work1List;
    /** 作業2リスト */
    private List<TaskImport> work2List;
    /** 作業3リスト */
    private List<TaskImport> work3List;
    /** 作業4リスト */
    private List<TaskImport> work4List;
    /** 作業5リスト */
    private List<TaskImport> work5List;

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
                val affWkplInfo = affWorkplaceInfoList.stream().filter(x -> x.getWorkplaceId().equals(code)).findFirst();
                if (affWkplInfo.isPresent())
                    dispInfo = createDisplayInfo(Optional.of(affWkplInfo.get().getWorkplaceCode()), Optional.of(affWkplInfo.get().getWorkplaceDisplayName()));
                else
                    dispInfo = createDisplayInfo(Optional.empty(), Optional.empty());
                break;
            case WORKPLACE:
                val wkplInfo = workPlaceInfoList.stream().filter(x -> x.getWorkplaceId().equals(code)).findFirst();
                if (wkplInfo.isPresent())
                    dispInfo = createDisplayInfo(Optional.of(wkplInfo.get().getWorkplaceCode()), Optional.of(wkplInfo.get().getWorkplaceDisplayName()));
                else
                    dispInfo = createDisplayInfo(Optional.empty(), Optional.empty());
                break;
            case EMPLOYEE:
                val empInfo = employeeInfoList.stream().filter(x -> x.getSid().equals(code)).findFirst();
                if (empInfo.isPresent())
                    dispInfo = createDisplayInfo(Optional.of(empInfo.get().getEmployeeCode()), Optional.of(empInfo.get().getEmployeeName()));
                else
                    dispInfo = createDisplayInfo(Optional.empty(), Optional.empty());
                break;
            case JOB1:
                val targetWork1 = work1List.stream().filter(x -> x.getCode().equals(code)).findFirst();
                if (targetWork1.isPresent())
                    dispInfo = createDisplayInfo(Optional.of(targetWork1.get().getCode()), Optional.of(targetWork1.get().getTaskName()));
                else
                    dispInfo = createDisplayInfo(Optional.empty(), Optional.empty());
                break;
            case JOB2:
                val targetWork2 = work2List.stream().filter(x -> x.getCode().equals(code)).findFirst();
                if (targetWork2.isPresent())
                    dispInfo = createDisplayInfo(Optional.of(targetWork2.get().getCode()), Optional.of(targetWork2.get().getTaskName()));
                else
                    dispInfo = createDisplayInfo(Optional.empty(), Optional.empty());
                break;
            case JOB3:
                val targetWork3 = work3List.stream().filter(x -> x.getCode().equals(code)).findFirst();
                if (targetWork3.isPresent())
                    dispInfo = createDisplayInfo(Optional.of(targetWork3.get().getCode()), Optional.of(targetWork3.get().getTaskName()));
                else
                    dispInfo = createDisplayInfo(Optional.empty(), Optional.empty());
                break;
            case JOB4:
                val targetWork4 = work4List.stream().filter(x -> x.getCode().equals(code)).findFirst();
                if (targetWork4.isPresent())
                    dispInfo = createDisplayInfo(Optional.of(targetWork4.get().getCode()), Optional.of(targetWork4.get().getTaskName()));
                else
                    dispInfo = createDisplayInfo(Optional.empty(), Optional.empty());
                break;
            case JOB5:
                val targetWork5 = work5List.stream().filter(x -> x.getCode().equals(code)).findFirst();
                if (targetWork5.isPresent())
                    dispInfo = createDisplayInfo(Optional.of(targetWork5.get().getCode()), Optional.of(targetWork5.get().getTaskName()));
                else
                    dispInfo = createDisplayInfo(Optional.empty(), Optional.empty());
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
        val affWkplIds = affWorkplaceInfoList.stream().map(WorkplaceInfor::getWorkplaceId).collect(Collectors.toList());
        val wkplIds = workPlaceInfoList.stream().map(WorkplaceInfor::getWorkplaceId).collect(Collectors.toList());
        val empIds = employeeInfoList.stream().map(EmployeeInfoImport::getSid).collect(Collectors.toList());
        val task1Codes = work1List.stream().map(TaskImport::getCode).collect(Collectors.toList());
        val task2Codes = work2List.stream().map(TaskImport::getCode).collect(Collectors.toList());
        val task3Codes = work3List.stream().map(TaskImport::getCode).collect(Collectors.toList());
        val task4Codes = work4List.stream().map(x -> x.getCode()).collect(Collectors.toList());
        val task5Codes = work5List.stream().map(x -> x.getCode()).collect(Collectors.toList());

        return workDetailList.stream().filter(x -> (!empIds.isEmpty() && empIds.contains(x.getEmployeeId()))
                && (!affWkplIds.isEmpty() && affWkplIds.contains(x.getAffWorkplaceId()))
                && (!wkplIds.isEmpty() && wkplIds.contains(x.getWorkplaceId()))
                && (!task1Codes.isEmpty() && task1Codes.contains(x.getWorkCode1()))
                && (!task2Codes.isEmpty() && task2Codes.contains(x.getWorkCode2()))
                && (!task3Codes.isEmpty() && task3Codes.contains(x.getWorkCode3()))
                && (!task4Codes.isEmpty() && task4Codes.contains(x.getWorkCode4()))
                && (!task5Codes.isEmpty() && task5Codes.contains(x.getWorkCode5())))
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
            return DisplayInformation.cannotGetName(code.get());
        return new DisplayInformation(code.get(), name.get());
    }
}
