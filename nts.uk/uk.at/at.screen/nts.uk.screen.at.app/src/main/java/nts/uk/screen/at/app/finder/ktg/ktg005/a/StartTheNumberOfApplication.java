package nts.uk.screen.at.app.finder.ktg.ktg005.a;

import javax.ejb.Stateless;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.StandardWidgetRepository;

/**
 * 
 * @author sonnlb
 * 
 */
@Stateless
public class StartTheNumberOfApplication {
	
	private StandardWidgetRepository standardRepo;

	// 申請件数起動
	/**
	 * 
	 * @param companyId  会社ID
	 * @param period     期間（開始日～終了日）
	 * @param employeeId 社員ID
	 */
	public void startTheNumberOfApplication(String companyId, DatePeriod period, String employeeId) {

		// 指定するウィジェットの設定を取得する
		// Input :標準ウィジェット種別＝申請状況
		getSettingSpecifiedWidget();
	}

	private void getSettingSpecifiedWidget() {

	}

}
