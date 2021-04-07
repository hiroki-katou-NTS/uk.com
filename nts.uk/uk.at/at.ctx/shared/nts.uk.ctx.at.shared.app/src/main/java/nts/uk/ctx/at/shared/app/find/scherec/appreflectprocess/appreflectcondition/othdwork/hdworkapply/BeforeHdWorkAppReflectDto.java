package nts.uk.ctx.at.shared.app.find.scherec.appreflectprocess.appreflectcondition.othdwork.hdworkapply;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.hdworkapply.BeforeHdWorkAppReflect;
/**
 * Refactor5
 * @author huylq
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BeforeHdWorkAppReflectDto {
	
	/**
     * 休日出勤時間を実績項目へ反映する
     */
    private int reflectActualHolidayWorkAtr;
    
    public static BeforeHdWorkAppReflectDto fromDomain(BeforeHdWorkAppReflect domain) {
    	return new BeforeHdWorkAppReflectDto(domain.getReflectActualHolidayWorkAtr().value);
    }
}
