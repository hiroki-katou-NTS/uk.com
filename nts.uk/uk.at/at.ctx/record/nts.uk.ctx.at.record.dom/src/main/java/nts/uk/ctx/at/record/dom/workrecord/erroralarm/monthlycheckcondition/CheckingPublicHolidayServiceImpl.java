package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.PublicHolidayMonthSetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.Year;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.company.CompanyMonthDaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.company.CompanyMonthDaySettingRepository;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidaySettingRepository;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employee.EmployeeMonthDaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employee.EmployeeMonthDaySettingRepository;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employment.EmploymentMonthDaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employment.EmploymentMonthDaySettingRepository;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.workplace.WorkplaceMonthDaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.workplace.WorkplaceMonthDaySettingRepository;

@Stateless
public class CheckingPublicHolidayServiceImpl implements CheckingPublicHolidayService {
	@Inject
	private PublicHolidaySettingRepository publicHolidaySettingRepo;

	@Inject
	private EmployeeMonthDaySettingRepository employeeMonthDaySettingRepo;

	@Inject
	private WorkplaceMonthDaySettingRepository workplaceMonthDaySettingRepo;

	@Inject
	private EmploymentMonthDaySettingRepository employmentMonthDaySettingRepo;

	@Inject
	private CompanyMonthDaySettingRepository companyMonthDaySettingRepo;

	@Override
	public boolean checkPublicHoliday(String companyId, String employeeCd, String employeeId, String workplaceId,
			int isManageComPublicHd, YearMonth yearMonth,SpecHolidayCheckCon specHolidayCheckCon) {
		Year y =new Year(yearMonth.year());
		CompanyId c = new CompanyId(companyId);
		//公休設定
		Optional<PublicHolidaySetting> optPublicHolidaySetting = this.publicHolidaySettingRepo.get(companyId);
		if(optPublicHolidaySetting.isPresent()){
			PublicHolidaySetting publicHolidaySetting = optPublicHolidaySetting.get();
			if(isManageComPublicHd!=publicHolidaySetting.getIsManagePublicHoliday()){
				return false;
			}
			//ドメインモデル「社員月間日数設定」を取得する
			Optional<EmployeeMonthDaySetting> optEmployeeMonthDaySetting = this.employeeMonthDaySettingRepo.findByYear(c, employeeId, y);
			
			//ドメインモデル「職場月間日数設定」を取得する
			Optional<WorkplaceMonthDaySetting> optWorkplaceMonthDaySetting = this.workplaceMonthDaySettingRepo.findByYear(c, workplaceId, y);
			
			//ドメインモデル「雇用月間日数設定」を取得する
			Optional<EmploymentMonthDaySetting> optEmploymentMonthDaySetting = this.employmentMonthDaySettingRepo.findByYear(c, employeeCd, y);
			
			//ドメインモデル「会社月間日数設定」を取得する
			Optional<CompanyMonthDaySetting> optCompanyMonthDaySetting = this.companyMonthDaySettingRepo.findByYear(c, y);
			
			List<PublicHolidayMonthSetting> publicHolidayMonthSettings=null;
			if(optEmployeeMonthDaySetting.isPresent()&&optEmployeeMonthDaySetting.get().getPublicHolidayMonthSettings()!=null && !optEmployeeMonthDaySetting.get().getPublicHolidayMonthSettings().isEmpty()){
				 publicHolidayMonthSettings = optEmployeeMonthDaySetting.get().getPublicHolidayMonthSettings();
			}
			else if(optWorkplaceMonthDaySetting.isPresent() &&optWorkplaceMonthDaySetting.get().getPublicHolidayMonthSettings()!=null && !optWorkplaceMonthDaySetting.get().getPublicHolidayMonthSettings().isEmpty()){
				 publicHolidayMonthSettings = optWorkplaceMonthDaySetting.get().getPublicHolidayMonthSettings();
			}
			else if(optEmploymentMonthDaySetting.isPresent() && optEmploymentMonthDaySetting.get().getPublicHolidayMonthSettings()!=null && !optEmploymentMonthDaySetting.get().getPublicHolidayMonthSettings().isEmpty()){
				 publicHolidayMonthSettings = optEmploymentMonthDaySetting.get().getPublicHolidayMonthSettings();
			}
			else if(optCompanyMonthDaySetting.isPresent()&& optCompanyMonthDaySetting.get().getPublicHolidayMonthSettings()!=null && !optCompanyMonthDaySetting.get().getPublicHolidayMonthSettings().isEmpty()){
				 publicHolidayMonthSettings = optCompanyMonthDaySetting.get().getPublicHolidayMonthSettings();
			}
			if(publicHolidayMonthSettings==null){
				return false;
			}
			//パラメータ．期間(年月)の月の「月間公休日数」を取得する
			int inLegalHoliday =-1;
			for (PublicHolidayMonthSetting publicHolidayMonthSetting : publicHolidayMonthSettings) {
				if(publicHolidayMonthSetting.getMonth()==yearMonth.month()){
					inLegalHoliday=	publicHolidayMonthSetting.getInLegalHoliday().v().intValue();
					break;
				}
			}
			if(inLegalHoliday==-1){
				return false;
			}
			//todo inLegalHoliday compare numberDayDiffHoliday1 and numberDayDiffHoliday2 in SpecHolidayCheckCon
			//SpecHolidayCheckCon.compareOperator<=5 single 
			return true;
		}
		return false;
		
	}

}
