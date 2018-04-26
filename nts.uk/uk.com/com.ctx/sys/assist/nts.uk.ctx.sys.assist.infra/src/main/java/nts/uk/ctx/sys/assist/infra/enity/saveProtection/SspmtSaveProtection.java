package nts.uk.ctx.sys.assist.infra.enity.saveProtection;

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
    * カテゴリID
    */
    @Basic(optional = false)
    @Column(name = "CATEGORY_ID")
    public int categoryId;
    
    /**
    * 補正区分
    */
    @Basic(optional = false)
    @Column(name = "CORRECT_CLASSCIFICATION")
    public int correctClasscification;
    
    /**
    * 置き換え列
    */
    @Basic(optional = false)
    @Column(name = "REPLACE_COLUMN")
    public String replaceColumn;
    
    /**
    * テーブルNo
    */
    @Basic(optional = false)
    @Column(name = "TABLE_NO")
    public int tableNo;
    
    @Override
    protected Object getKey()
    {
        return saveProtectionPk;
    }

    /*public SaveProtection toDomain() {
        return new SaveProtection(this.categoryId, this.correctClasscification, this.replaceColumn, this.tableNo);
    }
    public static SspmtSaveProtection toEntity(SaveProtection domain) {
        return new SspmtSaveProtection(new SspmtSaveProtectionPk(), domain.getCategoryId(), domain.getCorrectClasscification(), domain.getReplaceColumn(), domain.getTableNo());
    }*/

}
