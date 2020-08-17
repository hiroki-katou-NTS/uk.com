package nts.uk.ctx.hr.shared.dom.personalinfo.perinfo.perinfohr.qualificationhistory;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * 保有資格
 * @author chungnt
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EligibilityQualification {

	// 資格CD
	private String qualificationCd;
	
	// 資格ID
	private String qualificationId;
	
	// 社内外区分CD
	private Optional<String> categoryCd;
	
	// 社内外区分名
	private Optional<String> divisionName;
	
	// 終了日
	private Optional<GeneralDate> endDate;
	
	// 資格名
	private Optional<String> qualificationName;
	
	// 資格認定ランク
	private Optional<String> qualificationRank;
	
	// 資格認定団体
	private Optional<String> qualificationOrganization;
	
	// 資格認定番号
	private Optional<String> qualificationNumber;
	
}