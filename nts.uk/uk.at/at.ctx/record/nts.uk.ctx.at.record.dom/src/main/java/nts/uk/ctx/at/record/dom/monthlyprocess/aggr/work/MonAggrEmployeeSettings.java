package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffWorkPlaceSidImport;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShaDeforLaborMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShaFlexMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.monthcal.employee.ShaRegulaMonthActCalSet;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageContent;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 月別集計で必要な社員別設定
 * @author shuichu_ishida
 */
@Getter
public class MonAggrEmployeeSettings {

	/** 社員ID */
	private String employeeId;
	/** 社員 */
	private EmployeeImport employee;
	/** 所属雇用履歴 */
	private List<BsEmploymentHistoryImport> employments;
	/** 所属職場履歴 */
	private List<AffWorkPlaceSidImport> workplaces;
	/** 上位職場履歴 */
	private Map<String, List<String>> workPlacesToRoot;
	/** 社員別通常勤務労働時間 */
	private Optional<WorkingTimeSetting> regLaborTime;
	/** 社員別変形労働労働時間 */
	private Optional<WorkingTimeSetting> irgLaborTime;
	/** 通常勤務社員別月別実績集計設定 */
	private Optional<ShaRegulaMonthActCalSet> shaRegSetOpt;
	/** 変形労働社員別月別実績集計設定 */
	private Optional<ShaDeforLaborMonthActCalSet> shaIrgSetOpt;
	/** フレックス社員別月別実績集計設定 */
	private Optional<ShaFlexMonthActCalSet> shaFlexSetOpt;
	
	/** 集計開始日を締め開始日とする */
	@Setter
	private boolean noCheckStartDate;
	/** エラー情報 */
	private Map<String, ErrMessageContent> errorInfos;
	
	private MonAggrEmployeeSettings(String employeeId){
		this.employeeId = employeeId;
		this.employee = null;
		this.employments = new ArrayList<>();
		this.workplaces = new ArrayList<>();
		this.workPlacesToRoot = new HashMap<>();
		this.regLaborTime = Optional.empty();
		this.irgLaborTime = Optional.empty();
		this.shaRegSetOpt = Optional.empty();
		this.shaIrgSetOpt = Optional.empty();
		this.shaFlexSetOpt = Optional.empty();
		
		this.noCheckStartDate = true;
		this.errorInfos = new HashMap<>();
	}
	
	/**
	 * 設定読み込み
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param period 期間
	 * @param repositories 月別集計が必要とするリポジトリ
	 * @return 月別集計で必要な社員別設定
	 */
	public static MonAggrEmployeeSettings loadSettings(
			String companyId,
			String employeeId,
			DatePeriod period,
			RepositoriesRequiredByMonthlyAggr repositories){
	
		final String resourceId = "001";
		
		MonAggrEmployeeSettings domain = new MonAggrEmployeeSettings(employeeId);

		// 社員
		domain.employee = repositories.getEmpEmployee().findByEmpId(employeeId);
		if (domain.employee == null){
			domain.errorInfos.put(resourceId, new ErrMessageContent(TextResource.localize("Msg_1156")));
			return domain;
		}
		
		// 取得用期間の確認　（前月最終週分の過去6日を配慮）
		GeneralDate findStart = GeneralDate.min();
		if (period.start().after(GeneralDate.min().addDays(6))) findStart = period.start().addDays(-6);
		DatePeriod findPeriod = new DatePeriod(findStart, period.end());
		
		// 所属雇用履歴
		GeneralDate empCriteria = findPeriod.start();
		while (empCriteria.beforeOrEquals(findPeriod.end())){
			val employmentOpt = repositories.getEmployment().findEmploymentHistory(companyId, employeeId, empCriteria);
			if (employmentOpt.isPresent()){
				domain.employments.add(employmentOpt.get());
				empCriteria = employmentOpt.get().getPeriod().end();
				if (empCriteria.afterOrEquals(GeneralDate.max())) break;
				empCriteria = empCriteria.addDays(1);
			}
			else break;
		}
		
		// 所属職場履歴
		GeneralDate wkpCriteria = findPeriod.start();
		while (wkpCriteria.beforeOrEquals(findPeriod.end())){
			val workplaceOpt = repositories.getAffWorkplace().findBySidAndDate(employeeId, wkpCriteria);
			if (workplaceOpt.isPresent()){
				domain.workplaces.add(workplaceOpt.get());
				wkpCriteria = workplaceOpt.get().getDateRange().end();
				
				// 終了日時点の上位職場履歴
				val workplacesToRoot = repositories.getAffWorkplace().findAffiliatedWorkPlaceIdsToRoot(
						companyId, employeeId, wkpCriteria);
				domain.workPlacesToRoot.put(workplaceOpt.get().getWorkplaceId(), workplacesToRoot);
				
				// 次の履歴へ
				if (wkpCriteria.afterOrEquals(GeneralDate.max())) break;
				wkpCriteria = wkpCriteria.addDays(1);
			}
			else break;
		}
		
		// 社員別通常勤務労働時間
		val regWorkTime = repositories.getShainRegularWorkTime().find(companyId, employeeId);
		if (regWorkTime.isPresent()) domain.regLaborTime = Optional.of(regWorkTime.get().getWorkingTimeSet());
		
		// 社員別変形労働労働時間
		val irgWorkTime = repositories.getShainTransLaborTime().find(companyId, employeeId);
		if (irgWorkTime.isPresent()) domain.irgLaborTime = Optional.of(irgWorkTime.get().getWorkingTimeSet());
		
		// 通常勤務社員別月別実績集計設定
		domain.shaRegSetOpt = repositories.getShaRegSetRepo().find(companyId, employeeId);
		
		// 変形労働社員別月別実績集計設定
		domain.shaIrgSetOpt = repositories.getShaIrgSetRepo().find(companyId, employeeId);
		
		// フレックス社員別月別実績集計設定
		domain.shaFlexSetOpt = repositories.getShaFlexSetRepo().find(companyId, employeeId);
		
		return domain;
	}
	
	/**
	 * 所属雇用履歴の取得
	 * @param ymd 年月日
	 * @return 所属雇用履歴
	 */
	public Optional<BsEmploymentHistoryImport> getEmployment(GeneralDate ymd){
		return this.employments.stream().filter(c -> c.getPeriod().contains(ymd)).findFirst();
	}
	
	/**
	 * 所属職場履歴の取得
	 * @param ymd 年月日
	 * @return 所属職場履歴
	 */
	public Optional<AffWorkPlaceSidImport> getWorkplace(GeneralDate ymd){
		return this.workplaces.stream().filter(c -> c.getDateRange().contains(ymd)).findFirst();
	}
	
	/**
	 * 所属職場履歴の取得
	 * @param ymd 年月日
	 * @return 所属職場履歴
	 */
	public List<String> getWorkplacesToRoot(GeneralDate ymd){
		val workplaceOpt = this.workplaces.stream().filter(c -> c.getDateRange().contains(ymd)).findFirst();
		if (!workplaceOpt.isPresent()) return new ArrayList<>();
		val workplaceId = workplaceOpt.get().getWorkplaceId();
		if (!this.workPlacesToRoot.containsKey(workplaceId)) return new ArrayList<>();
		return this.workPlacesToRoot.get(workplaceId);
	}
}
