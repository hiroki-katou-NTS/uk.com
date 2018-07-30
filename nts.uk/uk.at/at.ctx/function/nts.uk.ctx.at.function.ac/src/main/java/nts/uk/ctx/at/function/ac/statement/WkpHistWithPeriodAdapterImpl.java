package nts.uk.ctx.at.function.ac.statement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.statement.WkpHistWithPeriodAdapter;
import nts.uk.ctx.at.function.dom.statement.dtoimport.WkpHistWithPeriodImport;
import nts.uk.ctx.at.function.dom.statement.dtoimport.WkpInfoHistImport;
import nts.uk.ctx.bs.employee.pub.workplace.SyWorkplacePub;
import nts.uk.shr.com.time.calendar.period.DatePeriod;


@Stateless
public class WkpHistWithPeriodAdapterImpl implements WkpHistWithPeriodAdapter{

	@Inject
	private SyWorkplacePub syWorkplacePub;

	@Override
	public WkpHistWithPeriodImport getLstHistByWkpsAndPeriod(List<String> wkpIds, DatePeriod period) {
		return (WkpHistWithPeriodImport) syWorkplacePub.getLstHistByWkpsAndPeriod(wkpIds, period).stream().map(dto -> {
			List<WkpInfoHistImport> lstWkpInfoHistImport = new ArrayList<>();
			
			dto.getWkpInfoHistLst().stream().forEach(dto2 -> {
				WkpInfoHistImport wkpInfoHistImport = new WkpInfoHistImport(dto2.getPeriod(), dto2.getWkpCode(), dto2.getWkpDisplayName());
				lstWkpInfoHistImport.add(wkpInfoHistImport);
			});
			
			WkpHistWithPeriodImport wkpHistWithPeriodImport = new WkpHistWithPeriodImport(dto.getWkpId(), lstWkpInfoHistImport);
			return wkpHistWithPeriodImport;
		}).collect(Collectors.toList());
	}
	
}
