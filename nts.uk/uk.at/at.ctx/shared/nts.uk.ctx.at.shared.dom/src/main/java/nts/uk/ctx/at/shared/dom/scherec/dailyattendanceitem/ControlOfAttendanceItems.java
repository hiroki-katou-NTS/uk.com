package nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.TimeInputUnit;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.primitivevalue.HeaderBackgroundColor;

@Getter
public class ControlOfAttendanceItems extends AggregateRoot {

	private BigDecimal attandanceTimeId;

	private TimeInputUnit inputUnitOfTimeItem;

	private int nameLineFeedPosition;

	private HeaderBackgroundColor headerBackgroundColorOfDailyPerformance;

	public ControlOfAttendanceItems(BigDecimal attandanceTimeId, TimeInputUnit inputUnitOfTimeItem,
			HeaderBackgroundColor headerBackgroundColorOfDailyPerformance, int nameLineFeedPosition) {
		super();
		this.attandanceTimeId = attandanceTimeId;
		this.inputUnitOfTimeItem = inputUnitOfTimeItem;
		this.headerBackgroundColorOfDailyPerformance = headerBackgroundColorOfDailyPerformance;
		this.nameLineFeedPosition = nameLineFeedPosition;
	}

	public static ControlOfAttendanceItems createFromJavaType(BigDecimal attandanceTimeId, int inputUnitOfTimeItem,
			String headerBackgroundColorOfDailyPerformance, int nameLineFeedPosition) {

		return new ControlOfAttendanceItems(attandanceTimeId,
				inputUnitOfTimeItem == -1 ? null : EnumAdaptor.valueOf(inputUnitOfTimeItem, TimeInputUnit.class),
				new HeaderBackgroundColor(headerBackgroundColorOfDailyPerformance), nameLineFeedPosition);
	}

}
