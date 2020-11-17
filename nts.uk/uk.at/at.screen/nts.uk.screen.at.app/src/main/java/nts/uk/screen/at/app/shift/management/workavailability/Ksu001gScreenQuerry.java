package nts.uk.screen.at.app.shift.management.workavailability;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.WorkAvailabilityDisplayInfoOfOneDay;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.WorkAvailabilityOfOneDay;
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.WorkAvailabilityOfOneDayRepository;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterCode;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterRepository;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingService;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.internal.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;

/**
 * 初期起動の情報取得
 * UKDesign.UniversalK.就業.KSU_スケジュール.KSU001_個人スケジュール修正(職場別).G：希望参照.メニュー別OCD.初期起動の情報取得.初期起動の情報取得
 * 
 * @author quytb
 *
 */
@Stateless
public class Ksu001gScreenQuerry {
	/** 一日分の勤務希望Repository */
	@Inject
	private WorkAvailabilityOfOneDayRepository availabilityOfOneDayRepository;

	@Inject
	private WorkTypeRepository workTypeRepository;

	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;

	@Inject
	private BasicScheduleService basicService;

	@Inject
	private WorkTimeSettingService workTimeService;

	@Inject
	private ShiftMasterRepository shiftMasterRepo;

	@Inject
	private SyEmployeePub syEmployeePub;

	public List<WorkAvailabilityInfoDto> getListWorkAvailability(List<String> employeeIDs, DatePeriod period) {
		List<WorkAvailabilityDisplayInfoOfOneDay> availabilityDisplayInfoOfOneDays = new ArrayList<WorkAvailabilityDisplayInfoOfOneDay>();
		List<WorkAvailabilityOfOneDay> availabilityOfOneDays = new ArrayList<WorkAvailabilityOfOneDay>();

		/** 1.get(List<社員ID>、対象期間) */
		for (int i = 0; i < employeeIDs.size(); i++) {
			List<WorkAvailabilityOfOneDay> availabilityOfOneDayTmps = availabilityOfOneDayRepository
					.getList(employeeIDs.get(i), period);//
			availabilityOfOneDays = Stream.of(availabilityOfOneDays, availabilityOfOneDayTmps).flatMap(x -> x.stream())
					.collect(Collectors.toList());
		}
		
		/** 2: List<一日分の勤務希望>.size == 0*/
		if (CollectionUtil.isEmpty(availabilityOfOneDays)) {
			return new ArrayList<WorkAvailabilityInfoDto>();
		/** 3: List<一日分の勤務希望>.size >0*/
		} else {
			Require require = new Require(workTypeRepository, workTimeSettingRepository, basicService, workTimeService,
					shiftMasterRepo);
			availabilityOfOneDays.forEach(x -> {
				availabilityDisplayInfoOfOneDays.add(x.getDisplayInformation(require));
			});
			List<WorkAvailabilityInfoDto> dtos = new ArrayList<>();
			availabilityDisplayInfoOfOneDays.forEach(x -> {
				String method = "";
				if (x.getDisplayInfo().getMethod().value == 0) {
					method = TextResource.localize("KSU001_4038");
				}
				if (x.getDisplayInfo().getMethod().value == 1) {
					method = TextResource.localize("KSU001_4035");
				}
				if (x.getDisplayInfo().getMethod().value == 2) {
					method = TextResource.localize("KSU001_4036");
				}

				if (x.getDisplayInfo().getNameList().size() >= 1) {
					for (int i = 0; i < x.getDisplayInfo().getNameList().size(); i++) {
						if (x.getDisplayInfo().getTimeZoneList().size() >= 1) {
							for (int j = 0; j < x.getDisplayInfo().getTimeZoneList().size(); j++) {
								dtos.add(new WorkAvailabilityInfoDto(x.getAvailabilityDate().toString("yyyy/MM/dd"),
										syEmployeePub.getEmpBasicBySId(x.getEmployeeId()).getEmployeeCode() + " "
												+ syEmployeePub.getEmpBasicBySId(x.getEmployeeId()).getBusinessName(),
										method, x.getDisplayInfo().getNameList().get(i),
										x.getDisplayInfo().getTimeZoneList().get(j).getStart().getInDayTimeWithFormat()
												+ TextResource.localize("KSU001_4055") + x.getDisplayInfo().getTimeZoneList().get(j).getEnd()
														.getInDayTimeWithFormat(),
										x.getMemo().v()));
							}
						} else {
							dtos.add(new WorkAvailabilityInfoDto(x.getAvailabilityDate().toString("yyyy/MM/dd"),
									syEmployeePub.getEmpBasicBySId(x.getEmployeeId()).getEmployeeCode() + " "
											+ syEmployeePub.getEmpBasicBySId(x.getEmployeeId()).getBusinessName(),
									method, x.getDisplayInfo().getNameList().get(i),
									"",
									x.getMemo().v()));
						}
					}
				} else {
					if (x.getDisplayInfo().getTimeZoneList().size() >= 1) {
						for (int j = 0; j < x.getDisplayInfo().getTimeZoneList().size(); j++) {
							dtos.add(new WorkAvailabilityInfoDto(x.getAvailabilityDate().toString("yyyy/MM/dd"),
									syEmployeePub.getEmpBasicBySId(x.getEmployeeId()).getEmployeeCode() + " "
											+ syEmployeePub.getEmpBasicBySId(x.getEmployeeId()).getBusinessName(),
									method, "",
									x.getDisplayInfo().getTimeZoneList().get(j).getStart().getInDayTimeWithFormat()
											+ TextResource.localize("KSU001_4055") + x.getDisplayInfo().getTimeZoneList().get(j).getEnd()
													.getInDayTimeWithFormat(),
									x.getMemo().v()));
						}
					} else {
						dtos.add(new WorkAvailabilityInfoDto(x.getAvailabilityDate().toString("yyyy/MM/dd"),
								syEmployeePub.getEmpBasicBySId(x.getEmployeeId()).getEmployeeCode() + " "
										+ syEmployeePub.getEmpBasicBySId(x.getEmployeeId()).getBusinessName(),
								method, "",
								"",
								x.getMemo().v()));
					}
				}
			});
			return dtos;			
		}
	}

