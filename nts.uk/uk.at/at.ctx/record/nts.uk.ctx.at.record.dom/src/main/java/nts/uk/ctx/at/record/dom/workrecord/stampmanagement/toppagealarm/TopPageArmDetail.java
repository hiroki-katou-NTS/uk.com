package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.toppagealarm;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * トップページアラーム詳細
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.shared.トップページアラーム.トップページアラーム詳細(更新処理用)
 * @author chungnt
 *
 */

@AllArgsConstructor
@Getter
public class TopPageArmDetail {
	
	/** エラーメッセージ */
	private final String errorMessage;
	
	/** 連番 */
	private final int serialNumber;
	
	/** 社員ID */
	private final String sid;
}
