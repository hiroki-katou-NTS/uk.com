package nts.uk.ctx.exio.infra.entity.exo.category;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* 外部出力カテゴリ: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class OiomtExOutCtgPk implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * 
    */
    @Basic(optional = false)
    @Column(name = "CATEGORY_ID")
    public String categoryId;
    
}
