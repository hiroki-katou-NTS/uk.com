package nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.adapter.function.alarmworkplace.EmployeeInfoImport;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceInfor;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.Task;
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
    private List<WorkplaceInfor> affWorkplaceInfoList;
    /** 勤務職場情報リスト */
    private List<WorkplaceInfor> workPlaceInfoList;
    /** 社員情報リスト */
    private List<EmployeeInfoImport> employeeInfoList;
    /**	作業1リスト */
    private List<Task> work1List;
    /** 作業2リスト */
    private List<Task> work2List;
    /** 作業3リスト */
    private List<Task> work3List;
    /** 作業4リスト */
    private List<Task> work4List;
    /** 作業5リスト */
    private List<Task> work5List;

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
                val wkplInfo = affWorkplaceInfoList.stream().filter(x -> x.getWorkplaceId().equals(code)).findFirst();
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
                val targetWork1 = work1List.stream().filter(x -> x.getCode().v().equals(code)).findFirst();
                if (targetWork1.isPresent())
                    dispInfo = createDisplayInfo(Optional.of(targetWork1.get().getCode().v()), Optional.of(targetWork1.get().getDisplayInfo().getTaskName().v()));
                else
                    dispInfo = createDisplayInfo(Optional.empty(), Optional.empty());
                break;
            case JOB2:
                val targetWork2 = work2List.stream().filter(x -> x.getCode().v().equals(code)).findFirst();
                if (targetWork2.isPresent())
                    dispInfo = createDisplayInfo(Optional.of(targetWork2.get().getCode().v()), Optional.of(targetWork2.get().getDisplayInfo().getTaskName().v()));
                else
                    dispInfo = createDisplayInfo(Optional.empty(), Optional.empty());
                break;
            case JOB3:
                val targetWork3 = work3List.stream().filter(x -> x.getCode().v().equals(code)).findFirst();
                if (targetWork3.isPresent())
                    dispInfo = createDisplayInfo(Optional.of(targetWork3.get().getCode().v()), Optional.of(targetWork3.get().getDisplayInfo().getTaskName().v()));
                else
                    dispInfo = createDisplayInfo(Optional.empty(), Optional.empty());
                break;
            case JOB4:
                val targetWork4 = work4List.stream().filter(x -> x.getCode().v().equals(code)).findFirst();
                if (targetWork4.isPresent())
                    dispInfo = createDisplayInfo(Optional.of(targetWork4.get().getCode().v()), Optional.of(targetWork4.get().getDisplayInfo().getTaskName().v()));
                else
                    dispInfo = createDisplayInfo(Optional.empty(), Optional.empty());
                break;
            case JOB5:
                val targetWork5 = work5List.stream().filter(x -> x.getCode().v().equals(code)).findFirst();
                if (targetWork5.isPresent())
                    dispInfo = createDisplayInfo(Optional.of(targetWork5.get().getCode().v()), Optional.of(targetWork5.get().getDisplayInfo().getTaskName().v()));
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
//        val wkplIds = //TODO
        val empIds = employeeInfoList.stream().map(EmployeeInfoImport::getSid).collect(Collectors.toList());
        val affWkplIds = affWorkplaceInfoList.stream().map(WorkplaceInfor::getWorkplaceId).collect(Collectors.toList());

        return workDetailList.stream().filter(x -> (!empIds.isEmpty() && empIds.contains(x.getEmployeeId()))
                && (!affWkplIds.isEmpty() && affWkplIds.contains(x.getAffWorkplaceId()))
//        && 	filter @勤務職場情報リスト.含む($.所属職場ID) //TODO
//                        && (!wkplIds.isEmpty() && wkplIds.contains(x.getWorkplaceId())) //TODO
                && (!work1List.isEmpty() && work1List.stream().filter(x -> x.getCode().contains(x.getWorkCode1())))
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
            return DisplayInformation.cannotGetName(code.get());
        return new DisplayInformation(code.get(), name.get());
    }
}
