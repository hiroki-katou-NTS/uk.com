package nts.uk.ctx.hr.shared.dom.personalinfo.medicalhistory;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListMedicalhistory {
	
	List<MedicalhistoryItem> listMedicalhistoryItem;
	List<String> searchedSIDList;
}
