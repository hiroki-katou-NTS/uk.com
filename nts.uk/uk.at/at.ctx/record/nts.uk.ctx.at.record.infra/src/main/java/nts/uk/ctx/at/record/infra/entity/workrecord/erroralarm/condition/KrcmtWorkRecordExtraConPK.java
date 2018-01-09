package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.condition;

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
public class KrcmtWorkRecordExtraConPK  implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "ERROR_ALARM_CHECK_ID")
	public String errorAlarmCheckID;
	
	@Column(name = "CHECK_ITEM")
	public int checkItem;
}
