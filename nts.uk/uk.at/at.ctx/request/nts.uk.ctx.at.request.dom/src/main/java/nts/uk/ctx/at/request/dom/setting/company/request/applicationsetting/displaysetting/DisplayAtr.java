package nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.displaysetting;

import lombok.AllArgsConstructor;

/**
 * 表示区分
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public enum DisplayAtr {
	
	// 使用しない
	NOT_DISPLAY(0, "使用しない"),
	
	// 使用する
	DISPLAY(1, "使用する");
	
	public final Integer value;
	
	public final String name;
}
