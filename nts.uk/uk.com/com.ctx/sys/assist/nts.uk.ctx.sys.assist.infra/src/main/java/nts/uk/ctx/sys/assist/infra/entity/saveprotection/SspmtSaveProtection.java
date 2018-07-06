package nts.uk.ctx.sys.assist.infra.entity.saveprotection;


import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
* 保存保護
*/
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SSPMT_SAVE_PROTECTION")
public class SspmtSaveProtection extends UkJpaEntity implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * ID
    */
    @EmbeddedId
    public SspmtSaveProtectionPk saveProtectionPk;
    
    /**
    * 補正区分
    */
    @Basic(optional = false)
    @Column(name = "CORRECT_CLASSCIFICATION")
    public int correctClasscification;
    
    @Override
    protected Object getKey()
    {
        return saveProtectionPk;
    }

   /* public SaveProtection toDomain() {
        return new SaveProtection(this.saveProtectionPk.categoryId, this.saveProtectionPk.replaceColumn, this.saveProtectionPk.tableNo, this.correctClasscification);
    }
    public static SspmtSaveProtection toEntity(SaveProtection domain) {
        return new SspmtSaveProtection(new SspmtSaveProtectionPk(domain.getCategoryId(), domain.getReplaceColumn(), domain.getTableNo()), domain.getCorrectClasscification());
    }*/

}
