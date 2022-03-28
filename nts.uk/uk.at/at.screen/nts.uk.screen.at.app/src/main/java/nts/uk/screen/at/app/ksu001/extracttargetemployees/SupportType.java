package nts.uk.screen.at.app.ksu001.extracttargetemployees;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SupportType {

	DO_NOT_GO_TO_SUPPORT( 0 ),
	GO_TO_SUPPORT( 1 ),
	COME_TO_SUPPORT( 2 ),
	;


	public final int value;

}
