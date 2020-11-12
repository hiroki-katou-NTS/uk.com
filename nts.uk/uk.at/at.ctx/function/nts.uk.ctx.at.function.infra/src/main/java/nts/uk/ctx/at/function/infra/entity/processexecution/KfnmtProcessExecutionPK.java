package nts.uk.ctx.at.function.infra.entity.processexecution;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KfnmtProcessExecutionPK implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 会社ID
	 */
	@Column(name = "CID")
	public String companyId;

	/**
	 * コード
	 */
	@Column(name = "EXEC_ITEM_CD")
	public String execItemCd;

}
