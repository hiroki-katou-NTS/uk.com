package nts.uk.file.at.app.export.dailyschedule;

import java.util.Optional;

import lombok.Data;

/**
 * 職場階層累計
 * @author HoangNDH
 *
 */
@Data
public class TotalWorkplaceHierachy {
	// 1階層
	private Boolean firstLevel;
	// 2階層
	private Boolean secondLevel;
	// 3階層
	private Boolean thirdLevel;
	// 4階層
	private Boolean fourthLevel;
	// 5階層
	private Boolean fifthLevel;
	// 6階層
	private Boolean sixthLevel;
	// 7階層
	private Boolean seventhLevel;
	// 8階層
	private Boolean eightLevel;
	// 9階層
	private Boolean ninthLevel;
	
	/**
	 * Check level enable from UI
	 * @param level
	 * @return
	 */
	public boolean checkLevelEnabled(int level) {
		switch (level) {
		case 1:
			return firstLevel != null && firstLevel;
		case 2:
			return secondLevel != null && firstLevel;
		case 3:
			return thirdLevel != null && thirdLevel;
		case 4:
			return fourthLevel != null && fourthLevel;
		case 5:
			return fifthLevel != null && fifthLevel;
		case 6:
			return sixthLevel != null && sixthLevel;
		case 7:
			return seventhLevel != null && seventhLevel;
		case 8:
			return eightLevel != null && eightLevel;
		case 9:
			return ninthLevel != null && ninthLevel;
		default:
			return false;
		}
	}
//	
//	public static TotalWorkplaceHierachy convertToDomain(TotalWorkplaceHierachyDto dto) {
//		TotalWorkplaceHierachy hierachy = new TotalWorkplaceHierachy();
//		if (dto.getFirstLevel() != null && dto.getFirstLevel()) {
//			hierachy.firstLevel = Optional.of(dto.getFirstLevel());
//		}
//		if (dto.getSecondLevel() != null && dto.getSecondLevel()) {
//			hierachy.secondLevel = Optional.of(dto.getSecondLevel());
//		}
//		if (dto.getThirdLevel() != null && dto.getThirdLevel()) {
//			hierachy.thirdLevel = Optional.of(dto.getThirdLevel());
//		}
//		if (dto.getFourthLevel() != null && dto.getFourthLevel()) {
//			hierachy.fourthLevel = Optional.of(dto.getFourthLevel());
//		}
//		if (dto.getFifthLevel() != null && dto.getFifthLevel()) {
//			hierachy.fifthLevel = Optional.of(dto.getFifthLevel());
//		}
//		if (dto.getSixthLevel() != null && dto.getSixthLevel()) {
//			hierachy.sixthLevel = Optional.of(dto.getSixthLevel());
//		}
//		if (dto.getSeventhLevel() != null && dto.getSeventhLevel()) {
//			hierachy.seventhLevel = Optional.of(dto.getSeventhLevel());
//		}
//		if (dto.getEightLevel() != null && dto.getEightLevel()) {
//			hierachy.eightLevel = Optional.of(dto.getEightLevel());
//		}
//		if (dto.getNinthLevel() != null && dto.getNinthLevel()) {
//			hierachy.ninthLevel = Optional.of(dto.getNinthLevel());
//		}
//		return hierachy;
//	}
}
