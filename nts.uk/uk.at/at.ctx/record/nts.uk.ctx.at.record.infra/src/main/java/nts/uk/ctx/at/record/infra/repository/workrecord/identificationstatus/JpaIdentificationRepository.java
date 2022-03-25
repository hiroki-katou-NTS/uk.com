package nts.uk.ctx.at.record.infra.repository.workrecord.identificationstatus;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.logging.log4j.util.Strings;

import lombok.SneakyThrows;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.Identification;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.repository.IdentificationRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.identificationstatus.KrcdtIdentificationStatus;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaIdentificationRepository extends JpaRepository implements IdentificationRepository {

	private static final String GET_BY_EMPLOYEE_ID = "SELECT c from KrcdtIdentificationStatus c "
			+ " WHERE c.krcdtIdentificationStatusPK.companyID = :companyID "
			+ " AND c.krcdtIdentificationStatusPK.employeeId = :employeeId "
			+ " AND c.krcdtIdentificationStatusPK.processingYmd BETWEEN :startDate AND :endDate  ";

//	private static final String GET_BY_LIST_EMPLOYEE_ID = "SELECT c from KrcdtIdentificationStatus c "
//			+ " WHERE c.krcdtIdentificationStatusPK.companyID = :companyID "
//			+ " AND c.krcdtIdentificationStatusPK.employeeId IN :employeeIds "
//			+ " AND c.krcdtIdentificationStatusPK.processingYmd BETWEEN :startDate AND :endDate  ";

	private static final String GET_BY_CODE = "SELECT c from KrcdtIdentificationStatus c "
			+ " WHERE c.krcdtIdentificationStatusPK.companyID = :companyID "
			+ " AND c.krcdtIdentificationStatusPK.employeeId = :employeeId "
			+ " AND c.krcdtIdentificationStatusPK.processingYmd = :processingYmd ";

	private static final String REMOVE_BY_KEY = "DELETE FROM KrcdtIdentificationStatus c "
			+ " WHERE c.krcdtIdentificationStatusPK.companyID = :companyID "
			+ " AND c.krcdtIdentificationStatusPK.employeeId = :employeeId "
			+ " AND c.krcdtIdentificationStatusPK.processingYmd = :processingYmd ";

	private static final String GET_BY_EMPLOYEE_ID_SORT_DATE = "SELECT c from KrcdtIdentificationStatus c "
			+ " WHERE c.krcdtIdentificationStatusPK.companyID = :companyID "
			+ " AND c.krcdtIdentificationStatusPK.employeeId = :employeeId "
			+ " AND c.krcdtIdentificationStatusPK.processingYmd BETWEEN :startDate AND :endDate  "
			+ " ORDER BY c.krcdtIdentificationStatusPK.processingYmd ASC ";

	private static final String GET_BY_EMPLOYEE_ID_DATE = "SELECT c from KrcdtIdentificationStatus c "
			+ " WHERE c.krcdtIdentificationStatusPK.companyID = :companyID "
			+ " AND c.krcdtIdentificationStatusPK.employeeId = :employeeId "
			+ " AND c.krcdtIdentificationStatusPK.processingYmd IN :dates ";

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<Identification> findByEmployeeID(String employeeID, GeneralDate startDate, GeneralDate endDate) {
		String companyID = AppContexts.user().companyId();

		return this.queryProxy().query(GET_BY_EMPLOYEE_ID, KrcdtIdentificationStatus.class)
				.setParameter("companyID", companyID).setParameter("employeeId", employeeID)
				.setParameter("startDate", startDate).setParameter("endDate", endDate).getList(c -> c.toDomain());
	}

	@Override
	public List<Identification> findByListEmployeeID(List<String> employeeIDs, GeneralDate startDate,
			GeneralDate endDate) {
		List<Identification> data = new ArrayList<>();
		CollectionUtil.split(employeeIDs, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			try (PreparedStatement statement = this.connection().prepareStatement(
						"SELECT * from KRCDT_DAY_SELF_CHECK h"
						+ " WHERE h.PROCESSING_YMD <= ?"
						+ " and h.PROCESSING_YMD >= ?"
						+ " AND h.CID = ?"
						+ " AND h.SID IN (" + subList.stream().map(s -> "?").collect(Collectors.joining(",")) + ")")) {
				statement.setDate(1, Date.valueOf(endDate.localDate()));
				statement.setDate(2, Date.valueOf(startDate.localDate()));
				statement.setString(3, AppContexts.user().companyId());
				for (int i = 0; i < subList.size(); i++) {
					statement.setString(i + 4, subList.get(i));
				}
				data.addAll(new NtsResultSet(statement.executeQuery()).getList(rec -> {
					return new Identification(
							rec.getString("CID"),
							rec.getString("SID"),
							rec.getGeneralDate("PROCESSING_YMD"),
							rec.getGeneralDate("INDENTIFICATION_YMD"));
				}));
			}catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
		
		return data;
	}
	
	@Override
	public List<Identification> findByListEmpDate(List<String> employeeIDs,GeneralDate dateRefer) {
		List<Identification> data = new ArrayList<>();
		CollectionUtil.split(employeeIDs, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			try (PreparedStatement statement = this.connection().prepareStatement(
						"SELECT * from KRCDT_DAY_SELF_CHECK h"
						+ " WHERE h.PROCESSING_YMD = ?"
						+ " AND h.CID = ?"
						+ " AND h.SID IN (" + subList.stream().map(s -> "?").collect(Collectors.joining(",")) + ")")) {
				statement.setDate(1, Date.valueOf(dateRefer.localDate()));
				statement.setString(2, AppContexts.user().companyId());
				for (int i = 0; i < subList.size(); i++) {
					statement.setString(i + 3, subList.get(i));
				}
				data.addAll(new NtsResultSet(statement.executeQuery()).getList(rec -> {
					return new Identification(
							rec.getString("CID"),
							rec.getString("SID"),
							rec.getGeneralDate("PROCESSING_YMD"),
							rec.getGeneralDate("INDENTIFICATION_YMD"));
				}));
			}catch (Exception e) {
				throw new RuntimeException(e);
			}
		});
		
		return data;
	}

	@Override
	public Optional<Identification> findByCode(String employeeID, GeneralDate processingYmd) {
		String companyID = AppContexts.user().companyId();

		return this.queryProxy().query(GET_BY_CODE, KrcdtIdentificationStatus.class)
				.setParameter("companyID", companyID).setParameter("employeeId", employeeID)
				.setParameter("processingYmd", processingYmd).getSingle(c -> c.toDomain());
	}

	@Override
	public void insert(Identification identification) {
		this.commandProxy().insert(KrcdtIdentificationStatus.toEntity(identification));
		this.getEntityManager().flush();
	}

	@Override
	public void remove(String companyId, String employeeId, GeneralDate processingYmd) {
		this.getEntityManager().createQuery(REMOVE_BY_KEY, KrcdtIdentificationStatus.class)
				.setParameter("companyID", companyId).setParameter("employeeId", employeeId)
				.setParameter("processingYmd", processingYmd).executeUpdate();
		this.getEntityManager().flush();
	}

	@Override
	@SneakyThrows
	public void removeByEmployeeIdAndDate(String employeeId, GeneralDate processingYmd) {

		Connection con = this.getEntityManager().unwrap(Connection.class);
		String sqlQuery = "Delete From KRCDT_DAY_SELF_CHECK Where SID = " + "'" + employeeId + "'"
				+ " and PROCESSING_YMD = " + "'" + processingYmd + "'";

		con.createStatement().executeUpdate(sqlQuery);
		
		// this.getEntityManager().createQuery(REMOVE_BY_EMPLOYEEID_AND_DATE,
		// KrcdtIdentificationStatus.class)
		// .setParameter("employeeId", employeeId).setParameter("processingYmd",
		// processingYmd).executeUpdate();
		// this.getEntityManager().flush();
	}

	@Override
	public List<Identification> findByEmployeeIDSortDate(String employeeID, GeneralDate startDate,
			GeneralDate endDate) {
		// Đối ứng SPR
		String companyID = "000000000004-0018";
		String loginCompanyID = AppContexts.user().companyId();
		if(Strings.isNotBlank(loginCompanyID)){
			companyID = loginCompanyID;
		}
		return this.queryProxy().query(GET_BY_EMPLOYEE_ID_SORT_DATE, KrcdtIdentificationStatus.class)
				.setParameter("companyID", companyID).setParameter("employeeId", employeeID)
				.setParameter("startDate", startDate).setParameter("endDate", endDate).getList(c -> c.toDomain());
	}

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<Identification> findByEmployeeID(String employeeID, List<GeneralDate> dates) {
		if (CollectionUtil.isEmpty(dates)) return Collections.emptyList();
		List<KrcdtIdentificationStatus> entities = new ArrayList<>();
		CollectionUtil.split(dates, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			entities.addAll(this.queryProxy().query(GET_BY_EMPLOYEE_ID_DATE, KrcdtIdentificationStatus.class)
					.setParameter("companyID", AppContexts.user().companyId()).setParameter("employeeId", employeeID)
					.setParameter("dates", subList).getList());
		});
		return entities.stream().map(c -> c.toDomain()).collect(Collectors.toList());
	}

	@Override
	@SneakyThrows
	public void removeByEmpListDate(String employeeId, List<GeneralDate> lstProcessingYmd) {
		
		PreparedStatement statement = this.connection().prepareStatement(
				"Delete From KRCDT_DAY_SELF_CHECK" + " Where SID = ?" + " AND PROCESSING_YMD IN ("
						+ lstProcessingYmd.stream().map(s -> "?").collect(Collectors.joining(",")) + ")");
		statement.setString(1, employeeId);
		for (int i = 0; i < lstProcessingYmd.size(); i++) {
			statement.setDate(i + 2, Date.valueOf(lstProcessingYmd.get(i).localDate()));
		}
		statement.executeUpdate();
	}
}
