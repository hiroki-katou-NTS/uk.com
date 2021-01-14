package nts.uk.screen.com.app.find.ccg005.attendance.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.employeebasic.EmployeeInfoImport;
import nts.uk.ctx.at.function.dom.adapter.employeebasic.SyEmployeeFnAdapter;
import nts.uk.ctx.at.function.dom.adapter.standardmenu.StandardMenuAdaptor;
import nts.uk.ctx.at.function.dom.adapter.standardmenu.StandardMenuNameQueryImport;
import nts.uk.ctx.health.dom.emoji.manage.EmojiStateMng;
import nts.uk.ctx.health.dom.emoji.manage.EmojiStateMngRepository;
import nts.uk.ctx.office.dom.favorite.FavoriteSpecify;
import nts.uk.ctx.office.dom.favorite.FavoriteSpecifyRepository;
import nts.uk.ctx.sys.auth.pub.role.RoleExportRepo;
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

	public DisplayAttendanceDataDto getDisplayAttendanceData() {

		String loginCid = AppContexts.user().companyId();
		String loginPid = AppContexts.user().personId();

		// 1: get(ログイン会社ID): Optional<感情状態管理>
		Optional<EmojiStateMng> emoji = emojiRepo.getByCid(loginCid);
		Integer emojiUsage = emoji.map(i -> i.getManageEmojiState().value).orElse(0);
		// 2: get(社員ID): List<お気に入りの指定>
		String loginSid = AppContexts.user().employeeId();
		List<FavoriteSpecify> listFavorites = favoriteRepo.getBySid(loginSid);
		List<FavoriteSpecifyDto> listFavoritesDto = listFavorites.stream()
				.map(mapper -> {
					FavoriteSpecifyDto dto = FavoriteSpecifyDto.builder().build();
					mapper.setMemento(dto);
					return dto;
				}).collect(Collectors.toList());
		// TODO 3: 在席情報を取得する(社員ID, 年月日, するしない区分): List<在席情報DTO>

		// 4: <call>() [RQ228]個人ID（List）からビジネスネームを取得する
		List<String> listLoginPids = new ArrayList<>();
		listLoginPids.add(loginPid);
		String bussinessName = syEmployeeFnAdapter.getByListSid(listLoginPids).stream().findAny()
				.map(v -> v.getBussinessName()).orElse(null);

		// 5: <call>() [No.50]ログイン者が担当者か判断する
		boolean isInCharge = roleExportRepo.getWhetherLoginerCharge().isEmployeeCharge();

		// TODO 6: <call>() [No.675]メニューの表示名を取得する
		List<StandardMenuNameQueryImport> listMenuName = new ArrayList<>();
		menuAdapter.getMenuDisplayName(loginCid, listMenuName);
		return DisplayAttendanceDataDto.builder()
				.favoriteSpecifyDto(listFavoritesDto)
				//
				.emojiUsage(emojiUsage)
				.isInCharge(isInCharge)
				//
				.bussinessName(bussinessName)
				.build();
	}
}
