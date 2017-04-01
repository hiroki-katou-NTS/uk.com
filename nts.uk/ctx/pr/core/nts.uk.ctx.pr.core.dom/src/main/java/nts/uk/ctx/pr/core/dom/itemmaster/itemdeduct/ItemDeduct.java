package nts.uk.ctx.pr.core.dom.itemmaster.itemdeduct;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.itemmaster.ItemCode;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.AlRangeHigh;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.AlRangeLow;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.ErrRangeHigh;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.ErrRangeLow;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.RangeAtr;
import nts.uk.shr.com.primitive.Memo;

@Getter
public class ItemDeduct extends AggregateRoot {

	private CompanyCode ccd;
	private ItemCode itemCd;
	private DeductAtr deductAtr;
	private RangeAtr errRangeLowAtr;
	private ErrRangeLow errRangeLow;
	private RangeAtr errRangeHighAtr;
	private ErrRangeHigh errRangeHigh;
	private RangeAtr alRangeLowAtr;
	private AlRangeLow alRangeLow;
	private RangeAtr alRangeHighAtr;
	private AlRangeHigh alRangeHigh;
	private Memo memo;

	public static ItemDeduct createFromJavaType(String ccd, String itemCd, int deductAtr, int errRangeLowAtr,
			BigDecimal errRangeLow, int errRangeHighAtr, BigDecimal errRangeHigh, int alRangeLowAtr,
			BigDecimal alRangeLow, int alRangeHighAtr, BigDecimal alRangeHigh, String memo

	) {
		return new ItemDeduct(new CompanyCode(ccd), new ItemCode(itemCd), EnumAdaptor.valueOf(deductAtr, DeductAtr.class),
				EnumAdaptor.valueOf(errRangeLowAtr, RangeAtr.class), new ErrRangeLow(errRangeLow),
				EnumAdaptor.valueOf(errRangeHighAtr, RangeAtr.class), new ErrRangeHigh(errRangeHigh),
				EnumAdaptor.valueOf(alRangeLowAtr, RangeAtr.class), new AlRangeLow(alRangeLow),
				EnumAdaptor.valueOf(alRangeHighAtr, RangeAtr.class), new AlRangeHigh(alRangeHigh), new Memo(memo));
	}

	public ItemDeduct(CompanyCode ccd, ItemCode itemCd, DeductAtr deductAtr, RangeAtr errRangeLowAtr,
			ErrRangeLow errRangeLow, RangeAtr errRangeHighAtr, ErrRangeHigh errRangeHigh, RangeAtr alRangeLowAtr,
			AlRangeLow alRangeLow, RangeAtr alRangeHighAtr, AlRangeHigh alRangeHigh, Memo memo) {
		super();
		this.ccd = ccd;
		this.itemCd = itemCd;
		this.deductAtr = deductAtr;
		this.errRangeLowAtr = errRangeLowAtr;
		this.errRangeLow = errRangeLow;
		this.errRangeHighAtr = errRangeHighAtr;
		this.errRangeHigh = errRangeHigh;
		this.alRangeLowAtr = alRangeLowAtr;
		this.alRangeLow = alRangeLow;
		this.alRangeHighAtr = alRangeHighAtr;
		this.alRangeHigh = alRangeHigh;
		this.memo = memo;
	}

}
