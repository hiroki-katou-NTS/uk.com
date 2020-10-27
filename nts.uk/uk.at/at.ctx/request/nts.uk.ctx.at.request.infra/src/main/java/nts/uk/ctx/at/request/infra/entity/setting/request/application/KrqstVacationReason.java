package nts.uk.ctx.at.request.infra.entity.setting.request.application;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "KRQST_VACATION_REASON")
public class KrqstVacationReason extends ContractUkJpaEntity {

	@EmbeddedId
	private KrqstVacationReasonPK krqstVacationReasonPK;

	@Column(name = "DISP_FIXED_REASON_FLG")
	public int dispFixedReasonFlg;

	@Column(name = "DISP_APP_REASON_FLG")
	public int dispAppReasonFlg;

	@Override
	protected Object getKey() {
		return krqstVacationReasonPK;
	};

}
