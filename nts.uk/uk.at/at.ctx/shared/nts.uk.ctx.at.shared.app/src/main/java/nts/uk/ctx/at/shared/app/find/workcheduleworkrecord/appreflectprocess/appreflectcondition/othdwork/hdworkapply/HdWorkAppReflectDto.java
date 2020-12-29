package nts.uk.ctx.at.shared.app.find.workcheduleworkrecord.appreflectprocess.appreflectcondition.othdwork.hdworkapply;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.hdworkapply.HdWorkAppReflect;

/**
 * Refactor5
 * @author huylq
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HdWorkAppReflectDto {

	/**
     * 事前
     */
    private BeforeHdWorkAppReflectDto before;
    
    /**
     * 事後
     */
    private AfterHdWorkAppReflectDto after;
    
    public static HdWorkAppReflectDto fromDomain(HdWorkAppReflect domain) {
        return new HdWorkAppReflectDto(
        		BeforeHdWorkAppReflectDto.fromDomain(domain.getBefore()),
        		AfterHdWorkAppReflectDto.fromDomain(domain.getAfter())
        		);
    }
}
