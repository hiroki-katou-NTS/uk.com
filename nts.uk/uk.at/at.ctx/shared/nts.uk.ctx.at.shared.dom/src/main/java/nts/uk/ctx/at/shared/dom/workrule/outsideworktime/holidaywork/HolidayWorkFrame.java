package nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork;

import nts.arc.layer.dom.AggregateRoot;

/**
 * 休出枠
 * Holiday Work Frame
 * @author keisuke_hoshina
 *
 */
public class HolidayWorkFrame extends AggregateRoot{
	private HolidayWorkFrameNo number;
	private HolidayWorkFrameName holidayWorkFrameName;
	private HolidayWorkFrameName substitubeFrameName;
	private StaturoryAtrOfHolidayWork staturoryAtr;
	
}
