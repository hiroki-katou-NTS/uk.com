package nts.uk.ctx.bs.employee.pubimp.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHist;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistByEmployee;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistItem;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistRepository;
import nts.uk.ctx.bs.employee.pub.company.AffComHistItem;
import nts.uk.ctx.bs.employee.pub.company.AffCompanyHistExport;
import nts.uk.ctx.bs.employee.pub.company.SyCompanyPub;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class ComPubImp implements SyCompanyPub {

	@Inject
	private AffCompanyHistRepository affComHistRepo;

	@Override
	public List<AffCompanyHistExport> GetAffCompanyHistByEmployee(List<String> sids, DatePeriod datePeriod) {

		if (sids.isEmpty() || datePeriod.start() == null || datePeriod.end() == null)
			return null;
		String cid = AppContexts.user().companyId();

		List<AffCompanyHistExport> result = new ArrayList<>();

		sids.forEach(sid -> {

			AffCompanyHistExport affComHostEx = new AffCompanyHistExport();
			affComHostEx.setEmployeeId(sid);

			affComHostEx.setLstAffComHistItem(getListAffComHistItem(cid, sid, datePeriod));

			result.add(affComHostEx);
		});

		return result;
	}

	private List<AffComHistItem> getListAffComHistItem(String cid, String sid, DatePeriod datePeriod) {
		
		List<AffComHistItem> result = new ArrayList<>();

		AffCompanyHist affComHist = affComHistRepo.getAffCompanyHistoryOfEmployee(sid);

		AffCompanyHistByEmployee affComHistByEmp = affComHist.getAffCompanyHistByEmployee(sid);

		if (affComHistByEmp.items() != null) {

			List<AffCompanyHistItem> filter = affComHistByEmp.getLstAffCompanyHistoryItem().stream()
					.filter(itemHist -> {
						return (itemHist.start().beforeOrEquals(datePeriod.end())
								&& itemHist.end().afterOrEquals(datePeriod.start()));
					}).collect(Collectors.toList());

			if (!filter.isEmpty()) {
				result = filter.stream().map(item ->
					new AffComHistItem(item.getHistoryId(), item.isDestinationData(), item.getDatePeriod())
				).collect(Collectors.toList());
			}
		}
		return result;

	}

	@Override
	public AffCompanyHistExport GetAffComHisBySidAndBaseDate(String sid, GeneralDate baseDate) {
		
		AffCompanyHist affComHis = affComHistRepo.getAffCompanyHistoryOfEmployeeAndBaseDate(sid, baseDate);
		
		if (affComHis == null){
			return new AffCompanyHistExport(null, Collections.emptyList());
		}
		
		AffCompanyHistByEmployee affComBySid = affComHis.getAffCompanyHistByEmployee(sid);
		
		AffCompanyHistExport affComHostEx = new AffCompanyHistExport();
		affComHostEx.setEmployeeId(sid);

		affComHostEx.setLstAffComHistItem(affComBySid.getLstAffCompanyHistoryItem().stream()
				.map(item -> new AffComHistItem(item.getHistoryId(), item.isDestinationData(), item.getDatePeriod()))
				.collect(Collectors.toList()));

		return affComHostEx;
	}

	@Override
	public AffCompanyHistExport GetAffComHisBySid(String cid, String sid) {
		AffCompanyHist affComHis = affComHistRepo.getAffCompanyHistoryOfEmployee(sid);
		
		if (affComHis == null){
			return new AffCompanyHistExport(null, Collections.emptyList());
		}
		
		AffCompanyHistByEmployee affComBySid = affComHis.getAffCompanyHistByEmployee(sid);
		
		AffCompanyHistExport affComHostEx = new AffCompanyHistExport();
		affComHostEx.setEmployeeId(sid);

		affComHostEx.setLstAffComHistItem(affComBySid.getLstAffCompanyHistoryItem().stream()
				.map(item -> new AffComHistItem(item.getHistoryId(), item.isDestinationData(), item.getDatePeriod()))
				.collect(Collectors.toList()));

		return affComHostEx;
	};

}
