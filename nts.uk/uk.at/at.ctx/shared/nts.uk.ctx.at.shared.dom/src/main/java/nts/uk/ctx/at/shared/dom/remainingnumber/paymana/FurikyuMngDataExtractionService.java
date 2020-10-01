package nts.uk.ctx.at.shared.dom.remainingnumber.paymana;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.PersonEmpBasicInfoImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.EmploymentHistShareImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.UseClassification;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class FurikyuMngDataExtractionService {
	@Inject
	private SubstitutionOfHDManaDataRepository substitutionOfHDManaDataRepository;
	
	@Inject
	private PayoutManagementDataRepository payoutManagementDataRepository;
	
	@Inject
	private PayoutSubofHDManaRepository payoutSubofHDManaRepository;
	
	@Inject
	private SysEmploymentHisAdapter sysEmploymentHisAdapter;
	
	@Inject
	private SysWorkplaceAdapter syWorkplaceAdapter;
	
	@Inject
	private EmpEmployeeAdapter empEmployeeAdapter;
	
	@Inject
	private EmpSubstVacationRepository empSubstVacationRepository;
	
	@Inject
	private ComSubstVacationRepository comSubstVacationRepository;
	
	@Inject 
	private ClosureEmploymentRepository closureEmploymentRepository;
	
	@Inject
	private ShareEmploymentAdapter shareEmploymentAdapter;		
	
	@Inject
	private ClosureRepository closureRepo;
	
	public FurikyuMngDataExtractionData getFurikyuMngDataExtraction(String sid, boolean isPeriod) {
		List<PayoutManagementData> payoutManagementData;
		List<PayoutSubofHDManagement> payoutSubofHDManagementLinkToPayout = new ArrayList<PayoutSubofHDManagement>();
		List<SubstitutionOfHDManagementData> substitutionOfHDManagementData;
		List<PayoutSubofHDManagement> payoutSubofHDManagementLinkToSub = new ArrayList<PayoutSubofHDManagement>();
		Double numberOfDayLeft;
		int expirationDate;
		Integer closureId;
		String cid = AppContexts.user().companyId();
		String empCD = null;
		boolean haveEmploymentCode = false;
		
		// select 全ての状況empId
		if(isPeriod) {
			payoutManagementData = payoutManagementDataRepository.getAllBySid(sid);
			substitutionOfHDManagementData = substitutionOfHDManaDataRepository.getAllBySid(sid);
		// select 現在の残数状況
		} else {
			payoutManagementData = payoutManagementDataRepository.getBySidStateAndInSub(sid);
			substitutionOfHDManagementData = substitutionOfHDManaDataRepository.getBySidRemainDayAndInPayout(sid);
		}
		
		if (!payoutManagementData.isEmpty()){
			List<String> listPayoutID = payoutManagementData.stream().map(x ->{
				return x.getPayoutId();
			}).collect(Collectors.toList());
			
//			payoutSubofHDManagementLinkToPayout = payoutSubofHDManaRepository.getByListPayoutID(listPayoutID);
		}
		
		if (!substitutionOfHDManagementData.isEmpty()){
			List<String> listSubID = substitutionOfHDManagementData.stream().map(x ->{
				return x.getSubOfHDID();
			}).collect(Collectors.toList());
			
//			payoutSubofHDManagementLinkToSub = payoutSubofHDManaRepository.getByListSubID(listSubID);
		}
		
		if (sysEmploymentHisAdapter.findSEmpHistBySid(cid, sid, GeneralDate.legacyDate(new Date())).isPresent()) {
			empCD = sysEmploymentHisAdapter.findSEmpHistBySid(cid, sid, GeneralDate.legacyDate(new Date())).get().getEmploymentCode();
		}
		
		if(empCD != null) {
			haveEmploymentCode = true;
		}
		
		numberOfDayLeft = getNumberOfDayLeft(sid);
		expirationDate = getExpirationDate(sid, empCD);
		closureId = getClosureId(sid, empCD);
		
		SWkpHistImport sWkpHistImport = null;
		if(syWorkplaceAdapter.findBySid(sid, GeneralDate.today()).isPresent()) {
			sWkpHistImport = syWorkplaceAdapter.findBySid(sid, GeneralDate.today()).get();
		}
		List<String> employeeIds = new ArrayList<>();
		employeeIds.add(sid);
		List<PersonEmpBasicInfoImport> employeeBasicInfo = empEmployeeAdapter.getPerEmpBasicInfo(employeeIds);
		PersonEmpBasicInfoImport personEmpBasicInfoImport = null;
		if (!employeeBasicInfo.isEmpty()){
			personEmpBasicInfoImport = employeeBasicInfo.get(0);
		}
		
		return new FurikyuMngDataExtractionData(payoutManagementData, substitutionOfHDManagementData, payoutSubofHDManagementLinkToPayout, payoutSubofHDManagementLinkToSub, expirationDate, numberOfDayLeft, closureId, haveEmploymentCode, sWkpHistImport, personEmpBasicInfoImport);
	}
	
	public DisplayRemainingNumberDataInformation getFurikyuMngDataExtractionUpdate(String empId, boolean isPeriod) {		
		String cid = AppContexts.user().companyId();
		List<PayoutManagementData> payoutManagementData;
		List<SubstitutionOfHDManagementData> substitutionOfHDManagementData;
		List<PayoutSubofHDManagement> payoutSubofHDManagementLinkToPayout = new ArrayList<PayoutSubofHDManagement>();	
		double useDays;
		ComSubstVacation comSubstVacation;
		Integer closureId;
		// Step 振休管理データを管理するかチェック
		EmploymentManageDistinctDto emplManage = getEmploymentManageDistinct(cid, empId);
		// Step 取得した管理区分をチェック
		if (emplManage.getIsManage().value == ManageDistinct.NO.value) {
			throw new BusinessException("Msg_1731");
		} else {
			// Step Input．設定期間区分をチェック
			// select 全ての状況
			if (isPeriod) {
				// Step ドメイン「振出管理データ」を取得する
				payoutManagementData = payoutManagementDataRepository.getSid(cid, empId);
				// Step ドメイン「振休管理データ」を取得する
				substitutionOfHDManagementData = substitutionOfHDManaDataRepository.getBysiD(cid, empId);
				// select 現在の残数状況
			} else {
				// Step ドメイン「振出管理データ」を取得する
				payoutManagementData = payoutManagementDataRepository.getBySidAndStateAtr(cid, empId);
				// Step ドメイン「振休管理データ」を取得する
				substitutionOfHDManagementData = substitutionOfHDManaDataRepository.getBysiDAndAtr(cid, empId);
			}
			// Step 取得したデータをチェック
			if(payoutManagementData.isEmpty() && substitutionOfHDManagementData.isEmpty()) {
				throw new BusinessException("Msg_726");
			}
			// Step ドメイン「振出振休紐付け管理」を取得する
			if(substitutionOfHDManagementData.isEmpty()) {
				List<GeneralDate> listPayoutDate = payoutManagementData.stream().map(x -> {
					return x.getPayoutDate().getDayoffDate().get();
				}).collect(Collectors.toList());
				payoutSubofHDManagementLinkToPayout = payoutSubofHDManaRepository.getByListDate(empId, listPayoutDate);
			}else {
				List<GeneralDate> listSubDate = substitutionOfHDManagementData.stream().map(x -> {
					return x.getHolidayDate().getDayoffDate().get();
				}).collect(Collectors.toList());
				payoutSubofHDManagementLinkToPayout = payoutSubofHDManaRepository.getByListDate(empId, listSubDate);
			}
			
			// Step 振休残数データ情報を作成
			List<RemainInfoData> lstRemainData = this.getRemainInfoData(payoutManagementData, substitutionOfHDManagementData, payoutSubofHDManagementLinkToPayout, empId);
			List<RemainInfoDto> lstDataRemainDto =  lstRemainData.stream().map(item -> {
				RemainInfoDto itemData = RemainInfoDto.builder()
						.occurrenceId(item.getOccurrenceId().isPresent() ? item.getOccurrenceId().get() : null)
						.occurrenceHour(item.getOccurrenceHour().isPresent() ? item.getOccurrenceHour().get() : 0)
						.occurrenceDay(item.getOccurrenceDay().isPresent() ? item.getOccurrenceDay().get() : 0)
						.accrualDate(item.getAccrualDate().isPresent() ? item.getAccrualDate().get().toString() :null)
						.digestionId(item.getDigestionId().isPresent() ? item.getDigestionId().get() : "")
						.digestionTimes(item.getDigestionTimes().isPresent() ? item.getDigestionTimes().get() : 0)
						.digestionDays(item.getDigestionDays().isPresent() ? item.getDigestionDays().get() : 0)
						.digestionDay(item.getDigestionDay().isPresent() ? item.getDigestionDay().get().toString() : "")
						.legalDistinction(item.getLegalDistinction().isPresent() ? item.getLegalDistinction().get() : 0)
						.remainingHours(item.getRemainingHours().isPresent() ? item.getRemainingHours().get() : 0)
						.dayLetf(item.getDayLetf())
						.deadLine(item.getDeadLine().isPresent() ? item.getDeadLine().get().toString() : "")
						.usedTime(item.getUsedTime())
						.usedDay(item.getUsedDay())
						.build();
				return itemData;
			}).collect(Collectors.toList());
			// Step 月初の振休残数を取得
			useDays = getNumberOfRemainingHolidays(empId);
			// Step 振休管理設定を取得する
			comSubstVacation = getClassifiedManagementSetup(cid, emplManage.getEmploymentCode());
			// Step 締めIDを取得する
			closureId = getClosureId(empId, emplManage.getEmploymentCode());
			
			Optional<PresentClosingPeriodExport> closing = this.find(cid, closureId);
			DisplayRemainingNumberDataInformation result = DisplayRemainingNumberDataInformation.builder()
					.employeeId(empId)
					.totalRemainingNumber(useDays)
					.expirationDate(comSubstVacation.getSetting().getExpirationDate().value)
					.remainingData(lstDataRemainDto)
					.startDate(closing.get().getClosureStartDate())
					.endDate(closing.get().getClosureEndDate())
					.build();
				
		return result;
		}
	}
	
	// Step 振休管理データを管理するかチェック
	public EmploymentManageDistinctDto getEmploymentManageDistinct(String compId, String empId) {
		// Step 管理区分 ＝ 管理しない
		EmploymentManageDistinctDto emplManage = new EmploymentManageDistinctDto();
		GeneralDate now = GeneralDate.today();
		emplManage.setIsManage(ManageDistinct.NO);
		// Step 社員IDから全ての雇用履歴を取得
		List<EmploymentHistShareImport> empHistShrImp = this.shareEmploymentAdapter.findByEmployeeIdOrderByStartDate(empId);
		// Step 取得した社員の雇用履歴をチェック
		if (empHistShrImp.isEmpty()) {
			// Step エラーメッセージ(Msg_1306)を表示する
			throw new BusinessException("Msg_1306");
		} else {
			// Step 取得した社員の雇用履歴をループする
			for (EmploymentHistShareImport empHist : empHistShrImp) {
				// Step 管理区分設定を取得する
				ComSubstVacation comSubstVaca = getClassifiedManagementSetup(compId, empHist.getEmploymentCode());
				// Step 取得した「振休管理設定」．管理区分をチェック
				if (comSubstVaca.isManaged()) {
					// Step 管理区分 ＝ 管理する
					emplManage.setIsManage(ManageDistinct.YES);

					// Step 雇用コード ＝ 取得した社員の雇用履歴．期間．開始日 ＜＝ システム日付 AND 取得した社員の雇用履歴．期間．終了日 ＞＝システム日付
					if (empHist.getPeriod().start().beforeOrEquals(now) && empHist.getPeriod().end().afterOrEquals(now)) {
						emplManage.setEmploymentCode(empHist.getEmploymentCode());
						return emplManage;
					}
				}
			}
		}
		// Step 管理区分、雇用コードを返す
		return emplManage;
	}
	
	// Step 管理区分設定を取得する
	public ComSubstVacation getClassifiedManagementSetup(String compId, String empCode) {
		// Step ドメインモデル「雇用振休管理設定」を取得
		Optional<ComSubstVacation> optComSubData = comSubstVacationRepository.findById(compId);
		// Step 取得した「振休管理設定」をチェック
		if (!optComSubData.isPresent()) {
			// Step ドメインモデル「振休管理設定」を取得
			Optional<EmpSubstVacation> optEmpSubData = empSubstVacationRepository.findById(compId, empCode);
			// Step ドメインモデル「雇用振休管理設定」を返す
			return new ComSubstVacation(optEmpSubData.get().getCompanyId(), optEmpSubData.get().getSetting());
		} else {
			// Step 取得したドメインモデル「振休管理設定」を返す
			return optComSubData.get();
		}
	}	
	
	// Step 月初の振休残数を取得
	public double getNumberOfRemainingHolidays(String empId) {
		// Step ドメインモデル「振出管理データ」を取得
		List<PayoutManagementData> payoutManagementData = payoutManagementDataRepository.getByStateAtr(empId, DigestionAtr.UNUSED);
		// Step 取得した「振出管理データ」の未使用日数を合計
		double totalUnUseDays = 0;
		for (PayoutManagementData py : payoutManagementData) {
			totalUnUseDays += py.getUnUsedDays().v();
		}
		// Step ドメインモデル「振休管理データ」を取得
		List<SubstitutionOfHDManagementData> substitutionOfHDManagementData = substitutionOfHDManaDataRepository
				.getByRemainDays(empId, 0);
		// Step 取得した「振休管理データ」の未相殺日数を合計
		double remainDays = 0;
		for (SubstitutionOfHDManagementData absComfirmDay : substitutionOfHDManagementData) {
			remainDays += absComfirmDay.getRemainDays().v();
		}
		// Step 振休発生数合計－振休使用数合計を返す
		return totalUnUseDays - remainDays;
	}	
	
	public Double getNumberOfDayLeft(String sID) {
		String cid = AppContexts.user().companyId();
		Double totalUnUseDay = 0.0;
		Double  totalUndeliveredDay = 0.0;
		
		List<PayoutManagementData> payoutManagementData = payoutManagementDataRepository.getSidWithCod(cid, sID, 0);
		List<SubstitutionOfHDManagementData> substitutionOfHDManagementData = substitutionOfHDManaDataRepository.getBysiDRemCod(cid, sID);
		
		for (PayoutManagementData item : payoutManagementData) {
			totalUnUseDay += item.getUnUsedDays().v();
		}
		
		for (SubstitutionOfHDManagementData item : substitutionOfHDManagementData) {
			totalUndeliveredDay += item.getRemainDays().v();
		}
		
		return totalUnUseDay - totalUndeliveredDay;
	}
	
	public int getExpirationDate(String sid, String empCD) {
		String cid = AppContexts.user().companyId();
		EmpSubstVacation empSubstVacation;
		ComSubstVacation comSubstVacation;
		int expirationDate = 0;
		
		// get scd
		if (empCD != null) {
			if(empSubstVacationRepository.findById(cid, empCD).isPresent()) {
				empSubstVacation = empSubstVacationRepository.findById(cid, empCD).get();
				expirationDate = empSubstVacation.getSetting().getExpirationDate().value;
			} else if (comSubstVacationRepository.findById(cid).isPresent()){
				comSubstVacation = comSubstVacationRepository.findById(cid).get();
				expirationDate = comSubstVacation.getSetting().getExpirationDate().value;
			}
		} else {
			if (comSubstVacationRepository.findById(cid).isPresent()){
				comSubstVacation = comSubstVacationRepository.findById(cid).get();
				expirationDate = comSubstVacation.getSetting().getExpirationDate().value;
			}
		}
		
		return expirationDate;
	}
	
	// Step 締めIDを取得する
	public Integer getClosureId(String sid, String empCD) {
		String cid = AppContexts.user().companyId();
		Integer closureId = null;
		
		if (empCD != null) {
			if(closureEmploymentRepository.findByEmploymentCD(cid, empCD).isPresent()) {
				closureId = closureEmploymentRepository.findByEmploymentCD(cid, empCD).get().getClosureId();
			}
		}
		 
		return closureId;
	}
	
	// UKDesign.UniversalK.就業.KDM_残数管理 (Quản lý số dư).KDM001_残数管理データの登録 (Đăng ký dữ liệu quản lý số dư).Ａ：振休管理.アルゴリズム.Ａ：振休管理データ抽出処理.振休残数データ情報を作成.振休残数データ情報を作成
	public List<RemainInfoData> getRemainInfoData(List<PayoutManagementData> payoutManagementData, List<SubstitutionOfHDManagementData> substitutionOfHDManagementData, 
			List<PayoutSubofHDManagement> payoutSubofHDManagementLinkToPayout, String empId) {
		// List＜残数データ情報＞を作成
		List<RemainInfoData> lstRemainInfoData = new ArrayList<RemainInfoData>();
		// Input．List<振出管理データ＞をループする
		for (PayoutManagementData itemPayout : payoutManagementData) {
			// Input.List＜振出振休紐付け管理＞を絞り込みする
			List<PayoutSubofHDManagement> listPayoutSub = payoutSubofHDManagementLinkToPayout.stream()
					.filter(item -> item.getAssocialInfo().getOutbreakDay().compareTo(itemPayout.getPayoutDate().getDayoffDate().get()) == 0)
					.collect(Collectors.toList());
			// 	絞り込みした「振出振休紐付け管理」をチェック
			if (!listPayoutSub.isEmpty()) {
				List<GeneralDate> lstDateOfUse = listPayoutSub.stream().map(x -> {
					return x.getAssocialInfo().getDateOfUse();
				}).collect(Collectors.toList());
				// Input．List＜振休管理データ＞を絞り込み
				 List<SubstitutionOfHDManagementData> listSubstitution = substitutionOfHDManagementData.stream()
						.filter(item -> lstDateOfUse.contains(item.getHolidayDate().getDayoffDate().get()))
						.collect(Collectors.toList());
				if(listSubstitution.isEmpty()) {
					// Step ドメインモデル「振休管理データ」を取得
					listSubstitution = substitutionOfHDManaDataRepository
							.getBySidListHoliday(AppContexts.user().companyId(),empId, lstDateOfUse);
				}
				for(SubstitutionOfHDManagementData item :listSubstitution) {
					//	残数データ情報を作成
					RemainInfoData itemRemainInfo = RemainInfoData.builder()
							.accrualDate(itemPayout.getPayoutDate().getDayoffDate())
							.deadLine(Optional.of(itemPayout.getExpiredDate()))
							.occurrenceDay(Optional.of(itemPayout.getOccurredDays().v()))
							.digestionDay(item.getHolidayDate().getDayoffDate())
							.digestionDays(Optional.of(item.getRequiredDays().v()))
							.legalDistinction(Optional.of(itemPayout.getLawAtr().value))
							.occurrenceId(Optional.of(itemPayout.getPayoutId()))
							.digestionId(Optional.of(item.getSubOfHDID()))
							.dayLetf(itemPayout.getExpiredDate().beforeOrEquals(GeneralDate.today())  ? itemPayout.getUnUsedDays().v() : 0.0)
							.usedDay(itemPayout.getExpiredDate().beforeOrEquals(GeneralDate.today()) ? 0.0 : itemPayout.getUnUsedDays().v())
							.usedTime(0)
							.occurrenceHour(Optional.empty())
							.digestionTimes(Optional.empty())
							.remainingHours(Optional.empty())
							.build();
					// List＜残数データ情報＞に作成した「残数データ情報＞を追加
					lstRemainInfoData.add(itemRemainInfo);
				}
				// Input．List<振出振休紐付け管理＞に絞り込みした「振出振休紐付け管理」を除く
				payoutSubofHDManagementLinkToPayout.removeIf(x -> listPayoutSub.contains(x));
				// Input．List＜振休管理データ＞に絞り込みした「振休管理データ」を除く
				final List<SubstitutionOfHDManagementData> listCopy = listSubstitution;
				substitutionOfHDManagementData.removeIf(y -> listCopy.contains(y));
			}else {
				//	残数データ情報を作成
				RemainInfoData itemRemainInfo = RemainInfoData.builder()
						.accrualDate(itemPayout.getPayoutDate().getDayoffDate())
						.deadLine(Optional.of(itemPayout.getExpiredDate()))
						.occurrenceDay(Optional.of(itemPayout.getOccurredDays().v()))
						.digestionDay(Optional.empty())
						.digestionDays(Optional.empty())
						.legalDistinction(Optional.of(itemPayout.getLawAtr().value))
						.occurrenceId(Optional.of(itemPayout.getPayoutId()))
						.digestionId(Optional.empty())
						.dayLetf(itemPayout.getExpiredDate().beforeOrEquals(GeneralDate.today())  ? itemPayout.getUnUsedDays().v() : 0.0)
						.usedDay(itemPayout.getExpiredDate().beforeOrEquals(GeneralDate.today()) ? 0.0 : itemPayout.getUnUsedDays().v())
						.usedTime(0)
						.occurrenceHour(Optional.empty())
						.digestionTimes(Optional.empty())
						.remainingHours(Optional.empty())
						.build();
				// List＜残数データ情報＞に作成した「残数データ情報＞を追加
				lstRemainInfoData.add(itemRemainInfo);
			}
		}
		// List＜振休管理データ＞をループする
		for(SubstitutionOfHDManagementData itemSubstitution: substitutionOfHDManagementData) {
			// List＜振出振休紐付け管理＞を絞り込みする
			List<PayoutSubofHDManagement> lstLinkToPayout =  payoutSubofHDManagementLinkToPayout.stream()
					.filter(item -> item.getAssocialInfo().getDateOfUse().compareTo(itemSubstitution.getHolidayDate().getDayoffDate().get()) == 0)
					.collect(Collectors.toList());
			//	絞り込みした「振出振休紐付け管理」をチェック: あるの場合
			if(!lstLinkToPayout.isEmpty()) {
				List<GeneralDate> lstDayOff = lstLinkToPayout.stream().map(x -> {
					return x.getAssocialInfo().getOutbreakDay();
				}).collect(Collectors.toList());
				//	ドメインモデル「振出管理データ」を取得する
				List<PayoutManagementData> listItemPayout = payoutManagementDataRepository.getByListPayoutDate(AppContexts.user().companyId(), empId
						,lstDayOff);
				//	残数データ情報を作成
				for(PayoutManagementData x : listItemPayout) {
					RemainInfoData itemRemainInfo = RemainInfoData.builder()
							.accrualDate(x.getPayoutDate().getDayoffDate())
							.deadLine(Optional.of(x.getExpiredDate()))
							.occurrenceDay(Optional.of(x.getOccurredDays().v()))
							.digestionDay(itemSubstitution.getHolidayDate().getDayoffDate())
							.digestionDays(Optional.of(itemSubstitution.getRequiredDays().v()))
							.legalDistinction(Optional.of(x.getLawAtr().value))
							.occurrenceId(Optional.of(x.getPayoutId()))
							.digestionId(Optional.of(itemSubstitution.getSubOfHDID()))
							.dayLetf(x.getExpiredDate().beforeOrEquals(GeneralDate.today())  ? x.getUnUsedDays().v() : 0.0)
							.usedDay(x.getExpiredDate().beforeOrEquals(GeneralDate.today()) ? 0.0 : x.getUnUsedDays().v())
							.usedTime(0)
							.occurrenceHour(Optional.empty())
							.digestionTimes(Optional.empty())
							.remainingHours(Optional.empty())
							.build();
					//	List＜残数データ情報＞に作成した残数データ情報を追加
					lstRemainInfoData.add(itemRemainInfo);
				}
			}else {
				//	絞り込みした「振出振休紐付け管理」をチェック: ないの場合
				//	残数データ情報を作成
				RemainInfoData itemRemainInfo = RemainInfoData.builder()
						.accrualDate(Optional.empty())
						.deadLine(Optional.empty())
						.occurrenceDay(Optional.empty())
						.digestionDay(itemSubstitution.getHolidayDate().getDayoffDate())
						.digestionDays(Optional.of(itemSubstitution.getRequiredDays().v()))
						.legalDistinction(Optional.empty())
						.occurrenceId(Optional.empty())
						.digestionId(Optional.of(itemSubstitution.getSubOfHDID()))
						.dayLetf(0.0)
						.usedDay(0.0)
						.usedTime(0)
						.occurrenceHour(Optional.empty())
						.digestionTimes(Optional.empty())
						.remainingHours(Optional.empty())
						.build();
				//	List＜残数データ情報＞に作成した残数データ情報を追加
				lstRemainInfoData.add(itemRemainInfo);
			}
		}
		//	作成したList＜残数データ情報＞を返す
		if(!lstRemainInfoData.isEmpty()) {
			lstRemainInfoData.sort((a, b) -> {
				return  a.getAccrualDate().isPresent() && b.getAccrualDate().isPresent() ?  a.getAccrualDate().get().compareTo(b.getAccrualDate().get()) : 0 ;
			});
		}
		return lstRemainInfoData;
	}
	
	public Optional<PresentClosingPeriodExport> find(String cId, int closureId) {
		Optional<Closure> optClosure = closureRepo.findById(cId, closureId);

		// Check exist and active
		if (!optClosure.isPresent() || optClosure.get().getUseClassification()
				.equals(UseClassification.UseClass_NotUse)) {
			return Optional.empty();
		}

		Closure closure = optClosure.get();

		// Get Processing Ym 処理年月
		YearMonth processingYm = closure.getClosureMonth().getProcessingYm();

		DatePeriod closurePeriod = ClosureService.getClosurePeriod(closureId, processingYm, optClosure);

		// Return
		return Optional.of(PresentClosingPeriodExport.builder().processingYm(processingYm)
				.closureStartDate(closurePeriod.start()).closureEndDate(closurePeriod.end())
				.build());
	}
}
