package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.declare;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.declare.DeclareAttdLeave;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.declare.DeclareCalcRange;
import nts.uk.ctx.at.shared.dom.worktime.IntegrationOfWorkTime;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 申告設定
 * @author shuichi_ishida
 */
@Getter
public class DeclareSet extends AggregateRoot {

	/** 会社ID */
	private String companyId;
	/** 申告利用区分 */
	public NotUseAtr usageAtr;
	/** 枠設定 */
	public DeclareFrameSet frameSet;
	/** 深夜時間自動計算 */
	public NotUseAtr midnightAutoCalc;
	/** 残業枠 */
	public DeclareOvertimeFrame overtimeFrame;
	/** 休出枠 */
	public DeclareHolidayWorkFrame holidayWorkFrame;
	
	/**
	 * コンストラクタ
	 * @param companyId 会社ID
	 */
	public DeclareSet(String companyId){
		this.companyId = companyId;
		this.usageAtr = NotUseAtr.NOT_USE;
		this.frameSet = DeclareFrameSet.WORKTIME_SET;
		this.midnightAutoCalc = NotUseAtr.NOT_USE;
		this.overtimeFrame = new DeclareOvertimeFrame();
		this.holidayWorkFrame = new DeclareHolidayWorkFrame();
	}
	
	/**
	 * ファクトリー
	 * @param companyId 会社ID
	 * @param usageAtr 申告利用区分
	 * @param frameSet 枠設定
	 * @param midnightAutoCalc 深夜時間自動計算
	 * @param overtimeFrame 残業枠
	 * @param holidayWorkFrame 休出枠
	 * @return 申告設定
	 */
	public static DeclareSet of(
			String companyId,
			NotUseAtr usageAtr,
			DeclareFrameSet frameSet,
			NotUseAtr midnightAutoCalc,
			DeclareOvertimeFrame overtimeFrame,
			DeclareHolidayWorkFrame holidayWorkFrame){
		
		DeclareSet myclass = new DeclareSet(companyId);
		myclass.usageAtr = usageAtr;
		myclass.frameSet = frameSet;
		myclass.midnightAutoCalc = midnightAutoCalc;
		myclass.overtimeFrame = overtimeFrame;
		myclass.holidayWorkFrame = holidayWorkFrame;
		return myclass;
	}
	
	/**
	 * ファクトリー (Java型)
	 * @param companyId 会社ID
	 * @param usageAtr 申告利用区分
	 * @param frameSet 枠設定
	 * @param midnightAutoCalc 深夜時間自動計算
	 * @param overtimeFrame 残業枠
	 * @param holidayWorkFrame 休出枠
	 * @return 申告設定
	 */
	public static DeclareSet createFromJavaType(
			String companyId,
			int usageAtr,
			int frameSet,
			int midnightAutoCalc,
			DeclareOvertimeFrame overtimeFrame,
			DeclareHolidayWorkFrame holidayWorkFrame){

		DeclareSet myclass = new DeclareSet(companyId);
		myclass.usageAtr = EnumAdaptor.valueOf(usageAtr, NotUseAtr.class);
		myclass.frameSet = EnumAdaptor.valueOf(frameSet, DeclareFrameSet.class);
		myclass.midnightAutoCalc = EnumAdaptor.valueOf(midnightAutoCalc, NotUseAtr.class);
		myclass.overtimeFrame = overtimeFrame;
		myclass.holidayWorkFrame = holidayWorkFrame;
		return myclass;
	}
	
