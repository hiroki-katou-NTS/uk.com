/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.pubimp.schedule.basicschedule;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleRepository;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone.WorkScheduleTimeZone;
import nts.uk.ctx.at.schedule.pub.schedule.basicschedule.ScBasicScheduleExport;
import nts.uk.ctx.at.schedule.pub.schedule.basicschedule.ScBasicSchedulePub;
import nts.uk.ctx.at.schedule.pub.schedule.basicschedule.ScWorkBreakTimeExport;
import nts.uk.ctx.at.schedule.pub.schedule.basicschedule.ScWorkScheduleExport;
import nts.uk.ctx.at.schedule.pub.schedule.basicschedule.ScheduleTimeSheetExport;
import nts.uk.ctx.at.schedule.pub.schedule.basicschedule.ShortWorkingTimeSheetExport;
import nts.uk.ctx.at.schedule.pub.schedule.basicschedule.WorkScheduleTimeZoneExport;
import nts.uk.shr.com.time.TimeWithDayAttr;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;

/**
 * The Class ScBasicSchedulePubImpl.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ScBasicSchedulePubImpl implements ScBasicSchedulePub {

	/** The repository. */
	@Inject
	private BasicScheduleRepository repository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.pub.schedule.basicschedule.ScBasicSchedulePub#
	 * findById(java.lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public Optional<ScBasicScheduleExport> findById(String employeeId, GeneralDate baseDate) {
		return this.repository.find(employeeId, baseDate).map(domain -> this.convertExport(domain));
	}
	
	@Override
	public Optional<ScWorkScheduleExport> findByIdNew(String employeeId, GeneralDate baseDate) {
	// return file here
		try {
			String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
			File fXmlFile = new File(currentPath + "\\datatest\\staff.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			
			doc.getDocumentElement().normalize();
			
			String testXml = doc.getDocumentElement().getNodeName();
			String sId = "";
			GeneralDate date = GeneralDate.today();
			Element eElement = (Element) doc.getElementsByTagName("data").item(0);
			String sid = eElement.getElementsByTagName("EmployeeId").item(0).getTextContent();
			String dateXml = eElement.getElementsByTagName("Date").item(0).getTextContent();
			String workType = eElement.getElementsByTagName("WorkType").item(0).getTextContent();
			String workTime = eElement.getElementsByTagName("WorkTime").item(0).getTextContent();
			List<ScheduleTimeSheetExport> scheduleTimeSheetExports = new ArrayList<ScheduleTimeSheetExport>(); 
			NodeList nodeScheduleTimeSheetExport = eElement.getElementsByTagName("ScheduleTimeSheetExport");
			for (int itr = 0; itr < nodeScheduleTimeSheetExport.getLength(); itr++) {
				Node node = nodeScheduleTimeSheetExport.item(itr); 
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) node; 
					Integer workNo = Integer.parseInt(e.getElementsByTagName("WorkNo").item(0).getTextContent());
					Integer attendance = Integer.parseInt(e.getElementsByTagName("Attendance").item(0).getTextContent());
					Integer leaveWork = Integer.parseInt(e.getElementsByTagName("LeaveWork").item(0).getTextContent());
					ScheduleTimeSheetExport scheduleTimeSheetExport = new ScheduleTimeSheetExport(workNo, new TimeWithDayAttr(attendance), new TimeWithDayAttr(leaveWork));
					
					scheduleTimeSheetExports.add(scheduleTimeSheetExport);
				}
			}
			List<ShortWorkingTimeSheetExport> shortWorkingTimeSheetExports = new ArrayList<ShortWorkingTimeSheetExport>(); 
			NodeList nodeShortWorkingTimeSheetExports = eElement.getElementsByTagName("ShortWorkingTimeSheetExport");
			for (int itr = 0; itr < nodeShortWorkingTimeSheetExports.getLength(); itr++) {
				Node node = nodeShortWorkingTimeSheetExports.item(itr); 
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) node; 
					Integer shortWorkTimeFrameNo = Integer.parseInt(e.getElementsByTagName("ShortWorkTimeFrameNo").item(0).getTextContent());
					Integer childCareAttr = Integer.parseInt(e.getElementsByTagName("ChildCareAttr").item(0).getTextContent());
					Integer startTime = Integer.parseInt(e.getElementsByTagName("StartTime").item(0).getTextContent());
					Integer endTime = Integer.parseInt(e.getElementsByTagName("EndTime").item(0).getTextContent());
					Integer deductionTime = Integer.parseInt(e.getElementsByTagName("DeductionTime").item(0).getTextContent());
					Integer shortTime = Integer.parseInt(e.getElementsByTagName("ShortTime").item(0).getTextContent());
					
					ShortWorkingTimeSheetExport shortWorkingTimeSheetExport = new ShortWorkingTimeSheetExport(
							shortWorkTimeFrameNo,
							childCareAttr,
							startTime,
							endTime,
							deductionTime,
							shortTime);
					shortWorkingTimeSheetExports.add(shortWorkingTimeSheetExport);
				}
			}
			ScWorkScheduleExport scheduleExport = new ScWorkScheduleExport(
					sid,
					date,
					workType,
					workTime,
					scheduleTimeSheetExports,
					shortWorkingTimeSheetExports);
			return Optional.of(scheduleExport);
			
			
		}catch(Exception e) {
			return Optional.empty();
		}
	}
	/*
	 * -PhuongDV- for test
	 * (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.pub.schedule.basicschedule.ScBasicSchedulePub#findById(java.lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public String findByIdTest(String employeeId, GeneralDate baseDate) {
		return "";
	}
	
	@Override
	public List<ScBasicScheduleExport> findById(List<String> employeeID, DatePeriod date) {
		return this.repository.findSomePropertyWithJDBC(employeeID, date).stream().map(domain -> this.convertExport(domain)).collect(Collectors.toList());
	}

	@Override
	public List<ScWorkBreakTimeExport> findWorkBreakTime(String employeeId, GeneralDate baseDate) {
		return this.repository.findWorkBreakTime(employeeId, baseDate).stream()
				.map(x -> new ScWorkBreakTimeExport(x.getScheduleBreakCnt().v(), x.getScheduledStartClock(),
						x.getScheduledEndClock()))
				.collect(Collectors.toList());
	}

	/**
	 * Convert export.
	 *
	 * @param domain
	 *            the domain
	 * @return the sc basic schedule export
	 */
	private ScBasicScheduleExport convertExport(BasicSchedule domain) {
		ScBasicScheduleExport export = new ScBasicScheduleExport();
		export.setDate(domain.getDate());
		export.setEmployeeId(domain.getEmployeeId());
		export.setWorkScheduleTimeZones(domain.getWorkScheduleTimeZones().stream()
				.map(timezone -> this.convertTimeZoneExport(timezone)).collect(Collectors.toList()));
		export.setWorkTimeCode(domain.getWorkTimeCode());
		export.setWorkTypeCode(domain.getWorkTypeCode());
		return export;
	}

	/**
	 * Convert time zone export.
	 *
	 * @param timezone
	 *            the timezone
	 * @return the work schedule time zone export
	 */
	private WorkScheduleTimeZoneExport convertTimeZoneExport(WorkScheduleTimeZone timezone) {
		WorkScheduleTimeZoneExport export = new WorkScheduleTimeZoneExport();
		export.setBounceAtr(timezone.getBounceAtr().value);
		export.setScheduleCnt(timezone.getScheduleCnt());
		export.setScheduleStartClock(timezone.getScheduleStartClock().valueAsMinutes());
		export.setScheduleEndClock(timezone.getScheduleEndClock().valueAsMinutes());
		return export;
	}

	@Override
	public GeneralDate acquireMaxDateBasicSchedule(List<String> sIds) {
		return this.repository.findMaxDateByListSid(sIds);
	}

}
