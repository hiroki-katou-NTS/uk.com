package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class KrcmtFixedConditionWorkRecordPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "ERROR_ALARM_CHECK_ID")
	public String errorAlarmID;

	@Column(name = "FIX_CON_WORK_RECORD_NO")
	public int fixConWorkRecordNo;

}
