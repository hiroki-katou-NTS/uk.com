package nts.uk.ctx.office.dom.favorite.adapter;

import java.util.List;
import java.util.Map;

import nts.arc.time.GeneralDate;

/*
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.お気に入り.Imported.職場情報を取得する
 *  @author hieutt
 */
public interface WorkplaceInforAdapter {
	public Map<String, WorkplaceInforImport> getWorkplaceInfor(List<String> lstWorkplaceId, GeneralDate baseDate);
}
