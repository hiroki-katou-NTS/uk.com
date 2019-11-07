package nts.uk.ctx.hr.develop.dom.interview;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
@Getter
public class InterviewRecordContent extends DomainObject{
 
	/** ヒアリング項目ID **/
	private String interviewItemId;
	/** 表示順 **/
	private int displayNumber; 
	/** 分析区分カテゴリID **/
	private Optional<String> analysisCategoryId;
	/**分析区分項目 **/ 
	private Optional<List<AnalysisItem>> listAnalysisItemId;
	/** 確認結果 **/
	private Optional<String> recordContent;
	public InterviewRecordContent(String interviewItemId, int displayNumber, Optional<String> analysisCategoryId,
			List<AnalysisItem> listAnalysisItemId, String recordContent) {
		super();
		this.interviewItemId = interviewItemId;
		this.displayNumber = displayNumber;
		this.analysisCategoryId = analysisCategoryId;
		this.listAnalysisItemId = Optional.ofNullable(listAnalysisItemId);
		this.recordContent = Optional.ofNullable(recordContent);
	}
}
