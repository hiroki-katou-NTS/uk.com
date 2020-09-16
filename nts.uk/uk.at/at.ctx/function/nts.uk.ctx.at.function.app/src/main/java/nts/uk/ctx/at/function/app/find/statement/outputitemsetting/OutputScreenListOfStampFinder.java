package nts.uk.ctx.at.function.app.find.statement.outputitemsetting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationAdapter;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationImport;
import nts.uk.ctx.at.function.dom.adapter.annualworkschedule.EmployeeInformationQueryDtoImport;
import nts.uk.ctx.at.function.dom.dailyworkschedule.scrA.RoleExportRepoAdapter;
import nts.uk.ctx.at.function.dom.holidaysremaining.repository.PermissionOfEmploymentFormRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocation;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.RefectActualResult;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampDakokuRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecord;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecordRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.EmployeeStampInfo;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.GetListStampEmployeeService;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.RetrieveNoStampCardRegisteredService;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampInfoDisp;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.shr.com.company.CompanyAdapter;
import nts.uk.shr.com.company.CompanyInfor;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;

/**
 * 
 * @author HieuLT
 *
 */
@Stateless
public class OutputScreenListOfStampFinder {

	@Inject
	private CompanyAdapter company;

	@Inject
	private StampCardRepository stampCardRepository;

	@Inject
	private StampRecordRepository stampRecordRepository;

	@Inject
	private StampDakokuRepository stampDakokuRepository;

	@Inject
	private EmployeeInformationAdapter employeeInformationAdapter;

	@Inject
	private WorkLocationRepository workLocationRepository;

	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;

	@Inject
	private PermissionOfEmploymentFormRepository permissionOfEmploymentFormRepository;

	@Inject
	private RoleExportRepoAdapter roleExportRepoAdapter;
	
	@Inject
	private ClosureRepository closureRepo;
	
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepo;
	
	@Inject
	private ShareEmploymentAdapter shareEmploymentAdapter;
	
	private static final Integer FUNCTION_NO = 5;

	// 起動する(khởi động)
	public OutputScreenListOfStampDto initScreen() {

		String employeeID = AppContexts.user().employeeId();
		GeneralDate ymd = GeneralDate.today();
		String roleId = AppContexts.user().roles().forAttendance();
		OutputScreenListOfStampDto result = new OutputScreenListOfStampDto();
		if (roleExportRepoAdapter.getRoleWhetherLogin().isEmployeeCharge()) {
			result.setExistAuthEmpl(true);
		} else {
			result.setExistAuthEmpl(false);
		}

		// [RQ622]会社IDから会社情報を取得する
		CompanyInfor companyInfo = company.getCurrentCompany().orElseGet(() -> {
			throw new RuntimeException("System Error: Company Info");
		});
		// 社員に対応する締め期間を取得する(Lấy closurePeriod ứng với employee)
		DatePeriod period = ClosureService.findClosurePeriod(
				ClosureService.createRequireM3(closureRepo, closureEmploymentRepo, shareEmploymentAdapter),
				new CacheCarrier(), employeeID, ymd);
		result.setCompanyCode(companyInfo.getCompanyCode());
		result.setCompanyName(companyInfo.getCompanyName());
		result.setStartDate(period.start());
		result.setEndDate(period.end());

		return result;
	}

