package nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto;

import java.util.Optional;

import lombok.Data;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonDisSet;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonNameSet;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonSettings;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonType;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ChangeCalArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ChangeClockArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ReservationArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SetPreClockArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampType;

/**
 * @author anhdt
 *
 */
@Data
public class ButtonSettingDto {
	private Integer btnPositionNo;
	private String btnName;
	private String btnTextColor;
	private String btnBackGroundColor;
	
	private Integer btnReservationArt;
	private Boolean changeHalfDay;
	private Integer goOutArt;
	private Integer setPreClockArt;
	private Integer changeClockArt;
	private Integer changeCalArt;
	
	private Integer usrArt;
	private Integer audioType;
	// 0 = reservation system, goingToWork = 1, departure = 2, goOut = 3, turnBack = 4
	private Integer btnDisplayType;
	
	public ButtonSettingDto(ButtonSettings btnSet) {
		this.btnPositionNo = btnSet.getButtonPositionNo().v();
		
		ButtonDisSet btnDisSet = btnSet.getButtonDisSet();
		ButtonNameSet btnNameSet = btnDisSet.getButtonNameSet();
		this.btnName = btnNameSet.getButtonName().isPresent() ? btnNameSet.getButtonName().get().v() : null;
		this.btnTextColor = btnNameSet.getTextColor().v();
		this.btnBackGroundColor = btnDisSet.getBackGroundColor().v();
		
		ButtonType btnType = btnSet.getButtonType();
		this.btnReservationArt = btnType.getReservationArt().value;
		
		Optional<StampType> oStampType = btnType.getStampType();
		if(oStampType.isPresent()) {
			StampType stampType = oStampType.get();
			this.changeHalfDay = stampType.getChangeHalfDay() == null ? null : stampType.getChangeHalfDay();
			this.goOutArt = stampType.getGoOutArt().isPresent() ? stampType.getGoOutArt().get().value : null;
			this.setPreClockArt = stampType.getSetPreClockArt() == null ? null : stampType.getSetPreClockArt().value;
			this.changeClockArt = stampType.getChangeClockArt() == null ? null : stampType.getChangeClockArt().value;
			this.changeCalArt = stampType.getChangeCalArt() == null ? null : stampType.getChangeCalArt().value;
		}
		
		this.usrArt = btnSet.getUsrArt().value;
		this.audioType = btnSet.getAudioType().value;
		this.btnDisplayType = corectBtnDisType();
	}
	
