package nts.uk.ctx.at.shared.dom.remainingnumber.export;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class RemainManagementExportImpl implements RemainManagementExport{
	@Inject
	private ClosureService closureService;
	@Override
	public ClosureRemainPeriodOutputData getClosureRemainPeriod(String employeeId, GeneralDate baseDate, YearMonth startMonth, YearMonth endMonth) {
		ClosureRemainPeriodOutputData outputData = new ClosureRemainPeriodOutputData(null, null, null);
		// アルゴリズム「社員に対応する処理締めを取得する」を実行する
		Closure closureData = closureService.getClosureDataByEmployee(employeeId, baseDate);
		if(closureData == null) {
			return null;
		}
		//当月と指定開始年月をチェックする
		if(startMonth.greaterThanOrEqualTo(closureData.getClosureMonth().getProcessingYm())) {
			outputData.setStartMonth(startMonth);
		} else {
			outputData.setStartMonth(closureData.getClosureMonth().getProcessingYm());
		}		
		outputData.setEndMonth(endMonth);
		if(outputData.getStartMonth().greaterThan(outputData.getEndMonth())) {
			return null;
		}
		
		outputData.setClosure(closureData);
		return outputData;
	}
	@Override
	public DatePeriod getClosureOfMonthDesignation(Closure closureData, YearMonth ym) {
		// 「締め」のアルゴリズム「指定した年月の期間をすべて取得する」を実行する
		List<DatePeriod> lstDatePeriod = closureData.getPeriodByYearMonth(ym);
		//残数算出期間を設定する
		if(lstDatePeriod.isEmpty()) {
			return null;
		}
		if(lstDatePeriod.size() == 1) {
			DatePeriod dateData = lstDatePeriod.get(0);
			return new DatePeriod(dateData.start(), dateData.end());
		}
		DatePeriod dateData1 = lstDatePeriod.get(0);
		DatePeriod dateData2 = lstDatePeriod.get(1);
		
		return new DatePeriod(dateData1.start(), dateData2.end());
	}
	@Override
	public DatePeriod periodCovered(String sid, GeneralDate baseDate) {
		DatePeriod closureBySid = closureService.findClosurePeriod(sid, baseDate);
		if(closureBySid == null) {
			return null;
		}
		GeneralDate endDate = closureBySid.end().addYears(1).addDays(-1);  
		DatePeriod adjustDate = new DatePeriod(closureBySid.start(), endDate);
		return adjustDate;
	}

}
