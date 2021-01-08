package nts.uk.ctx.office.ac.favoritespecify;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.bs.employee.pub.workplace.ResultRequest597Export;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplacePub;
import nts.uk.ctx.office.dom.favorite.adapter.EmployeeBelongWorkplaceAdapter;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class EmployeeBelongWorkplaceAdapterImpl implements EmployeeBelongWorkplaceAdapter {

	@Inject
	private WorkplacePub workplacePub;

	@Override
	public List<String> getEmployeeByWplAndBaseDate(List<String> wkps, GeneralDate baseDate) {

		// $期間 ＝ 期間(基準日、基準日)
		DatePeriod period = new DatePeriod(baseDate, baseDate);

		// Request 597 職場の所属社員を取得する
		List<ResultRequest597Export> data = workplacePub.getLstEmpByWorkplaceIdsAndPeriod(wkps, period);
		return data.stream().map(v -> v.getSid()).collect(Collectors.toList());
	}

}
