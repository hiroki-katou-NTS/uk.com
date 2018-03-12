package nts.uk.ctx.at.function.dom.adapter.eralworkrecorddto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class AlarmCheckTargetConAdapterDto {
	/* 勤務種別でしぼり込む */
	private boolean filterByBusinessType;
	/* 職位でしぼり込む */
	private boolean filterByJobTitle;
	/* 雇用でしぼり込む */
	private boolean filterByEmployment;
	/* 分類でしぼり込む */
	private boolean filterByClassification;
	/* 対象勤務種別 */
	private List<String> lstBusinessTypeCode;
	/* 対象職位 */
	private List<String> lstJobTitleId;
	/* 対象雇用 */
	private List<String> lstEmploymentCode;
	/* 対象分類 */
	private List<String> lstClassificationCode;
	
	public AlarmCheckTargetConAdapterDto(boolean filterByBusinessType, boolean filterByJobTitle,
			boolean filterByEmployment, boolean filterByClassification, List<String> lstBusinessType,
			List<String> lstJobTitle, List<String> lstEmployment, List<String> lstClassification) {
		super();
		this.filterByBusinessType = filterByBusinessType;
		this.filterByJobTitle = filterByJobTitle;
		this.filterByEmployment = filterByEmployment;
		this.filterByClassification = filterByClassification;
		this.lstBusinessTypeCode = lstBusinessType;
		this.lstJobTitleId = lstJobTitle;
		this.lstEmploymentCode = lstEmployment;
		this.lstClassificationCode = lstClassification;
	}
	
	
}
