package nts.uk.ctx.at.record.dom.adapter.employmentinfoterminal.infoterminal;

import java.util.List;

/**
 * 
 * @author xuannt
 *	UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.就業情報端末.端末情報.Import.社員IDListから管理情報を取得する
 *
 */
public interface GetMngInfoFromEmpIDListAdapter {
	
	//	取得する(List<社員IDList: 社員ID>): List<社員データ>
	List<EmpDataImport> getEmpData(List<String> empIDList); 

}
