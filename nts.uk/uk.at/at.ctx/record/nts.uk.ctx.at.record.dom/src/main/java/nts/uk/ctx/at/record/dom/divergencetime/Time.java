package nts.uk.ctx.at.record.dom.divergencetime;

import java.math.BigDecimal;

import nts.arc.primitive.DecimalPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.DecimalRange;
import nts.arc.primitive.constraint.TimeRange;
@TimeRange(max="99:59", min="00:00")
public class Time extends TimeDurationPrimitiveValue<PrimitiveValue<BigDecimal>> {
	 private static final long serialVersionUID = 1L;
	 public Time(int hour, int minute) {
	  super(hour, minute);
	 }
	 
	 public Time(BigDecimal time) {
	  super(Long.valueOf(time.toString()));
	 }
	}
