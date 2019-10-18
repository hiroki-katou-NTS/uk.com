/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.repository.employee.mngdata;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import org.apache.commons.lang3.StringUtils;

import lombok.SneakyThrows;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDeletionAttr;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeSimpleInfo;
import nts.uk.ctx.bs.employee.dom.employee.service.dto.EmployeeIdPersonalIdDto;
import nts.uk.ctx.bs.employee.infra.entity.employee.mngdata.BsymtEmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.infra.entity.employee.mngdata.BsymtEmployeeDataMngInfoPk;

@Stateless
public class EmployeeDataMngInfoRepositoryImp extends JpaRepository implements EmployeeDataMngInfoRepository {

	private static final String SELECT_NO_PARAM = String.join(" ", "SELECT e FROM BsymtEmployeeDataMngInfo e");

	private static final String SELECT_BY_ID = String.join(" ", SELECT_NO_PARAM,
			"WHERE e.bsymtEmployeeDataMngInfoPk.sId = :sId", "AND e.bsymtEmployeeDataMngInfoPk.pId = :pId");

	private static final String SELECT_BY_EMP_ID = String.join(" ", SELECT_NO_PARAM,
			"WHERE e.bsymtEmployeeDataMngInfoPk.sId = :sId");

	private static final String SELECT_BY_PERSON_ID = String.join(" ", SELECT_NO_PARAM,
			"WHERE e.bsymtEmployeeDataMngInfoPk.pId = :pId");

	private static final String SELECT_EMPLOYEE_NOTDELETE_IN_COMPANY = String.join(" ", SELECT_NO_PARAM,
			"WHERE e.companyId = :cId AND e.employeeCode= :sCd AND e.delStatus=0");
	
	private static final String SELECT_BY_COM_ID = String.join(" ", SELECT_NO_PARAM, "WHERE e.companyId = :companyId");

	private static final String SELECT_BY_COM_ID_AND_BASEDATE = "SELECT e.companyId, e.employeeCode, e.bsymtEmployeeDataMngInfoPk.sId, e.bsymtEmployeeDataMngInfoPk.pId  , ps.businessName , ps.personName"
			+ " FROM BsymtEmployeeDataMngInfo e "
			+ " INNER JOIN BsymtAffCompanyHist h ON e.bsymtEmployeeDataMngInfoPk.pId = h.bsymtAffCompanyHistPk.pId "
			+ " INNER JOIN BpsmtPerson ps ON  e.bsymtEmployeeDataMngInfoPk.pId = ps.bpsmtPersonPk.pId "
			+ " WHERE e.companyId = :companyId AND h.startDate <= :baseDate AND h.endDate >= :baseDate ";

	private static final String GET_LAST_EMPLOYEE = "SELECT c.employeeCode FROM BsymtEmployeeDataMngInfo c "
			+ " WHERE c.companyId = :companyId AND c.delStatus = 0 AND c.employeeCode LIKE CONCAT(:emlCode, '%')"
			+ " ORDER BY c.employeeCode DESC";

	// Lanlt end
	private static final String SELECT_BY_SID_1 = "SELECT e.employeeCode, p.personName, p.businessName , p.birthday, p.gender, p.bpsmtPersonPk.pId "
			+ " FROM BsymtEmployeeDataMngInfo e " + " INNER JOIN BpsmtPerson p"
			+ " ON e.bsymtEmployeeDataMngInfoPk.pId = p.bpsmtPersonPk.pId"
			+ " WHERE e.bsymtEmployeeDataMngInfoPk.sId = :sid";

	private static final String SEL_DEPARTMENT = " SELECT d.cd , d.name FROM  BsymtDepartmentInfo d"
			+ " INNER JOIN BsymtDepartmentHist h" + " ON  d.bsymtDepartmentInfoPK.depId = h.bsymtDepartmentHistPK.depId"
			+ " AND d.bsymtDepartmentInfoPK.histId = h.bsymtDepartmentHistPK.histId "
			+ " AND d.bsymtDepartmentInfoPK.cid = h.bsymtDepartmentHistPK.cid"
			+ " WHERE  d.bsymtDepartmentInfoPK.depId =:depId" + " AND h.strD <= :date" + " AND h.endD >= :date";

