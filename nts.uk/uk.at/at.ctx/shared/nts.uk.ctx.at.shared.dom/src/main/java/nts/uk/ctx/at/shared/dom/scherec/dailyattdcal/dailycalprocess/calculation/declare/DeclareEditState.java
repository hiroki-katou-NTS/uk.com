package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.declare;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.HolidayWorkFrameNo;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.StaturoryAtrOfHolidayWork;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;

/**
 * 申告編集状態
 * @author shuichi_ishida
 */
@Getter
public class DeclareEditState implements Cloneable {

	/** 残業 */
	private List<OverTimeFrameNo> overtime;
	/** 残業深夜 */
	@Setter
	private boolean overtimeMn;
	/** 休出 */
	private List<HolidayWorkFrameNo> holidayWork;
	/** 休出深夜 */
	private List<StaturoryAtrOfHolidayWork> holidayWorkMn;

	/**
	 * コンストラクタ
	 */
	public DeclareEditState(){
		this.overtime = new ArrayList<>();
		this.overtimeMn = false;
		this.holidayWork = new ArrayList<>();
		this.holidayWorkMn = new ArrayList<>();
	}
}
