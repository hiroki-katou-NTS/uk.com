package nts.uk.ctx.at.request.infra.entity.setting.company.applicationcommonsetting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
/**
 * 
 * @author yennth
 *
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "KRQST_APPROVAL_SET")
public class KrqstApprovalSet extends UkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/**　会社ID*/
	@Id
	@Column(name = "CID")
	public String companyId;
	/**申請理由*/
	@Column(name = "REASON_DISP_ATR")
	public int reasonDisp;
	/**　残業の事前申請 */
	@Column(name = "OVERTIME_PRE_ATR")
	public int overtimePre;
	/**　休出の事前申請 */
	@Column(name = "HD_PRE_ATR")
	public int hdPre;
	/**　事前申請の超過メッセージ */
	@Column(name = "MSG_ADVANCE_ATR")
	public int msgAdvance;
	/**  残業の実績 */
	@Column(name = "OVERTIME_PERFOM_ATR")
	public int overtimePerfom;
	/**　休出の実績 */
	@Column(name = "HD_PERFORM_ATR")
	public int hdPerform;
	/**　実績超過メッセージ */
	@Column(name = "MSG_EXCEEDED_ATR")
	public int msgExceeded;
	/**　申請対象日に対して警告表示 */
	@Column(name = "WARNING_DATE_DISP_ATR")
	public int warnDateDisp;
	/**　スケジュールが確定されている場合 */
	@Column(name = "SCHEDULE_CONFIRM_ATR")
	public int scheduleCon;
	/**　実績が確定されている場合 */
	@Column(name = "ACHIEVEMENT_CONFIRM_ATR")
	public int achiveCon;
	@Override
	protected Object getKey() {
		return companyId;
	}
}
