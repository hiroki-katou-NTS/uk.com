package nts.uk.ctx.pereg.dom.usesetting;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

@Getter
public class UserSetting extends AggregateRoot {
	// 社員ID
	private String employeeId;
	// 社員コード初期値
	private EmpCodeValType empCodeValType;
	// カードNO初期値
	private CardNoValType cardNoValType;
	// 最近の登録
	private RecentRegType recentRegType;
	// 社員コード頭文字
	private EmpCodeLetter empCodeLetter;
	// カードNO頭文字
	private CardNoLetter cardNoLetter;

	private UserSetting(String employeeId, int empCodeValType, int cardNoValType, int recentRegType,
			String empCodeLetter, String cardNoLetter) {
		this.employeeId = employeeId;
		this.empCodeValType = EnumAdaptor.valueOf(empCodeValType, EmpCodeValType.class);
		this.cardNoValType = EnumAdaptor.valueOf(cardNoValType, CardNoValType.class);
		this.recentRegType = EnumAdaptor.valueOf(recentRegType, RecentRegType.class);
		this.empCodeLetter = new EmpCodeLetter(empCodeLetter);
		this.cardNoLetter = new CardNoLetter(cardNoLetter);
	}

	public static UserSetting generateFullObject(String employeeId, int empCodeValType, int cardNoValType,
			int recentRegType, String empCodeLetter, String cardNoLetter) {
		return new UserSetting(employeeId, empCodeValType, cardNoValType, recentRegType, empCodeLetter, cardNoLetter);
	}

	public static UserSetting createFromJavaType(String employeeId, int empCodeValType, int cardNoValType,
			int recentRegType, String empCodeLetter, String cardNoLetter) {
		return new UserSetting(employeeId, empCodeValType, cardNoValType, recentRegType, empCodeLetter, cardNoLetter);
	}
}
