package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.reflectworkinfor.updateifnotmanaged;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.businesscalendar.daycalendar.BasicWorkSettingImport;
import nts.uk.ctx.at.record.dom.adapter.businesscalendar.daycalendar.RecCalendarCompanyAdapter;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.schedule.WorkingDayCategory;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.workingcondition.ManageAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemService;

/**
 * スケジュール管理しない場合勤務情報を更新
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.日別実績作成処理.作成処理.アルゴリズム.1.勤務情報を反映する.スケジュール管理しない場合勤務情報を更新
 * 
 * @author tutk
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class UpdateIfNotManaged {

	@Inject
	private WorkingConditionItemRepository workingConditionItemRepository;

	@Inject
	private RecCalendarCompanyAdapter recCalendarCompanyAdapter;

	@Inject
	private WorkingConditionItemService workingConditionItemService;

	/**
	 * スケジュール管理しない場合勤務情報を更新
	 * 
	 * @param cid
	 * @param employeeId
	 * @param ymd
	 */
	public void update(String cid, String employeeId, GeneralDate ymd, IntegrationOfDaily integrationOfDaily) {
		// ドメインモデル「労働条件項目」を取得する
		Optional<WorkingConditionItem> optWorkingConditionItem = this.workingConditionItemRepository
				.getBySidAndStandardDate(employeeId, ymd);
		if (optWorkingConditionItem.isPresent() && integrationOfDaily.getAffiliationInfor() != null) {
			// 会社カレンダーが稼働日か非稼働日か確認する
			WorkingDayCategory workingDayCategory = recCalendarCompanyAdapter.getWorkingDayAtr(cid,
					integrationOfDaily.getAffiliationInfor().getWplID(),
					integrationOfDaily.getAffiliationInfor().getClsCode().v(), ymd);
			if (optWorkingConditionItem.get().getScheduleManagementAtr() == ManageAtr.NOTUSE) {
				
				if (workingDayCategory != null
						&& integrationOfDaily.getWorkInformation().getRecordInfo().getWorkTypeCode() != null) {
					// 基本勤務を取得する
					BasicWorkSettingImport settingImport = recCalendarCompanyAdapter.getBasicWorkSetting(cid, integrationOfDaily.getAffiliationInfor().getWplID(), integrationOfDaily.getAffiliationInfor().getClsCode().v(), workingDayCategory.value);
					
					// 個人情報勤務情報を取得
					Optional<WorkInformation> optData =  workingConditionItemService.getHolidayWorkScheduleNew(cid, employeeId, ymd,
							settingImport.getWorktypeCode(),
							workingDayCategory);
					//勤務情報を反映
					if(optData.isPresent()) {
						integrationOfDaily.getWorkInformation().getRecordInfo().setWorkTypeCode(optData.get().getWorkTypeCode());
						integrationOfDaily.getWorkInformation().getRecordInfo().setWorkTimeCode(optData.get().getWorkTimeCode());
						
//						integrationOfDaily.getWorkInformation().getScheduleInfo().setWorkTypeCode(optData.get().getWorkTypeCode());
//						integrationOfDaily.getWorkInformation().getScheduleInfo().setWorkTimeCode(optData.get().getWorkTimeCode());
					}
				}
			}
		}
	}

}
