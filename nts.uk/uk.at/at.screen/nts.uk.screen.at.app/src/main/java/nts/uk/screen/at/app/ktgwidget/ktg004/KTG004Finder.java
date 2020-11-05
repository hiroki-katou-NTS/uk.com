
package nts.uk.screen.at.app.ktgwidget.ktg004;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.ApproveWidgetRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.DetailedWorkStatusSetting;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.StandardWidget;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.StandardWidgetType;
import nts.uk.ctx.sys.portal.dom.toppagepart.standardwidget.WorkStatusItem;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

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
	public WorkStatusSettingDto getApprovedDataWidgetStart() {
		
		String companyId = AppContexts.user().companyId();
		
		List<ItemsSettingDto> itemsSetting = new ArrayList<>();
	
		//指定するウィジェットの設定を取得する(Get the settings of the specified widget)
		Optional<StandardWidget> standardWidget = approveWidgetRepo.findByWidgetTypeAndCompanyId(StandardWidgetType.WORK_STATUS, companyId);
		
		//ドメインモデル「特別休暇」を取得する(Get the domain model "special leave")
		List<SpecialHoliday> specialHolidays = specialHolidayRepository.findByCompanyId(companyId);
		
		if(standardWidget.isPresent()) {
			
			itemsSetting.addAll(standardWidget.get().getDetailedWorkStatusSettingList().stream().map(c-> new ItemsSettingDto(c, "")).collect(Collectors.toList()));
			for (SpecialHoliday specialHoliday : specialHolidays) {
				Optional<DetailedWorkStatusSetting> sphl = standardWidget.get().getDetailedWorkStatusSettingList().stream().filter(c->c.getItem().value == specialHoliday.getSpecialHolidayCode().v()).findFirst();
				if(sphl.isPresent()) {
					itemsSetting.add(new ItemsSettingDto(sphl.get(), specialHoliday.getSpecialHolidayName().v()));
				}else {
					itemsSetting.add(new ItemsSettingDto(new DetailedWorkStatusSetting(NotUseAtr.NOT_USE, EnumAdaptor.valueOf(specialHoliday.getSpecialHolidayCode().v(), WorkStatusItem.class)), specialHoliday.getSpecialHolidayName().v()));
				}
			}
		}else {
			for (int i = WorkStatusItem.DAY_ERR_DISPLAY_ATR.value; i <= WorkStatusItem.CARE_DISPLAY_ATR.value; i++) {
				itemsSetting.add(new ItemsSettingDto(new DetailedWorkStatusSetting(NotUseAtr.NOT_USE, EnumAdaptor.valueOf(i, WorkStatusItem.class)), ""));
			}
			for (SpecialHoliday specialHoliday : specialHolidays) {
				itemsSetting.add(new ItemsSettingDto(new DetailedWorkStatusSetting(NotUseAtr.NOT_USE, EnumAdaptor.valueOf(specialHoliday.getSpecialHolidayCode().v(), WorkStatusItem.class)), specialHoliday.getSpecialHolidayName().v()));
			}
		}
		
		return new WorkStatusSettingDto(itemsSetting, standardWidget.isPresent() ? standardWidget.get().getName().v() : null);
	}
}