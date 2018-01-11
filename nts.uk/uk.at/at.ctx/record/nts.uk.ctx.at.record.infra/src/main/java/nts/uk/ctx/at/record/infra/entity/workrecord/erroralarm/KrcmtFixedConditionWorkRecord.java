package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcmtFixedConditionWorkRecordPK krcmtFixedConditionWorkRecordPK;
	
	@Column(name = "MESSAGE")
	public String message;

	@Column(name = "USE_ATR")
	public int useAtr;
	
	
	public KrcmtFixedConditionWorkRecord(KrcmtFixedConditionWorkRecordPK krcmtFixedConditionWorkRecordPK,
			String message, int useAtr) {
		super();
		this.krcmtFixedConditionWorkRecordPK = krcmtFixedConditionWorkRecordPK;
		this.message = message;
		this.useAtr = useAtr;
	}
	
	@Override
	protected Object getKey() {
		return krcmtFixedConditionWorkRecordPK;
	}
	
	public static KrcmtFixedConditionWorkRecord toEntity(FixedConditionWorkRecord domain) {
		return new KrcmtFixedConditionWorkRecord(
				new KrcmtFixedConditionWorkRecordPK( domain.getErrorAlarmID(),
				domain.getFixConWorkRecordNo().value),
				domain.getMessage().v(),
				domain.isUseAtr()?1:0
				);
	}
	
	public FixedConditionWorkRecord toDomain() {
		return new FixedConditionWorkRecord(
				this.krcmtFixedConditionWorkRecordPK.errorAlarmID,
				EnumAdaptor.valueOf(this.krcmtFixedConditionWorkRecordPK.fixConWorkRecordNo, WorkRecordFixedCheckItem.class),
				new FixedConditionWorkRecordName(this.message),
				this.useAtr == 1?true:false
				);
	}
	
	
	



}
