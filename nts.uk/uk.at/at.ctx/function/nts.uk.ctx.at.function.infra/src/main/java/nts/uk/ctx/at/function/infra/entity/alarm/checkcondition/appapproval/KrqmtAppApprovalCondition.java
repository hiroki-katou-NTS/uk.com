package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.appapproval;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionCode;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.appapproval.AppApprovalAlarmCheckCondition;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.KfnmtAlarmCheckConditionCategory;
import nts.uk.ctx.at.shared.dom.alarmList.AlarmCategory;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@Table(name = "KRQMT_APPAPV_LINKCON")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class KrqmtAppApprovalCondition  extends ContractUkJpaEntity  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ERRALARM_APLIAPRO_ID")
	private String id;

	@Column(name = "CID")
	private String companyId;

	@Column(name = "CATEGORY")
	private int category;

	@Column(name = "AL_CHECK_COND_CATE_CD")
	private String checkConditionCode;

	@OneToOne
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
			@JoinColumn(name = "CATEGORY", referencedColumnName = "CATEGORY", insertable = false, updatable = false),
			@JoinColumn(name = "AL_CHECK_COND_CATE_CD", referencedColumnName = "CD", insertable = false, updatable = false) })
	public KfnmtAlarmCheckConditionCategory condition;

	@Override
	protected Object getKey() {
		return this.id;
	}
	
	public KrqmtAppApprovalCondition(String id, String companyId, int category, String checkConditionCode) {
		super();
		this.id = id;
		this.companyId = companyId;
		this.category = category;
		this.checkConditionCode = checkConditionCode;
	}
	
	public static KrqmtAppApprovalCondition toEntity(String companyId, AlarmCheckConditionCode code,
			AlarmCategory category, AppApprovalAlarmCheckCondition domain) {
		return new KrqmtAppApprovalCondition(domain.getApprovalAlarmConID(), companyId, category.value, code.v());
	}
	
	public AppApprovalAlarmCheckCondition toDomain() {
		return new AppApprovalAlarmCheckCondition(this.id);
	}
}
