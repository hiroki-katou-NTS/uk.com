package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.CreateTempAnnLeaMngProc.AlgorithmResult;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonthlyCalculatingDailys;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;

/**
 * 実装：暫定年休管理データを作成する
 * @author shuichu_ishida
 */
@Stateless
public class CreateTempAnnualLeaveManagementImpl implements CreateTempAnnualLeaveManagement {

	/** 勤務情報の取得 */
	@Inject
	public WorkTypeRepository workTypeRepo;

	@Inject
	private RecordDomRequireService requireService;
	
	/** 暫定年休管理データを作成する */
	@Override
	public List<InterimRemain> algorithm(String companyId, String employeeId, DatePeriod period,
			InterimRemainMngMode mode) {
		
		CreateTempAnnLeaMngProc proc = new CreateTempAnnLeaMngProc();
		
		AlgorithmResult result = proc.algorithm(requireService.createRequire(), 
				companyId, employeeId, period, mode);

		result.getAtomTask().run();
		
		return result.getTempAnnualLeaveMngs();
	}
	
	/** 暫定年休管理データを作成する　（月別集計用） */
	@Override
	public List<InterimRemain> algorithm(String companyId, String employeeId, DatePeriod period,
			InterimRemainMngMode mode, Optional<MonAggrCompanySettings> companySets,
			Optional<MonthlyCalculatingDailys> monthlyCalcDailys) {
		
		CreateTempAnnLeaMngProc proc = new CreateTempAnnLeaMngProc();
		
		AlgorithmResult result = proc.algorithm(requireService.createRequire(),
				companyId, employeeId, period, mode, companySets, monthlyCalcDailys);
		
		result.getAtomTask().run();
		
		return result.getTempAnnualLeaveMngs();
	}
}
