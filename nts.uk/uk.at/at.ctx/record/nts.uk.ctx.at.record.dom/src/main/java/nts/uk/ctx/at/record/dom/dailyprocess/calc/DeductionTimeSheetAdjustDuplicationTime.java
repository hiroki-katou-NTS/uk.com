package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeMethodSet;
import nts.uk.ctx.at.shared.dom.worktime.fluidworkset.fluidbreaktimeset.BreakClockOfManageAtr;

/**
 * 控除時間帯の重複部分を調整する処理を担うクラス
 * @author keisuke_hoshina
 *
 */
public class DeductionTimeSheetAdjustDuplicationTime {
	@Getter
	@Setter
	private List<TimeSheetOfDeductionItem> timeSpanList;
	
	private DeductionTimeSheetAdjustDuplicationTime(List<TimeSheetOfDeductionItem> useDedTimeSheet) {
		this.timeSpanList = useDedTimeSheet;
	}
	/**
	 * Constructor
	 * @param useDedTimeSheet
	 */
	public DeductionTimeSheetAdjustDuplicationTime createTimeSpanList(List<TimeSheetOfDeductionItem> useDedTimeSheet) {
		 return new DeductionTimeSheetAdjustDuplicationTime(useDedTimeSheet); 
	}
	
	/**
	 * 控除時間の重複部分調整
	 * @param setMethod
	 * @param clockManage
	 * @return
	 */
	public List<TimeSheetOfDeductionItem> reCreate(WorkTimeMethodSet setMethod,BreakClockOfManageAtr clockManage){
		List<TimeSheetOfDeductionItem> originCopyList = timeSpanList;
		List<TimeSheetOfDeductionItem> toReturnCopyList = new ArrayList<TimeSheetOfDeductionItem>();
		
		while(originCopyList.size() > toReturnCopyList.size()) {
			for(int number = 0 ;  number < originCopyList.size() ; number++) {
				if(number > 0) {
					originCopyList = toReturnCopyList;
					toReturnCopyList.clear();
				}
				if(isDeplicated(originCopyList.get(number).calculationTimeSheet, originCopyList.get(number+1).calculationTimeSheet)){
					toReturnCopyList.addAll(convertFromTimeSpanToDeductionItem(originCopyList.get(number), originCopyList.get(number + 1), setMethod, clockManage));
					if(toReturnCopyList.size()>number) {
						/*追加された*/
						toReturnCopyList.addAll(originCopyList.subList(number + 2, originCopyList.size()));
						break;
					}
					else if(toReturnCopyList.size() < number) {
						/*削除された*/
					}
				}
				else {
					toReturnCopyList.add(originCopyList.get(number));
				}
			}

		}
		return toReturnCopyList;
	}
	
	public List<TimeSheetOfDeductionItem> convertFromTimeSpanToDeductionItem(TimeSheetOfDeductionItem nowItem,TimeSheetOfDeductionItem nextItem,WorkTimeMethodSet setMethod,BreakClockOfManageAtr clockManage){
		return nowItem.DeplicateBreakGoOut(nextItem,setMethod,clockManage);
	}
	
	/**
	 * アクセスしようとしている場所の調整(配列の最大要素数を超えていた場合最大数に減らす)
	 * @param upperlimit 配列の要素数
	 * @param now　アクセスしようとしている場所
	 * @return 調整後の値
	 */
	public int adjustUpperLimit(int upperlimit,int now) {
		return (upperlimit > now)?now:upperlimit;
	}
	
	/**
	 * 重複チェック
	 * @param nowTimeSpan
	 * @param nextTimeSpan
	 * @return　重複している
	 */
	public boolean isDeplicated(TimeSpanForCalc nowTimeSpan,TimeSpanForCalc nextTimeSpan) {
		return nowTimeSpan.checkDuplication(nextTimeSpan).isDuplicated();
	}
	
	private enum adjustItemAtr{
		now,
		next;
		
		public boolean isNow(){
			return next.equals(this);
		}
	}
}
