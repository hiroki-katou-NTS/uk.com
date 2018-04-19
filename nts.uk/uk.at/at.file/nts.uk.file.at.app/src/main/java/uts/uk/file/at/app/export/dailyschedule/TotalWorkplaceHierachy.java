package uts.uk.file.at.app.export.dailyschedule;

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
	private Optional<Boolean> firstLevel;
	// 2階層
	private Optional<Boolean> secondLevel;
	// 3階層
	private Optional<Boolean> thirdLevel;
	// 4階層
	private Optional<Boolean> fourthLevel;
	// 5階層
	private Optional<Boolean> fifthLevel;
	// 6階層
	private Optional<Boolean> sixthLevel;
	// 7階層
	private Optional<Boolean> seventhLevel;
	// 8階層
	private Optional<Boolean> eightLevel;
	// 9階層
	private Optional<Boolean> ninthLevel;
}
