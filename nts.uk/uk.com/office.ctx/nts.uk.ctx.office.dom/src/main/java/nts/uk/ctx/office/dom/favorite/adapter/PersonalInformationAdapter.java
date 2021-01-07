package nts.uk.ctx.office.dom.favorite.adapter;

import java.util.List;
import java.util.Map;

/*
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.お気に入り.Imported.個人情報を取得する
 *  @author hieutt
 */
public interface PersonalInformationAdapter {
	public Map<String, EmployeeBasicImport> getPersonalInformation(List<String> lstSid);
}
