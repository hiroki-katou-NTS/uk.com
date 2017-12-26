package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class KfnmtCheckConItemPK implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Column(name = "CID")
	public String companyID;
	
	@Column(name = "ALARM_PATTERN_CD")
	public String alarmPatternCD;
	
	@Column(name = "ALARM_CATEGORY")
	public int alarmCategory;
	
	@Column(name = "ALARM_CHECK_CONDITION_CODE")
	public String checkConditionCD;
}
