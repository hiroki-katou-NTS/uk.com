package nts.uk.ctx.at.function.dom.holidaysremaining;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author thanh.tq 出力する代休の項目
 */
@Getter
@Setter
@NoArgsConstructor
public class ItemsOutputtedAlternate {
	/**
	 * 代休残数を出力する
	 */
	private boolean remainingChargeSubstitute;

	/**
	 * 代休未消化出力する
	 */
	private boolean representSubstitute;
	/**
	 * 代休の項目を出力する
	 */
	private boolean outputItemSubstitute;
	
	public ItemsOutputtedAlternate(boolean remainingChargeSubstitute, boolean representSubstitute,
			boolean outputItemSubstitute) {
		super();
		this.remainingChargeSubstitute = outputItemSubstitute && remainingChargeSubstitute;
		this.representSubstitute = outputItemSubstitute && representSubstitute;
		this.outputItemSubstitute = outputItemSubstitute;
	}
	
	

}
