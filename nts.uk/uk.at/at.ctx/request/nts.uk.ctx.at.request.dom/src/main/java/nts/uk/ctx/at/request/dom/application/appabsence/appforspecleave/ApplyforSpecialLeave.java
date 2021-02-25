package nts.uk.ctx.at.request.dom.application.appabsence.appforspecleave;

import java.util.Optional;

import lombok.Data;

/**
 * 
 * @author anhnm
 * Refactor 5
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.申請.休暇申請.特別休暇申請
 * 特別休暇申請
 * 
 */

@Data
public class ApplyforSpecialLeave {

    /**
     * 喪主フラグ
     */
    private boolean mournerFlag;
    /**
     * 続柄コード
     */
    private Optional<RelationshipCDPrimitive> relationshipCD;
    /**
     * 続柄理由
     */
    private Optional<RelationshipReasonPrimitive> relationshipReason;
    
    public ApplyforSpecialLeave(boolean mournerFlag, Optional<RelationshipCDPrimitive> relationshipCD,
            Optional<RelationshipReasonPrimitive> relationshipReason) {
        this.mournerFlag = mournerFlag;
        this.relationshipCD = relationshipCD;
        this.relationshipReason = relationshipReason;
    }
}
