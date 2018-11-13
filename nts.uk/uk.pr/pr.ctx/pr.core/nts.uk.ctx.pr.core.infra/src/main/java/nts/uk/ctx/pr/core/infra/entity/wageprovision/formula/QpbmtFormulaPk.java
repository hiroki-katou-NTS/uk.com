package nts.uk.ctx.pr.core.infra.entity.wageprovision.formula;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* 計算式: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QpbmtFormulaPk implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * 会社ID
    */
    @Basic(optional = false)
    @Column(name = "CID")
    public String cid;
    
    /**
    * 計算式コード
    */
    @Basic(optional = false)
    @Column(name = "FORMULA_CODE")
    public String formulaCode;
    
}
