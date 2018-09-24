package nts.uk.ctx.at.function.dom.attendanceitemname.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.text.StringUtil;
import nts.uk.ctx.at.function.dom.adapter.DailyAttendanceItemAdapter;
import nts.uk.ctx.at.function.dom.adapter.DivergenceTimeAdapter;
import nts.uk.ctx.at.function.dom.adapter.DivergenceTimeAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.OptionalItemAdapter;
import nts.uk.ctx.at.function.dom.adapter.OptionalItemImport;
import nts.uk.ctx.at.function.dom.adapter.PremiumItemFuncAdapter;
import nts.uk.ctx.at.function.dom.adapter.PremiumItemFuncAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.SpecificDateAdapter;
import nts.uk.ctx.at.function.dom.adapter.SpecificDateImport;
import nts.uk.ctx.at.function.dom.attendanceitemframelinking.AttendanceItemLinking;
import nts.uk.ctx.at.function.dom.attendanceitemframelinking.enums.TypeOfItem;
import nts.uk.ctx.at.function.dom.attendanceitemframelinking.repository.AttendanceItemLinkingRepository;
import nts.uk.ctx.at.function.dom.attendanceitemname.AttendanceItemName;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.BPTimeItemRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.timeitem.BonusPayTimeItem;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItemRepository;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrame;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameRepository;
import nts.uk.ctx.at.shared.dom.outsideot.OutsideOTSetting;
import nts.uk.ctx.at.shared.dom.outsideot.OutsideOTSettingRepository;
import nts.uk.ctx.at.shared.dom.outsideot.breakdown.OutsideOTBRDItem;
import nts.uk.ctx.at.shared.dom.outsideot.overtime.Overtime;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimes;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayRepository;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameRepository;
import nts.uk.ctx.at.shared.dom.worktype.absenceframe.AbsenceFrame;
import nts.uk.ctx.at.shared.dom.worktype.absenceframe.AbsenceFrameRepository;
import nts.uk.ctx.at.shared.dom.worktype.specialholidayframe.SpecialHolidayFrame;
import nts.uk.ctx.at.shared.dom.worktype.specialholidayframe.SpecialHolidayFrameRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.com.i18n.TextResource;

@Stateless
public class AttendanceItemNameServiceImpl implements AttendanceItemNameService{
	@Inject
	private DailyAttendanceItemAdapter dailyAttendanceItemAdapter;
	
	@Inject
	private MonthlyAttendanceItemRepository monthlyAttendanceItemRepository;
	
	@Inject
	private AttendanceItemLinkingRepository attendanceItemLinkingRepository;

	@Inject
	private DivergenceTimeAdapter divergenceTimeAdapter;

	@Inject
	private PremiumItemFuncAdapter premiumItemFuncAdapter;

	@Inject
	private BPTimeItemRepository bPTimeItemRepository;

	@Inject
	private OvertimeWorkFrameRepository overtimeFrameRepository;

	@Inject
	private OptionalItemAdapter optionalItemAdapter;

	@Inject
	private WorkdayoffFrameRepository workdayoffFrameRepository;

	@Inject
	private SpecificDateAdapter specificDateAdapter;
	
	@Inject
	private OutsideOTSettingRepository outsideOTSettingRepository;
	
	@Inject 
	private AbsenceFrameRepository absenceFrameRepository;
	
	@Inject
	private SpecialHolidayFrameRepository specialHolidayFrameRepo;

	@Inject
	private TotalTimesRepository totalTimesRepository;
	
	@Inject
	private SpecialHolidayRepository specialHolidayRepository;
	
