package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;

/**
 * 控除時間帯の重複部分を調整する処理を担うクラス
 * @author keisuke_hoshina
 *
 */
public class DeductionTimeSheetAdjustDuplicationTime {
	@Getter
	@Setter
	private List<TimeSpanForCalc> timeSpanList;
	
	private DeductionTimeSheetAdjustDuplicationTime(List<TimeSheetOfDeductionItem> useDedTimeSheet) {
		useDedTimeSheet.forEach(tc -> timeSpanList.add(tc.calculationTimeSheet));
	}
	/**
	 * Constructor
	 * @param useDedTimeSheet
	 */
	public DeductionTimeSheetAdjustDuplicationTime createTimeSpanList(List<TimeSheetOfDeductionItem> useDedTimeSheet) {
		 return new DeductionTimeSheetAdjustDuplicationTime(useDedTimeSheet); 
	}
	
	public List<TimeSpanForCalc> adjust(){
		for(???) {
			if(timeSpanList.get(0).checkDuplication(timeSpanList.get(1)).isDuplicated()){
				
			}
			else {
				
			}
		}
	}
	
	
	/**
	 * n番目の時間帯を修正する(完全に被っていない前提)
	 * @param nowListNumber
	 */
	public void moveFirstItem(int nowListNumber) {
		timeSpanList.set(nowListNumber, reCreateTimeSpan(nowListNumber,adjustItemAtr.now));
	}
	
	/**
	 * n+1番目の時間帯を修正する(完全に被っていない前提)
	 * @param nextListNumber
	 */
	public void moveSecondItem(int nextListNumber) {
		timeSpanList.set(nextListNumber, reCreateTimeSpan(nextListNumber,adjustItemAtr.next));
	}
	
	
	/**
	 * 時間帯を被らないように補正する
	 * @param listNumber
	 * @param itemAtr
	 * @return
	 */
	public TimeSpanForCalc reCreateTimeSpan(int listNumber,adjustItemAtr itemAtr) {
		if(itemAtr.isNow()) {
			return timeSpanList.get(listNumber).getNotDuplicationWith(timeSpanList.get(listNumber + 1)).get();
		}
		else {
			return timeSpanList.get(listNumber + 1).getNotDuplicationWith(timeSpanList.get(listNumber)).get();
		}
	}
	
	
	/**
	 * 指定した場所に要素が存在するかの判定(Listの中にNULLが無いという前提)
	 * @param ListNumber チェックしたい要素の番号(1～リストの長さ)
	 * @return 存在する
	 */
	public boolean existNextItem(int ListNumber) {
		return timeSpanList.size()>=ListNumber;
	}
	
	private enum adjustItemAtr{
		now,
		next;
		
		public boolean isNow(){
			return next.equals(this);
		}
	}
}
