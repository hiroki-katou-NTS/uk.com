package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import java.util.ArrayList;
import java.util.List;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.location.GeoCoordinate;
import nts.uk.ctx.at.record.dom.breakorgoout.enums.GoingOutReason;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ChangeCalArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ChangeClockArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SetPreClockArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampType;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailywork.worktime.overtimedeclaration.OvertimeDeclaration;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
/**
 * 
 * @author tutk
 *
 */
public class StampHelper {

	public static Stamp getStampDefault() {
		return new Stamp(
				new StampNumber("stampNumber"),
				GeneralDateTime.now(), 
				new Relieve(
						AuthcMethod.valueOf(0), 
						StampMeans.valueOf(0)), 
				new StampType(
						false,
						GoingOutReason.valueOf(0), 
						SetPreClockArt.valueOf(0), 
						ChangeClockArt.valueOf(0),
						ChangeCalArt.valueOf(0)),
				new RefectActualResult(
						"cardNumberSupport", 
						new WorkLocationCD("workLocationCD"), 
						new WorkTimeCode("workTimeCode"), 
						new OvertimeDeclaration(
								new AttendanceTime(1),
								new AttendanceTime(2))), 
				false,
				new StampLocationInfor(
						false, 
						getGeoCoordinateDefault()));
	}
	public static Stamp getStampByChangeClockArt(String stampNumber,ChangeClockArt changeClockArt) {
		return new Stamp(
				new StampNumber(stampNumber),
				GeneralDateTime.now(), 
				new Relieve(
						AuthcMethod.valueOf(0), 
						StampMeans.valueOf(0)), 
				new StampType(
						false,
						GoingOutReason.valueOf(0), 
						SetPreClockArt.valueOf(0), 
						changeClockArt,
						ChangeCalArt.valueOf(0)),
				new RefectActualResult(
						"cardNumberSupport", 
						new WorkLocationCD("workLocationCD"), 
						new WorkTimeCode("workTimeCode"), 
						new OvertimeDeclaration(
								new AttendanceTime(1),
								new AttendanceTime(2))), 
				false,
				new StampLocationInfor(
						false, 
						getGeoCoordinateDefault()));
	}
	public static List<Stamp> getListStampDefault() {
		List<Stamp> data = new ArrayList<>();
		data.add(getStampDefault());
		data.add(new Stamp(new StampNumber("stampNumber1"), GeneralDateTime.now(),
				new Relieve(AuthcMethod.valueOf(0), StampMeans.valueOf(0)),
				new StampType(false, GoingOutReason.valueOf(0), SetPreClockArt.valueOf(0), ChangeClockArt.valueOf(0),
						ChangeCalArt.valueOf(0)),
				new RefectActualResult("cardNumberSupport", new WorkLocationCD("workLocationCD"),
						new WorkTimeCode("workTimeCode"),
						new OvertimeDeclaration(new AttendanceTime(1), new AttendanceTime(2))),
				false, new StampLocationInfor(false, getGeoCoordinateDefault())));
		data.add(new Stamp(new StampNumber("stampNumber"), GeneralDateTime.now().addDays(1),
				new Relieve(AuthcMethod.valueOf(0), StampMeans.valueOf(0)),
				new StampType(false, GoingOutReason.valueOf(0), SetPreClockArt.valueOf(0), ChangeClockArt.valueOf(0),
						ChangeCalArt.valueOf(0)),
				new RefectActualResult("cardNumberSupport", new WorkLocationCD("workLocationCD"),
						new WorkTimeCode("workTimeCode"),
						new OvertimeDeclaration(new AttendanceTime(1), new AttendanceTime(2))),
				false, new StampLocationInfor(false, getGeoCoordinateDefault())));
		return data;
	}
	
	public static StampType getStampTypeDefault() {
		return new StampType(
				false,
				GoingOutReason.valueOf(0), 
				SetPreClockArt.valueOf(0), 
				ChangeClockArt.valueOf(0),
				ChangeCalArt.valueOf(0));
	}
	public static StampType getStampTypeHaveInput(boolean changeHalfDay, GoingOutReason goOutArt, SetPreClockArt setPreClockArt,
			ChangeClockArt changeClockArt, ChangeCalArt changeCalArt) {
		return new StampType(
				changeHalfDay, goOutArt, setPreClockArt, changeClockArt, changeCalArt);
	}
	
	public static OvertimeDeclaration getOvertimeDeclarationDefault() {
		return new OvertimeDeclaration(
				new AttendanceTime(1), new AttendanceTime(10));
	}
	
	public static RefectActualResult getRefectActualResultDefault() {
		return new RefectActualResult("cardNumberSupport", new WorkLocationCD("workLocationCD"),
				new WorkTimeCode("workTimeCode"),
				getOvertimeDeclarationDefault());
	}
	
	public static Relieve getRelieveDefault() {
		return new Relieve(AuthcMethod.valueOf(1), StampMeans.valueOf(0));
	}
	
	public static StampLocationInfor getStampLocationInforDefault() {
		return new StampLocationInfor(
				false, 
				getGeoCoordinateDefault());
	}
	public static GeoCoordinate getGeoCoordinateDefault() {
		return new GeoCoordinate(1, 2);
		
	}

	public static StampCard getStampCardByInput(String stampCardId, String stampNumber, GeneralDate registerDate) {
		return new StampCard(stampCardId, "employeeId",new StampNumber(stampNumber), registerDate, new ContractCode("contractCd"));
		
	}
	
}
