package nts.uk.screen.com.app.find.ccg005.attendance.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
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
import nts.uk.screen.com.app.find.ccg005.attendance.information.EmpIdParam;
import nts.uk.screen.com.app.find.ccg005.favorite.information.FavoriteSpecifyDto;
import nts.uk.shr.com.context.AppContexts;

/*
 * UKDesign.UniversalK.共通.CCG_メニュートップページ.CCG005_ミニ在席照会.A:ミニ在席照会.メニュー別OCD.表示初期の在席データ.表示初期の在席データ
 */
@Stateless
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

	@Inject
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
		EmpIdParam empId = EmpIdParam.builder()
		.sid(loginSid)
		.pid(loginPid)
		.build();
		List<EmpIdParam> empIds = new ArrayList<>();
		empIds.add(empId);
		List<AttendanceInformationDto> attendanceInformationDtos = attendanceInfoScreenQuery.getAttendanceInformation(empIds, GeneralDate.today(), emojiUsage == 1);
		
		// 4: <call>() [RQ228]個人ID（List）からビジネスネームを取得する
		List<String> listLoginSids = new ArrayList<>();
		listLoginSids.add(loginSid);
		String bussinessName = syEmployeeFnAdapter.getByListSid(listLoginSids).stream().findFirst()
				.map(v -> v.getBussinessName()).orElse(null);

		// 5: <call>() [No.50]ログイン者が担当者か判断する
		boolean isInCharge = roleExportRepo.getWhetherLoginerCharge().isEmployeeCharge();

		// 6: <call>() [No.675]メニューの表示名を取得する
		List<StandardMenuNameQueryImport> listMenuName = new ArrayList<>();
		listMenuName.add(this.createMenu("KAF005", "A", "overworkatr=0"));
		listMenuName.add(this.createMenu("KAF005", "A", "overworkatr=1"));
		listMenuName.add(this.createMenu("KAF005", "A", "overworkatr=2"));
		listMenuName.add(this.createMenu("KAF008", "A", null));
		listMenuName.add(this.createMenu("KAF009", "A", null));
		listMenuName.add(this.createMenu("KAF010", "A", null));
		listMenuName.add(this.createMenu("KAF012", "A", null));
		listMenuName.add(this.createMenu("KAF014", "A", null));
		listMenuName.add(this.createMenu("KAF002", "A", null));
		listMenuName.add(this.createMenu("KAF002", "B", null));
		listMenuName.add(this.createMenu("KAF011", "A", null));
		listMenuName.add(this.createMenu("KAF020", "A", null));
		List<StandardMenuNameImport> menu = menuAdapter.getMenuDisplayName(loginCid, listMenuName); //・メニュー分類　＝　標準　＝　0
		List<ApplicationNameDto> applicationNameDtos =  menu.stream().map(item -> {
			return ApplicationNameDto.builder()
					.appName(item.getDisplayName())
					.appType(this.getAppType(item.getProgramId(), item.getScreenId()))
					.otherType(this.getOtherType(item.getProgramId(), item.getScreenId(), item.getQueryString()))
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
	
	private StandardMenuNameQueryImport createMenu(String programId, String screenId, String queryString) {
		return new StandardMenuNameQueryImport(programId, screenId, Optional.ofNullable(queryString));
	}
	
	private Integer getAppType(String programId, String screenId) {
		String key = (programId+screenId).trim();
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
		case "KAF002B":
			return 7;
		case "KAF011A":
			return 10;
		case "KAF020A":
			return 15;
		default:
			return null;
		}
	}
	
	private Integer getOtherType(String programId, String screenId, String queryString) {
		String key = (programId+screenId+queryString).trim();
		switch (key) {
		case "KAF005Aoverworkatr=0":
		case "KAF002A":
			return 0;
		case "KAF005Aoverworkatr=1":
		case "KAF002B":
			return 1;
		case "KAF005Aoverworkatr=2":
			return 2;
		default:
			return null;
		}
	}
}
