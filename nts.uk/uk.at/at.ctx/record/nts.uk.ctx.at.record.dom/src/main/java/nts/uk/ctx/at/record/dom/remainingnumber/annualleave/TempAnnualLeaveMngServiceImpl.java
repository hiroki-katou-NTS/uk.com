package nts.uk.ctx.at.record.dom.remainingnumber.annualleave;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.TempAnnualLeaveMngRepository;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * ドメインサービス実装：暫定年休管理データ
 * @author shuichu_ishida
 */
@Stateless
public class TempAnnualLeaveMngServiceImpl implements TempAnnualLeaveMngService {

	/** 会社ID */
	private String companyId;
	/** 社員ID */
	private String employeeId;
	/** 期間 */
	private DatePeriod period;
	/** モード */
	private TempAnnualLeaveMngMode mode;
	
	/** 日別実績の勤務情報リスト */
	private List<WorkInfoOfDailyPerformance> workInfoOfDailys;
	/** 暫定年休管理データリスト */
	private List<TempAnnualLeaveManagement> tempAnnualLeaveMngs;
	
	/** 日別実績の勤務情報 */
	@Inject
	public WorkInformationRepository workInformationRepo;
	/** 暫定年休管理データ */
	@Inject
	private TempAnnualLeaveMngRepository tempAnnualLeaveMngRepo;
	
	/** 暫定年休管理データを作成する */
	@Override
	public List<TempAnnualLeaveManagement> create(String companyId, String employeeId, DatePeriod period,
			TempAnnualLeaveMngMode mode) {
		
		this.companyId = companyId;
		this.employeeId = employeeId;
		this.period = period;
		this.mode = mode;
		
		this.workInfoOfDailys = new ArrayList<>();
		this.tempAnnualLeaveMngs = new ArrayList<>();
		
		// 暫定データ作成用の勤務予定・勤務実績・申請を取得
		this.getSourceDataForCreate();
		
		// 暫定管理データを作成する
		this.createTempManagementData();
		
		//　「モード」をチェック
		if (mode == TempAnnualLeaveMngMode.OTHER){

			// 「暫定年休管理データ」をDELETEする
			this.tempAnnualLeaveMngRepo.removeByPeriod(employeeId, period);
			
			// 「暫定年休管理データ」をINSERTする
			for (val tempAnnualLeaveMng : this.tempAnnualLeaveMngs){
				this.tempAnnualLeaveMngRepo.persistAndUpdate(tempAnnualLeaveMng);
			}
			
			// 「個人残数更新フラグ管理．更新状況」を「更新済み」にUpdateする
			//*****（未）　設計待ち。
		}
		
		return this.tempAnnualLeaveMngs;
	}
	
	/**
	 * 暫定データ作成用の勤務予定・勤務実績・申請を取得
	 */
	private void getSourceDataForCreate(){
	
		// 「モード」をチェックする
		if (this.mode == TempAnnualLeaveMngMode.MONTHLY){
			
			// 「日別実績の勤務情報」を取得する
			this.workInfoOfDailys = this.workInformationRepo.findByPeriodOrderByYmd(this.employeeId, this.period);
		}
		if (this.mode == TempAnnualLeaveMngMode.OTHER){

			// 社員．期間に未反映の申請を取得する
			//*****（未）　RequestList依頼待ち。requestコンテキストの処理を呼んで、データを貰う。
			
			// 「日別実績の勤務情報」を取得する
			this.workInfoOfDailys = this.workInformationRepo.findByPeriodOrderByYmd(this.employeeId, this.period);
		
			// 「勤務予定基本情報」を取得する
			//*****（未）　RequestList#141の完成待ち。
		}
	}
	
	/**
	 * 暫定管理データを作成する
	 */
	private void createTempManagementData(){
		
		GeneralDate procDate = this.period.start();
		while (procDate.beforeOrEquals(this.period.end())){
			
			
			procDate = procDate.addDays(1);
		}
	}
}
