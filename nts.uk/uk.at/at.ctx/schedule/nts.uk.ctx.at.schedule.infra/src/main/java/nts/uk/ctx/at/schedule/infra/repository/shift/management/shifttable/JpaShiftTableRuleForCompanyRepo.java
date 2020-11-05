package nts.uk.ctx.at.schedule.infra.repository.shift.management.shifttable;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.ShiftTableRuleForCompany;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.ShiftTableRuleForCompanyRepo;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.WorkAvailabilityPeriodUnit;
import nts.uk.ctx.at.schedule.infra.entity.shift.management.shifttable.KrcmtShiftTableRuleForCompany;

@Stateless
public class JpaShiftTableRuleForCompanyRepo extends JpaRepository implements ShiftTableRuleForCompanyRepo {

	private static final String SELECT_BY_KEY = "SELECT c FROM KrcmtShiftTableRuleForCompany c WHERE c.companyId = :companyId ";
	
	@Override
	public void insert(String companyId, ShiftTableRuleForCompany domain) {
		this.commandProxy().insert(KrcmtShiftTableRuleForCompany.toEntity(companyId, domain));
	}

	@Override
	public void update(String companyId, ShiftTableRuleForCompany domain) {
		Optional<KrcmtShiftTableRuleForCompany> oldEntity = this.queryProxy().query(SELECT_BY_KEY, KrcmtShiftTableRuleForCompany.class)
				.setParameter("companyId", companyId)
				.getSingle();
		
		if(oldEntity.isPresent()) {
			KrcmtShiftTableRuleForCompany newEntity = KrcmtShiftTableRuleForCompany.toEntity(companyId, domain);
			if(oldEntity.get().useWorkAvailabilityAtr == 1) {
				if(newEntity.useWorkAvailabilityAtr == 1) {
					oldEntity.get().usePublicAtr = newEntity.usePublicAtr;
					oldEntity.get().useWorkAvailabilityAtr = newEntity.useWorkAvailabilityAtr;
					oldEntity.get().krcmtShiftTableRuleForCompanyAvai.holidayAtr = newEntity.krcmtShiftTableRuleForCompanyAvai.holidayAtr;
					oldEntity.get().krcmtShiftTableRuleForCompanyAvai.shiftAtr = newEntity.krcmtShiftTableRuleForCompanyAvai.shiftAtr;
					oldEntity.get().krcmtShiftTableRuleForCompanyAvai.timeSheetAtr = newEntity.krcmtShiftTableRuleForCompanyAvai.timeSheetAtr;
					oldEntity.get().krcmtShiftTableRuleForCompanyAvai.fromNoticeDays = newEntity.krcmtShiftTableRuleForCompanyAvai.fromNoticeDays;
					oldEntity.get().krcmtShiftTableRuleForCompanyAvai.periodUnit = newEntity.krcmtShiftTableRuleForCompanyAvai.periodUnit;
					
					if(newEntity.krcmtShiftTableRuleForCompanyAvai.periodUnit == WorkAvailabilityPeriodUnit.MONTHLY.value) {
						oldEntity.get().krcmtShiftTableRuleForCompanyAvai.dateCloseDay = newEntity.krcmtShiftTableRuleForCompanyAvai.dateCloseDay;
						oldEntity.get().krcmtShiftTableRuleForCompanyAvai.dateCloseIsLastDay = newEntity.krcmtShiftTableRuleForCompanyAvai.dateCloseIsLastDay;
						oldEntity.get().krcmtShiftTableRuleForCompanyAvai.dateDeadlineDay = newEntity.krcmtShiftTableRuleForCompanyAvai.dateDeadlineDay;
						oldEntity.get().krcmtShiftTableRuleForCompanyAvai.dateDeadlineIsLastDay = newEntity.krcmtShiftTableRuleForCompanyAvai.dateDeadlineIsLastDay;
						oldEntity.get().krcmtShiftTableRuleForCompanyAvai.dateHDUpperlimit = newEntity.krcmtShiftTableRuleForCompanyAvai.dateHDUpperlimit;
						
						oldEntity.get().krcmtShiftTableRuleForCompanyAvai.weekSetStart = null;
						oldEntity.get().krcmtShiftTableRuleForCompanyAvai.weekSetDeadlineAtr = null;
						oldEntity.get().krcmtShiftTableRuleForCompanyAvai.weekSetDeadlineWeek = null;
					}else if(newEntity.krcmtShiftTableRuleForCompanyAvai.periodUnit == WorkAvailabilityPeriodUnit.WEEKLY.value) {
						oldEntity.get().krcmtShiftTableRuleForCompanyAvai.dateCloseDay = null;
						oldEntity.get().krcmtShiftTableRuleForCompanyAvai.dateCloseIsLastDay = null;
						oldEntity.get().krcmtShiftTableRuleForCompanyAvai.dateDeadlineDay = null;
						oldEntity.get().krcmtShiftTableRuleForCompanyAvai.dateDeadlineIsLastDay = null;
						oldEntity.get().krcmtShiftTableRuleForCompanyAvai.dateHDUpperlimit = null;
						
						oldEntity.get().krcmtShiftTableRuleForCompanyAvai.weekSetStart = newEntity.krcmtShiftTableRuleForCompanyAvai.weekSetStart;
						oldEntity.get().krcmtShiftTableRuleForCompanyAvai.weekSetDeadlineAtr = newEntity.krcmtShiftTableRuleForCompanyAvai.weekSetDeadlineAtr;
						oldEntity.get().krcmtShiftTableRuleForCompanyAvai.weekSetDeadlineWeek = newEntity.krcmtShiftTableRuleForCompanyAvai.weekSetDeadlineWeek;
					}else {
						oldEntity.get().krcmtShiftTableRuleForCompanyAvai.dateCloseDay = null;
						oldEntity.get().krcmtShiftTableRuleForCompanyAvai.dateCloseIsLastDay = null;
						oldEntity.get().krcmtShiftTableRuleForCompanyAvai.dateDeadlineDay = null;
						oldEntity.get().krcmtShiftTableRuleForCompanyAvai.dateDeadlineIsLastDay = null;
						oldEntity.get().krcmtShiftTableRuleForCompanyAvai.dateHDUpperlimit = null;
						
						oldEntity.get().krcmtShiftTableRuleForCompanyAvai.weekSetStart = null;
						oldEntity.get().krcmtShiftTableRuleForCompanyAvai.weekSetDeadlineAtr = null;
						oldEntity.get().krcmtShiftTableRuleForCompanyAvai.weekSetDeadlineWeek = null;
					}
				}else{
					oldEntity.get().krcmtShiftTableRuleForCompanyAvai = null;
				}
			}else {
				if(newEntity.useWorkAvailabilityAtr == 1) {
					oldEntity.get().krcmtShiftTableRuleForCompanyAvai = newEntity.krcmtShiftTableRuleForCompanyAvai;
				}else {
					oldEntity.get().krcmtShiftTableRuleForCompanyAvai = null;
				}
				
			}
			this.commandProxy().update(oldEntity.get());
		}
	}

	@Override
	public void delete(String companyId) {
		Optional<ShiftTableRuleForCompany>  data = this.get(companyId);
		if(data.isPresent()) {
			this.commandProxy().remove(KrcmtShiftTableRuleForCompany.class, companyId);
		}
		
	}

	@Override
	public Optional<ShiftTableRuleForCompany> get(String companyId) {
		Optional<ShiftTableRuleForCompany> shiftTableRuleForCompany = this.queryProxy().query(SELECT_BY_KEY, KrcmtShiftTableRuleForCompany.class)
				.setParameter("companyId", companyId)
				.getSingle(c -> c.toDomain());
		return shiftTableRuleForCompany;
	}

}
