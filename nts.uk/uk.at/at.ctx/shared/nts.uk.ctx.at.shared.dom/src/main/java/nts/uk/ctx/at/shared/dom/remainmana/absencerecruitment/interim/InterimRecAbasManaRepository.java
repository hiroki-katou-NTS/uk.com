package nts.uk.ctx.at.shared.dom.remainmana.absencerecruitment.interim;

import java.util.Optional;

public interface InterimRecAbasManaRepository {
	/**
	 * 暫定振出管理データ
	 * @param recId
	 * @return
	 */
	Optional<InterimRecMana> getReruitmentById(String recId);
	
	//暫定振休管理データ
	Optional<InterimAbsMana> getAbsById(String absId);
	
}
