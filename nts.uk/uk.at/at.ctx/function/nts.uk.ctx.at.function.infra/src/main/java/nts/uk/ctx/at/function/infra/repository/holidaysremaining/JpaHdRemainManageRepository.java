package nts.uk.ctx.at.function.infra.repository.holidaysremaining;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.error.BusinessException;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.holidaysremaining.ChildNursingLeave;
import nts.uk.ctx.at.function.dom.holidaysremaining.HolidaysRemainingManagement;
import nts.uk.ctx.at.function.dom.holidaysremaining.ItemOutputForm;
import nts.uk.ctx.at.function.dom.holidaysremaining.ItemsOutputtedAlternate;
import nts.uk.ctx.at.function.dom.holidaysremaining.ItemsPublicOutput;
import nts.uk.ctx.at.function.dom.holidaysremaining.NursingCareLeave;
import nts.uk.ctx.at.function.dom.holidaysremaining.PauseItem;
import nts.uk.ctx.at.function.dom.holidaysremaining.SpecialHolidayOutput;
import nts.uk.ctx.at.function.dom.holidaysremaining.YearlyItemsOutput;
import nts.uk.ctx.at.function.dom.holidaysremaining.YearlyReserved;
import nts.uk.ctx.at.function.dom.holidaysremaining.repository.HolidaysRemainingManagementRepository;
import nts.uk.ctx.at.function.infra.entity.holidaysremaining.KfnmtHdRemainManage;
import nts.uk.ctx.at.function.infra.entity.holidaysremaining.KfnmtHdRemainManagePk;
import nts.uk.ctx.at.function.infra.entity.holidaysremaining.KfnmtSpecialHoliday;
import nts.uk.ctx.at.function.infra.entity.holidaysremaining.KfnmtSpecialHolidayPk;

