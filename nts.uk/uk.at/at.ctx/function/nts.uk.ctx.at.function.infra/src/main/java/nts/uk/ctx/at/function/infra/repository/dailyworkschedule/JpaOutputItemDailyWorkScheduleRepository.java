/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.infra.repository.dailyworkschedule;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.SneakyThrows;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkSchedule;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemDailyWorkScheduleRepository;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtRptWkDaiOutItem;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtRptWkDaiOutatd;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtRptWkDaiOutatdPK;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtRptWkDaiOutnote;
import nts.uk.ctx.at.function.infra.entity.dailyworkschedule.KfnmtRptWkDaiOutnotePK;

/**
 * The Class JpaOutputItemDailyWorkScheduleRepository.
 * author: HoangDD
 */
@Stateless
public class JpaOutputItemDailyWorkScheduleRepository extends JpaRepository implements OutputItemDailyWorkScheduleRepository {
	
	private static final String SELECT_BY_LAYOUT_ID = "SELECT outItem FROM KfnmtRptWkDaiOutItem outItem"
			+ "	WHERE outItem.layoutId = ?";

	@Override
	@SneakyThrows
	public Optional<OutputItemDailyWorkSchedule> findByLayoutId(String layoutId) {
		String sqlJDBC2 = "select * from KFNMT_RPT_WK_DAI_OUTNOTE where LAYOUT_ID = ?";
		String sqlJDBC1 = "select * from KFNMT_RPT_WK_DAI_OUTATD where LAYOUT_ID = ? ORDER BY ORDER_NO";
		List<KfnmtRptWkDaiOutatd> lstKfnmtRptWkDaiOutatds = new ArrayList<>();
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
		
		List<KfnmtRptWkDaiOutnote> lstKfnmtRptWkDaiOutnotes = new ArrayList<>();
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
		try (PreparedStatement statement = this.connection().prepareStatement(SELECT_BY_LAYOUT_ID)) {
			statement.setString(1, layoutId);
			Optional<OutputItemDailyWorkSchedule> result = new NtsResultSet(statement.executeQuery()).getSingle(rec -> {
				KfnmtRptWkDaiOutItem entity = new KfnmtRptWkDaiOutItem(rec.getString("LAYOUT_ID")
						, rec.getInt("ITEM_SEL_TYPE")
						, rec.getString("CID")
						, rec.getString("SID")
						, rec.getString("ITEM_CD")
						, rec.getString("ITEM_NAME")
						, rec.getBigDecimal("WORKTYPE_NAME_DISPLAY")
						, rec.getBigDecimal("NOTE_INPUT_NO")
						, rec.getBigDecimal("CHAR_SIZE_TYPE")
						, lstKfnmtRptWkDaiOutatds
						, lstKfnmtRptWkDaiOutnotes);
				return new OutputItemDailyWorkSchedule(entity);
			});
			return result;
		}
	}

	@Override
	public void update(OutputItemDailyWorkSchedule domain, int selectionType, String companyId, String employeeId) {
		KfnmtRptWkDaiOutItem entity = new KfnmtRptWkDaiOutItem();
		domain.saveToMemento(entity);
		entity.setItemSelType(selectionType);
		entity.setCid(companyId);
		entity.setSid(employeeId);
		this.commandProxy().update(entity);
	}
}