	@Override
	public List<AttendanceItemName> getNameOfAttendanceItem(List<Integer> attendanceItemIds, TypeOfItem type) {
		List<AttendanceItemName> attendanceItems = this.getAttendanceItemName(attendanceItemIds, type);
		// 対応するドメインモデル 「勤怠項目と枠の紐付け」 を取得する
		List<AttendanceItemLinking> attendanceItemAndFrameNos = this.attendanceItemLinkingRepository
				.getFullDataByAttdIdAndType(attendanceItemIds, type);
		return this.getNameOfAttendanceItem(attendanceItems, attendanceItemAndFrameNos);
	}

	@Override
	public List<AttendanceItemName> getNameOfAttendanceItem(List<AttendanceItemName> attendanceItems,
			List<AttendanceItemLinking> attendanceItemAndFrameNos) {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();
		// // get list frame No 0
		Map<Integer, AttendanceItemLinking> frameNoOverTimeMap = attendanceItemAndFrameNos.stream()
				.filter(item -> item.getFrameCategory().value == 0)
				.collect(Collectors.toMap(AttendanceItemLinking::getAttendanceItemId, x -> x));
		// // get list frame No 1
		Map<Integer, AttendanceItemLinking> frameNoOverTimeTransferMap = attendanceItemAndFrameNos.stream()
				.filter(item -> item.getFrameCategory().value == 1)
				.collect(Collectors.toMap(AttendanceItemLinking::getAttendanceItemId, x -> x));
		// get list frame No 2
		Map<Integer, AttendanceItemLinking> frameNoLeaveMap = attendanceItemAndFrameNos.stream()
				.filter(item -> item.getFrameCategory().value == 2)
				.collect(Collectors.toMap(AttendanceItemLinking::getAttendanceItemId, x -> x));
		// get list frame No 3
		Map<Integer, AttendanceItemLinking> frameNoLeaveTransferMap = attendanceItemAndFrameNos.stream()
				.filter(item -> item.getFrameCategory().value == 3)
				.collect(Collectors.toMap(AttendanceItemLinking::getAttendanceItemId, x -> x));
		// get list frame No 4
		Map<Integer, AttendanceItemLinking> frameNoPremiumMap = attendanceItemAndFrameNos.stream()
				.filter(item -> item.getFrameCategory().value == 4)
				.collect(Collectors.toMap(AttendanceItemLinking::getAttendanceItemId, x -> x));
		// get list frame No 5
		Map<Integer, AttendanceItemLinking> frameNoBonusPayMap = attendanceItemAndFrameNos.stream()
				.filter(item -> item.getFrameCategory().value == 5)
				.collect(Collectors.toMap(AttendanceItemLinking::getAttendanceItemId, x -> x));
		// get list frame No 6
		Map<Integer, AttendanceItemLinking> frameNoSpecialBonusPayMap = attendanceItemAndFrameNos.stream()
				.filter(item -> item.getFrameCategory().value == 6)
				.collect(Collectors.toMap(AttendanceItemLinking::getAttendanceItemId, x -> x));
		// get list frame No 8
		Map<Integer, AttendanceItemLinking> frameNoOptionalItemMap = attendanceItemAndFrameNos.stream()
				.filter(item -> item.getFrameCategory().value == 8)
				.collect(Collectors.toMap(AttendanceItemLinking::getAttendanceItemId, x -> x));
		// get list frame No 7
		Map<Integer, AttendanceItemLinking> frameNoDivergenceMap = attendanceItemAndFrameNos.stream()
				.filter(item -> item.getFrameCategory().value == 7)
				.collect(Collectors.toMap(AttendanceItemLinking::getAttendanceItemId, x -> x));
		// get list frame No 10
		Map<Integer, AttendanceItemLinking> frameNoSpecificDateMap = attendanceItemAndFrameNos.stream()
				.filter(item -> item.getFrameCategory().value == 10)
				.collect(Collectors.toMap(AttendanceItemLinking::getAttendanceItemId, x -> x));

		//get list frame No 11
		Map<Integer, AttendanceItemLinking> frameNoOverTimeSettingMap = attendanceItemAndFrameNos.stream()
				.filter(item -> item.getFrameCategory().value == 11)
				.collect(Collectors.toMap(AttendanceItemLinking::getAttendanceItemId, x -> x));
		
		//get list frame No 12
		Map<Integer, AttendanceItemLinking> frameAbsence = attendanceItemAndFrameNos.stream()
				.filter(item -> item.getFrameCategory().value == 12)
				.collect(Collectors.toMap(AttendanceItemLinking::getAttendanceItemId, x -> x));
		
		//get list frame No 13
		Map<Integer, AttendanceItemLinking> frameSpecialHoliday = attendanceItemAndFrameNos.stream()
				.filter(item -> item.getFrameCategory().value == 13)
				.collect(Collectors.toMap(AttendanceItemLinking::getAttendanceItemId, x -> x));

		//get list frame No 14
		Map<Integer, AttendanceItemLinking> frameTotalTimes = attendanceItemAndFrameNos.stream()
				.filter(item -> item.getFrameCategory().value == 14)
				.collect(Collectors.toMap(AttendanceItemLinking::getAttendanceItemId, x -> x));
		
		//get list frame No 15
				Map<Integer, AttendanceItemLinking> specialHoliday15 = attendanceItemAndFrameNos.stream()
						.filter(item -> item.getFrameCategory().value == 15)
						.collect(Collectors.toMap(AttendanceItemLinking::getAttendanceItemId, x -> x));	
				
		List<Integer> frameNos = attendanceItemAndFrameNos.stream().map(f -> {
			return f.getFrameNo().v();
		}).collect(Collectors.toList());

		if (frameNos.isEmpty()) {
			return attendanceItems;
		}
		
		List<OvertimeWorkFrame> overtimeWorkFrames = this.overtimeFrameRepository
				.getOvertimeWorkFrameByFrameNos(companyId, frameNos);
		Function<OvertimeWorkFrame, Integer> function = overTime -> overTime.getOvertimeWorkFrNo().v().intValue();
		// 残業 0 + 残業振替 1
		Map<Integer, OvertimeWorkFrame> overTimes = overtimeWorkFrames.stream().collect(Collectors.toMap(function, x -> x));

		List<WorkdayoffFrame> workdayoffFrames = this.workdayoffFrameRepository
				.getWorkdayoffFrameBy(companyId, frameNos);
		Function<WorkdayoffFrame, Integer> fun = workDay -> workDay.getWorkdayoffFrNo().v().intValue();
		// 休出 2 + 休出振替 3
		Map<Integer, WorkdayoffFrame> leave = workdayoffFrames.stream().collect(Collectors.toMap(fun, x -> x));

		// 割増項目 4
		Map<Integer, PremiumItemFuncAdapterDto> premiumItemnames = this.premiumItemFuncAdapter
				.getPremiumItemName(companyId, frameNos).stream()
				.collect(Collectors.toMap(PremiumItemFuncAdapterDto::getDisplayNumber, x -> x));

		// 加給時間項目 5
		Map<Integer, BonusPayTimeItem> bonusPayTimeItems = this.bPTimeItemRepository
				.getListBonusPayTimeItemName(companyId, frameNos).stream()
				.collect(Collectors.toMap(BonusPayTimeItem::getId, x -> x));

		// 特定加給時間項目 6
		Map<Integer, BonusPayTimeItem> specialBonusPayTimeItem = this.bPTimeItemRepository
				.getListSpecialBonusPayTimeItemName(companyId, frameNos).stream()
				.collect(Collectors.toMap(BonusPayTimeItem::getId, x -> x));

		// 乖離時間 7
		Map<Integer, DivergenceTimeAdapterDto> divergenceTimes = this.divergenceTimeAdapter
				.getDivergenceTimeName(companyId, frameNos).stream()
				.collect(Collectors.toMap(DivergenceTimeAdapterDto::getDivTimeId, x -> x, (x,y)-> x));

		// 任意項目 8
		Map<Integer, OptionalItemImport> optionalItems = this.optionalItemAdapter.findOptionalItem(companyId, frameNos)
				.stream().collect(Collectors.toMap(OptionalItemImport::getOptionalItemNo, x -> x));

		// 特定日 10
		Map<Integer, SpecificDateImport> specificDates = this.specificDateAdapter.getSpecificDate(companyId, frameNos)
				.stream().collect(Collectors.toMap(SpecificDateImport::getSpecificDateItemNo, x -> x));
		
		// 超過時間 : 時間外超過設定 11
		List<OvertimeDto> overtimesSetting ;
		List<OutsideOTBRDItemDto> outsideOTBRDItem;
		Optional<OutsideOTSetting> outsideOTSetting = outsideOTSettingRepository.findById(companyId);
		if(outsideOTSetting.isPresent()) {
			overtimesSetting = outsideOTSetting.get().getOvertimes().stream().map(c->convertFromOvertime(c)).collect(Collectors.toList());
			outsideOTBRDItem = outsideOTSetting.get().getBreakdownItems().stream().map(c->convertToOutsideOTBRDItem(c)).collect(Collectors.toList());
		}else {
			overtimesSetting = new ArrayList<>();
			outsideOTBRDItem = new ArrayList<>();
		}
		
		//　欠勤 12	
		Map<Integer, AbsenceFrame> absenceFrame = this.absenceFrameRepository.findAbsenceFrameByListFrame(companyId, frameNos)
				.stream().collect(Collectors.toMap(AbsenceFrame::getAbsenceFrameNo, x -> x));
		
		//　特別休暇枠  13
		Map<Integer, SpecialHolidayFrame> specialHolidayFrame = this.specialHolidayFrameRepo.findHolidayFrameByListFrame(companyId, frameNos)
				.stream().collect(Collectors.toMap(SpecialHolidayFrame::getSpecialHdFrameNo, x -> x));
		
		// 回数集計 14 
		Map<Integer, TotalTimes> totalTimes = this.totalTimesRepository.getTotalTimesDetailByListNo(companyId, frameNos)
				.stream().collect(Collectors.toMap(TotalTimes::getTotalCountNo, x -> x));
		// 特別休暇 15
		Map<Integer, SpecialHoliday> specialHoliday = new HashMap<>();
		this.specialHolidayRepository.findByListCode(companyId, frameNos).forEach(x->{
			  specialHoliday.put(x.getSpecialHolidayCode().v(), x);
		});

		List<AttendanceItemName> result = new ArrayList<>();

		for (AttendanceItemName item : attendanceItems) {
			AttendanceItemName attendanceDto = new AttendanceItemName();
			attendanceDto.setAttendanceItemDisplayNumber(item.getAttendanceItemDisplayNumber());
			attendanceDto.setAttendanceItemId(item.getAttendanceItemId());
			attendanceDto.setAttendanceItemName(item.getAttendanceItemName());
			attendanceDto.setUserCanUpdateAtr(item.getUserCanUpdateAtr());
			if (frameNoOverTimeMap.containsKey(item.getAttendanceItemId())
					&& overTimes.containsKey(frameNoOverTimeMap.get(item.getAttendanceItemId()).getFrameNo().v())) {
				attendanceDto.setAttendanceItemName(MessageFormat.format(attendanceDto.getAttendanceItemName(),
						overTimes.get(frameNoOverTimeMap.get(item.getAttendanceItemId()).getFrameNo().v())
								.getOvertimeWorkFrName()));
				attendanceDto
						.setFrameCategory(frameNoOverTimeMap.get(item.getAttendanceItemId()).getFrameCategory().value);
				attendanceDto.setTypeOfAttendanceItem(
						frameNoOverTimeMap.get(item.getAttendanceItemId()).getTypeOfAttendanceItem().value);
			} else if (frameNoOverTimeTransferMap.containsKey(item.getAttendanceItemId()) && overTimes
					.containsKey(frameNoOverTimeTransferMap.get(item.getAttendanceItemId()).getFrameNo().v())) {
				attendanceDto.setAttendanceItemName(MessageFormat.format(attendanceDto.getAttendanceItemName(),
						overTimes.get(frameNoOverTimeTransferMap.get(item.getAttendanceItemId()).getFrameNo().v())
								.getTransferFrName()));
				attendanceDto.setFrameCategory(
						frameNoOverTimeTransferMap.get(item.getAttendanceItemId()).getFrameCategory().value);
				attendanceDto.setTypeOfAttendanceItem(
						frameNoOverTimeTransferMap.get(item.getAttendanceItemId()).getTypeOfAttendanceItem().value);
			} else
			if (frameNoLeaveMap.containsKey(item.getAttendanceItemId())
					&& leave.containsKey(frameNoLeaveMap.get(item.getAttendanceItemId()).getFrameNo().v())) {
				attendanceDto.setAttendanceItemName(MessageFormat.format(attendanceDto.getAttendanceItemName(), leave
						.get(frameNoLeaveMap.get(item.getAttendanceItemId()).getFrameNo().v()).getWorkdayoffFrName()));
				attendanceDto
						.setFrameCategory(frameNoLeaveMap.get(item.getAttendanceItemId()).getFrameCategory().value);
				attendanceDto.setTypeOfAttendanceItem(
						frameNoLeaveMap.get(item.getAttendanceItemId()).getTypeOfAttendanceItem().value);
			} else if (frameNoLeaveTransferMap.containsKey(item.getAttendanceItemId())
					&& leave.containsKey(frameNoLeaveTransferMap.get(item.getAttendanceItemId()).getFrameNo().v())) {
				attendanceDto.setAttendanceItemName(MessageFormat.format(attendanceDto.getAttendanceItemName(),
						leave.get(frameNoLeaveTransferMap.get(item.getAttendanceItemId()).getFrameNo().v())
								.getTransferFrName()));
				attendanceDto.setFrameCategory(
						frameNoLeaveTransferMap.get(item.getAttendanceItemId()).getFrameCategory().value);
				attendanceDto.setTypeOfAttendanceItem(
						frameNoLeaveTransferMap.get(item.getAttendanceItemId()).getTypeOfAttendanceItem().value);
			} else if (frameNoDivergenceMap.containsKey(item.getAttendanceItemId()) && divergenceTimes
					.containsKey(frameNoDivergenceMap.get(item.getAttendanceItemId()).getFrameNo().v())) {
				attendanceDto.setAttendanceItemName(MessageFormat.format(attendanceDto.getAttendanceItemName(),
						divergenceTimes.get(frameNoDivergenceMap.get(item.getAttendanceItemId()).getFrameNo().v())
								.getDivTimeName()));
				attendanceDto.setFrameCategory(
						frameNoDivergenceMap.get(item.getAttendanceItemId()).getFrameCategory().value);
				attendanceDto.setTypeOfAttendanceItem(
						frameNoDivergenceMap.get(item.getAttendanceItemId()).getTypeOfAttendanceItem().value);
			} else if (frameNoPremiumMap.containsKey(item.getAttendanceItemId()) && premiumItemnames
					.containsKey(frameNoPremiumMap.get(item.getAttendanceItemId()).getFrameNo().v())) {
				attendanceDto.setAttendanceItemName(MessageFormat.format(attendanceDto.getAttendanceItemName(),
						premiumItemnames.get(frameNoPremiumMap.get(item.getAttendanceItemId()).getFrameNo().v())
								.getPremiumItemname()));
				attendanceDto
						.setFrameCategory(frameNoPremiumMap.get(item.getAttendanceItemId()).getFrameCategory().value);
				attendanceDto.setTypeOfAttendanceItem(
						frameNoPremiumMap.get(item.getAttendanceItemId()).getTypeOfAttendanceItem().value);
			} else if (frameNoBonusPayMap.containsKey(item.getAttendanceItemId()) && bonusPayTimeItems
					.containsKey(frameNoBonusPayMap.get(item.getAttendanceItemId()).getFrameNo().v())) {
				attendanceDto.setAttendanceItemName(MessageFormat.format(attendanceDto.getAttendanceItemName(),
						bonusPayTimeItems.get(frameNoBonusPayMap.get(item.getAttendanceItemId()).getFrameNo().v())
								.getTimeItemName().v()));
				attendanceDto
						.setFrameCategory(frameNoBonusPayMap.get(item.getAttendanceItemId()).getFrameCategory().value);
				attendanceDto.setTypeOfAttendanceItem(
						frameNoBonusPayMap.get(item.getAttendanceItemId()).getTypeOfAttendanceItem().value);
			} else if (frameNoSpecialBonusPayMap.containsKey(item.getAttendanceItemId()) && specialBonusPayTimeItem
					.containsKey(frameNoSpecialBonusPayMap.get(item.getAttendanceItemId()).getFrameNo().v())) {
				attendanceDto.setAttendanceItemName(MessageFormat.format(attendanceDto.getAttendanceItemName(),
						specialBonusPayTimeItem
								.get(frameNoSpecialBonusPayMap.get(item.getAttendanceItemId()).getFrameNo().v())
								.getTimeItemName().v()));
				attendanceDto.setFrameCategory(
						frameNoSpecialBonusPayMap.get(item.getAttendanceItemId()).getFrameCategory().value);
				attendanceDto.setTypeOfAttendanceItem(
						frameNoSpecialBonusPayMap.get(item.getAttendanceItemId()).getTypeOfAttendanceItem().value);
			} else if (frameNoOptionalItemMap.containsKey(item.getAttendanceItemId()) && optionalItems
					.containsKey(frameNoOptionalItemMap.get(item.getAttendanceItemId()).getFrameNo().v())) {
				// get value
				OptionalItemImport optItem = optionalItems
						.get(frameNoOptionalItemMap.get(item.getAttendanceItemId()).getFrameNo().v());
				String unit = StringUtil.isNullOrEmpty(optItem.getOptionalItemUnit(), true) ? ""
						: "（" + optItem.getOptionalItemUnit() + "）";
				// set value
				attendanceDto.setAttendanceItemName(MessageFormat.format(attendanceDto.getAttendanceItemName(),
						optItem.getOptionalItemName() + unit));
				attendanceDto.setFrameCategory(
						frameNoOptionalItemMap.get(item.getAttendanceItemId()).getFrameCategory().value);
				attendanceDto.setTypeOfAttendanceItem(
						frameNoOptionalItemMap.get(item.getAttendanceItemId()).getTypeOfAttendanceItem().value);
			} else if (frameNoSpecificDateMap.containsKey(item.getAttendanceItemId()) && specificDates
					.containsKey(frameNoSpecificDateMap.get(item.getAttendanceItemId()).getFrameNo().v())) {
				attendanceDto.setAttendanceItemName(MessageFormat.format(attendanceDto.getAttendanceItemName(),
						specificDates.get(frameNoSpecificDateMap.get(item.getAttendanceItemId()).getFrameNo().v())
								.getSpecificName()));
				attendanceDto.setFrameCategory(
						frameNoSpecificDateMap.get(item.getAttendanceItemId()).getFrameCategory().value);
				attendanceDto.setTypeOfAttendanceItem(
						frameNoSpecificDateMap.get(item.getAttendanceItemId()).getTypeOfAttendanceItem().value);
			//11
			} else if (frameNoOverTimeSettingMap.containsKey(item.getAttendanceItemId())) {
				String overTimeName = "";
				String outsideOTBRDItemName = "";
				for(OvertimeDto ovt: overtimesSetting) {
					if(ovt.getOvertimeNo() == frameNoOverTimeSettingMap.get(item.getAttendanceItemId()).getFrameNo().v()) {
						overTimeName = ovt.getName();
						break;
					}
				}
				for(OutsideOTBRDItemDto oso: outsideOTBRDItem) {
					if(frameNoOverTimeSettingMap.get(item.getAttendanceItemId()).getPreliminaryFrameNO() == null)
						break;
					if(!frameNoOverTimeSettingMap.get(item.getAttendanceItemId()).getPreliminaryFrameNO().isPresent())
						break;
					if(oso.getBreakdownItemNo() == frameNoOverTimeSettingMap.get(item.getAttendanceItemId()).getPreliminaryFrameNO().get().v()) {
						outsideOTBRDItemName = oso.getName();
						break;
					}
				}
				
				attendanceDto.setAttendanceItemName(MessageFormat.format(
						attendanceDto.getAttendanceItemName(),
						overTimeName,
						outsideOTBRDItemName));
				attendanceDto.setFrameCategory(
						frameNoOverTimeSettingMap.get(item.getAttendanceItemId()).getFrameCategory().value);
				attendanceDto.setTypeOfAttendanceItem(
						frameNoOverTimeSettingMap.get(item.getAttendanceItemId()).getTypeOfAttendanceItem().value);
			//12	
			} else if (frameAbsence.containsKey(item.getAttendanceItemId()) && absenceFrame
					.containsKey(frameAbsence.get(item.getAttendanceItemId()).getFrameNo().v())) {
				attendanceDto.setAttendanceItemName(MessageFormat.format(attendanceDto.getAttendanceItemName(),
						absenceFrame.get(frameAbsence.get(item.getAttendanceItemId()).getFrameNo().v())
								.getAbsenceFrameName().v()));
				attendanceDto.setFrameCategory(
						frameAbsence.get(item.getAttendanceItemId()).getFrameCategory().value);
				attendanceDto.setTypeOfAttendanceItem(
						frameAbsence.get(item.getAttendanceItemId()).getTypeOfAttendanceItem().value);
			//13
			}else if (frameSpecialHoliday.containsKey(item.getAttendanceItemId()) && specialHolidayFrame
					.containsKey(frameSpecialHoliday.get(item.getAttendanceItemId()).getFrameNo().v())) {
				attendanceDto.setAttendanceItemName(MessageFormat.format(attendanceDto.getAttendanceItemName(),
						specialHolidayFrame.get(frameSpecialHoliday.get(item.getAttendanceItemId()).getFrameNo().v())
								.getSpecialHdFrameName().v()));
				attendanceDto.setFrameCategory(
						frameSpecialHoliday.get(item.getAttendanceItemId()).getFrameCategory().value);
				attendanceDto.setTypeOfAttendanceItem(
						frameSpecialHoliday.get(item.getAttendanceItemId()).getTypeOfAttendanceItem().value);
			//14
			}else if (frameTotalTimes.containsKey(item.getAttendanceItemId()) && totalTimes
					.containsKey(frameTotalTimes.get(item.getAttendanceItemId()).getFrameNo().v())) {
				attendanceDto.setAttendanceItemName(MessageFormat.format(attendanceDto.getAttendanceItemName(),
						totalTimes.get(frameTotalTimes.get(item.getAttendanceItemId()).getFrameNo().v())
						.getTotalTimesName().v()));
				attendanceDto.setFrameCategory(
						frameTotalTimes.get(item.getAttendanceItemId()).getFrameCategory().value);
				attendanceDto.setTypeOfAttendanceItem(
						frameTotalTimes.get(item.getAttendanceItemId()).getTypeOfAttendanceItem().value);
			//15
			}else if (specialHoliday15.containsKey(item.getAttendanceItemId())) {
				if (specialHoliday.containsKey(specialHoliday15.get(item.getAttendanceItemId()).getFrameNo().v())) {
					attendanceDto.setAttendanceItemName(MessageFormat.format(attendanceDto.getAttendanceItemName(),
							specialHoliday.get(specialHoliday15.get(item.getAttendanceItemId()).getFrameNo().v())
									.getSpecialHolidayName().v()));
				} else {
					attendanceDto.setAttendanceItemName(MessageFormat.format("特別休暇{0}",
							specialHoliday15.get(item.getAttendanceItemId()).getFrameNo().v()));
				}
				attendanceDto.setFrameCategory(
						specialHoliday15.get(item.getAttendanceItemId()).getFrameCategory().value);
				attendanceDto.setTypeOfAttendanceItem(
						specialHoliday15.get(item.getAttendanceItemId()).getTypeOfAttendanceItem().value);
			} else {
				attendanceDto.setFrameCategory(0);
				attendanceDto.setTypeOfAttendanceItem(0);
			}
			result.add(attendanceDto);
		};

		return result;
	}
	
