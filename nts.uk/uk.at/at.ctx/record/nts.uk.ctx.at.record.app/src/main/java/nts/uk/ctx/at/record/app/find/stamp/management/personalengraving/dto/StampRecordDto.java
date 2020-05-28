package nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto;

import java.util.Optional;

import lombok.Data;
import nts.arc.i18n.I18NText;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.RefectActualResult;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampLocationInfor;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampMeans;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecord;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ChangeCalArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ChangeClockArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ContentsStampType;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ReservationArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SetPreClockArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampType;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailywork.worktime.overtimedeclaration.OvertimeDeclaration;

/**
 * @author anhdt
 *
 */
@Data
public class StampRecordDto {

	private String stampNumber;
	private String stampDate;
	private String stampTime;
	private String stampHow;
	private boolean stampArt;
	private String stampArtName;
	private Integer revervationAtr;
	private Integer empInfoTerCode;
	private String timeStampType;

	// stamp
	private Integer authcMethod;
	private Integer stampMeans;

	private boolean changeHalfDay;
	private Integer goOutArt;
	private Integer setPreClockArt;
	private Integer changeClockArt;
	private String changeClockArtName;
	private Integer changeCalArt;

	private String cardNumberSupport;
	private String workLocationCD;
	private String workTimeCode;
	private String overTime;
	private String overLateNightTime;

	private boolean reflectedCategory;

	private Optional<StampLocationInfor> locationInfor;
	private boolean outsideAreaAtr;
	private Double latitude;
	private Double longitude;

	private String attendanceTime;

	public StampRecordDto(StampRecord stampRecord, Stamp stamp) {
		this.stampNumber = stampRecord.getStampNumber().v();
		this.stampDate = stampRecord.getStampDateTime().toString("yyyy/MM/dd");
		this.stampTime = stampRecord.getStampDateTime().toString("HH:mm");
		this.stampHow = getCorrectTimeString(stamp != null ? stamp.getRelieve().getStampMeans() : null);
		this.stampArt = stampRecord.isStampArt();
		
		this.revervationAtr = stampRecord.getRevervationAtr().value;
		this.empInfoTerCode = stampRecord.getEmpInfoTerCode().isPresent() ? stampRecord.getEmpInfoTerCode().get().v()
				: null;

		// stamp
		if (stamp != null) {
			this.authcMethod = stamp.getRelieve().getAuthcMethod().value;
			this.stampMeans = stamp.getRelieve().getStampMeans().value;
			

			StampType type = stamp.getType();
			this.stampArtName = type.createStampTypeDisplay();
			this.changeHalfDay = type.getChangeHalfDay();
			this.goOutArt = type.getGoOutArt().isPresent() ? type.getGoOutArt().get().value : null;
			this.setPreClockArt = type.getSetPreClockArt().value;
			this.changeClockArt = type.getChangeClockArt().value;
			this.changeClockArtName = type.getChangeClockArt().nameId;
			this.changeCalArt = type.getChangeCalArt().value;

			RefectActualResult raResult = stamp.getRefActualResults();
			this.cardNumberSupport = raResult.getCardNumberSupport().isPresent() ? raResult.getCardNumberSupport().get()
					: null;
			this.workLocationCD = raResult.getWorkLocationCD().isPresent() ? raResult.getWorkLocationCD().get().v()
					: null;
			this.workTimeCode = raResult.getWorkTimeCode().isPresent() ? raResult.getWorkTimeCode().get().v() : null;
			if (raResult.getOvertimeDeclaration().isPresent()) {
				OvertimeDeclaration overtime = raResult.getOvertimeDeclaration().get();
				this.overTime = getTimeString(overtime.getOverTime().v());
				this.overLateNightTime = getTimeString(overtime.getOverLateNightTime().v());
			}
			this.reflectedCategory = stamp.isReflectedCategory();
			if (stamp.getLocationInfor().isPresent()) {
				StampLocationInfor stampLocate = stamp.getLocationInfor().get();
				this.outsideAreaAtr = stampLocate.isOutsideAreaAtr();
				this.latitude = stampLocate.getPositionInfor().getLatitude();
				this.longitude = stampLocate.getPositionInfor().getLongitude();
			}
			this.attendanceTime = stamp.getAttendanceTime().isPresent()
					? getTimeString(stamp.getAttendanceTime().get().v())
					: null;
			this.timeStampType = this.stampArtName;
		}
	}

