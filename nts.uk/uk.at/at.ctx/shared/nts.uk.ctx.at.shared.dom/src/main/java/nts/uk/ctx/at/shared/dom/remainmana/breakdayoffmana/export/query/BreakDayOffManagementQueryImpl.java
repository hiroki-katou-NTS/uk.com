package nts.uk.ctx.at.shared.dom.remainmana.breakdayoffmana.export.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.remainmana.breakdayoffmana.interim.InterimBreakDayOffManaRepository;
import nts.uk.ctx.at.shared.dom.remainmana.breakdayoffmana.interim.InterimBreakMana;
import nts.uk.ctx.at.shared.dom.remainmana.breakdayoffmana.interim.InterimDayOffMana;
import nts.uk.ctx.at.shared.dom.remainmana.export.ClosureRemainPeriodOutputData;
import nts.uk.ctx.at.shared.dom.remainmana.export.RemainManagementExport;
import nts.uk.ctx.at.shared.dom.remainmana.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainmana.interimremain.InterimRemainRepository;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class BreakDayOffManagementQueryImpl implements BreakDayOffManagementQuery{
	@Inject
	private RemainManagementExport remainManaExport;
	@Inject
	private InterimBreakDayOffManaRepository breakDayOffRepo;
	@Inject
	private InterimRemainRepository remainRepo;
	
	@Override
	public List<InterimRemainAggregateOutputData> getInterimRemainAggregate(String employeeId, GeneralDate baseDate,
			YearMonth startMonth, YearMonth endMonth) {
		
		//アルゴリズム「締めと残数算出対象期間を取得する」を実行する
		ClosureRemainPeriodOutputData closureData = remainManaExport.getClosureRemainPeriod(employeeId, baseDate, startMonth, endMonth);
		//残数算出対象年月を設定する
		List<InterimRemainAggregateOutputData> lstData = new ArrayList<>(); 
		for(YearMonth ym = closureData.getStartMonth(); closureData.getEndMonth().greaterThanOrEqualTo(ym); ym.addMonths(1)) {
			InterimRemainAggregateOutputData outPutData = new InterimRemainAggregateOutputData(ym, null, null, null, null, null);
			//アルゴリズム「指定年月の締め期間を取得する」を実行する
			DatePeriod dateData = remainManaExport.getClosureOfMonthDesignation(closureData.getClosure(), ym);
			//アルゴリズム「期間内の代休発生数合計を取得」を実行する
			Double occurrentDays = this.getTotalOccurrenceDays(employeeId, dateData);
			//アルゴリズム「期間内の代休使用数合計を取得」を実行する
			Double useDays = this.getTotalUseDays(employeeId, dateData);
			
			
			//残数算出対象年月をチェックする
			//残数算出対象年月＞締め.当月.処理年月
			if(ym.greaterThan(closureData.getClosure().getClosureMonth().getProcessingYm())) {
				//月度別代休残数集計を作成する
				outPutData.setYm(ym);
				outPutData.setMonthOccurrence(occurrentDays);
				outPutData.setMonthUse(useDays);
			} else {
				//当月の代休残数を集計する
				outPutData = this.aggregatedDayoffCurrentMonth(employeeId);
				//月度別代休残数集計を作成する
				//TODO: can xac nhan lai outputdata trong truong hop nay
				
				
			}
			lstData.add(outPutData);
		}
		return lstData;
	}
	@Override
	public Double getTotalOccurrenceDays(String employeeId, DatePeriod dateData) {
		//ドメインモデル「暫定休出管理データ」を取得する
		List<InterimRemain> getRemainBySidPriod = remainRepo.getRemainBySidPriod(employeeId, dateData);
		Double outputData = (double) 0;
		if(!getRemainBySidPriod.isEmpty()) {
			for (InterimRemain interimRemain : getRemainBySidPriod) {
				Optional<InterimBreakMana> getBreakManaBybreakManaId = breakDayOffRepo.getBreakManaBybreakManaId(interimRemain.getRemainManaID());
				if(getBreakManaBybreakManaId.isPresent()) {
					outputData += getBreakManaBybreakManaId.get().getOccurrenceDays().v();
				}
			}	
		}
		
		//ドメインモデル「休出管理データ」を取得する
		//TODO cần phai chuyển domain về shared
		return outputData;
	}
	@Override
	public Double getTotalUseDays(String employeeId, DatePeriod dateData) {
		//ドメインモデル「暫定代休管理データ」を取得する
		List<InterimRemain> getRemainBySidPriod = remainRepo.getRemainBySidPriod(employeeId, dateData);
		Double outputData = (double) 0;
		if(!getRemainBySidPriod.isEmpty()) {
			for (InterimRemain interimRemain : getRemainBySidPriod) {
				Optional<InterimDayOffMana> getDayoffById = breakDayOffRepo.getDayoffById(interimRemain.getRemainManaID());
				if(getDayoffById.isPresent()) {
					outputData += getDayoffById.get().getRequiredDay().v();
				}			
			}	
		}
		
		//ドメインモデル「休出管理データ」を取得する
		//TODO can chuyen domain ve shared
		
		return outputData;
	}
	@Override
	public InterimRemainAggregateOutputData aggregatedDayoffCurrentMonth(String employeeId) {
		//アルゴリズム「月初の代休残数を取得」を実行する
		//TODO can chuyen domain ve share
		
		//アルゴリズム「期間内の代休消滅数合計を取得」を実行する
		//TODO hien tai chua lam duoc
		
		
		return null;
	}
	

}
