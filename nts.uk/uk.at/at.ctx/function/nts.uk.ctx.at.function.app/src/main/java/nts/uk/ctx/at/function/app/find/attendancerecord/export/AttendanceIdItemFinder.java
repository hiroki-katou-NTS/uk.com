package nts.uk.ctx.at.function.app.find.attendancerecord.export;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.function.dom.attendanceitemname.AttendanceItemName;
import nts.uk.ctx.at.function.dom.attendanceitemname.service.AttendanceItemNameDomainService;
import nts.uk.ctx.at.function.dom.attendancetype.AttendanceTypeRepository;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.converter.MonthlyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItem;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItemRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItemAuthority;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.DailyAttdItemAuthRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.DailyAttendanceItemRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.MonthlyItemControlByAuthRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.MonthlyItemControlByAuthority;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AttendanceIdItemFinder {

	// /** The at type. */
	// @Inject
	// private AttendanceTypeDivergenceAdapter atType;

	/** The repository. */
	@Inject
	private AttendanceTypeRepository atTypeRepo;

	/** The at name. */
	@Inject
	private AttendanceItemNameDomainService atName;

	@Inject
	private DailyAttdItemAuthRepository dailyAuRepo;

	@Inject
	private MonthlyItemControlByAuthRepository monthlyAuRepo;

	@Inject
	private DailyAttendanceItemRepository dailyAtRepo;

	@Inject
	private MonthlyAttendanceItemRepository monthlyAtRepo;

	private static final int DAILY = 1;
	private static final int USE = 1;

	/**
	 * Gets the attendance item.
	 *
	 * @param screenUse
	 *            the screen use
	 * @param attendanceType
	 *            the attendance type
	 * @return the attendance item
	 */
	public List<AttendanceIdItemDto> getAttendanceItem(List<Integer> screenUse, int attendanceType) {
		String companyId = AppContexts.user().companyId();

		List<AttendanceIdItemDto> attendanceItemList = new ArrayList<AttendanceIdItemDto>();
		BundledBusinessException exceptions = BundledBusinessException.newInstance();

		// Get RoleID
		String roleId = AppContexts.user().roles().forAttendance();

		// get attendanceId
		screenUse.forEach(item -> {
			attendanceItemList.addAll(atTypeRepo.getItemByAtrandType(companyId, item, attendanceType).stream()
					.map(e -> new AttendanceIdItemDto(e.getAttendanceItemId(), null, item))
					.collect(Collectors.toList()));
		});

		if (attendanceType == DAILY) {
			// Handle for Daily Attendance
			Optional<DailyAttendanceItemAuthority> dailyAuthorOpt;
			DailyAttendanceItemAuthority dailyAuthor;
			List<DailyAttendanceItem> atItemList = new ArrayList<>();
			List<Integer> attendanceIDs = attendanceItemList.stream().map(e -> e.getAttendanceItemId())
					.collect(Collectors.toList());
			if (attendanceIDs.isEmpty()) {
				dailyAuthorOpt = dailyAuRepo.getDailyAttdItem(companyId, roleId);
			} else {
				dailyAuthorOpt = dailyAuRepo.getDailyAttdItemByAttItemId(companyId, roleId, attendanceIDs);
			}

			if (dailyAuthorOpt.isPresent()) {
				dailyAuthor = dailyAuthorOpt.get();
				attendanceIDs = dailyAuthor.getListDisplayAndInputControl().stream().filter(i -> i.isToUse())
						.map(item -> item.getItemDailyID()).collect(Collectors.toList());
			}

			if (attendanceIDs.isEmpty()) {
				atItemList = dailyAtRepo.getList(companyId);
			} else {
				atItemList = dailyAtRepo.getListById(companyId, attendanceIDs);
			}
			if (atItemList.isEmpty()) {
				exceptions.throwExceptions();
			}
			// get attendanceName
			List<AttendanceItemName> dailyAtNameList = atName.getNameOfAttendanceItem(
					atItemList.stream().map(e -> e.getAttendanceItemId()).collect(Collectors.toList()), attendanceType);

			return dailyAtNameList.stream().filter(e -> e.getAttendanceItemName() != null).distinct()
					.sorted(Comparator.comparing(AttendanceItemName::getAttendanceItemId))
					.map(e -> new AttendanceIdItemDto(e.getAttendanceItemId(), e.getAttendanceItemName(), 0))
					.collect(Collectors.toList());
		} else {
			// Handle for Monthly Attendance
			Optional<MonthlyItemControlByAuthority> monthlyAuthorOpt;
			MonthlyItemControlByAuthority monthlyAuthor;
			List<MonthlyAttendanceItem> atItemList = new ArrayList<>();
			List<Integer> itemList = attendanceItemList.stream().map(item -> item.getAttendanceItemId())
					.collect(Collectors.toList());

			if (itemList.isEmpty()) {
				monthlyAuthorOpt = monthlyAuRepo.getMonthlyAttdItem(companyId, roleId);
				if (monthlyAuthorOpt.isPresent()) {
					monthlyAuthor = monthlyAuthorOpt.get();
					itemList = monthlyAuthor.getListDisplayAndInputMonthly().stream().filter(e -> e.isToUse())
							.map(e -> e.getItemMonthlyId()).collect(Collectors.toList());
				}
			} else {
				monthlyAuthorOpt = monthlyAuRepo.getMonthlyAttdItemByUse(companyId, roleId, itemList, USE);
				if (monthlyAuthorOpt.isPresent()) {
					monthlyAuthor = monthlyAuthorOpt.get();
					itemList = monthlyAuthor.getListDisplayAndInputMonthly().stream().map(e -> e.getItemMonthlyId())
							.collect(Collectors.toList());
				}
			}

			if (itemList.isEmpty()) {
				atItemList = monthlyAtRepo.findAll(companyId);
			} else {
				atItemList = monthlyAtRepo.findByAttendanceItemId(companyId, itemList);
			}

			if (atItemList.isEmpty()) {
				exceptions.throwExceptions();
			}
			// get attendanceName
			List<AttendanceItemName> monthlyAtNameList = atName.getNameOfAttendanceItem(
					atItemList.stream().map(e -> e.getAttendanceItemId()).collect(Collectors.toList()), attendanceType);

			return monthlyAtNameList.stream().filter(e -> e.getAttendanceItemName() != null).distinct()
					.sorted(Comparator.comparing(AttendanceItemName::getAttendanceItemId))
					.map(e -> new AttendanceIdItemDto(e.getAttendanceItemId(), e.getAttendanceItemName(), 0))
					.collect(Collectors.toList());

			// map attendanceId,attendanceName,ScreenUseItem
			// attendanceItemList.forEach(attendanceItem -> {
			// for (AttendanceItemName attendanceName : monthlyAtNameList) {
			// if (attendanceItem.getAttendanceItemId() ==
			// attendanceName.getAttendanceItemId())
			// attendanceItem.setAttendanceItemName(attendanceName.getAttendanceItemName());
			// }
			// });

			// return attendanceItemList.stream().filter(e ->
			// e.getAttendanceItemName() != null)
			// .collect(Collectors.toList());
		}
	}
}
