package nts.uk.ctx.at.request.app.find.application.lateorleaveearly;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrEarlyAtr;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrEarlyInfo;

@Data
@AllArgsConstructor
@NoArgsConstructor
// 取り消す初期情報
public class LateOrEarlyInfoDto {
	// チェックする
	private Boolean isCheck;
	// 勤務NO
	private int workNo;
	// 活性する
	private Boolean isActive;
	// 表示する
	private Boolean isIndicated;
	// 遅刻早退区分
	private int category;

	public static LateOrEarlyInfoDto convertDto(LateOrEarlyInfo lateOrEarlyInfo) {
		return new LateOrEarlyInfoDto(lateOrEarlyInfo.getIsCheck(), lateOrEarlyInfo.getWorkNo(),
				lateOrEarlyInfo.getIsActive(), lateOrEarlyInfo.getIsIndicated(), lateOrEarlyInfo.getCategory().value);

	}

	public LateOrEarlyInfo toDomain() {
		return new LateOrEarlyInfo(this.isCheck, this.workNo, this.isActive, this.isIndicated,
				EnumAdaptor.valueOf(this.category, LateOrEarlyAtr.class));
	}
}
