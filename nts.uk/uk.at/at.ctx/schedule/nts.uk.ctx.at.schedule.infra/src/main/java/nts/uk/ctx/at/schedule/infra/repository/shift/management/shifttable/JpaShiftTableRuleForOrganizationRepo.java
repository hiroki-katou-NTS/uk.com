package nts.uk.ctx.at.schedule.infra.repository.shift.management.shifttable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.ShiftTableRuleForOrganization;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.ShiftTableRuleForOrganizationRepo;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.WorkAvailabilityPeriodUnit;
import nts.uk.ctx.at.schedule.infra.entity.shift.management.shifttable.KscmtShiftTableRuleForOrg;
import nts.uk.ctx.at.schedule.infra.entity.shift.management.shifttable.KscdtManagementOfShiftTablePk;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

@Stateless
public class JpaShiftTableRuleForOrganizationRepo extends JpaRepository implements ShiftTableRuleForOrganizationRepo {

	private static final String SELECT_BY_KEY = "SELECT c FROM KscmtShiftTableRuleForOrg c WHERE c.pk.companyId = :companyId "
			+ " AND c.pk.targetUnit = :targetUnit" + " AND c.pk.targetID = :targetID  ";

	private static final String SELECT_ALL = "SELECT c FROM KscmtShiftTableRuleForOrg c WHERE c.pk.companyId = :companyId ";

	@Override
	public void insert(String companyId, ShiftTableRuleForOrganization domain) {
		this.commandProxy().insert(KscmtShiftTableRuleForOrg.toEntity(companyId, domain));

	}

	@Override
	public void update(String companyId, ShiftTableRuleForOrganization domain) {
		Optional<KscmtShiftTableRuleForOrg> oldEntity = this.queryProxy()
				.query(SELECT_BY_KEY, KscmtShiftTableRuleForOrg.class).setParameter("companyId", companyId)
				.setParameter("targetUnit", domain.getTargetOrg().getUnit().value)
				.setParameter("targetID", domain.getTargetOrg().getTargetId()).getSingle();

		if (oldEntity.isPresent()) {
			KscmtShiftTableRuleForOrg newEntity = KscmtShiftTableRuleForOrg.toEntity(companyId, domain);
			if (oldEntity.get().useWorkAvailabilityAtr == 1) {
				if (newEntity.useWorkAvailabilityAtr == 1) {
					oldEntity.get().usePublicAtr = newEntity.usePublicAtr;
					oldEntity.get().useWorkAvailabilityAtr = newEntity.useWorkAvailabilityAtr;
					oldEntity
							.get().kscmtShiftTableRuleForOrgAvai.holidayAtr = newEntity.kscmtShiftTableRuleForOrgAvai.holidayAtr;
					oldEntity
							.get().kscmtShiftTableRuleForOrgAvai.shiftAtr = newEntity.kscmtShiftTableRuleForOrgAvai.shiftAtr;
					oldEntity
							.get().kscmtShiftTableRuleForOrgAvai.timeSheetAtr = newEntity.kscmtShiftTableRuleForOrgAvai.timeSheetAtr;
					oldEntity
							.get().kscmtShiftTableRuleForOrgAvai.fromNoticeDays = newEntity.kscmtShiftTableRuleForOrgAvai.fromNoticeDays;
					oldEntity
							.get().kscmtShiftTableRuleForOrgAvai.periodUnit = newEntity.kscmtShiftTableRuleForOrgAvai.periodUnit;

					if (newEntity.kscmtShiftTableRuleForOrgAvai.periodUnit == WorkAvailabilityPeriodUnit.MONTHLY.value) {
						oldEntity
								.get().kscmtShiftTableRuleForOrgAvai.dateCloseDay = newEntity.kscmtShiftTableRuleForOrgAvai.dateCloseDay;
						oldEntity
								.get().kscmtShiftTableRuleForOrgAvai.dateCloseIsLastDay = newEntity.kscmtShiftTableRuleForOrgAvai.dateCloseIsLastDay;
						oldEntity
								.get().kscmtShiftTableRuleForOrgAvai.dateDeadlineDay = newEntity.kscmtShiftTableRuleForOrgAvai.dateDeadlineDay;
						oldEntity
								.get().kscmtShiftTableRuleForOrgAvai.dateDeadlineIsLastDay = newEntity.kscmtShiftTableRuleForOrgAvai.dateDeadlineIsLastDay;
						oldEntity
								.get().kscmtShiftTableRuleForOrgAvai.dateHDUpperlimit = newEntity.kscmtShiftTableRuleForOrgAvai.dateHDUpperlimit;

						oldEntity.get().kscmtShiftTableRuleForOrgAvai.weekSetStart = null;
						oldEntity.get().kscmtShiftTableRuleForOrgAvai.weekSetDeadlineAtr = null;
						oldEntity.get().kscmtShiftTableRuleForOrgAvai.weekSetDeadlineWeek = null;
					} else if (newEntity.kscmtShiftTableRuleForOrgAvai.periodUnit == WorkAvailabilityPeriodUnit.WEEKLY.value) {
						oldEntity.get().kscmtShiftTableRuleForOrgAvai.dateCloseDay = null;
						oldEntity.get().kscmtShiftTableRuleForOrgAvai.dateCloseIsLastDay = null;
						oldEntity.get().kscmtShiftTableRuleForOrgAvai.dateDeadlineDay = null;
						oldEntity.get().kscmtShiftTableRuleForOrgAvai.dateDeadlineIsLastDay = null;
						oldEntity.get().kscmtShiftTableRuleForOrgAvai.dateHDUpperlimit = null;

						oldEntity
								.get().kscmtShiftTableRuleForOrgAvai.weekSetStart = newEntity.kscmtShiftTableRuleForOrgAvai.weekSetStart;
						oldEntity
								.get().kscmtShiftTableRuleForOrgAvai.weekSetDeadlineAtr = newEntity.kscmtShiftTableRuleForOrgAvai.weekSetDeadlineAtr;
						oldEntity
								.get().kscmtShiftTableRuleForOrgAvai.weekSetDeadlineWeek = newEntity.kscmtShiftTableRuleForOrgAvai.weekSetDeadlineWeek;
					} else {
						oldEntity.get().kscmtShiftTableRuleForOrgAvai.dateCloseDay = null;
						oldEntity.get().kscmtShiftTableRuleForOrgAvai.dateCloseIsLastDay = null;
						oldEntity.get().kscmtShiftTableRuleForOrgAvai.dateDeadlineDay = null;
						oldEntity.get().kscmtShiftTableRuleForOrgAvai.dateDeadlineIsLastDay = null;
						oldEntity.get().kscmtShiftTableRuleForOrgAvai.dateHDUpperlimit = null;

						oldEntity.get().kscmtShiftTableRuleForOrgAvai.weekSetStart = null;
						oldEntity.get().kscmtShiftTableRuleForOrgAvai.weekSetDeadlineAtr = null;
						oldEntity.get().kscmtShiftTableRuleForOrgAvai.weekSetDeadlineWeek = null;
					}
				} else {
					oldEntity.get().kscmtShiftTableRuleForOrgAvai = null;
				}
			} else {
				if (newEntity.useWorkAvailabilityAtr == 1) {
					oldEntity.get().kscmtShiftTableRuleForOrgAvai = newEntity.kscmtShiftTableRuleForOrgAvai;
				} else {
					oldEntity.get().kscmtShiftTableRuleForOrgAvai = null;
				}

			}

			this.commandProxy().update(oldEntity.get());
		}
	}

