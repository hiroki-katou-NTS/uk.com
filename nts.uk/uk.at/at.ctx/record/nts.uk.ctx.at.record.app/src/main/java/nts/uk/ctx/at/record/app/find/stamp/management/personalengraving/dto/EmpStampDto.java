package nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto;

import java.util.Optional;

import lombok.Data;
import nts.uk.ctx.at.record.dom.stamp.management.StampType;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationName;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.RefectActualResult;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampLocationInfor;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * @author anhdt
 *
 */
@Data
public class EmpStampDto {

	private String cardNumber;
	private String stampDateTime;
	private Integer authcMethod;
	private Integer stampMeans;
	
	private boolean changeHalfDay;
	private Integer goOutArt;
	private Integer setPreClockArt;
	private Integer changeClockArt;
	private Integer changeCalArt;
	private RefectActualResult refActualResults;
	private boolean reflectedCategory;
	private Optional<StampLocationInfor> locationInfor;
	private Optional<AttendanceTime> attendanceTime = Optional.empty();
	
	private String employeeId;
	private WorkLocationName workLocationName;

	public EmpStampDto(Stamp stamp) {
		this.cardNumber = stamp.getCardNumber().v();
		this.stampDateTime = stamp.getStampDateTime().toString();
		this.authcMethod = stamp.getRelieve().getAuthcMethod().value;
		this.stampMeans = stamp.getRelieve().getStampMeans().value;

		StampType type = stamp.getType();
		this.changeHalfDay = type.isChangeHalfDay();
		this.goOutArt = type.getGoOutArt().isPresent() ? type.getGoOutArt().get().value : null;
		this.setPreClockArt = type.getSetPreClockArt().value;
		this.changeClockArt = type.getChangeClockArt().value;
		this.changeCalArt = type.getChangeCalArt().value;
	}

}
