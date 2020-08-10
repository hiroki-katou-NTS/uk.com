package nts.uk.ctx.at.schedule.infra.entity.shift.management.shifttable;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.PublicManagementShiftTable;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * シフト表の公開管理 entity
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSCMT_PALETTE_CMP")
public class KscmtManagementOfShiftTable extends ContractUkJpaEntity  implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KscmtManagementOfShiftTablePk pk;

	@Column(name = "OPEN_END_DATE")
	public GeneralDate endDate;

	@Column(name = "EDIT_START_DATE")
	public GeneralDate startDate;

	@Override
	protected Object getKey() {
		return this.pk;
	}

	public static KscmtManagementOfShiftTable toEntity(PublicManagementShiftTable shiftTable) {
		return new KscmtManagementOfShiftTable(
				new KscmtManagementOfShiftTablePk(AppContexts.user().companyId(),
						shiftTable.getTargetOrgIdenInfor().getUnit().value,
						shiftTable.getTargetOrgIdenInfor().getUnit().value == 0
								? shiftTable.getTargetOrgIdenInfor().getWorkplaceId().get()
								: shiftTable.getTargetOrgIdenInfor().getWorkplaceGroupId().get()),
				shiftTable.getEndDatePublicationPeriod(),
				shiftTable.getOptEditStartDate().isPresent() ? shiftTable.getOptEditStartDate().get() : null);
	}

}
