package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationcommonsetting;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationcommonsetting.primitive.Confirm;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationcommonsetting.primitive.MsgAdvance;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationcommonsetting.primitive.WarnDateDisp;

/**
 * 承認一覧表示設定
 * データが確立が確定されている場合の承認済申請の反映
 * @author yennth
 */
@Value
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
public class ApprovalSet extends DomainObject{
	/**　会社ID*/
	private String companyId;
	/** 申請理由 */
	private MsgAdvance reasonDisp;
	/** 残業の事前申請 */
	private MsgAdvance overtimePre;
	/** 休出の事前申請 */
	private MsgAdvance hdPre;
	/** 事前申請の超過メッセージ */
	private MsgAdvance msgAdvance;
	/** 残業の実績 */
	private MsgAdvance overtimePerfom;
	/** 休出の実績 */
	private MsgAdvance hdPerform;
	/** 実績超過メッセージ */
	private MsgAdvance msgExceeded;
	/** 申請対象日に対して警告表示 */
	private WarnDateDisp warnDateDisp;
	
	
	
	// domain khac AppReflectAfterConfirm
	/** スケジュールが確定されている場合 */
	private Confirm scheduleCon;
	/** 実績が確定されている場合 */
	private Confirm achiveCon;
	
	public static ApprovalSet createSimpleFromJavaType(String companyId, int reasonDisp, int overtimePre, int hdPre,
			 int msgAdvance, int overtimePerfom, int hdPerform, int msgExceeded, int warnDateDisp, int scheduleCon,
			 int achiveCon){
		return new ApprovalSet(companyId, EnumAdaptor.valueOf(reasonDisp, MsgAdvance.class), 
							EnumAdaptor.valueOf(overtimePre, MsgAdvance.class),
							EnumAdaptor.valueOf(hdPre, MsgAdvance.class), 
							EnumAdaptor.valueOf(msgAdvance, MsgAdvance.class), 
							EnumAdaptor.valueOf(overtimePerfom, MsgAdvance.class), 
							EnumAdaptor.valueOf(hdPerform, MsgAdvance.class), 
							EnumAdaptor.valueOf(msgExceeded, MsgAdvance.class), 
							new WarnDateDisp(warnDateDisp),
							EnumAdaptor.valueOf(scheduleCon, Confirm.class),
							EnumAdaptor.valueOf(achiveCon, Confirm.class));
	}
}
