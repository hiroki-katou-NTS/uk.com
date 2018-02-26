package nts.uk.ctx.at.request.app.command.setting.company.applicationcommonsetting;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationcommonsetting.ApprovalSet;
/**
 * 
 * @author yennth
 *
 */
@Data
@AllArgsConstructor
public class ApprovalSetCommand {
	/**申請理由*/
	private int reasonDisp;
	/**　残業の事前申請 */
	private int overtimePre;
	/**　休出の事前申請 */
	private int hdPre;
	/**　事前申請の超過メッセージ */
	private int msgAdvance;
	/**  残業の実績 */
	private int overtimePerfom;
	/**　休出の実績 */
	private int hdPerform;
	/**　実績超過メッセージ */
	private int msgExceeded;
	/**　申請対象日に対して警告表示 */
	private int warnDateDisp;
	/**　スケジュールが確定されている場合 */
	private int scheduleCon;
	/**　実績が確定されている場合 */
	private int achiveCon;
	
	public ApprovalSet toDomain(String companyId){
		ApprovalSet appSet = ApprovalSet.createSimpleFromJavaType(companyId, this.reasonDisp, this.overtimePre,
				this.hdPre, this.msgAdvance, this.overtimePerfom, this.hdPerform, 
				this.msgExceeded, this.warnDateDisp, this.scheduleCon, this.achiveCon);
		return appSet;
	}
}
