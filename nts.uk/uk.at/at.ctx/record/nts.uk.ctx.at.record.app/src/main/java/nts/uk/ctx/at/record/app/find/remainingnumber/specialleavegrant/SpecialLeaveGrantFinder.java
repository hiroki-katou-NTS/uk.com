package nts.uk.ctx.at.record.app.find.remainingnumber.specialleavegrant;

import java.util.List;
import java.util.Optional;
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
	
	public Optional<SpecialLeaveGrantRemainingData> getDetail(String specialid){
		
		Optional<SpecialLeaveGrantRemainingData> data = repo.getBySpecialId(specialid);
		if (!data.isPresent()) {
			return Optional.empty();
		}
		return data;
	}

	public List<SpecialLeaveGrantDto> getListDataByCheckState(String employeeId, int specialCode, Boolean checkState) {
		List<SpecialLeaveGrantRemainingData> datalist = repo.getAll(employeeId, specialCode);
		return datalist.stream().map(domain -> SpecialLeaveGrantDto.createFromDomain(domain))
				.collect(Collectors.toList());
	}
}
