package nts.uk.ctx.at.shared.infra.repository.yearholidaygrant;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.ConditionValue;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantCondition;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTblSet;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.UseConditionAtr;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHolidayCode;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHolidayRepository;
import nts.uk.ctx.at.shared.infra.entity.yearholidaygrant.KshmtHdpaidCondition;
import nts.uk.ctx.at.shared.infra.entity.yearholidaygrant.KshmtHdpaidConditionPK;
import nts.uk.ctx.at.shared.infra.entity.yearholidaygrant.KshmtHdpaidTblSet;
import nts.uk.ctx.at.shared.infra.entity.yearholidaygrant.KshmtHdpaidTblSetPK;

/**
 * 
 * @author TanLV
 *
 */

@Stateless
public class JpaYearHolidayRepository extends JpaRepository implements YearHolidayRepository {
	
	private static final String FIND_ALL_BY_CID = "SELECT a FROM KshmtHdpaidTblSet a "
			+ "WHERE a.kshmtHdpaidTblSetPK.companyId = :companyId ORDER BY a.kshmtHdpaidTblSetPK.yearHolidayCode ASC";
	private static final String DELETE_CONDITION = "DELETE FROM KshmtHdpaidCondition c "
			+ "WHERE c.kshmtHdpaidConditionPK.companyId =:companyId "
			+ "AND c.kshmtHdpaidConditionPK.yearHolidayCode =:yearHolidayCode ";
	private static final String DELETE_GRANT_DATES = "DELETE FROM KshstGrantHdTbl g "
			+ "WHERE g.kshstGrantHdTblPK.companyId =:companyId "
			+ "AND g.kshstGrantHdTblPK.yearHolidayCode =:yearHolidayCode ";
	
	@Override
	public List<GrantHdTblSet> findAll(String companyId) {
		return this.queryProxy().query(FIND_ALL_BY_CID, KshmtHdpaidTblSet.class)
				.setParameter("companyId", companyId)
				.getList(x -> convertToDomainYearHoliday(x));
	}	

	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Optional<GrantHdTblSet> findByCode(String companyId, String yearHolidayCode) {
		return this.queryProxy().find(new KshmtHdpaidTblSetPK(companyId, yearHolidayCode), KshmtHdpaidTblSet.class)
				.map(x -> convertToDomainYearHoliday(x));
	}

	@Override
	public void add(GrantHdTblSet yearHoliday) {
		this.commandProxy().insert(toEntity(yearHoliday));
	}

	@Override
	public void update(GrantHdTblSet yearHoliday) {
		KshmtHdpaidTblSetPK key = new KshmtHdpaidTblSetPK(yearHoliday.getCompanyId(), yearHoliday.getYearHolidayCode().v());
		Optional<KshmtHdpaidTblSet> entity = this.queryProxy().find(key, KshmtHdpaidTblSet.class);
		KshmtHdpaidTblSet kshstYearHoliday = entity.get();
		kshstYearHoliday.yearHolidayName = yearHoliday.getYearHolidayName().v();
		kshstYearHoliday.calculationMethod = yearHoliday.getCalculationMethod().value;
		kshstYearHoliday.simultaneousGrandMonthDays = yearHoliday.getSimultaneousGrandMonthDays();
		kshstYearHoliday.standardCalculation = yearHoliday.getStandardCalculation().value;
		kshstYearHoliday.useSimultaneousGrant = yearHoliday.getUseSimultaneousGrant().value;
		kshstYearHoliday.yearHolidayNote = yearHoliday.getYearHolidayNote().v();
		
		List<KshmtHdpaidCondition> grantCoditionList = yearHoliday.getGrantConditions().stream()
				.map(x -> {
					KshmtHdpaidConditionPK conditionKey = new KshmtHdpaidConditionPK(yearHoliday.getCompanyId(), yearHoliday.getYearHolidayCode().v(), x.getConditionNo());
					return new KshmtHdpaidCondition(conditionKey, x.getConditionValue() != null ? x.getConditionValue().v() : null, x.getUseConditionAtr().value);
				}).collect(Collectors.toList());
		
		kshstYearHoliday.grantConditions = grantCoditionList;
		
		this.commandProxy().update(kshstYearHoliday);
	}

	@Override
	public void remove(String companyId, String yearHolidayCode) {
		this.commandProxy().remove(KshmtHdpaidTblSet.class, new KshmtHdpaidTblSetPK(companyId, yearHolidayCode));
	}
	
	@Override
	public void removeCondition(String companyId,  String yearHolidayCode) {
		this.getEntityManager().createQuery(DELETE_CONDITION)
			.setParameter("companyId", companyId)
			.setParameter("yearHolidayCode", yearHolidayCode)
			.executeUpdate();
	}
	
	@Override
	public void removeGrantDates(String companyId, String yearHolidayCode) {
		this.getEntityManager().createQuery(DELETE_GRANT_DATES)
		.setParameter("companyId", companyId)
		.setParameter("yearHolidayCode", yearHolidayCode)
		.executeUpdate();
	}
	
	/**
	 * Convert to domain
	 * 
	 * @param KshmtHdpaidTblSet
	 * @return
	 */
	private GrantHdTblSet convertToDomainYearHoliday(KshmtHdpaidTblSet x) {
		List<GrantCondition> grantConditions = x.grantConditions.stream().map(t -> {
			return new 	GrantCondition(t.kshmtHdpaidConditionPK.companyId, 
					new YearHolidayCode(t.kshmtHdpaidConditionPK.yearHolidayCode), 
					t.kshmtHdpaidConditionPK.conditionNo, 
					t.conditionValue != null ? new ConditionValue(t.conditionValue) : null, 
					EnumAdaptor.valueOf(t.useConditionAtr, UseConditionAtr.class),
					!CollectionUtil.isEmpty(t.yearHolidayGrants));
		}).collect(Collectors.toList());
		
		return GrantHdTblSet.createFromJavaType(x.kshmtHdpaidTblSetPK.companyId, 
				x.kshmtHdpaidTblSetPK.yearHolidayCode, 
				x.yearHolidayName, 
				x.calculationMethod, 
				x.standardCalculation, 
				x.useSimultaneousGrant,	
				x.simultaneousGrandMonthDays, 
				x.yearHolidayNote, grantConditions);
	}

	/**
	 * Convert to entity
	 * 
	 * @param yearHoliday
	 * @return
	 */
	private KshmtHdpaidTblSet toEntity(GrantHdTblSet yearHoliday) {
		List<KshmtHdpaidCondition> grantCoditionList = yearHoliday.getGrantConditions().stream()
				.map(x -> {
					KshmtHdpaidConditionPK key = new KshmtHdpaidConditionPK(yearHoliday.getCompanyId(), yearHoliday.getYearHolidayCode().v(), x.getConditionNo());
					return new KshmtHdpaidCondition(key, x.getConditionValue() != null ? x.getConditionValue().v() : null, x.getUseConditionAtr().value);
				}).collect(Collectors.toList());
		
		return new KshmtHdpaidTblSet(
				new KshmtHdpaidTblSetPK(yearHoliday.getCompanyId(), yearHoliday.getYearHolidayCode().v()),
				yearHoliday.getYearHolidayName().v(),
				yearHoliday.getCalculationMethod().value,
				yearHoliday.getStandardCalculation().value,
				yearHoliday.getUseSimultaneousGrant().value,
				yearHoliday.getSimultaneousGrandMonthDays(),
				yearHoliday.getYearHolidayNote().v(), grantCoditionList);
	}
}
