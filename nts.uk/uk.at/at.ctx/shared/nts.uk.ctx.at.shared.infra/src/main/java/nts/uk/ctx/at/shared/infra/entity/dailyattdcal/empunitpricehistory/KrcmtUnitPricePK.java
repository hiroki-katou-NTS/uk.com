package nts.uk.ctx.at.shared.infra.entity.dailyattdcal.empunitpricehistory;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/**
 * @author laitv
 */
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KrcmtUnitPricePK  implements Serializable{
	private static final long serialVersionUID = 1L;
	/**社員ID									*/
	@Column(name = "SID")
	public String sid;
	/**履歴ID*/
	@Column(name = "HIST_ID")
	public String histId;
}
