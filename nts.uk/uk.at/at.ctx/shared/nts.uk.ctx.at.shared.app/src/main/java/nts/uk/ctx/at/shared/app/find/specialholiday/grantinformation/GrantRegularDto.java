package nts.uk.ctx.at.shared.app.find.specialholiday.grantinformation;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantRegular;

@Value
public class GrantRegularDto {

	/** 会社ID */
	private String companyId;

	/** 特別休暇コード */
	private int specialHolidayCode;

	/** 付与するタイミングの種類 */
	private int typeTime;

	/** 付与基準日 */
	private int grantDate;

	/** 取得できなかった端数は消滅する */
	private boolean allowDisappear;

	/** 取得できなかった端数は消滅する */
	private GrantTimeDto grantTime;

	public static GrantRegularDto fromDomain(GrantRegular grantRegular) {
		if(grantRegular == null) {
			return null;
		}

		//KMF004A 特別休暇情報の登録　実装時にこの処理を実装すること
//		GrantTimeDto grantTimeDto = GrantTimeDto.fromDomain(grantRegular.getGrantTime() != null ? grantRegular.getGrantTime() : null);
//
//		return new GrantRegularDto(
//				grantRegular.getCompanyId(),
//				grantRegular.getSpecialHolidayCode().v(),
//				grantRegular.getTypeTime().value,
//				grantRegular.getGrantDate().value,
//				grantRegular.isAllowDisappear(),
//				grantTimeDto
//		);
		return new GrantRegularDto();
	}

	/**
	 * コンストラクタ
	 */
	public GrantRegularDto() {
		companyId = "";
		typeTime = -1;
		specialHolidayCode = -1;
		grantDate = -1;
		allowDisappear = false;
		grantTime = new GrantTimeDto();
	}
}
