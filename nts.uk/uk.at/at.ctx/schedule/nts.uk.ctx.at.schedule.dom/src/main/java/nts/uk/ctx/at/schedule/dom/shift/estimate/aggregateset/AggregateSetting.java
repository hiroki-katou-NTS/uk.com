package nts.uk.ctx.at.schedule.dom.shift.estimate.aggregateset;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class AggregateSetting.
 */
// 集計設定
@Getter
@Setter
public class AggregateSetting extends AggregateRoot{
	
	/** The company id. */
	// 会社ID
	private CompanyId companyId;
	
	/** The premium no. */
	// 人件費対象項目
	private List<ExtraTimeItemNo> premiumNo;
	
	/** The monthly working day setting. */
	// 月間勤務日数集計設定
	private MonthlyWorkingDaySetting monthlyWorkingDaySetting;
	
	/**
	 * Instantiates a new aggregate setting.
	 *
	 * @param mememto the mememto
	 */
	public AggregateSetting(AggregateSettingGetMemento mememto){
		this.companyId = mememto.getCompanyId();
		this.premiumNo = mememto.getPremiumNo();
		this.monthlyWorkingDaySetting = mememto.getMonthlyWorkingDaySetting();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(AggregateSettingSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setPremiumNo(this.premiumNo);
		memento.setMonthlyWorkingDaySetting(this.monthlyWorkingDaySetting);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((premiumNo == null) ? 0 : premiumNo.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AggregateSetting other = (AggregateSetting) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		
		if (premiumNo == null) {
			if (other.premiumNo != null)
				return false;
		} else if (!premiumNo.equals(other.premiumNo))
			return false;
		
		return true;
	}
}
