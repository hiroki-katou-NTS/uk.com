package nts.uk.ctx.at.request.dom.application.holidayshipment;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveApp;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentApp;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.substituteapplicationsetting.SubstituteHdWorkAppSet;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsRecRemainMngOfInPeriod;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.substituteworkapplication.SubstituteWorkAppReflect;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.vacationapplication.VacationAppReflectOption;

@Data
@AllArgsConstructor
public class HolidayShipmentOutput {

  //振出申請起動時の表示情報
    public DisplayInformationApplicationRoot applicationForWorkingDay;
    //申請表示情報
    public AppDispInfoStartupOutput appDispInfoStartup;
    //振休申請起動時の表示情報
    public DisplayInformationApplicationRoot applicationForHoliday;
    //振休残数情報
    public AbsRecRemainMngOfInPeriod remainingHolidayInfor;
    //振休振出申請設定
    public SubstituteHdWorkAppSet substituteHdWorkAppSet;
    //振休紐付け管理区分
    public ManageDistinct holidayManage;
    //代休紐付け管理区分
    public ManageDistinct substituteManagement;
    //振休申請の反映
    public VacationAppReflectOption workInfoAttendanceReflect;
    //振出申請の反映
    public SubstituteWorkAppReflect substituteWorkAppReflect;
    //振休申請
    public Optional<AbsenceLeaveApp> abs;
    //振出申請
    public Optional<RecruitmentApp> rec;
}
