package nts.uk.file.at.app.export.dailyschedule;

import lombok.Data;

/**
 * 職場階層累計.
 *
 * @author HoangNDH
 */
@Data
public class TotalWorkplaceHierachy {
	
	/** The Constant ROOT_LEVEL. */
	public static final int ROOT_LEVEL = 0;
	
	/** The Constant FIRST_LEVEL. */
	public static final int FIRST_LEVEL = 1;
	
	/** The Constant SECOND_LEVEL. */
	public static final int SECOND_LEVEL = 2;
	
	/** The Constant THIRD_LEVEL. */
	public static final int THIRD_LEVEL = 3;
	
	/** The Constant FOURTH_LEVEL. */
	public static final int FOURTH_LEVEL = 4;
	
	/** The Constant FIFTH_LEVEL. */
	public static final int FIFTH_LEVEL = 5;
	
	/** The Constant SIXTH_LEVEL. */
	public static final int SIXTH_LEVEL = 6;
	
	/** The Constant SEVENTH_LEVEL. */
	public static final int SEVENTH_LEVEL = 7;
	
	/** The Constant EIGHTH_LEVEL. */
	public static final int EIGHTH_LEVEL = 8;
	
	/** The Constant NINTH_LEVEL. */
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
	 * Check level enable from UI.
	 *
	 * @param level the level
	 * @return true, if successful
	 */
	public boolean checkLevelEnabled(int level) {
		switch (level) {
		case FIRST_LEVEL:
			return firstLevel != null && firstLevel;
		case SECOND_LEVEL:
			return secondLevel != null && secondLevel;
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
	
	/**
	 * Gets the highest level enabled.
	 *
	 * @return the highest level enabled
	 */
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
	
	/**
	 * Gets the closest selected hierarchy level (below current level).
	 *
	 * @param level the level
	 * @return the closest selected hierarchy level
	 */
	public int getLowerClosestSelectedHierarchyLevel(int level) {
		for (int i = level; i <= NINTH_LEVEL; i++) {
			if (checkLevelEnabled(level))
				return i;
		}
		
		// Return root level
		return ROOT_LEVEL;
	}
	
	/**
	 * Gets the closest selected hierarchy level (above current level).
	 *
	 * @param level the level
	 * @return the closest selected hierarchy level
	 */
	public int getHigherClosestSelectedHierarchyLevel(int level) {
		for (int i = level; i > ROOT_LEVEL; i--) {
			if (checkLevelEnabled(level))
				return i;
		}
		
		// Return root level
		return ROOT_LEVEL;
	}
}
