package nts.uk.ctx.at.schedule.dom.shift.management;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainValue;

/**
 * シフトパレット
 * 
 * @author phongtq
 *
 */

public class ShiftPallet implements DomainValue {

	/** 表示情報 */
	@Getter
	private ShiftPalletDisplayInfor displayInfor;

	/** 組み合わせ */
	@Getter
	private List<ShiftPalletCombinations> combinations;

	public ShiftPallet(ShiftPalletDisplayInfor displayInfor, List<ShiftPalletCombinations> combinations) {
		// 会社別シフトパレット(最大20個)を修正する。
		if (!(1 <= combinations.size() && combinations.size() <= 20)) {
			throw new BusinessException("Msg_1616");
		}
		
	    List<Integer> lstElement = combinations.stream().map(x -> x.getPositionNumber()).distinct().collect(Collectors.toList());
		
		if(lstElement.size() < combinations.size()){
			throw new BusinessException("Msg_1616");
		}else {
			combinations.sort((p1, p2)-> p1.getPositionNumber() - p2.getPositionNumber());
		}
		
		this.displayInfor = displayInfor;
		this.combinations = combinations;
	}
}
