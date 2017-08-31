package nts.uk.ctx.workflow.pub.jobtitlesearchset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 職位別のサーチ設定Dto
 * 
 * @author vunv
 *
 */
@Getter
@AllArgsConstructor
public class JobtitleSearchSetDto extends AggregateRoot {
	/** 会社ID */
	private String companyId;
	/** 職位ID */
	private String jobId;

	private int searchSetFlg;

	public static JobtitleSearchSetDto createSimpleFromJavaType(String companyId, String jobId, int searchSetFlg) {
		return new JobtitleSearchSetDto(companyId, jobId, searchSetFlg);
	}
}
