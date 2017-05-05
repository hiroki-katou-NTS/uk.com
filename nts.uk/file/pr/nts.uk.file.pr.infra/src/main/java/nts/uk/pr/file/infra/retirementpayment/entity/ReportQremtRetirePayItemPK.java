/**
 * 
 */
package nts.uk.pr.file.infra.retirementpayment.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

/**
 * @author hungnm
 *
 */
@Data
@Embeddable
public class ReportQremtRetirePayItemPK implements Serializable {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The company code. */
	@Basic(optional = false)
	@Column(name = "CCD")
	private String ccd;

	/** The person id. */
	@Basic(optional = false)
	@Column(name = "CTG_ATR")
	private BigDecimal categoryAtr;

	@Basic(optional = false)
	@Column(name = "ITEM_CD")
	private String itemCode;
}
