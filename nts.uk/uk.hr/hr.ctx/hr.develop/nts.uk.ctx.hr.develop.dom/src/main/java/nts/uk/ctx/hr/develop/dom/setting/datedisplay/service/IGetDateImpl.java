package nts.uk.ctx.hr.develop.dom.setting.datedisplay.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.hr.develop.dom.setting.datedisplay.DateDisplaySetting;
import nts.uk.ctx.hr.develop.dom.setting.datedisplay.DateDisplaySettingRepository;
import nts.uk.ctx.hr.develop.dom.setting.datedisplay.algorithm.DateDisplaySettingService;

/**
 * @author anhdt
 *
 */
@Stateless
public class IGetDateImpl implements IGetDate {

	@Inject
	private DateDisplaySettingRepository dateDisplaySettingRepo;

	@Inject
	private DateDisplaySettingService dateDisplaySettingSv;

	@Override
	public GeneralDate getDate(String companyId, String programId) {
		// ドメインモデル [日付表示設定] を取得する(Lấy domain [Set hiển thị date])
		List<DateDisplaySetting> settings = dateDisplaySettingRepo.getSettingByCompanyIdAndProgramId(programId,
				companyId);
		// 値を返す(Trả về value)
		if (CollectionUtil.isEmpty(settings)) {
			return null;
		}

		DateDisplaySetting setting = settings.get(0);
		// アルゴリズム [表示日の取得] を実行する(thực hiện thuật toán [lấy Displaydate])
		return dateDisplaySettingSv.getDisplayDate(companyId, setting.getStartDateSetting());
	}

}
