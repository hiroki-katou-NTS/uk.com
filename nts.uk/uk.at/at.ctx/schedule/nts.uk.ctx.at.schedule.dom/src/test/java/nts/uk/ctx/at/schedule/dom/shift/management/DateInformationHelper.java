package nts.uk.ctx.at.schedule.dom.shift.management;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.DayOfWeek;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.EventName;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.specificdate.primitives.SpecificName;

public class DateInformationHelper {

	
	
	public static List<SpecificName> listNameWorkplace = new ArrayList<SpecificName>(
			Arrays.asList(
					new SpecificName ("NameWorkplace1"),
					new SpecificName ("NameWorkplace2"),
					new SpecificName ("NameWorkplace3")
					));
	
	public static List<SpecificName> listNameCompany= new ArrayList<SpecificName>(
			Arrays.asList(
					new SpecificName ("NameCompany1"),
					new SpecificName ("NameCompany2"),
					new SpecificName ("NameCompany3")
					));
	public static DateInformation DUMMY = new DateInformation(
			GeneralDate.today(),
			DayOfWeek.MONDAY,
			true,
			true,
			Optional.ofNullable(new EventName("OptWorkplaceEventName")),
			Optional.ofNullable(new EventName("OptCompanyEventName")),
			listNameWorkplace,
			listNameCompany);		
			
	
}
