package nts.uk.ctx.pr.core.infra.entity.wageprovision.statementbindingsetting;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* 明細書紐付け設定（マスタ基準日）: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QpbmtStateLinkSetDatePk implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * 履歴ID
    */
    @Basic(optional = false)
    @Column(name = "HIS_ID")
    public String hisId;
    
}
