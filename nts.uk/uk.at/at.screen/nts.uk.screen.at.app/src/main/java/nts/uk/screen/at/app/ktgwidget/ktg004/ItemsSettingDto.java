package nts.uk.screen.at.app.ktgwidget.ktg004;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.DetailedWorkStatusSetting;

/**
 * @author thanhPV
 */
@NoArgsConstructor
@Setter
@Getter
public class ItemsSettingDto {

	// 表示区分
	private boolean displayType;

	// 項目
	private int item;
	
	// 特別休暇名称
	private String name;

	public ItemsSettingDto(DetailedWorkStatusSetting domain, String name) {
		this.item = domain.getItem().value;
		this.displayType = domain.getDisplayType().value == 1;
		this.name = name;
	}
	
}
