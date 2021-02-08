package nts.uk.ctx.at.request.app.command.application.overtime;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.app.command.workcheduleworkrecord.appreflectprocess.appreflectcondition.othdwork.hdworkapply.HdWorkAppReflectCommand;
import nts.uk.ctx.at.shared.app.command.workcheduleworkrecord.appreflectprocess.appreflectcondition.othdwork.otworkapply.OtWorkAppReflectCommand;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.AppReflectOtHdWork;

public class AppReflectOtHdWorkCommand {
	/**
     * 会社ID
     */
    public String companyId;

    /**
     * 休日出勤申請
     */
    public HdWorkAppReflectCommand holidayWorkAppReflect;

    /**
     * 残業申請
     */
    public OtWorkAppReflectCommand overtimeWorkAppReflect;

    /**
     * 時間外深夜時間を反映する
     */
    public Integer nightOvertimeReflectAtr;
    
    
    public AppReflectOtHdWork toDomain() {
    	return new AppReflectOtHdWork(
    			companyId,
    			holidayWorkAppReflect.toDomain(),
    			overtimeWorkAppReflect.toDomain(),
    			EnumAdaptor.valueOf(nightOvertimeReflectAtr, NotUseAtr.class));
    }
}
