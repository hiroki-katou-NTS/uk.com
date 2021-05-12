package nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * 家族情報
 * @author yuri_tamakoshi
*/
@Getter
@Setter
public class FamilyInfo {

	/** 家族ID */
	private String familyID;
	/** 生年月日 */
	private GeneralDate birthday;
	/** 死亡年月日 */
	private Optional<GeneralDate> deadYmd;

	/**
	 * コンストラクタ
	 */
	public FamilyInfo(){

		this.familyID = "";
		this.birthday = GeneralDate.today();
		this.deadYmd = Optional.empty();
	}

	/**
	 * ファクトリー
	 * @param familyID 家族ID
	 * @param birthday 生年月日
	 * @param deadYmd 死亡年月日
	 * @return 家族情報
	 */
	public static FamilyInfo of(
			String familyID,
			GeneralDate birthday,
			Optional<GeneralDate> deadYmd){

		FamilyInfo domain = new FamilyInfo();
		domain.familyID = familyID;
		domain.birthday = birthday;
		domain.deadYmd = deadYmd;
		return domain;
	}

}
