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
public class KfnctIndexReogrCatPk implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The category no. */
	@Column(name = "CATEGORY_NO")
	public BigDecimal categoryNo;
}
