package nts.uk.ctx.at.schedule.infra.repository.shift.management.shifttable;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.ShiftTableRuleForCompany;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.ShiftTableRuleForCompanyRepo;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.WorkAvailabilityPeriodUnit;
import nts.uk.ctx.at.schedule.infra.entity.shift.management.shifttable.KscmtShiftTableRuleForCompany;

@Stateless
public class JpaShiftTableRuleForCompanyRepo extends JpaRepository implements ShiftTableRuleForCompanyRepo {

	private static final String SELECT_BY_KEY = "SELECT c FROM KscmtShiftTableRuleForCompany c WHERE c.companyId = :companyId ";
	
	@Override
	public void insert(String companyId, ShiftTableRuleForCompany domain) {
		this.commandProxy().insert(KscmtShiftTableRuleForCompany.toEntity(companyId, domain));
	}

	@Override
	public void update(String companyId, ShiftTableRuleForCompany domain) {
		Optional<KscmtShiftTableRuleForCompany> oldEntity = this.queryProxy().query(SELECT_BY_KEY, KscmtShiftTableRuleForCompany.class)
				.setParameter("companyId", companyId)
				.getSingle();
		
		if(oldEntity.isPresent()) {
			KscmtShiftTableRuleForCompany newEntity = KscmtShiftTableRuleForCompany.toEntity(companyId, domain);
			if(oldEntity.get().useWorkAvailabilityAtr == 1) {
				if(newEntity.useWorkAvailabilityAtr == 1) {
					oldEntity.get().usePublicAtr = newEntity.usePublicAtr;
					oldEntity.get().useWorkAvailabilityAtr = newEntity.useWorkAvailabilityAtr;
					oldEntity.get().kscmtShiftTableRuleForCompanyAvai.holidayAtr = newEntity.kscmtShiftTableRuleForCompanyAvai.holidayAtr;
					oldEntity.get().kscmtShiftTableRuleForCompanyAvai.shiftAtr = newEntity.kscmtShiftTableRuleForCompanyAvai.shiftAtr;
					oldEntity.get().kscmtShiftTableRuleForCompanyAvai.timeSheetAtr = newEntity.kscmtShiftTableRuleForCompanyAvai.timeSheetAtr;
					oldEntity.get().kscmtShiftTableRuleForCompanyAvai.fromNoticeDays = newEntity.kscmtShiftTableRuleForCompanyAvai.fromNoticeDays;
					oldEntity.get().kscmtShiftTableRuleForCompanyAvai.periodUnit = newEntity.kscmtShiftTableRuleForCompanyAvai.periodUnit;
					
					if(newEntity.kscmtShiftTableRuleForCompanyAvai.periodUnit == WorkAvailabilityPeriodUnit.MONTHLY.value) {
						oldEntity.get().kscmtShiftTableRuleForCompanyAvai.dateCloseDay = newEntity.kscmtShiftTableRuleForCompanyAvai.dateCloseDay;
						oldEntity.get().kscmtShiftTableRuleForCompanyAvai.dateCloseIsLastDay = newEntity.kscmtShiftTableRuleForCompanyAvai.dateCloseIsLastDay;
						oldEntity.get().kscmtShiftTableRuleForCompanyAvai.dateDeadlineDay = newEntity.kscmtShiftTableRuleForCompanyAvai.dateDeadlineDay;
						oldEntity.get().kscmtShiftTableRuleForCompanyAvai.dateDeadlineIsLastDay = newEntity.kscmtShiftTableRuleForCompanyAvai.dateDeadlineIsLastDay;
						oldEntity.get().kscmtShiftTableRuleForCompanyAvai.dateHDUpperlimit = newEntity.kscmtShiftTableRuleForCompanyAvai.dateHDUpperlimit;
						
						oldEntity.get().kscmtShiftTableRuleForCompanyAvai.weekSetStart = null;
						oldEntity.get().kscmtShiftTableRuleForCompanyAvai.weekSetDeadlineAtr = null;
						oldEntity.get().kscmtShiftTableRuleForCompanyAvai.weekSetDeadlineWeek = null;
					}else if(newEntity.kscmtShiftTableRuleForCompanyAvai.periodUnit == WorkAvailabilityPeriodUnit.WEEKLY.value) {
						oldEntity.get().kscmtShiftTableRuleForCompanyAvai.dateCloseDay = null;
						oldEntity.get().kscmtShiftTableRuleForCompanyAvai.dateCloseIsLastDay = null;
						oldEntity.get().kscmtShiftTableRuleForCompanyAvai.dateDeadlineDay = null;
						oldEntity.get().kscmtShiftTableRuleForCompanyAvai.dateDeadlineIsLastDay = null;
						oldEntity.get().kscmtShiftTableRuleForCompanyAvai.dateHDUpperlimit = null;
						
						oldEntity.get().kscmtShiftTableRuleForCompanyAvai.weekSetStart = newEntity.kscmtShiftTableRuleForCompanyAvai.weekSetStart;
						oldEntity.get().kscmtShiftTableRuleForCompanyAvai.weekSetDeadlineAtr = newEntity.kscmtShiftTableRuleForCompanyAvai.weekSetDeadlineAtr;
						oldEntity.get().kscmtShiftTableRuleForCompanyAvai.weekSetDeadlineWeek = newEntity.kscmtShiftTableRuleForCompanyAvai.weekSetDeadlineWeek;
					}else {
						oldEntity.get().kscmtShiftTableRuleForCompanyAvai.dateCloseDay = null;
						oldEntity.get().kscmtShiftTableRuleForCompanyAvai.dateCloseIsLastDay = null;
						oldEntity.get().kscmtShiftTableRuleForCompanyAvai.dateDeadlineDay = null;
						oldEntity.get().kscmtShiftTableRuleForCompanyAvai.dateDeadlineIsLastDay = null;
						oldEntity.get().kscmtShiftTableRuleForCompanyAvai.dateHDUpperlimit = null;
						
						oldEntity.get().kscmtShiftTableRuleForCompanyAvai.weekSetStart = null;
						oldEntity.get().kscmtShiftTableRuleForCompanyAvai.weekSetDeadlineAtr = null;
						oldEntity.get().kscmtShiftTableRuleForCompanyAvai.weekSetDeadlineWeek = null;
					}
				}else{
					oldEntity.get().kscmtShiftTableRuleForCompanyAvai = null;
				}
			}else {
				if(newEntity.useWorkAvailabilityAtr == 1) {
					oldEntity.get().kscmtShiftTableRuleForCompanyAvai = newEntity.kscmtShiftTableRuleForCompanyAvai;
				}else {
					oldEntity.get().kscmtShiftTableRuleForCompanyAvai = null;
				}
				
			}
			this.commandProxy().update(oldEntity.get());
		}
	}

	@Override
	public void delete(String companyId) {
		Optional<ShiftTableRuleForCompany>  data = this.get(companyId);
		if(data.isPresent()) {
			this.commandProxy().remove(KscmtShiftTableRuleForCompany.class, companyId);
		}
		
	}

	@Override
	public Optional<ShiftTableRuleForCompany> get(String companyId) {
		Optional<ShiftTableRuleForCompany> shiftTableRuleForCompany = this.queryProxy().query(SELECT_BY_KEY, KscmtShiftTableRuleForCompany.class)
				.setParameter("companyId", companyId)
				.getSingle(c -> c.toDomain());
		return shiftTableRuleForCompany;
	}

}
