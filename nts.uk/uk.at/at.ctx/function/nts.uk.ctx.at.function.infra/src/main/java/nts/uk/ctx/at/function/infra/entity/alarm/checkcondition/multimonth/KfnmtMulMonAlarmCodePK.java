package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.multimonth;

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
public class KfnmtMulMonAlarmCodePK implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "MUL_MON_ALARM_CON_ID")
	public String mulMonAlarmCondID;
	
	@Column(name = "ERAL_CHECK_ID")
	public String errorAlarmCheckID;

}
