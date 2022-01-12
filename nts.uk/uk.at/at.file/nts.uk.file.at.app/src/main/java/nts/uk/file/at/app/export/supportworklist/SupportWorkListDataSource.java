package nts.uk.file.at.app.export.supportworklist;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.workplace.WorkPlaceInforExport;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.SupportWorkListOutputSetting;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.outputdata.SupportWorkOutputData;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocation;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeBasicInfoImport;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemName;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.Task;
import nts.uk.ctx.bs.company.dom.company.Company;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Data
public class SupportWorkListDataSource {
    /**集計単位*/
    private int aggregationUnit;

    /**応援勤務表コード*/
    private String supportWorkCode;

    /**期間*/
    private DatePeriod period;

    /**ヘッダ情報<List>*/
    private List<SupportWorkHeaderInfo> lstHeaderInfo;

    /**応援勤務一覧表の出力設定*/
    private SupportWorkListOutputSetting supportWorkOutputSetting;

    /**応援勤務出力データ*/
    private SupportWorkOutputData supportWorkOutputData;

    /**会社の日次項目<List>*/
    private List<AttItemName> attendanceItems;

    /**職場情報一覧<List>*/
    private List<WorkPlaceInforExport> workplaceInfoList;

    /**勤務場所<List>     -------今回対象外(2021/12)*/
    private List<WorkLocation> workLocations;

    /**社員情報<List>*/
    private List<EmployeeBasicInfoImport> employeeInfoList;

    /**会社情報*/
    private Optional<Company> companyInfo;

    /**作業リスト_①*/
    private List<Task> workList1;

    /**作業リスト_②*/
    private List<Task> workList2;

    /**作業リスト_③*/
    private List<Task> workList3;

    /**作業リスト_④*/
    private List<Task> workList4;

    /**作業リスト_⑤*/
    private List<Task> workList5;

    /**前回応援勤務表コード*/
//    private String lastSupportWorkCode;  //TODO: QA

    /**前回職場リスト*/  //TODO: QA

    /**前回場所リスト*/  //TODO: QA

}
