package nts.uk.ctx.at.record.app.find.remainingnumber.specialleavegrant;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRemainingData;
import nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRepository;

@Stateless
public class SpecialLeaveGrantFinder {

	@Inject
	private SpecialLeaveGrantRepository repo;

	public List<SpecialLeaveGrantDto> getListData(String employeeId, int specialCode) {

		List<SpecialLeaveGrantRemainingData> datalist = repo.getAll(employeeId, specialCode);
		
		return datalist.stream().map(domain -> SpecialLeaveGrantDto.createFromDomain(domain))
				.collect(Collectors.toList());
		
	}
	
}
