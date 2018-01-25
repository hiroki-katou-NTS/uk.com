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
	public String companyId;
	/**申請理由*/
	public int reasonDisp;
	/**　残業の事前申請 */
	public int overtimePre;
	/**　休出の事前申請 */
	public int hdPre;
	/**　事前申請の超過メッセージ */
	public int msgAdvance;
	/**  残業の実績 */
	public int overtimePerfom;
	/**　休出の実績 */
	public int hdPerform;
	/**　実績超過メッセージ */
	public int msgExceeded;
	/**　申請対象日に対して警告表示 */
	public int warnDateDisp;
	/**　スケジュールが確定されている場合 */
	public int scheduleCon;
	/**　実績が確定されている場合 */
	public int achiveCon;
	
	public ApprovalSet toDomain(String companyId){
		ApprovalSet appSet = ApprovalSet.createSimpleFromJavaType(companyId, this.getReasonDisp(), this.getOvertimePre(),
				this.getHdPre(), this.getMsgAdvance(), this.getOvertimePerfom(), this.getHdPerform(), 
				this.getMsgExceeded(), this.getWarnDateDisp(), this.getScheduleCon(), this.getAchiveCon());
		return appSet;
	}
}
