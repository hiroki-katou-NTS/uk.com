package nts.uk.ctx.at.function.dom.attendanceitemname.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.DailyAttendanceItemAdapter;
import nts.uk.ctx.at.function.dom.adapter.DailyAttendanceItemAdapterDto;
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
import nts.uk.ctx.at.function.dom.dailyattendanceitem.DailyAttendanceItem;
import nts.uk.ctx.at.function.dom.dailyattendanceitem.repository.DailyAttendanceItemNameDomainService;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.BPTimeItemRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.timeitem.BonusPayTimeItem;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItem;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItemRepository;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrame;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameRepository;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.com.i18n.TextResource;

/**
 * 
 * @author nampt set name of attendance item with 2 param
 */
@Stateless
public class AttendanceItemNameDomainServiceImpl implements AttendanceItemNameDomainService {

	@Inject
	private DailyAttendanceItemAdapter dailyAttendanceItemAdapter;

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
	private DailyAttendanceItemNameDomainService dailyAttendanceItemNameDomainService;

	@Inject
	private MonthlyAttendanceItemRepository monthlyAttendanceItemRepository;

	@Override
	public List<AttendanceItemName> getNameOfAttendanceItem(List<Integer> dailyAttendanceItemIds,
			int typeOfAttendanceItem) {
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();

		List<AttendanceItemName> attendanceItemNames = new ArrayList<>();

		/**
		 * this process will get name of Daily AttendanceItem author don't merge
		 * dailyAttendanceItemNameDomainService because
		 * dailyAttendanceItemNameDomainService has been used
		 * 
		 */
		if (typeOfAttendanceItem == 1) {
			List<DailyAttendanceItem> dailyAttendanceItems = this.dailyAttendanceItemNameDomainService
					.getNameOfDailyAttendanceItem(dailyAttendanceItemIds);
			attendanceItemNames = dailyAttendanceItems.stream().map(item -> {
				AttendanceItemName attendanceItemName = new AttendanceItemName();
				attendanceItemName.setAttendanceItemDisplayNumber(item.getAttendanceItemDisplayNumber());
				attendanceItemName.setAttendanceItemId(item.getAttendanceItemId());
				attendanceItemName.setAttendanceItemName(item.getAttendanceItemName());
				attendanceItemName.setFrameCategory(item.getFrameCategory());
				attendanceItemName.setTypeOfAttendanceItem(item.getTypeOfAttendanceItem());
				return attendanceItemName;
			}).collect(Collectors.toList());
		} else {
//			attendanceItemNames = this.getNameOfMonthlyAttendanceItem(companyId, typeOfAttendanceItem, dailyAttendanceItemIds);
			attendanceItemNames = this.getNameOfMonthlyAttendanceItem(companyId, dailyAttendanceItemIds);
		}

		return attendanceItemNames;
	}

	private List<AttendanceItemName> getNameOfMonthlyAttendanceItem(String companyId, List<Integer> dailyAttendanceItemIds) {

		List<MonthlyAttendanceItem> monthlyAttendanceItems = this.monthlyAttendanceItemRepository
				.findByAttendanceItemId(companyId, dailyAttendanceItemIds);
		
		List<DailyAttendanceItemAdapterDto> dailyAttendanceItems = monthlyAttendanceItems.stream().map(item -> {
					String name = item.getAttendanceName().v();
					if (name.indexOf("{#") >= 0) {
						int startLocation = name.indexOf("{");
						int endLocation = name.indexOf("}");
						name = name.replace(name.substring(startLocation, endLocation + 1),
								TextResource.localize(name.substring(startLocation + 2, endLocation)));
					}
					return new DailyAttendanceItemAdapterDto(item.getCompanyId(), item.getAttendanceItemId(), name,
							item.getDisplayNumber(), item.getUserCanUpdateAtr().value,
							item.getMonthlyAttendanceAtr().value, item.getNameLineFeedPosition());
				}).collect(Collectors.toList());

		// 対応するドメインモデル 「勤怠項目と枠の紐付け」 を取得する
		List<AttendanceItemLinking> attendanceItemAndFrameNos = this.attendanceItemLinkingRepository
				.getByAttendanceIdAndType(dailyAttendanceItemIds, TypeOfItem.Monthly);

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
		
		List<Integer> frameNos = attendanceItemAndFrameNos.stream().map(f -> {
			return f.getFrameNo().v();
		}).collect(Collectors.toList());

		if (frameNos.isEmpty()) {
			return dailyAttendanceItems.stream().map(item -> {
				AttendanceItemName attendanceItem = new AttendanceItemName();
				attendanceItem.setAttendanceItemDisplayNumber(item.getDisplayNumber());
				attendanceItem.setAttendanceItemId(item.getAttendanceItemId());
				attendanceItem.setAttendanceItemName(item.getAttendanceName());
				attendanceItem.setFrameCategory(0);
				attendanceItem.setTypeOfAttendanceItem(0);
				return attendanceItem;
			}).collect(Collectors.toList());
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
				.collect(Collectors.toMap(DivergenceTimeAdapterDto::getDivTimeId, x -> x));

		// 任意項目 8
		Map<Integer, OptionalItemImport> optionalItems = this.optionalItemAdapter.findOptionalItem(companyId, frameNos)
				.stream().collect(Collectors.toMap(OptionalItemImport::getOptionalItemNo, x -> x));

		// 特定日 10
		Map<Integer, SpecificDateImport> specificDates = this.specificDateAdapter.getSpecificDate(companyId, frameNos)
				.stream().collect(Collectors.toMap(SpecificDateImport::getSpecificDateItemNo, x -> x));
		
		List<AttendanceItemName> attendanceItemDomainServiceDtos = new ArrayList<>();
		
		dailyAttendanceItems.stream().forEach(item -> {
			AttendanceItemName attendanceDto = new AttendanceItemName();
			attendanceDto.setAttendanceItemDisplayNumber(item.getDisplayNumber());
			attendanceDto.setAttendanceItemId(item.getAttendanceItemId());
			attendanceDto.setAttendanceItemName(item.getAttendanceName());
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
				attendanceDto.setAttendanceItemName(MessageFormat.format(attendanceDto.getAttendanceItemName(),
						optionalItems.get(frameNoOptionalItemMap.get(item.getAttendanceItemId()).getFrameNo().v())
								.getOptionalItemName()));
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
			} else {
				attendanceDto.setFrameCategory(0);
				attendanceDto.setTypeOfAttendanceItem(0);
			}
			attendanceItemDomainServiceDtos.add(attendanceDto);
		});

		return attendanceItemDomainServiceDtos;
	}

}
