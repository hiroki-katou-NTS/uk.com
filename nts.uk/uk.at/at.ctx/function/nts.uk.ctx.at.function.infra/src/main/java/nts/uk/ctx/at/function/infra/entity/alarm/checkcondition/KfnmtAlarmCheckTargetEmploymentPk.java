package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * @author HungTT
 *
 */

@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class KfnmtAlarmCheckTargetEmploymentPk implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Basic
	@Column(name = "AL_CHK_TARGET_ID")
	public String targetConditionId;
	
	@Basic
	@Column(name = "EMP_CD")
	public String employmentCode;

}
