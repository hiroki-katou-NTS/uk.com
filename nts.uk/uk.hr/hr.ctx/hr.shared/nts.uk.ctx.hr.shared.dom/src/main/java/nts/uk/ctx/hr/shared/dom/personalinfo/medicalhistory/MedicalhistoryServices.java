package nts.uk.ctx.hr.shared.dom.personalinfo.medicalhistory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;


@Stateless
public class MedicalhistoryServices {

	@Inject
	private MedicalhistoryRepository repo;

	// 受診履歴のロード
	public MedicalhistoryManagement loadMedicalhistoryItem(List<String> listSID, GeneralDate baseDate, MedicalhistoryManagement result) {

		List<MedicalhistoryItem> listDomain = repo.getListMedicalhistoryItem(listSID, baseDate);
		result.fillData(listDomain, listSID);
		return result;
	}

	// 受診履歴の取得
	public MedicalhistoryItem getMedicalhistoryItem(String sId, GeneralDate baseDate, MedicalhistoryManagement medicalhisManagement) {
		Optional<MedicalhistoryItem> domainOpt = medicalhisManagement.getMedicalhistoryItems().stream()
				.filter(e -> e.sid.equals(sId)).findFirst();
		if (domainOpt.isPresent()) {
			return domainOpt.get();
		} else {
			Optional<String> pidOpt = medicalhisManagement.getSearchedSIDs()
					.stream().filter(e -> e.equals(sId)).findFirst();
			
			if (pidOpt.isPresent()) {
				return null;
			}
			
			List<MedicalhistoryItem> domain = repo.getListMedicalhistoryItem(Arrays.asList(sId), baseDate);
			if (domain.isEmpty()) {
				return null;
			}
			return domain.get(0);
		}
	}
}
