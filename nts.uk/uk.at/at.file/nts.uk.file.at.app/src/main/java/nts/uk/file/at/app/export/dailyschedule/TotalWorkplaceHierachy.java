package nts.uk.file.at.app.export.dailyschedule;

import lombok.Data;

/**
 * 職場階層累計
 * @author HoangNDH
 *
 */
@Data
public class TotalWorkplaceHierachy {
	
	// Level constants
	public static final int FIRST_LEVEL = 1;
	public static final int SECOND_LEVEL = 2;
	public static final int THIRD_LEVEL = 3;
	public static final int FOURTH_LEVEL = 4;
	public static final int FIFTH_LEVEL = 5;
	public static final int SIXTH_LEVEL = 6;
	public static final int SEVENTH_LEVEL = 7;
	public static final int EIGHTH_LEVEL = 8;
	public static final int NINTH_LEVEL = 9;
	
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
		case FIRST_LEVEL:
			return firstLevel != null && firstLevel;
		case SECOND_LEVEL:
			return secondLevel != null && firstLevel;
		case THIRD_LEVEL:
			return thirdLevel != null && thirdLevel;
		case FOURTH_LEVEL:
			return fourthLevel != null && fourthLevel;
		case FIFTH_LEVEL:
			return fifthLevel != null && fifthLevel;
		case SIXTH_LEVEL:
			return sixthLevel != null && sixthLevel;
		case SEVENTH_LEVEL:
			return seventhLevel != null && seventhLevel;
		case EIGHTH_LEVEL:
			return eightLevel != null && eightLevel;
		case NINTH_LEVEL:
			return ninthLevel != null && ninthLevel;
		default:
			return false;
		}
	}
	
	public int getHighestLevelEnabled() {
		return  checkLevelEnabled(FIRST_LEVEL)? FIRST_LEVEL : 
			 	checkLevelEnabled(SECOND_LEVEL)? SECOND_LEVEL :
			 	checkLevelEnabled(THIRD_LEVEL)? THIRD_LEVEL :
		 		checkLevelEnabled(FOURTH_LEVEL)? FOURTH_LEVEL :
	 			checkLevelEnabled(FIFTH_LEVEL)? FIFTH_LEVEL :
 				checkLevelEnabled(SIXTH_LEVEL)? SIXTH_LEVEL :
				checkLevelEnabled(SEVENTH_LEVEL)? SEVENTH_LEVEL:
				checkLevelEnabled(EIGHTH_LEVEL)? EIGHTH_LEVEL :
				checkLevelEnabled(NINTH_LEVEL)? NINTH_LEVEL : -1;
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
