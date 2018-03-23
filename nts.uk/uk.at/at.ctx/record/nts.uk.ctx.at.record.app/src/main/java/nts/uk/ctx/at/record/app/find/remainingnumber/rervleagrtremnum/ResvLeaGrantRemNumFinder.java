package nts.uk.ctx.at.record.app.find.remainingnumber.rervleagrtremnum;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.RervLeaGrantRemDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantRemainingData;

@Stateless
public class ResvLeaGrantRemNumFinder {

	@Inject
	private RervLeaGrantRemDataRepository repository;

	public List<ResvLeaGrantRemNumDto> getListData(String employeeId) {
		List<ReserveLeaveGrantRemainingData> dataList = repository.find(employeeId);
		return dataList.stream().map(domain -> ResvLeaGrantRemNumDto.createFromDomain(domain))
				.collect(Collectors.toList());
	}

}
