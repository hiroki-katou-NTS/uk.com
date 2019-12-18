package nts.uk.ctx.hr.develop.app.databeforereflecting.retirementinformation.command;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@Data
public class RetiInforRegisInfoCommand {

	private String historyID;
	// (A222_44) 社員コード
	private String scd;
	// (A222_45) 社員名
	private String employeeName;
	// (A222_51) 入社日
	private GeneralDate dateJoinComp;
	// (A222_40) 保留
	private boolean onHoldFlag;
	// (A222_41) 退職/継続 == `継続`
	private boolean DurationFlg;
	// (A222_43) 希望勤務コース.ID
	private long retirePlanCourseId;
	// (A222_43) 希望勤務コース.CD
	private String retirePlanCourseCode;
	// (A222_43) 希望勤務コース.名称
	private String retirePlanCourseName;
	// (A222_52) 退職日
	private GeneralDateTime retirementDate;
	// (A222_53) 公開日
	private GeneralDateTime releaseDate;
}