	/**
	 * 深夜時間自動計算の判定
	 * @return true=自動計算する,false=自動計算しない
	 */
	public boolean checkMidnightAutoCalc(){
		
		// 枠設定を確認する
		if (this.frameSet == DeclareFrameSet.OT_HDWK_SET){
			// 深夜時間自動計算を確認する
			if (this.midnightAutoCalc == NotUseAtr.NOT_USE){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 残業休出枠設定を調整する
	 * @param itgOfWorkTime 統合就業時間帯(ref)
	 * @param calcRange 申告計算範囲
	 * @param workType 勤務種類
	 */
	public void adjustOvertimeHolidayWorkFrameSet(
			IntegrationOfWorkTime itgOfWorkTime,
			DeclareCalcRange calcRange,
			WorkType workType){
		
		// 「枠設定」を確認する
		if (calcRange.getDeclareSet().getFrameSet() == DeclareFrameSet.WORKTIME_SET) return;
		// 申告残業枠の設定
		{
			// 固定勤務の申告残業枠の設定
			this.overtimeFrame.setDeclareOvertimeFrameOfFixed(itgOfWorkTime, calcRange, workType);
			// 流動勤務の申告残業枠の設定
			this.overtimeFrame.setDeclareOvertimeFrameOfFlow(itgOfWorkTime, calcRange);
		}
		// 申告休出枠の設定
		{
			// 固定勤務の申告休出枠の設定
			this.holidayWorkFrame.setDeclareHolidayWorkFrameOfFixed(itgOfWorkTime, calcRange);
			// 流動勤務の申告休出枠の設定
			this.holidayWorkFrame.setDeclareHolidayWorkFrameOfFlow(itgOfWorkTime, calcRange);
		}
	}
	
	/**
	 * 申告エラーチェック
	 * @param isHolidayWork 休出かどうか
	 * @param attdLeave 申告出退勤
	 * @return true=エラーあり,false=エラーなし
	 */
	public boolean checkError(boolean isHolidayWork, DeclareAttdLeave attdLeave){
	
		// 申告設定残業枠エラーチェック
		if (this.overtimeFrame.checkErrorOvertimeFrame()) return true;
		// 申告設定休出枠エラーチェック
		if (this.holidayWorkFrame.checkErrorHolidayWorkFrame()) return true;
		// 申告設定深夜エラーチェック
		if (this.checkErrorMidnightFrame()) return true;
		// 申告時間枠エラーチェック
		List<DeclareTimeFrameError> errors = this.checkErrorFrame(isHolidayWork, attdLeave);
		if (errors.size() > 0) return true;
		
		return false;
	}
	
	/**
	 * 申告設定深夜枠エラーチェック
	 * @return true=エラーあり,false=エラーなし
	 */
	public boolean checkErrorMidnightFrame(){
		
		// 枠設定を確認する
		if (this.frameSet == DeclareFrameSet.OT_HDWK_SET){
			// 深夜時間自動計算を確認する
			if (this.midnightAutoCalc == NotUseAtr.USE){
				// 残業枠を確認する
				if (!this.overtimeFrame.getEarlyOvertimeMn().isPresent() ||		// 早出残業深夜
					!this.overtimeFrame.getOvertimeMn().isPresent()){			// 普通残業深夜 
					return true;
				}
				// 休出枠を確認する
				if (!this.holidayWorkFrame.getHolidayWorkMn().isPresent()){		// 休出深夜 
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 申告時間枠エラーチェック
	 * @param isHolidayWork 休出かどうか
	 * @param attdLeave 申告出退勤
	 * @return 申告時間枠エラー(List)
	 */
	public List<DeclareTimeFrameError> checkErrorFrame(
			boolean isHolidayWork,
			DeclareAttdLeave attdLeave){
		
		List<DeclareTimeFrameError> errors = new ArrayList<>();
		
		// 枠設定を確認する
		if (this.frameSet == DeclareFrameSet.OT_HDWK_SET){
			// 休出かどうかの判断
			if (isHolidayWork){
				// 申告休出枠エラーチェック
				{
					// 退勤時間外を確認する
					if (attdLeave.getLeaveOvertime().isPresent()){
						if (attdLeave.getLeaveOvertime().get().getOverTime().v() > 0){
							// 休出枠．休出を確認する
							if (this.holidayWorkFrame.getHolidayWork().isPresent()){
							}
							else{
								// エラー値「休出」を追加する
								errors.add(DeclareTimeFrameError.HOLIDAYWORK);
							}
						}
						if (attdLeave.getLeaveOvertime().get().getOverLateNightTime().v() > 0){
							// 休出枠．休出深夜を確認する
							if (this.holidayWorkFrame.getHolidayWorkMn().isPresent()){
							}
							else{
								// エラー値「休出深夜」を追加する
								errors.add(DeclareTimeFrameError.HOLIDAYWORK_MN);
							}
						}
					}
				}
			}
			else{
				// 申告早出枠エラーチェック
				{
					// 出勤時間外を確認する
					if (attdLeave.getAttdOvertime().isPresent()){
						if (attdLeave.getAttdOvertime().get().getOverTime().v() > 0){
							// 残業枠．早出残業を確認する
							if (this.overtimeFrame.getEarlyOvertime().isPresent()){
							}
							else{
								// エラー値「早出残業」を追加する
								errors.add(DeclareTimeFrameError.EARLY_OT);
							}
						}
						if (attdLeave.getAttdOvertime().get().getOverLateNightTime().v() > 0){
							// 残業枠．早出残業深夜を確認する
							if (this.overtimeFrame.getEarlyOvertimeMn().isPresent()){
							}
							else{
								// エラー値「早出残業深夜」を追加する
								errors.add(DeclareTimeFrameError.EARLY_OT_MN);
							}
						}
					}
				}
				// 申告残業枠エラーチェック
				{
					// 退勤時間外を確認する
					if (attdLeave.getLeaveOvertime().isPresent()){
						if (attdLeave.getLeaveOvertime().get().getOverTime().v() > 0){
							// 残業枠．普通残業を確認する
							if (this.overtimeFrame.getOvertime().isPresent()){
							}
							else{
								// エラー値「普通残業」を追加する
								errors.add(DeclareTimeFrameError.OVERTIME);
							}
						}
						if (attdLeave.getLeaveOvertime().get().getOverLateNightTime().v() > 0){
							// 残業枠．普通残業深夜を確認する
							if (this.overtimeFrame.getOvertimeMn().isPresent()){
							}
							else{
								// エラー値「普通残業深夜」を追加する
								errors.add(DeclareTimeFrameError.OVERTIME_MN);
							}
						}
					}
				}
			}
		}
		return errors;
	}
}