	//// 社員別打刻一覧表を作成す Create a list of stamps for each employee
	public List<EmployeEngravingInfor> createListOfStampEmpQuery(DatePeriod datePerriod, List<String> listEmp) {
		List<EmployeEngravingInfor> result = new ArrayList<>();

		// 1取得する(@Require, 社員ID, 年月日) :社員の打刻情報
		List<EmployeeStampInfo> listEmployeeStampInfo = new ArrayList<>();
		List<String> listWorkLocationCode = new ArrayList<>();

		GetListStampEmployeeService.Require require = new RequireImpl(stampCardRepository, stampRecordRepository,
				stampDakokuRepository);
		for (GeneralDate date : datePerriod.datesBetween()) {
			for (String employeeId : listEmp) {
				Optional<EmployeeStampInfo> optEmployeeStampInfo = GetListStampEmployeeService.get(require, employeeId,
						date);
				if (optEmployeeStampInfo.isPresent()) {
					val employeeStampInfo = optEmployeeStampInfo.get();
					listEmployeeStampInfo.add(employeeStampInfo);

					// Get distinct WorkLocationCD
					val listStempInfoDisp = employeeStampInfo.getListStampInfoDisp();
					for (val stampInfoDisp : listStempInfoDisp) {
						val optStamp = stampInfoDisp.getStamp();
						if (!optStamp.isEmpty()) {
							val workLocationCD = optStamp.get(0).getRefActualResults().getWorkLocationCD();
							if (workLocationCD.isPresent())
								listWorkLocationCode.add(workLocationCD.get().v());
						}
					}
				}
			}
		}
		List<String> listEmployeeId = listEmployeeStampInfo.stream().map(c -> c.getEmployeeId())
				.collect(Collectors.toList());

		// 2 <call> List＜社員の打刻情報＞.社員ID :List<社員情報>
		// <<Public>> 社員の情報を取得する
		List<EmployeeInformationImport> listEmpInfo = employeeInformationAdapter
				.getEmployeeInfo(new EmployeeInformationQueryDtoImport(listEmployeeId, datePerriod.end(), true, false,
						true, false, false, false));

		// 3 get* List<社員の打刻情報>．勤務場所コード : List< 勤務場所>
		List<WorkLocation> listWorkLocation = workLocationRepository.findByCodes(AppContexts.user().companyId(),
				listWorkLocationCode);

		// 4 get* List<社員の打刻情報>.就業時間帯コード : List< 就業時間帯>
		List<RefectActualResult> listRefectActualResult = listEmployeeStampInfo.stream().flatMap(c -> {
			List<RefectActualResult> stampInfos = c.getListStampInfoDisp().stream().map(t -> t.getStamp())
					.filter(s -> !s.isEmpty()).map(r -> r.get(0).getRefActualResults()).collect(Collectors.toList());
			return stampInfos.stream();
		}).collect(Collectors.toList());
		List<WorkTimeCode> listWorkTime = listRefectActualResult.stream().map(c -> c.getWorkTimeCode())
				.filter(t -> t.isPresent()).distinct().map(g -> g.get()).collect(Collectors.toList());
		List<String> listWorkTimeCode = listWorkTime.stream().map(c -> c.v()).collect(Collectors.toList());
		List<WorkTimeSetting> listWorkTimeSetting = workTimeSettingRepository
				.getListWorkTimeSetByListCode(AppContexts.user().companyId(), listWorkTimeCode);

		for (val item : listEmployeeStampInfo) {
			EmployeeInformationImport empInfo = listEmpInfo.stream()
					.filter(c -> c.getEmployeeId().equals(item.getEmployeeId())).findFirst().orElse(null);

			// StampInfoDisp
			for (val stampInfoDisp : item.getListStampInfoDisp()) {
				EmployeEngravingInfor employeEngravingInfor = new EmployeEngravingInfor();
				if(stampInfoDisp.getStamp().isEmpty()){
					employeEngravingInfor
					.setWorkplaceCd((empInfo != null) ? empInfo.getWorkplace().getWorkplaceCode() : "");
			employeEngravingInfor
					.setWorkplaceName((empInfo != null) ? empInfo.getWorkplace().getWorkplaceName() : "");
			employeEngravingInfor.setEmployeeCode((empInfo != null) ? empInfo.getEmployeeCode() : "");
			employeEngravingInfor.setEmployeeName((empInfo != null) ? empInfo.getBusinessName() : "");
			employeEngravingInfor.setDateAndTime(stampInfoDisp.getStampDatetime().toString());
			employeEngravingInfor.setAttendanceAtr(stampInfoDisp.getStampAtr());
			employeEngravingInfor.setStampMeans("");
			employeEngravingInfor.setAuthcMethod("");
			employeEngravingInfor.setInstallPlace("");
			employeEngravingInfor.setLocalInfor("");
			employeEngravingInfor.setCardNo(stampInfoDisp.getStampNumber().v());
			employeEngravingInfor.setSupportCard("");
			employeEngravingInfor.setWorkTimeDisplayName("");
			result.add(employeEngravingInfor);
				}
				if (stampInfoDisp.getStamp().isEmpty())
					continue;
				val stamp = stampInfoDisp.getStamp().get(0);

				String local = "";
				String optSupportCard = "";
				String workLocationName = "";
				String workTimeName = "";
				
				if (stamp.getRefActualResults().getWorkLocationCD().isPresent()) {
					val workLocationCode = stamp.getRefActualResults().getWorkLocationCD().get();
					val optWorkLocation = listWorkLocation.stream()
							.filter(c -> c.getWorkLocationCD().v().equals(workLocationCode.v())).findFirst();

					if (optWorkLocation.isPresent() && workLocationCode.v().trim() != "") {
						workLocationName = optWorkLocation.get().getWorkLocationName().v();
					}
					if (!optWorkLocation.isPresent() && !workLocationCode.v().trim().equals("")) {
						workLocationName = workLocationCode.v() + " " + TextResource.localize("KDP011_50");
					}
				}

				// Local Infor
				if (stamp.getLocationInfor().isPresent()) {
					val localInfo = stamp.getLocationInfor().get();
					if (localInfo.getPositionInfor() == null) {
						local = "";

					} else {
						local = String.format("%.6f", localInfo.getPositionInfor().getLatitude()) + " "
								+ String.format("%.6f", localInfo.getPositionInfor().getLongitude());
					}

				}

				// Support Card
				if (stamp.getRefActualResults().getCardNumberSupport().isPresent()) {
					optSupportCard = stamp.getRefActualResults().getCardNumberSupport().get();
				}
				// WorkTime Name

				val optWorkTimeCode = stamp.getRefActualResults().getWorkTimeCode();
				if (optWorkTimeCode.isPresent() && optWorkTimeCode.get().v().trim() != "") {
					val optWorkTimeSetting = listWorkTimeSetting.stream().filter(c -> optWorkTimeCode.isPresent()
							&& c.getWorktimeCode().v().equals(optWorkTimeCode.get().v())).findFirst();
					// val workTimeName = (optWorkTimeSetting.isPresent()) ?
					// optWorkTimeSetting.get().getWorkTimeDisplayName().getWorkTimeName().v()
					// : optWorkTimeCode.get().v() +" "+
					// TextResource.localize("KDP011_50");
					if (optWorkTimeSetting.isPresent() && optWorkTimeCode.get().v().trim() != "") {
						workTimeName = optWorkTimeSetting.get().getWorkTimeDisplayName().getWorkTimeName().v();
					}
					if (!optWorkTimeSetting.isPresent() && !optWorkTimeCode.get().v().trim().equals("")) {
						workTimeName = optWorkTimeCode.get().v() + " " + TextResource.localize("KDP011_50");
					}
				}

				// Overtime Hour & Late Night Time
				val optOvertimeDeclaration = stamp.getRefActualResults().getOvertimeDeclaration();
				// val overtimeHours = (optOvertimeDeclaration.isPresent()) ?
				// optOvertimeDeclaration.get().getOverTime().v() : 0;
				// lateNightTime = (optOvertimeDeclaration.isPresent()) ?
				// optOvertimeDeclaration.get().getOverLateNightTime().v() : 0;

				// Set data
				employeEngravingInfor
						.setWorkplaceCd((empInfo != null) ? empInfo.getWorkplace().getWorkplaceCode() : "");
				employeEngravingInfor
						.setWorkplaceName((empInfo != null) ? empInfo.getWorkplace().getWorkplaceName() : "");
				employeEngravingInfor.setEmployeeCode((empInfo != null) ? empInfo.getEmployeeCode() : "");
				employeEngravingInfor.setEmployeeName((empInfo != null) ? empInfo.getBusinessName() : "");
				employeEngravingInfor.setDateAndTime(stampInfoDisp.getStampDatetime().toString());
				employeEngravingInfor.setAttendanceAtr(stampInfoDisp.getStampAtr());
				employeEngravingInfor.setStampMeans(stamp.getRelieve().getStampMeans().name);
				employeEngravingInfor.setAuthcMethod(stamp.getRelieve().getAuthcMethod().name);
				employeEngravingInfor.setInstallPlace(workLocationName);
				employeEngravingInfor.setLocalInfor(local);
				employeEngravingInfor.setCardNo(stampInfoDisp.getStampNumber().v());
				employeEngravingInfor.setSupportCard(optSupportCard);
				employeEngravingInfor.setWorkTimeDisplayName(workTimeName);
				if (optOvertimeDeclaration.isPresent()) {
					employeEngravingInfor
							.setOvertimeHours(getTimeString(optOvertimeDeclaration.get().getOverTime().v()));
					employeEngravingInfor
							.setLateNightTime(getTimeString(optOvertimeDeclaration.get().getOverLateNightTime().v()));
				} else {
					employeEngravingInfor.setOvertimeHours("");
					employeEngravingInfor.setLateNightTime("");
				}
				result.add(employeEngravingInfor);
			}
		}

		return result;
	}

