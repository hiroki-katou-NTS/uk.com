package nts.uk.ctx.at.request.dom.application.common.adapter.bs.jobtitle.dto;

/*import java.util.List;
import nts.uk.shr.com.history.DateHistoryItem;*/
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AffJobTitleHistoryImport {
	/** The job title id. */
	// 職位ID
	private String jobTitleId;

	/** The job title code. */
	// 職位コード
	private String jobTitleCode;

	/** The job title name. */
	// 職位名称
	private String jobTitleName;
}
