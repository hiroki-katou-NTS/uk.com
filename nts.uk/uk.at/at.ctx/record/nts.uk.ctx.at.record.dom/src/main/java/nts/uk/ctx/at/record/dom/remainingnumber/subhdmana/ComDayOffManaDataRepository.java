package nts.uk.ctx.at.record.dom.remainingnumber.subhdmana;

import java.util.List;

public interface ComDayOffManaDataRepository {
	
	/**
	 * ドメインモデル「代休管理データ」を取得
	 * @param sid
	 * @return
	 */
	// 社員ID=パラメータ「社員ID」
	// 未相殺日数>0.0
	List<CompensatoryDayOffManaData> getBySidWithReDay(String cid, String sid);
	
	List<CompensatoryDayOffManaData> getBySid(String cid, String sid);
	
	void create(CompensatoryDayOffManaData domain);
}
