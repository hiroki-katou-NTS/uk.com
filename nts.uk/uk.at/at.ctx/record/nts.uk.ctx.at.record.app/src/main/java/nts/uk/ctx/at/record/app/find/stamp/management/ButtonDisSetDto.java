package nts.uk.ctx.at.record.app.find.stamp.management;

import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * 
 * @author phongtq
 *
 */
@Data
@AllArgsConstructor
public class ButtonDisSetDto {
	/** ボタン名称設定 */
	private ButtonNameSetDto buttonNameSet;
	
	/** 背景色 */
	private String backGroundColor;
}
