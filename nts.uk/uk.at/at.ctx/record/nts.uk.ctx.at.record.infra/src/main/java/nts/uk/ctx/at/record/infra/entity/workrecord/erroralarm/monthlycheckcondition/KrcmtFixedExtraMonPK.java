package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.monthlycheckcondition;

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
public class KrcmtFixedExtraMonPK implements Serializable  {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "MON_ALARM_CHECK_ID")
	public String monAlarmCheckID;
	
	@Column(name = "FIX_EXTRA_ITEM_MON_NO")
	public int fixedExtraItemMonNo;

}
