package nts.uk.ctx.at.shared.dom.remainingnumber.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.LeaveGrantRemainingData;


/**
 * 休暇残数シフトリストWORK
 * @author masaaki_jinno
 */
public class RemNumShiftListWork {

	/**
	 * 休暇残数シフトのリスト
	 */
	private Optional<List<RemNumShiftWork>> remNumShiftWorkListOpt;
	
	/**
	 * 休暇付与残数データを追加する
	 * @param leaveGrantRemainingData　休暇付与残数データ
	 */
	public void AddLeaveGrantRemainingData(LeaveGrantRemainingData leaveGrantRemainingData){
		
		if ( !remNumShiftWorkListOpt.isPresent() ){ // リストが存在しないとき
			remNumShiftWorkListOpt = Optional.of(new ArrayList<RemNumShiftWork>());
		}
		
		// 休暇残数シフトのオブジェクトを作成
		RemNumShiftWork remNumShiftWork = new RemNumShiftWork();
		remNumShiftWork.setRefLeaveGrantRemainingData(leaveGrantRemainingData);
		
		// リストへ追加
		remNumShiftWorkListOpt.get().add(remNumShiftWork);
		
	}
	
	/**
	 * 合計残数を取得する
	 * @return
	 */
	public void GetTotalRemNum(){
		if ( !remNumShiftWorkListOpt.isPresent() ){ // リストが存在しないとき
			return;
		}
		
		List<RemNumShiftWork> list = remNumShiftWorkListOpt.get();
		
		for(int i = 0; i < list.size(); i++) {
            
			
			
        }
		
		

	}
	
	
}
