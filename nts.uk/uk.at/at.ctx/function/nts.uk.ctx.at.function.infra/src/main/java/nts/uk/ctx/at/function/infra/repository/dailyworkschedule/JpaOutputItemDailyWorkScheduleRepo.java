package nts.uk.ctx.at.function.infra.repository.dailyworkschedule;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.SneakyThrows;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.uk.ctx.at.function.dom.dailyworkschedule.FreeSettingOfOutputItemForDailyWorkSchedule;
import nts.uk.ctx.at.function.dom.dailyworkschedule.FreeSettingOfOutputItemRepository;
import nts.uk.ctx.at.function.dom.dailyworkschedule.ItemSelectionType;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkSchedule;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputStandardSettingOfDailyWorkSchedule;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputStandardSettingRepository;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtRptWkDaiOutItem;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtRptWkDaiOutatd;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtRptWkDaiOutatdPK;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtRptWkDaiOutnote;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtRptWkDaiOutnotePK;

@Stateless
public class JpaOutputItemDailyWorkScheduleRepo extends JpaRepository implements OutputStandardSettingRepository, FreeSettingOfOutputItemRepository {
	
	public static final String GET_FREE_SETTING_BY_EMPLOYEE_AND_COMPANY = "SELECT outItem FROM KfnmtRptWkDaiOutItem outItem"
			+ "	WHERE outItem.cid = :companyId"
			+ "		AND outItem.sid = :employeeId"
			+ "		AND outItem.itemSelType = :itemSelType";

	public static final String GET_STANDARD_SETTING_BY_COMPANY = "SELECT ot FROM KfnmtRptWkDaiOutItem ot"
			+ "	WHERE ot.cid = ?"
			+ "		AND ot.itemSelType = :itemSelType";
	
	public static final String GET_SETTING_BY_COMPANY_AND_CODE = "SELECT ot FROM KfnmtRptWkDaiOutItem ot"
			+ "	WHERE ot.cid = ?"
			+ "		AND ot.itemSelType = :itemSelType"
			+ "		AND ot.itemCode = :itemCode";
	
	public static final String GET_SETTING_BY_EMPLOYEE_AND_COMPANY_AND_CODE = "SELECT outItem FROM KfnmtRptWkDaiOutItem outItem"
			+ "	WHERE outItem.cid = :companyId"
			+ "		AND outItem.sid = :employeeId"
			+ "		AND outItem.itemSelType = :itemSelType"
			+ "		AND outItem.itemCode = :itemCode";

