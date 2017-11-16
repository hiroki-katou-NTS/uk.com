package nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 職位別のサーチ設定Import
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
}
