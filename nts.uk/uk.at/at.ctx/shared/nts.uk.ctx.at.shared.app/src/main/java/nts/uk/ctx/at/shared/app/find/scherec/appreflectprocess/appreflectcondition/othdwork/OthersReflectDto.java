package nts.uk.ctx.at.shared.app.find.scherec.appreflectprocess.appreflectcondition.othdwork;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.overtimeholidaywork.OthersReflect;

/**
 * Refactor5
 * @author huylq
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OthersReflectDto {

	/**
     * 乖離理由を反映する
     */
    private int reflectDivergentReasonAtr;

//    private int reflectOptionalItemsAtr;

    /**
     * 加給時間を反映する
     */
    private int reflectPaytimeAtr;
    
    public static OthersReflectDto fromDomain(OthersReflect domain) {
    	return new OthersReflectDto(domain.getReflectDivergentReasonAtr().value, domain.getReflectPaytimeAtr().value);
    }
}
