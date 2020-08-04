package nts.uk.screen.at.app.query.kmp.kmp001.a;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.workplace.WorkPlaceInforExport;
import nts.uk.ctx.at.function.dom.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.bs.employee.pub.person.IPersonInfoPub;
import nts.uk.ctx.bs.employee.pub.person.PersonInfoExport;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KDP_打刻.KMP001_IDカードの登録.A：個人指定によるIDカード登録.メニュー別OCD.選択した社員情報と登録カード番号を取得する
 * @author chungnt
 *
 */

@Stateless
public class EmployeeInfoCardNumber {

	@Inject
	private StampCardRepository stampCardRepo;
	
	@Inject
	private IPersonInfoPub IPersonInfoPub;
	
	@Inject
	private WorkplaceAdapter workplaceAdapter;
	
	public EmployeeInfoCardNumberDto getInfoEmployeeandStamCard (String employeeId, String workplaceId, GeneralDate basedate) {
		String conpanyId = AppContexts.user().companyId();
		List<String> workplaceIds = new ArrayList<>();
		workplaceIds.add(workplaceId);
		
		List<WorkPlaceInforExport> placeInforExports = workplaceAdapter.getWorkplaceInforByWkpIds(conpanyId, workplaceIds, basedate);
		
		if (placeInforExports.isEmpty()) {
			throw new RuntimeException("Not found");
		}
		
		WorkPlaceInforExport placeInforExport = placeInforExports.get(0);
		
		PersonInfoExport personInfoExport = IPersonInfoPub.getPersonInfo(employeeId);
		
		if (personInfoExport == null) {
			throw new RuntimeException("Not found");
		}
		
		List<StampCard> stampCards = stampCardRepo.getListStampCard(employeeId);
		
		List<StampCardDto> cardDtos = new ArrayList<>();
		
		if (!stampCards.isEmpty()) {
			cardDtos = stampCards.stream().map(m -> {
				StampCardDto stampCard = new StampCardDto();
				stampCard.setStampCardId(m.getStampCardId());
				stampCard.setStampNumber(m.getStampNumber().v() == null ? "" : m.getStampNumber().v());
				return stampCard;
			}).collect(Collectors.toList());
		}
		
		return new EmployeeInfoCardNumberDto(placeInforExport.getWorkplaceCode() == null ? "" : placeInforExport.getWorkplaceCode(),
				placeInforExport.getWorkplaceName() ==  null ? "" : placeInforExport.getWorkplaceName(),
				personInfoExport.getPid() == null ? "" : personInfoExport.getPid(),
				personInfoExport.getBusinessName() == null ? "" : personInfoExport.getBusinessName(),
				personInfoExport.getEntryDate() == null ? GeneralDate.min() : personInfoExport.getEntryDate(),
				personInfoExport.getGender(),
				personInfoExport.getBirthDay() == null ? GeneralDate.today() : personInfoExport.getBirthDay(),
				personInfoExport.getEmployeeId() == null ? "" : personInfoExport.getEmployeeId(),
				personInfoExport.getEmployeeCode() == null ? "" : personInfoExport.getEmployeeCode(),
				personInfoExport.getRetiredDate() == null ? GeneralDate.min() : personInfoExport.getRetiredDate(),
				cardDtos);
	}
}
