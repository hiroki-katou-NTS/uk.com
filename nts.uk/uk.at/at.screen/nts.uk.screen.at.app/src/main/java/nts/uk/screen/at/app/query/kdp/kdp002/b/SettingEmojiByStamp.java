package nts.uk.screen.at.app.query.kdp.kdp002.b;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.health.dom.emoji.manage.EmojiStateMng;
import nts.uk.ctx.health.dom.emoji.manage.EmojiStateMngRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 打刻入力でニコニコマークの表示設定を取得する
 * UKDesign.UniversalK.就業.KDP_打刻.KDP002_打刻入力(個人打刻).B:打刻結果確認.メニュー別OCD.打刻入力でニコニコマークの表示設定を取得する.打刻入力でニコニコマークの表示設定を取得する
 * 
 * @author chungnt
 *
 */

@Stateless
public class SettingEmojiByStamp {
	
	@Inject
	private EmojiStateMngRepository emojiStateMngRepository;

	public boolean getSettingEmoji() {
		
		boolean result = false;
		
		String cid = AppContexts.user().companyId();
		
		Optional<EmojiStateMng> emojiStateMng = this.emojiStateMngRepository.getByCid(cid);
		
		if (!emojiStateMng.isPresent()) {
			return result;
		}
		
		result = emojiStateMng.get().getManageEmojiState().value == 0 ? false : true;
		
//		if (emojiStateMng.get().getEmojiStateSetting().getEmijiName().v().equals("")){
//			result = false;
//		}
		
		return result;
	}
}
