package nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.appabsence;

import java.util.Optional;

import lombok.Data;

/**
 * 
 * @author thanhnx
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.申請.休暇申請.特別休暇申請
 * 特別休暇申請
 * 
 */

@Data
public class ApplyforSpecialLeaveShare {

    /**
     * 喪主フラグ
     */
    private boolean mournerFlag;
    /**
     * 続柄コード
     */
    private Optional<RelationshipCDPrimitiveShare> relationshipCD;
    /**
     * 続柄理由
     */
    private Optional<RelationshipReasonPrimitiveShare> relationshipReason;
    
    public ApplyforSpecialLeaveShare(boolean mournerFlag, Optional<RelationshipCDPrimitiveShare> relationshipCD,
            Optional<RelationshipReasonPrimitiveShare> relationshipReason) {
        this.mournerFlag = mournerFlag;
        this.relationshipCD = relationshipCD;
        this.relationshipReason = relationshipReason;
    }
}
