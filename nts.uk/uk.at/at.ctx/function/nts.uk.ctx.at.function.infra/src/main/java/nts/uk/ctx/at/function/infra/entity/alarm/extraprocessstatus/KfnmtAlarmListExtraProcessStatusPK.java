package nts.uk.ctx.at.function.infra.entity.alarm.extraprocessstatus;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class KfnmtAlarmListExtraProcessStatusPK  implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "CID")	
	public String companyID;
	
	@Column(name = "START_DATE")	
	public GeneralDate startDate;
	
	@Column(name = "START_TIME")	
	public int startTime;
}