	public List<CardNoStampInfo> createCardNoStampQuery(DatePeriod datePerriod) {
		// RetrieveNoStampCardRegisteredService
		// 1取得する(@Require, 期間): 打刻情報リスト
		// 打刻カード未登録の打刻データを取得する
		RetrieveNoStampCardRegisteredService.Require requireCardNo = new RequireCardNoIml(stampRecordRepository,
				stampDakokuRepository);
		List<StampInfoDisp> listStampInfoDisp = RetrieveNoStampCardRegisteredService.get(requireCardNo, datePerriod);
		List<RefectActualResult> listRefectActual = listStampInfoDisp.stream().map(c -> c.getStamp())
				.filter(t -> !t.isEmpty()).distinct().map(g -> g.get(0).getRefActualResults())
				.collect(Collectors.toList());
		// 勤務場所コードリスト = 打刻情報リスト:map $.打刻場所distinct
		List<String> listWorkLocationCd = listRefectActual.stream().map(c -> c.getWorkLocationCD())
				.filter(t -> t.isPresent()).map(g -> g.get().v()).collect(Collectors.toList());

		// 2 打刻情報リスト: List< 勤務場所>
		List<WorkLocation> listWorkLocation = workLocationRepository.findByCodes(AppContexts.user().companyId(),
				listWorkLocationCd);

		// 就業時間帯コードリスト＝打刻情報リスト：map $.就業時間帯コード distinct
		List<WorkTimeCode> listWorkTime = listRefectActual.stream().map(c -> c.getWorkTimeCode())
				.filter(t -> t.isPresent()).distinct().map(g -> g.get()).collect(Collectors.toList());
		List<String> listWorkTimeCode = listWorkTime.stream().map(c -> c.v()).collect(Collectors.toList());

		// 3get 打刻．就業時間帯コード List<就業時間帯>
		List<WorkTimeSetting> listWorkTimeSetting = workTimeSettingRepository
				.getListWorkTimeSetByListCode(AppContexts.user().companyId(), listWorkTimeCode);

		// 打刻情報リスト
		List<CardNoStampInfo> cardNoStampInfos = new ArrayList<>();
		for (StampInfoDisp stampInfoDisp : listStampInfoDisp) {
			CardNoStampInfo cardNoStampInfo = new CardNoStampInfo();
			cardNoStampInfo.setCardNo(stampInfoDisp.getStampNumber().toString());

			// Chuẩn bị data
			String stampMeans = "";
			String authcMethod = "";

			String localInfor = "";
			String supportCard = "";
			String workTimeDisplayName = "";
			String overtimeHours = "";
			String lateNightTime = "";
			String workLocationName = "";
			// String latitude = "";
			// longitude = "";

			if (!stampInfoDisp.getStamp().isEmpty()) {

				stampMeans = stampInfoDisp.getStamp().get(0).getRelieve().getStampMeans().name;
				authcMethod = stampInfoDisp.getStamp().get(0).getRelieve().getAuthcMethod().name;

				val refActualResults = stampInfoDisp.getStamp().get(0).getRefActualResults();
				val workLocationCD = refActualResults.getWorkLocationCD();

				if (workLocationCD.isPresent()) {
					val optWorkLocation = listWorkLocation.stream()
							.filter(c -> c.getWorkLocationCD().v().equals(workLocationCD.get().v())).findFirst();
					if (optWorkLocation.isPresent() && workLocationCD.get().v().trim() != "") {
						workLocationName = optWorkLocation.get().getWorkLocationName().v();
					}
					if (!optWorkLocation.isPresent() && !workLocationCD.get().v().trim().equals("")) {

						workLocationName = workLocationCD.get().v() + " " + TextResource.localize("KDP011_50");
					}

				}

				val locationInfo = stampInfoDisp.getStamp().get(0).getLocationInfor();
				if (locationInfo.isPresent()) {
					val positionInfo = locationInfo.get().getPositionInfor();
					if (positionInfo == null) {
						localInfor = "";
					} else {
						localInfor = String.format("%.6f", positionInfo.getLatitude()) + " "
								+ String.format("%.6f", positionInfo.getLongitude());
					}

				}
				if (refActualResults.getCardNumberSupport().isPresent()) {
					supportCard = refActualResults.getCardNumberSupport().get();
				}
				val workTimeCode = refActualResults.getWorkTimeCode();
				if (workTimeCode.isPresent()) {
					val workTimeDisplayNameCheck = listWorkTimeSetting.stream()
							.filter(c -> c.getWorktimeCode().v().equals(workTimeCode.get().v()))
							.map(c -> c.getWorkTimeDisplayName().getWorkTimeName().v()).findFirst();
					if (workTimeDisplayNameCheck.isPresent() && workTimeCode.get().v().trim() != "") {
						workTimeDisplayName = workTimeDisplayNameCheck.get();
					}
					if (!workTimeDisplayNameCheck.isPresent() && !workTimeCode.get().v().trim().equals("")) {
						workTimeDisplayName = workTimeCode.get().v() + " " + TextResource.localize("KDP011_50");
					}

				}

				val overtimeDeclaration = refActualResults.getOvertimeDeclaration();
				if (overtimeDeclaration.isPresent()) {
					overtimeHours = getTimeString(overtimeDeclaration.get().getOverTime().v());
					lateNightTime = getTimeString(overtimeDeclaration.get().getOverLateNightTime().v());
				} else {
					overtimeHours = "";
					lateNightTime = "";
				}
			}

			// Set data
			cardNoStampInfo.setCardNo(stampInfoDisp.getStampNumber().toString());
			cardNoStampInfo.setDateAndTime(stampInfoDisp.getStampDatetime().toString());
			cardNoStampInfo.setAttendanceAtr(stampInfoDisp.getStampAtr());
			cardNoStampInfo.setStampMeans(stampMeans);
			cardNoStampInfo.setAuthcMethod(authcMethod);
			cardNoStampInfo.setInstallPlace(workLocationName);
			cardNoStampInfo.setLocalInfor(localInfor);
			cardNoStampInfo.setSupportCard(supportCard);
			cardNoStampInfo.setWorkTimeDisplayName(workTimeDisplayName);
			cardNoStampInfo.setOvertimeHours(overtimeHours);
			cardNoStampInfo.setLateNightTime(lateNightTime);
			cardNoStampInfos.add(cardNoStampInfo);
		}

		return cardNoStampInfos;
	}

