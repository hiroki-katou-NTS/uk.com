package nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.qualificationhistory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/**
 * 保有資格
 * @author chungnt
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EligibilityQualification {

	// 資格CD
	private String qualificationCd;
	
	// 資格ID
	private String qualificationId;
	
	// 社内外区分CD
	private String categoryCd;
	
	// 社内外区分名
	private String divisionName;
	
	// 終了日
	private GeneralDate endDate;
	
	// 資格名
	private String qualificationName;
	
	// 資格認定ランク
	private String qualificationRank;
	
	// 資格認定団体
	private String qualificationOrganization;
	
	// 資格認定番号
	private String qualificationNumber;
	
}