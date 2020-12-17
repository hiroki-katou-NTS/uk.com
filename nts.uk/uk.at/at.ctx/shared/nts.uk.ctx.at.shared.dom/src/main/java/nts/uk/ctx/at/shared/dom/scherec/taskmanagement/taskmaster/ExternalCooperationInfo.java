package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 外部連携情報
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared(勤務予定、勤務実績).作業管理.作業マスタ.外部連携情報
 * @author lan_lt
 *
 */
@Getter
@AllArgsConstructor
public class ExternalCooperationInfo {
	/** 外部コード1 */
	private Optional<TaskExternalCode> externalCode1;
	
	/** 外部コード2 */
	private Optional<TaskExternalCode> externalCode2;
	
	/** 外部コード3 */
	private Optional<TaskExternalCode> externalCode3;
	
	/** 外部コード4 */
	private Optional<TaskExternalCode> externalCode4;
	
	/** 外部コード5 */
	private Optional<TaskExternalCode> externalCode5;

}
