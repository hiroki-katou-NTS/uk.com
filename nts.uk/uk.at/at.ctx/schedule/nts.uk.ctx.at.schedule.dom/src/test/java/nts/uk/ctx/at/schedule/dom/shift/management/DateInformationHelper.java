package nts.uk.ctx.at.schedule.dom.shift.management;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.DayOfWeek;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.CompanyEvent;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.EventName;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event.WorkplaceEvent;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.HolidayName;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.company.CompanySpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.item.SpecificDateItem;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.primitives.SpecificName;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.workplace.WorkplaceSpecificDateItem;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

public class DateInformationHelper {

	public static List<SpecificName> listNameWorkplace = new ArrayList<SpecificName>(
			Arrays.asList(new SpecificName("NameWorkplace1"), new SpecificName("NameWorkplace2"),
					new SpecificName("NameWorkplace3")));

	public static List<SpecificName> listNameCompany = new ArrayList<SpecificName>(Arrays.asList(
			new SpecificName("NameCompany1"), new SpecificName("NameCompany2"), new SpecificName("NameCompany3")));
	public static DateInformation DUMMY = new DateInformation(GeneralDate.today(), DayOfWeek.MONDAY, 
			true, Optional.ofNullable(new HolidayName("OptHolidayName")),
			true, Optional.ofNullable(new EventName("OptWorkplaceEventName")),
			Optional.ofNullable(new EventName("OptCompanyEventName")), listNameWorkplace, listNameCompany);

	public static TargetOrgIdenInfor getTargetOrgIdenInforWorkplace() {
		return TargetOrgIdenInfor.creatIdentifiWorkplace("workplaceId") ;

	}

	public static TargetOrgIdenInfor getTargetOrgIdenInforWorkplaceGroup() {
		return TargetOrgIdenInfor.creatIdentifiWorkplaceGroup("workplaceGroupId");

	}

	public static CompanyEvent getCompanyEventDefault() {
		return CompanyEvent.createFromJavaType("companyId", GeneralDate.today(), "EventName");

	}

	public static List<CompanySpecificDateItem> getListDefaultByNumberItem(int number) {
		List<CompanySpecificDateItem> listData = new ArrayList<>();
		for (int i = 1; i <= number; i++) {
			listData.add(CompanySpecificDateItem.createFromJavaType("companyId", GeneralDate.today().addDays(i + 1), i,
					"specificDateItemName" + i));
		}
		return listData;
	}

	public static List<SpecificDateItem> getListSpecificDateItemByNumberItem(int number) {
		List<SpecificDateItem> listData = new ArrayList<>();
		for (int i = 1; i <= number; i++) {
			listData.add(SpecificDateItem.createFromJavaType("companyId", i % 2, i, "specificName" + i));
		}
		return listData;
	}

	public static WorkplaceEvent getWorkplaceEventDefault() {
		return WorkplaceEvent.createFromJavaType("workplaceId", GeneralDate.today(), "eventName");
	}
	
	public static List<WorkplaceSpecificDateItem> getListWorkplaceSpecificDateItemByNumber(int number) {
		List<WorkplaceSpecificDateItem> listData = new ArrayList<>();
		for (int i = 1; i <= number; i++) {
			listData.add(WorkplaceSpecificDateItem.createFromJavaType("companyId",GeneralDate.today(), i, "specificDateItemName" + i));
		}
		return listData;
	}

}
