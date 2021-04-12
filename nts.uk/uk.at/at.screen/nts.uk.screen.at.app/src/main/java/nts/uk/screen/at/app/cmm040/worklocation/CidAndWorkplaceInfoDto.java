package nts.uk.screen.at.app.cmm040.worklocation;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.query.model.workplace.WorkplaceInfoImport;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CidAndWorkplaceInfoDto {
	
	private String companyId ;
	
	//Map＜会社ID、List＜職場ID、職場コード、職場名称、職場示名、職場総称、職場外部コード、階層コード＞
	private List<WorkplaceInfoImport> listWorkplaceInfoImport = new ArrayList<>();
}	
