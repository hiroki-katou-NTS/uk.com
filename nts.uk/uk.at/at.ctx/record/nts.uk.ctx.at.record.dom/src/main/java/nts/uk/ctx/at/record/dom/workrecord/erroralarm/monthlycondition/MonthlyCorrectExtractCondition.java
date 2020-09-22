/**
 * 9:18:51 AM Mar 28, 2018
 */
package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycondition;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordName;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmWorkRecordCode;

/**
 * @author hungnm 月別修正の抽出条件
 */
@Getter
public class MonthlyCorrectExtractCondition extends AggregateRoot {

	/* 会社ID */
	private String companyId;

	/* コード */
	private ErrorAlarmWorkRecordCode code;

	/* 名称 */
	private ErrorAlarmWorkRecordName name;

	/* 使用する */
	private Boolean useAtr;

	/* ID */
	private String errorAlarmCheckID;

	/* Constructor */
	private MonthlyCorrectExtractCondition() {
		super();
	}

	private MonthlyCorrectExtractCondition(String companyId, ErrorAlarmWorkRecordCode code,
			ErrorAlarmWorkRecordName name, Boolean useAtr, String errorAlarmCheckID) {
		super();
		this.companyId = companyId;
		this.code = code;
		this.name = name;
		this.useAtr = useAtr;
		this.errorAlarmCheckID = errorAlarmCheckID;
	}

	public static MonthlyCorrectExtractCondition createFromJavaType(String companyId, String code, String name,
			Boolean useAtr, String errorAlarmCheckID) {
		return new MonthlyCorrectExtractCondition(companyId, code.length() < 4 ? new ErrorAlarmWorkRecordCode("U" + code) : new ErrorAlarmWorkRecordCode(code),
				new ErrorAlarmWorkRecordName(name), useAtr, errorAlarmCheckID);
	}

	public static MonthlyCorrectExtractCondition init(String companyId, String code, String name, Boolean useAtr) {
		return new MonthlyCorrectExtractCondition(companyId, code.length() < 4 ? new ErrorAlarmWorkRecordCode("U" + code) : new ErrorAlarmWorkRecordCode(code),
				new ErrorAlarmWorkRecordName(name), useAtr, IdentifierUtil.randomUniqueId());
	}

	public void setCheckId(String errorAlarmCheckID) {
		this.errorAlarmCheckID = errorAlarmCheckID;
	}
}
