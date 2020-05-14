package nts.uk.ctx.at.shared.dom.remainingnumber.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.LeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveRemainingNumber;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LimitedTimeHdTime;


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
	public void AddLeaveGrantRemainingData(
			LeaveGrantRemainingData leaveGrantRemainingData){
		
		if ( !remNumShiftWorkListOpt.isPresent() ){ // リストが存在しないとき
			remNumShiftWorkListOpt = Optional.of(new ArrayList<RemNumShiftWork>());
		}
		
		// 休暇残数シフトのオブジェクトを作成
		RemNumShiftWork remNumShiftWork 
			= new RemNumShiftWork(leaveGrantRemainingData);
		
		// リストへ追加
		remNumShiftWorkListOpt.get().add(remNumShiftWork);
	}
	
	/**
	 * 合計残数を取得する
	 * @return
	 */
	public Optional<LeaveRemainingNumber> GetTotalRemNum(){
		if ( !remNumShiftWorkListOpt.isPresent() ){ // リストが存在しないとき
			return Optional.empty();
		}
		
		// 残数計算用
		LeaveRemainingNumber totalLeaveRemainingNumber 
			= new LeaveRemainingNumber();
		
		List<RemNumShiftWork> list = remNumShiftWorkListOpt.get();
		
		for(RemNumShiftWork aRemNumShiftWork : list){
			if ( aRemNumShiftWork.getLeaveRemainingNumber().isPresent() ){
				totalLeaveRemainingNumber.add(
					aRemNumShiftWork.getLeaveRemainingNumber().get());
			}
        }

		return Optional.of(totalLeaveRemainingNumber);
	}
	
	public LeaveRemainingTime getContractTime(){
		
		// アルゴリズム「社員の労働条件を取得する」を実行し、契約時間を取得する
				
		
	}
	
	
}
