package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionWorkRecordName;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.WorkRecordFixedCheckItem;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@NoArgsConstructor
@Entity
@Table(name = "KRCMT_FIX_CON_WORK_RECORD")
public class KrcmtFixedConditionWorkRecord extends UkJpaEntity implements Serializable {


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ERROR_ALARM_CHECK_ID")
	public String errorAlarmID;

	@Column(name = "FIX_CON_WORK_RECORD_NO")
	public int fixConWorkRecordNo;
	
	@Column(name = "MESSAGE")
	public String message;

	@Column(name = "USE_ATR")
	public int useAtr;
	
	@Override
	protected Object getKey() {
		return errorAlarmID;
	}
	
	public KrcmtFixedConditionWorkRecord(String errorAlarmID, int fixConWorkRecordNo, String message, int useAtr) {
		super();
		this.errorAlarmID = errorAlarmID;
		this.fixConWorkRecordNo = fixConWorkRecordNo;
		this.message = message;
		this.useAtr = useAtr;
	}
	
	public static KrcmtFixedConditionWorkRecord toEntity(FixedConditionWorkRecord domain) {
		return new KrcmtFixedConditionWorkRecord(
				domain.getErrorAlarmID(),
				domain.getFixConWorkRecordNo().value,
				domain.getMessage().v(),
				domain.isUseAtr()?1:0
				);
	}
	
	public FixedConditionWorkRecord toDomain() {
		return new FixedConditionWorkRecord(
				this.errorAlarmID,
				EnumAdaptor.valueOf(this.fixConWorkRecordNo, WorkRecordFixedCheckItem.class),
				new FixedConditionWorkRecordName(this.message),
				this.useAtr == 1?true:false
				);
	}

}
