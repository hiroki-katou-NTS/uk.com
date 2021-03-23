package nts.uk.ctx.at.record.infra.entity.temporarywork;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author nampt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_TEM_WORK_MANAGE")
public class KrcdtTemporaryWorkManage extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtTemporaryWorkManagePK krcdtTemporaryWorkManagePK;

	// 最大使用回数
	@Column(name = "MAX_USE_COUNT")
	public int maxUseCount;

	// 臨時打刻を同一と扱う時間
	@Column(name = "TEM_STAMP_TIME")
	public Integer temporatyStampTime;

	@Override
	protected Object getKey() {
		return this.krcdtTemporaryWorkManagePK;
	}

//	public TemporaryWorkManage toDomain() {
//		TemporaryWorkManage temporaryWorkManage = new TemporaryWorkManage(this.krcdtTemporaryWorkManagePK.companyId,
//				this.maxUseCount, this.temporatyStampTime);
//		return temporaryWorkManage;
//	}
}
