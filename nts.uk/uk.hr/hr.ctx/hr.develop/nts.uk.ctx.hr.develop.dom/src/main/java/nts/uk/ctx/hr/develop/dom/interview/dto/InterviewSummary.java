package nts.uk.ctx.hr.develop.dom.interview.dto;

import java.util.List;

public class InterviewSummary {
	/** 面談記録有無リスト **/	
	public List<InterviewRecordAvailability> listInterviewRecordAvailability;
    /** 面談記録情報リスト **/
    public List<InterviewRecordInfo> listInterviewRecordInfo;
    /** サブ面談者社員情報リスト **/
    public List<SubInterviewInfor> listSubInterviewInfor;
}
