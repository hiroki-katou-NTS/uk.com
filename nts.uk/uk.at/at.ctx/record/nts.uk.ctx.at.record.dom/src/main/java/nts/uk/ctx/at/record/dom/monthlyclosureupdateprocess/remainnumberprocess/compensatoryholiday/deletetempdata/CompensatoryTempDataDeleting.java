package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.compensatoryholiday.deletetempdata;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.AggrPeriodEachActualClosure;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakDayOffMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemainRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 
 * @author HungTT - <<Work>> 代休暫定データ削除
 *
 */

@Stateless
public class CompensatoryTempDataDeleting {
	
	@Inject
	private InterimRemainRepository interimRemainRepo;
	
	@Inject
	private InterimBreakDayOffMngRepository tempBreakDayOffRepo;

	// 暫定データ削除
	public void deleteTempDataProcess(AggrPeriodEachActualClosure period, String empId) {
		this.deleteTempLeaveMngData(period.getPeriod(), empId);
		this.deleteTempCompensatoryData(period.getPeriod(), empId);
	}
	
	// 休出暫定データの削除
	private void deleteTempLeaveMngData(DatePeriod period, String empId) {
		List<InterimRemain> listTempRemain = interimRemainRepo.getRemainBySidPriod(empId, period, RemainType.BREAK);
		if (CollectionUtil.isEmpty(listTempRemain)) return;
		List<String> listBreakId = listTempRemain.stream().map(b -> b.getRemainManaID()).collect(Collectors.toList());
		tempBreakDayOffRepo.deleteInterimBreakMng(listBreakId);
	}
	
	// 代休暫定データの削除
	private void deleteTempCompensatoryData(DatePeriod period, String empId) {
		List<InterimRemain> listTempRemain = interimRemainRepo.getRemainBySidPriod(empId, period, RemainType.SUBHOLIDAY);
		if (CollectionUtil.isEmpty(listTempRemain)) return;
		List<String> listDayOffId = listTempRemain.stream().map(b -> b.getRemainManaID()).collect(Collectors.toList());
		tempBreakDayOffRepo.deleteInterimDayOffMng(listDayOffId);
	}
	
}
