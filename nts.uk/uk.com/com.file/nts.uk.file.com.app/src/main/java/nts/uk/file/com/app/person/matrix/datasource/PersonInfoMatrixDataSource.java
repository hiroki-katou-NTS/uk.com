package nts.uk.file.com.app.person.matrix.datasource;

import java.util.List;
import lombok.Getter;
/**
 * PersonInfoMatrixDataSource
 * @author lanlt
 *
 */
@Getter
public class PersonInfoMatrixDataSource {
	
	private String categoryId;
	
	private String categoryName;
	
	private List<GridHeaderData> headDatas;
	
	private List<GridEmployeeInfoDataSource> bodyDatas;
}