@Stateless
public class JpaHdRemainManageRepository extends JpaRepository implements HolidaysRemainingManagementRepository{

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM KfnmtHdRemainManage f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.hdRemainManagePk.cid =:cid AND  f.hdRemainManagePk.cd =:cd ";
	private static final String SELECT_BY_COMPANY_ID = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.hdRemainManagePk.cid =:cid ";
	@Override
	public Optional<HolidaysRemainingManagement> getHolidayManagerByCidAndExecCd(String companyID, String code) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, KfnmtHdRemainManage.class)
				.setParameter("cd", code)
				.setParameter("cid", companyID).getSingle(c -> toDomain(c));
	}
	@Override
	public void insert(HolidaysRemainingManagement domain) {
		Optional<HolidaysRemainingManagement> duplicateDomain = getHolidayManagerByCidAndExecCd(domain.getCompanyID(), domain.getCode());
		if(duplicateDomain.isPresent()) throw new BusinessException("Msg_3");
		
		this.commandProxy().insert(this.toEntity(domain));		
	}
	@Override
	public void update(HolidaysRemainingManagement domain) {
		Optional<HolidaysRemainingManagement> duplicateDomain = getHolidayManagerByCidAndExecCd(domain.getCompanyID(), domain.getCode());
		if(duplicateDomain.isPresent()) 
		{	
			KfnmtHdRemainManage updateData = this.toEntity(domain);
			KfnmtHdRemainManage oldData = this.queryProxy().find(updateData.hdRemainManagePk, KfnmtHdRemainManage.class).get();
			this.commandProxy().update(oldData);
		}
	}
	@Override
	public void remove(String companyId, String code) {
		KfnmtHdRemainManagePk kfnmtSpecialHolidayPk = new KfnmtHdRemainManagePk(companyId, code);
		this.commandProxy().remove(KfnmtHdRemainManage.class, kfnmtSpecialHolidayPk);
		
	}
	@Override
	public List<HolidaysRemainingManagement> getHolidayManagerLogByCompanyId(String companyId) {
		return this.queryProxy().query(SELECT_BY_COMPANY_ID, KfnmtHdRemainManage.class)
				.setParameter("cid", companyId).getList(c -> toDomain(c));
	}
	
	private KfnmtHdRemainManage toEntity(HolidaysRemainingManagement domain) {
		return new KfnmtHdRemainManage(new KfnmtHdRemainManagePk(
											domain.getCompanyID(), 
											domain.getCode()),
				domain.getName(), 
				domain.getListItemsOutput().getAnnualHoliday().isYearlyHoliday() ? 1 : 0,
				domain.getListItemsOutput().getAnnualHoliday().isInsideHalfDay() ? 1 : 0,
				domain.getListItemsOutput().getAnnualHoliday().isInsideHours() ? 1 : 0,
				domain.getListItemsOutput().getYearlyReserved().isYearlyReserved() ? 1 : 0,
				domain.getListItemsOutput().getSubstituteHoliday().isOutputItemSubstitute() ? 1 : 0,
				domain.getListItemsOutput().getSubstituteHoliday().isRepresentSubstitute() ? 1 : 0,
				domain.getListItemsOutput().getSubstituteHoliday().isRemainingChargeSubstitute() ? 1 : 0,
				domain.getListItemsOutput().getPause().isPauseItem() ? 1 : 0,
				domain.getListItemsOutput().getPause().isUndigestedPause() ? 1 : 0,
				domain.getListItemsOutput().getPause().isNumberRemainingPause() ? 1 : 0,
				domain.getListItemsOutput().getHolidays().isOutputitemsholidays() ? 1 : 0,
				domain.getListItemsOutput().getHolidays().isOutputholidayforward() ? 1 : 0,
				domain.getListItemsOutput().getHolidays().isMonthlyPublic() ? 1 : 0,
				domain.getListItemsOutput().getChildNursingVacation().isChildNursingLeave() ? 1 : 0,
				domain.getListItemsOutput().getNursingcareLeave().isNursingLeave() ? 1 : 0,
						domain.getListItemsOutput().getSpecialHoliday().stream().map(itemDetai -> {
							return new KfnmtSpecialHoliday(
								new KfnmtSpecialHolidayPk(itemDetai.getCompanyID(), 
										itemDetai.getCode(), itemDetai.getHolidayCode()), null);
						}).collect(Collectors.toList()));
	}
	
	private static HolidaysRemainingManagement toDomain(KfnmtHdRemainManage entity) {
		return new HolidaysRemainingManagement(
				entity.name, entity.hdRemainManagePk.cid, entity.hdRemainManagePk.cd,
				new ItemOutputForm(	new NursingCareLeave(entity.childCareLeave > 0 ? true : false),
									new ItemsOutputtedAlternate(
											entity.remainChargeSub > 0 ? true : false,
											entity.representSub > 0 ? true : false, 
											entity.outItemSub > 0 ? true : false),
									new ItemsPublicOutput(
											entity.outputHolidayForward > 0 ? true : false,		
											entity.monthlyPublic > 0 ? true : false, 
											entity.outputItemsHolidays > 0 ? true : false),
									new ChildNursingLeave(entity.childCareLeave > 0 ? true : false),
									new YearlyItemsOutput(
											entity.yearlyHoliday > 0 ? true : false,
											entity.insideHours > 0 ? true : false, 
											entity.insideHalfDay > 0 ? true : false),
									new PauseItem(
											entity.numRemainPause > 0 ? true : false, 
											entity.undigestedPause > 0 ? true : false,
											entity.pauseItem > 0 ? true : false),
									new YearlyReserved(entity.yearlyReserved > 0 ? true : false),
									entity.kfnmtSpecialHolidays.stream().map(itemDetail -> {
										return new SpecialHolidayOutput(itemDetail.kfnmtSpecialHolidayPk.specialCd, 
												itemDetail.kfnmtSpecialHolidayPk.cd,
												itemDetail.kfnmtSpecialHolidayPk.cid);
										}).collect(Collectors.toList())
									)
				);
	}
}
