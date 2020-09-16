package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.mastercheck;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@Table(name = "KRCMT_MSTCHK_FIXEDEXTRIT")
@NoArgsConstructor
@Getter @Setter
public class KrcmtMasterCheckFixedExtractItem extends UkJpaEntity {

	@Id
	@Column(name = "NO")
	private int no;
	
	@Column(name = "CONTRACT_CD")
	private String contractCode;
	
	@Column(name = "FIRST_MESSAGE_DIS")
	private String initMessage;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "ERALARM_ATR")
	private int elAlAtr;

	@Override
	protected Object getKey() {
		return this.no;
	}
}
