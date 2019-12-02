package nts.uk.ctx.hr.develop.dom.setting.datedisplay.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.hr.develop.dom.setting.datedisplay.DateDisplaySetting;
import nts.uk.ctx.hr.develop.dom.setting.datedisplay.DateDisplaySettingRepository;
import nts.uk.ctx.hr.develop.dom.setting.datedisplay.algorithm.DateDisplaySettingService;
import nts.uk.ctx.hr.develop.dom.setting.datedisplay.dto.DateDisplaySettingPeriod;

/**
 * @author anhdt
 *
 */
@Stateless
public class IGetDatePeriodImpl implements IGetDatePeriod {

	@Inject
	private DateDisplaySettingRepository dateDisplaySettingRepo;

	@Inject
	private DateDisplaySettingService dateDisplaySettingSv;

	@Override
	public DateDisplaySettingPeriod getDatePeriod(String companyId, String programId) {
		// ドメインモデル [日付表示設定] を取得する(Lấy domain [Set hiển thị date])
		List<DateDisplaySetting> settings = dateDisplaySettingRepo.getSettingByCompanyIdAndProgramId(programId,
				companyId);
		// 値を返す(Trả về value)
		if (CollectionUtil.isEmpty(settings)) {
			return null;
		}

		DateDisplaySetting setting = settings.get(0);
		// アルゴリズム [表示日の取得] を実行する(thực hiện thuật toán [lấy Displaydate])
		GeneralDate startDisplay = dateDisplaySettingSv.getDisplayDate(companyId, setting.getStartDateSetting());

		GeneralDate endDisplay = null;

		if (setting.getEndDateSetting().isPresent()) {
			endDisplay = dateDisplaySettingSv.getDisplayDate(companyId, setting.getEndDateSetting().get());
		}

		return new DateDisplaySettingPeriod(startDisplay, endDisplay);
	}

}
