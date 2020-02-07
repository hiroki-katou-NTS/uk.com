package nts.uk.ctx.at.schedule.dom.shift.management;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.persistence.internal.xr.ValueObject;

import lombok.Getter;
import nts.arc.error.BusinessException;

/**
 * シフトパレットのシフト組み合わせ
 * 
 * @author phongtq
 *
 */

public class ShiftPalletCombinations extends ValueObject {

	/** 位置番号 */
	@Getter
	private final int positionNumber;

	/** 名称 */
	@Getter
	private ShiftCombinationName combinationName;

	/** シフト組み合わせ */
	@Getter
	private List<Combinations> combinations;

	// 説明：新しい＜シフトパレットのシフト組み合わせ＞を作る。														
	// 不変条件をチェックするため。
	public ShiftPalletCombinations(int positionNumber, ShiftCombinationName combinationName,
			List<Combinations> combinations) {
		
		// inv-1	1 <= 位置番号 <= 20		
		if (!(1 <= positionNumber && positionNumber <= 10)) {
			throw new BusinessException("Msg_1616");
		}

		// 	inv-2	1 <= @シフト組み合わせ.Size <= 31
		if (1 <= combinations.size() && combinations.size() <= 31) {
			throw new BusinessException("Msg_1627");
		}

		List<Integer> lstElement = combinations.stream().map(x -> x.getOrder()).distinct()
				.collect(Collectors.toList());

		if (lstElement.size() < combinations.size()) {
			//	inv-3	@シフト組み合わせ.順番が重複しないこと	
			throw new BusinessException("Msg_1627");
		} else {
			//	inv-4	@シフト組み合わせ: 昇順(シフトパレットのシフト.順番)
			combinations.sort((p1, p2) -> p1.getOrder() - p2.getOrder());
		}

		this.positionNumber = positionNumber;
		this.combinationName = combinationName;
		this.combinations = combinations;
	}
	
	public void sortCombinations(){
		// シフト組み合わせの順番を1～の連番に整頓する。
		List<Combinations> list = new ArrayList<>();
		Combinations com = null;
		for(int i = 1; i <= this.combinations.size(); i++ ){
			com = new Combinations(i, this.combinations.get(i).getShiftCode());
			list.add(com);
		};
		this.combinations = list;
	}
}
