
package nts.uk.screen.at.app.ktgwidget.ktg004;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.ApproveWidgetRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KTG_ウィジェット.KTG004_勤務状況.B：設定ダイアログ.ユースケース.起動する.起動する
 * @author thanhPV
 */
@Stateless
public class KTG004Finder {

	@Inject
	private ApproveWidgetRepository approveWidgetRepo;
	
	@Inject
	private SpecialHolidayRepository specialHolidayRepository;

	/** 起動する */
	public void getApprovedDataWidgetStart() {
		
		String companyId = AppContexts.user().companyId();
	
		//指定するウィジェットの設定を取得する(Get the settings of the specified widget)
		
		
		//ドメインモデル「特別休暇」を取得する(Get the domain model "special leave")
		List<SpecialHoliday> specialHolidays = specialHolidayRepository.findByCompanyId(companyId);
	}
}