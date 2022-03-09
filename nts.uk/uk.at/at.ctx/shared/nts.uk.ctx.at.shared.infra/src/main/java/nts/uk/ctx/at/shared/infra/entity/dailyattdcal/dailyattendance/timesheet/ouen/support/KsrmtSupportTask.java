package nts.uk.ctx.at.shared.infra.entity.dailyattdcal.dailyattendance.timesheet.ouen.support;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.support.SupportWorkSetting;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
/**
 * 応援・作業設定 entity
 * @author NWS
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSRMT_SUPPORT_TASK")
public class KsrmtSupportTask extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	// 会社ID
	@Id
	@Column(name = "CID")
	public String cid;

	// 移動時間の計上先  
	@Column(name = "MOVE_TIME_ATR")
	public int moveTimeAtr;

	@Override
	protected Object getKey() {
		return cid;
	}
	
	public static KsrmtSupportTask convert(SupportWorkSetting domain) {
		KsrmtSupportTask entity = new KsrmtSupportTask();
		entity.cid = domain.getCompanyId().toString();
		entity.moveTimeAtr = domain.getAccountingOfMoveTime().value;		
		return entity;
	}
	
}
