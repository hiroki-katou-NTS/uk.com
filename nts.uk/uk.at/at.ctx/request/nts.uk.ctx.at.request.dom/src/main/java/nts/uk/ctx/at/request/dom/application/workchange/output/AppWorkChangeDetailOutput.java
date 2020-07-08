package nts.uk.ctx.at.request.dom.application.workchange.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppWorkChangeDetailOutput {
	/**
	 * 勤務変更申請の表示情報
	 */
	private AppWorkChangeDispInfo appWorkChangeDispInfo;
	
	/**
	 * 勤務変更申請
	 */
	private AppWorkChange appWorkChange;
}
