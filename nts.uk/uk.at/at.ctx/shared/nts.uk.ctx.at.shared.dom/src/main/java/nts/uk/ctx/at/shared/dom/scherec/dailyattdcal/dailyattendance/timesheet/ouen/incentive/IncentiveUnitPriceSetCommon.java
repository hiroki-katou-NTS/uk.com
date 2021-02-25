package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.incentive;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

@Getter
public abstract class IncentiveUnitPriceSetCommon extends AggregateRoot {
	
	/** 会社ID: 会社ID */
	protected String companyId;
	
	/** インセンティブ単価: 作業別インセンティブ単価の設定 */
	protected List<IncentiveUnitPriceSetEachWork> unitPriceSet;
	
	protected IncentiveUnitPriceSetCommon(String companyId) {
		super();
		this.companyId = companyId;
		this.unitPriceSet = new ArrayList<>();
	}
	
	public void addUnitPriceSet(IncentiveUnitPriceSetEachWork useHis) {
	
		this.unitPriceSet.add(useHis);
	}
}
