package nts.uk.ctx.at.function.app.find.attendancerecord.export;

import java.math.BigDecimal;
import java.util.ArrayList;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Collections;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.function.dom.attendanceitemframelinking.enums.TypeOfItem;
import nts.uk.ctx.at.function.dom.attendanceitemname.AttendanceItemName;
import nts.uk.ctx.at.function.dom.attendanceitemname.service.AttendanceItemNameDomainService;
import nts.uk.ctx.at.function.dom.attendancetype.AttendanceTypeRepository;
import nts.uk.ctx.at.function.dom.dailyattendanceitem.FormCanUsedForTime;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItem;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttendanceItemRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItemAuthority;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemName;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.DailyAttdItemAuthRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.DailyAttendanceItemRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.service.CompanyDailyItemService;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.MonthlyAttendanceItemUsedRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.MonthlyItemControlByAuthRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.MonthlyItemControlByAuthority;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.service.CompanyMonthlyItemService;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.DailyAttendanceItemUsedRepository;
import nts.uk.ctx.at.function.dom.attendanceitemname.service.AttendanceItemNameService;
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
	
	@Inject
	private CompanyDailyItemService companyDailyItemService;
	
	@Inject
	private CompanyMonthlyItemService companyMonthlyItemService;
	
	/** The daily attendance item used repository. */
	@Inject
	private DailyAttendanceItemUsedRepository dailyAttendanceItemUsedRepository;
	
	/** The monthly attendance item used repository. */
	@Inject
	private MonthlyAttendanceItemUsedRepository monthlyAttendanceItemUsedRepository;
	
	/** The attendance item name service */
	@Inject
	private AttendanceItemNameService attendanceItemNameService;

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
	
	/**
	 * Get daily attendance item attributes
	 * @return List attribute of attendance item
	 */
	public List<AttributeOfAttendanceItemDto> getDailyAttendanceItemAtrs() {
		String companyId = AppContexts.user().companyId();

		// 画面で使用可能な日次勤怠項目を取得する
		List<Integer> attendanceIdList = this.getDailyAttendanceItemsAvaiable(companyId, FormCanUsedForTime.ATTENDANCE_BOOK, TypeOfItem.Daily);
		
		// 日次勤怠項目に対応する名称、属性を取得する 
		List<AttributeOfAttendanceItemDto> result = this.getDailyAttendanceItemNameAndAttr(companyId, attendanceIdList);

		return result;
	}
	
	/**
	 * UKDesign.UniversalK.就業.KWR_帳表.帳票共通アルゴリズム.画面で使用可能な日次勤怠項目を取得する.画面で使用可能な日次勤怠項目を取得する
	 *
	 * @param companyId 会社ID
	 * @param formId 帳票ID
	 * @param type 勤怠項目の種類
	 * @return the daily attendance items avaiable
	 */
	public List<Integer> getDailyAttendanceItemsAvaiable(String companyId, FormCanUsedForTime formId, TypeOfItem type) {
		
		// アルゴリズム「帳票で利用できる日次の勤怠項目を取得する」を実行する Thực hiện thuật toán 「帳票で利用できる日次の勤怠項目を取得する」
		List<Integer> dailyItemUsed = this.dailyAttendanceItemUsedRepository.getAllDailyItemId(companyId,
				BigDecimal.valueOf(formId.value));
		
		// アルゴリズム「使用不可の勤怠項目を除く」を実行する Thực hiện thuật toán 「使用不可の勤怠項目を除く」
		List<Integer> avaiableItem = this.attendanceItemNameService.getAvaiableAttendanceItem(companyId, type, dailyItemUsed);

		return avaiableItem;
		
	}
	
	/**
	 * UKDesign.UniversalK.就業.KWR_帳表.帳票共通アルゴリズム.日次勤怠項目に対応する名称、属性を取得する.日次勤怠項目に対応する名称、属性を取得する
	 * @param companyId
	 * @param attendanceIdList
	 * @return the daily attendance item name and attribute
	 */
	public List<AttributeOfAttendanceItemDto> getDailyAttendanceItemNameAndAttr(String companyId, List<Integer> attendanceIdList) {
		List<AttributeOfAttendanceItemDto> result = new ArrayList<AttributeOfAttendanceItemDto>();
		// 日次の勤怠項目を取得する Nhận daily Attendance items
		List<DailyAttendanceItem> dailyAttendanceItemList = dailyAtRepo.getListById(companyId, attendanceIdList);
		
		// 取得した勤怠項目の件数をチェックする (Check số attendance item đã lấy)
		if (dailyAttendanceItemList.isEmpty()) { //0件の場合
			// 終了状態：取得失敗 (Trạng thái kết thúc : Acquisition failure)
			return Collections.emptyList();
		}

		// アルゴリズム「会社の日次を取得する」を実行する
		List<AttItemName> dailyItems = companyDailyItemService.getDailyItems(
			companyId,
			Optional.of(AppContexts.user().roles().forAttendance()),
			attendanceIdList,
			Collections.emptyList());
		
		// 取得したドメインモデル「日次の勤怠項目」（日次勤怠項目の属性、日次の勤怠項目に関連するマスタの種類、表示番号）と取得したList＜勤怠項目ID、名称＞を結合する
		dailyAttendanceItemList.stream()
			.forEach(item -> {
				AttItemName attendance = dailyItems.stream()
					.filter(attd -> attd.getAttendanceItemId() == item.getAttendanceItemId())
					.findFirst().get();

				result.add(AttributeOfAttendanceItemDto.builder()
					.attendanceItemId(attendance.getAttendanceItemId())
					.attendanceItemName(attendance.getAttendanceItemName())
					.attendanceAtr(item.getDailyAttendanceAtr().value)
					.masterType(item.getMasterType().isPresent() ? item.getMasterType().get().value : null)
					.displayNumbers(item.getDisplayNumber())
					.build());
			});

		// List＜勤怠項目ID、名称、属性、マスタの種類。表示番号＞を渡す
		return result;

	}
	
	/**
	 * Get monthly attendance item attributes
	 * @return List attribute of attendance item
	 */
	public List<AttributeOfAttendanceItemDto> getMonthlyAttendanceItemAtrs() {
		String companyId = AppContexts.user().companyId();
		
		// 画面で使用可能な日次勤怠項目を取得する
		List<Integer> attendanceIdList = this.getMonthlyAttendanceItemsAvaiable(companyId, FormCanUsedForTime.ATTENDANCE_BOOK, TypeOfItem.Monthly);
		
		// 日次勤怠項目に対応する名称、属性を取得する 
		List<AttributeOfAttendanceItemDto> result = this.getMonthlyAttendanceItemNameAndAttr(companyId, attendanceIdList);
		
		return result;
		
	}
	
	/**
	 * UKDesign.UniversalK.就業.KWR_帳表.帳票共通アルゴリズム.画面で使用可能な月次勤怠項目を取得する.画面で使用可能な月次勤怠項目を取得する
	 * @param companyId 会社ID
	 * @param formId 帳票ID
	 * @param type 勤怠項目の種類
	 * @return the monthly attendance items avaiable
	 */
	public List<Integer> getMonthlyAttendanceItemsAvaiable(String companyId, FormCanUsedForTime formId, TypeOfItem type) {
		// アルゴリズム「帳票で利用できる月次の勤怠項目を取得する」を実行する Thực hiện thuật toán 「帳票で利用できる月次の勤怠項目を取得する」
		List<Integer> monthlyItemUsed = this.monthlyAttendanceItemUsedRepository.getAllMonthlyItemId(companyId, formId.value);

		// アルゴリズム「使用不可の勤怠項目を除く」を実行する Thực hiện thuật toán 「使用不可の勤怠項目を除く」
		List<Integer> avaiableItem = this.attendanceItemNameService.getAvaiableAttendanceItem(companyId, type, monthlyItemUsed);
		
		return avaiableItem;
	}
	
	/**
	 * UKDesign.UniversalK.就業.KWR_帳表.帳票共通アルゴリズム.月次勤怠項目に対応する名称、属性を取得する.月次勤怠項目に対応する名称、属性を取得する
	 * @param companyId
	 * @param attendanceIdList
	 * @return list attribute of attendance item
	 */
	public List<AttributeOfAttendanceItemDto> getMonthlyAttendanceItemNameAndAttr(String companyId, List<Integer> attendanceIdList) {
		List<AttributeOfAttendanceItemDto> result = new ArrayList<AttributeOfAttendanceItemDto>();
		// 月次の勤怠項目を取得する Nhận Monthly attendance items
		List<MonthlyAttendanceItem> monthlyAttendanceItemList = monthlyAtRepo.findByAttendanceItemId(companyId, attendanceIdList);
		
		// 取得した勤怠項目の件数をチェックする Check Attendance items đã nhận
		if (monthlyAttendanceItemList.isEmpty()) { //0件の場合
			// 終了状態：取得失敗 Trạng thái kết thúc : Nhận thất bại
			return Collections.emptyList();
		}
		
		// アルゴリズム「会社の日次を取得する」を実行する
		List<AttItemName> monthlyItems = companyMonthlyItemService.getMonthlyItems(
			companyId,
			Optional.of(AppContexts.user().roles().forAttendance()),
			attendanceIdList,
			Collections.emptyList());

		// 取得したドメインモデル「月次の勤怠項目」（月次項目の属性、表示番号）と取得したList＜勤怠項目ID、名称＞を結合する
		monthlyAttendanceItemList.stream()
			.forEach(item -> {
				AttItemName attendance = monthlyItems.stream()
					.filter(attd -> attd.getAttendanceItemId() == item.getAttendanceItemId())
					.findFirst().get();

				result.add(AttributeOfAttendanceItemDto.builder()
					.attendanceItemId(attendance.getAttendanceItemId())
					.attendanceItemName(attendance.getAttendanceItemName())
					.attendanceAtr(item.getMonthlyAttendanceAtr().value)
					.displayNumbers(item.getDisplayNumber())
					.build());
			});

		// List＜勤怠項目ID、名称、属性、表示番号＞を渡す
		return result;
	}

}
