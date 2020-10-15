package nts.uk.ctx.at.schedule.infra.repository.shift.management.shifttable;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.PublicManagementShiftTable;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.PublicManagementShiftTableRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.management.shifttable.KscdtManagementOfShiftTable;
import nts.uk.ctx.at.schedule.infra.entity.shift.management.shifttable.KscdtManagementOfShiftTablePk;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaManagementShiftTableRepository extends JpaRepository implements PublicManagementShiftTableRepository {

	private static final String GET = "SELECT a FROM KscdtManagementOfShiftTable a "
			+ " WHERE a.pk.targetUnit = :targetUnit AND a.pk.targetID = :targetID";

	@Override
	public void insert(PublicManagementShiftTable shiftTable) {
		this.commandProxy().insert(KscdtManagementOfShiftTable.toEntity(shiftTable));

	}

	@Override
	public void update(PublicManagementShiftTable shiftTable) {
		Optional<KscdtManagementOfShiftTable> result = this.queryProxy()
				.find(new KscdtManagementOfShiftTablePk(AppContexts.user().companyId(),
						shiftTable.getTargetOrgIdenInfor().getUnit().value,
						shiftTable.getTargetOrgIdenInfor().getUnit().value == 0
								? shiftTable.getTargetOrgIdenInfor().getWorkplaceId().get()
								: shiftTable.getTargetOrgIdenInfor().getWorkplaceGroupId().get()),
						KscdtManagementOfShiftTable.class);
		if (result.isPresent()) {
			result.get().endDate = shiftTable.getEndDatePublicationPeriod();
			result.get().startDate = shiftTable.getOptEditStartDate().isPresent()
					? shiftTable.getOptEditStartDate().get() : null;
		}

	}

	@Override
	public Optional<PublicManagementShiftTable> get(TargetOrgIdenInfor idenInfor) {
		Optional<KscdtManagementOfShiftTable> result = this.queryProxy().query(GET, KscdtManagementOfShiftTable.class)
				.setParameter("targetUnit", idenInfor.getUnit().value)
				.setParameter("targetID", idenInfor.getUnit().value == 0 ? idenInfor.getWorkplaceId().get()
						: idenInfor.getWorkplaceGroupId().get())
				.getSingle();
		return result.isPresent() ? Optional.of(new PublicManagementShiftTable(
				new TargetOrgIdenInfor(TargetOrganizationUnit.valueOf(result.get().pk.targetUnit),
						result.get().pk.targetUnit == 0 ? Optional.of(result.get().pk.targetID) : Optional.empty(),
						result.get().pk.targetUnit == 1 ? Optional.of(result.get().pk.targetID) : Optional.empty()),
				result.get().endDate, Optional.ofNullable(result.get().startDate))) : Optional.empty();
	}

}
