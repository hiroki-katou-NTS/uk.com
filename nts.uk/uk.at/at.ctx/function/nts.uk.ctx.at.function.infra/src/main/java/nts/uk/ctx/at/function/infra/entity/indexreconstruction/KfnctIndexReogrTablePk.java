package nts.uk.ctx.at.function.infra.entity.indexreconstruction;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * The Class KfndtProcExecIndexPk.
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KfnctIndexReogrTablePk implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * The category no. 
	 * 	カテゴリNO
	 */
	@Column(name = "CATEGORY_NO")
	public BigDecimal categoryNo;

	/** The table phys name. 
	 *	 テーブル物理名									
	 */
	@Column(name = "TABLE_PHYS_NAME")
	public String tablePhysName;
}
