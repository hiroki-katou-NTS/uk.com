package nts.uk.ctx.sys.assist.dom.favorite.adapter;

import java.util.HashMap;
import java.util.List;

import nts.arc.time.GeneralDate;

/*
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.お気に入り.Imported.職場情報を取得する
 *  @author hieutt
 */
public interface WorkplaceInforAdaptor {
	public HashMap<String, WorkplaceInforImport> getWorkplaceInfor(List<String> lstWorkplaceID, GeneralDate baseDate);
}
