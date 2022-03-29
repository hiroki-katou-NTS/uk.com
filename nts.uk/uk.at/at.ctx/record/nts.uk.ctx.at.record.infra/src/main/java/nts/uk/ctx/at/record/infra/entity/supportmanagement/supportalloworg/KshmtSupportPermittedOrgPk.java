package nts.uk.ctx.at.record.infra.entity.supportmanagement.supportalloworg;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KshmtSupportPermittedOrgPk implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 対象組織の単位
     * 0: 職場
     * 1: 職場グループ
     */
    @Column(name = "TARGET_UNIT")
    private int targetUnit;
    /**
     * 対象組織の単位に応じたID
     * TARGET_UNIT = 0 -> 職場ID
     * TARGET_UNIT = 1 -> 職場グループID
     */
    @Column(name = "TARGET_ID")
    private String targetId;
    /**
     * 許可される組織の単位
     * 対象組織の単位
     * 0: 職場
     * 1: 職場グループ
     */
    @Column(name = "PERMITTED_TARGET_UNIT")
    private int permittedTargetUnit;
    /**
     * 許可される組織の単位に応じたID
     * 対象組織の単位に応じたID
     * PERMITTED_TARGET_UNIT = 0 -> 職場ID
     * PERMITTED_TARGET_UNIT = 1 -> 職場グループID
     */
    @Column(name = "PERMITTED_TARGET_ID")
    private String permittedTargetId;
}
