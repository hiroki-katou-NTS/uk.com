package nts.uk.screen.at.app.query.kmp.kmp001.a;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author chungnt
 *
 */
@Stateless
public class ExtractedEmployeeCardSetting {

	@Inject
	private StampCardRepository stampCardRepo;
	
	public List<ExtractedEmployeeCardSettingDto> getStatusStampCard (List<String> prEmployee){
		String contractCd = AppContexts.user().contractCode();
		List<StampCard> employees = stampCardRepo.getLstStampCardByLstSidAndContractCd(prEmployee, contractCd);
		return employees.stream().map(m -> {
			ExtractedEmployeeCardSettingDto employeeid = new ExtractedEmployeeCardSettingDto();
			employeeid.setEmployee(m.getEmployeeId());
			return employeeid;
		}).collect(Collectors.toList());
	}
}
