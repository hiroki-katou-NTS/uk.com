package nts.uk.ctx.at.function.infra.repository.attendancetype;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.SneakyThrows;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.function.dom.attendancetype.AttendanceType;
import nts.uk.ctx.at.function.dom.attendancetype.AttendanceTypeRepository;
import nts.uk.ctx.at.function.dom.attendancetype.ScreenUseAtr;
import nts.uk.ctx.at.function.infra.entity.attendancetype.KmnmtAttendanceType;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class JpaAttendanceTypeRepository extends JpaRepository implements AttendanceTypeRepository {

	private static final String SEL_ITEM_BY_TYPE = "SELECT a " + "FROM KmnmtAttendanceType a "
			+ "WHERE a.kmnmtAttendanceTypePK.companyId = :companyId "
			+ "AND a.kmnmtAttendanceTypePK.screenUseAtr = :screenUseAtr";

	private static final String SEL_ITEM_BY_TYPE_AND_ATR = SEL_ITEM_BY_TYPE
			+ " AND a.kmnmtAttendanceTypePK.attendanctType = :attendanctType";

	@Override
	public List<AttendanceType> getItemByScreenUseAtr(String companyID, int screenUseAtr) {
		return this.queryProxy().query(SEL_ITEM_BY_TYPE, KmnmtAttendanceType.class).setParameter("companyId", companyID)
				.setParameter("screenUseAtr", screenUseAtr).getList().stream()
				.map(x -> AttendanceType.createSimpleFromJavaType(x.kmnmtAttendanceTypePK.companyId,
						x.kmnmtAttendanceTypePK.attendanceItemId, x.kmnmtAttendanceTypePK.screenUseAtr,
						x.kmnmtAttendanceTypePK.attendanctType))
				.collect(Collectors.toList());
	}

	@Override
	@SneakyThrows
	public List<AttendanceType> getItemByAtrandType(String companyId, int screenUseAtr, int attendanceItemType) {

		String sql = "select CID, ATTENDANCEITEM_ID, SCREEN_USE_ATR, ATTENDANCEITEM_TYPE from KMNMT_ATTENDANCE_TYPE"
				+ " where CID = ?" + " and SCREEN_USE_ATR = ?" + " and ATTENDANCEITEM_TYPE = ?";
		try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
			stmt.setString(1, companyId);
			stmt.setInt(2, screenUseAtr);
			stmt.setInt(3, attendanceItemType);

			return new NtsResultSet(stmt.executeQuery()).getList(r -> {
				return AttendanceType.createSimpleFromJavaType(companyId, r.getInt("ATTENDANCEITEM_ID"), screenUseAtr,
						attendanceItemType);
			});
		}

	}

	@Override
	@SneakyThrows
	public List<AttendanceType> getItemByAtrandType(String companyId, List<ScreenUseAtr> screenUseAtr,
			int attendanceItemType, List<Integer> AttendanceIds) {
		if(CollectionUtil.isEmpty(screenUseAtr) && CollectionUtil.isEmpty(AttendanceIds)){
			return getItemByAtrandType(companyId, attendanceItemType);
		}
		
		if(CollectionUtil.isEmpty(AttendanceIds)){
			return getItemByAtrandType(companyId, attendanceItemType).stream()
					.filter(c -> screenUseAtr.contains(c.getScreenUseAtr().value)).collect(Collectors.toList());
		}
		
		List<AttendanceType> result = new ArrayList<>();
		
		CollectionUtil.split(AttendanceIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, ids -> {
			result.addAll(runExternal(companyId, screenUseAtr, attendanceItemType, ids));
		});
		
		return result;
		
	}

	@SneakyThrows
	private List<AttendanceType> runExternal(String companyId, List<ScreenUseAtr> screenUseAtr, int attendanceItemType, List<Integer> ids) {
		StringBuilder queryBuilder = new StringBuilder("SELECT CID, ATTENDANCEITEM_ID, SCREEN_USE_ATR, ATTENDANCEITEM_TYPE FROM KMNMT_ATTENDANCE_TYPE");
		queryBuilder.append(" WHERE CID = ?");
		if(!CollectionUtil.isEmpty(screenUseAtr)){
			queryBuilder.append(" AND SCREEN_USE_ATR IN (");
			queryBuilder.append(screenUseAtr.stream().map(x -> "?").collect(Collectors.joining(",")));
			queryBuilder.append(" )");
		}
		queryBuilder.append(" AND ATTENDANCEITEM_TYPE = ?");
		if(!CollectionUtil.isEmpty(ids)){
			queryBuilder.append(" AND ATTENDANCEITEM_ID IN (");
			queryBuilder.append(ids.stream().map(x -> "?").collect(Collectors.joining(",")));
			queryBuilder.append(" )");
		}
		
		try (PreparedStatement stmt = this.connection().prepareStatement(queryBuilder.toString())) {
			stmt.setString(1, companyId);
			for(int i = 0; i < screenUseAtr.size(); i++){
				stmt.setInt(i + 2, screenUseAtr.get(i).value);
			}
			stmt.setInt(screenUseAtr.isEmpty() ? 2 : screenUseAtr.size() + 2, attendanceItemType);
			for(int i = 0; i < ids.size(); i++){
				stmt.setInt(i + 3 + screenUseAtr.size(), ids.get(i));
			}
			
			return new NtsResultSet(stmt.executeQuery()).getList(r -> {
				return AttendanceType.createSimpleFromJavaType(
						companyId,
						r.getInt("ATTENDANCEITEM_ID"),
						r.getInt("SCREEN_USE_ATR"),
						attendanceItemType);
			});
		}
	}

	@Override
	@SneakyThrows
	public List<AttendanceType> getItemByAtrandType(String companyId, int attendanceItemType) {
		String sql = "select CID, ATTENDANCEITEM_ID, SCREEN_USE_ATR, ATTENDANCEITEM_TYPE from KMNMT_ATTENDANCE_TYPE"
				+ " where CID = ?" + " and ATTENDANCEITEM_TYPE = ?";
		try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
			stmt.setString(1, companyId);
			stmt.setInt(2, attendanceItemType);

			return new NtsResultSet(stmt.executeQuery()).getList(r -> {
				return AttendanceType.createSimpleFromJavaType(companyId, r.getInt("ATTENDANCEITEM_ID"),
						r.getInt("SCREEN_USE_ATR"), attendanceItemType);
			});
		}
	}

	@Override
	@SneakyThrows
	public List<AttendanceType> getItemByAtrandType(String companyId, List<ScreenUseAtr> screenUseAtr,
			int attendanceItemType) {
		if(CollectionUtil.isEmpty(screenUseAtr)){
			return getItemByAtrandType(companyId, attendanceItemType);
		}
		StringBuilder queryBuilder = new StringBuilder("SELECT CID, ATTENDANCEITEM_ID, SCREEN_USE_ATR, ATTENDANCEITEM_TYPE FROM KMNMT_ATTENDANCE_TYPE");
		queryBuilder.append(" WHERE CID = ?  AND SCREEN_USE_ATR IN (");
		queryBuilder.append(screenUseAtr.stream().map(x -> "?").collect(Collectors.joining(",")));
		queryBuilder.append(") AND ATTENDANCEITEM_TYPE = ?");
		
		try (PreparedStatement stmt = this.connection().prepareStatement(queryBuilder.toString())) {
			stmt.setString(1, companyId);
			for(int i = 0; i < screenUseAtr.size(); i++){
				stmt.setInt(i + 2, screenUseAtr.get(i).value);
			}
			stmt.setInt(screenUseAtr.isEmpty() ? 2 : screenUseAtr.size() + 2, attendanceItemType);
			
			return new NtsResultSet(stmt.executeQuery()).getList(r -> {
				return AttendanceType.createSimpleFromJavaType(
						companyId,
						r.getInt("ATTENDANCEITEM_ID"),
						r.getInt("SCREEN_USE_ATR"),
						attendanceItemType);
			});
		}
	}
}
