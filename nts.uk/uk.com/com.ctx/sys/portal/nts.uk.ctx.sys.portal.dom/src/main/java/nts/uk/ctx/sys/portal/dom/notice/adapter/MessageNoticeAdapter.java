package nts.uk.ctx.sys.portal.dom.notice.adapter;

import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.ポータル.お知らせ.Imported.社員IDから職場情報を取得する
 * @author DungDV
 *
 */
public interface MessageNoticeAdapter {
	
	/**
	 * 社員IDから職場IDを取得する
	 * @param sid 社員ID
	 * @param baseDate 基準日
	 * @return 職場ID
	 */
	Optional<String> getWpId(String sid, GeneralDate baseDate);
}
