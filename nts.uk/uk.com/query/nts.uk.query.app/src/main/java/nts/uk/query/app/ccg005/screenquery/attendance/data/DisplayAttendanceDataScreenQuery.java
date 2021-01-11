package nts.uk.query.app.ccg005.screenquery.attendance.data;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.standardmenu.StandardMenuAdaptor;
import nts.uk.ctx.health.dom.emoji.manage.EmojiStateMng;
import nts.uk.ctx.health.dom.emoji.manage.EmojiStateMngRepository;
import nts.uk.ctx.office.dom.favorite.FavoriteSpecify;
import nts.uk.ctx.office.dom.favorite.FavoriteSpecifyRepository;
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
	
	public DisplayAttendanceDataDto getDisplayAttendanceData() {
		//1: get(ログイン会社ID): Optional<感情状態管理>
		String loginCid = AppContexts.user().companyId();
		Optional<EmojiStateMng> emoji = emojiRepo.getByCid(loginCid);
		
		//2: get(社員ID): List<お気に入りの指定>
		String loginSid = AppContexts.user().employeeId();
		 List<FavoriteSpecify> listFavorites = favoriteRepo.getBySid(loginSid);
		//TODO 3: 在席情報を取得する(社員ID, 年月日, するしない区分): List<在席情報DTO>
		 
		//TODO 4: <call>() [RQ618]個人ID（List）からビジネスネームを取得する
		 
		//TODO 5: <call>() [No.50]ログイン者が担当者か判断する
		 
		//TODO 6: <call>() [No.675]メニューの表示名を取得する
		return null;
	}
}
