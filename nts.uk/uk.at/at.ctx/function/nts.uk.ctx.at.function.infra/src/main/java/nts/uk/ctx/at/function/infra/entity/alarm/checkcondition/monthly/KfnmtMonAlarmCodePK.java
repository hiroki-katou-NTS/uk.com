package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.monthly;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class KfnmtMonAlarmCodePK implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "MON_ALARM_CHECK_CON_ID")
	public String monAlarmCheckConID;
	
	@Column(name = "ERAL_CHECK_ID")
	public String errorAlarmCheckID;

}
