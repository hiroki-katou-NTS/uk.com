package nts.uk.ctx.at.request.app.command.application.appabsence;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.dom.application.appabsence.appforspecleave.ApplyforSpecialLeave;
import nts.uk.ctx.at.request.dom.application.appabsence.appforspecleave.RelationshipCDPrimitive;
import nts.uk.ctx.at.request.dom.application.appabsence.appforspecleave.RelationshipReasonPrimitive;

/**
 * @author anhnm
 * 特別休暇申請
 *
 */
@Data
@AllArgsConstructor
public class ApplyforSpecialLeaveDto {

    /**
     * 喪主フラグ
     */
    private boolean mournerFlag;
    
    /**
     * 続柄コード
     */
    private String relationshipCD;
    
    /**
     * 続柄理由
     */
    private String relationshipReason;
    
    public ApplyforSpecialLeave toDomain() {
        return new ApplyforSpecialLeave(
                mournerFlag, 
                Optional.ofNullable(new RelationshipCDPrimitive(relationshipCD)), 
                Optional.ofNullable(new RelationshipReasonPrimitive(relationshipReason)));
    }
}
