package nts.uk.ctx.pr.report.dom.payment.comparing.setting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComparingPrintSet extends AggregateRoot {

	private String companyCode;
	private PlushBackColor plushBackColor;
	private MinusBackColor minusBackColor;
	private ShowItemIfCfWithNull showItemIfCfWithNull;
	private ShowItemIfSameValue showItemIfSameValue;
	private ShowPayment showPayment;
	private TotalSet totalSet;
	private SumEachDeprtSet sumEachDeprtSet;
	private SumDepHrchyIndexSet sumDepHrchyIndexSet;
	private HrchyIndex1 hrchyIndex1;
	private HrchyIndex2 hrchyIndex2;
	private HrchyIndex3 hrchyIndex3;
	private HrchyIndex4 hrchyIndex4;
	private HrchyIndex5 hrchyIndex5;

	public static ComparingPrintSet createFromJavaType(String companyCode, String plushBackColor, String minusBackColor,
			int showItemIfCfWithNull, int showItemIfSameValue, int showPayment, int totalSet, int sumEachDeprtSet,
			int sumDepHrchyIndexSet, int hrchyIndex1, int hrchyIndex2, int hrchyIndex3, int hrchyIndex4,
			int hrchyIndex5) {
		return new ComparingPrintSet(companyCode, new PlushBackColor(plushBackColor), new MinusBackColor(minusBackColor),
				EnumAdaptor.valueOf(showItemIfCfWithNull, ShowItemIfCfWithNull.class),
				EnumAdaptor.valueOf(showItemIfSameValue, ShowItemIfSameValue.class),
				EnumAdaptor.valueOf(showPayment, ShowPayment.class), 
				EnumAdaptor.valueOf(totalSet, TotalSet.class),
				EnumAdaptor.valueOf(sumEachDeprtSet, SumEachDeprtSet.class),
				EnumAdaptor.valueOf(sumDepHrchyIndexSet, SumDepHrchyIndexSet.class),
				EnumAdaptor.valueOf(hrchyIndex1, HrchyIndex1.class),
				EnumAdaptor.valueOf(hrchyIndex2, HrchyIndex2.class),
				EnumAdaptor.valueOf(hrchyIndex3, HrchyIndex3.class),
				EnumAdaptor.valueOf(hrchyIndex4, HrchyIndex4.class),
				EnumAdaptor.valueOf(hrchyIndex5, HrchyIndex5.class));
	}
}
