package nts.uk.screen.at.app.kdw013.a;

import javax.ejb.Stateless;

/**
 * UKDesign.UniversalK.就業.KDW_日別実績.KDW013_工数入力.A:工数入力.メニュー別OCD.対象社員の表示情報を取得する
 * @author tutt
 *
 */
@Stateless
public class GetEmployeeDisplayInfo {
	
	public void getInfo() {
		//1: 取得する(@Require, 社員ID, 年月日):List<作業グループ>
		
		//2: get(ログイン会社ID)
		
		//3: [作業変更可能期間設定.isPresent]:作業修正可能開始日付を取得する(@Require, 社員ID):年月日
		
		//4: <call>(社員ID,基準日)
		
		//5: <call>(社員ID,表示期間)
	}
}
