package nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * シフトパレット- VO
 * 
 * @author phongtq
 *
 */
@RequiredArgsConstructor
public class ShiftPalette implements DomainValue {

	/** 表示情報 */
	@Getter
	private final ShiftPaletteDisplayInfor displayInfor;

	/** 組み合わせ */
	@Getter
	private final List<ShiftPaletteCombinations> combinations;
	
	/**
	 * [C-1] 作る																							
	 * @param displayInfor
	 * @param combinations
	 */
	public static ShiftPalette create(ShiftPaletteDisplayInfor displayInfor, List<ShiftPaletteCombinations> combinations) {
		// 会社別シフトパレット(最大20個)を修正する。
		if (!(1 <= combinations.size() && combinations.size() <= 20)) {
			throw new BusinessException("Msg_1616");
		}
		
	    List<Integer> lstElement = combinations.stream().map(x -> x.getPositionNumber()).distinct().collect(Collectors.toList());
		
		if(lstElement.size() < combinations.size()){
			throw new BusinessException("Msg_1616");
		}
		
		combinations.sort((p1, p2)-> p1.getPositionNumber() - p2.getPositionNumber());
		return new ShiftPalette(displayInfor, combinations);
	}
	
	/**
	 * [1] 利用するシフトマスタコードのリストを取得する																							
	 */
	public List<ShiftMasterCode> getListShiftMasterCode() {
		List<ShiftMasterCode> data =  new ArrayList<>();
		List<Combinations> listCombinations = new ArrayList<>();
		this.combinations.stream().map(c -> listCombinations.addAll(c.getCombinations())).collect(Collectors.toList());
		data = listCombinations.stream().map(c->c.getShiftCode()).distinct().collect(Collectors.toList());
		return data;
	}
	
	/**
	 * [2] 複製する																							
	 */
	public ShiftPalette reproduct(ShiftPaletteName shiftPalletName) {
		ShiftPaletteDisplayInfor shiftPalletDisplayInfor = new ShiftPaletteDisplayInfor(shiftPalletName, NotUseAtr.USE,
				displayInfor.getRemarks());
		return new ShiftPalette(shiftPalletDisplayInfor, this.combinations);
		
	}
	
}
