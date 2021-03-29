package nts.uk.ctx.at.shared.app.command.scherec.appreflectprocess.appreflectcondition.othdwork;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.OthersReflect;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * Refactor5
 * @author huylq
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OthersReflectCommand {

	/**
     * 乖離理由を反映する
     */
    private int reflectDivergentReasonAtr;

//    private int reflectOptionalItemsAtr;

    /**
     * 加給時間を反映する
     */
    private int reflectPaytimeAtr;
    
    public OthersReflect toDomain() {
    	return new OthersReflect(
    			EnumAdaptor.valueOf(this.reflectDivergentReasonAtr, NotUseAtr.class),
    			EnumAdaptor.valueOf(this.reflectPaytimeAtr, NotUseAtr.class)
    			);
    }
}
