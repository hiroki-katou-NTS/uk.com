package nts.uk.ctx.at.function.infra.entity.processexecution;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KfnctExecutionIndexCategoryPk implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** The company id.
	 * 	会社ID
	 */
	@Column(name = "CID")
	public String companyId;
	
	/** The exec item cd. 
	 * 	コード
	 */
	@Column(name = "EXEC_ITEM_CD")
	public String execItemCd;
	
	/** The category no. 
	 * 	カテゴリNO
	 */
	@Column(name = "CATEGORY_NO")
	public int categoryNo;
}
