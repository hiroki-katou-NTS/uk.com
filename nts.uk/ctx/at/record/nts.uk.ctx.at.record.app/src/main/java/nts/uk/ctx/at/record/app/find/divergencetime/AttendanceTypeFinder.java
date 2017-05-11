package nts.uk.ctx.at.record.app.find.divergencetime;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.divergencetime.DivergenceTimeRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AttendanceTypeFinder {
	@Inject
	private DivergenceTimeRepository divTimeRepo;
	//user contexts
	String companyId = AppContexts.user().companyId();
	int attendanceItemType = 1;
	public List<AttendanceTypeDto> getAllItem(){
		List<AttendanceTypeDto> lst = this.divTimeRepo.getAllItem(companyId, attendanceItemType)
				.stream()
				.map(c->AttendanceTypeDto.fromDomain(c))
				.collect(Collectors.toList());
		return lst;
	}
}
