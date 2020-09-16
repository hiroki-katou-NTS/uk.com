package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.mastercheck;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
}
