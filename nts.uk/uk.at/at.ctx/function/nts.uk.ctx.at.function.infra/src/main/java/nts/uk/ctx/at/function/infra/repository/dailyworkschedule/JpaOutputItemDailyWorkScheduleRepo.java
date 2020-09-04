package nts.uk.ctx.at.function.infra.repository.dailyworkschedule;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.SneakyThrows;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.uk.ctx.at.function.dom.dailyworkschedule.FreeSettingOfOutputItemForDailyWorkSchedule;
import nts.uk.ctx.at.function.dom.dailyworkschedule.FreeSettingOfOutputItemRepository;
import nts.uk.ctx.at.function.dom.dailyworkschedule.NameWorkTypeOrHourZone;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputStandardSettingOfDailyWorkSchedule;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputStandardSettingRepository;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtRptWkDaiOutItem;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtRptWkDaiOutatd;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtRptWkDaiOutatdPK;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtRptWkDaiOutnote;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtRptWkDaiOutnotePK;

public class JpaOutputItemDailyWorkScheduleRepo extends JpaRepository implements OutputStandardSettingRepository, FreeSettingOfOutputItemRepository {
	
	public static final String GET_FREE_SETTING_BY_EMPLOYEE_AND_COMPANY = "SELECT outItem FROM KfnmtRptWkDaiOutItem outItem"
			+ "	WHERE outItem.cid = :companyId"
			+ "		AND outItem.sid = :employeeId";

	public static final String GET_STANDARD_SETTING_BY_COMPANY = "SELECT ot FROM KfnmtRptWkDaiOutItem ot"
			+ "	WHERE ot.cid = ?";

	@Override
	public Optional<FreeSettingOfOutputItemForDailyWorkSchedule> getFreeSettingByCompanyAndEmployee(String companyId,
			String employeeId) {
		
		return Optional.empty();
	}

	@Override
	public Optional<OutputStandardSettingOfDailyWorkSchedule> getStandardSettingByCompanyId(String companyId) {
//		try (PreparedStatement statement = this.connection().prepareStatement(GET_STANDARD_SETTING_BY_COMPANY)) {
//			statement.setString(1, companyId);
//			return new NtsResultSet(statement.executeQuery()).getList((rec -> {
//				KfnmtRptWkDaiOutItem entity = new KfnmtRptWkDaiOutItem();
//				entity.setLayoutId(rec.getString("LAYOUT_ID"));
//				entity.setSid(rec.getString("SID"));
//				entity.setCid(rec.getString("CID"));
//				entity.setItemSelType(rec.getInt("ITEM_SEL_TYPE"));
//				entity.setItemName(new OutputItemSettingName(rec.getString("ITEM_NAME")));
//				entity.setItemCode(new OutputItemSettingCode(rec.getString("ITEM_CD")));
//				entity.setWorkTypeNameDisplay(NameWorkTypeOrHourZone.valueOf(rec.getBigDecimal("WORKTYPE_NAME_DISPLAY").intValue()));
//				entity.setNoteInputNo(rec.getBigDecimal("NOTE_INPUT_NO"));
//				entity.setCharSizeType(rec.getBigDecimal("CHAR_SIZE_TYPE"));
//				entity.setLstKfnmtRptWkDaiOutatds(mapKfnmtAttendanceDisplay.get(entity.getLayoutId()));
//				entity.setLstKfnmtRptWkDaiOutnotes(mapKfnmtPrintRemarkCont.get(entity.getLayoutId()));
//				return this.toDomain(entity);
//			});
//		}
		return Optional.empty();
	}

	@Override
	public void add(FreeSettingOfOutputItemForDailyWorkSchedule freeSettingOfOutputItemForDailyWorkSchedule) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(OutputStandardSettingOfDailyWorkSchedule outputStandard) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteFreeSetting(String companyId, String employeeId, String layoutId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<FreeSettingOfOutputItemForDailyWorkSchedule> findByCompanyIdAndEmployeeIdAndCode(String companyId,
			String employeeId, String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteStandardSetting(String companyId, String layoutId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<OutputStandardSettingOfDailyWorkSchedule> findByCompanyIdAndCode(String companyId, String code) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Gets the lst kfnmt rpt wk dai outatds.
	 *
	 * @param layoutId the layout id
	 * @return the lst kfnmt rpt wk dai outatds
	 */
	@SneakyThrows
	private List<KfnmtRptWkDaiOutatd> getLstKfnmtRptWkDaiOutatds(String layoutId) {
		List<KfnmtRptWkDaiOutatd> lstKfnmtRptWkDaiOutatds = new ArrayList<>();
		String sqlJDBC1 = "select * from KFNMT_RPT_WK_DAI_OUTATD where LAYOUT_ID = ? ORDER BY ORDER_NO";
		try (PreparedStatement statement1 = this.connection().prepareStatement(sqlJDBC1)) {
			statement1.setString(1, layoutId);
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
	 * @param layoutId the layout id
	 * @return the lst kfnmt rpt wk dai outnotes
	 */
	@SneakyThrows
	private List<KfnmtRptWkDaiOutnote> getLstKfnmtRptWkDaiOutnotes(String layoutId) {
		List<KfnmtRptWkDaiOutnote> lstKfnmtRptWkDaiOutnotes = new ArrayList<>();
		String sqlJDBC2 = "select * from KFNMT_RPT_WK_DAI_OUTNOTE where LAYOUT_ID = ?";
		try (PreparedStatement statement2 = this.connection().prepareStatement(sqlJDBC2)) {
			statement2.setString(1, layoutId);
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

}
