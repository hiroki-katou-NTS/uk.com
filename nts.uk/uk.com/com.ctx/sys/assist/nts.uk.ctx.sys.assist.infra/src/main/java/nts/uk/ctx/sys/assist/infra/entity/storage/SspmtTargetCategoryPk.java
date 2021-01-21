package nts.uk.ctx.sys.assist.infra.entity.storage;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* 対象カテゴリ: 主キー情報
*/
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class SspmtTargetCategoryPk implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * データ保存処理ID
    */
    @Basic(optional = false)
    @Column(name = "STORE_PROCESSING_ID")
    public String storeProcessingId;
    
    /**
    * カテゴリID
    */
    @Basic(optional = false)
    @Column(name = "CATEGORY_ID")
    public String categoryId;
    
    /**
     * システム種類
     */
    @Basic(optional = false)
    @Column(name = "SYSTEM_TYPE")
    public int systemType;

	public SspmtTargetCategoryPk(String storeProcessingId, String categoryId) {
		super();
		this.storeProcessingId = storeProcessingId;
		this.categoryId = categoryId;
	}
}
