package nts.uk.ctx.at.function.infra.entity.employmentinfoterminal.infoterminal;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.ContractCode;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalComStatus;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KFNDT_TIMERECORDER_SIGNAL_ST")
public class KfndtTimeRecorderSignalST extends UkJpaEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KfndtTimeRecorderSignalSTPK pk;
	
	/**
	 * 最終通信日時
	 */
	@Column(name = "SIGNAL_LAST_TIME")
	public GeneralDateTime signalLastTime;
	
	public static KfndtTimeRecorderSignalST toEntity(EmpInfoTerminalComStatus domain) {
		return new KfndtTimeRecorderSignalST(
				new KfndtTimeRecorderSignalSTPK(domain.getContractCode().v(), domain.getEmpInfoTerCode().v()),
				domain.getSignalLastTime()
				);
	}

	public EmpInfoTerminalComStatus toDomain() {
		return new EmpInfoTerminalComStatus(
				new ContractCode(this.pk.contractCode),
				new EmpInfoTerminalCode(this.pk.timeRecordCode),
				this.signalLastTime
				);
	}
	
	@Override
	protected Object getKey() {
		return pk;
	}
}
