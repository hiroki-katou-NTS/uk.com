package nts.uk.ctx.at.shared.infra.entity.workrule.week;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.workrule.weekmanage.WeekRuleManagement;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * KSRMT_WEEK_RULE_MNG 週の管理
 */
@Entity
@Table(name = "KSRMT_WEEK_RULE_MNG")
@NoArgsConstructor
@AllArgsConstructor
public class KsrmtWeekRuleMng extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 会社ID */
	@Id
	@Column(name = "CID")
	public String cid;
	
	/** 週開始 */
	@Column(name = "START_OF_WEEK")
	public int startOfWeek;
	
	/** 週割増時間を締め開始日から計算する */
	@Column(name = "IS_CLOSING_START_DATE")
	public int closingStartDate;
	
	/**
	 * キー取得
	 */
	@Override
	protected Object getKey() {
		return this.cid;
	}
	
	public WeekRuleManagement toDomain(){

		return WeekRuleManagement.of(this.cid, this.startOfWeek, closingStartDate == 1);
	}
	
	public void from(WeekRuleManagement domain){
		
		this.startOfWeek = domain.getWeekStart().value;
		this.closingStartDate = domain.isCalcWeekPremFromClosureStart() ? 1 : 0;
	}
	
	public static KsrmtWeekRuleMng map(WeekRuleManagement domain){
		
		KsrmtWeekRuleMng entity = new KsrmtWeekRuleMng();
		
		entity.cid = domain.getCid();
		entity.from(domain);
		
		return entity;
	}
}
