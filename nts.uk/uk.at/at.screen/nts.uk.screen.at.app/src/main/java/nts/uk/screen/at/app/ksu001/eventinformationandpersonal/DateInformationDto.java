package nts.uk.screen.at.app.ksu001.eventinformationandpersonal;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import nts.arc.i18n.I18NText;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.management.DateInformation;

/**
 * 
 */
@Getter
@RequiredArgsConstructor
public class DateInformationDto {
	/** 年月日 **/
	public  GeneralDate ymd;
	/** 曜日 **/
	public  int dayOfWeek;
	/** 祝日であるか **/
	public  boolean isHoliday;
	/** 特定日であるか **/
	public  boolean isSpecificDay;
	
	public boolean isToday;
	/** 職場行事名称 **/
	public  String optWorkplaceEventName;
	/** 会社行事名称 **/
	public  String optCompanyEventName;
	/** 職場の特定日名称リスト **/
	public  List<String> listSpecDayNameWorkplace;
	/** 会社の特定日名称リスト **/
	public  List<String> listSpecDayNameCompany;
	
	public String htmlTooltip;
	
	public DateInformationDto(DateInformation domain) {
		super();
		this.ymd = domain.getYmd();
		this.dayOfWeek = domain.getDayOfWeek().value;
		this.isHoliday = domain.isHoliday();
		this.isSpecificDay = domain.isSpecificDay();
		this.optWorkplaceEventName = domain.getOptWorkplaceEventName().isPresent() ? domain.getOptWorkplaceEventName().get().toString() : I18NText.getText("KSU001_4019");
		this.optCompanyEventName = domain.getOptCompanyEventName().isPresent() ? domain.getOptCompanyEventName().get().toString() : I18NText.getText("KSU001_4019");
		this.listSpecDayNameWorkplace = domain.getListSpecDayNameWorkplace().stream().map(x -> {
			if (x != null) {
				return x.toString();
			}
			return "";
		}).collect(Collectors.toList());
		this.listSpecDayNameCompany = domain.getListSpecDayNameCompany().stream().map(y -> {
			if (y != null) {
				return y.toString();
			}
			return "";
		}).collect(Collectors.toList());
		
		this.isToday = domain.getYmd().equals(GeneralDate.today());
		
		if(this.isSpecificDay || domain.getOptWorkplaceEventName().isPresent() || domain.getOptCompanyEventName().isPresent()){
			val htmlTooltip = new StringBuilder();
			htmlTooltip.append("<table>");
				htmlTooltip.append("<tr>");
					htmlTooltip.append("<td>");
					htmlTooltip.append(I18NText.getText("KSU001_4014"));
					htmlTooltip.append("</td>");
		
					htmlTooltip.append("<td>");
					htmlTooltip.append(I18NText.getText("KSU001_4018") + " " + this.optCompanyEventName);
					htmlTooltip.append("</td>");
				htmlTooltip.append("</tr>");
				
				htmlTooltip.append("<tr>");
					htmlTooltip.append("<td>");
					htmlTooltip.append(I18NText.getText("KSU001_4015"));
					htmlTooltip.append("</td>");
		
					htmlTooltip.append("<td>");
					htmlTooltip.append(I18NText.getText("KSU001_4018") + " " + this.optWorkplaceEventName);
					htmlTooltip.append("</td>");
				htmlTooltip.append("</tr>");
				
			if (this.listSpecDayNameCompany.isEmpty()) {
				htmlTooltip.append("<tr>");
					htmlTooltip.append("<td>");
					htmlTooltip.append(I18NText.getText("KSU001_4016"));
					htmlTooltip.append("</td>");

					htmlTooltip.append("<td>");
					htmlTooltip.append(I18NText.getText("KSU001_4018") + " " + I18NText.getText("KSU001_4019"));
					htmlTooltip.append("</td>");
				htmlTooltip.append("</tr>");
			} else {
				for (int i = 0; i < this.listSpecDayNameCompany.size(); i++) {
					if (i == 0) {
						htmlTooltip.append("<tr>");
							htmlTooltip.append("<td>");
							htmlTooltip.append(I18NText.getText("KSU001_4016"));
							htmlTooltip.append("</td>");
	
							htmlTooltip.append("<td>");
							htmlTooltip.append(I18NText.getText("KSU001_4018") + " " + this.listSpecDayNameCompany.get(0));
							htmlTooltip.append("</td>");
						htmlTooltip.append("</tr>");
					} else {
						htmlTooltip.append("<tr>");
							htmlTooltip.append("<td>");
							htmlTooltip.append("");
							htmlTooltip.append("</td>");
	
							htmlTooltip.append("<td>");
							htmlTooltip.append(I18NText.getText("KSU001_4018") + " " + this.listSpecDayNameCompany.get(i));
							htmlTooltip.append("</td>");
						htmlTooltip.append("</tr>");
					}
				}
			}
			
			if (this.listSpecDayNameWorkplace.isEmpty()) {
				htmlTooltip.append("<tr>");
					htmlTooltip.append("<td>");
					htmlTooltip.append(I18NText.getText("KSU001_4017"));
					htmlTooltip.append("</td>");

					htmlTooltip.append("<td>");
					htmlTooltip.append(I18NText.getText("KSU001_4018") + " " + I18NText.getText("KSU001_4019"));
					htmlTooltip.append("</td>");
				htmlTooltip.append("</tr>");
			} else {
				for (int i = 0; i < this.listSpecDayNameWorkplace.size(); i++) {
					if (i == 0) {
						htmlTooltip.append("<tr>");
							htmlTooltip.append("<td>");
							htmlTooltip.append(I18NText.getText("KSU001_4017"));
							htmlTooltip.append("</td>");
	
							htmlTooltip.append("<td>");
							htmlTooltip.append(I18NText.getText("KSU001_4018") + " " + this.listSpecDayNameWorkplace.get(0));
							htmlTooltip.append("</td>");
						htmlTooltip.append("</tr>");
					} else {
						htmlTooltip.append("<tr>");
							htmlTooltip.append("<td>");
							htmlTooltip.append("");
							htmlTooltip.append("</td>");
	
							htmlTooltip.append("<td>");
							htmlTooltip.append(I18NText.getText("KSU001_4018") + " " + this.listSpecDayNameWorkplace.get(i));
							htmlTooltip.append("</td>");
						htmlTooltip.append("</tr>");
					}
				}
			}
			htmlTooltip.append("</table>");
			this.htmlTooltip = htmlTooltip.toString();
		}
	}	
	
	
}
