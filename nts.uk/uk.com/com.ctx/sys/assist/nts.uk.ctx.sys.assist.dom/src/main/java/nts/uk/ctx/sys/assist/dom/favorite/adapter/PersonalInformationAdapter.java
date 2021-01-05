package nts.uk.ctx.sys.assist.dom.favorite.adapter;

import java.util.HashMap;
import java.util.List;

/*
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.お気に入り.Imported.個人情報を取得する
 *  @author hieutt
 */
public interface PersonalInformationAdapter {
	public HashMap<String, EmployeeBasicImport> getPersonalInformation(List<String> lstSid);
}