	public String getTimeString(Integer t) {
		int hours = t / 60;
		int minutes = t % 60;
		return String.format("%02d:%02d", hours, minutes);
	}

	@AllArgsConstructor
	private static class RequireImpl implements GetListStampEmployeeService.Require {
		@Inject
		private StampCardRepository stampCardRepository;
		@Inject
		private StampRecordRepository stampRecordRepository;
		@Inject
		private StampDakokuRepository stampDakokuRepository;

		@Override
		public List<StampCard> getListStampCard(String sid) {
			return stampCardRepository.getListStampCard(sid);
		}

		@Override
		public List<StampRecord> getStampRecord(List<StampNumber> stampNumbers, GeneralDate stampDate) {
			return stampRecordRepository.get(AppContexts.user().contractCode(), stampNumbers, stampDate);
		}

		@Override
		public List<Stamp> getStamp(List<StampNumber> stampNumbers, GeneralDate stampDateTime) {
			return stampDakokuRepository.get(AppContexts.user().contractCode(),stampNumbers, stampDateTime);
		}

	}

	@AllArgsConstructor
	private static class RequireCardNoIml implements RetrieveNoStampCardRegisteredService.Require {

		@Inject
		private StampRecordRepository stampRecordRepo;
		@Inject
		private StampDakokuRepository stampDakokuRepo;

		@Override
		public List<StampRecord> getStempRcNotResgistNumber(DatePeriod period) {

			return stampRecordRepo.getStempRcNotResgistNumber(AppContexts.user().contractCode(), period);

		}

		@Override
		public List<Stamp> getStempRcNotResgistNumberStamp(String contractCode, DatePeriod period) {
			// TODO Auto-generated method stub
			return stampDakokuRepo.getStempRcNotResgistNumberStamp(AppContexts.user().contractCode(),period);
		}	
	}

}
