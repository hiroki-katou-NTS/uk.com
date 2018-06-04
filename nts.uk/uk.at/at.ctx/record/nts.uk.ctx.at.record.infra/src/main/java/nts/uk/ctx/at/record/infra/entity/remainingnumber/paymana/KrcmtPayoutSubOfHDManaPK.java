package nts.uk.ctx.at.record.infra.entity.remainingnumber.paymana;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class KrcmtPayoutSubOfHDManaPK implements Serializable{
	private static final long serialVersionUID = 1L;

	// 振出データID
	@Column(name = "PAYOUT_ID")
	public String payoutId;
	
	// 振休データID
	@Column(name = "SUBOFHD_ID")
	public String subOfHDID;
}
