package nts.uk.ctx.at.shared.dom.remainmng.absencerecruitment.interim;

import java.util.Optional;

public interface InterimRecAbasMngRepository {
	/**
	 * 暫定振出管理データ
	 * @param recId
	 * @return
	 */
	Optional<InterimRecMng> getReruitmentById(String recId);
	
	//暫定振休管理データ
	Optional<InterimAbsMng> getAbsById(String absId);
	
}