	public String getCorectTtimeStampType() {

		if (this.changeClockArt == ChangeClockArt.GOING_TO_WORK.value
				&& this.setPreClockArt == SetPreClockArt.NONE.value && this.changeCalArt == ChangeCalArt.NONE.value
				&& this.changeHalfDay == false && this.revervationAtr == ReservationArt.NONE.value) {
			return ContentsStampType.WORK.nameId;
		}
		if (this.changeClockArt == ChangeClockArt.GOING_TO_WORK.value
				&& this.setPreClockArt == SetPreClockArt.DIRECT.value && this.changeCalArt == ChangeCalArt.NONE.value
				&& this.changeHalfDay == false && this.revervationAtr == ReservationArt.NONE.value) {
			return ContentsStampType.WORK_STRAIGHT.nameId;
		}
		if (this.changeClockArt == ChangeClockArt.GOING_TO_WORK.value
				&& this.setPreClockArt == SetPreClockArt.NONE.value
				&& this.changeCalArt == ChangeCalArt.EARLY_APPEARANCE.value && this.changeHalfDay == false
				&& this.revervationAtr == ReservationArt.NONE.value) {
			return ContentsStampType.WORK_EARLY.nameId;
		}
		if (this.changeClockArt == ChangeClockArt.GOING_TO_WORK.value
				&& this.setPreClockArt == SetPreClockArt.DIRECT.value && this.changeCalArt == ChangeCalArt.BRARK.value
				&& this.changeHalfDay == false && this.revervationAtr == ReservationArt.NONE.value) {
			return ContentsStampType.WORK_BREAK.nameId;
		}
		if (this.changeClockArt == ChangeClockArt.WORKING_OUT.value && this.changeCalArt == ChangeCalArt.OVER_TIME.value
				&& this.setPreClockArt == SetPreClockArt.NONE.value && this.changeHalfDay == false
				&& this.revervationAtr == ReservationArt.NONE.value) {
			return ContentsStampType.DEPARTURE.nameId;
		}
		if (this.changeClockArt == ChangeClockArt.WORKING_OUT.value && this.changeCalArt == ChangeCalArt.OVER_TIME.value
				&& this.setPreClockArt == SetPreClockArt.BOUNCE.value && this.changeHalfDay == false
				&& this.revervationAtr == ReservationArt.NONE.value) {
			return ContentsStampType.DEPARTURE_BOUNCE.nameId;
		}
		if (this.changeClockArt == ChangeClockArt.WORKING_OUT.value && this.changeCalArt == ChangeCalArt.OVER_TIME.value
				&& this.setPreClockArt == SetPreClockArt.NONE.value && this.changeHalfDay == false
				&& this.revervationAtr == ReservationArt.NONE.value) {
			return ContentsStampType.DEPARTURE_OVERTIME.nameId;
		}
		if (this.changeClockArt == ChangeClockArt.GO_OUT.value && this.changeCalArt == ChangeCalArt.NONE.value
				&& this.setPreClockArt == SetPreClockArt.NONE.value && this.changeHalfDay == false
				&& this.revervationAtr == ReservationArt.NONE.value) {
			return ContentsStampType.OUT.nameId;
		}
		if (this.changeClockArt == ChangeClockArt.RETURN.value && this.changeCalArt == ChangeCalArt.NONE.value
				&& this.setPreClockArt == SetPreClockArt.NONE.value && this.changeHalfDay == false
				&& this.revervationAtr == ReservationArt.NONE.value) {
			return ContentsStampType.RETURN.nameId;
		}
		if (this.changeClockArt == ChangeClockArt.WORKING_OUT.value && this.changeCalArt == ChangeCalArt.NONE.value
				&& this.setPreClockArt == SetPreClockArt.NONE.value && this.changeHalfDay == false
				&& this.revervationAtr == ReservationArt.NONE.value) {
			return ContentsStampType.GETTING_STARTED.nameId;
		}
		if (this.changeClockArt == ChangeClockArt.OVER_TIME.value && this.changeCalArt == ChangeCalArt.NONE.value
				&& this.setPreClockArt == SetPreClockArt.NONE.value && this.changeHalfDay == false
				&& this.revervationAtr == ReservationArt.NONE.value) {
			return ContentsStampType.DEPAR.nameId;
		}
		if (this.changeClockArt == ChangeClockArt.TEMPORARY_WORK.value && this.changeCalArt == ChangeCalArt.NONE.value
				&& this.setPreClockArt == SetPreClockArt.NONE.value && this.changeHalfDay == false
				&& this.revervationAtr == ReservationArt.NONE.value) {
			return ContentsStampType.TEMPORARY_WORK.nameId;
		}
		if (this.changeClockArt == ChangeClockArt.TEMPORARY_LEAVING.value
				&& this.changeCalArt == ChangeCalArt.NONE.value && this.setPreClockArt == SetPreClockArt.NONE.value
				&& this.changeHalfDay == false && this.revervationAtr == ReservationArt.NONE.value) {
			return ContentsStampType.TEMPORARY_LEAVING.nameId;
		}
		if (this.changeClockArt == ChangeClockArt.FIX.value && this.changeCalArt == ChangeCalArt.NONE.value
				&& this.setPreClockArt == SetPreClockArt.NONE.value && this.changeHalfDay == false
				&& this.revervationAtr == ReservationArt.NONE.value) {
			return ContentsStampType.START_SUPPORT.nameId;
		}
		if (this.changeClockArt == ChangeClockArt.END_OF_SUPPORT.value && this.changeCalArt == ChangeCalArt.NONE.value
				&& this.setPreClockArt == SetPreClockArt.NONE.value && this.changeHalfDay == false
				&& this.revervationAtr == ReservationArt.NONE.value) {
			return ContentsStampType.END_SUPPORT.nameId;
		}
		if (this.changeClockArt == ChangeClockArt.SUPPORT.value && this.changeCalArt == ChangeCalArt.NONE.value
				&& this.setPreClockArt == SetPreClockArt.NONE.value && this.changeHalfDay == false
				&& this.revervationAtr == ReservationArt.NONE.value) {
			return ContentsStampType.WORK_SUPPORT.nameId;
		}
		if (this.changeClockArt == ChangeClockArt.FIX.value && this.changeCalArt == ChangeCalArt.EARLY_APPEARANCE.value
				&& this.setPreClockArt == SetPreClockArt.NONE.value && this.changeHalfDay == false
				&& this.revervationAtr == ReservationArt.NONE.value) {
			return ContentsStampType.START_SUPPORT_EARLY_APPEARANCE.nameId;
		}
		if (this.changeClockArt == ChangeClockArt.FIX.value && this.changeCalArt == ChangeCalArt.BRARK.value
				&& this.setPreClockArt == SetPreClockArt.NONE.value && this.changeHalfDay == false
				&& this.revervationAtr == ReservationArt.NONE.value) {
			return ContentsStampType.START_SUPPORT_BREAK.nameId;
		}
		if (this.changeClockArt == ChangeClockArt.GOING_TO_WORK.value && this.changeCalArt == ChangeCalArt.BRARK.value
				&& this.setPreClockArt == SetPreClockArt.NONE.value && this.changeHalfDay == false
				&& this.revervationAtr == ReservationArt.RESERVATION.value) {
			return ContentsStampType.RESERVATION.nameId;
		}
		if (this.changeClockArt == ChangeClockArt.GOING_TO_WORK.value && this.changeCalArt == ChangeCalArt.BRARK.value
				&& this.setPreClockArt == SetPreClockArt.NONE.value && this.changeHalfDay == false
				&& this.revervationAtr == ReservationArt.CANCEL_RESERVATION.value) {
			return ContentsStampType.CANCEL_RESERVATION.nameId;
		}

		return null;
	}

	public String getCorrectTimeString(StampMeans mean) {

		if (mean == null) {
			return " ";
		}

		switch (mean) {
		case NAME_SELECTION:
			return I18NText.getText("KDP002_120");
		case FINGER_AUTHC:
			return I18NText.getText("KDP002_120");
		case IC_CARD:
			return I18NText.getText("KDP002_120");
		case INDIVITION:
			return I18NText.getText("KDP002_120");
		case PORTAL:
			return I18NText.getText("KDP002_120");
		case SMART_PHONE:
			return I18NText.getText("KDP002_121");
		case TIME_CLOCK:
			return I18NText.getText("KDP002_122");
		case TEXT:
			return " ";
		case RICOH_COPIER:
			return I18NText.getText("KDP002_120");
		default:
			break;
		}

		return " ";
	}

	public String getTimeString(Integer t) {
		int hours = t / 60;
		int minutes = t % 60;
		return String.format("%02d:%02d", hours, minutes);
	}

}
