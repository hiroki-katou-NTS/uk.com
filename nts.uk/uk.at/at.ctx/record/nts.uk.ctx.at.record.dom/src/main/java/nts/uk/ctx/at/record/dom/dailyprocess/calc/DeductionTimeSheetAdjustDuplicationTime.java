package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.RestClockManageAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDailyAtr;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeMethodSet;

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
	public List<TimeSheetOfDeductionItem> reCreate(WorkTimeMethodSet setMethod,RestClockManageAtr clockManage,WorkTimeDailyAtr workTimeDailyAtr){
		List<TimeSheetOfDeductionItem> originCopyList = timeSpanList;
		int processedListNumber = 0;
		//分割処理などを行っている現在処理中の時間帯中でどの時間帯を処理しているかチェック(末尾までいったらこの処理を終える) 
		while(originCopyList.size() - 1 > processedListNumber) {
			//手前の時間帯の位置
			for(int number = 0 ;  number < originCopyList.size()  ; number++) {
				//現在、どのリストNoまで処理が進んでいるかの位置を保持
				processedListNumber = number;
				//手前の時間帯の位置を保持
				int beforeCorrectSize = originCopyList.size();
				//比較対象の時間帯の位置
				for(int nextNumber = number + 1; nextNumber < originCopyList.size(); nextNumber++) {
					if(originCopyList.get(number).calcrange.checkDuplication(originCopyList.get(nextNumber).calcrange).isDuplicated()){
						originCopyList = convertFromDeductionItemToList(originCopyList,number,nextNumber, setMethod, clockManage,workTimeDailyAtr);
						if(originCopyList.size()>beforeCorrectSize)
							break;
					}
				}
				//分割などで元の時間帯より数が増えたらループを頭からやり直す
				if(originCopyList.size()>beforeCorrectSize) {
					break;
				}
			}
		}
		this.timeSpanList = originCopyList;
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
	private List<TimeSheetOfDeductionItem> convertFromDeductionItemToList(List<TimeSheetOfDeductionItem> originList,int number,int nextNumber,WorkTimeMethodSet setMethod,RestClockManageAtr clockManage,WorkTimeDailyAtr workTimeDailyAtr){
		return replaceListItem(originList,
							   originList.get(number).DeplicateBreakGoOut(originList.get(nextNumber),setMethod,clockManage,true,FluidFixedAtr.FixedWork,workTimeDailyAtr)
							   ,number,nextNumber);
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
		nowList.remove(nextNumber-1);
		nowList.addAll(newItems);
		return nowList.stream().sorted((first,second) -> first.calcrange.getStart().compareTo(second.calcrange.getStart())).collect(Collectors.toList());
	}
}
