package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.agree36;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Kfnmt36AgreeNamePK implements Serializable{
	private static final long serialVersionUID = 1L;
	/** 期間 **/
	@Column(name = "PERIOD_ATR")
	public int period;
	
	/** エラーアラーム **/
	@Column(name = "ERROR_ALARM")
	public int errorAlarm;
}
