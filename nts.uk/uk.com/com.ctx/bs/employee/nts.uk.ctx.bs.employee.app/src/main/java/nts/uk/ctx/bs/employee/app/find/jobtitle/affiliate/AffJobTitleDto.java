/**
 * 
 */
package nts.uk.ctx.bs.employee.app.find.jobtitle.affiliate;

import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.AffJobTitleHistoryItem;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.AffJobTitleHistory_ver1;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

/**
 * @author danpv
 *
 */
@Setter
public class AffJobTitleDto extends PeregDomainDto {

	/**
	 * 期間
	 */
	@PeregItem("IS00076")
	private DateHistoryItem dateHistoryItem;

	/**
	 * 開始日
	 */
	@PeregItem("IS00077")
	private GeneralDate startDate;

	/**
	 * 終了日
	 */
	@PeregItem("IS00078")
	private GeneralDate endDate;

	/**
	 * 職位CD
	 */
	@PeregItem("IS00079")
	private String jobTitleId;

	/**
	 * 備考
	 */
	@PeregItem("IS00080")
	private String note;

	public AffJobTitleDto() {

	}

	public AffJobTitleDto(String recordId, String employeeId) {
		super(recordId, employeeId, null);
	}

	public static AffJobTitleDto createFromDomain(AffJobTitleHistoryItem histItem, AffJobTitleHistory_ver1 history) {
		AffJobTitleDto dto = new AffJobTitleDto(histItem.getHistoryId(), histItem.getEmployeeId());
		dto.setDateHistoryItem(history.getHistoryItems().get(0));
		dto.setStartDate(history.getHistoryItems().get(0).start());
		dto.setEndDate(history.getHistoryItems().get(0).end());
		dto.setJobTitleId(histItem.getJobTitleId());
		dto.setNote(histItem.getNote().v());
		return dto;
	}

}