	public Integer corectBtnDisType() {
		if(this.changeClockArt == null) {
			return null;
		}

		if (this.changeClockArt == ChangeClockArt.GOING_TO_WORK.value
				&& this.setPreClockArt == SetPreClockArt.NONE.value && this.changeCalArt == ChangeCalArt.NONE.value
				&& this.changeHalfDay == false && this.btnReservationArt == ReservationArt.NONE.value) {
			return 1;
		}
		if (this.changeClockArt == ChangeClockArt.GOING_TO_WORK.value
				&& this.setPreClockArt == SetPreClockArt.DIRECT.value && this.changeCalArt == ChangeCalArt.NONE.value
				&& this.changeHalfDay == false && this.btnReservationArt == ReservationArt.NONE.value) {
			return 1;
		}
		if (this.changeClockArt == ChangeClockArt.GOING_TO_WORK.value
				&& this.setPreClockArt == SetPreClockArt.NONE.value
				&& this.changeCalArt == ChangeCalArt.EARLY_APPEARANCE.value && this.changeHalfDay == false
				&& this.btnReservationArt == ReservationArt.NONE.value) {
			return 1;
		}
		if (this.changeClockArt == ChangeClockArt.GOING_TO_WORK.value
				&& this.setPreClockArt == SetPreClockArt.DIRECT.value && this.changeCalArt == ChangeCalArt.BRARK.value
				&& this.changeHalfDay == false && this.btnReservationArt == ReservationArt.NONE.value) {
			return 1;
		}
		if (this.changeClockArt == ChangeClockArt.GOING_TO_WORK.value
				&& this.setPreClockArt == SetPreClockArt.NONE.value && this.changeCalArt == ChangeCalArt.BRARK.value
				&& this.changeHalfDay == false && this.btnReservationArt == ReservationArt.NONE.value) {
			return 1;
		}
		if (this.changeClockArt == ChangeClockArt.WORKING_OUT.value && this.changeCalArt == ChangeCalArt.OVER_TIME.value
				&& this.setPreClockArt == SetPreClockArt.NONE.value && this.changeHalfDay == false
				&& this.btnReservationArt == ReservationArt.NONE.value) {
			return 2;
		}
		if (this.changeClockArt == ChangeClockArt.WORKING_OUT.value && this.changeCalArt == ChangeCalArt.OVER_TIME.value
				&& this.setPreClockArt == SetPreClockArt.BOUNCE.value && this.changeHalfDay == false
				&& this.btnReservationArt == ReservationArt.NONE.value) {
			return 2;
		}
		if (this.changeClockArt == ChangeClockArt.WORKING_OUT.value && this.changeCalArt == ChangeCalArt.OVER_TIME.value
				&& this.setPreClockArt == SetPreClockArt.NONE.value && this.changeHalfDay == false
				&& this.btnReservationArt == ReservationArt.NONE.value) {
			return 2;
		}
		if (this.changeClockArt == ChangeClockArt.GO_OUT.value && this.changeCalArt == ChangeCalArt.NONE.value
				&& this.setPreClockArt == SetPreClockArt.NONE.value && this.changeHalfDay == false
				&& this.btnReservationArt == ReservationArt.NONE.value) {
			return 3;
		}
		if (this.changeClockArt == ChangeClockArt.RETURN.value && this.changeCalArt == ChangeCalArt.NONE.value
				&& this.setPreClockArt == SetPreClockArt.NONE.value && this.changeHalfDay == false
				&& this.btnReservationArt == ReservationArt.NONE.value) {
			return 4;
		}
		if (this.changeClockArt == ChangeClockArt.WORKING_OUT.value && this.changeCalArt == ChangeCalArt.NONE.value
				&& this.setPreClockArt == SetPreClockArt.NONE.value && this.changeHalfDay == false
				&& this.btnReservationArt == ReservationArt.NONE.value) {
			return 3;
		}
		if (this.changeClockArt == ChangeClockArt.WORKING_OUT.value && this.changeCalArt == ChangeCalArt.NONE.value
				&& this.setPreClockArt == SetPreClockArt.BOUNCE.value && this.changeHalfDay == false
				&& this.btnReservationArt == ReservationArt.NONE.value) {
			return 3;
		}
		if (this.changeClockArt == ChangeClockArt.OVER_TIME.value && this.changeCalArt == ChangeCalArt.NONE.value
				&& this.setPreClockArt == SetPreClockArt.NONE.value && this.changeHalfDay == false
				&& this.btnReservationArt == ReservationArt.NONE.value) {
			return 2;
		}
		if (this.changeClockArt == ChangeClockArt.TEMPORARY_WORK.value && this.changeCalArt == ChangeCalArt.NONE.value
				&& this.setPreClockArt == SetPreClockArt.NONE.value && this.changeHalfDay == false
				&& this.btnReservationArt == ReservationArt.NONE.value) {
			return 1;
		}
		if (this.changeClockArt == ChangeClockArt.TEMPORARY_LEAVING.value
				&& this.changeCalArt == ChangeCalArt.NONE.value && this.setPreClockArt == SetPreClockArt.NONE.value
				&& this.changeHalfDay == false && this.btnReservationArt == ReservationArt.NONE.value) {
			return 2;
		}
		if (this.changeClockArt == ChangeClockArt.FIX.value && this.changeCalArt == ChangeCalArt.NONE.value
				&& this.setPreClockArt == SetPreClockArt.NONE.value && this.changeHalfDay == false
				&& this.btnReservationArt == ReservationArt.NONE.value) {
			return 1;
		}
		if (this.changeClockArt == ChangeClockArt.END_OF_SUPPORT.value && this.changeCalArt == ChangeCalArt.NONE.value
				&& this.setPreClockArt == SetPreClockArt.NONE.value && this.changeHalfDay == false
				&& this.btnReservationArt == ReservationArt.NONE.value) {
			return 2;
		}
		if (this.changeClockArt == ChangeClockArt.SUPPORT.value && this.changeCalArt == ChangeCalArt.NONE.value
				&& this.setPreClockArt == SetPreClockArt.NONE.value && this.changeHalfDay == false
				&& this.btnReservationArt == ReservationArt.NONE.value) {
			return 1;
		}
		if (this.changeClockArt == ChangeClockArt.FIX.value && this.changeCalArt == ChangeCalArt.EARLY_APPEARANCE.value
				&& this.setPreClockArt == SetPreClockArt.NONE.value && this.changeHalfDay == false
				&& this.btnReservationArt == ReservationArt.NONE.value) {
			return 1;
		}
		if (this.changeClockArt == ChangeClockArt.FIX.value && this.changeCalArt == ChangeCalArt.BRARK.value
				&& this.setPreClockArt == SetPreClockArt.NONE.value && this.changeHalfDay == false
				&& this.btnReservationArt == ReservationArt.NONE.value) {
			return 1;
		}
		if (this.changeClockArt == ChangeClockArt.GOING_TO_WORK.value && this.changeCalArt == ChangeCalArt.BRARK.value
				&& this.setPreClockArt == SetPreClockArt.NONE.value && this.changeHalfDay == false
				&& this.btnReservationArt == ReservationArt.RESERVATION.value) {
			return 0;
		}
		if (this.changeClockArt == ChangeClockArt.GOING_TO_WORK.value && this.changeCalArt == ChangeCalArt.BRARK.value
				&& this.setPreClockArt == SetPreClockArt.NONE.value && this.changeHalfDay == false
				&& this.btnReservationArt == ReservationArt.CANCEL_RESERVATION.value) {
			return 0;
		}

		return null;
	}
}
