package nts.uk.ctx.at.request.app.find.application.holidayshipment.refactor5.dto;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.app.command.application.holidayshipment.refactor5.command.AbsenceLeaveAppCmd;
import nts.uk.ctx.at.request.app.command.application.holidayshipment.refactor5.command.RecruitmentAppCmd;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.substituteapplicationsetting.SubstituteHdWorkAppSetDto;
import nts.uk.ctx.at.request.dom.application.holidayshipment.HolidayShipmentOutput;
import nts.uk.ctx.at.shared.app.find.scherec.appreflectprocess.appreflectcondition.substituteworkapplication.SubstituteWorkAppReflectDto;
import nts.uk.ctx.at.shared.app.find.scherec.appreflectprocess.appreflectcondition.vacationapplication.VacationAppReflectOptionDto;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

/**
 * @author thanhpv
 * 振休振出申請起動時の表示情報
 */
@NoArgsConstructor
@Getter
@Setter
public class DisplayInforWhenStarting {

	//振出申請起動時の表示情報
	public DisplayInformationApplication applicationForWorkingDay;
	//申請表示情報
	public AppDispInfoStartupDto appDispInfoStartup;
	//振休申請起動時の表示情報
	public DisplayInformationApplication applicationForHoliday;
	//振休残数情報
	public RemainingHolidayInforDto remainingHolidayInfor;
	//振休振出申請設定
	public SubstituteHdWorkAppSetDto substituteHdWorkAppSet;
	//振休紐付け管理区分
	public Integer holidayManage;
	//代休紐付け管理区分
	public Integer substituteManagement;
	//振休申請の反映
	public VacationAppReflectOptionDto workInfoAttendanceReflect;
	//振出申請の反映
	public SubstituteWorkAppReflectDto substituteWorkAppReflect;
	//振休申請
	public AbsenceLeaveAppCmd abs;
	//振出申請
	public RecruitmentAppCmd rec;
	
	public boolean checkFlag;
	
	/** 代行申請か có phải người đại diện không*/
	public boolean represent;
	
	public boolean existAbs() {
		return this.abs != null;
	}
	
	public boolean existRec() {
		return this.rec != null;
	}
	
	public HolidayShipmentOutput toDomain() {
	    return new HolidayShipmentOutput(
	            applicationForWorkingDay == null ? null : applicationForWorkingDay.toDomain(), 
	            appDispInfoStartup.toDomain(), 
	            applicationForHoliday == null ? null : applicationForHoliday.toDomain(), 
	            remainingHolidayInfor.toDomain(), 
	            substituteHdWorkAppSet.toDomain(), 
	            EnumAdaptor.valueOf(holidayManage, ManageDistinct.class), 
	            EnumAdaptor.valueOf(substituteManagement, ManageDistinct.class), 
	            workInfoAttendanceReflect.toDomain(), 
	            substituteWorkAppReflect.toDomain(), 
	            this.existAbs() ? Optional.of(abs.toDomainAbs()) : Optional.empty(), 
	            this.existRec() ? Optional.of(rec.toDomain()) : Optional.empty());
	}
}
