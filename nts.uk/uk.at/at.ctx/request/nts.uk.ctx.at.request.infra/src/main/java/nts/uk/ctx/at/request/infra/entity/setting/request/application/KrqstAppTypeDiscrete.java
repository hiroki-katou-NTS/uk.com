package nts.uk.ctx.at.request.infra.entity.setting.request.application;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "KRQST_APP_TYPE_DISCRETE")
public class KrqstAppTypeDiscrete extends UkJpaEntity{
	
	@EmbeddedId
	public KrqstAppTypeDiscretePK krqstAppTypeDiscretePK;
	
	@Column(name = "PRE_POST_INIT_ATR")
	public int prePostInitAtr;

	@Column(name = "PRE_POST_CAN_CHANGE_FLG")
	public int prePostCanChangeFlg;

	@Column(name = "TYPICAL_REASON_DISPLAY_FLG")
	public int typicalReasonDisplayFlg;

	@Column(name = "SEND_MAIL_WHEN_APPROVAL_FLG")
	public int sendMailWhenApprovalFlg;
	
	@Column(name = "SEND_MAIL_WHEN_REGISTER_FLG")
	public int sendMailWhenRegisterFlg;
	
	@Column(name = "DISPLAY_REASON_FLG")
	public int displayReasonFlg;
	
	@Column(name = "RETRICT_PRE_METHOD_CHECK_FLG")
	public int retrictPreMethodFlg;

	@Column(name = "RETRICT_PRE_USE_FLG")
	public int retrictPreUseFlg;

	@Column(name = "RETRICT_PRE_DAY")
	public int retrictPreDay;

	@Column(name = "RETRICT_PRE_TIMEDAY")
	public int retrictPreTimeDay;

	@Column(name = "RETRICT_PRE_CAN_ACCEPT_FLG")
	public int retrictPreCanAcceptFlg;

	@Column(name = "RETRICT_POST_ALLOW_FUTURE_FLG")
	public int retrictPostAllowFutureFlg;
	
	@ManyToOne
	@PrimaryKeyJoinColumns({
		@PrimaryKeyJoinColumn(name="CID",referencedColumnName="CID")
	})
	private KrqstApplicationSetting krqstApplicationSetting;

	@Override
	protected Object getKey() {
		return krqstAppTypeDiscretePK;
	}
}
