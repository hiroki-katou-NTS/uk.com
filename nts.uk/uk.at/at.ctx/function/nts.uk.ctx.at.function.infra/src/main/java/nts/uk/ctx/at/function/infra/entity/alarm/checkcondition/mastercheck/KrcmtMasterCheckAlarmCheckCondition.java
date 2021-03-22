package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.mastercheck;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionCode;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.master.MasterCheckAlarmCheckCondition;
import nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.KfnmtAlarmCheckConditionCategory;
import nts.uk.ctx.at.shared.dom.alarmList.AlarmCategory;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
/**
 * マスタチェックのアラームチェック条件
 * @author do_dt
 *
 */
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_MSTCHK_LINKCON")
public class KrcmtMasterCheckAlarmCheckCondition extends ContractUkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	/**
	 * マスタチェックの固定抽出条件のID
	 */
	@Id
	@Column(name = "ALARM_CHK_ID")
	public String alarmCheckId;
	
	@Column(name = "CID")
	public String companyId;
	/**
	 * アラームチェック条件コード
	 */
	@Column(name = "AL_CHECK_COND_CATE_CD")
	public String code;

	@Column(name = "CATEGORY")
	public int category;
	
	@OneToOne
	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
			@JoinColumn(name = "CATEGORY", referencedColumnName = "CATEGORY", insertable = false, updatable = false),
			@JoinColumn(name = "AL_CHECK_COND_CATE_CD", referencedColumnName = "CD", insertable = false, updatable = false) })
	public KfnmtAlarmCheckConditionCategory condition;
	
	@Override
	protected Object getKey() {
		return alarmCheckId;
	}
	
	public KrcmtMasterCheckAlarmCheckCondition(String alarmCheckId, String companyId, String code, int category) {
		super();
		this.alarmCheckId = alarmCheckId;
		this.companyId = companyId;
		this.code = code;
		this.category = category;		
	}
	
	public static KrcmtMasterCheckAlarmCheckCondition toEntity(String companyId, AlarmCheckConditionCode code,
			AlarmCategory category, MasterCheckAlarmCheckCondition domain) {
		return new KrcmtMasterCheckAlarmCheckCondition(domain.getAlarmCheckId(), companyId, code.v(), category.value);
	}
	
	public MasterCheckAlarmCheckCondition toDomain() {
		return new MasterCheckAlarmCheckCondition(this.alarmCheckId);
	}

}
