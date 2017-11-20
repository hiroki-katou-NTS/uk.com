package nts.uk.ctx.at.record.dom.monthly.calc.flex;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 月別実績のフレックス時間
 * @author shuichi_ishida
 */
@Getter
public class FlexTimeOfMonthly {

	/** フレックス時間 */
	private FlexTime flexTime;
	/** フレックス超過時間 */
	private AttendanceTimeMonth flexExcessTime;
	/** フレックス不足時間 */
	private AttendanceTimeMonth flexShortageTime;
	/** 事前フレックス時間 */
	private AttendanceTimeMonth beforeFlexTime;
	/** フレックス繰越時間 */
	private FlexCarryforwardTime flexCarryforwardTime;
	/** 時間外超過のフレックス時間 */
	private FlexTimeOfExcessOutsideWork flexTimeOfExcessOutsideWork;
	
	/**
	 * コンストラクタ
	 */
	public FlexTimeOfMonthly(){
		
		this.flexTime = new FlexTime();
		this.flexCarryforwardTime = new FlexCarryforwardTime();
		this.flexTimeOfExcessOutsideWork = new FlexTimeOfExcessOutsideWork();
	}

	/**
	 * ファクトリー
	 * @param flexTime フレックス時間
	 * @param flexExcessTime フレックス超過時間
	 * @param flexShortageTime フレックス不足時間
	 * @param beforeFlexTime 事前フレックス時間
	 * @param flexCarryforwardTime フレックス繰越時間
	 * @param flexTimeOfExcessOutsideWork 時間外超過のフレックス時間
	 * @return 月別実績のフレックス時間
	 */
	public static FlexTimeOfMonthly of(
			FlexTime flexTime,
			AttendanceTimeMonth flexExcessTime,
			AttendanceTimeMonth flexShortageTime,
			AttendanceTimeMonth beforeFlexTime,
			FlexCarryforwardTime flexCarryforwardTime,
			FlexTimeOfExcessOutsideWork flexTimeOfExcessOutsideWork){
		
		FlexTimeOfMonthly domain = new FlexTimeOfMonthly();
		domain.flexTime = flexTime;
		domain.flexExcessTime = flexExcessTime;
		domain.flexShortageTime = flexShortageTime;
		domain.beforeFlexTime = beforeFlexTime;
		domain.flexCarryforwardTime = flexCarryforwardTime;
		domain.flexTimeOfExcessOutsideWork = flexTimeOfExcessOutsideWork;
		return domain;
	}
	
	/**
	 * 月別実績を集計する
	 */
	public void aggregateMonthly(){
		
		// パラメータ「期間．開始日」を処理日にする
		
		// 処理をする期間の日数分ループ
		
		// 日別実績を集計する
		
		// フレックス時間を集計する
		
		// パラメータ「集計区分」を確認する（２次）
		
		// フレックス超過時間を割り当てる（２次）
		
		// 処理する日が残っているか確認する
		
		// ループ終了
		
	}
	
	/**
	 * 月単位の時間を集計する
	 */
	public void aggregateMonthlyHours(){

		// フレックス不足時の繰越設定を確認する
		
		// フレックス不足時間を取得する
		
		// フレックス不足時間をフレックス繰越時間にコピーする
		
		// パラメータ「フレックス集計方法」を確認する
		
		// 便宜上集計をする
		
		// 原則集計をする
		
	}
}
