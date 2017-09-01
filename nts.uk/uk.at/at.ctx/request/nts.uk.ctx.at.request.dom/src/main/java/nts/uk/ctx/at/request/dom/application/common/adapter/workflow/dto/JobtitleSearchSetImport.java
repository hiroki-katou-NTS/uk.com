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
public class JobtitleSearchSetImport {
	/** 会社ID */
	private String companyId;
	/** 職位ID */
	private String jobId;

	private int searchSetFlg;

	public static JobtitleSearchSetImport createSimpleFromJavaType(String companyId, String jobId, int searchSetFlg) {
		return new JobtitleSearchSetImport(companyId, jobId, searchSetFlg);
	}
}
