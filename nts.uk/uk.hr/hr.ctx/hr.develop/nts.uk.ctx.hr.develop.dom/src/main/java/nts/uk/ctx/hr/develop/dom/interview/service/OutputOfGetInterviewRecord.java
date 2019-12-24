package nts.uk.ctx.hr.develop.dom.interview.service;

import java.util.List;

import lombok.Data;

/**
 * 
 * @author lamvt
 *
 */
@Data
public class OutputOfGetInterviewRecord {
	
	/** 面談記録有無リスト = {社員IDリスト．社員ID、有無} */
	private List<InterviewRecordAvailability> listAvailable;
	
	/** 面談記録リスト = {社員ID、面談記録ID、面談日、メイン面談者社員ID} */
	private List<SimpleInterviewRecord> listSimpleInterview;
}
