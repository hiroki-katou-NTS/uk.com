package nts.uk.screen.at.app.query.ksu.ksu002.a.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.management.DateInformation;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.LegalWorkTimeOfEmployee;

/**
 * 社員の法定労働時間Dto
 * @author thanhPV
 *
 */
@NoArgsConstructor
@Data
public class LegalWorkTimeOfEmployeeDto {
	
	/** 社員ID */
	private String sid;
	/** 週の時間 */
	private Integer weeklyEstimateTime = 0;
	/** 月の時間 */
	private Integer monthlyEstimateTime = 0;
	
	private String affWorkPlace;
	
	List<DateInformationDto> dateInfo = new ArrayList<>();
	
	public void setValue(Optional<LegalWorkTimeOfEmployee> domain) {
		domain.ifPresent(e -> {
			this.sid = e.getSid();
			e.getWeeklyEstimateTime().ifPresent(d-> {
				this.weeklyEstimateTime = d.valueAsMinutes();
			});
			this.monthlyEstimateTime = e.getMonthlyEstimateTime().v();
		});
	}
	
	public void setDateInfo(List<DateInformation> domains) {
		dateInfo = domains.stream().map(m -> {
			return new DateInformationDto( m.getYmd(),
					m.getDayOfWeek().value,
					m.isHoliday(),
					m.getHolidayName().map(h -> h.v()).orElse(""),
					m.isSpecificDay(),
					m.getOptWorkplaceEventName().map(h -> h.v()).orElse(""),
					m.getOptCompanyEventName().map(h -> h.v()).orElse(""),
					m.getListSpecDayNameWorkplace().stream().map(w -> w.v()).collect(Collectors.toList()),
					m.getListSpecDayNameCompany().stream().map(w -> w.v()).collect(Collectors.toList()));
		}).collect(Collectors.toList());
	}
	
	
	@NoArgsConstructor
	@AllArgsConstructor
	@Data
	public class DateInformationDto {
		private GeneralDate ymd;
		/** 曜日 **/
		private int dayOfWeek;
		/** 祝日であるか **/
		private boolean isHoliday;
		/** 祝日名称 **/
		private String holidayName;
		/** 特定日であるか **/
		private boolean isSpecificDay;
		/** 職場行事名称 **/
		private String optWorkplaceEventName;
		/** 会社行事名称 **/
		private String optCompanyEventName;
		/** 職場の特定日名称リスト **/
		private List<String> listSpecDayNameWorkplace;
		/** 会社の特定日名称リスト **/
		private List<String> listSpecDayNameCompany;
	}

}

