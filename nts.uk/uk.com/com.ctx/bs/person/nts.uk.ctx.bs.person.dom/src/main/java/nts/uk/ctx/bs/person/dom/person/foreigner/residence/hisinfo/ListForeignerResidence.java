package nts.uk.ctx.bs.person.dom.person.foreigner.residence.hisinfo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListForeignerResidence {
	
	List<ForeignerResidenceHistoryInforItem> listForeignerResidenceHistoryInforItem;
	List<String> searchedPersonIDList;
}
