package nts.uk.ctx.at.shared.infra.entity.dailyattdcal.dailyattendance.timesheet.ouen.incentive;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
public class KrcmtInsentivePriceWkpPK implements Serializable {

	/***/
	private static final long serialVersionUID = 1L;
	
	/** 会社ID */
	@Column(name = "CID")
	public String cid;
	
	/** インセンティブ単価ID */
	@Column(name = "INSENTIVE_PRICE_ID")
	public String insentiveId;
	
	/** 職場ID */
	@Column(name = "WORKPLACE_ID")
	public String workplaceId;
	
	
}
