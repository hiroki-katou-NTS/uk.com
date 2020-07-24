package nts.uk.screen.at.app.ksu001.eventinformationandpersonal;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
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
	/** 職場行事名称 **/
	public  String optWorkplaceEventName;
	/** 会社行事名称 **/
	public  String optCompanyEventName;
	/** 職場の特定日名称リスト **/
	public  List<String> listSpecDayNameWorkplace;
	/** 会社の特定日名称リスト **/
	public  List<String> listSpecDayNameCompany;
	public DateInformationDto(DateInformation domain) {
		super();
		this.ymd = domain.getYmd();
		this.dayOfWeek = domain.getDayOfWeek().value;
		this.isHoliday = domain.isHoliday();
		this.isSpecificDay = domain.isSpecificDay();
		this.optWorkplaceEventName = domain.getOptWorkplaceEventName().isPresent() ? domain.getOptWorkplaceEventName().get().toString() : null;
		this.optCompanyEventName = domain.getOptCompanyEventName().isPresent() ? domain.getOptCompanyEventName().get().toString() : null;
		this.listSpecDayNameWorkplace = domain.getListSpecDayNameWorkplace().stream().map(x -> {
			if (x != null) {
				return x.toString();
			}
			return null;
		}).collect(Collectors.toList());
		this.listSpecDayNameCompany = domain.getListSpecDayNameCompany().stream().map(y -> {
			if (y != null) {
				return y.toString();
			}
			return null;
		}).collect(Collectors.toList());
	}	
	
	
}
