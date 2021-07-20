package nts.uk.ctx.at.record.dom.workmanagement.workinitselectset;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.history.strategic.PersistentHistory;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.作業管理.作業初期選択設定
 * 作業初期選択履歴
 * 
 * @author HieuLt
 *
 */
@Getter
@AllArgsConstructor
public class TaskInitialSelHist extends AggregateRoot
		implements PersistentHistory<TaskInitialSel, DatePeriod, GeneralDate> {
	/** 社員ID **/
	private final String empId;

	/** 作業初期選択項目 ---履歴リスト **/
	private List<TaskInitialSel> lstHistory;

	/**
	 * [6] 履歴を追加する 説明:新しい作業初期選択項目を追加する
	 **/
	public void addHistory(TaskInitialSel taskInitialSel) {

		// $最新の履歴 = 最新履歴の履歴項目()
		Optional<TaskInitialSel> data = this.latestStartItem();
		// 追加する(追加する履歴項目)
//		List<TaskInitialSel> lstTask = new ArrayList<TaskInitialSel>();
//		lstTask.add(taskInitialSel);
		lstHistory.add(taskInitialSel);

		if (data.isPresent()) {
			// @履歴リスト：except $最新の履歴
			lstHistory.remove(data);
			
			// $最新の履歴.適用による終了の調整(追加する履歴項目)
			data.get().shortenStartToAccept(taskInitialSel);
			//	@履歴リスト.追加($最新の履歴)		
			lstHistory.add(data.get());

		}
	}
	
	/**
	 * [7] 履歴を削除する
	 * immediatelyBefore
	 */
	public void deleteHistory(TaskInitialSel taskInitialSel){
		//	$直前の履歴 = 直前の履歴の履歴項目(追加する履歴項目)	
		Optional<TaskInitialSel> data = this.immediatelyBefore(taskInitialSel);
		//		削除する(削除する履歴項目)	
//		List<TaskInitialSel> lstTask = new ArrayList<TaskInitialSel>();
		lstHistory.remove(taskInitialSel);
		//	if $直前の履歴.isPresent
		if(data.isPresent()){
			//	$最大終了日 = 年月日#年月日を指定(9999,12,31)
			GeneralDate maxEndDate =  GeneralDate.ymd(9999, 12, 31);
			// 	$変更後の期間 = 期間#期間($直前の履歴.期間.開始日,$最大終了日)		
			DatePeriod changePeriod = new DatePeriod(data.get().getDatePeriod().start(), maxEndDate);
			// 	期間を変更する($直前の履歴,$変更後の期間)																			
			this.exValidateIfCanChangeSpan(data.get(), changePeriod);
		}
	}
	/**
	 * [8] 履歴を変更する	
	 */
	public void changeHistory(TaskInitialSel taskInitialSel , DatePeriod datePeriod){
		//	$直前の履歴 = 直前の履歴の履歴項目(追加する履歴項目)	
		Optional<TaskInitialSel> data = this.immediatelyAfter(taskInitialSel);
		
		//	期間を変更する(変更する履歴項目,期間)	
		this.exValidateIfCanChangeSpan(taskInitialSel, datePeriod);
		this.changeSpan(taskInitialSel, datePeriod);
		
//		lstHistory.remove(taskInitialSel);
//		lstHistory.add(taskInitialSel);
		//	if $直前の履歴.isPresent
		if(data.isPresent()){
			//@履歴リスト：except $直前の履歴	
			lstHistory.remove(data);
			//	$直前の履歴.適用による終了の調整(変更する履歴項目)
			data.get().shortenStartToAccept(taskInitialSel); 
			// @履歴リスト.追加($直前の履歴)		
			lstHistory.add(data.get());
		} 		
	}
	@Override
	public List<TaskInitialSel> items() {
		// TODO Auto-generated method stub
		return lstHistory;
	}

	


}
