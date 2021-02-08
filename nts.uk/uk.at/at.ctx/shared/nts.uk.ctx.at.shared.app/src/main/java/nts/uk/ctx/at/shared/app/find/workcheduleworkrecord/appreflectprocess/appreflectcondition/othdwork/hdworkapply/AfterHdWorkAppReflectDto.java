package nts.uk.ctx.at.shared.app.find.workcheduleworkrecord.appreflectprocess.appreflectcondition.othdwork.hdworkapply;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.app.find.workcheduleworkrecord.appreflectprocess.appreflectcondition.othdwork.BreakApplicationDto;
import nts.uk.ctx.at.shared.app.find.workcheduleworkrecord.appreflectprocess.appreflectcondition.othdwork.OthersReflectDto;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.hdworkapply.AfterHdWorkAppReflect;

/**
 * Refactor5
 * @author huylq
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AfterHdWorkAppReflectDto {
	
	/**
     * その他項目を反映する
     */
	private OthersReflectDto othersReflect;
	
	/**
     * 休憩・外出を申請反映する
     */
	private BreakApplicationDto breakLeaveApplication;

	/**
     * 出退勤を反映する
     */
    private int workReflect;
    
    public static AfterHdWorkAppReflectDto fromDomain(AfterHdWorkAppReflect domain) {
    	return new AfterHdWorkAppReflectDto(
    			OthersReflectDto.fromDomain(domain.getOthersReflect()),
    			BreakApplicationDto.fromDomain(domain.getBreakLeaveApplication()),
    			domain.getWorkReflect().value);
    }
}
