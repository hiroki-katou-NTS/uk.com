package nts.uk.screen.at.app.kdw013.a;

import java.util.List;

import lombok.Getter;

/**
 * 
 * @author tutt
 *
 */
@Getter
public class RegisterWorkContentCommand {
	
	/** 対象者 */
	private String employeeId;
	
	/** 編集状態<Enum.日別勤怠の編集状態> */
	private int editStateSetting;
	
	/** List<年月日,List<作業詳細>> */
	private List<WorkDetailCommand> workDetails;
	
	private int mode;
	
}
