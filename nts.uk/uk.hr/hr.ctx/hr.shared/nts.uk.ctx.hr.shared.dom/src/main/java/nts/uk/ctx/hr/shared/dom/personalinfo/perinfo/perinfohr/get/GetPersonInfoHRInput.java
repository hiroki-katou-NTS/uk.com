package nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.get;

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
public class GetPersonInfoHRInput {
	
	// 会社ID
	private String companyId;
	
	// 業務ID
	private String businessId;
	
	// （Option）List<個人ID>
	private List<String> personalId;
	
	// （Option）List<社員ID>
	private List<String> employeeId;
	
	// （Option）ソートカラム名
	private String columnName;
	
	// （Option）ソート昇順降順
	private String typeSort;
}
