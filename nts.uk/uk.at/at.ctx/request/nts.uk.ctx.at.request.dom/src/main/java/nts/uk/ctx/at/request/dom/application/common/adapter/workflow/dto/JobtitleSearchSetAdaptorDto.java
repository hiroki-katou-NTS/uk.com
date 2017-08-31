package nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto;

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
public class JobtitleSearchSetAdaptorDto extends AggregateRoot {
	/** 会社ID */
	private String companyId;
	/** 職位ID */
	private String jobId;

	private int searchSetFlg;

	public static JobtitleSearchSetAdaptorDto createSimpleFromJavaType(String companyId, String jobId, int searchSetFlg) {
		return new JobtitleSearchSetAdaptorDto(companyId, jobId, searchSetFlg);
	}
}
