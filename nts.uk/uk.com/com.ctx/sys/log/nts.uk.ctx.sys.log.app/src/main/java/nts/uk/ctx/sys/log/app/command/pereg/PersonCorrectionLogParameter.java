package nts.uk.ctx.sys.log.app.command.pereg;

import java.io.Serializable;
import java.util.List;

import lombok.Value;
import nts.uk.shr.com.security.audittrail.correction.content.UserInfo;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.CategoryCorrectionLog;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.PersonInfoCorrectionLog;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.PersonInfoProcessAttr;

@Value
public class PersonCorrectionLogParameter implements Serializable{
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	private final List<PersonCorrectionTarget> targets;
	
	@Value
	public static class PersonCorrectionTarget implements Serializable {

		/** serialVersionUID */
		private static final long serialVersionUID = 1L;
		
		public final String userId;
		public final String employeeId;
		public final String userName;
		public  PersonInfoProcessAttr processAttr;
		public final String remark;
		
		
		public PersonCorrectionTarget(String userId,String employeeId, String userName, PersonInfoProcessAttr processAttr, String remark){
			this.userId = userId;
			this.employeeId = employeeId;
			this.userName = userName;
			this.processAttr = processAttr;
			this.remark = remark;
		}
		private PersonInfoCorrectionLog toPersonInfoCorrection(String operationId , String remark, List<CategoryCorrectionLog> ctgLog){
			return new PersonInfoCorrectionLog(operationId, processAttr, new UserInfo(this.userId, this.employeeId,this. userName), ctgLog, remark);
		}
	}
}
