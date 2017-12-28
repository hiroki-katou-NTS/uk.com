package nts.uk.ctx.at.shared.dom.calculation.holiday.flex;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 
 * @author phongtq
 *
 */
@AllArgsConstructor
@Getter
public class FlexSet extends AggregateRoot {

	/** 会社ID */
	private String companyId;

	/** 不足計算 */
	public FlexCalcAtr missCalcHd;

	/** 割増計算 */
	public FlexCalcAtr premiumCalcHd;

	/** 不足計算 */
	public FlexCalcAtr missCalcSubhd;

	/** 割増計算 */
	public FlexCalcAtr premiumCalcSubhd;

	/**
	 * Create from Java Type of Flex Set
	 * 
	 * @param companyId
	 * @param missCalcHd
	 * @param premiumCalcHd
	 * @param missCalcSubhd
	 * @param premiumCalcSubhd
	 * @return
	 */
	public static FlexSet createFromJavaType(String companyId, int missCalcHd, int premiumCalcHd, int missCalcSubhd,
			int premiumCalcSubhd) {
		return new FlexSet(companyId, EnumAdaptor.valueOf(missCalcHd, FlexCalcAtr.class), EnumAdaptor.valueOf(premiumCalcHd, FlexCalcAtr.class),EnumAdaptor.valueOf(missCalcSubhd, FlexCalcAtr.class),EnumAdaptor.valueOf(premiumCalcSubhd, FlexCalcAtr.class));
	}
}