	private OvertimeDto convertFromOvertime(Overtime overtime) {
		return new OvertimeDto(
				overtime.isSuperHoliday60HOccurs(), 
				overtime.getUseClassification().value,
				overtime.getName().v(), 
				overtime.getOvertime().v(), 
				overtime.getOvertimeNo().value);
	}
	
	private OutsideOTBRDItemDto convertToOutsideOTBRDItem(OutsideOTBRDItem outsideOTBRDItem) {
		return new OutsideOTBRDItemDto(
				outsideOTBRDItem.getUseClassification().value,
				outsideOTBRDItem.getBreakdownItemNo().value, 
				outsideOTBRDItem.getName().v(),
				outsideOTBRDItem.getProductNumber().value, 
				outsideOTBRDItem.getAttendanceItemIds());
	}
	
	private List<AttendanceItemName> getAttendanceItemName(List<Integer> attendanceItemIds, TypeOfItem type){
		String companyId = AppContexts.user().companyId();
		List<AttendanceItemName> attendanceItemList = new ArrayList<>();

		switch (type) {
		case Daily:
			attendanceItemList = this.dailyAttendanceItemAdapter.getDailyAttendanceItem(companyId, attendanceItemIds)
					.stream().map(item -> {
						AttendanceItemName dto = new AttendanceItemName();
						dto.setAttendanceItemId(item.getAttendanceItemId());
						dto.setAttendanceItemName(this.formatName(item.getAttendanceName()));
						dto.setAttendanceItemDisplayNumber(item.getDisplayNumber());
						dto.setUserCanUpdateAtr(item.getUserCanUpdateAtr());
						dto.setNameLineFeedPosition(item.getNameLineFeedPosition());
						return dto;
					}).collect(Collectors.toList());
			break;
		case Monthly:
			attendanceItemList = this.monthlyAttendanceItemRepository
					.findByAttendanceItemId(companyId, attendanceItemIds).stream().map(item -> {
						AttendanceItemName dto = new AttendanceItemName();
						dto.setAttendanceItemId(item.getAttendanceItemId());
						dto.setAttendanceItemName(this.formatName(item.getAttendanceName().v()));
						dto.setAttendanceItemDisplayNumber(item.getDisplayNumber());
						dto.setUserCanUpdateAtr(item.getUserCanUpdateAtr().value);
						dto.setNameLineFeedPosition(item.getNameLineFeedPosition());
						return dto;
					}).collect(Collectors.toList());
			break;
		default:
			break;
		}

		return attendanceItemList;
	}
	
	private String formatName(String name) {
		if (name.indexOf("{#") >= 0) {
			int startLocation = name.indexOf("{");
			int endLocation = name.indexOf("}");
			name = name.replace(name.substring(startLocation, endLocation + 1),
					TextResource.localize(name.substring(startLocation + 2, endLocation)));
		}
		return name;
	}
}
