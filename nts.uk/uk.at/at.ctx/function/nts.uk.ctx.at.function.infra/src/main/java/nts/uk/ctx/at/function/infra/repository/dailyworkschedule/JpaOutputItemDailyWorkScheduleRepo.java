package nts.uk.ctx.at.function.infra.repository.dailyworkschedule;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.function.dom.dailyworkschedule.FreeSettingOfOutputItemForDailyWorkSchedule;
import nts.uk.ctx.at.function.dom.dailyworkschedule.FreeSettingOfOutputItemRepository;
import nts.uk.ctx.at.function.dom.dailyworkschedule.ItemSelectionType;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkSchedule;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputStandardSettingOfDailyWorkSchedule;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputStandardSettingRepository;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtRptWkDaiOutItem;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtRptWkDaiOutatd;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtRptWkDaiOutnote;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaOutputItemDailyWorkScheduleRepo extends JpaRepository implements OutputStandardSettingRepository, FreeSettingOfOutputItemRepository {
	
	private static final String GET_FREE_SETTING_BY_EMPLOYEE_AND_COMPANY = "SELECT outItem FROM KfnmtRptWkDaiOutItem outItem"
			+ "	WHERE outItem.cid = :companyId"
			+ "		AND outItem.sid = :employeeId"
			+ "		AND outItem.itemSelType = :itemSelType";

	private static final String GET_STANDARD_SETTING_BY_COMPANY = "SELECT ot FROM KfnmtRptWkDaiOutItem ot"
			+ "	WHERE ot.cid = :companyId"
			+ "		AND ot.itemSelType = :itemSelType";
	
	private static final String GET_SETTING_BY_COMPANY_AND_CODE = "SELECT ot FROM KfnmtRptWkDaiOutItem ot"
			+ "	WHERE ot.cid = :companyId"
			+ "		AND ot.itemSelType = :itemSelType"
			+ "		AND ot.itemCode = :itemCode";
	
	private static final String GET_SETTING_BY_EMPLOYEE_AND_COMPANY_AND_CODE = "SELECT outItem FROM KfnmtRptWkDaiOutItem outItem"
			+ "	WHERE outItem.cid = :companyId"
			+ "		AND outItem.sid = :employeeId"
			+ "		AND outItem.itemSelType = :itemSelType"
			+ "		AND outItem.itemCode = :itemCode";
	
	private static final String GET_ATD_BY_COMPANY_ID = "SELECT atd FROM KfnmtRptWkDaiOutatd atd"
			+ " WHERE atd.cid = :cid ORDER BY atd.id.orderNo";

	private static final String GET_NOTE_BY_COMPANY_ID = "SELECT note FROM KfnmtRptWkDaiOutnote note"
			+ " WHERE note.cid = :cid";

	@Override
	public Optional<FreeSettingOfOutputItemForDailyWorkSchedule> getFreeSettingByCompanyAndEmployee(String companyId,
			String employeeId) {
		Map<String, List<KfnmtRptWkDaiOutatd>> mapAtd = this.getLstKfnmtRptWkDaiOutatds(companyId).stream()
				.collect(Collectors.groupingBy(t -> t.getId().getLayoutId()));

		Map<String, List<KfnmtRptWkDaiOutnote>> mapNote = this.getLstKfnmtRptWkDaiOutnotes(companyId).stream()
				.collect(Collectors.groupingBy(t -> t.getId().getLayoutId()));

		List<KfnmtRptWkDaiOutItem> lstResult = this.queryProxy().query(GET_FREE_SETTING_BY_EMPLOYEE_AND_COMPANY, KfnmtRptWkDaiOutItem.class)
				.setParameter("companyId", companyId)
				.setParameter("employeeId", employeeId)
				.setParameter("itemSelType", ItemSelectionType.FREE_SETTING.value)
				.getList();
		
		lstResult = lstResult.stream()
				.map(t -> {
					t.setLstKfnmtRptWkDaiOutatds(mapAtd.get(t.getLayoutId()));
					t.setLstKfnmtRptWkDaiOutnotes(mapNote.get(t.getLayoutId()));
					return t;
				}).collect(Collectors.toList());
		
		if (lstResult.isEmpty()) {
			return Optional.empty();
		}
		
		FreeSettingOfOutputItemForDailyWorkSchedule result = FreeSettingOfOutputItemForDailyWorkSchedule
				.createFromMemento(new JpaFreeSettingOfDailyWorkScheduleGetMemento(
						lstResult,
						companyId,
						employeeId,
						ItemSelectionType.FREE_SETTING.value));

		return Optional.of(result);
	}

	@Override
	public Optional<OutputStandardSettingOfDailyWorkSchedule> getStandardSettingByCompanyId(String companyId) {
		Map<String, List<KfnmtRptWkDaiOutatd>> mapAtd = this.getLstKfnmtRptWkDaiOutatds(companyId).stream()
				.collect(Collectors.groupingBy(t -> t.getId().getLayoutId()));

		Map<String, List<KfnmtRptWkDaiOutnote>> mapNote = this.getLstKfnmtRptWkDaiOutnotes(companyId).stream()
				.collect(Collectors.groupingBy(t -> t.getId().getLayoutId()));

		List<KfnmtRptWkDaiOutItem> lstResult = this.queryProxy().query(GET_STANDARD_SETTING_BY_COMPANY, KfnmtRptWkDaiOutItem.class)
				.setParameter("companyId", companyId)
				.setParameter("itemSelType", ItemSelectionType.STANDARD_SELECTION.value)
				.getList().stream()
				.map(t -> {
					t.setLstKfnmtRptWkDaiOutatds(mapAtd.get(t.getLayoutId()));
					t.setLstKfnmtRptWkDaiOutnotes(mapNote.get(t.getLayoutId()));
					return t;
				}).collect(Collectors.toList());
		
		if (lstResult.isEmpty()) {
			return Optional.empty();
		}
		
		OutputStandardSettingOfDailyWorkSchedule result = OutputStandardSettingOfDailyWorkSchedule
				.createFromMemento(new JpaOutputStandardSettingOfDailyWorkScheduleGetMemento(
						lstResult,
						companyId,
						ItemSelectionType.STANDARD_SELECTION.value));

		return Optional.of(result);
	}

	@Override
	public void add(FreeSettingOfOutputItemForDailyWorkSchedule freeSettingOfOutputItemForDailyWorkSchedule) {
		List<KfnmtRptWkDaiOutItem> entities = freeSettingOfOutputItemForDailyWorkSchedule.getOutputItemDailyWorkSchedules().stream()
				.map(t -> {
					KfnmtRptWkDaiOutItem entity = new KfnmtRptWkDaiOutItem();
					entity.setItemSelType(ItemSelectionType.FREE_SETTING.value);
					entity.setCid(freeSettingOfOutputItemForDailyWorkSchedule.getCompanyId().v());
					entity.setSid(freeSettingOfOutputItemForDailyWorkSchedule.getEmployeeId().v());
					entity.setLayoutId(UUID.randomUUID().toString());
					entity.setContractCd(AppContexts.user().contractCode());
					t.saveToMemento(entity);
					return entity;
				})
				.collect(Collectors.toList());
		
		this.commandProxy().insertAll(entities);
	}

	@Override
	public void add(OutputStandardSettingOfDailyWorkSchedule outputStandard) {
		List<KfnmtRptWkDaiOutItem> entities = outputStandard.getOutputItems().stream()
				.map(t -> {
					KfnmtRptWkDaiOutItem entity = new KfnmtRptWkDaiOutItem();
					entity.setItemSelType(ItemSelectionType.STANDARD_SELECTION.value);
					entity.setCid(outputStandard.getCompanyId().v());
					entity.setLayoutId(UUID.randomUUID().toString());
					entity.setContractCd(AppContexts.user().contractCode());
					t.saveToMemento(entity);
					return entity;
				})
				.collect(Collectors.toList());
		
		this.commandProxy().insertAll(entities);
	}

	@Override
	public void deleteFreeSetting(String companyId, String employeeId, String layoutId) {
		Optional<FreeSettingOfOutputItemForDailyWorkSchedule> domain = this.getFreeSettingByCompanyAndEmployee(companyId, employeeId);
		// get all free setting id
		List<String> layoutIds = domain.get().getOutputItemDailyWorkSchedules().stream()
				.map(OutputItemDailyWorkSchedule::getLayoutId)
				.collect(Collectors.toList());
		// if layoutId is exist
		if (domain.isPresent() && layoutIds.contains(layoutId)) {
			// remove setting
			this.commandProxy().remove(KfnmtRptWkDaiOutItem.class, layoutId);
		}
		
	}

	@Override
	public Optional<OutputItemDailyWorkSchedule> findByCompanyIdAndEmployeeIdAndCode(String companyId,
			String employeeId, String code) {
		Map<String, List<KfnmtRptWkDaiOutatd>> mapAtd = this.getLstKfnmtRptWkDaiOutatds(companyId).stream()
				.collect(Collectors.groupingBy(t -> t.getId().getLayoutId()));

		Map<String, List<KfnmtRptWkDaiOutnote>> mapNote = this.getLstKfnmtRptWkDaiOutnotes(companyId).stream()
				.collect(Collectors.groupingBy(t -> t.getId().getLayoutId()));

		return this.queryProxy()
				.query(GET_SETTING_BY_EMPLOYEE_AND_COMPANY_AND_CODE, KfnmtRptWkDaiOutItem.class)
				.setParameter("companyId", companyId)
				.setParameter("employeeId", employeeId)
				.setParameter("itemSelType", ItemSelectionType.FREE_SETTING.value)
				.setParameter("itemCode", code)
				.getSingle(t -> {
					t.setLstKfnmtRptWkDaiOutatds(mapAtd.get(t.getLayoutId()));
					t.setLstKfnmtRptWkDaiOutnotes(mapNote.get(t.getLayoutId()));
					return t;
				}).map(outputItem -> new OutputItemDailyWorkSchedule(outputItem));
	}

	@Override
	public void deleteStandardSetting(String companyId, String layoutId) {
		Optional<OutputStandardSettingOfDailyWorkSchedule> domain = this.getStandardSettingByCompanyId(companyId);
		// get all standard setting id
		List<String> layoutIds = domain.get().getOutputItems().stream().map(OutputItemDailyWorkSchedule::getLayoutId).collect(Collectors.toList());
		// if layoutId is exist
		if (domain.isPresent() && layoutIds.contains(layoutId)) {
			// remove setting
			this.commandProxy().remove(KfnmtRptWkDaiOutItem.class, layoutId);
		}
	}

	@Override
	public Optional<OutputItemDailyWorkSchedule> findByCompanyIdAndCode(String companyId, String code) {
		Map<String, List<KfnmtRptWkDaiOutatd>> mapAtd = this.getLstKfnmtRptWkDaiOutatds(companyId).stream()
				.collect(Collectors.groupingBy(t -> t.getId().getLayoutId()));

		Map<String, List<KfnmtRptWkDaiOutnote>> mapNote = this.getLstKfnmtRptWkDaiOutnotes(companyId).stream()
				.collect(Collectors.groupingBy(t -> t.getId().getLayoutId()));

		return this.queryProxy().query(GET_SETTING_BY_COMPANY_AND_CODE, KfnmtRptWkDaiOutItem.class)
				.setParameter("companyId", companyId)
				.setParameter("itemSelType", ItemSelectionType.STANDARD_SELECTION.value)
				.setParameter("itemCode", code)
				.getSingle(t -> {
					t.setLstKfnmtRptWkDaiOutatds(mapAtd.get(t.getLayoutId()));
					t.setLstKfnmtRptWkDaiOutnotes(mapNote.get(t.getLayoutId()));
					return t;
				}).map(outputItem -> new OutputItemDailyWorkSchedule(outputItem));
	}

	/**
	 * Gets the lst kfnmt rpt wk dai outatds.
	 *
	 * @param companyId the company id
	 * @return the lst kfnmt rpt wk dai outatds
	 */
	private List<KfnmtRptWkDaiOutatd> getLstKfnmtRptWkDaiOutatds(String companyId) {
		return this.queryProxy().query(GET_ATD_BY_COMPANY_ID, KfnmtRptWkDaiOutatd.class)
				.setParameter("cid", companyId)
				.getList();
	}


	/**
	 * Gets the lst kfnmt rpt wk dai outnotes.
	 *
	 * @param companyId the company id
	 * @return the lst kfnmt rpt wk dai outnotes
	 */
	private List<KfnmtRptWkDaiOutnote> getLstKfnmtRptWkDaiOutnotes(String companyId) {
		return this.queryProxy().query(GET_NOTE_BY_COMPANY_ID, KfnmtRptWkDaiOutnote.class)
				.setParameter("cid", companyId)
				.getList();
	}
}
