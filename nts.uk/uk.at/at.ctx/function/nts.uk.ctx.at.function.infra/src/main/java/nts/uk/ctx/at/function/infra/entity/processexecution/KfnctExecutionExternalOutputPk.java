package nts.uk.ctx.at.function.infra.entity.processexecution;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * The Class KfnctExecutionExternalOutputPk.
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KfnctExecutionExternalOutputPk implements Serializable {

	/** The Constant serialVersionUID. */
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
	
	/** The ext output cd. */
	@Column(name = "EXT_OUTPUT_CD")
	public String extOutputCd;
}
