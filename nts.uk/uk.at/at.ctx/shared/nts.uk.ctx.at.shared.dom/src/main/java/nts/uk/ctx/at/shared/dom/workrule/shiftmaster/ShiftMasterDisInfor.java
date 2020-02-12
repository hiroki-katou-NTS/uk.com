package nts.uk.ctx.at.shared.dom.workrule.shiftmaster;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;

/**
 * シフトマスタの表示情報
 * @author tutk
 *
 */
public class ShiftMasterDisInfor {
	/**
	 * 名称
	 */
	@Getter
	private NameShiftMater name;
	
	/**
	 * 色
	 */
	@Getter
	private ColorCodeChar6 color;
	
	/**
	 * 備考
	 */
	@Getter
	private Optional<Remarks> remarks;

	public ShiftMasterDisInfor(NameShiftMater name, ColorCodeChar6 color, Remarks remarks) {
		this.name = name;
		this.color = color;
		this.remarks = Optional.ofNullable(remarks);
	}
	
	
	
}
