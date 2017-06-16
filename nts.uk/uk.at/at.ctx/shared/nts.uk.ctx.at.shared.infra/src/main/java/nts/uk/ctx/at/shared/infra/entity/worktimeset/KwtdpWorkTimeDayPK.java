package nts.uk.ctx.at.shared.infra.entity.worktimeset;

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
	
	@Column(name="TIME_DAY_ID")
	public String timeDayID;
	
	@Column(name="WORK_TIME_SET_ID")
	public String workTimeSetID;
	
	@Column(name="TIME_NUMBER_CNT")
	public int timeNumberCnt;
}
