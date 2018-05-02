package nts.uk.ctx.sys.assist.infra.entity.categoryfieldmaster;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* カテゴリ項目マスタ: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class SspmtCategoryFieldMtPk implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * カテゴリID
    */
    @Basic(optional = false)
    @Column(name = "CATEGORY_ID")
    public String categoryId;
    
    /**
    * テーブルNo
    */
    @Basic(optional = false)
    @Column(name = "TABLE_NO")
    public int tableNo;
    
}
