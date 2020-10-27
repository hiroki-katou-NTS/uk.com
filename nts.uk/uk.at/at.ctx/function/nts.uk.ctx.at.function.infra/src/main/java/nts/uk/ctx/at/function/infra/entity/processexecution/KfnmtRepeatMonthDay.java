package nts.uk.ctx.at.function.infra.entity.processexecution;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail.RepeatMonthDaysSelect;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
@Entity
@Table(name="KFNMT_REP_MONTH_DAY")
@AllArgsConstructor
@NoArgsConstructor
public class KfnmtRepeatMonthDay extends ContractUkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/* 主キー */
	@EmbeddedId
    public KfnmtRepeatMonthDayPK kfnmtRepMonthDayPK;
	
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="CID", referencedColumnName="CID", insertable = false, updatable = false),
		@JoinColumn(name="EXEC_ITEM_CD", referencedColumnName="EXEC_ITEM_CD", insertable = false, updatable = false)
	})
	public KfnmtExecutionTaskSetting execTaskSetting;
	
	@Override
	protected Object getKey() {
		return this.kfnmtRepMonthDayPK;
	}
	
	public KfnmtRepeatMonthDay(KfnmtRepeatMonthDayPK kfnmtRepMonthDayPK) {
		super();
		this.kfnmtRepMonthDayPK = kfnmtRepMonthDayPK;
	}
	
	public static KfnmtRepeatMonthDay toEntity(String companyId, String execItemCd, RepeatMonthDaysSelect monthDay) {
		return new KfnmtRepeatMonthDay(new KfnmtRepeatMonthDayPK(companyId, execItemCd, monthDay.value));
	}

	
}
