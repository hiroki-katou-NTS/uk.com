package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ReservationArt;
/**
 * 
 * @author tutk
 *
 */
public class StampRecordHelper {

	public static StampRecord getStampRecord() {
		return new StampRecord(
				new StampNumber("stampNumber"), 
				GeneralDateTime.now(), 
				false, 
				ReservationArt.valueOf(0), 
				Optional.of(new EmpInfoTerminalCode(1000)));
	}
	
	public static List<StampRecord> getListStampRecord() {
		List<StampRecord> data = new ArrayList<>();
		data.add(getStampRecord());
		data.add(new StampRecord(
				new StampNumber("stampNumber1"), 
				GeneralDateTime.now(), 
				false, 
				ReservationArt.valueOf(2), 
				Optional.of(new EmpInfoTerminalCode(1000))));
		data.add(new StampRecord(
				new StampNumber("stampNumber"), 
				GeneralDateTime.now().addDays(1), 
				false, 
				ReservationArt.valueOf(2), 
				Optional.of(new EmpInfoTerminalCode(1000))));
		return data;
	}
	public static StampRecord getStampSetStampArtAndRevervationAtr(boolean stampArt,ReservationArt revervationAtr) {
		return new StampRecord(
				new StampNumber("stampNumber"), 
				GeneralDateTime.now(), 
				stampArt, 
				revervationAtr, 
				Optional.of(new EmpInfoTerminalCode(1000)));
	}
}
