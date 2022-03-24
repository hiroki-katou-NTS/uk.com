package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.reflectworkinfor.updateifnotmanaged;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.businesscalendar.daycalendar.RecCalendarCompanyAdapter;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.calculationsetting.BreakSwitchClass;
import nts.uk.ctx.at.shared.dom.calculationsetting.StampReflectionManagement;
import nts.uk.ctx.at.shared.dom.calculationsetting.repository.StampReflectionManagementRepository;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.ErrMessageResource;
import nts.uk.ctx.at.shared.dom.schedule.WorkingDayCategory;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.ManageAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageContent;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrorMessageInfo;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionContent;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;

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
	private StampReflectionManagementRepository stampReflectionManagementRepository;

	/**
	 * スケジュール管理しない場合勤務情報を更新
	 * 
	 * @param cid
	 * @param employeeId
	 * @param ymd
	 */
	public UpdateIfNotManagedOutput update(String cid, String employeeId, GeneralDate ymd, IntegrationOfDaily integrationOfDaily) {
		String companyId = AppContexts.user().companyId();
		List<ErrorMessageInfo> listError = new ArrayList<>();
		// ドメインモデル「労働条件項目」を取得する
		Optional<WorkingConditionItem> optWorkingConditionItem = this.workingConditionItemRepository
				.getBySidAndStandardDate(employeeId, ymd);
		WorkInformation workInformation = null;
		if (optWorkingConditionItem.isPresent() && integrationOfDaily.getAffiliationInfor() != null) {
			//スケジュール管理しないか確認
			if (optWorkingConditionItem.get().getScheduleManagementAtr() == ManageAtr.NOTUSE) {
				//休出切替区分を確認する
				Optional<StampReflectionManagement> optStampReflectionMana = stampReflectionManagementRepository.findByCid(companyId);
				if(optStampReflectionMana.isPresent()) {
					if(optStampReflectionMana.get().getBreakSwitchClass() == BreakSwitchClass.WORKTYPE_IS_HOLIDAY_WORK) {
						// カレンダーが稼働日か非稼働日か確認する - RQ651
						WorkingDayCategory workingDayCategory = recCalendarCompanyAdapter.getWorkingDayAtr(cid,
							integrationOfDaily.getAffiliationInfor().getWplID(),
							integrationOfDaily.getAffiliationInfor().getClsCode().v(), ymd);
						
						if (workingDayCategory == null) {
							//曜日の勤務情報を取得する
							workInformation = optWorkingConditionItem.get().getWorkCategory().getWorkInformationDayOfTheWeek(ymd);
						} else {
							// 稼働日区分で勤務情報を取得する
							if (optWorkingConditionItem.map(x -> x.getWorkCategory()).orElse(null) == null) { // 勤務情報なし
								//リソースID không có nên để tạm là ""
								listError.add(
										new ErrorMessageInfo(companyId, employeeId, ymd, ExecutionContent.DAILY_CREATION,
												new ErrMessageResource(""), new ErrMessageContent(TextResource.localize("Msg_588"))));
								return new UpdateIfNotManagedOutput(false, listError);
							} else { // 勤務情報なし以外
								workInformation = optWorkingConditionItem.get().getWorkCategory().getWorkInfo(workingDayCategory);
							}
						}
						
					} else {
						//曜日の勤務情報を取得する
						workInformation = optWorkingConditionItem.get().getWorkCategory().getWorkInformationDayOfTheWeek(ymd);
						if(!workInformation.getWorkTimeCodeNotNull().isPresent()) {
							// 取得した勤務情報の就業時間帯コードを確認する
							workInformation = optWorkingConditionItem.get().getWorkCategory().getWorkInformationWorkDay();
						}
					}
					
					
					
					
				}
				// 勤務情報を反映
				integrationOfDaily.getWorkInformation().getRecordInfo().setWorkTypeCode(workInformation.getWorkTypeCode());
				integrationOfDaily.getWorkInformation().getRecordInfo().setWorkTimeCode(workInformation.getWorkTimeCode());
				
				// 日別勤怠の編集状態を追加する
				Optional<EditStateOfDailyAttd> editState28 = integrationOfDaily.getEditState().stream()
						.filter(c->c.getAttendanceItemId() == 28).findFirst();
				Optional<EditStateOfDailyAttd> editState29 = integrationOfDaily.getEditState().stream()
						.filter(c->c.getAttendanceItemId() == 29).findFirst();
				if(!editState28.isPresent()) {
					integrationOfDaily.getEditState().add(new EditStateOfDailyAttd(28, EditStateSetting.IMPRINT));
				}
				if(!editState29.isPresent()) {
					integrationOfDaily.getEditState().add(new EditStateOfDailyAttd(29, EditStateSetting.IMPRINT));
				}
				return new UpdateIfNotManagedOutput(true, listError);
			}
		}
		
		return new UpdateIfNotManagedOutput(false, listError);
	}

}
