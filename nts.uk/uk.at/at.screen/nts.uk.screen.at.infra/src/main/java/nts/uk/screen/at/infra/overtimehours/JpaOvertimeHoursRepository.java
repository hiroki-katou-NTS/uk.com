package nts.uk.screen.at.infra.overtimehours;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHist;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistByEmployee;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistItem;
import nts.uk.ctx.bs.employee.infra.entity.employee.history.BsymtAffCompanyHist;
import nts.uk.screen.at.app.ktgwidget.OvertimeHoursRepository;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class JpaOvertimeHoursRepository extends JpaRepository implements OvertimeHoursRepository {
	private static final String SELECT_BY_LIST_WKPID_DATEPERIOD = "SELECT awit.sid FROM BsymtAffiWorkplaceHistItem awit" + " INNER JOIN BsymtAffiWorkplaceHist aw on aw.hisId = awit.hisId"
			+ " WHERE awit.workPlaceId IN :workplaceIds AND aw.strDate <= :endDate AND :startDate <= aw.endDate";

	@Override
	public List<String> getAffWkpHistItemByListWkpIdAndDatePeriod(DatePeriod dateperiod, List<String> workplaceId) {
		List<String> listHistItem = new ArrayList<>();
		CollectionUtil.split(workplaceId, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			listHistItem.addAll(this.queryProxy().query(SELECT_BY_LIST_WKPID_DATEPERIOD, String.class)
					.setParameter("workplaceIds", subList)
					.setParameter("startDate", dateperiod.start())
					.setParameter("endDate", dateperiod.end()).getList());
		});
		if (listHistItem.isEmpty()) {
			return Collections.emptyList();
		}
		return listHistItem;
	}


	private static final String SELECT_BY_LIST_EMPTCODE_DATEPERIOD = "SELECT ehi.sid FROM BsymtEmploymentHistItem ehi" + " INNER JOIN  BsymtEmploymentHist eh on eh.hisId = ehi.hisId"
			+ " WHERE ehi.empCode IN :employmentCodes AND eh.strDate <= :endDate AND :startDate <= eh.endDate";

	@Override
	public List<String> getListEmptByListCodeAndDatePeriod(DatePeriod dateperiod, List<String> employmentCodes) {
		List<String> listHistItem = new ArrayList<>();
		CollectionUtil.split(employmentCodes, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			listHistItem.addAll(this.queryProxy().query(SELECT_BY_LIST_EMPTCODE_DATEPERIOD, String.class).setParameter("employmentCodes", subList).setParameter("startDate", dateperiod.start())
					.setParameter("endDate", dateperiod.end()).getList());
		});
		if (listHistItem.isEmpty()) {
			return Collections.emptyList();
		}
		 
		return listHistItem;
	}
	
	/** The Constant MAX_ELEMENTS. */
	private static final String SELECT_NO_PARAM = String.join(" ", "SELECT c FROM BsymtAffCompanyHist c");
	private static final String SELECT_BY_EMPID_AND_DATE_PERIOD = String.join(" ", SELECT_NO_PARAM,
			" WHERE c.bsymtAffCompanyHistPk.sId IN :employeeIds   AND c.startDate <= :endDate AND :startDate <= c.endDate ");
	@Override
	public List<AffCompanyHist> getAffComHisEmpByLstSidAndPeriod(List<String> employeeIds, DatePeriod datePeriod) {
		long startTime2 = System.nanoTime();
		// OutPut Data
		List<AffCompanyHist> resultData = new ArrayList<>();
		// CHECK EMPTY of employeeIds
		if (CollectionUtil.isEmpty(employeeIds)) {
			return new ArrayList<>();
		}
		// ResultList
		List<BsymtAffCompanyHist> resultList = new ArrayList<>();
		// Split employeeId List if size of employeeId List is greater than 1000
		CollectionUtil.split(employeeIds, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, (subList) -> {
			List<BsymtAffCompanyHist> lstBsymtAffCompanyHist = this.queryProxy()
					.query(SELECT_BY_EMPID_AND_DATE_PERIOD, BsymtAffCompanyHist.class)
					.setParameter("employeeIds", subList)
					.setParameter("startDate", datePeriod.start())
					.setParameter("endDate", datePeriod.end())
					.getList();
			resultList.addAll(lstBsymtAffCompanyHist);
		});

		// check empty ResultList
		if (CollectionUtil.isEmpty(resultList)) {
			return new ArrayList<>();
		}
		// Convert Result List to Map
		Map<String, List<BsymtAffCompanyHist>> resultMap = resultList.stream()
				.collect(Collectors.groupingBy(item -> item.bsymtAffCompanyHistPk.pId));

		// Foreach Map: Convert to Domain then add to Output List
		resultMap.entrySet().forEach(data -> {
			AffCompanyHist affComHist = this.toDomain(data.getValue());
			resultData.add(affComHist);
		});
		long endTime2 = System.nanoTime();	
		long duration2 = (endTime2 - startTime2) / 1000000; // ms;
		System.out.println("TimeData:" + duration2);		
		return resultData;
		
	}

	private AffCompanyHist toDomain(List<BsymtAffCompanyHist> entities) {
		if (entities.isEmpty()) {
			return null;
		}
		AffCompanyHist domain = new AffCompanyHist();

		for (BsymtAffCompanyHist item : entities) {
			if (domain.getPId() == null) {
				domain.setPId(item.bsymtAffCompanyHistPk.pId);
			}

			AffCompanyHistByEmployee affCompanyHistByEmployee = domain
					.getAffCompanyHistByEmployee(item.bsymtAffCompanyHistPk.sId);

			if (affCompanyHistByEmployee == null) {
				affCompanyHistByEmployee = new AffCompanyHistByEmployee();
				affCompanyHistByEmployee.setSId(item.bsymtAffCompanyHistPk.sId);

				domain.addAffCompanyHistByEmployee(affCompanyHistByEmployee);
			}

			AffCompanyHistItem affCompanyHistItem = affCompanyHistByEmployee
					.getAffCompanyHistItem(item.bsymtAffCompanyHistPk.historyId);

			if (affCompanyHistItem == null) {
				affCompanyHistItem = new AffCompanyHistItem();
				affCompanyHistItem.setDestinationData(item.destinationData == 1);
				affCompanyHistItem.setHistoryId(item.bsymtAffCompanyHistPk.historyId);
				affCompanyHistItem.setDatePeriod(new DatePeriod(item.startDate, item.endDate));

				affCompanyHistByEmployee.addAffCompanyHistItem(affCompanyHistItem);
			}
		}

		return domain;
	}
}
