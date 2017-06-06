package nts.uk.ctx.at.shared.infra.entity.worktime;

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
	
	@Column(name="TIME_DAY_CD")
	public String timeDayCD;
	
	@Column(name="WORK_TIME_SET_CD")
	public String workTimeSetCD;
	
	@Column(name="TIME_NUMBER_CNT")
	public int timeNumberCnt;
}
