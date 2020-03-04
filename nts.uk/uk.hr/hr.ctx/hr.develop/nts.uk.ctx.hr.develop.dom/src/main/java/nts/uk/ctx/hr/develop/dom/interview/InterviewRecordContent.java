package nts.uk.ctx.hr.develop.dom.interview;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
/**
 * 
 * @author hieult
 *
 */
@Getter
public class InterviewRecordContent extends DomainObject{
 
	/** ヒアリング項目ID **/
	private long interviewItemId;
	/** 表示順 **/
	private int displayNumber; 
	
	/** ヒアリング項目CD **/
	private Optional<String> interviewItemCd;
	/** ヒアリング項目名 **/
	private Optional<String> interviewItemName;
	/** 分析区分カテゴリCD*/
	private Optional<String> analysisCategoryCd;
	/** 分析区分カテゴリID **/
	private long analysisCategoryId;
	/** 分析区分カテゴリ名 **/
	private Optional<String> analysisCategoryName;
	/**分析区分項目 **/ 
	private List<AnalysisItem> listAnalysisItemId;
	/** 確認結果 **/
	private Optional<String> recordContent;
	
	public InterviewRecordContent(long interviewItemId, int displayNumber, String interviewItemCd,
			String interviewItemName,String analysisCategoryCd, long analysisCategoryId,
			String analysisCategoryName, List<AnalysisItem> listAnalysisItemId,
			String recordContent) {
		super();
		this.interviewItemId = interviewItemId;
		this.displayNumber = displayNumber;
		this.interviewItemCd = Optional.ofNullable(interviewItemCd);
		this.interviewItemName = Optional.ofNullable(interviewItemName);
		this.analysisCategoryCd = Optional.ofNullable(analysisCategoryCd);
		this.analysisCategoryId = analysisCategoryId;
		this.analysisCategoryName = Optional.ofNullable(analysisCategoryName);
		this.listAnalysisItemId = listAnalysisItemId;
		this.recordContent = Optional.ofNullable(recordContent);
	}
	

	
	
	
}
