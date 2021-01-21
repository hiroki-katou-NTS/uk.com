package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.monthlycheckcondition;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
//import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.SingleValueCompareType;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.AgreementCheckCon36;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.ErrorAlarmRecord;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@NoArgsConstructor
@Entity      
@Table(name = "KRCMT_36_AGREEMENT_CHECK")
public class KrcmtAgreementCheckCon36 extends ContractUkJpaEntity implements Serializable  {

	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ERAL_CHECK_ID")
	public String errorAlarmCheckID;
	
	@Column(name = "CLASSIFICATION")
	public int classification;
	
	@Column(name = "COMPARE_OPERATOR")
	public int compareOperator;
	
	@Column(name = "ERAL_BEFORE_TIME")
	public BigDecimal eralBeforeTime;
	
	@Override
	protected Object getKey() {
		return errorAlarmCheckID;
	}
	
	public KrcmtAgreementCheckCon36(String errorAlarmCheckID, int classification, int compareOperator, BigDecimal eralBeforeTime) {
		super();
		this.errorAlarmCheckID = errorAlarmCheckID;
		this.classification = classification;
		this.compareOperator = compareOperator;
		this.eralBeforeTime = eralBeforeTime;
	}
	
	public static KrcmtAgreementCheckCon36 toEntity(AgreementCheckCon36 domain) {
		return new  KrcmtAgreementCheckCon36(
				domain.getErrorAlarmCheckID(),
				domain.getClassification().value,
				domain.getCompareOperator().value,
				domain.getEralBeforeTime()
				);
	}
	
	public AgreementCheckCon36 toDomain() {
		return new AgreementCheckCon36(
				this.errorAlarmCheckID,
				EnumAdaptor.valueOf(this.classification, ErrorAlarmRecord.class),
				EnumAdaptor.valueOf(this.compareOperator, SingleValueCompareType.class),
				this.eralBeforeTime
				);
	}



	




	

}
