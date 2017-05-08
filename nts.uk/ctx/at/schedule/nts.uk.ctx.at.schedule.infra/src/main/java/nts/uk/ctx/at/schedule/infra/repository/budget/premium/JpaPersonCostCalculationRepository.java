package nts.uk.ctx.at.schedule.infra.repository.budget.premium;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.budget.premium.PersonCostCalculation;
import nts.uk.ctx.at.schedule.dom.budget.premium.PersonCostCalculationRepository;
import nts.uk.ctx.at.schedule.dom.budget.premium.PremiumSetting;
import nts.uk.ctx.at.schedule.dom.budget.premium.UnitPrice;
import nts.uk.ctx.at.schedule.infra.entity.budget.premium.KmlmpPersonCostCalculationPK;
import nts.uk.ctx.at.schedule.infra.entity.budget.premium.KmlmtPersonCostCalculation;
import nts.uk.ctx.at.schedule.infra.entity.budget.premium.KmlspPremiumSetPK;
import nts.uk.ctx.at.schedule.infra.entity.budget.premium.KmlstPremiumSet;
import nts.uk.shr.com.primitive.Memo;

/**
 * 
 * @author Doan Duy Hung
 *
 */

@Stateless
public class JpaPersonCostCalculationRepository extends JpaRepository implements PersonCostCalculationRepository{

	private final String SEL_BY_CID = "Select a from KmlmtPersonCostCalculation a where a.kmlmpPersonCostCalculationPK.CID = :CID";
	
	private final String SEL_DAY_AFTER = "Select a from KmlmtPersonCostCalculation a where a.startDate >= :startDate";
	
	@Override
	public void add(PersonCostCalculation personCostCalculation) {
		//personCostCalculation.getPremiumSettings()
		this.commandProxy().insert(toPersonCostCalculationEntity(personCostCalculation));
	}

	@Override
	public List<PersonCostCalculation> findByCompanyID(String CID) {
		return null;
		//return this.queryProxy().query(SEL_BY_CID, KmlmtPersonCostCalculation.class).setParameter("CID", CID).getList(x -> convertToDomain(x));
	}
	
	@Override
	public Optional<PersonCostCalculation> find(String CID, String HID) {
		return null;
		//return this.queryProxy().find(new KmlmpPersonCostCalculationPK(CID, HID), KmlmtPersonCostCalculation.class).map(x -> convertToDomain(x));
	}

	@Override
	public Optional<PersonCostCalculation> findAfterDay(String CID, GeneralDate date) {
		return null;
		//return this.queryProxy().query(SEL_DAY_AFTER, KmlmtPersonCostCalculation.class).setParameter("startDate", date).getSingle().map(x -> convertToDomain(x));
	}

	@Override
	public void update(PersonCostCalculation personCostCalculation) {
		this.commandProxy().update(toPersonCostCalculationEntity(personCostCalculation));
	}

	@Override
	public void delete(PersonCostCalculation personCostCalculation) {
		this.commandProxy().remove(toPersonCostCalculationEntity(personCostCalculation));
	}
	
	/**
	 * convert Entity Object to Domain Object
	 * @param kmlmtPersonCostCalculation Entity Object
	 * @return Domain Object
	 */
//	private PersonCostCalculation convertToDomain(KmlmtPersonCostCalculation kmlmtPersonCostCalculation){
//		return new PersonCostCalculation(
//				kmlmtPersonCostCalculation.kmlmpPersonCostCalculationPK.CID, 
//				kmlmtPersonCostCalculation.kmlmpPersonCostCalculationPK.HID, 
//				new Memo(kmlmtPersonCostCalculation.memo), 
//				EnumAdaptor.valueOf(kmlmtPersonCostCalculation.unitPrice, UnitPrice.class) , 
//				kmlmtPersonCostCalculation.startDate, 
//				kmlmtPersonCostCalculation.endDate);
//	}
	
	/**
	 * convert Domain Object to Entity Object
	 * @param personCostCalculation Domain Object
	 * @return Entity Object
	 */
	private KmlmtPersonCostCalculation toPersonCostCalculationEntity(PersonCostCalculation personCostCalculation) {
		return new KmlmtPersonCostCalculation(
				new KmlmpPersonCostCalculationPK(
						personCostCalculation.getCompanyID(), 
						personCostCalculation.getHistoryID()
				), 
				personCostCalculation.getMemo().v(), 
				personCostCalculation.getUnitprice().unitPrice, 
				personCostCalculation.getStartDate(), 
				personCostCalculation.getEndDate());
	}
	
//	private KmlstPremiumSet toPremiumSetEntity(String companyID, String historyID, PremiumSetting premiumSetting) {
//		return new KmlstPremiumSet(
//				new KmlspPremiumSetPK(companyID, historyID, premiumSetting.getAttendanceID()), 
//				premiumSetting.getPremiumRate(), 
//				);
//	}
}
