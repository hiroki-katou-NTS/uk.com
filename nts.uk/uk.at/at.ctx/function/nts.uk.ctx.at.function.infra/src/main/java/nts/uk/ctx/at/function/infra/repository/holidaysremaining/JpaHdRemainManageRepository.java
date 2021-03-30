package nts.uk.ctx.at.function.infra.repository.holidaysremaining;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.holidaysremaining.HolidaysRemainingManagement;
import nts.uk.ctx.at.function.dom.holidaysremaining.ItemOutputForm;
import nts.uk.ctx.at.function.dom.holidaysremaining.repository.HolidaysRemainingManagementRepository;
import nts.uk.ctx.at.function.dom.monthlyworkschedule.ItemSelectionEnum;
import nts.uk.ctx.at.function.infra.entity.holidaysremaining.KfnmtRptHdRemainOut;
import nts.uk.ctx.at.function.infra.entity.holidaysremaining.KfnmtRptHdRemainHdsp;
import nts.uk.ctx.at.function.infra.entity.holidaysremaining.KfnmtRptHdRemainHdspPk;

@Stateless
public class JpaHdRemainManageRepository extends JpaRepository implements HolidaysRemainingManagementRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM KfnmtRptHdRemainOut f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.cid =:cid AND  f.cd =:cd ";
	private static final String SELECT_BY_COMPANY_ID = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.cid =:cid ";

	private static final String DELETEBY_COMPANY_ID_AND_CD = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.cid =:cid "
			+ " WHERE  f.cd =:cd ";
	@Override
	public Optional<HolidaysRemainingManagement> getHolidayManagerByCidAndExecCd(String companyID, String code) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, KfnmtRptHdRemainOut.class).setParameter("cd", code)
				.setParameter("cid", companyID).getSingle(JpaHdRemainManageRepository::toDomain);
	}
	@Override
	public Optional<HolidaysRemainingManagement> getHolidayManagerByLayOutId(String layOutId) {
		Optional<KfnmtRptHdRemainOut> optEntity = this.queryProxy().find(layOutId,KfnmtRptHdRemainOut.class);
		return optEntity.map(JpaHdRemainManageRepository::toDomain);

	}

	@Override
	public void insert(HolidaysRemainingManagement domain) {
		Optional<KfnmtRptHdRemainOut> duplicateDomain =
				this.queryProxy().find(domain.getLayOutId(),KfnmtRptHdRemainOut.class);
		if (duplicateDomain.isPresent())
			throw new BusinessException("Msg_3");
		this.commandProxy().insert(this.toEntity(domain));
	}

	@Override
	public void update(HolidaysRemainingManagement domain) {
		KfnmtRptHdRemainOut updateData = this.toEntity(domain);
		Optional<KfnmtRptHdRemainOut> optOldData = this.queryProxy().find(updateData.getKey(),
				KfnmtRptHdRemainOut.class);
		if (optOldData.isPresent()) {
			this.commandProxy().update(updateData);
		}
	}
	@Override
	public void remove(String companyId, String code) {
	this.queryProxy().query(DELETEBY_COMPANY_ID_AND_CD, KfnmtRptHdRemainOut.class)
				.setParameter("cid", companyId)
				.setParameter("cd", code);

	}
	@Override
	public void remove(String layoutId) {
		this.commandProxy().remove(KfnmtRptHdRemainOut.class, layoutId);
	}
	@Override
	public List<HolidaysRemainingManagement> getHolidayManagerLogByCompanyId(String companyId) {
		return this.queryProxy().query(SELECT_BY_COMPANY_ID, KfnmtRptHdRemainOut.class).setParameter("cid", companyId)
				.getList(JpaHdRemainManageRepository::toDomain);
	}

	private KfnmtRptHdRemainOut toEntity(HolidaysRemainingManagement domain) {
		return new KfnmtRptHdRemainOut(
				domain.getLayOutId(),
				domain.getItemSelectionCategory().value,
				domain.getEmployeeId().isPresent()? domain.getEmployeeId().get() : null,
				domain.getCompanyID(),
				domain.getCode().v(),
				domain.getName().v(),
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
				domain.getListItemsOutput().getOutOfTime().isOvertimeItem() ? 1: 0,
				domain.getListItemsOutput().getOutOfTime().isOvertimeRemaining() ? 1: 0,
				domain.getListItemsOutput().getOutOfTime().isOvertimeOverUndigested() ? 1: 0,
				domain.getListItemsOutput().getHolidays().isOutputItemsHolidays() ? 1 : 0,
				domain.getListItemsOutput().getHolidays().isOutputHolidayForward() ? 1 : 0,
				domain.getListItemsOutput().getHolidays().isMonthlyPublic() ? 1 : 0,
				domain.getListItemsOutput().getChildNursingVacation().isChildNursingLeave() ? 1 : 0,
				domain.getListItemsOutput().getNursingcareLeave().isNursingLeave() ? 1	: 0,
				domain.getListItemsOutput().getSpecialHoliday().stream()
						.map(itemDetai -> new KfnmtRptHdRemainHdsp(
								new KfnmtRptHdRemainHdspPk(
										domain.getLayOutId(),
										itemDetai),
								domain.getCompanyID(),
								domain.getItemSelectionCategory().value,
								domain.getEmployeeId().isPresent() ? domain.getEmployeeId().get():null,
								domain.getCode().v(),
								null))
						.collect(Collectors.toList()));
	}

	private static HolidaysRemainingManagement toDomain(KfnmtRptHdRemainOut entity) {
		return new HolidaysRemainingManagement(
				entity.cid,
				entity.cd,
				entity.name,
				new ItemOutputForm(entity.nursingCareLeave > 0, entity.remainChargeSub > 0, entity.representSub > 0,
						entity.outItemSub > 0, entity.outputHolidayForward > 0, entity.monthlyPublic > 0,
						entity.outputItemsHolidays > 0, entity.childCareLeave > 0, entity.yearlyHoliday > 0,
						entity.insideHours > 0, entity.insideHalfDay > 0, entity.numRemainPause > 0,
						entity.undigestedPause > 0, entity.pauseItem > 0, entity.yearlyReserved > 0,
						entity.kfnmtSpecialHolidays.stream()
								.map(itemDetail -> itemDetail.kfnmtRptHdRemainHdspPk.specialCd)
								.collect(Collectors.toList())),
				entity.layoutId,
				EnumAdaptor.valueOf(entity.itemSelType, ItemSelectionEnum.class),
				Optional.of(entity.sid)
		);

	}
}
