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
                Optional.ofNullable(relationshipCD).flatMap(x -> Optional.of(new RelationshipCDPrimitive(x))), 
                Optional.ofNullable(relationshipReason).flatMap(x -> Optional.of(new RelationshipReasonPrimitive(x))));
    }
    
    public static ApplyforSpecialLeaveDto fromDomain(ApplyforSpecialLeave domain) {
        return new ApplyforSpecialLeaveDto(
                domain.isMournerFlag(), 
                domain.getRelationshipCD().map(x -> x.v()).orElse(null), 
                domain.getRelationshipReason().map(x -> x.v()).orElse(null));
    }
}
