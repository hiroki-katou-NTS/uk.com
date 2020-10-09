package nts.uk.ctx.sys.assist.infra.entity.categoryfieldmtfordelete;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
* データ削除カテゴリ項目マスタ
*/
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class SspmtCategoryFieldMtForDeletePk implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
    * カテゴリID
    */
    @NotNull
    @Column(name = "CATEGORY_ID")
    public String categoryId;
    
    /**
    * テーブルNo
    */
    @NotNull
    @Column(name = "TABLE_NO")
    public int tableNo;
    
    /**
	 * システム種類
	 */
	@Column(name = "SYSTEM_TYPE")
	public int systemType;
}
