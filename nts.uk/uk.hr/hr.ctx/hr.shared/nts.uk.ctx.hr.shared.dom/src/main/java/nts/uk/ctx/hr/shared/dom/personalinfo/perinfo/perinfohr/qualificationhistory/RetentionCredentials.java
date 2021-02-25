package nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.qualificationhistory;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 保有資格情報
 * @author chungnt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RetentionCredentials {

	// List<保有資格>
	private List<EligibilityQualification> eligibilitys;
	
	//社員ID
	private String sId;
}