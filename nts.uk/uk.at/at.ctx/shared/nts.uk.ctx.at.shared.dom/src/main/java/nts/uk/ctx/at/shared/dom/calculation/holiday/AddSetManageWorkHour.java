package nts.uk.ctx.at.shared.dom.calculation.holiday;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Getter
@Setter
@Builder
// 就業時間の加算設定管理
public class AddSetManageWorkHour extends AggregateRoot{
	/** 会社ID */
	private String companyId;

	/** 時間外超過の加算設定 */
	private NotUseAtr additionSettingOfOvertime;
}
