package nts.uk.ctx.at.record.infra.entity.goout;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.goout.OutingManagement;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.GoingOutReason;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 外出管理
 * 
 * @author nampt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCDT_OUTING_MANAGEMENT")
public class KrcdtOutingManagement extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcdtOutingManagementPK krcdtOutingManagementPK;

	@Column(name = "OUTING_REASON")
	public int outingReason;

	@Column(name = "MAXIMUM_USAGE_COUNT")
	public int maximumUsageCount;

	@Override
	protected Object getKey() {
		return this.krcdtOutingManagementPK;
	}

	public OutingManagement toDomain() {
		OutingManagement outingManagement = new OutingManagement(this.krcdtOutingManagementPK.companyID,
				this.maximumUsageCount, EnumAdaptor.valueOf(this.outingReason, GoingOutReason.class));
		return outingManagement;
	}
}
