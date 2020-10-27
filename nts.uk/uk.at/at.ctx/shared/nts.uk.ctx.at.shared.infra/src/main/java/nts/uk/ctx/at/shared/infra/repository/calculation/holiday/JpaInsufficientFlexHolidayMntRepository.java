package nts.uk.ctx.at.shared.infra.repository.calculation.holiday;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.flexshortage.InsufficientFlexHolidayMnt;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.flexshortage.InsufficientFlexHolidayMntRepository;
import nts.uk.ctx.at.shared.infra.entity.calculation.holiday.KshmtCalcMFlexComMax;
import nts.uk.ctx.at.shared.infra.entity.calculation.holiday.KshmtCalcMFlexComMaxPK;

/**
 * Implementation of insufficient flex holiday time management repository
 * @author HoangNDH
 *
 */
@Stateless
public class JpaInsufficientFlexHolidayMntRepository extends JpaRepository implements InsufficientFlexHolidayMntRepository {
	private static final String SELECT_BY_CID;
	static {
		StringBuilder builderString = new StringBuilder();
		builderString.append("SELECT e");
		builderString.append(" FROM KshmtCalcMFlexComMax e");
		builderString.append(" WHERE e.kshmtCalcMFlexComMaxPK.companyId = :companyId");
		SELECT_BY_CID = builderString.toString();
	}
	
	/**
	 * Convert from db type to domain type
	 * @param kshmtCalcMFlexComMax
	 * @return
	 */
	private InsufficientFlexHolidayMnt convertToDomain(KshmtCalcMFlexComMax kshmtCalcMFlexComMax) {
		InsufficientFlexHolidayMnt insufficientFlexHolidayMnt = 
				InsufficientFlexHolidayMnt.createFromJavaType(kshmtCalcMFlexComMax.kshmtCalcMFlexComMaxPK.companyId, kshmtCalcMFlexComMax.getSupplementableDays());
		return insufficientFlexHolidayMnt;
	}
	
	/**
	 * Convert from domain type to db type
	 * @param insufficientFlexHolidayMnt
	 * @return
	 */
	private KshmtCalcMFlexComMax convertToDBType(InsufficientFlexHolidayMnt insufficientFlexHolidayMnt) {
		KshmtCalcMFlexComMax kshmtCalcMFlexComMax = new KshmtCalcMFlexComMax();
		KshmtCalcMFlexComMaxPK kshmtCalcMFlexComMaxPK = new KshmtCalcMFlexComMaxPK(insufficientFlexHolidayMnt.getCompanyId());
		kshmtCalcMFlexComMax.setKshmtCalcMFlexComMaxPK(kshmtCalcMFlexComMaxPK);
		kshmtCalcMFlexComMax.setSupplementableDays(insufficientFlexHolidayMnt.getSupplementableDays().v());
		return kshmtCalcMFlexComMax;
	}
	
	/**
	 * Add Insufficient Flex Holiday
	 * @param refreshInsuffFlex 
	 */
	@Override
	public void add(InsufficientFlexHolidayMnt insufficientFlexHolidayMnt) {
		this.commandProxy().insert(convertToDBType(insufficientFlexHolidayMnt));
	}

	/**
	 * Update Insufficient Flex Holiday
	 * @param refreshInsuffFlex
	 */
	@Override
	public void update(InsufficientFlexHolidayMnt refreshInsuffFlex) {
		KshmtCalcMFlexComMax entity = new KshmtCalcMFlexComMax();
		KshmtCalcMFlexComMaxPK primaryKey = new KshmtCalcMFlexComMaxPK(refreshInsuffFlex.getCompanyId());
		entity.setKshmtCalcMFlexComMaxPK(primaryKey);
		entity.setSupplementableDays(refreshInsuffFlex.getSupplementableDays().v());
		this.commandProxy().update(entity);
	}

	/**
	 * Find by CID
	 * @param companyId
	 * @return
	 */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public Optional<InsufficientFlexHolidayMnt> findByCId(String cid) {
		return this.queryProxy().find(new KshmtCalcMFlexComMaxPK(cid),KshmtCalcMFlexComMax.class)
				.map(c->convertToDomain(c));
	}

	/**
	 * Find by company ID
	 * @param companyId
	 * @return
	 */
	@Override
	public List<InsufficientFlexHolidayMnt> findByCompanyId(String companyId) {
		return this.queryProxy().query(SELECT_BY_CID, KshmtCalcMFlexComMax.class).setParameter("companyId", companyId)
				.getList(c -> convertToDomain(c));
	}

}
