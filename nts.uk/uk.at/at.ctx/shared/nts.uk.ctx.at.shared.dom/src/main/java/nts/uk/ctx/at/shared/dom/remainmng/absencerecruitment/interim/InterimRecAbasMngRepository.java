package nts.uk.ctx.at.shared.dom.remainmng.absencerecruitment.interim;

import java.util.Optional;

public interface InterimRecAbasMngRepository {
	/**
	 * 暫定振出管理データ
	 * @param recId
	 * @return
	 */
	Optional<InterimRecMng> getReruitmentById(String recId);
	
	/**
	 * 暫定振休管理データ
	 * @param absId
	 * @return
	 */
	Optional<InterimAbsMng> getAbsById(String absId);
	/**
	 * ドメインモデル「暫定振出振休紐付け管理」を取得する
	 * @param interimId
	 * @param isRec: True: 振出管理データ, false: 振休管理データ
	 * @return
	 */
	Optional<InterimRecAbsMng> getRecOrAbsMng(String interimId, boolean isRec);
	
}
