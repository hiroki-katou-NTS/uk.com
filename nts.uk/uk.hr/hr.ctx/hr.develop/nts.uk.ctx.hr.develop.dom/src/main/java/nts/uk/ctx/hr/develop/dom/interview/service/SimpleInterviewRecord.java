package nts.uk.ctx.hr.develop.dom.interview.service;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;

/**
 * the type of the object 面談記録リスト = {社員ID、面談記録ID、面談日、メイン面談者社員ID}
 * @author lamvt
 *
 */
@Data
@AllArgsConstructor
public class SimpleInterviewRecord {
	
	/** 社員ID **/
	private String employeeID ;
	
	/** 面談記録ID **/
	private String interviewRecordId;
	
	/** 面談日 */
	private GeneralDate interviewDate;
	
	/** メイン面談者社員ID */
	private String mainInterviewerSid;
	
	/** サブ面談者社員ID - Cai này tiện công thì lấy luôn để dùng cho đoạn xử lý
	 * nếu サブ面談者を取得する = true  (Get subInterviewer = true ) 
	 * thì ドメインモデル [面談記録] を取得する(Get the domain model [Interview Record])*/
	private List<String> subInterviewSids;
}
