package nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.applicationlatearrival;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.jdbc.map.JpaEntityMapper;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival.CancelAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival.LateEarlyCancelAppSet;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.lateearlycancellation.LateEarlyCancelReflect;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
import nts.uk.shr.infra.data.entity.UkJpaEntity;
import org.apache.commons.lang3.BooleanUtils;

@Entity
@Table(name = "KRQMT_APP_LATE_OR_LEAVE")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class KrqmtAppLateOrEarly extends ContractUkJpaEntity {

	@Id
	@Column(name = "CID")
	private String companyid;

	@Column(name = "CANCLE_ATR")
	private int cancelAtr;

	@Column(name = "LATE_AL_CLEAR_ATR")
	private int lateAlClearAtr;

	@Override
	protected String getKey() {
		return this.companyid;
	}

	public static final JpaEntityMapper<KrqmtAppLateOrEarly> MAPPER = new JpaEntityMapper<>(KrqmtAppLateOrEarly.class);

	public LateEarlyCancelAppSet toSettingDomain() {
		return new LateEarlyCancelAppSet(this.companyid, EnumAdaptor.valueOf(cancelAtr, CancelAtr.class));
	}

	public LateEarlyCancelReflect toReflectDomain() {
		return new LateEarlyCancelReflect(companyid, BooleanUtils.toBoolean(lateAlClearAtr));
	}

	public static KrqmtAppLateOrEarly create(String companyId, int cancleAtr, int lateAlClearAtr) {
		return new KrqmtAppLateOrEarly(companyId, cancleAtr, lateAlClearAtr);
	}

}
