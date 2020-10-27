package nts.uk.ctx.at.shared.infra.repository.calculation.holiday;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worklabor.flex.FlexSet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worklabor.flex.FlexSetRepository;
import nts.uk.ctx.at.shared.infra.entity.calculation.holiday.KshmtCalcCFlex;
import nts.uk.ctx.at.shared.infra.entity.calculation.holiday.KshmtCalcCFlexPK;
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
		builderString.append(" WHERE e.kshmtCalcCFlexPK.companyId = :companyId");
		SELECT_BY_CID = builderString.toString();
	}

	/**
	 * convert To Domain Flex Set
	 * @param kshmtCalcCFlex
	 * @return
	 */
	private FlexSet convertToDomain(KshmtCalcCFlex kshmtCalcCFlex) {
		FlexSet flexSet = FlexSet.createFromJavaType(kshmtCalcCFlex.kshmtCalcCFlexPK.companyId, kshmtCalcCFlex.missCalcHd,
				kshmtCalcCFlex.premiumCalcHd, kshmtCalcCFlex.missCalcSubhd, kshmtCalcCFlex.premiumCalcSubhd, kshmtCalcCFlex.flexDeductCalc, kshmtCalcCFlex.flexNonwkingCalc);
		
		return flexSet;
	}
	
	/**
	 * convert To Db Type KshmtCalcCFlex
	 * @param flexSet
	 * @return
	 */
	private KshmtCalcCFlex convertToDbType(FlexSet flexSet) {
		KshmtCalcCFlex kshmtCalcCFlex = new KshmtCalcCFlex();
		KshmtCalcCFlexPK kshmtCalcCFlexPK = new KshmtCalcCFlexPK(flexSet.getCompanyId());
				kshmtCalcCFlex.missCalcHd = flexSet.getMissCalcHd().value;
				kshmtCalcCFlex.premiumCalcHd = flexSet.getPremiumCalcHd().value;
				kshmtCalcCFlex.missCalcSubhd = flexSet.getMissCalcSubhd().value;
				kshmtCalcCFlex.premiumCalcSubhd = flexSet.getPremiumCalcSubhd().value;
				kshmtCalcCFlex.kshmtCalcCFlexPK = kshmtCalcCFlexPK;
		return kshmtCalcCFlex;
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
		KshmtCalcCFlexPK primaryKey = new KshmtCalcCFlexPK(flexSet.getCompanyId());
		KshmtCalcCFlex entity = this.queryProxy().find(primaryKey, KshmtCalcCFlex.class).get();
				entity.missCalcHd = flexSet.getMissCalcHd().value;
				entity.premiumCalcHd = flexSet.getPremiumCalcHd().value;
				entity.missCalcSubhd = flexSet.getMissCalcSubhd().value;
				entity.premiumCalcSubhd = flexSet.getPremiumCalcSubhd().value;
				entity.flexDeductCalc = flexSet.getFlexDeductTimeCalc().value;
				entity.flexNonwkingCalc = flexSet.getFlexNonworkingDayCalc().value;
				
				entity.kshmtCalcCFlexPK = primaryKey;
		this.commandProxy().update(entity);
	}
	
	/**
	 * check By CID
	 */
	@Override
	public Optional<FlexSet> findByCId(String companyId) {
		return this.queryProxy().find(new KshmtCalcCFlexPK(companyId),KshmtCalcCFlex.class)
				.map(c->convertToDomain(c));
	}
}