	@Override
	public void delete(String companyId, TargetOrgIdenInfor targetOrg) {
		Optional<ShiftTableRuleForOrganization> data = this.get(companyId, targetOrg);
		if (data.isPresent()) {
			this.commandProxy().remove(KscmtShiftTableRuleForOrg.class,
					new KscdtManagementOfShiftTablePk(companyId, targetOrg.getUnit().value, targetOrg.getTargetId()));
		}
	}

	@Override
	public Optional<ShiftTableRuleForOrganization> get(String companyId, TargetOrgIdenInfor targetOrg) {
		Optional<ShiftTableRuleForOrganization> shiftTableRuleForOrganization = this.queryProxy()
				.query(SELECT_BY_KEY, KscmtShiftTableRuleForOrg.class).setParameter("companyId", companyId)
				.setParameter("targetUnit", targetOrg.getUnit().value).setParameter("targetID", targetOrg.getTargetId())
				.getSingle(c -> c.toDomain());
		return shiftTableRuleForOrganization;
	}

	@Override
	public List<ShiftTableRuleForOrganization> getList(String companyId, List<TargetOrgIdenInfor> targetOrgList) {
		List<ShiftTableRuleForOrganization> data = new ArrayList<>();
		for (TargetOrgIdenInfor targetOrgIdenInfor : targetOrgList) {
			Optional<ShiftTableRuleForOrganization> shiftTableRuleForOrganization = this.get(companyId,
					targetOrgIdenInfor);
			if (shiftTableRuleForOrganization.isPresent()) {
				data.add(shiftTableRuleForOrganization.get());
			}
		}
		return data;
	}

	@Override
	public List<ShiftTableRuleForOrganization> getAll(String companyId) {
		List<ShiftTableRuleForOrganization> shiftTableRuleForOrganization = this.queryProxy()
				.query(SELECT_ALL, KscmtShiftTableRuleForOrg.class).setParameter("companyId", companyId)
				.getList(c -> c.toDomain());
		return shiftTableRuleForOrganization;
	}

}
