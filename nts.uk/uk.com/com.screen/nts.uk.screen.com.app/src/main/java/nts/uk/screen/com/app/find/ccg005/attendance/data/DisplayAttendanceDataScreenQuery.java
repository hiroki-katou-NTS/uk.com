package nts.uk.screen.com.app.find.ccg005.attendance.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.employeebasic.SyEmployeeFnAdapter;
import nts.uk.ctx.at.function.dom.adapter.standardmenu.StandardMenuAdaptor;
import nts.uk.ctx.at.function.dom.adapter.standardmenu.StandardMenuNameImport;
import nts.uk.ctx.at.function.dom.adapter.standardmenu.StandardMenuNameQueryImport;
import nts.uk.ctx.health.dom.emoji.manage.EmojiStateMng;
import nts.uk.ctx.health.dom.emoji.manage.EmojiStateMngRepository;
import nts.uk.ctx.office.dom.favorite.FavoriteSpecify;
import nts.uk.ctx.office.dom.favorite.FavoriteSpecifyRepository;
import nts.uk.ctx.sys.auth.pub.role.RoleExportRepo;
import nts.uk.screen.com.app.find.ccg005.attendance.information.AttendanceInformationDto;
import nts.uk.screen.com.app.find.ccg005.attendance.information.AttendanceInformationScreenQuery;
import nts.uk.screen.com.app.find.ccg005.favorite.information.FavoriteSpecifyDto;
import nts.uk.shr.com.context.AppContexts;

/*
 * UKDesign.UniversalK.共通.CCG_メニュートップページ.CCG005_ミニ在席照会.A:ミニ在席照会.メニュー別OCD.表示初期の在席データ.表示初期の在席データ
 */
public class DisplayAttendanceDataScreenQuery {

	@Inject
	private EmojiStateMngRepository emojiRepo;

	@Inject
	private FavoriteSpecifyRepository favoriteRepo;

	@Inject
	private StandardMenuAdaptor menuAdapter;

	@Inject
	private SyEmployeeFnAdapter syEmployeeFnAdapter;

	@Inject
	private RoleExportRepo roleExportRepo;

	private AttendanceInformationScreenQuery attendanceInfoScreenQuery;
	public DisplayAttendanceDataDto getDisplayAttendanceData() {
		String loginSid = AppContexts.user().employeeId();
		String loginCid = AppContexts.user().companyId();
		String loginPid = AppContexts.user().personId();

		// 1: get(ログイン会社ID): Optional<感情状態管理>
		Optional<EmojiStateMng> emoji = emojiRepo.getByCid(loginCid);
		Integer emojiUsage = emoji.map(i -> i.getManageEmojiState().value).orElse(0);
		
		// 2: get(社員ID): List<お気に入りの指定>
		List<FavoriteSpecify> listFavorites = favoriteRepo.getBySid(loginSid);
		List<FavoriteSpecifyDto> listFavoritesDto = listFavorites.stream()
				.map(mapper -> {
					FavoriteSpecifyDto dto = FavoriteSpecifyDto.builder().build();
					mapper.setMemento(dto);
					return dto;
				}).collect(Collectors.toList());
		
		// 3: 在席情報を取得する(社員ID, 年月日, するしない区分): List<在席情報DTO>
		List<String> loginSidList = new ArrayList<>();
		loginSidList.add(loginSid);
		List<String> loginPidList = new ArrayList<>();
		loginPidList.add(loginPid);
		List<AttendanceInformationDto> attendanceInformationDtos = attendanceInfoScreenQuery.getAttendanceInformation(loginSidList, loginPidList, GeneralDate.today(), emojiUsage == 1);
		
		// 4: <call>() [RQ228]個人ID（List）からビジネスネームを取得する
		List<String> listLoginPids = new ArrayList<>();
		listLoginPids.add(loginPid);
		String bussinessName = syEmployeeFnAdapter.getByListSid(listLoginPids).stream().findAny()
				.map(v -> v.getBussinessName()).orElse(null);

		// 5: <call>() [No.50]ログイン者が担当者か判断する
		boolean isInCharge = roleExportRepo.getWhetherLoginerCharge().isEmployeeCharge();

		// 6: <call>() [No.675]メニューの表示名を取得する
		List<StandardMenuNameQueryImport> listMenuName = new ArrayList<>();
		listMenuName.add(this.createMenu("KAF005", "A"));
		listMenuName.add(this.createMenu("KAF006", "A"));
		listMenuName.add(this.createMenu("KAF007", "A"));
		listMenuName.add(this.createMenu("KAF008", "A"));
		listMenuName.add(this.createMenu("KAF009", "A"));
		listMenuName.add(this.createMenu("KAF010", "A"));
		listMenuName.add(this.createMenu("KAF012", "A"));
		listMenuName.add(this.createMenu("KAF014", "A"));
		listMenuName.add(this.createMenu("KAF002", "A"));
		listMenuName.add(this.createMenu("KAF011", "A"));
		listMenuName.add(this.createMenu("KAF020", "A"));
		List<StandardMenuNameImport> menu = menuAdapter.getMenuDisplayName(loginCid, listMenuName); //・メニュー分類　＝　標準　＝　0
		List<ApplicationNameDto> applicationNameDtos =  menu.stream().map(item -> {
			return ApplicationNameDto.builder()
					.appName(item.getDisplayName())
					.appType(this.getAppType(item.getProgramId(), item.getScreenId()))
					.build();
		}).collect(Collectors.toList());
		
		return DisplayAttendanceDataDto.builder()
				.favoriteSpecifyDto(listFavoritesDto)
				.attendanceInformationDtos(attendanceInformationDtos)
				.emojiUsage(emojiUsage)
				.isInCharge(isInCharge)
				.applicationNameDtos(applicationNameDtos)
				.bussinessName(bussinessName)
				.build();
	}
	
	private StandardMenuNameQueryImport createMenu(String programId, String screenId) {
		return new StandardMenuNameQueryImport(programId, screenId, Optional.ofNullable(null));
	}
	
	private Integer getAppType(String programId, String screenId) {
		String key = programId+screenId;
		switch (key) {
		case "KAF005A":
			return 0;
		case "KAF006A":
			return 1;
		case "KAF007A":
			return 2;
		case "KAF008A":
			return 3;
		case "KAF009A":
			return 4;
		case "KAF010A":
			return 6;
		case "KAF012A":
			return 8;
		case "KAF004A":
			return 9;
		case "KAF002A":
			return 7;
		case "KAF011A":
			return 10;
		case "KAF020A":
			return 15;
		default:
			return -1;
		}
	}
}
