package nts.uk.file.at.app.export.workinghours;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author chungnt
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CompanyExportDatasource {

	public List<ChildCompany> company;
	
}
