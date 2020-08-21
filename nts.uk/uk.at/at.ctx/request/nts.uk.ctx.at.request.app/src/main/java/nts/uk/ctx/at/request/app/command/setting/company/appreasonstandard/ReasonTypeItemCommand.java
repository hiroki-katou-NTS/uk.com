package nts.uk.ctx.at.request.app.command.setting.company.appreasonstandard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppReasonStandard;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.ReasonTypeItem;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReasonTypeItemCommand {
	/**
	 * 申請種類
	 */
	private int appType;

	/**
	 * 理由コード
	 */
	private int reasonCode;

	/**
	 * 表示順
	 */
	private int dispOrder;

	/**
	 * 既定
	 */
	private boolean defaultFlg;

	/**
	 * 定型理由
	 */
	private String reasonTemp;
	
	/**
	 * 休暇申請の種類
	 */
	private Integer holidayAppType;

	public ReasonTypeItem toDomain() {
		return ReasonTypeItem.createNew(reasonCode, dispOrder, defaultFlg, reasonTemp);
	}
}
