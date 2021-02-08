package nts.uk.ctx.at.function.infra.entity.indexreconstruction;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * The Class KfndtProcExecIndexPk.
 */
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class KfnctIndexReorgTablePk implements Serializable {

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The category no.<br>
	 * カテゴリNO
	 */
	@Column(name = "CATEGORY_NO")
	public int categoryNo;

	/**
	 * The table phys name.<br>
	 * テーブル物理名
	 */
	@Column(name = "TABLE_PHYS_NAME")
	public String tablePhysName;

}
