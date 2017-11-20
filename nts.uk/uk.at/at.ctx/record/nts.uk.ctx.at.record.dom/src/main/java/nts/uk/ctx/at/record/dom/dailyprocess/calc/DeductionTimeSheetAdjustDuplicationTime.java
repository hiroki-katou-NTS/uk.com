package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.List;
import java.util.stream.Collectors;

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
	
	
	public DeductionTimeSheetAdjustDuplicationTime(List<TimeSheetOfDeductionItem> useDedTimeSheet) {
		this.timeSpanList = useDedTimeSheet;
	}
	
	/**
	 * 控除時間の重複部分調整
	 * @param setMethod
	 * @param clockManage
	 * 
	 */
	public List<TimeSheetOfDeductionItem> reCreate(WorkTimeMethodSet setMethod,BreakClockOfManageAtr clockManage){
		List<TimeSheetOfDeductionItem> originCopyList = timeSpanList;
		int processedListNumber = 0;
		while(originCopyList.size() - 1 > processedListNumber) {
			for(int number = 0 ;  number < originCopyList.size()  ; number++) {
				for(int nextNumber = number + 1; nextNumber < originCopyList.size(); nextNumber++) {
					processedListNumber = number;
					if(isDeplicated(originCopyList.get(number).calcrange, originCopyList.get(nextNumber).calcrange)){
						int beforeCorrectSize = originCopyList.size();
						originCopyList = convertFromDeductionItemToList(originCopyList,number,nextNumber, setMethod, clockManage);
						if(originCopyList.size()>beforeCorrectSize) {
							/*追加された*/
							break;
						}
					}
				}
			}
		}
		timeSpanList = originCopyList;
		return originCopyList;
	}
	
	/**
	 * 重複の調整後の値を入れたListを作成
	 * @param originList　編集前の時間帯
	 * @param number 今の要素番号
	 * @param setMethod　 就業時間帯の設定方法
	 * @param clockManage　休憩打刻の時刻管理設定区分
	 * @return 調整後の値を入れたList
	 */
	private List<TimeSheetOfDeductionItem> convertFromDeductionItemToList(List<TimeSheetOfDeductionItem> originList,int number,int nextNumber,WorkTimeMethodSet setMethod,BreakClockOfManageAtr clockManage){
		return replaceListItem(originList,originList.get(number).DeplicateBreakGoOut(originList.get(nextNumber),setMethod,clockManage,true,FluidFixedAtr.FixedWork),number,nextNumber).stream().sorted((first,second) -> first.calcrange.getStart().compareTo(second.calcrange.getStart())).collect(Collectors.toList()
						);
	}
	
	/**
	 * Listの指定した場所の値を調整後の値で置き換える
	 * @param nowList
	 * @param newItems
	 * @param number
	 * @return
	 */
	private List<TimeSheetOfDeductionItem> replaceListItem(List<TimeSheetOfDeductionItem> nowList,List<TimeSheetOfDeductionItem> newItems,int number,int nextNumber ) {
		nowList.remove(number);
		nowList.remove(nextNumber);
		nowList.addAll(newItems);
		return nowList;
	}
	
	/**
	 * 重複チェック
	 * @param nowTimeSpan 
	 * @param nextTimeSpan
	 * @return　重複している
	 */
	private boolean isDeplicated(TimeSpanForCalc nowTimeSpan,TimeSpanForCalc nextTimeSpan) {
		return nowTimeSpan.checkDuplication(nextTimeSpan).isDuplicated();
	}
}
