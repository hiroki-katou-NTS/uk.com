package nts.uk.ctx.at.shared.dom.remainmana.absencerecruitment.export.query;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.remainmana.absencerecruitment.interim.InterimAbsMana;
import nts.uk.ctx.at.shared.dom.remainmana.absencerecruitment.interim.InterimRecAbasManaRepository;
import nts.uk.ctx.at.shared.dom.remainmana.absencerecruitment.interim.InterimRecMana;
import nts.uk.ctx.at.shared.dom.remainmana.breakdayoffmana.export.query.InterimRemainAggregateOutputData;
import nts.uk.ctx.at.shared.dom.remainmana.export.ClosureRemainPeriodOutputData;
import nts.uk.ctx.at.shared.dom.remainmana.export.RemainManagementExport;
import nts.uk.ctx.at.shared.dom.remainmana.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainmana.interimremain.InterimRemainRepository;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
@Stateless
public class AbsenceReruitmentManaQueryImpl implements AbsenceReruitmentManaQuery{
	@Inject
	private RemainManagementExport remainManaExport;
	@Inject
	private InterimRemainRepository remainRepo;
	@Inject
	private InterimRecAbasManaRepository recAbsRepo;
	@Override
	public List<InterimRemainAggregateOutputData> getAbsRecRemainAggregate(String employeeId, GeneralDate baseDate,
			YearMonth startMonth, YearMonth endMonth) {
		//アルゴリズム「締めと残数算出対象期間を取得する」を実行する
		ClosureRemainPeriodOutputData closureData = remainManaExport.getClosureRemainPeriod(employeeId, baseDate, startMonth, endMonth);
		//残数算出対象年月を設定する
		for(YearMonth ym = closureData.getStartMonth(); closureData.getEndMonth().greaterThanOrEqualTo(ym); ym.addMonths(1)) {
			InterimRemainAggregateOutputData outPutData = new InterimRemainAggregateOutputData(ym, null, null, null, null, null);
			//アルゴリズム「指定年月の締め期間を取得する」を実行する
			DatePeriod dateData = remainManaExport.getClosureOfMonthDesignation(closureData.getClosure(), ym);
			//アルゴリズム「期間内の振休発生数合計を取得」を実行する
			Double occurrentDays = this.getTotalOccurrentDay(employeeId, dateData);
			//アルゴリズム「期間内の振休使用数合計を取得」を実行する
			Double useDays = this.getUsedDays(employeeId, dateData);			
			//残数算出対象年月をチェックする
			if(ym.greaterThan(closureData.getClosure().getClosureMonth().getProcessingYm())) {
				//月度別代休残数集計を作成する
				outPutData.setYm(ym);
				outPutData.setMonthOccurrence(occurrentDays);
				outPutData.setMonthUse(useDays);
			} else {
				//当月の振休残数を集計する
			}
			
		}
		return null;
	}
	@Override
	public Double getTotalOccurrentDay(String employeeId, DatePeriod dateData) {
		//ドメインモデル「暫定振出管理データ」を取得する
		List<InterimRemain> getRemainBySidPriod = remainRepo.getRemainBySidPriod(employeeId, dateData);
		Double outputData = (double) 0;
		if(!getRemainBySidPriod.isEmpty()) {
			for (InterimRemain data : getRemainBySidPriod) {
				Optional<InterimRecMana> recData = recAbsRepo.getReruitmentById(data.getRemainManaID());
				if(recData.isPresent()) {
					outputData += recData.get().getOccurrenceDays().v();					
				}
			}
		}
		//ドメインモデル「振出管理データ」を取得する
		//TODO
		return outputData;
	}
	@Override
	public Double getUsedDays(String employeeId, DatePeriod dateData) {
		//ドメインモデル「暫定振休管理データ」を取得する
		List<InterimRemain> getRemainBySidPriod = remainRepo.getRemainBySidPriod(employeeId, dateData);
		Double outputData = (double) 0;
		if(!getRemainBySidPriod.isEmpty()) {
			for (InterimRemain data : getRemainBySidPriod) {
				Optional<InterimAbsMana> absData = recAbsRepo.getAbsById(data.getRemainManaID());
				if(absData.isPresent()) {
					outputData += absData.get().getRequeiredDays().v();
				}
			}			
		}
		//ドメインモデル「振休管理データ」を取得する
		//TODO
		return outputData;
	}

}
