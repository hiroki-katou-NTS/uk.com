package nts.uk.screen.at.infra.schedule.workschedulestate;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedulestate.KscdtScheState;
import nts.uk.screen.at.app.schedule.workschedulestate.WorkScheduleStateScreenDto;
import nts.uk.screen.at.app.schedule.workschedulestate.WorkScheduleStateScreenRepository;

/**
 * 
 * @author sonnh1
 *
 */
@Stateless
public class JpaWorkScheduleStateScreenRepository extends JpaRepository implements WorkScheduleStateScreenRepository {

	private final String SELECT_BY_SID_AND_DATE_AND_SCHEDULE_ITEM_ID = "SELECT a FROM KscdtScheState a "
			+ "WHERE a.kscdtScheStatePK.employeeId IN :sId "
			+ "AND (a.kscdtScheStatePK.date BETWEEN :startDate AND :endDate) "
			+ "AND (a.kscdtScheStatePK.scheduleItemId = 1 OR a.kscdtScheStatePK.scheduleItemId = 2 "
			+ "OR a.kscdtScheStatePK.scheduleItemId = 3 OR a.kscdtScheStatePK.scheduleItemId = 4)";

	private static WorkScheduleStateScreenDto toDto(KscdtScheState entity) {
		return new WorkScheduleStateScreenDto(entity.kscdtScheStatePK.employeeId,
				entity.kscdtScheStatePK.date, entity.kscdtScheStatePK.scheduleItemId,
				entity.scheduleEditState);
	}

	@Override
	public List<WorkScheduleStateScreenDto> getByListSidAndDateAndScheId(List<String> sId, GeneralDate startDate,
			GeneralDate endDate) {
		List<WorkScheduleStateScreenDto> datas = new ArrayList<WorkScheduleStateScreenDto>();
		CollectionUtil.split(sId, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subIdList -> {
			String sql = "select SID, SCHE_ITEM_ID, YMD, SCHE_EDIT_STATE"
					+ " from KSCDT_SCHE_STATE"
					+ " where SID in (" + NtsStatement.In.createParamsString(subIdList) + ")"
					+ " and YMD between ? and ?"
					+ " and SCHE_ITEM_ID in (1, 2, 3, 4)";
			try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
				for (int i = 0; i < subIdList.size(); i++) {
					stmt.setString(i + 1, subIdList.get(i));
				}
				stmt.setDate(subIdList.size() + 1, Date.valueOf(startDate.toLocalDate()));
				stmt.setDate(subIdList.size() + 2, Date.valueOf(endDate.toLocalDate()));
				
				List<WorkScheduleStateScreenDto> results = new NtsResultSet(stmt.executeQuery()).getList(rec -> {
					return new WorkScheduleStateScreenDto(
							rec.getString("SID"),
							rec.getGeneralDate("YMD"),
							rec.getInt("SCHE_ITEM_ID"),
							rec.getInt("SCHE_EDIT_STATE")
							);
				});
				datas.addAll(results);
				
			} catch (SQLException ex) {
				throw new RuntimeException(ex);
			}
		});
		return datas;
	}
	
}
