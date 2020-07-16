package nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.applicationlatearrival;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival.CancelAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival.LateEarlyCancelAppSet;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Table(name = "KRQST_APP_LATE_OR_LEAVE")
public class KrqstAppLateOrEarly extends UkJpaEntity {

	@Id
	@Column(name = "CID")
	private String companyid;

	@Column(name = "CANCLE_ATR")
	private int cancleAtr;

	@Override
	protected String getKey() {
		return this.companyid;
	}

	public static final JpaEntityMapper<KrqstAppLateOrEarly> MAPPER = new JpaEntityMapper<>(KrqstAppLateOrEarly.class);

	public LateEarlyCancelAppSet toDomain() {
		return new LateEarlyCancelAppSet(this.companyid, EnumAdaptor.valueOf(cancleAtr, CancelAtr.class));
	}

}
