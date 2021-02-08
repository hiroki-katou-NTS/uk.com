package nts.uk.ctx.at.request.app.find.application.overtime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.app.find.workcheduleworkrecord.appreflectprocess.appreflectcondition.othdwork.hdworkapply.HdWorkAppReflectDto;
import nts.uk.ctx.at.shared.app.find.workcheduleworkrecord.appreflectprocess.appreflectcondition.othdwork.otworkapply.OtWorkAppReflectDto;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.AppReflectOtHdWork;

@AllArgsConstructor
@NoArgsConstructor
public class AppReflectOtHdWorkDto {
	 /**
     * 会社ID
     */
    public String companyId;

    /**
     * 休日出勤申請
     */
    public HdWorkAppReflectDto holidayWorkAppReflect;

    /**
     * 残業申請
     */
    public OtWorkAppReflectDto overtimeWorkAppReflect;

    /**
     * 時間外深夜時間を反映する
     */
    public Integer nightOvertimeReflectAtr;
    
    public static AppReflectOtHdWorkDto fromDomain(AppReflectOtHdWork appReflectOtHdWork) {
    	
    	return new AppReflectOtHdWorkDto(
    			appReflectOtHdWork.getCompanyId(),
    			HdWorkAppReflectDto.fromDomain(appReflectOtHdWork.getHolidayWorkAppReflect()),
    			OtWorkAppReflectDto.fromDomain(appReflectOtHdWork.getOvertimeWorkAppReflect()),
    			appReflectOtHdWork.getNightOvertimeReflectAtr().value);
    }
}
