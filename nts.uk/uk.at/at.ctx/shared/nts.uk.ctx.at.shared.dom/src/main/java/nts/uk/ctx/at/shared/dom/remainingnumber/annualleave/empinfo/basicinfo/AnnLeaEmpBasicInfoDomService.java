package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.valueobject.AnnLeaEmpBasicInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.valueobject.AnnLeaRemNumValueObject;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnLeaGrantRemDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveRemainingTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AnnLeaEmpBasicInfoDomService{
	
	@Inject
	private AnnualPaidLeaveSettingRepository annPaidLeaSettingRepo;

	@Inject
	private AnnLeaGrantRemDataRepository annLeaDataRepo;
	
	@Inject 
	private AnnualPaidLeaveSettingRepository annualPaidLeaveSettingRepository;
		
	private static final String not_grant = "未付与";
	
	public AnnLeaRemNumValueObject getAnnLeaveNumber(String companyId, String employeeId) {
		List<AnnualLeaveGrantRemainingData> annualLeaveDataList = annLeaDataRepo.findNotExp(employeeId);
		return getAnnualLeaveNumber(companyId, annualLeaveDataList);
	}
	
	public AnnLeaRemNumValueObject getAnnualLeaveNumber(String companyId,
			List<AnnualLeaveGrantRemainingData> listData) {
		Double remainingDays = 0d;
		for (AnnualLeaveGrantRemainingData data : listData) {
			remainingDays += data.getDetails().getRemainingNumber().getDays().v();
		}

		AnnualPaidLeaveSetting setting = annPaidLeaSettingRepo.findByCompanyId(companyId);
		boolean useTimeAnnualLeave = setting.getYearManageType() == ManageDistinct.YES
				&& setting.getTimeSetting().getTimeManageType() == ManageDistinct.YES;

		int remainingMinutes = 0;
		if (useTimeAnnualLeave) {
			for (AnnualLeaveGrantRemainingData data : listData) {
				Optional<AnnualLeaveRemainingTime> minutes = data.getDetails().getRemainingNumber().getMinutes();
				remainingMinutes += minutes.isPresent() ? minutes.get().v() : 0;
			}
		}
		return new AnnLeaRemNumValueObject(remainingDays, remainingMinutes);
	}

	public String calculateAnnLeaNumWithFormat(String companyId, List<AnnualLeaveGrantRemainingData> listData) {

		AnnLeaRemNumValueObject annLeaveRemainNumber = getAnnualLeaveNumber(companyId, listData);

		// Total time
		// No268特別休暇の利用制御
		AnnualPaidLeaveSetting annualPaidLeaveSet = annualPaidLeaveSettingRepository.findByCompanyId(AppContexts.user().companyId());	
		
		if (annualPaidLeaveSet.getTimeSetting().getMaxYearDayLeave().manageType == ManageDistinct.NO){
			return annLeaveRemainNumber.getDays() + "日";
		}
		
		int remainingMinutes = annLeaveRemainNumber.getMinutes();

		int remainingHours = remainingMinutes / 60;
		remainingMinutes -= remainingHours * 60;

		return annLeaveRemainNumber.getDays() + "日と　" + remainingHours + ":" + convertWithMinutes(remainingMinutes);
	}
	
	public String calculateLastGrantDate(String employeeId) {
		Optional<AnnualLeaveGrantRemainingData> lastDataOpt = annLeaDataRepo.getLast(employeeId);
		if (!lastDataOpt.isPresent()) {
			return not_grant;
		} else {
			return lastDataOpt.get().getGrantDate().toString();
		}
	}
	
	public String calculateRervLeaveNumber(List<ReserveLeaveGrantRemainingData> listData) {
		Double remainingDays = 0d;
		for (ReserveLeaveGrantRemainingData data : listData) {
			remainingDays += data.getDetails().getRemainingNumber().v();
		}
		return remainingDays + "日";
	}
	
	private String convertWithMinutes(int minutes) {
		if ( Math.abs(minutes) < 10) {
			return "0" + Math.abs(minutes);
		}
		return "" + Math.abs(minutes);
	}
	
	/**
	 * アルゴリズム「次回年休情報を取得する」
　	 * パラメータ＝社員ID：画面で選択している社員ID
　	 * パラメータ＝年休付与基準日：IS00279の値
	 * パラメータ＝年休付与テーブル：IS00280の値
	 * パラメータ＝労働条件の期間：NULL
	 * パラメータ＝契約時間：NULL
	 * パラメータ＝入社年月日：NULL
	 * パラメータ＝退職年月日：NULL
	 * @return 次回年休付与日
	 */
	public GeneralDate getNextHDGrantDate(AnnLeaEmpBasicInfo annLea){
		return GeneralDate.today();
	}
	
	/**
	 * 次回年休情報を取得する
	 * パラメータ＝社員ID：画面で選択している社員ID
	 * パラメータ＝年休付与基準日：IS00279の値
	 * パラメータ＝年休付与テーブル：IS00280の値
	 * パラメータ＝労働条件の期間：NULL
	 * パラメータ＝契約時間：NULL
	 * パラメータ＝入社年月日：NULL
	 * パラメータ＝退職年月日：NULL
	 * @return 次回年休付与日数
	 */
	public String getNextHolidayGrantDays(AnnLeaEmpBasicInfo annLea){
		return null;
	}
	
	/**
	 * 次回年休情報を取得する
	 * パラメータ＝社員ID：画面で選択している社員ID
	 * パラメータ＝年休付与基準日：IS00279の値
	 * パラメータ＝年休付与テーブル：IS00280の値
	 * パラメータ＝労働条件の期間：NULL 
	 * パラメータ＝契約時間：NULL
	 * パラメータ＝入社年月日：NULL
	 * パラメータ＝退職年月日：NULL
	 * @return 次回時間年休付与上限
	 */
	public String getNextMaxHolidayGrant(AnnLeaEmpBasicInfo annLea){
		return null;
	}
}
