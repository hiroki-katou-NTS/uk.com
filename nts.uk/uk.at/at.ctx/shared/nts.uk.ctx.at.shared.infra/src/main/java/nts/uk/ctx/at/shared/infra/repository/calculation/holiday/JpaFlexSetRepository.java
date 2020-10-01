package nts.uk.ctx.at.shared.infra.repository.calculation.holiday;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worklabor.flex.FlexSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worklabor.flex.FlexSetRepository;
import nts.uk.ctx.at.shared.infra.entity.calculation.holiday.KshstFlexSet;
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
		builderString.append(" FROM KshstFlexSet e");
		builderString.append(" WHERE e.kshstFlexSetPK.companyId = :companyId");
		SELECT_BY_CID = builderString.toString();
	}

	/**
	 * convert To Domain Flex Set
	 * @param kshstFlexSet
	 * @return
	 */
	private FlexSet convertToDomain(KshstFlexSet kshstFlexSet) {
		FlexSet flexSet = FlexSet.createFromJavaType(kshstFlexSet.kshstFlexSetPK.companyId, kshstFlexSet.missCalcHd,
				kshstFlexSet.premiumCalcHd, kshstFlexSet.missCalcSubhd, kshstFlexSet.premiumCalcSubhd, kshstFlexSet.flexDeductCalc, kshstFlexSet.flexNonwkingCalc);
		
		return flexSet;
	}
	
	/**
	 * convert To Db Type KshstFlexSet
	 * @param flexSet
	 * @return
	 */
	private KshstFlexSet convertToDbType(FlexSet flexSet) {
		KshstFlexSet kshstFlexSet = new KshstFlexSet();
		KshstFlexSetPK kshstFlexSetPK = new KshstFlexSetPK(flexSet.getCompanyId());
				kshstFlexSet.missCalcHd = flexSet.getMissCalcHd().value;
				kshstFlexSet.premiumCalcHd = flexSet.getPremiumCalcHd().value;
				kshstFlexSet.missCalcSubhd = flexSet.getMissCalcSubhd().value;
				kshstFlexSet.premiumCalcSubhd = flexSet.getPremiumCalcSubhd().value;
				kshstFlexSet.kshstFlexSetPK = kshstFlexSetPK;
		return kshstFlexSet;
	}
	
	/**
	 * find By CID
	 */
	@Override
	public List<FlexSet> findByCompanyId(String companyId) {
		return this.queryProxy().query(SELECT_BY_CID, KshstFlexSet.class).setParameter("companyId", companyId)
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
		KshstFlexSet entity = this.queryProxy().find(primaryKey, KshstFlexSet.class).get();
				entity.missCalcHd = flexSet.getMissCalcHd().value;
				entity.premiumCalcHd = flexSet.getPremiumCalcHd().value;
				entity.missCalcSubhd = flexSet.getMissCalcSubhd().value;
				entity.premiumCalcSubhd = flexSet.getPremiumCalcSubhd().value;
				entity.flexDeductCalc = flexSet.getFlexDeductTimeCalc().value;
				entity.flexNonwkingCalc = flexSet.getFlexNonworkingDayCalc().value;
				
				entity.kshstFlexSetPK = primaryKey;
		this.commandProxy().update(entity);
	}
	
	/**
	 * check By CID
	 */
	@Override
	public Optional<FlexSet> findByCId(String companyId) {
		return this.queryProxy().find(new KshstFlexSetPK(companyId),KshstFlexSet.class)
				.map(c->convertToDomain(c));
	}
}
