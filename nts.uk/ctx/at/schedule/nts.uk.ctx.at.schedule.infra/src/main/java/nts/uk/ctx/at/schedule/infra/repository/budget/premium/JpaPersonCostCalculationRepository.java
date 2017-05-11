package nts.uk.ctx.at.schedule.infra.repository.budget.premium;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.budget.premium.PersonCostCalculation;
import nts.uk.ctx.at.schedule.dom.budget.premium.PersonCostCalculationRepository;
import nts.uk.ctx.at.schedule.dom.budget.premium.PremiumName;
import nts.uk.ctx.at.schedule.dom.budget.premium.PremiumRate;
import nts.uk.ctx.at.schedule.dom.budget.premium.PremiumSetting;
import nts.uk.ctx.at.schedule.dom.budget.premium.UnitPrice;
import nts.uk.ctx.at.schedule.dom.budget.premium.UseClassification;
import nts.uk.ctx.at.schedule.infra.entity.budget.premium.KmldpPremiumAttendancePK;
import nts.uk.ctx.at.schedule.infra.entity.budget.premium.KmldtPremiumAttendance;
import nts.uk.ctx.at.schedule.infra.entity.budget.premium.KmlmpPersonCostCalculationPK;
import nts.uk.ctx.at.schedule.infra.entity.budget.premium.KmlmtPersonCostCalculation;
import nts.uk.ctx.at.schedule.infra.entity.budget.premium.KmlspExtraTimePK;
import nts.uk.ctx.at.schedule.infra.entity.budget.premium.KmlspPremiumSetPK;
import nts.uk.ctx.at.schedule.infra.entity.budget.premium.KmlstExtraTime;
import nts.uk.ctx.at.schedule.infra.entity.budget.premium.KmlstPremiumSet;
import nts.uk.shr.com.primitive.Memo;

/**
 * 
 * @author Doan Duy Hung
 *
 */

@Stateless
public class JpaPersonCostCalculationRepository extends JpaRepository implements PersonCostCalculationRepository{

	private final String SEL_BY_CID = "SELECT a FROM KmlmtPersonCostCalculation a WHERE a.kmlmpPersonCostCalculationPK.companyID = :companyID ORDER BY a.startDate ASC";
	
	private final String SEL_ITEM_BY_DATE = "SELECT a FROM KmlmtPersonCostCalculation a WHERE a.kmlmpPersonCostCalculationPK.companyID = :companyID AND a.startDate = :startDate";
	
	private final String SEL_ITEM_BY_HID = "SELECT a FROM KmlmtPersonCostCalculation a WHERE a.kmlmpPersonCostCalculationPK.companyID = :companyID AND a.kmlmpPersonCostCalculationPK.historyID = :historyID";
	
	private final String SEL_ITEM_BEFORE = "SELECT a FROM KmlmtPersonCostCalculation a WHERE a.kmlmpPersonCostCalculationPK.companyID = :companyID AND a.startDate < :startDate ORDER BY a.startDate DESC";
	
	private final String SEL_ITEM_AFTER = "SELECT a FROM KmlmtPersonCostCalculation a WHERE a.kmlmpPersonCostCalculationPK.companyID = :companyID AND a.startDate > :startDate";
	
	@Override
	public void add(PersonCostCalculation personCostCalculation) {
		this.commandProxy().insert(toPersonCostCalculationEntity(personCostCalculation));
	}

	@Override
	public List<PersonCostCalculation> findByCompanyID(String companyID) {
		return this.queryProxy().query(SEL_BY_CID, KmlmtPersonCostCalculation.class).setParameter("companyID", companyID)
				.getList(x -> toDomainPersonCostCalculation(x));
	}
	
	@Override
	public Optional<PersonCostCalculation> findItemByDate(String companyID, GeneralDate startDate) {
		return this.queryProxy().query(SEL_ITEM_BY_DATE, KmlmtPersonCostCalculation.class).setParameter("companyID", companyID).setParameter("startDate", startDate)
				.getSingle().map(x -> toDomainPersonCostCalculation(x));
	}

	@Override
	public Optional<PersonCostCalculation> findItemByHistoryID(String companyID, String historyID) {
		return this.queryProxy().query(SEL_ITEM_BY_HID, KmlmtPersonCostCalculation.class).setParameter("companyID", companyID).setParameter("historyID", historyID)
				.getSingle().map(x -> toDomainPersonCostCalculation(x));
	}

	@Override
	public Optional<PersonCostCalculation> findItemBefore(String companyID, GeneralDate startDate) {
		List<KmlmtPersonCostCalculation> result = this.queryProxy().query(SEL_ITEM_BEFORE, KmlmtPersonCostCalculation.class)
				.setParameter("companyID", companyID).setParameter("startDate", startDate).getList();
		if(result.isEmpty()) return Optional.ofNullable(null);
		return Optional.of(toDomainPersonCostCalculation(result.get(0)));
	}

