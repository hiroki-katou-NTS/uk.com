package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.stamp;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.shared.dom.application.reflectprocess.condition.ApplicationReflect;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author thanh_nx
 *
 *         打刻申請の反映
 */
@Getter
public class ReflectAppStamp implements DomainAggregate, ApplicationReflect {

	// 会社ID
	private String companyId;

	// 出退勤を反映する
	private NotUseAtr reflectAttLeav;

	// 育児時間帯を反映する
	private NotUseAtr reflectChildCare;

	// 臨時出退勤を反映する
	private NotUseAtr reflectTemporary;

	// 応援開始、終了を反映する
	private NotUseAtr reflectSupport;

	// 介護時間帯を反映する
	private NotUseAtr reflectCare;

	// 外出時間帯を反映する
	private NotUseAtr reflectGoOut;

	// 休憩時間帯を反映する
	private NotUseAtr reflectBreakTime;

	public ReflectAppStamp(String companyId, NotUseAtr reflectAttLeav, NotUseAtr reflectChildCare,
			NotUseAtr reflectTemporary, NotUseAtr reflectSupport, NotUseAtr reflectCare, NotUseAtr reflectGoOut,
			NotUseAtr reflectBreakTime) {
		super();
		this.companyId = companyId;
		this.reflectAttLeav = reflectAttLeav;
		this.reflectChildCare = reflectChildCare;
		this.reflectTemporary = reflectTemporary;
		this.reflectSupport = reflectSupport;
		this.reflectCare = reflectCare;
		this.reflectGoOut = reflectGoOut;
		this.reflectBreakTime = reflectBreakTime;
	}

}
