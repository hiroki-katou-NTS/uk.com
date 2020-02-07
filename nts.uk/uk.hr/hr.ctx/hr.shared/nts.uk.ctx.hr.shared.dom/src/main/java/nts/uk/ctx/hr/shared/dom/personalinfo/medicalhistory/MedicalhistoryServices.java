package nts.uk.ctx.hr.shared.dom.personalinfo.medicalhistory;

import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;


@Stateless
public class MedicalhistoryServices {

	@Inject
	private MedicalhistoryRepository repo;

	// 受診履歴のロード
	public MedicalhistoryManagement loadMedicalhistoryItem(List<String> listSID, GeneralDate baseDate) {
		List<MedicalhistoryItemResults> medicalhistoryItemResults = repo.getListMedicalhistoryItem(listSID, baseDate);
		return new MedicalhistoryManagement(medicalhistoryItemResults, listSID);
	}

	// 受診履歴の取得
	public List<MedicalhistoryItemResults> getMedicalhistoryItem(String sId, GeneralDate baseDate) {
		MedicalhistoryManagement domain = this.loadMedicalhistoryItem(Arrays.asList(sId), baseDate);
		return domain.getMedicalhistoryItemResults();
	}
}
