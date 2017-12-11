package nts.uk.ctx.bs.employee.infra.repository.employee.mngdata;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeInfo;
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

	// Lanlt end
	private static final String SELECT_BY_SID_1 = "SELECT e.employeeCode, p.personName, p.businessName , p.birthday "
			+ " FROM BsymtEmployeeDataMngInfo e " + " INNER JOIN BpsmtPerson p"
			+ " ON e.bsymtEmployeeDataMngInfoPk.pId = p.bpsmtPersonPk.pId"
			+ " WHERE e.bsymtEmployeeDataMngInfoPk.sId = :sid";

	private static final String SEL_DEPARTMENT = " SELECT d.cd , d.name FROM  BsymtDepartmentInfo d"
			+ " INNER JOIN BsymtDepartmentHist h" + " ON  d.bsymtDepartmentInfoPK.depId = h.bsymtDepartmentHistPK.depId"
			+ " AND d.bsymtDepartmentInfoPK.histId = h.bsymtDepartmentHistPK.histId "
			+ " AND d.bsymtDepartmentInfoPK.cid = h.bsymtDepartmentHistPK.cid"
			+ " WHERE  d.bsymtDepartmentInfoPK.depId =:depId" + " AND h.strD <= :date" + " AND h.endD >= :date";

	// Lanlt end
	private static final String GET_ALL_BY_CID = " SELECT e FROM BsymtEmployeeDataMngInfo e WHERE e.companyId = :cid AND e.delStatus = 1 ";

	private static final String SELECT_BY_SID = "SELECT e FROM BsymtEmployeeDataMngInfo e WHERE e.bsymtEmployeeDataMngInfoPk.sId = :sId";

	private static final String SELECT_BY_EMP_CODE = String.join(" ", SELECT_NO_PARAM,
			"WHERE e.delStatus = 0 AND e.employeeCode = :empcode AND e.companyId = :cid");

	@Override
	public void add(EmployeeDataMngInfo domain) {
		commandProxy().insert(toEntity(domain));
	}

	@Override
	public void update(EmployeeDataMngInfo domain) {
		BsymtEmployeeDataMngInfo entity = queryProxy().query(SELECT_BY_ID, BsymtEmployeeDataMngInfo.class)
				.setParameter("sId", domain.getEmployeeId()).setParameter("pId", domain.getPersonId())
				.getSingleOrNull();

		if (entity != null) {

			// entity.delDateTmp = domain.getDeleteDateTemporary();
			// entity.delStatus = domain.getDeletedStatus().value;
			// entity.removeReason = domain.getRemoveReason().v();
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
	public List<EmployeeDataMngInfo> findByEmployeeId(String sId) {
		return queryProxy().query(SELECT_BY_EMP_ID, BsymtEmployeeDataMngInfo.class).setParameter("sId", sId).getList()
				.stream().map(m -> toDomain(m)).collect(Collectors.toList());
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
				emp.setEmployeeName(entity[1].toString());
			}

			if (entity[2] != null) {
				emp.setPersonName(entity[2].toString());
			}

			if (entity[3] != null) {
				emp.setBirthday(GeneralDate.fromString(entity[3].toString(), "yyyy/MM/dd"));
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
		this.commandProxy().update(toEntity(domain));
	}

	@Override
	public Optional<EmployeeInfo> findById(String sid) {
		Optional<EmployeeInfo> emp = queryProxy().query(SELECT_BY_SID_1, Object[].class).setParameter("sid", sid)
				.getSingle(c -> toDomain(c, 0));
		return emp;

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

}
