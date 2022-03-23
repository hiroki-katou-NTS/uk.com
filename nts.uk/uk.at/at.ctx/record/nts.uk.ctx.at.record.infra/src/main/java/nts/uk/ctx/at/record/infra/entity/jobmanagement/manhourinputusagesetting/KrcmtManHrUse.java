package nts.uk.ctx.at.record.infra.entity.jobmanagement.manhourinputusagesetting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.jobmanagement.usagesetting.ManHrInputUsageSetting;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author tutt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_MAN_HR_USE")
public class KrcmtManHrUse extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 会社ID */
	@Id
	@Column(name = "CID")
	public String cId;
	
	/** 使用区分*/
	@Column(name = "USE_ATR")
	public int useAtr;
	
	/** 設備入力を利用する*/
	@Column(name = "EQUIPMENT_USE_ATR")
	public int equipmentUseAtr;

	@Override
	protected Object getKey() {
		return this.cId;
	}

	public KrcmtManHrUse(ManHrInputUsageSetting setting) {
		this.cId = setting.getCid();
		this.useAtr = setting.getUsrAtr().value;
		this.equipmentUseAtr = setting.getEquipmentUseAtr().value;
	}

	public ManHrInputUsageSetting toDomain() {
		return new ManHrInputUsageSetting(cId, NotUseAtr.valueOf(this.useAtr), NotUseAtr.valueOf(this.equipmentUseAtr));
	}

}
