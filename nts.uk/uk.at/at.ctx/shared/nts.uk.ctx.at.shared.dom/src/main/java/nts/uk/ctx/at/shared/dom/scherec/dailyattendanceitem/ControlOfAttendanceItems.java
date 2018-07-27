package nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.TimeInputUnit;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.primitivevalue.HeaderBackgroundColor;
/**
 * 日次の勤怠項目の制御
 * @author tutk
 *
 */
@Getter
public class ControlOfAttendanceItems extends AggregateRoot {
	
	/**会社ID*/
	private String companyID;
	
	/**勤怠項目ID*/
	private int itemDailyID;
	
	/**日別実績のヘッダ背景色*/
	private Optional<HeaderBackgroundColor> headerBgColorOfDailyPer;

	/**時間項目の入力単位*/
	private Optional<TimeInputUnit> inputUnitOfTimeItem;

	public ControlOfAttendanceItems(String companyID, int itemDailyID, HeaderBackgroundColor headerBgColorOfDailyPer, TimeInputUnit inputUnitOfTimeItem) {
		super();
		this.companyID = companyID;
		this.itemDailyID = itemDailyID;
		this.headerBgColorOfDailyPer = Optional.ofNullable(headerBgColorOfDailyPer);
		this.inputUnitOfTimeItem =  Optional.ofNullable(inputUnitOfTimeItem);
	}
	
	
	


}
