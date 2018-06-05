package nts.uk.shr.com.security.audittrail.correction.content;

import java.util.Optional;

import lombok.Getter;
import nts.arc.time.GeneralDate;

/**
 * 対象データKEY情報
 */
public class TargetDataKey {

	/** 年月日 */
	@Getter
	private final Optional<GeneralDate> dateKey;
	
	/** 文字列KEY */
	@Getter
	private final Optional<String> stringKey;
	
	private TargetDataKey(GeneralDate dateKey, String stringKey) {
		this.dateKey = Optional.ofNullable(dateKey);
		this.stringKey = Optional.ofNullable(stringKey);
	}
	
	public static TargetDataKey of(GeneralDate dateKey) {
		return new TargetDataKey(dateKey, null);
	}

	public static TargetDataKey of(String stringKey) {
		return new TargetDataKey(null, stringKey);
	}
	
	public static TargetDataKey of(GeneralDate dateKey, String stringKey) {
		return new TargetDataKey(dateKey, stringKey);
	}
}
