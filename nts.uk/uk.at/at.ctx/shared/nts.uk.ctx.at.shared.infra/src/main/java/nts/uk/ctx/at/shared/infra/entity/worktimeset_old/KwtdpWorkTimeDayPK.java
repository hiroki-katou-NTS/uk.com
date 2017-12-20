package nts.uk.ctx.at.shared.infra.entity.worktimeset_old;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Doan Duy Hung
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KwtdpWorkTimeDayPK {
	
	@Column(name="CID")
	public String companyID;
	
	@Column(name="SIFT_CD")
	public String siftCD;
	
	@Column(name="TIME_NUMBER_CNT")
	public int timeNumberCnt;
}
