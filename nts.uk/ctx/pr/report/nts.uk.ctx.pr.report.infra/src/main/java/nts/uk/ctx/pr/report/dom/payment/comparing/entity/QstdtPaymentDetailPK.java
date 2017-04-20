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
public class QstdtPaymentDetailPK implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "CCD")
	@NotNull
	public String companyCode;
	
	@Column(name = "PID")
	@NotNull
	public String personId;
	
	@Column(name = "PROCESSING_NO")
	@NotNull
	public int processNo;
	
	@Column(name = "PAY_BONUS_ATR")
	@NotNull
	public int payBonusAtr;
	
	@Column(name = "PROCESSING_YM")
	@NotNull
	public int processYM;
	
	@Column(name = "SPARE_PAY_ATR")
	@NotNull
	public int sparePayAtr;
	
	@Column(name = "CTG_ATR")
	@NotNull
	public int categoryAtr;
	
	@Column(name = "ITEM_CD")
	@NotNull
	public String itemCD;
	
}
