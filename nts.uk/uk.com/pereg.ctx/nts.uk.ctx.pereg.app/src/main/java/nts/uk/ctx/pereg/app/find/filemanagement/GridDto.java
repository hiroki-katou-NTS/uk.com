package nts.uk.ctx.pereg.app.find.filemanagement;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.GridEmpHead;
@Data
@AllArgsConstructor
public class GridDto {
	private int updateMode;
	// định nghĩa kiểu dữ liệu item
	private List<GridEmpHead> headDatas;
	// danh sách employees, dữ liệu từng dòng
	private List<EmployeeRowDto> employees;
	// danh sách những item bị lỗi
	private List<ItemError> errorItems;
	

}
