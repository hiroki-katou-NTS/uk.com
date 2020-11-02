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
public class DetailedWorkStatusSettingDto {

	// 表示区分
	private int displayType;

	// 項目
	private int item;

	public DetailedWorkStatusSettingDto(DetailedWorkStatusSetting domain) {
		super();
	}
	
}