	private static final String SELECT_INFO_BY_IDS = String.join(" ",
			"SELECT e.bsymtEmployeeDataMngInfoPk.sId, e.employeeCode, p.businessName",
			"FROM BsymtEmployeeDataMngInfo e INNER JOIN BpsmtPerson p",
			"ON e.bsymtEmployeeDataMngInfoPk.pId = p.bpsmtPersonPk.pId",
			"WHERE e.bsymtEmployeeDataMngInfoPk.sId IN :lstId AND e.delStatus = 0",
			"ORDER BY e.bsymtEmployeeDataMngInfoPk.sId ASC");

	// Lanlt end
	private static final String GET_ALL_BY_CID = " SELECT e FROM BsymtEmployeeDataMngInfo e WHERE e.companyId = :cid AND e.delStatus = 1 ORDER BY  e.employeeCode ASC";

	private static final String SELECT_BY_EMP_CODE = String.join(" ", SELECT_NO_PARAM,
			"WHERE e.employeeCode = :empcode AND e.companyId = :cid AND e.delStatus = 0");

	// duongtv start code
	/** The select by list emp code. */
	public static final String SELECT_BY_LIST_EMP_CODE = SELECT_NO_PARAM + " WHERE e.companyId = :companyId"
			+ " AND e.employeeCode IN :listEmployeeCode ";

	/** The select by list emp id. */
	public static final String SELECT_BY_LIST_EMP_ID = SELECT_NO_PARAM + " WHERE e.companyId = :companyId"
			+ " AND e.bsymtEmployeeDataMngInfoPk.sId IN :employeeIds ORDER BY e.employeeCode ASC";

	// duongtv end code

	/** The select by list empId. */
	public static final String SELECT_BY_LIST_EMPID = SELECT_NO_PARAM
			+ " WHERE e.bsymtEmployeeDataMngInfoPk.sId IN :listSid ";

	/** The select by cid and pid. */

	public static final String SELECT_BY_CID_PID = SELECT_NO_PARAM
			+ " WHERE e.companyId = :cid AND e.bsymtEmployeeDataMngInfoPk.pId = :pid ";

	/** The select by cid and sid. */
	public static final String SELECT_BY_CID_SID = SELECT_NO_PARAM
			+ " WHERE e.companyId = :cid AND e.bsymtEmployeeDataMngInfoPk.sId = :sid ";

	/** The select by cid and sid. */
	public static final String SELECT_BY_SIDS = " SELECT e FROM BsymtEmployeeDataMngInfo e WHERE e.bsymtEmployeeDataMngInfoPk.sId IN :listSid";

	private static final String GET_ALL = " SELECT e FROM BsymtEmployeeDataMngInfo e WHERE e.companyId = :cid ORDER BY  e.employeeCode ASC";


	private static final String COUNT_EMPL_BY_LSTCID_AND_BASE_DATE = String.join(" ",
			"SELECT COUNT(dmi.companyId) FROM BsymtEmployeeDataMngInfo dmi", 
			"INNER JOIN BsymtAffCompanyHist ach",
			"ON dmi.bsymtEmployeeDataMngInfoPk.sId = ach.bsymtAffCompanyHistPk.sId",
			"WHERE dmi.companyId IN :lstCompID AND dmi.delStatus = 0 AND ach.destinationData = 0",
			"AND (ach.endDate >= :baseDate)");

	private static final String FIND_BY_CID_PID_AND_DELSTATUS = "SELECT e FROM BsymtEmployeeDataMngInfo e WHERE e.companyId = :cid AND "
			+ "e.bsymtEmployeeDataMngInfoPk.sId = :sid AND e.delStatus = :delStatus ";
	
