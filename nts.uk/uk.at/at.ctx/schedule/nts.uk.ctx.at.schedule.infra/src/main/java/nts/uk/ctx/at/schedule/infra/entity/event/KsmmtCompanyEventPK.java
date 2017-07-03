/**
 * 9:56:08 AM Jun 13, 2017
 */
package nts.uk.ctx.at.schedule.infra.entity.event;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author hungnm
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KsmmtCompanyEventPK implements Serializable{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = "CID")
	public String companyId;

	@Basic(optional = false)
	@Column(name = "YMD_K")
	public BigDecimal date;

}
