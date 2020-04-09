package nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto;

import java.util.Optional;

import lombok.Data;
import nts.arc.i18n.I18NText;
import nts.uk.ctx.at.record.dom.stamp.management.StampType;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.OvertimeDeclaration;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.RefectActualResult;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampLocationInfor;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampMeans;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecord;

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
			this.changeHalfDay = type.isChangeHalfDay();
			this.goOutArt = type.getGoOutArt().isPresent() ? type.getGoOutArt().get().value : null;
			this.setPreClockArt = type.getSetPreClockArt().value;
			this.changeClockArt = type.getChangeClockArt().value;
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
			if(stamp.getLocationInfor().isPresent()) {
				StampLocationInfor stampLocate = stamp.getLocationInfor().get();
				this.outsideAreaAtr = stampLocate.isOutsideAreaAtr();
				this.latitude = stampLocate.getPositionInfor().getLatitude();
				this.longitude = stampLocate.getPositionInfor().getLongitude();
			}
			this.attendanceTime = stamp.getAttendanceTime().isPresent() ? getTimeString(stamp.getAttendanceTime().get().v()) : null;
		}
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
			return I18NText.getText("KDP002_120");
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
