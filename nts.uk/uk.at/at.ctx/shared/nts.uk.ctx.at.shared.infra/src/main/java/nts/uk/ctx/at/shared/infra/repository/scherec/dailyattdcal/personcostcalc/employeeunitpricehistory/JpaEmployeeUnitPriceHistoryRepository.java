package nts.uk.ctx.at.shared.infra.repository.scherec.dailyattdcal.personcostcalc.employeeunitpricehistory;

import java.util.List;
import java.util.Optional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.personcostcalc.employeeunitpricehistory.EmployeeUnitPriceHistoryItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.personcostcalc.employeeunitpricehistory.EmployeeUnitPriceHistoryRepositoly;
import nts.uk.ctx.at.shared.infra.entity.scherec.dailyattdcal.personcostcalc.employeeunitpricehistory.KsrmtUnitPriceItemSya;

public class JpaEmployeeUnitPriceHistoryRepository extends JpaRepository implements EmployeeUnitPriceHistoryRepositoly {

	private static final String SELECT_BY_SID_AND_BASEDATE = new StringBuilder("SELECT a FROM EmployeeUnitPriceHistoryItem a ")
			.append("INNER JOIN EmployeeUnitPriceHistory b ON a.key.histId = b.key.histId ")
			.append("WHERE b.key.sId <= :sId ")
			.append("AND b.startDate <= :baseDate ")
			.append("AND b.endDate >= :baseDate")
			.toString();

	public Optional<EmployeeUnitPriceHistoryItem> get(String sId, GeneralDate baseDate) {
		List<KsrmtUnitPriceItemSya> items = this.queryProxy().query(SELECT_BY_SID_AND_BASEDATE, KsrmtUnitPriceItemSya.class)
				.setParameter("sid", sId)
				.setParameter("baseDate", baseDate)
				.getList();
		
		return KsrmtUnitPriceItemSya.toDomain(items);
	}
}