	@AllArgsConstructor
	public static class Require implements WorkAvailabilityOfOneDay.Require {

		private WorkTypeRepository workTypeRepository;

		private WorkTimeSettingRepository workTimeSettingRepository;

		private BasicScheduleService basicService;

		private WorkTimeSettingService workTimeService;

		private ShiftMasterRepository shiftMasterRepo;

		@Override
		public Optional<WorkType> findByPK(String workTypeCd) {
			return workTypeRepository.findByDeprecated(AppContexts.user().companyId(), workTypeCd);
		}

		@Override
		public Optional<WorkTimeSetting> findByCode(String workTimeCode) {
			return workTimeSettingRepository.findByCode(AppContexts.user().companyId(), workTimeCode);
		}

		@Override
		public SetupType checkNeededOfWorkTimeSetting(String workTypeCode) {
			return basicService.checkNeededOfWorkTimeSetting(workTypeCode);
		}

		@Override
		public PredetermineTimeSetForCalc getPredeterminedTimezone(String workTimeCd, String workTypeCd,
				Integer workNo) {
			return workTimeService.getPredeterminedTimezone(AppContexts.user().companyId(), workTimeCd, workTypeCd,
					workNo);
		}

		@Override
		public WorkStyle checkWorkDay(String workTypeCode) {
			return basicService.checkWorkDay(workTypeCode);
		}

		@Override
		public Optional<ShiftMaster> getShiftMasterByWorkInformation(WorkTypeCode workTypeCode,
				WorkTimeCode workTimeCode) {
			return shiftMasterRepo.getByWorkTypeAndWorkTime(AppContexts.user().companyId(), workTypeCode.v(),
					workTimeCode.v());
		}

		@Override
		public List<ShiftMaster> getShiftMaster(List<ShiftMasterCode> shiftMasterCodeList) {
			List<String> shiftMaterCodes = shiftMasterCodeList.stream().map(x -> x.v()).collect(Collectors.toList());
			return shiftMasterRepo.getByListShiftMaterCd2(AppContexts.user().companyId(), shiftMaterCodes);
		}
	}
}
