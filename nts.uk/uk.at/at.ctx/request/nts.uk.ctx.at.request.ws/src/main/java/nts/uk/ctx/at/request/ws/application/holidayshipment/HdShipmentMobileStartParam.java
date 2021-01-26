package nts.uk.ctx.at.request.ws.application.holidayshipment;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.refactor5.dto.DisplayInforWhenStarting;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class HdShipmentMobileStartParam {
	/**
	 * 画面モード
	 */
	private boolean newMode;
	
	/**
	 * 社員ID＜Optional＞
	 */
	private String employeeID;
	
	/**
	 * 申請対象日リスト＜Optional＞
	 */
	private List<String> dateLst;
	
	/**
	 * 振休振出申請起動時の表示情報＜Optional＞
	 */
	private DisplayInforWhenStarting displayInforWhenStarting;
	
	private AppDispInfoStartupDto appDispInfoStartupCmd;
}
