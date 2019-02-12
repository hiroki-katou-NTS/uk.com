package nts.uk.file.com.app.person.matrix.datasource;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * PersonInfoMatrixDataSource
 * @author lanlt
 *
 */
@AllArgsConstructor
@Getter
public class PersonInfoMatrixDataSource {
	
	private String categoryId;
	
	private String categoryName;
	
	// thiết lập hiển thị cột fixed như workplace, department,...
	private FixedColumnDisplay fixedHeader;
	
	// lấy ra những header được hiển thị ra
	private List<GridHeaderData> dynamicHeader;
	
	// thông tin của employee
	private List<GridEmployeeInfoDataSource> detailData;
}