	private static final String SELECT_EMP_NOT_DEL = String.join(" ", SELECT_NO_PARAM,
			" WHERE e.bsymtEmployeeDataMngInfoPk.sId IN :sId AND e.delStatus = 0 ");
	
	
	private static final String SELECT_EMPL_NOT_DELETE_BY_CID = String.join(" ", SELECT_NO_PARAM, "WHERE e.companyId = :companyId AND e.delStatus = 0");
	
	@Override
	public void add(EmployeeDataMngInfo domain) {
		commandProxy().insert(toEntity(domain));
		this.getEntityManager().flush();
	}

	@Override
	public void update(EmployeeDataMngInfo domain) {
		BsymtEmployeeDataMngInfo entity = queryProxy().query(SELECT_BY_ID, BsymtEmployeeDataMngInfo.class)
				.setParameter("sId", domain.getEmployeeId()).setParameter("pId", domain.getPersonId())
				.getSingleOrNull();

		if (entity != null) {
			entity.employeeCode = domain.getEmployeeCode().v();
			entity.extCode = domain.getExternalCode().v();
			commandProxy().update(entity);
		}
	}

	@Override
	public void remove(EmployeeDataMngInfo domain) {
		remove(domain.getEmployeeId(), domain.getPersonId());
	}

	@Override
	public void remove(String sId, String pId) {
		this.commandProxy().remove(BsymtEmployeeDataMngInfo.class, new BsymtEmployeeDataMngInfoPk(sId, pId));
	}

	@Override
	public EmployeeDataMngInfo findById(String sId, String pId) {
		// TODO Auto-generated method stub
		return queryProxy().query(SELECT_BY_ID, BsymtEmployeeDataMngInfo.class).setParameter("sId", sId)
				.setParameter("pid", pId).getSingle().map(m -> toDomain(m)).orElse(null);
	}

