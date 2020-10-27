package nts.uk.ctx.sys.assist.infra.entity.categoryfieldmt;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
* カテゴリ項目マスタ: 主キー情報
*/
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class SspmtSaveCategoryFieldPk implements Serializable
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
