package nts.uk.ctx.at.record.dom.reservation.bentomenu.totalfee;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;

/**
 * 弁当合計金額
 * @author Doan Duy Hung
 *
 */
public class BentoAmountTotal {
	
	/**
	 * 金額１
	 */
	@Getter
	private final int totalAmount1;
	
	/**
	 * 金額２
	 */
	@Getter
	private final int totalAmount2;
	
	/**
	 * 明細リスト
	 */
	@Getter
	private final List<BentoDetailsAmountTotal> detailsAmountTotal;
	
	public BentoAmountTotal(int totalAmount1, int totalAmount2, List<BentoDetailsAmountTotal> detailsAmountTotal) {
		this.totalAmount1 = totalAmount1;
		this.totalAmount2 = totalAmount2;
		this.detailsAmountTotal = detailsAmountTotal;
	}
	
	/**
	 * 作る
	 * @param detailsAmountTotal
	 * @return
	 */
	public static BentoAmountTotal createNew(List<BentoDetailsAmountTotal> detailsAmountTotal) {
		int sum1 = detailsAmountTotal.stream().map(x -> x.getAmount1()).collect(Collectors.summingInt(Integer::intValue));
		int sum2 = detailsAmountTotal.stream().map(x -> x.getAmount2()).collect(Collectors.summingInt(Integer::intValue));
		return new BentoAmountTotal(sum1, sum2, detailsAmountTotal);
	}
	
	
}
