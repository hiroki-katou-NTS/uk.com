package nts.uk.ctx.at.record.pubimp.dailyprocess.attendancetime;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.cache.DateHistoryCache;
import nts.arc.layer.app.cache.KeyDateHistoryCache;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.DailyHolidayWorkPub;
import nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.DailyHolidayWorkPubExport;
import nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.DailyHolidayWorkPubImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SEmpHistoryImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SysEmploymentHisAdapter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.holidayworktime.HolidayWorkFrameTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.HolidayWorkTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.timezone.outsideworktime.OverTimeSheet;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CheckDateForManageCmpLeaveService;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveComSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveEmSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveEmSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CheckDateForManageCmpLeaveService.Require;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DailyHolidayWorkPubImpl implements DailyHolidayWorkPub{
	
	/** 代休を管理する年月日かどうかを判断する */
	@Inject
	private CheckDateForManageCmpLeaveService checkDateForManageCmpLeaveService;
	// 以下、「代休を管理する年月日かどうかを判断する」で利用するRepository
	/** 社員雇用履歴 */
	@Inject
	private SysEmploymentHisAdapter sysEmploymentHisAdapter;
	/** 代休管理設定（会社別） */
	@Inject
	private CompensLeaveComSetRepository compensLeaveComSetRepo;
	/** 雇用の代休管理設定 */
	@Inject
	private CompensLeaveEmSetRepository compensLeaveEmSetRepo;
	
	@Override
	public DailyHolidayWorkPubExport calcHolidayWorkTransTime(DailyHolidayWorkPubImport imp) {
		
		OverTimeSheet.TransProcRequire require = new TransProcRequireImpl(
				this.checkDateForManageCmpLeaveService,
				this.sysEmploymentHisAdapter,
				this.compensLeaveComSetRepo,
				this.compensLeaveEmSetRepo);
		
		List<HolidayWorkFrameTime> result = HolidayWorkTimeSheet.transProcess(
				require, imp.getEmployeeId(), imp.getYmd(),
				imp.getWorkType(), imp.getAfterCalcUpperTimeList(), imp.getEachWorkTimeSet(),
				imp.getEachCompanyTimeSet());
		return DailyHolidayWorkPubExport.create(result);
	}
	
	/**
	 * Require実装：代休を管理する年月日かどうかを判断する
	 * @author shuichi_ishida
	 */
	private class CheckDateRequireImpl implements CheckDateForManageCmpLeaveService.Require{
		
		/** 社員雇用履歴 */
		private SysEmploymentHisAdapter sysEmploymentHisAdapter;
		/** 代休管理設定（会社別） */
		private CompensLeaveComSetRepository compensLeaveComSetRepo;
		/** 雇用の代休管理設定 */
		private CompensLeaveEmSetRepository compensLeaveEmSetRepo;

		private final KeyDateHistoryCache<String, SEmpHistoryImport> historyCache =
				KeyDateHistoryCache.incremental((employeeId, date) ->
				this.sysEmploymentHisAdapter.findSEmpHistBySid(AppContexts.user().companyId(), employeeId, date)
				.map(h -> DateHistoryCache.Entry.of(h.getPeriod(), h)));
		
		public CheckDateRequireImpl(
				SysEmploymentHisAdapter sysEmploymentHisAdapter,
				CompensLeaveComSetRepository compensLeaveComSetRepo,
				CompensLeaveEmSetRepository compensLeaveEmSetRepo){
			
			this.sysEmploymentHisAdapter = sysEmploymentHisAdapter;
			this.compensLeaveComSetRepo = compensLeaveComSetRepo;
			this.compensLeaveEmSetRepo = compensLeaveEmSetRepo;
		}
		
		@Override
		public Optional<SEmpHistoryImport> getEmploymentHis(String employeeId, GeneralDate baseDate) {
			return this.historyCache.get(employeeId, baseDate);
		}
		
		@Override
		public Optional<CompensatoryLeaveComSetting> getCmpLeaveComSet(String companyId){
			return Optional.ofNullable(this.compensLeaveComSetRepo.find(companyId));
		}
		
		@Override
		public Optional<CompensatoryLeaveEmSetting> getCmpLeaveEmpSet(String companyId, String employmentCode){
			return Optional.ofNullable(this.compensLeaveEmSetRepo.find(companyId, employmentCode));
		}
	}

	/**
	 * Require実装：残業時間帯.振替処理Require
	 * @author shuichi_ishida
	 */
	private class TransProcRequireImpl extends CheckDateRequireImpl implements OverTimeSheet.TransProcRequire{
		
		/** 代休を管理する年月日かどうかを判断する */
		private CheckDateForManageCmpLeaveService checkDateForManageCmpLeaveService;
		
		public TransProcRequireImpl(
				CheckDateForManageCmpLeaveService checkDateForManageCmpLeaveService,
				SysEmploymentHisAdapter sysEmploymentHisAdapter,
				CompensLeaveComSetRepository compensLeaveComSetRepo,
				CompensLeaveEmSetRepository compensLeaveEmSetRepo){
			
			super(sysEmploymentHisAdapter, compensLeaveComSetRepo, compensLeaveEmSetRepo);
			this.checkDateForManageCmpLeaveService = checkDateForManageCmpLeaveService;
		}
		
		@Override
		public boolean checkDateForManageCmpLeave(
				Require require, String companyId, String employeeId, GeneralDate ymd) {
			return this.checkDateForManageCmpLeaveService.check(require, companyId, employeeId, ymd);
		}
	}
}
