package nts.uk.ctx.at.shared.dom.workrule.shiftmaster;

import java.util.Optional;

import lombok.Getter;

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
	private ShiftMasterName name;
	
	/**
	 * 色
	 */
	@Getter
	private ColorCodeChar6 color;
	
	/**
	 * スマホ表示用の色
	 */
	@Getter
	private ColorCodeChar6 colorSmartPhone;
	
	/**
	 * 備考
	 */
	@Getter
	private Optional<Remarks> remarks;

	public ShiftMasterDisInfor(ShiftMasterName name, ColorCodeChar6 color,ColorCodeChar6 colorSmartPhone, Remarks remarks) {
		this.name = name;
		this.color = color;
		this.colorSmartPhone = colorSmartPhone;
		this.remarks = Optional.ofNullable(remarks);
	}
	
}
