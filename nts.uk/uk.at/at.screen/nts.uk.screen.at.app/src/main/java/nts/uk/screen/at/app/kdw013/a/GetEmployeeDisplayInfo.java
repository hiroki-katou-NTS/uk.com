package nts.uk.screen.at.app.kdw013.a;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.jobmanagement.workchangeableperiodsetting.WorkChangeablePeriodSetting;
import nts.uk.ctx.at.record.dom.jobmanagement.workchangeableperiodsetting.WorkChangeablePeriodSettingRepository;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DateRange;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KDW013_工数入力.A:工数入力.メニュー別OCD.対象社員の表示情報を取得する
 * 
 * @author tutt
 *
 */
@Stateless
public class GetEmployeeDisplayInfo {

	@Inject
	private WorkChangeablePeriodSettingRepository workChangeablePeriodSettingRepo;
	
	@Inject
	private GetWorkConfirmationStatus getWorkConfirmationStatus;

	/**
	 * 
	 * @param sid
	 * @param refDate
	 * @param period
	 * @return
	 */
	public EmployeeDisplayInfo getInfo(String sid, GeneralDate refDate, DateRange period) {
		// 1: 取得する(@Require, 社員ID, 年月日):List<作業グループ>
		// TODO: Thanh chua code xong DS: 最近よく使う作業を取得する

		// 2: get(ログイン会社ID)
		Optional<WorkChangeablePeriodSetting> OptWorkChangeablePeriodSetting = workChangeablePeriodSettingRepo
				.get(AppContexts.user().companyId());

		// 3: [作業変更可能期間設定.isPresent]:作業修正可能開始日付を取得する(@Require, 社員ID):年月日

		// 4: <call>(社員ID,基準日)
		List<ConfirmerDto> listConfirmDto = getWorkConfirmationStatus.get(sid, refDate);
		
		// 5: <call>(社員ID,表示期間)
		
		
		return null;
	}
}
