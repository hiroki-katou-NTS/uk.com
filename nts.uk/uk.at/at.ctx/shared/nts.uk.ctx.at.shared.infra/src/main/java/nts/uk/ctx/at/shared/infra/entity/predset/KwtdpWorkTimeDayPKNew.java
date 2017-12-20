package nts.uk.ctx.at.shared.infra.entity.predset;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Doan Duy Hung
 *
 */

@Getter
@Setter
@Embeddable
public class KwtdpWorkTimeDayPKNew {
	
	@Column(name="CID")
	public String companyId;
	
	@Column(name="SIFT_CD")
	public String siftCD;
	
	@Column(name="TIME_NUMBER_CNT")
	public int timeNumberCnt;
}
