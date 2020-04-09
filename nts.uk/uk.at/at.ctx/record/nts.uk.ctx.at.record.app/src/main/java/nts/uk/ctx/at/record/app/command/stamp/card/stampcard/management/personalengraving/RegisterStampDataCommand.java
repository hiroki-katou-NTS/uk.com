package nts.uk.ctx.at.record.app.command.stamp.card.stampcard.management.personalengraving;

import java.util.Optional;

import lombok.Data;
import nts.arc.time.GeneralDateTime;
import nts.gul.location.GeoCoordinate;
import nts.uk.ctx.at.record.dom.breakorgoout.enums.GoingOutReason;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.stamp.management.ButtonType;
import nts.uk.ctx.at.record.dom.stamp.management.ChangeCalArt;
import nts.uk.ctx.at.record.dom.stamp.management.ChangeClockArt;
import nts.uk.ctx.at.record.dom.stamp.management.ReservationArt;
import nts.uk.ctx.at.record.dom.stamp.management.SetPreClockArt;
import nts.uk.ctx.at.record.dom.stamp.management.StampType;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationCD;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.AuthcMethod;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.RefectActualResult;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Relieve;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampMeans;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailywork.worktime.overtimedeclaration.OvertimeDeclaration;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;

/**
 * @author anhdt 
 */
@Data
public class RegisterStampDataCommand {

	private GeneralDateTime datetime;
	private Integer authcMethod;
	private Integer stampMeans;

	private Integer reservationArt;
	private boolean changeHalfDay;
	private Integer goOutArt;
	private Integer setPreClockArt;
	private Integer changeClockArt;
	private Integer changeCalArt;

	private String cardNumberSupport;
	private String workLocationCD;
	private String workTimeCode;
	private Optional<OvertimeDeclaration> overtimeDeclaration;
	private Integer overTime;
	private Integer overLateNightTime;

	private Double latitude;
	private Double longitude;
	
	private Integer empInfoTerCode;
	
	public Relieve toRelieve() {
		return new Relieve(AuthcMethod.valueOf(authcMethod), StampMeans.valueOf(stampMeans));
	}

	public ButtonType toButtonType() {
		StampType stampType = new StampType(changeHalfDay, GoingOutReason.valueOf(goOutArt),
				SetPreClockArt.valueOf(setPreClockArt), ChangeClockArt.valueOf(changeClockArt),
				ChangeCalArt.valueOf(changeCalArt));
		return new ButtonType(ReservationArt.valueOf(reservationArt), stampType);
	}

	public RefectActualResult toRefectActualResult() {
		return new RefectActualResult(cardNumberSupport, new WorkLocationCD(workLocationCD),
				new WorkTimeCode(workTimeCode),
				new OvertimeDeclaration(new AttendanceTime(overTime), new AttendanceTime(overLateNightTime)));
	}
	
	public GeoCoordinate toGeoCoordinate() {
		return new GeoCoordinate(latitude, longitude);
	}
	
	public EmpInfoTerminalCode toEmpInfoTerCode(){
		return new EmpInfoTerminalCode(empInfoTerCode);
	}

}
