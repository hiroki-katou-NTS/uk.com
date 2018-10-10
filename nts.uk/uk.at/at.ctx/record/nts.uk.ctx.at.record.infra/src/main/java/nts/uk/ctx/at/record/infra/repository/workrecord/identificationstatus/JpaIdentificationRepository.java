package nts.uk.ctx.at.record.infra.repository.workrecord.identificationstatus;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.Identification;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.repository.IdentificationRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.identificationstatus.KrcdtIdentificationStatus;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaIdentificationRepository extends JpaRepository implements IdentificationRepository {

	private static final String GET_BY_EMPLOYEE_ID = "SELECT c from KrcdtIdentificationStatus c "
			+ " WHERE c.krcdtIdentificationStatusPK.employeeId = :employeeId "
			+ " AND c.krcdtIdentificationStatusPK.processingYmd BETWEEN :startDate AND :endDate  ";
	
	private static final String GET_BY_LIST_EMPLOYEE_ID = "SELECT c from KrcdtIdentificationStatus c "
			+ " WHERE c.krcdtIdentificationStatusPK.employeeId IN :employeeIds "
			+ " AND c.krcdtIdentificationStatusPK.processingYmd BETWEEN :startDate AND :endDate  ";

	private static final String GET_BY_CODE = "SELECT c from KrcdtIdentificationStatus c "
			+ " WHERE c.krcdtIdentificationStatusPK.employeeId = :employeeId "
			+ " AND c.krcdtIdentificationStatusPK.processingYmd = :processingYmd ";

	private static final String REMOVE_BY_KEY = "DELETE FROM KrcdtIdentificationStatus c "
			+ " WHERE c.krcdtIdentificationStatusPK.employeeId = :employeeId "
			+ " AND c.krcdtIdentificationStatusPK.processingYmd = :processingYmd "
			+ " AND c.krcdtIdentificationStatusPK.companyID = :companyID ";
	
	private static final String GET_BY_EMPLOYEE_ID_SORT_DATE = "SELECT c from KrcdtIdentificationStatus c "
			+ " WHERE c.krcdtIdentificationStatusPK.employeeId = :employeeId "
			+ " AND c.krcdtIdentificationStatusPK.companyID = :companyID "
			+ " AND c.krcdtIdentificationStatusPK.processingYmd BETWEEN :startDate AND :endDate  "
			+ " ORDER BY c.krcdtIdentificationStatusPK.processingYmd ASC ";

	private static final String REMOVE_BY_EMPLOYEEID_AND_DATE = "DELETE FROM KrcdtIdentificationStatus c "
			+ " WHERE c.krcdtIdentificationStatusPK.employeeId = :employeeId "
			+ " AND c.krcdtIdentificationStatusPK.processingYmd = :processingYmd ";
   
	private static final String GET_BY_EMPLOYEE_ID_DATE = "SELECT c from KrcdtIdentificationStatus c "
			+ " WHERE c.krcdtIdentificationStatusPK.employeeId = :employeeId "
			+ " AND c.krcdtIdentificationStatusPK.processingYmd IN :dates ";

	@Override
	public List<Identification> findByEmployeeID(String employeeID, GeneralDate startDate, GeneralDate endDate) {
		return this.queryProxy().query(GET_BY_EMPLOYEE_ID, KrcdtIdentificationStatus.class)
				.setParameter("employeeId", employeeID).setParameter("startDate", startDate)
				.setParameter("endDate", endDate).getList(c -> c.toDomain());
	}
	
	@Override
	public List<Identification> findByListEmployeeID(List<String> employeeIDs,GeneralDate startDate,GeneralDate endDate) {
		List<Identification> resultList = new ArrayList<>();
		CollectionUtil.split(employeeIDs, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			resultList.addAll(this.queryProxy().query(GET_BY_LIST_EMPLOYEE_ID, KrcdtIdentificationStatus.class)
				.setParameter("employeeIds", subList)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.getList(c -> c.toDomain()));
		});
		return resultList;
	}

	@Override
	public Optional<Identification> findByCode(String employeeID, GeneralDate processingYmd) {
		return this.queryProxy().query(GET_BY_CODE, KrcdtIdentificationStatus.class)
				.setParameter("employeeId", employeeID).setParameter("processingYmd", processingYmd)
				.getSingle(c -> c.toDomain());
	}

	@Override
	public void insert(Identification identification) {
		this.commandProxy().insert(KrcdtIdentificationStatus.toEntity(identification));
		this.getEntityManager().flush();
	}

	@Override
	public void remove(String companyId, String employeeId, GeneralDate processingYmd) {
		this.getEntityManager().createQuery(REMOVE_BY_KEY, KrcdtIdentificationStatus.class)
				.setParameter("employeeId", employeeId).setParameter("processingYmd", processingYmd)
				.setParameter("companyID", companyId).executeUpdate();
		this.getEntityManager().flush();
	}

	@Override
	public void removeByEmployeeIdAndDate(String employeeId, GeneralDate processingYmd) {
		
		Connection con = this.getEntityManager().unwrap(Connection.class);
		String sqlQuery = "Delete From KRCDT_CONFIRMATION_DAY Where SID = " + "'" + employeeId + "'" + " and PROCESSING_YMD = " + "'" + processingYmd + "'" ;
		try {
			con.createStatement().executeUpdate(sqlQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
//		this.getEntityManager().createQuery(REMOVE_BY_EMPLOYEEID_AND_DATE, KrcdtIdentificationStatus.class)
//		.setParameter("employeeId", employeeId).setParameter("processingYmd", processingYmd).executeUpdate();
//		this.getEntityManager().flush();
	}

	@Override
	public List<Identification> findByEmployeeIDSortDate(String employeeID, GeneralDate startDate,
			GeneralDate endDate) {
		return this.queryProxy().query(GET_BY_EMPLOYEE_ID_SORT_DATE, KrcdtIdentificationStatus.class)
				.setParameter("employeeId", employeeID).setParameter("companyID", AppContexts.user().companyId())
				.setParameter("startDate", startDate).setParameter("endDate", endDate).getList(c -> c.toDomain());
	}

	@Override
	public List<Identification> findByEmployeeID(String employeeID, List<GeneralDate> dates) {
		List<Identification> resultList = new ArrayList<>();
		CollectionUtil.split(dates, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			resultList.addAll(this.queryProxy().query(GET_BY_EMPLOYEE_ID_DATE, KrcdtIdentificationStatus.class)
				.setParameter("employeeId", employeeID)
				.setParameter("dates", subList)
				.getList(c -> c.toDomain()));
		});
		return resultList;
	}

}
