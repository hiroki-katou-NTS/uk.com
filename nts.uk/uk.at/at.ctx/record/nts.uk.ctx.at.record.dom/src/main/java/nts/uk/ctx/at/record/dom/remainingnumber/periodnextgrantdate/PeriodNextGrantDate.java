package nts.uk.ctx.at.record.dom.remainingnumber.periodnextgrantdate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
/**
 * 指定した年月日を基準に、前回付与日から次回付与日までの期間を取得
 * @author tutk
 *
 */
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeRecordAdapter;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeRecordImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.yearholiday.employeeinfo.basicinfo.function.calnextholidaygrant.CalNextHolidayGrantService;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.NextAnnualLeaveGrant;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.service.Period;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class PeriodNextGrantDate {
	
	@Inject 
	private AnnLeaEmpBasicInfoRepository annLeaEmpBasicInfoRepository;
		
	@Inject
	private CalNextHolidayGrantService calNextHolidayGrantService;
	
	@Inject
	private EmployeeRecordAdapter pmployeeRecordAdapter;
	
	public Optional<Period> getPeriodNextGrantDate(String employeeId,GeneralDate designatedDate) {
		String companyId = AppContexts.user().companyId();
		//Imported(就業)「社員」を取得する
		EmployeeRecordImport employeeRecordImport = pmployeeRecordAdapter.getPersonInfor(employeeId);
		//ドメインモデル「年休社員基本情報」を取得する
		Optional<AnnualLeaveEmpBasicInfo> annualLeaveEmpBasicInfo = annLeaEmpBasicInfoRepository.get(employeeId);
		if(!annualLeaveEmpBasicInfo.isPresent())
			return Optional.empty();
		
		//次回年休付与を計算 : not period
		List<NextAnnualLeaveGrant> listDataNotPeriod = calNextHolidayGrantService.getCalNextHolidayGrant(companyId, employeeId, Optional.empty());
		//次回年休付与を計算 : have period
		//Update EA修正履歴NO.3175 : add 1 year 
		Period periodParam = new Period(employeeRecordImport.getEntryDate(),listDataNotPeriod.get(0).getGrantDate().addYears(1));
		List<NextAnnualLeaveGrant> listDataByPeriod = calNextHolidayGrantService.getCalNextHolidayGrant(companyId, employeeId, Optional.of(periodParam));
		
		//nếu k lấy dc dữ liệu nào
		if(listDataByPeriod.isEmpty()) {
			//nullを返す
			return Optional.empty();
		}
		
		Period period = new Period();
		period.setStartDate(listDataByPeriod.get(0).getGrantDate());
		//取得できない場合(Khi nó không thể có được)
		if(listDataByPeriod.size() > 1) {
			List<NextAnnualLeaveGrant> listDataByPeriodSort = listDataByPeriod.stream()
					.sorted((x, y) -> y.getGrantDate().compareTo(x.getGrantDate())).collect(Collectors.toList());
			period.setStartDate(listDataByPeriodSort.get(1).getGrantDate());
			period.setEndDate(listDataByPeriodSort.get(0).getGrantDate().addDays(-1));
		}else if(listDataByPeriod.size() == 1) {
			period.setStartDate(employeeRecordImport.getEntryDate());
			//前回付与日←入社日
			period.setEndDate(listDataByPeriod.get(0).getGrantDate().addDays(-1));
		}
		
		return Optional.of(period);
	}
	
}
