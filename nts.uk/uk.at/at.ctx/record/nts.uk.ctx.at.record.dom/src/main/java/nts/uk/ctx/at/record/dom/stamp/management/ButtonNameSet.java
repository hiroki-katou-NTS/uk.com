package nts.uk.ctx.at.record.dom.stamp.management;

import java.util.Optional;

import lombok.Getter;

/**
 * ボタン名称設定
 * @author phongtq
 *
 */
public class ButtonNameSet {

	/** 文字色 */
	@Getter
	private TextColor textColor;
	
	/** ボタン名称 */
	@Getter
	private Optional<ButtonName> buttonName;
}
