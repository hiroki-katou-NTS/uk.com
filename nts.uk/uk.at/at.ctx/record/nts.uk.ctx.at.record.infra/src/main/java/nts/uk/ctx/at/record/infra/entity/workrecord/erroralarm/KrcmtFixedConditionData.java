package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixConWorkRecordName;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionData;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionWorkRecordName;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.WorkRecordFixedCheckItem;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
@NoArgsConstructor
@Entity
@Table(name = "KRCCT_ALST_FXITM_DAY")
public class KrcmtFixedConditionData extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "FIX_CON_WORK_RECORD_NO")
	public int fixConWorkRecordNo;
	
	@Column(name = "FIX_CON_WORK_RECORD_NAME")
	public String fixConWorkRecordName;
	
	@Column(name = "INITIAL_MESSAGE")
	public String message;
	
	@Override
	protected Object getKey() {
		return fixConWorkRecordNo;
	}

	public KrcmtFixedConditionData(int fixConWorkRecordNo, String fixConWorkRecordName, String message) {
		super();
		this.fixConWorkRecordNo = fixConWorkRecordNo;
		this.fixConWorkRecordName = fixConWorkRecordName;
		this.message = message;
	}
	
	public static KrcmtFixedConditionData toEntity(FixedConditionData domain) {
		return new KrcmtFixedConditionData(
				domain.getFixConWorkRecordNo().value,
				domain.getFixConWorkRecordName().v(),
				domain.getMessage().v()
				);
		
	}
	
	public FixedConditionData toDomain() {
		return new FixedConditionData(
				EnumAdaptor.valueOf(this.fixConWorkRecordNo, WorkRecordFixedCheckItem.class),
				new FixConWorkRecordName(this.fixConWorkRecordName),
				new FixedConditionWorkRecordName(this.message)
				);
		
	}

}