	@Override
	@SneakyThrows
	public Optional<FreeSettingOfOutputItemForDailyWorkSchedule> getFreeSettingByCompanyAndEmployee(String companyId,
			String employeeId) {
		Map<String, List<KfnmtRptWkDaiOutatd>> mapAtd = this.getLstKfnmtRptWkDaiOutatds(companyId).stream()
				.collect(Collectors.groupingBy(t -> t.getId().getLayoutId()));

		Map<String, List<KfnmtRptWkDaiOutnote>> mapNote = this.getLstKfnmtRptWkDaiOutnotes(companyId).stream()
				.collect(Collectors.groupingBy(t -> t.getId().getLayoutId()));

		try (PreparedStatement statement = this.connection().prepareStatement(GET_FREE_SETTING_BY_EMPLOYEE_AND_COMPANY)) {
			statement.setString(1, companyId);
			statement.setString(2, employeeId);
			statement.setInt(3, ItemSelectionType.FREE_SETTING.value);
			List<KfnmtRptWkDaiOutItem> lstResult = new NtsResultSet(statement.executeQuery()).getList(rec -> {
				return this.convertToEntity(rec, mapAtd, mapNote);
			});

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
	}

	@Override
	@SneakyThrows
	public Optional<OutputStandardSettingOfDailyWorkSchedule> getStandardSettingByCompanyId(String companyId) {
		Map<String, List<KfnmtRptWkDaiOutatd>> mapAtd = this.getLstKfnmtRptWkDaiOutatds(companyId).stream()
				.collect(Collectors.groupingBy(t -> t.getId().getLayoutId()));

		Map<String, List<KfnmtRptWkDaiOutnote>> mapNote = this.getLstKfnmtRptWkDaiOutnotes(companyId).stream()
				.collect(Collectors.groupingBy(t -> t.getId().getLayoutId()));

		try (PreparedStatement statement = this.connection().prepareStatement(GET_STANDARD_SETTING_BY_COMPANY)) {
			statement.setString(1, companyId);
			statement.setInt(2, ItemSelectionType.STANDARD_SELECTION.value);
			List<KfnmtRptWkDaiOutItem> lstResult = new NtsResultSet(statement.executeQuery()).getList(rec -> {
				return this.convertToEntity(rec, mapAtd, mapNote);
			});

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
	}

	@Override
	public void add(FreeSettingOfOutputItemForDailyWorkSchedule freeSettingOfOutputItemForDailyWorkSchedule) {
		List<KfnmtRptWkDaiOutItem> entities = freeSettingOfOutputItemForDailyWorkSchedule.getOutputItemDailyWorkSchedules().stream()
				.map(t -> {
					KfnmtRptWkDaiOutItem entity = new KfnmtRptWkDaiOutItem();
					t.saveToMemento(entity);
					entity.setItemSelType(ItemSelectionType.FREE_SETTING.value);
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
					t.saveToMemento(entity);
					entity.setItemSelType(ItemSelectionType.STANDARD_SELECTION.value);
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
				.map(OutputItemDailyWorkSchedule::getOutputLayoutId)
				.collect(Collectors.toList());
		// if layoutId is exist
		if (domain.isPresent() && layoutIds.contains(layoutId)) {
			// remove setting
			this.commandProxy().remove(KfnmtRptWkDaiOutItem.class, layoutId);
		}
		
	}

	@Override
	@SneakyThrows
	public Optional<FreeSettingOfOutputItemForDailyWorkSchedule> findByCompanyIdAndEmployeeIdAndCode(String companyId,
			String employeeId, String code) {
		Map<String, List<KfnmtRptWkDaiOutatd>> mapAtd = this.getLstKfnmtRptWkDaiOutatds(companyId).stream()
				.collect(Collectors.groupingBy(t -> t.getId().getLayoutId()));

		Map<String, List<KfnmtRptWkDaiOutnote>> mapNote = this.getLstKfnmtRptWkDaiOutnotes(companyId).stream()
				.collect(Collectors.groupingBy(t -> t.getId().getLayoutId()));

		try (PreparedStatement statement = this.connection().prepareStatement(GET_FREE_SETTING_BY_EMPLOYEE_AND_COMPANY)) {
			statement.setString(1, companyId);
			statement.setString(2, employeeId);
			statement.setInt(3, ItemSelectionType.FREE_SETTING.value);
			statement.setString(4, code);
			List<KfnmtRptWkDaiOutItem> lstResult = new NtsResultSet(statement.executeQuery()).getList(rec -> {
				return this.convertToEntity(rec, mapAtd, mapNote);
			});

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
	}

	@Override
	public void deleteStandardSetting(String companyId, String layoutId) {
		Optional<OutputStandardSettingOfDailyWorkSchedule> domain = this.getStandardSettingByCompanyId(companyId);
		// get all standard setting id
		List<String> layoutIds = domain.get().getOutputItems().stream().map(OutputItemDailyWorkSchedule::getOutputLayoutId).collect(Collectors.toList());
		// if layoutId is exist
		if (domain.isPresent() && layoutIds.contains(layoutId)) {
			// remove setting
			this.commandProxy().remove(KfnmtRptWkDaiOutItem.class, layoutId);
		}
	}

	@Override
	@SneakyThrows
	public Optional<OutputStandardSettingOfDailyWorkSchedule> findByCompanyIdAndCode(String companyId, String code) {
		Map<String, List<KfnmtRptWkDaiOutatd>> mapAtd = this.getLstKfnmtRptWkDaiOutatds(companyId).stream()
				.collect(Collectors.groupingBy(t -> t.getId().getLayoutId()));

		Map<String, List<KfnmtRptWkDaiOutnote>> mapNote = this.getLstKfnmtRptWkDaiOutnotes(companyId).stream()
				.collect(Collectors.groupingBy(t -> t.getId().getLayoutId()));

		try (PreparedStatement statement = this.connection().prepareStatement(GET_SETTING_BY_EMPLOYEE_AND_COMPANY_AND_CODE)) {
			statement.setString(1, companyId);
			statement.setInt(2, ItemSelectionType.STANDARD_SELECTION.value);
			statement.setString(3, code);
			List<KfnmtRptWkDaiOutItem> lstResult = new NtsResultSet(statement.executeQuery()).getList(rec -> {
				return this.convertToEntity(rec, mapAtd, mapNote);
			});

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
	}

	/**
	 * Gets the lst kfnmt rpt wk dai outatds.
	 *
	 * @param companyId the company id
	 * @return the lst kfnmt rpt wk dai outatds
	 */
	@SneakyThrows
	private List<KfnmtRptWkDaiOutatd> getLstKfnmtRptWkDaiOutatds(String companyId) {
		List<KfnmtRptWkDaiOutatd> lstKfnmtRptWkDaiOutatds = new ArrayList<>();
		String sqlJDBC1 = "select * from KFNMT_RPT_WK_DAI_OUTATD where CID = ? ORDER BY ORDER_NO";
		try (PreparedStatement statement1 = this.connection().prepareStatement(sqlJDBC1)) {
			statement1.setString(1, companyId);
			lstKfnmtRptWkDaiOutatds
					.addAll(new NtsResultSet(statement1.executeQuery()).getList(rec -> {
						KfnmtRptWkDaiOutatdPK pk = new KfnmtRptWkDaiOutatdPK();
						pk.setLayoutId(rec.getString("LAYOUT_ID"));
						pk.setOrderNo(rec.getLong("ORDER_NO"));
						KfnmtRptWkDaiOutatd entity = new KfnmtRptWkDaiOutatd();
						entity.setId(pk);
						entity.setAtdDisplay(rec.getBigDecimal("ATD_DISPLAY"));
						entity.setCid(rec.getString("CID"));
						return entity;
					}));
		}
		return lstKfnmtRptWkDaiOutatds;

	}


	/**
	 * Gets the lst kfnmt rpt wk dai outnotes.
	 *
	 * @param companyId the company id
	 * @return the lst kfnmt rpt wk dai outnotes
	 */
	@SneakyThrows
	private List<KfnmtRptWkDaiOutnote> getLstKfnmtRptWkDaiOutnotes(String companyId) {
		List<KfnmtRptWkDaiOutnote> lstKfnmtRptWkDaiOutnotes = new ArrayList<>();
		String sqlJDBC2 = "select * from KFNMT_RPT_WK_DAI_OUTNOTE where CID = ?";
		try (PreparedStatement statement2 = this.connection().prepareStatement(sqlJDBC2)) {
			statement2.setString(1, companyId);
			lstKfnmtRptWkDaiOutnotes
					.addAll(new NtsResultSet(statement2.executeQuery()).getList(rec -> {
						KfnmtRptWkDaiOutnotePK pk = new KfnmtRptWkDaiOutnotePK();
						pk.setLayoutId(rec.getString("LAYOUT_ID"));
						pk.setPrintItem(rec.getLong("PRINT_ITEM"));
						KfnmtRptWkDaiOutnote entity = new KfnmtRptWkDaiOutnote();
						entity.setId(pk);
						entity.setUseCls(rec.getBigDecimal("USE_CLS"));
						entity.setCid(rec.getString("CID"));
						return entity;
					}));
		}

		return lstKfnmtRptWkDaiOutnotes;
	}
	
	/**
	 * Convert to entity.
	 *
	 * @param rec the rec
	 * @param mapAtd the map atd
	 * @param mapNote the map note
	 * @return the kfnmt rpt wk dai out item
	 */
	private KfnmtRptWkDaiOutItem convertToEntity(NtsResultRecord rec, Map<String
			, List<KfnmtRptWkDaiOutatd>> mapAtd
			, Map<String, List<KfnmtRptWkDaiOutnote>> mapNote) {
		KfnmtRptWkDaiOutItem entity = new KfnmtRptWkDaiOutItem(rec.getString("LAYOUT_ID")
				, rec.getInt("ITEM_SEL_TYPE")
				, rec.getString("CID")
				, rec.getString("SID")
				, rec.getString("ITEM_CD")
				, rec.getString("ITEM_NAME")
				, rec.getBigDecimal("WORKTYPE_NAME_DISPLAY")
				, rec.getBigDecimal("NOTE_INPUT_NO")
				, rec.getBigDecimal("CHAR_SIZE_TYPE")
				, mapAtd.get(rec.getString("LAYOUT_ID"))
				, mapNote.get(rec.getString("LAYOUT_ID")));
		return entity;
	}

}
