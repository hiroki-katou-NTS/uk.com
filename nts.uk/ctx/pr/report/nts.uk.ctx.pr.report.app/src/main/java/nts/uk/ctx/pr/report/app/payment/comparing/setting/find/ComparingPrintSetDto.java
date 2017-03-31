package nts.uk.ctx.pr.report.app.payment.comparing.setting.find;

import lombok.Value;
import nts.uk.ctx.pr.report.dom.payment.comparing.setting.ComparingPrintSet;

@Value
public class ComparingPrintSetDto {

	private String plushBackColor;
	private String minusBackColor;
	private int showItemIfCfWithNull;
	private int showItemIfSameValue;
	private int showPayment;
	private int totalSet;
	private int sumEachDeprtSet;
	private int sumDepHrchyIndexSet;
	private int hrchyIndex1;
	private int hrchyIndex2;
	private int hrchyIndex3;
	private int hrchyIndex4;
	private int hrchyIndex5;

	public static ComparingPrintSetDto fromDomain(ComparingPrintSet domain) {
		return new ComparingPrintSetDto(domain.getPlushBackColor().v(), domain.getMinusBackColor().v(),
				domain.getShowItemIfCfWithNull().value, domain.getShowItemIfSameValue().value,
				domain.getShowPayment().value, domain.getTotalSet().value, domain.getSumEachDeprtSet().value,
				domain.getSumDepHrchyIndexSet().value, domain.getHrchyIndex1().value, domain.getHrchyIndex2().value,
				domain.getHrchyIndex3().value, domain.getHrchyIndex4().value, domain.getHrchyIndex5().value);
	}
}
