package nts.uk.screen.at.app.ksu001.sortsetting;

import java.util.List;

import lombok.Value;

@Value
public class SortedListEmpDto {
	// List<社員所属チーム情報>
	List<EmpTeamInforDto> lstEmpTeamInforDto;
	// List<社員ランク情報>
	List<EmpRankInforDto> lstEmpRankInforDto;
	// List<社員免許区分>
	List<EmpLicenseClassificationDto> lstEmpLicenseClassificationDto;

	List<EmplInforATR> lstEmpInforATR;

	List<String> lstEmpId;

}
