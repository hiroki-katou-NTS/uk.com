package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalAtrOvertime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.AutoCalRestTimeSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.ManageAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.service.WorkingConditionService;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.i18n.TextResource;

/**
 * 打刻種類
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻.打刻種類
 * @author phongtq
 *
 */
@AllArgsConstructor
public class StampType implements DomainValue, Cloneable{
	
	/** 勤務種類を半休に変更する */
	//勤務種類を半休に変更する 1 old
	@Getter
	private final boolean changeHalfDay;

	/** 外出区分 */
	//外出理由 old
	@Getter
	private final Optional<GoingOutReason> goOutArt;
	
	/** 所定時刻セット区分 */
	//勤務種類を半休に変更する 2 old
	@Getter
	private final SetPreClockArt setPreClockArt;
	
	/** 時刻変更区分 */
	//打刻区分 old
	@Getter
	private final ChangeClockAtr changeClockArt;
	
	/** 計算区分変更対象 */
	//勤務種類を半休に変更する 3 old
	@Getter
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
			ChangeClockAtr changeClockArt, ChangeCalArt changeCalArt) {
		super();
		this.changeHalfDay = changeHalfDay;
		this.goOutArt = Optional.ofNullable(goOutArt);
		this.setPreClockArt = setPreClockArt;
		this.changeClockArt = changeClockArt;
		this.changeCalArt = changeCalArt;
	}
	
	/**
	 * [C-1] 打刻種類
	 * @param changeHalfDay
	 * @param goOutArt
	 * @param setPreClockArt
	 * @param changeClockArt
	 * @param changeCalArt
	 */
	public static StampType getStampType(boolean changeHalfDay, GoingOutReason goOutArt, SetPreClockArt setPreClockArt,
			ChangeClockAtr changeClockArt, ChangeCalArt changeCalArt) {
		if(changeClockArt != null && changeClockArt.value == ChangeClockAtr.GO_OUT.value && goOutArt == null) {
			throw new BusinessException("Msg_1704"); 
		}
		return new StampType(
				changeHalfDay, 
				changeClockArt != null ? changeClockArt.value == ChangeClockAtr.GOING_TO_WORK.value ? null : goOutArt : null, 
				setPreClockArt, 
				changeClockArt, 
				changeCalArt);
	}
	
	/**
	 * [1] 表示する打刻区分を作成する
	 * @return
	 */
	public String createStampTypeDisplay() {
		
		if (this.setPreClockArt == SetPreClockArt.DIRECT) {
			return TextResource.localize("KDP011_35");
		}
		if (this.setPreClockArt == SetPreClockArt.BOUNCE) {
			return TextResource.localize("KDP011_36");
		}
		
		if (this.changeCalArt == ChangeCalArt.EARLY_APPEARANCE && this.changeClockArt.equals(ChangeClockAtr.GOING_TO_WORK)) {
			return TextResource.localize("KDP011_37");
		}
		
		if (this.changeCalArt == ChangeCalArt.EARLY_APPEARANCE && this.changeClockArt.equals(ChangeClockAtr.START_OF_SUPPORT)) {
			return TextResource.localize("KDP011_40");
		}
		
		if (this.changeCalArt == ChangeCalArt.BRARK && this.changeClockArt.equals(ChangeClockAtr.GOING_TO_WORK)) {
			return TextResource.localize("KDP011_38");
		}
		
		if (this.changeCalArt == ChangeCalArt.BRARK && this.changeClockArt.equals(ChangeClockAtr.START_OF_SUPPORT)) {
			return TextResource.localize("KDP011_41");
		}
		
//		if (this.changeCalArt == ChangeCalArt.BRARK) {
//			return TextResource.localize("KDP011_38");
//		}

		String stampAtr = this.changeClockArt.nameId;

		if (this.goOutArt.isPresent()) {
			stampAtr = stampAtr + "(" + this.goOutArt.get().nameId + ")";
		}

		if (this.changeCalArt != ChangeCalArt.NONE) {
			stampAtr = stampAtr + "+" + this.changeCalArt.nameId;
		}

		if (changeHalfDay) {
			stampAtr = stampAtr + "+" + TextResource.localize("KDP011_39");
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
		
		if(this.changeClockArt == ChangeClockAtr.GOING_TO_WORK ) {
			if(this.changeCalArt == ChangeCalArt.NONE || this.changeCalArt == ChangeCalArt.EARLY_APPEARANCE ) {
				return true;
			}
		}else if(this.changeClockArt == ChangeClockAtr.START_OF_SUPPORT ) {
			if(this.changeCalArt == ChangeCalArt.EARLY_APPEARANCE ) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 休日出勤に変更するか
	 * @param require
	 * @param holidayTimeSetting 休出時間の自動計算設定
	 * @param workTypeCode 勤務種類コード
	 * @return
	 */
	public boolean changeWorkOnHolidays(
			Require require,
			AutoCalRestTimeSetting holidayTimeSetting,
			String workTypeCode,
			String employeeId, 
			GeneralDate baseDate
			) {
		// 社員の労働条件を取得する
		Optional<WorkingConditionItem> workCondOpt = require.getBySidAndStandardDate(employeeId, baseDate);
		// スケジュール管理しないか確認
		if (workCondOpt.map(x -> x.getScheduleManagementAtr() == ManageAtr.NOTUSE).orElse(false)) return false;
		
		
		// 勤務種類を取得する
		Optional<WorkType> workType = require.findByPK(workTypeCode);
		if (!workType.isPresent())
			return false;
		//一日休日かを確認する
		if(!workType.get().isHoliday()) {
			return false;
		}
		//日別勤怠の計算区分の休出時間の計算区分を確認する
		if(holidayTimeSetting.getRestTime().getCalAtr() == AutoCalAtrOvertime.CALCULATEMBOSS) {
			return true;
		}else if(holidayTimeSetting.getRestTime().getCalAtr() == AutoCalAtrOvertime.APPLYMANUALLYENTER) {
			return false;
		}
		//打刻の計算区分変更対象を確認する
		if(this.changeCalArt == ChangeCalArt.BRARK) {
			return true;
		}
		return false;
	}

	public static interface Require {
		Optional<WorkType> findByPK(String workTypeCd);
		Optional<WorkingConditionItem> getBySidAndStandardDate(String employeeId, GeneralDate ymd);
	}

	@Override
	public StampType clone() {
		return new StampType(changeHalfDay, goOutArt.map(x -> GoingOutReason.valueOf(x.value)),
				SetPreClockArt.valueOf(setPreClockArt.value), ChangeClockAtr.valueOf(changeClockArt.value),
				ChangeCalArt.valueOf(changeCalArt.value));
	}
}
