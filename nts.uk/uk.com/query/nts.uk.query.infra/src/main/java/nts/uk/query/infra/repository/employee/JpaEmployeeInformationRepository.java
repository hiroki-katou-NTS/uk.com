/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.infra.repository.employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.infra.entity.employee.mngdata.BsymtEmployeeDataMngInfo;
import nts.uk.ctx.bs.person.infra.entity.person.info.BpsmtPerson;
import nts.uk.query.model.employee.EmployeeInformation;
import nts.uk.query.model.employee.EmployeeInformationQuery;
import nts.uk.query.model.employee.EmployeeInformationRepository;

/**
 * The Class JpaEmployeeInformationRepository.
 */
@Stateless
public class JpaEmployeeInformationRepository extends JpaRepository implements EmployeeInformationRepository {

	private final String EMPLOYEE_QUERY = "SELECT e, p FROM BSYMT_EMP_DTA_MNG_INFO e"
			+ "LEFT JOIN BPSMT_PERSON p ON p.PID = e.PID"
			+ "WHERE e.SID IN :listSid";

	private final String WORKPLACE_QUERY = "SELECT awh.SID, wi.WKP_NAME FROM BSYMT_AFF_WORKPLACE_HIST awh"
			+ " LEFT JOIN BSYMT_AFF_WPL_HIST_ITEM awhi ON awhi.HIST_ID = awh.HIST_ID"
			+ " LEFT JOIN BSYMT_WORKPLACE_HIST wh ON wh.HIST_ID = awhi.HIST_ID"
			+ " LEFT JOIN BSYMT_WORKPLACE_INFO wi ON wi.HIST_ID = wh.HIST_ID"
			+ " WHERE awh.SID IN :listSid"
			+ " AND awh.START_DATE <= :refDate"
			+ " AND awh.END_DATE >= :refDate"
			+ " AND wh.START_DATE <= :refDate"
			+ " AND wh.END_DATE >= :refDate";

	private final String POSITION_QUERY = "SELECT ajh.SID, ji.JOB_NAME FROM BSYMT_AFF_JOB_HIST ajh"
			+ " LEFT JOIN BSYMT_AFF_JOB_HIST_ITEM ajhi ON ajhi.HIST_ID = ajhi.HIST_ID"
			+ " LEFT JOIN BSYMT_JOB_HIST jh ON jh.JOB_ID = ajhi.JOB_TITLE_ID"
			+ " LEFT JOIN BSYMT_JOB_INFO ji ON ji.HIST_ID = jh.HIST_ID"
			+ " WHERE awh.SID IN :listSid"
			+ " AND ajh.START_DATE <= :refDate"
			+ " AND ajh.END_DATE >= :refDate"
			+ " AND jh.START_DATE <= :refDate"
			+ " AND jh.END_DATE >= :refDate";

	private final String EMPLOYMENT_QUERY = "SELECT eh.SID, e.NAME FROM BSYMT_EMPLOYMENT_HIST eh "
			+ "LEFT JOIN BSYMT_EMPLOYMENT_HIS_ITEM ehi ON ehi.HIST_ID = eh.HIST_ID "
			+ "LEFT JOIN BSYMT_EMPLOYMENT e ON e.CODE = ehi.EMP_CD "
			+ "WHERE eh.SID IN :listSid "
			+ "AND eh.START_DATE <= :refDate "
			+ "AND eh.END_DATE >= :refDate";

	private final String CLASSIFICATION_QUERY = "SELECT ach.SID, c.CLSNAME FROM BSYMT_AFF_CLASS_HISTORY ach "
			+ "LEFT JOIN BSYMT_AFF_CLASS_HIS_ITEM achi ON achi.HIST_ID = ach.HIST_ID "
			+ "LEFT JOIN BSYMT_CLASSIFICATION c ON c.CLSCD = achi.CLASSIFICATION_CODE "
			+ "WHERE ach.SID IN :listSid "
			+ "AND ach.START_DATE <= :refDate "
			+ "AND ach.END_DATE >= :refDate";

