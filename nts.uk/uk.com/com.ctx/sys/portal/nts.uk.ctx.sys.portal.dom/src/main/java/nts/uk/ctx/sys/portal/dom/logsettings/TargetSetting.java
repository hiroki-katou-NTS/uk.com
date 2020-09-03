package nts.uk.ctx.sys.portal.dom.logsettings;

import lombok.Data;

@Data
public class TargetSetting {
	/**
	 * 使用区分
	 */
	private int usageCategory;
	
	/**
	 * 活性区分
	 */
	private int activeCategory;
}
