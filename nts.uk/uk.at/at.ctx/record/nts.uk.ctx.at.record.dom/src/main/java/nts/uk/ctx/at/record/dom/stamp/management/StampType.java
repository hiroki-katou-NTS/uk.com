package nts.uk.ctx.at.record.dom.stamp.management;

import java.util.Optional;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;

/**
 * 打刻種類
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻.打刻種類
 * @author phongtq
 *
 */
@Value
public class StampType implements DomainValue {
	
	/** 勤務種類を半休に変更する */
	//勤務種類を半休に変更する 1 old
	private final boolean changeHalfDay;

	/** 外出区分 */
	//外出理由 old
	private final Optional<GoingOutReason> goOutArt;
	
	/** 所定時刻セット区分 */
	//勤務種類を半休に変更する 2 old
	private final SetPreClockArt setPreClockArt;
	
	/** 時刻変更区分 */
	//打刻区分 old
	private final ChangeClockArt changeClockArt;
	
	/** 計算区分変更対象 */
	//勤務種類を半休に変更する 3 old
	private final ChangeCalArt changeCalArt;

	/**
	 * [C-0] 打刻種類(時刻変更区分, 計算区分変更対象, 所定時刻セット区分, 勤務種類を半休に変更する, 外出区分)
	 * @param changeHalfDay
	 * @param goOutArt
	 * @param setPreClockArt
	 * @param changeClockArt
	 * @param changeCalArt
	 */
	public StampType(boolean changeHalfDay, GoingOutReason goOutArt, SetPreClockArt setPreClockArt,
			ChangeClockArt changeClockArt, ChangeCalArt changeCalArt) {
		super();
		this.changeHalfDay = changeHalfDay;
		this.goOutArt = Optional.ofNullable(goOutArt);
		this.setPreClockArt = setPreClockArt;
		this.changeClockArt = changeClockArt;
		this.changeCalArt = changeCalArt;
	}
	
	/**
	 * [1] 表示する打刻区分を作成する
	 * @return
	 */
	public String createStampTypeDisplay() {
		if (this.setPreClockArt == SetPreClockArt.DIRECT) {
			return "直行";
		} else if (this.setPreClockArt == SetPreClockArt.BOUNCE) {
			return "直帰";
		} else if (changeCalArt == ChangeCalArt.EARLY_APPEARANCE) {
			return "早出";
		} else if (changeCalArt == ChangeCalArt.BRARK) {
			return "休出";
		}

		String stampAtr = this.changeClockArt.nameId;

		if (this.goOutArt.isPresent()) {
			stampAtr = stampAtr + "+(" + this.goOutArt.get().nameId + ")";
		}

		if (this.changeCalArt != ChangeCalArt.NONE) {
			stampAtr = stampAtr + "+" + this.changeCalArt.nameId;
		}

		if (changeHalfDay) {
			stampAtr = stampAtr + "+" + "半休";
		}

		return stampAtr;
	}
	/**
	 * [2] 弁当を自動予約するか
	 * @return
	 */
	public boolean checkBookAuto() {
		if(this.changeHalfDay || this.setPreClockArt != SetPreClockArt.NONE) {
			return false;
		}
		
		if(this.changeClockArt == ChangeClockArt.GOING_TO_WORK ) {
			if(this.changeCalArt == ChangeCalArt.NONE || this.changeCalArt == ChangeCalArt.EARLY_APPEARANCE ) {
				return true;
			}
		}else if(this.changeClockArt == ChangeClockArt.FIX ) {
			if(this.changeCalArt == ChangeCalArt.EARLY_APPEARANCE ) {
				return true;
			}
		}
		return false;
	}
	
	

}
