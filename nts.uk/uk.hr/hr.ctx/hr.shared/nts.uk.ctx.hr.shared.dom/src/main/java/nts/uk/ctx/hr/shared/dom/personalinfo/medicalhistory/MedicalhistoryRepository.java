package nts.uk.ctx.hr.shared.dom.personalinfo.medicalhistory;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface MedicalhistoryRepository {

	// 受診履歴の取得
	List<MedicalhistoryItem> getListMedicalhistoryItem(List<String> listSID , GeneralDate baseDate);

}
