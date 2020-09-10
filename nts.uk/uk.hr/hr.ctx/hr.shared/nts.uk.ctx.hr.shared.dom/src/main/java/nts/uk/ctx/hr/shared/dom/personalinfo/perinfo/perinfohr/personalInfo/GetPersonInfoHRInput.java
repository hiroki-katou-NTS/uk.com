package nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.personalInfo;

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
	String companyId;
	
	// 業務ID
	String businessId;
	
	// （Option）List<個人ID>
	List<String> personalId;
	
	// （Option）List<社員ID>
	List<String> employeeId;
	
	// （Option）ソートカラム名
	String columnName;
	
	// （Option）ソート昇順降順
	String typeSort;
}