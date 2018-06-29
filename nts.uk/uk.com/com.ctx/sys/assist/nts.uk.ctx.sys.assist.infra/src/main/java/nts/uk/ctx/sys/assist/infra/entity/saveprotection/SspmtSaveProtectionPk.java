package nts.uk.ctx.sys.assist.infra.entity.saveprotection;


import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* 保存保護: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class SspmtSaveProtectionPk implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * カテゴリID
    */
    @Basic(optional = false)
    @Column(name = "CATEGORY_ID")
    public int categoryId;
    
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
    
}

