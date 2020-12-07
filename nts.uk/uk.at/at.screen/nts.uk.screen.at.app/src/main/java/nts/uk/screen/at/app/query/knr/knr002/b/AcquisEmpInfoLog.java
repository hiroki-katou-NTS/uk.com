package nts.uk.screen.at.app.query.knr.knr002.b;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * UKDesign.UniversalK.就業.KNR_就業情報端末.KNR002_就業情報端末の監視.Ｂ：就業情報端末のログ表示.メニュー別OCD.Ｂ：就業情報端末のログ情報の取得
 * 
 * @author xuannt
 *
 */

@Stateless
public class AcquisEmpInfoLog {
	//@Inject
	// 就業情報端末通信異常期間Repository.[4] 期間で取得する

	// 期間で取得する
	public List<AcquisEmpInfoLogDto> getInPeriod(String empInfoTerCode, String sTime, String eTime) {
		//ContractCode contractCode = new ContractCode(AppContexts.user().contractCode());
		List<AcquisEmpInfoLogDto> acquisitionEmpInfoLog = new ArrayList<AcquisEmpInfoLogDto>();
		return acquisitionEmpInfoLog;
	}
}
