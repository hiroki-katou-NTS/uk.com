package nts.uk.ctx.pr.transfer.infra.entity.sourcebank;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * @author HungTT
 *
 */

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class QxxmtTrfSrcBankPk {

	/**
	 * 会社ID
	 */
	@Column(name = "CID")
	public String companyId;

	/**
	 * コード
	 */
	@Column(name = "CD")
	public String code;
	
}
