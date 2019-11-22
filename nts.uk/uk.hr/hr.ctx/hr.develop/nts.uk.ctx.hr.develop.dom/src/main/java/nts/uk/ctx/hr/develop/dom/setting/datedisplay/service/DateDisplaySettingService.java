package nts.uk.ctx.hr.develop.dom.setting.datedisplay.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.hr.develop.dom.setting.datedisplay.DateDisplaySetting;
import nts.uk.ctx.hr.develop.dom.setting.datedisplay.DateDisplaySettingRepository;
import nts.uk.ctx.hr.develop.dom.setting.datedisplay.DateDisplaySettingValue;
import nts.uk.ctx.hr.develop.dom.setting.datedisplay.dto.PeriodDisplaySettingList;

/**
 * @author anhdt
 *
 */
@Stateless
public class DateDisplaySettingService {

	@Inject
	private DateDisplaySettingRepository dateDisplaySettingRepo;

	// 日付表示設定の取得
	public List<PeriodDisplaySettingList> getDateDisplaySetting(String programId, String companyId) {
		List<DateDisplaySetting> results = new ArrayList<>();
		if (StringUtils.isEmpty(programId)) {
			// ドメインモデル [日付表示設定] を取得する(Lấy domain [DateDisplaySetting])
			results = dateDisplaySettingRepo.getSettingByCompanyId(companyId);
		} else {
			// ドメインモデル [日付表示設定] を取得する(Lấy domain [DateDisplaySetting])
			results = dateDisplaySettingRepo.getSettingByCompanyIdAndProgramId(programId, companyId);
		}
		
		if(CollectionUtil.isEmpty(results)) {
			return Collections.emptyList();
		}

		return results.stream().map(d -> {
			
			DateDisplaySettingValue startSet = d.getStartDateSetting();
			DateDisplaySettingValue endSet = d.getEndDateSetting().isPresent() ? d.getEndDateSetting().get() : null;
			
			return PeriodDisplaySettingList.builder()
					.programId(d.getProgramId())
					.startDateSettingCategory(startSet.getSettingClass().value)
					.startDateSettingDate(startSet.getSettingDate())
					.startDateSettingMonth(startSet.getSettingMonth())
					.startDateSettingNum(startSet.getSettingNum())
					.endDateSettingCategory(endSet != null ? endSet.getSettingClass().value: null)
					.endDateSettingDate(endSet != null ? endSet.getSettingDate() : null)
					.endDateSettingMonth(endSet != null ? endSet.getSettingMonth() : null)
					.endDateSettingNum(endSet != null ? endSet.getSettingNum() : null)
					.build();
		}).collect(Collectors.toList());
	}
}
