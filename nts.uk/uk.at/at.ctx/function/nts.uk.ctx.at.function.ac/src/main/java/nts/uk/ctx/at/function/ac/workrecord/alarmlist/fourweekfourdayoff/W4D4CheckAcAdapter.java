package nts.uk.ctx.at.function.ac.workrecord.alarmlist.fourweekfourdayoff;

import java.util.Optional;
import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.at.function.dom.adapter.workrecord.alarmlist.fourweekfourdayoff.W4D4CheckAdapter;
import nts.uk.ctx.at.function.dom.alarm.alarmdata.ValueExtractAlarm;
import nts.uk.ctx.at.record.pub.workrecord.alarmlist.fourweekfourdayoff.AlarmExtractionValue4W4DExport;
import nts.uk.ctx.at.record.pub.workrecord.alarmlist.fourweekfourdayoff.W4D4CheckPub;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class W4D4CheckAcAdapter implements W4D4CheckAdapter {

	@Inject
	private W4D4CheckPub w4D4CheckPub;

	@Override
	public Optional<ValueExtractAlarm> checkHoliday(String workplaceID, String employeeID, DatePeriod period) {

		Optional<AlarmExtractionValue4W4DExport> optAlarmExport = w4D4CheckPub.checkHoliday(workplaceID, employeeID,
				period);
		if (optAlarmExport.isPresent()) {			
			AlarmExtractionValue4W4DExport alarmExport = optAlarmExport.get();
			ValueExtractAlarm alarmImport = new ValueExtractAlarm(workplaceID, employeeID, alarmExport.getDatePeriod(),
					alarmExport.getClassification(), alarmExport.getAlarmItem(), alarmExport.getAlarmValueMessage(),
					alarmExport.getComment());
			return Optional.of(alarmImport);
		} else {

			return Optional.empty();
		}

	}

}
