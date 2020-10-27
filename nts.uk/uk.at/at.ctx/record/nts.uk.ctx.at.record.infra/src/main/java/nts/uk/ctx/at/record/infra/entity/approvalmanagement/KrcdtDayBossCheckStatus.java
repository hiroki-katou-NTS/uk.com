package nts.uk.ctx.at.record.infra.entity.approvalmanagement;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.approvalmanagement.ApprovalStatusOfDailyPerfor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author nampt 日別実績の承認状況
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_DAY_BOSS_CHECK")
public class KrcdtDayBossCheckStatus extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtDayBossCheckStatusPK krcdtDayBossCheckPK;

	@Column(name = "APPROVAL_ROUTE_ID")
	public String approvalRouteID;

	@Override
	protected Object getKey() {
		return this.krcdtDayBossCheckPK;
	}

	public static KrcdtDayBossCheckStatus toEntity(ApprovalStatusOfDailyPerfor approvalStatusOfDailyPerfor) {
		return new KrcdtDayBossCheckStatus(new KrcdtDayBossCheckStatusPK(approvalStatusOfDailyPerfor.getEmployeeId(),
				approvalStatusOfDailyPerfor.getYmd()), approvalStatusOfDailyPerfor.getRootInstanceID());
	}
	
	public ApprovalStatusOfDailyPerfor toDomain() {
		return new ApprovalStatusOfDailyPerfor(krcdtDayBossCheckPK.employeeId, krcdtDayBossCheckPK.ymd, approvalRouteID);
	}
}
