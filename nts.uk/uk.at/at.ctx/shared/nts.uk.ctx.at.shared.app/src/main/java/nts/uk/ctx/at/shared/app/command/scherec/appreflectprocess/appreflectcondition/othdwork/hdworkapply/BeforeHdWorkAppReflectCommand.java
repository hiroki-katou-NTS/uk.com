package nts.uk.ctx.at.shared.app.command.scherec.appreflectprocess.appreflectcondition.othdwork.hdworkapply;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.hdworkapply.BeforeHdWorkAppReflect;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * Refactor5
 * @author huylq
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BeforeHdWorkAppReflectCommand {

	/**
     * 休日出勤時間を実績項目へ反映する
     */
    private int reflectActualHolidayWorkAtr;
    
    public BeforeHdWorkAppReflect toDomain() {
    	return new BeforeHdWorkAppReflect(EnumAdaptor.valueOf(this.reflectActualHolidayWorkAtr, NotUseAtr.class));
    }
}
