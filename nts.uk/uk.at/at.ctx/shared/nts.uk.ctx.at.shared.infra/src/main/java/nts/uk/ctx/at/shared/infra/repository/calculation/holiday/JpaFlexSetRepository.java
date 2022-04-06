package nts.uk.ctx.at.shared.infra.repository.calculation.holiday;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worklabor.flex.FlexSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worklabor.flex.FlexSetRepository;
import nts.uk.ctx.at.shared.infra.entity.calculation.holiday.KshmtCalcCFlex;
import nts.uk.ctx.at.shared.infra.entity.calculation.holiday.KshstFlexSetPK;
/**
 * 
 * @author phongtq
 *
 */
@Stateless
public class JpaFlexSetRepository extends JpaRepository implements FlexSetRepository {
	private static final String SELECT_BY_CID;
	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KshmtCalcCFlex e");
		builderString.append(" WHERE e.kshstFlexSetPK.companyId = :companyId");
		SELECT_BY_CID = builderString.toString();
	}

	/**
	 * convert To Domain Flex Set
	 * @param kshstFlexSet
	 * @return
	 */
	private FlexSet convertToDomain(KshmtCalcCFlex kshstFlexSet) {
		FlexSet flexSet = FlexSet.createFromJavaType(
				kshstFlexSet.kshstFlexSetPK.companyId,
				kshstFlexSet.missCalcHd ? 1 : 0,
				kshstFlexSet.premiumCalcHd ? 1 : 0,
				kshstFlexSet.isDeductPred ? 1 : 0,
				kshstFlexSet.premiumCalcSubhd ? 1 : 0,
				kshstFlexSet.calcSetTimeSubhd ? 1 : 0,
				kshstFlexSet.flexNoworkingCalc ? 1 : 0);
		return flexSet;
	}
	
	/**
	 * convert To Db Type KshmtCalcCFlex
	 * @param flexSet
	 * @return
	 */
	private KshmtCalcCFlex convertToDbType(FlexSet flexSet) {
		KshmtCalcCFlex entity = new KshmtCalcCFlex();
		KshstFlexSetPK kshstFlexSetPK = new KshstFlexSetPK(flexSet.getCompanyId());
		entity.kshstFlexSetPK = kshstFlexSetPK;
		entity.missCalcHd = flexSet.getHalfHoliday().getCalcLack().value == 1;
		entity.premiumCalcHd = flexSet.getHalfHoliday().getCalcPremium().value == 1;
		entity.isDeductPred = flexSet.getCompLeave().isDeductFromPred();
		entity.premiumCalcSubhd = flexSet.getCompLeave().getCalcPremium().value == 1;
		entity.calcSetTimeSubhd = flexSet.getCompLeave().getCalcSetOfTimeCompLeave().value == 1;
		entity.flexNoworkingCalc = flexSet.getCalcNoWorkingDay().getSetting().value == 1;
		return entity;
	}
	
	/**
	 * find By CID
	 */
	@Override
	public List<FlexSet> findByCompanyId(String companyId) {
		return this.queryProxy().query(SELECT_BY_CID, KshmtCalcCFlex.class).setParameter("companyId", companyId)
				.getList(c -> convertToDomain(c));
	}
	
	/**
	 * Add Flex Set
	 */
	@Override
	public void add(FlexSet flexSet) {
		this.commandProxy().insert(convertToDbType(flexSet));
	}
	
	/**
	 * Update Flex Set
	 */
	@Override
	public void update(FlexSet flexSet) {
		KshstFlexSetPK primaryKey = new KshstFlexSetPK(flexSet.getCompanyId());
		KshmtCalcCFlex entity = this.queryProxy().find(primaryKey, KshmtCalcCFlex.class).get();
		entity.missCalcHd = flexSet.getHalfHoliday().getCalcLack().value == 1;
		entity.premiumCalcHd = flexSet.getHalfHoliday().getCalcPremium().value == 1;
		entity.isDeductPred = flexSet.getCompLeave().isDeductFromPred();
		entity.premiumCalcSubhd = flexSet.getCompLeave().getCalcPremium().value == 1;
		entity.calcSetTimeSubhd = flexSet.getCompLeave().getCalcSetOfTimeCompLeave().value == 1;
		entity.flexNoworkingCalc = flexSet.getCalcNoWorkingDay().getSetting().value == 1;
		this.commandProxy().update(entity);
	}
	
	/**
	 * check By CID
	 */
	@Override
	public Optional<FlexSet> findByCId(String companyId) {
		return this.queryProxy().find(new KshstFlexSetPK(companyId),KshmtCalcCFlex.class)
				.map(c->convertToDomain(c));
	}
}
