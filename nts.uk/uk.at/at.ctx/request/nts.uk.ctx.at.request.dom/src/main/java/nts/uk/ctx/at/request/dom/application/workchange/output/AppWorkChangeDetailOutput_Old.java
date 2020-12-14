package nts.uk.ctx.at.request.dom.application.workchange.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange_Old;

/**
 * 「古いクラス → 削除予定 → 使わないでください (Old Class → Delete plan → Please don't use it)」
 * @author HoangND
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppWorkChangeDetailOutput_Old {
	
	/**
	 * 勤務変更申請の表示情報
	 */
	private AppWorkChangeDispInfo_Old appWorkChangeDispInfo;
	
	/**
	 * 勤務変更申請
	 */
	private AppWorkChange_Old appWorkChange;
	
}
