package nts.uk.ctx.pr.report.dom.payment.comparing.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class QlsdtPaycompConfirmPK implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Column(name = "CCD")
	public String companyCode;
	
	@Column(name = "PID")
	public String personId;
	
	@Column(name = "PROCESSING_YM_EARLY")
	public int processYMEarly;
	
	@Column(name = "PROCESSING_YM_LATER")
	public int processYMLater;

	@Column(name = "CTG_ATR")
	public int categoryAtr;
	
	@Column(name = "ITEM_CD")
	public String itemCD;

}