	private final String EMPCLS_QUERY = "SELECT wc,SID, wci.LABOR_SYS FROM KSHMT_WORKING_COND wc "
			+ "LEFT JOIN KSHMT_WORKING_COND_ITEM wci ON wci.HIST_ID = wc.HIST_ID "
			+ "WHERE wc.SID IN :listSid "
			+ "AND wc.START_DATE <= :refDate "
			+ "AND wc.END_DATE >= :refDate";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.query.model.employee.EmployeeInformationRepository#find(nts.uk.
	 * query.model.employee.EmployeeInformationQuery)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<EmployeeInformation> find(EmployeeInformationQuery param) {
		List<Object[]> persons = new ArrayList<>();

		CollectionUtil.split(param.getEmployeeIds(), 1000, (subList) -> {
			persons.addAll(this.getEntityManager().createQuery(EMPLOYEE_QUERY).setParameter("listSid", subList)
					.getResultList());
		});

		// get list employee
		Map<String, EmployeeInformation> employeeInfoList = persons.stream().map(item -> {
			BsymtEmployeeDataMngInfo e = (BsymtEmployeeDataMngInfo) item[0];
			BpsmtPerson p = (BpsmtPerson) item[1];
			return EmployeeInformation.builder()
					.employeeId(e.bsymtEmployeeDataMngInfoPk.sId)
					.employeeCode(e.employeeCode)
					.businessName(p.businessName)
					.build();
		}).collect(Collectors.toMap(EmployeeInformation::getEmployeeId, v -> v));

		// set workplace name
		if (param.isToGetWorkplace()) {
			List<Object[]> workplaces = this.getOptionalResult(param, WORKPLACE_QUERY);

			employeeInfoList.keySet().forEach(empId -> {
				Optional<Object[]> workplace = workplaces.stream().filter(wpl -> {
					String id = (String) wpl[0];
					return id.equals(empId);
				}).findAny();

				if (workplace.isPresent()) {
					String workplaceName = (String) workplace.get()[1];
					employeeInfoList.get(empId).setWorkplace(Optional.of(workplaceName));
				}
			});
		}

		// set position
		if (param.isToGetPosition()) {
			List<Object[]> positions = this.getOptionalResult(param, POSITION_QUERY);
			
			employeeInfoList.keySet().forEach(empId -> {
				Optional<Object[]> job = positions.stream().filter(wpl -> {
					String id = (String) wpl[0];
					return id.equals(empId);
				}).findAny();
				
				if (job.isPresent()) {
					String jobName = (String) job.get()[1];
					employeeInfoList.get(empId).setPosition(Optional.of(jobName));
				}
			});
		}

		// set employment
		if (param.isToGetEmployment()) {
			List<Object[]> employments = this.getOptionalResult(param, EMPLOYMENT_QUERY);
			
			employeeInfoList.keySet().forEach(empId -> {
				Optional<Object[]> em = employments.stream().filter(e -> {
					String id = (String) e[0];
					return id.equals(empId);
				}).findAny();
				
				if (em.isPresent()) {
					String empName = (String) em.get()[1];
					employeeInfoList.get(empId).setEmployment(Optional.of(empName));
				}
			});
		}

		// set classification
		if (param.isToGetClassification()) {
			List<Object[]> classifications = this.getOptionalResult(param, CLASSIFICATION_QUERY);
			
			employeeInfoList.keySet().forEach(empId -> {
				Optional<Object[]> cls = classifications.stream().filter(c -> {
					String id = (String) c[0];
					return id.equals(empId);
				}).findAny();
				
				if (cls.isPresent()) {
					String clsName = (String) cls.get()[1];
					employeeInfoList.get(empId).setClassification(Optional.of(clsName));
				}
			});
		}

		// set Employment classification
		if (param.isToGetEmploymentCls()) {
			List<Object[]> listEmpCls = this.getOptionalResult(param, EMPCLS_QUERY);
			
			employeeInfoList.keySet().forEach(empId -> {
				Optional<Object[]> cls = listEmpCls.stream().filter(c -> {
					String id = (String) c[0];
					return id.equals(empId);
				}).findAny();
				
				if (cls.isPresent()) {
					Integer laborSystem = (Integer) cls.get()[1];
					employeeInfoList.get(empId).setEmploymentCls(Optional.of(laborSystem));
				}
			});
		}
		
		return employeeInfoList.values().stream().collect(Collectors.toList());
	}

	@SuppressWarnings("unchecked")
	private List<Object[]> getOptionalResult(EmployeeInformationQuery param, String query) {
		List<Object[]> results = new ArrayList<>();
		CollectionUtil.split(param.getEmployeeIds(), 1000, (subList) -> {
			results.addAll(this.getEntityManager().createQuery(query).setParameter("listSid", subList)
					.setParameter("refDate", param.getReferenceDate()).getResultList());
		});
		return results;
	}

}
