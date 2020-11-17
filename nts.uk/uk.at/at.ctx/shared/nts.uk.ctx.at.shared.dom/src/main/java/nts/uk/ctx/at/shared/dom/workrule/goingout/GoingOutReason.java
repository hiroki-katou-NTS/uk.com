package nts.uk.ctx.at.shared.dom.workrule.goingout;

import java.util.Optional;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.ConditionAtr;

/**
 * 
 * @author nampt
 * 外出理由
 *
 */
@AllArgsConstructor
public enum GoingOutReason {
	
	/* 私用 */
	PRIVATE(0, "私用", "Enum_Private"),
	/* 公用 */
	PUBLIC(1, "公用","Enum_Public"),
	/* 有償 */
	COMPENSATION(2, "有償", "Enum_Compensation"),
	/* 組合 */
	UNION(3, "組合", "Enum_Union");
	
	public final int value;
	
	/** The name id. */
	public final String nameId;
	
	/** The description. */
	public  String description;

	/**
	 * 私用か組合であるか判定する
	 * @return　私用か組合である
	 */
	public boolean isPrivateOrUnion() {
		return this.equals(PRIVATE)||this.equals(UNION);
	}
	
	/**
	 * 公用か有償であるか判定する
	 * @return　公用か有償である
	 */
	public boolean isPublicOrCmpensation() {
		return !isPrivateOrUnion();
	}

	
	/**
	 * 私用であるか判定する
	 * @return 私用である
	 */
	public boolean isPrivate(){
		return PRIVATE.equals(this);
	}
	
	/**
	 * 公用であるか判定する
	 * @return 公用である
	 */
	public boolean isPublic() {
		return PUBLIC.equals(this);
	}
	
	/**
	 * 有償であるか判定する
	 * @return　有償である
	 */
	public boolean isCompensation() {
		return COMPENSATION.equals(this);
	}
	
	/**
	 * 組合であるか判定する
	 * @return　組合である
	 */
	public boolean isUnion() {
		return UNION.equals(this);
	}
	
	/**
	 * 条件と外出理由が一致しているかを判定
	 * @param atr　条件
	 * @return　一致している
	 */
	public boolean equalReason(ConditionAtr atr) {
		switch(atr) {
		case PrivateGoOut:
			return PRIVATE.equals(this);
		case PublicGoOut:
			return PUBLIC.equals(this);
		case UnionGoOut:
			return UNION.equals(this);
		case CompesationGoOut:
			return COMPENSATION.equals(this);
		default:
			return false;
		}
	}
	
	public static Optional<GoingOutReason> corvert(int value) {
		for(GoingOutReason reason:GoingOutReason.values()) {
			if(reason.value == value) return Optional.of(reason);
		}
		return Optional.empty();
	}
	
	private final static GoingOutReason[] values = GoingOutReason.values();
	
	public static GoingOutReason valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (GoingOutReason val : GoingOutReason.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
	
	/**
	 * @param goOutReasons 外出理由
	 * @return true：一致している  false：一致していない
	 */
	public boolean anyMatch(GoingOutReason... goOutReasons) {
		for(GoingOutReason reason : goOutReasons) {
			if(reason.equals(this)) return true;
		}
		return false;
	}
}