	@Override
	@SneakyThrows
	public List<EmployeeDataMngInfo> findByEmployeeId(String sId) {
		String sql = "select CID, SID, PID, SCD, DEL_STATUS_ATR, DEL_DATE, REMV_REASON, EXT_CD"
				+ " from BSYMT_EMP_DTA_MNG_INFO"
				+ " where SID = ?";
		try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
			stmt.setString(1, sId);
			
			return new NtsResultSet(stmt.executeQuery()).getList(r -> {
				BsymtEmployeeDataMngInfo e = new BsymtEmployeeDataMngInfo();
				e.bsymtEmployeeDataMngInfoPk = new BsymtEmployeeDataMngInfoPk();
				e.bsymtEmployeeDataMngInfoPk.sId = sId;
				e.bsymtEmployeeDataMngInfoPk.pId = r.getString("PID");
				e.companyId = r.getString("CID");
				e.employeeCode = r.getString("SCD");
				e.delStatus = r.getInt("DEL_STATUS_ATR");
				e.delDateTmp = r.getGeneralDateTime("DEL_DATE");
				e.removeReason = r.getString("REMV_REASON");
				e.extCode = r.getString("EXT_CD");
				return toDomain(e);
			});
		}
	}

	@Override
	public Optional<EmployeeDataMngInfo> findByEmpId(String sId) {
		List<EmployeeDataMngInfo> lst = findByEmployeeId(sId);
		if (!lst.isEmpty()) {
			return Optional.of(lst.get(0));
		}

		return Optional.empty();
	}

	@Override
	public List<EmployeeDataMngInfo> findByPersonId(String pId) {
		return queryProxy().query(SELECT_BY_PERSON_ID, BsymtEmployeeDataMngInfo.class).setParameter("pId", pId)
				.getList().stream().map(m -> toDomain(m)).collect(Collectors.toList());
	}

	@Override
	public List<EmployeeDataMngInfo> findByCompanyId(String cId) {
		return queryProxy().query(SELECT_BY_COM_ID, BsymtEmployeeDataMngInfo.class).setParameter("companyId", cId)
				.getList().stream().map(m -> toDomain(m)).collect(Collectors.toList());
	}

	private EmployeeDataMngInfo toDomain(BsymtEmployeeDataMngInfo entity) {
		return EmployeeDataMngInfo.createFromJavaType(entity.companyId, entity.bsymtEmployeeDataMngInfoPk.pId,
				entity.bsymtEmployeeDataMngInfoPk.sId, entity.employeeCode, entity.delStatus, entity.delDateTmp,
				entity.removeReason, entity.extCode);
	}

	// Lanlt start - header
	private EmployeeInfo toDomain(Object[] entity, int component) {
		EmployeeInfo emp = new EmployeeInfo();
		if (component == 0) {
			if (entity[0] != null) {
				emp.setEmployeeCode(entity[0].toString());
			}

			if (entity[1] != null) {
				emp.setPersonName(entity[1].toString());
			}

			if (entity[2] != null) {
				emp.setEmployeeName(entity[2].toString());
			}

			if (entity[3] != null) {
				emp.setBirthday(GeneralDate.fromString(entity[3].toString(), "yyyy/MM/dd"));
			}

			if (entity[4] != null) {
				if (Integer.valueOf(entity[4].toString()) == 1) {
					emp.setGender("男性");

				} else if (Integer.valueOf(entity[4].toString()) == 2) {
					emp.setGender("女性");
				}
			}

			if (entity[5] != null) {
				emp.setPId(entity[5].toString());
			}

		} else if (component == 1) {
			if (entity[0] != null && entity[1] != null)
				emp.setDepartmentName(entity[0].toString() + " " + entity[1].toString());
		}

		return emp;
	}

	// Lanlt end

	private BsymtEmployeeDataMngInfo toEntity(EmployeeDataMngInfo domain) {
		BsymtEmployeeDataMngInfoPk primaryKey = new BsymtEmployeeDataMngInfoPk(domain.getEmployeeId(),
				domain.getPersonId());

		return new BsymtEmployeeDataMngInfo(primaryKey, domain.getCompanyId(), domain.getEmployeeCode().v(),
				domain.getDeletedStatus().value, domain.getDeleteDateTemporary(),
				domain.getRemoveReason() != null ? domain.getRemoveReason().v() : null, domain.getExternalCode().v());
	}

	// sonnlb code start

	@Override
	public List<EmployeeDataMngInfo> getEmployeeNotDeleteInCompany(String cId, String sCd) {

		return queryProxy().query(SELECT_EMPLOYEE_NOTDELETE_IN_COMPANY, BsymtEmployeeDataMngInfo.class)
				.setParameter("cId", cId).setParameter("sCd", sCd).getList().stream().map(x -> toDomain(x))
				.collect(Collectors.toList());
	}

	// sonnlb code end

	@Override
	public void updateRemoveReason(EmployeeDataMngInfo domain) {

		this.updateAfterRemove(domain);
	}

	@Override
	public Optional<EmployeeInfo> findById(String sid) {
		Optional<EmployeeInfo> emp = queryProxy().query(SELECT_BY_SID_1, Object[].class).setParameter("sid", sid)
				.getSingle(c -> toDomain(c, 0));
		return emp;
	}

	@Override
	public List<EmployeeSimpleInfo> findByIds(List<String> lstId) {
		List<EmployeeSimpleInfo> emps = new ArrayList<EmployeeSimpleInfo>();

		CollectionUtil.split(lstId, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, ids -> {
			List<EmployeeSimpleInfo> _emps = queryProxy().query(SELECT_INFO_BY_IDS, Object[].class)
					.setParameter("lstId", ids)
					.getList(m -> new EmployeeSimpleInfo(m[0].toString(), m[1].toString(), m[2].toString()));
			emps.addAll(_emps);
		});

		emps.sort(Comparator.comparing(EmployeeSimpleInfo::getEmployeeId));
		return emps;
	}

	@Override
	public Optional<EmployeeInfo> getDepartment(String departmentId, GeneralDate date) {
		// TODO Auto-generated method stub SEL_DEPARTMENT
		Optional<EmployeeInfo> emp = queryProxy().query(SEL_DEPARTMENT, Object[].class)
				.setParameter("depId", departmentId).setParameter("date", date).getSingle(c -> toDomain(c, 1));
		return emp;
	}

	public List<EmployeeDataMngInfo> getListEmpToDelete(String cid) {

		List<BsymtEmployeeDataMngInfo> listEntity = this.queryProxy()
				.query(GET_ALL_BY_CID, BsymtEmployeeDataMngInfo.class).setParameter("cid", cid).getList();

		return toListEmployeeDataMngInfo(listEntity);
	}

	private List<EmployeeDataMngInfo> toListEmployeeDataMngInfo(List<BsymtEmployeeDataMngInfo> listEntity) {
		List<EmployeeDataMngInfo> lstEmployeeDataMngInfo = new ArrayList<>();
		if (!listEntity.isEmpty()) {
			listEntity.stream().forEach(c -> {
				EmployeeDataMngInfo employeeDataMngInfo = toDomain(c);

				lstEmployeeDataMngInfo.add(employeeDataMngInfo);
			});
		}
		return lstEmployeeDataMngInfo;
	}

	public Optional<EmployeeDataMngInfo> findByEmployeCD(String empcode, String cid) {
		return queryProxy().query(SELECT_BY_EMP_CODE, BsymtEmployeeDataMngInfo.class).setParameter("empcode", empcode)
				.setParameter("cid", cid).getSingle().map(m -> toDomain(m));

	}

	// duong tv start code
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository
	 * #findByListEmployeeId(java.lang.String, java.util.List)
	 */
	@Override
	public List<EmployeeDataMngInfo> findByListEmployeeId(String companyId, List<String> employeeIds) {
		// fix bug empty list
		if (CollectionUtil.isEmpty(employeeIds)) {
			return new ArrayList<>();
		}

		// Split query.
		List<BsymtEmployeeDataMngInfo> resultList = new ArrayList<>();
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, (subList) -> {
			
			String sql = "select * from BSYMT_EMP_DTA_MNG_INFO"
					+ " where CID = ?"
					+ " and SID in (" + NtsStatement.In.createParamsString(subList) + ")";
			try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
				
				stmt.setString(1, companyId);
				for (int i = 0; i < subList.size(); i++) {
					stmt.setString(2 + i, subList.get(i));
				}
				
				List<BsymtEmployeeDataMngInfo> subResults = new NtsResultSet(stmt.executeQuery())
						.getList(rec -> {
							BsymtEmployeeDataMngInfo entity = new BsymtEmployeeDataMngInfo();
							entity.bsymtEmployeeDataMngInfoPk = new BsymtEmployeeDataMngInfoPk();
							entity.bsymtEmployeeDataMngInfoPk.sId = rec.getString("SID");
							entity.bsymtEmployeeDataMngInfoPk.pId = rec.getString("PID");
							entity.companyId = rec.getString("CID");
							entity.employeeCode = rec.getString("SCD");
							entity.delStatus = rec.getInt("DEL_STATUS_ATR");
							entity.delDateTmp = rec.getGeneralDateTime("DEL_DATE");
							entity.removeReason = rec.getString("REMV_REASON");
							entity.extCode = rec.getString("EXT_CD");
							return entity;
						});
				
				resultList.addAll(subResults);
				
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});

		return resultList.stream().map(entity -> this.toDomain(entity)).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository
	 * #findByListEmployeeCode(java.lang.String, java.util.List)
	 */
	@Override
	public List<EmployeeDataMngInfo> findByListEmployeeCode(String companyId, List<String> employeeCodes) {
		// fix bug empty list
		if (CollectionUtil.isEmpty(employeeCodes)) {
			return new ArrayList<>();
		}

		// Split query.
		List<BsymtEmployeeDataMngInfo> resultList = new ArrayList<>();
		CollectionUtil.split(employeeCodes, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, (subList) -> {
			resultList.addAll(this.queryProxy().query(SELECT_BY_LIST_EMP_CODE, BsymtEmployeeDataMngInfo.class)
					.setParameter("companyId", companyId).setParameter("listEmployeeCode", subList).getList());
		});
		return resultList.stream().map(entity -> this.toDomain(entity)).collect(Collectors.toList());
	}

	// duong tv end code

	@Override
	public List<EmployeeDataMngInfo> findByListEmployeeId(List<String> listSid) {

		if (CollectionUtil.isEmpty(listSid)) {
			return new ArrayList<>();
		}

		// Split query.
		List<BsymtEmployeeDataMngInfo> resultList = new ArrayList<>();
		CollectionUtil.split(listSid, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, (subList) -> {
			resultList.addAll(this.queryProxy().query(SELECT_BY_LIST_EMPID, BsymtEmployeeDataMngInfo.class)
					.setParameter("listSid", subList).getList());
		});

		return resultList.stream().map(entity -> this.toDomain(entity)).collect(Collectors.toList());
	}

	@Override
	public Optional<EmployeeDataMngInfo> findByCidPid(String cid, String pid) {
		BsymtEmployeeDataMngInfo entity = this.queryProxy().query(SELECT_BY_CID_PID, BsymtEmployeeDataMngInfo.class)
				.setParameter("cid", cid).setParameter("pid", pid).getSingleOrNull();

		EmployeeDataMngInfo empDataMng = new EmployeeDataMngInfo();
		if (entity != null) {
			empDataMng = toDomain(entity);
			return Optional.of(empDataMng);

		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<EmployeeDataMngInfo> getEmployeeByCidScd(String cId, String sCd) {
		// query to Req 125
		BsymtEmployeeDataMngInfo entity = queryProxy().query(SELECT_EMPLOYEE_NOTDELETE_IN_COMPANY, BsymtEmployeeDataMngInfo.class)
				.setParameter("cId", cId).setParameter("sCd", sCd).getSingleOrNull();

		EmployeeDataMngInfo empDataMng = new EmployeeDataMngInfo();
		if (entity != null) {
			empDataMng = toDomain(entity);
			return Optional.of(empDataMng);

		} else {
			return Optional.empty();
		}
	}
	
	@Override
	public Optional<EmployeeDataMngInfo> getEmployeeNotDel(String cId, String sCd) {
		// query to Req 18
		BsymtEmployeeDataMngInfo entity = queryProxy().query(SELECT_EMPLOYEE_NOTDELETE_IN_COMPANY, BsymtEmployeeDataMngInfo.class)
				.setParameter("cId", cId).setParameter("sCd", sCd).getSingleOrNull();

		EmployeeDataMngInfo empDataMng = new EmployeeDataMngInfo();
		if (entity != null) {
			empDataMng = toDomain(entity);
			return Optional.of(empDataMng);

		} else {
			return Optional.empty();
		}
	}

	// sonnlb code start
	@Override
	public Optional<String> findLastEml(String companyId, String startLetters, int length) {
		List<String> lastEmployeeCode = this.queryProxy().query(GET_LAST_EMPLOYEE, String.class)
				.setParameter("companyId", companyId)
				.setParameter("emlCode", StringUtils.isEmpty(startLetters) ? "" : startLetters).getList().stream()
				.filter(emCode -> emCode.length() == length).collect(Collectors.toList());
		if (!lastEmployeeCode.isEmpty()) {
			return Optional.of(lastEmployeeCode.get(0));
		}
		return Optional.empty();
	}

	// sonnlb code end

	@Override
	public void updateAfterRemove(EmployeeDataMngInfo domain) {
		BsymtEmployeeDataMngInfo entity = queryProxy().query(SELECT_BY_ID, BsymtEmployeeDataMngInfo.class)
				.setParameter("sId", domain.getEmployeeId()).setParameter("pId", domain.getPersonId())
				.getSingleOrNull();

		if (entity != null) {
			if (domain.getEmployeeCode() != null && !domain.getEmployeeCode().v().equals("")) {
				entity.employeeCode = domain.getEmployeeCode().v();
			}
			if (domain.getExternalCode() != null && !domain.getExternalCode().v().equals("")) {
				entity.extCode = domain.getExternalCode().v();
			}

			entity.removeReason = domain.getRemoveReason() != null ? domain.getRemoveReason().v() : null;
			entity.delStatus = domain.getDeletedStatus().value;
			entity.delDateTmp = domain.getDeleteDateTemporary();

			commandProxy().update(entity);
		}

	}

	@Override
	public List<EmployeeDataMngInfo> getAllByCid(String cid) {
		List<BsymtEmployeeDataMngInfo> listEntity = this.queryProxy().query(GET_ALL, BsymtEmployeeDataMngInfo.class)
				.setParameter("cid", cid).getList();

		return toListEmployeeDataMngInfo(listEntity);
	}

	@Override
	public List<Object[]> findByCompanyIdAndBaseDate(String cid, GeneralDate baseDate) {

		return queryProxy().query(SELECT_BY_COM_ID_AND_BASEDATE, Object[].class).setParameter("companyId", cid)
				.setParameter("baseDate", baseDate).getList();
	}
	@Override
	public Optional<EmployeeDataMngInfo> findByCidEmployeeCodeAndDeletedStatus(String cid, String pid,
			EmployeeDeletionAttr deletedStatus) {
		List<EmployeeDataMngInfo> listEmployeeDataMngInfo = this.queryProxy().query(FIND_BY_CID_PID_AND_DELSTATUS,BsymtEmployeeDataMngInfo.class)
																.setParameter("cid", cid)
																.setParameter("sid", pid)
																.setParameter("delStatus", deletedStatus.value).getList().stream().map(e -> toDomain(e)).collect(Collectors.toList());
		if(listEmployeeDataMngInfo!=null && listEmployeeDataMngInfo.size()!=0) {
			return Optional.of(listEmployeeDataMngInfo.get(0));
		}
		return Optional.empty();
	}

	

	@Override
	public int countEmplsByBaseDate(List<String> lstCompID, GeneralDate baseDate) {
		if (lstCompID == null) return 0;
		LongAdder counter = new LongAdder();
		CollectionUtil.split(lstCompID, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			queryProxy().query(COUNT_EMPL_BY_LSTCID_AND_BASE_DATE, Long.class)
			.setParameter("baseDate", baseDate)
			.setParameter("lstCompID", subList)
			.getSingle().ifPresent(value -> {
				counter.add(value);
			});
		});
		return counter.intValue();
	}

	/**
	 * request list 515
	 * @return
	 * @author yennth
	 */
	@Override
	public List<EmployeeDataMngInfo> findBySidNotDel(List<String> sId) {
		List<EmployeeDataMngInfo> resultList = new ArrayList<>();
		CollectionUtil.split(sId, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			
			String sql = "select CID, SID, PID, SCD, DEL_STATUS_ATR, DEL_DATE, REMV_REASON, EXT_CD"
					+ " from BSYMT_EMP_DTA_MNG_INFO"
					+ " where SID in (" + NtsStatement.In.createParamsString(subList) + ")"
					+ " and DEL_STATUS_ATR = 0";
			
			try (PreparedStatement stmt = this.connection().prepareStatement(sql)) {
				for (int i = 0; i < subList.size(); i++) {
					stmt.setString(i + 1, subList.get(i));
				}
				
				List<EmployeeDataMngInfo> subResults = new NtsResultSet(stmt.executeQuery()).getList(r -> {
					BsymtEmployeeDataMngInfo e = new BsymtEmployeeDataMngInfo();
					e.bsymtEmployeeDataMngInfoPk = new BsymtEmployeeDataMngInfoPk();
					e.bsymtEmployeeDataMngInfoPk.sId = r.getString("SID");
					e.bsymtEmployeeDataMngInfoPk.pId = r.getString("PID");
					e.companyId = r.getString("CID");
					e.employeeCode = r.getString("SCD");
					e.delStatus = r.getInt("DEL_STATUS_ATR");
					e.delDateTmp = r.getGeneralDateTime("DEL_DATE");
					e.removeReason = r.getString("REMV_REASON");
					e.extCode = r.getString("EXT_CD");
					return toDomain(e);
				});
				
				resultList.addAll(subResults);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});
		return resultList;
	}

	/**
	 * getAllEmpNotDeleteByCid
	 * @param companyId
	 * 
	 * @author lanlt
	 */
	@Override
	public List<EmployeeDataMngInfo> getAllEmpNotDeleteByCid(String companyId) {
		return this.queryProxy().query(SELECT_EMPL_NOT_DELETE_BY_CID, BsymtEmployeeDataMngInfo.class).setParameter("companyId", companyId)
				.getList().stream().map(m -> toDomain(m)).collect(Collectors.toList());
	}
	// laitv code end
	
	private static final String FIND_EMPLOYEES_MATCHING_COMPANYID = "SELECT e FROM BsymtEmployeeDataMngInfo e "
			+ "WHERE e.bsymtEmployeeDataMngInfoPk.pId IN :pId "
			+ "AND e.companyId = :companyId ";
	
	@Override
	public List<EmployeeDataMngInfo> findEmployeesMatchingName(List<String> pid, String companyId) {
		List<EmployeeDataMngInfo> emps = new ArrayList<>();
		CollectionUtil.split(pid, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, ids -> {
			List<EmployeeDataMngInfo> _emps = queryProxy().query(FIND_EMPLOYEES_MATCHING_COMPANYID, BsymtEmployeeDataMngInfo.class)
					.setParameter("pId", ids)
					.setParameter("companyId", companyId)
					.getList(m -> toDomain(m));
			emps.addAll(_emps);
		});
		return emps;
	}
	
	private static final String FIND_EMPLOYEES_MATCHING_COMPANYID_FIX = "SELECT e FROM BsymtEmployeeDataMngInfo e, BpsmtPerson c "
			+ "WHERE (c.businessName LIKE CONCAT(:keyWord, '%') " 
			+ "OR c.businessNameKana LIKE CONCAT(:keyWord, '%')) "
			+ "AND e.bsymtEmployeeDataMngInfoPk.pId = c.bpsmtPersonPk.pId "
			+ "AND e.companyId = :companyId ";
	
	@Override
	public List<EmployeeDataMngInfo> findEmployeesMatchingName(String keyWord, String companyId) {
		return queryProxy().query(FIND_EMPLOYEES_MATCHING_COMPANYID_FIX, BsymtEmployeeDataMngInfo.class)
				.setParameter("keyWord", keyWord)
				.setParameter("companyId", companyId)
				.getList(m -> toDomain(m));
	}
	
	private static final String FIND_EMPLOYEE_PARTIAL_MATCH = "SELECT e FROM BsymtEmployeeDataMngInfo e "
			+ "WHERE e.companyId = :cId "
			+ "AND e.employeeCode LIKE CONCAT('%', :sCd, '%') "
			+ "AND e.delStatus = 0 ";

	@Override
	public List<EmployeeIdPersonalIdDto> findEmployeePartialMatchCode(String cId, String sCd) {
		return queryProxy().query(FIND_EMPLOYEE_PARTIAL_MATCH, BsymtEmployeeDataMngInfo.class)
				.setParameter("cId", cId)
				.setParameter("sCd", sCd)
				.getList(m -> new EmployeeIdPersonalIdDto(m.bsymtEmployeeDataMngInfoPk.pId, m.bsymtEmployeeDataMngInfoPk.sId, m.employeeCode));
	}
}
