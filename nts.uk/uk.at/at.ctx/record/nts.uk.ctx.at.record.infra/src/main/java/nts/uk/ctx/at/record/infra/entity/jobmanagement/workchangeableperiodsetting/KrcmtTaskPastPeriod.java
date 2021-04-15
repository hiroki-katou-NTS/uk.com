package nts.uk.ctx.at.record.infra.entity.jobmanagement.workchangeableperiodsetting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.jobmanagement.workchangeableperiodsetting.MonthsAgo;
import nts.uk.ctx.at.record.dom.jobmanagement.workchangeableperiodsetting.WorkChangeablePeriodSetting;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * @author thanhpv
 * @name 作業変更可能期間設定 WorkChangeablePeriodSetting
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_TASK_PAST_PERIOD")
public class KrcmtTaskPastPeriod extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CID")
	public String cid;

	@Column(name = "MONTHS_AGO")
	public int monthsAgo;

	@Override
	protected Object getKey() {
		return this.cid;
	}
	
	public KrcmtTaskPastPeriod(WorkChangeablePeriodSetting domain) {
		this.cid = AppContexts.user().companyId();
		this.monthsAgo = domain.getMonthsAgo().value;
	}

	public WorkChangeablePeriodSetting toDomain() {
		return new WorkChangeablePeriodSetting(EnumAdaptor.valueOf(this.monthsAgo, MonthsAgo.class));
	}
}
