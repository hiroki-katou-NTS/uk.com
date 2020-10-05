package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.incentive;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.work.WorkGroup;

@Getter
/** 作業別インセンティブ単価の設定 */
public class IncentiveUnitPriceSetEachWork extends AggregateRoot {

	/** 作業グループ: 作業グループ */
	private WorkGroup work;
	
	/** 単価設定単位: 単価設定単位 */
	private List<IncentiveUnitPriceSetHis> useHis;
	
	private IncentiveUnitPriceSetEachWork(WorkGroup work) {
		super();
		this.work = work;
		this.useHis = new ArrayList<>();
	}

	public static IncentiveUnitPriceSetEachWork create(WorkGroup work) {
		
		return new IncentiveUnitPriceSetEachWork(work);
	}
	
	public void addHis(IncentiveUnitPriceSetHis useHis) {
	
		this.useHis.add(useHis);
	}
}
