package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.mastercheck;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.mastercheck.ErrorAlarmMessageMSTCHK;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.mastercheck.MasterCheckFixedExtractCondition;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KRCMT_MSTCHK_FIXEDEXTRCON")
@NoArgsConstructor
@Getter @Setter
public class KrcmtMasterCheckFixedExtractCondition extends UkJpaEntity {

	@EmbeddedId
	private KrcmtMasterCheckFixedExtractConditionPK pk;
	
	@Column(name = "CONTRACT_CD")
	private String contractCode;
	
	@Column(name = "MESSAGE_DIS")
	private String message;
	
	@Column(name = "USE_ATR")
	private int useAtr;
	
	public KrcmtMasterCheckFixedExtractCondition(KrcmtMasterCheckFixedExtractConditionPK pk, String contractCode,
			String message, int useAtr) {
		this.pk = pk;
		this.contractCode = contractCode;
		this.message = message;
		this.useAtr = useAtr;
	}
	
	@Override
	protected Object getKey() {
		return pk;
	}
	
	public static KrcmtMasterCheckFixedExtractCondition toEntity(MasterCheckFixedExtractCondition domain) {
		return new KrcmtMasterCheckFixedExtractCondition(
				new KrcmtMasterCheckFixedExtractConditionPK(domain.getErrorAlarmCheckId(), domain.getNo()),
				AppContexts.user().contractCode(),
				domain.getMessage().v(), domain.isUseAtr()?1:0
				);
	}
	
	public MasterCheckFixedExtractCondition toDomain() {
		return new MasterCheckFixedExtractCondition(
					this.pk.getErAlId(),
					this.pk.getNo(),
					new ErrorAlarmMessageMSTCHK(this.message),
					this.useAtr==1?true:false
				);
	}
}
