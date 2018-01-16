package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.daily;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrcmtDailyErrorCodePK implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(name = "DAILY_ALARM_CON_ID")
	public String dailyAlarmConID;
	
	@Column(name = "ERROR_ALARM_CHECK_ID")
	public String errorAlarmID;
}
