package nts.uk.ctx.at.record.infra.entity.worktime;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrcdtDayTsAtdStmpPK implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(name = "SID")
	public String employeeId;
	
	@Column(name = "WORK_NO")
	public int workNo;

	@Column(name = "YMD")
	public GeneralDate ymd;
	
	/**
	 * 0. 日別実績の出退勤 - timeLeaving
	 * 1. 日別実績の臨時出退勤 - temporaryTime
	 */
	@Column(name = "TIME_LEAVING_TYPE")
	public int timeLeavingType;
}
