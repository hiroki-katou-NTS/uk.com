package nts.uk.ctx.hr.develop.dom.setting.datedisplay.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author anhdt
 *
 */
@Data
@Builder
public class PeriodDisplaySettingList {
	private String programId;
	private Integer startDateSettingCategory;
	private int startDateSettingNum;
	private int startDateSettingDate;
	private int startDateSettingMonth;
	private Integer endDateSettingCategory;
	private int endDateSettingNum;
	private int endDateSettingDate;
	private int endDateSettingMonth;
}
