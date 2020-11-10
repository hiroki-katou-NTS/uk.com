package nts.uk.ctx.at.request.dom.application.appabsence.service.output;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.appabsence.service.RemainVacationInfo;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HdAppSet;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.DisplayReason;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.leaveapplication.VacationApplicationReflect;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 休暇申請起動時の表示情報
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppAbsenceStartInfoOutput {
	
	/**
	 * 申請表示情報
	 */
	private AppDispInfoStartupOutput appDispInfoStartupOutput;
	
	/**
	 * 休暇申請の反映
	 */
	private VacationApplicationReflect vacationAppReflect;
	
	/**
	 * 休暇申請設定
	 */
	private HdAppSet hdAppSet;
	
	/**
	 * 申請理由表示
	 */
	private DisplayReason displayReason;
	
	/**
	 * 休暇残数情報
	 */
	private RemainVacationInfo remainVacationInfo;
	
	/**
	 * 就業時間帯表示フラグ
	 */
	private boolean workHoursDisp;
	
	/**
	 * 勤務種類一覧
	 */
	private List<WorkType> workTypeLst = new ArrayList<>();
	
	/**
	 * 勤務時間帯一覧
	 */
	private List<TimezoneUse> workTimeLst = new ArrayList<>();
	
	/**
	 * 勤務種類マスタ未登録
	 */
	private boolean workTypeNotRegister;
	
	/**
	 * 特別休暇表示情報
	 */
	private Optional<SpecAbsenceDispInfo> specAbsenceDispInfo = Optional.empty();
	
	/**
	 * 選択中の勤務種類
	 */
	private Optional<String> selectedWorkTypeCD = Optional.empty();
	
	/**
	 * 選択中の就業時間帯
	 */
	private Optional<String> selectedWorkTimeCD = Optional.empty();
	
	/**
	 * 必要休暇時間
	 */
	private Optional<AttendanceTime> requiredVacationTimeOptional = Optional.empty();
	
}
