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
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.EmpInfoTerComAbPeriod;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KFNDT_TR_SIGNAL_ABNORMAL")
public class KfndtTrSignalAbNormal extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KfndtTrSignalAbNormalPK pk;
	
	/**
	 * 最新通信成功日時
	 */
	@Column(name = "LATEST_TIME_SUCCESS_DATE")
	public GeneralDateTime lastestTimeSuccDate;
	
	@Override
	protected Object getKey() {
		return pk;
	}
	
	public static KfndtTrSignalAbNormal toEntity(EmpInfoTerComAbPeriod domain) {
		return new KfndtTrSignalAbNormal(
				new KfndtTrSignalAbNormalPK(domain.getContractCode().v(),Integer.parseInt(domain.getEmpInfoTerCode().v()), domain.getLastComSuccess()),
				domain.getLastestComSuccess());
	}
	
	public EmpInfoTerComAbPeriod toDomain() {
		return new EmpInfoTerComAbPeriod(
				new ContractCode(this.pk.contractCode),
				new EmpInfoTerminalCode(String.valueOf(this.pk.timeRecordCode)),
				this.pk.preTimeSuccDate,
				this.lastestTimeSuccDate);
	}
}
