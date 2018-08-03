package nts.uk.ctx.sys.log.app.command.pereg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.security.audittrail.correction.content.UserInfo;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.PersonInfoCorrectionLog;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.PersonInfoProcessAttr;

@Value
public class PeregCorrectionLogParameter implements Serializable{
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	private final List<PeregCorrectionTarget> targets;
	
	@Value
	public static class PeregCorrectionTarget implements Serializable {

		/** serialVersionUID */
		private static final long serialVersionUID = 1L;
		
		private final String userId;
		private final String employeeId;
		private final String userName;
		private final GeneralDate date;
		private final PersonInfoProcessAttr processAttr;
		private final String remark;
		
		
		public PeregCorrectionTarget(String userId,String employeeId, String userName, GeneralDate date,PersonInfoProcessAttr processAttr, String remark){
			this.userId = userId;
			this.employeeId = employeeId;
			this.userName = userName;
			this.date = date;
			this.processAttr = processAttr;
			this.remark = remark;
		}
		
		private PersonInfoCorrectionLog toPersonInfoCorrection(String remark){
			return new PersonInfoCorrectionLog("", processAttr, new UserInfo(this.userId, this.employeeId,this. userName), new ArrayList<>(), remark);
		}
	}
}
