package nts.uk.ctx.at.shared.app.command.scherec.appreflectprocess.appreflectcondition.othdwork.hdworkapply;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.app.command.scherec.appreflectprocess.appreflectcondition.othdwork.BreakApplicationCommand;
import nts.uk.ctx.at.shared.app.command.scherec.appreflectprocess.appreflectcondition.othdwork.OthersReflectCommand;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.hdworkapply.AfterHdWorkAppReflect;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * Refactor5
 * @author huylq
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AfterHdWorkAppReflectCommand {
	
	/**
     * その他項目を反映する
     */
	private OthersReflectCommand othersReflect;
	
	/**
     * 休憩・外出を申請反映する
     */
	private BreakApplicationCommand breakLeaveApplication;

	/**
     * 出退勤を反映する
     */
    private int workReflect;
    
    public AfterHdWorkAppReflect toDomain() {
    	return new AfterHdWorkAppReflect(
    			this.othersReflect.toDomain(),
    			this.breakLeaveApplication.toDomain(),
    			EnumAdaptor.valueOf(this.workReflect, NotUseAtr.class));
    }
}
