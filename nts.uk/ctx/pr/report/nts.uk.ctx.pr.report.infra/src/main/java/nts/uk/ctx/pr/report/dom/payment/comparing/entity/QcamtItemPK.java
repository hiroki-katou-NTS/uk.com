package nts.uk.ctx.pr.report.dom.payment.comparing.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QcamtItemPK implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "CCD")
	@NotNull
	public String companyCode;
	
	@Column(name = "CTG_ATR")
	@NotNull
	public int categoryAtr;
	
	@Column(name = "ITEM_CD")
	@NotNull
	public String itemCD;
	
}
