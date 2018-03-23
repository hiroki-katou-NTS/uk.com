package nts.uk.ctx.at.request.dom.application.appabsence.service.ten;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.MaxDayReference;

@Stateless
public class AbsenceTenProcessImpl implements AbsenceTenProcess{
	@Inject
	private AnnualPaidLeaveSettingRepository annualPaidLeaveSettingRepository;
	// 10-1.年休の設定を取得する
	public AnnualHolidaySetOutput getSettingForAnnualHoliday(String companyID){
		AnnualHolidaySetOutput annualHoliday = new AnnualHolidaySetOutput();
		// ドメインモデル「年休設定」を取得する(lấy dữ liệu domain 「年休設定」)
		AnnualPaidLeaveSetting annualPaidLeave = this.annualPaidLeaveSettingRepository.findByCompanyId(companyID);
		if(annualPaidLeave.getYearManageType() != null && annualPaidLeave.getYearManageType().equals(ManageDistinct.NO)){
			// ドメインモデル「年休設定」．年休管理区分 = 管理しない
						annualHoliday.setYearHolidayManagerFlg(false);
						annualHoliday.setHalfManageCategoryFlg(false);
						annualHoliday.setHalfHolidayLitmit(0);
						annualHoliday.setSuspensionTimeYearFlg(false);
						annualHoliday.setHoursHolidayType(false);
						annualHoliday.setHoursHolidayLimit(0);
						annualHoliday.setTimeYearRest(1);
						return annualHoliday;
		}
		if(annualPaidLeave.getYearManageType() != null && annualPaidLeave.getYearManageType().equals(ManageDistinct.YES)){
			// ドメインモデル「年休設定」．年休管理区分 = 管理する
			annualHoliday.setYearHolidayManagerFlg(true);
			// ドメインモデル「半日年休管理」．管理区分をチェックする(kiểm tra domain 「半日年休管理」．管理区分)
			if(annualPaidLeave.getManageAnnualSetting().getHalfDayManage().manageType.equals(ManageDistinct.YES)){
				annualHoliday.setHalfManageCategoryFlg(true);
				// ドメインモデル「半日年休管理」．参照先をチェックする(kiểm tra domain 「半日年休管理」．参照先)
				if(annualPaidLeave.getManageAnnualSetting().getHalfDayManage().reference.equals(MaxDayReference.CompanyUniform)){
					// ドメインモデル「半日年休管理」．参照先 = 会社一律
					if(annualPaidLeave.getManageAnnualSetting().getHalfDayManage().maxNumberUniformCompany != null){
						annualHoliday.setHalfHolidayLitmit(annualPaidLeave.getManageAnnualSetting().getHalfDayManage().maxNumberUniformCompany.v());
					}
				}else{
					// ドメインモデル「半日年休管理」．参照先 = 年休付与テーブルを参照 : TODO
				}
			}else{
				annualHoliday.setHalfManageCategoryFlg(false);
				annualHoliday.setHalfHolidayLitmit(0);
			}
			// ドメインモデル「時間年休管理設定」．時間年休管理区分をチェックする(kiểm tra domain「時間年休管理設定」．時間年休管理区分)
			if(annualPaidLeave.getTimeSetting().getTimeManageType().equals(ManageDistinct.NO)){
				// ドメインモデル「時間年休管理設定」．時間年休管理区分 = 管理しない
				annualHoliday.setSuspensionTimeYearFlg(false);
				annualHoliday.setHoursHolidayLimit(0);
				annualHoliday.setTimeYearRest(1);
			}else{
				// ドメインモデル「時間年休管理設定」．時間年休管理区分 = 管理する
				annualHoliday.setSuspensionTimeYearFlg(true);
				annualHoliday.setTimeYearRest(annualPaidLeave.getTimeSetting().getTimeUnit().value);
				// ドメインモデル「時間年休の上限日数」．参照先をチェックする(kiêm tra domain 「時間年休の上限日数」．参照先)
				if(annualPaidLeave.getTimeSetting().getMaxYearDayLeave().reference.equals(MaxDayReference.CompanyUniform)){
					// ドメインモデル「時間年休の上限日数」．参照先 = 会社一律
					annualHoliday.setHoursHolidayLimit(annualPaidLeave.getTimeSetting().getMaxYearDayLeave().maxNumberUniformCompany.v());
				}else{
					// ドメインモデル「時間年休の上限日数」．参照先 = 年休付与テーブルを参照: TODO
				}
				
			}
		}
		return annualHoliday;
	}
}
