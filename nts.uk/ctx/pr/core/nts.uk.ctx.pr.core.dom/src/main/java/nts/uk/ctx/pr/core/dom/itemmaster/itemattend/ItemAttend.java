package nts.uk.ctx.pr.core.dom.itemmaster.itemattend;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.pr.core.dom.itemmaster.Memo;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.AlRangeHigh;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.AlRangeLow;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.ErrRangeHigh;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.ErrRangeLow;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.RangeAtr;

@Getter
public class ItemAttend extends AggregateRoot {

	public AvePayAtr avePayAtr;
	public ItemAtr itemAtr;
	public RangeAtr errRangeLowAtr;
	public ErrRangeLow errRangeLow;
	public RangeAtr errRangeHighAtr;
	public ErrRangeHigh errRangeHigh;
	public RangeAtr alRangeLowAtr;
	public AlRangeLow alRangeLow;
	public RangeAtr alRangeHighAtr;
	public AlRangeHigh alRangeHigh;
	public WorkDaysScopeAtr workDaysScopeAtr;
	public Memo memo;

	public ItemAttend(AvePayAtr avePayAtr, ItemAtr itemAtr, RangeAtr errRangeLowAtr, ErrRangeLow errRangeLow,
			RangeAtr errRangeHighAtr, ErrRangeHigh errRangeHigh, RangeAtr alRangeLowAtr, AlRangeLow alRangeLow,
			RangeAtr alRangeHighAtr, AlRangeHigh alRangeHigh, WorkDaysScopeAtr workDaysScopeAtr, Memo memo) {
		super();
		this.avePayAtr = avePayAtr;
		this.itemAtr = itemAtr;
		this.errRangeLowAtr = errRangeLowAtr;
		this.errRangeLow = errRangeLow;
		this.errRangeHighAtr = errRangeHighAtr;
		this.errRangeHigh = errRangeHigh;
		this.alRangeLowAtr = alRangeLowAtr;
		this.alRangeLow = alRangeLow;
		this.alRangeHighAtr = alRangeHighAtr;
		this.alRangeHigh = alRangeHigh;
		this.workDaysScopeAtr = workDaysScopeAtr;
		this.memo = memo;
	}

	public static ItemAttend createFromJavaType(int avePayAtr, int itemAtr, int errRangeLowAtr, BigDecimal errRangeLow,
			int errRangeHighAtr, BigDecimal errRangeHigh, int alRangeLowAtr, BigDecimal alRangeLow, int alRangeHighAtr,
			BigDecimal alRangeHigh, int workDaysScopeAtr, String memo) {
		new ItemAttend(EnumAdaptor.valueOf(avePayAtr, AvePayAtr.class), EnumAdaptor.valueOf(itemAtr, ItemAtr.class),
				EnumAdaptor.valueOf(errRangeLowAtr, RangeAtr.class), new ErrRangeLow(errRangeLow),
				EnumAdaptor.valueOf(errRangeHighAtr, RangeAtr.class), new ErrRangeHigh(errRangeHigh),
				EnumAdaptor.valueOf(alRangeLowAtr, RangeAtr.class), new AlRangeLow(alRangeLow),
				EnumAdaptor.valueOf(alRangeHighAtr, RangeAtr.class), new AlRangeHigh(alRangeHigh),
				EnumAdaptor.valueOf(workDaysScopeAtr, WorkDaysScopeAtr.class), new Memo(memo));
		return null;
	}

}