	@Override
	public Optional<PersonCostCalculation> findItemAfter(String companyID, GeneralDate startDate) {
		List<KmlmtPersonCostCalculation> result = this.queryProxy().query(SEL_ITEM_AFTER, KmlmtPersonCostCalculation.class)
				.setParameter("companyID", companyID).setParameter("startDate", startDate).getList();
		if(result.isEmpty()) return Optional.empty();
		return Optional.of(toDomainPersonCostCalculation(result.get(0)));
	}

	@Override
	public void update(PersonCostCalculation personCostCalculation) {
		this.commandProxy().update(toPersonCostCalculationEntity(personCostCalculation));
	}

	@Override
	public void delete(PersonCostCalculation personCostCalculation) {
		this.commandProxy().remove(KmlmtPersonCostCalculation.class, 
				new KmlmpPersonCostCalculationPK(personCostCalculation.getCompanyID(), personCostCalculation.getHistoryID()));
	}
	
	/**
	 * convert Entity Object to Domain Object
	 * @param kmlmtPersonCostCalculation Entity Object
	 * @return Domain Object
	 */
	private PersonCostCalculation toDomainPersonCostCalculation(KmlmtPersonCostCalculation kmlmtPersonCostCalculation){
		return new PersonCostCalculation(
				kmlmtPersonCostCalculation.kmlmpPersonCostCalculationPK.companyID, 
				kmlmtPersonCostCalculation.kmlmpPersonCostCalculationPK.historyID, 
				new Memo(kmlmtPersonCostCalculation.memo), 
				EnumAdaptor.valueOf(kmlmtPersonCostCalculation.unitPrice, UnitPrice.class) , 
				kmlmtPersonCostCalculation.startDate, 
				kmlmtPersonCostCalculation.endDate,
				kmlmtPersonCostCalculation.premiumSets.stream().map(x -> toDomainPremiumSetting(x)).collect(Collectors.toList()));
	}
	
	private PremiumSetting toDomainPremiumSetting(KmlstPremiumSet kmlstPremiumSet) {
		return new PremiumSetting(
				kmlstPremiumSet.kmlspPremiumSet.companyID, 
				kmlstPremiumSet.kmlspPremiumSet.historyID, 
				kmlstPremiumSet.kmlspPremiumSet.premiumCD, 
				new PremiumRate(kmlstPremiumSet.premiumRate),
				new PremiumName(kmlstPremiumSet.extraTime.premiumName),
				kmlstPremiumSet.extraTime.timeItemCD,
				EnumAdaptor.valueOf(kmlstPremiumSet.extraTime.useAtr, UseClassification.class) ,
				kmlstPremiumSet.premiumAttendances.stream().map(x -> x.premiumAttendancePK.attendanceCD).collect(Collectors.toList()));
	}
	
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
				personCostCalculation.getUnitprice().value, 
				personCostCalculation.getStartDate(), 
				personCostCalculation.getEndDate(),
				personCostCalculation.getPremiumSettings().stream().map(x -> toPremiumSetEntity(x)).collect(Collectors.toList()));
	}
	
	private KmlstPremiumSet toPremiumSetEntity(PremiumSetting premiumSetting) {
		return new KmlstPremiumSet(
				new KmlspPremiumSetPK(
						premiumSetting.getCompanyID(), 
						premiumSetting.getHistoryID(), 
						premiumSetting.getAttendanceID()
				), 
				premiumSetting.getPremiumRate().v(),
				null,
				toExtraTimeEntity(premiumSetting),
				premiumSetting.getTimeItemIDs().stream().map(x -> toPremiumAttendanceEntity(
								premiumSetting.getCompanyID(), 
								premiumSetting.getHistoryID(), 
								premiumSetting.getAttendanceID(), 
								x)
						).collect(Collectors.toList())
				);
	}
	
	private KmldtPremiumAttendance toPremiumAttendanceEntity(String companyID, String historyID, String premiumCD, String attendanceCD){
		return new KmldtPremiumAttendance(
				new KmldpPremiumAttendancePK(companyID, historyID, attendanceCD, premiumCD)
				);
	}
	
	private KmlstExtraTime toExtraTimeEntity(PremiumSetting premiumSetting){
		return new KmlstExtraTime(
				new KmlspExtraTimePK(
						premiumSetting.getCompanyID(), 
						premiumSetting.getAttendanceID()), 
				premiumSetting.getPremiumName().v(), 
				premiumSetting.getInternalID(), 
				premiumSetting.getUseAtr().value);
	}
}
