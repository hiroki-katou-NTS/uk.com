package nts.uk.ctx.sys.assist.app.find.datarestoration;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.sys.assist.dom.datarestoration.PerformDataRecovery;

@AllArgsConstructor
@Value
public class ScreenItemDto {

	/**
	 * データ復旧処理ID
	 */
	private String dataRecoveryProcessId;

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 保存処理ID
	 */
	private Optional<String> saveProcessId;

	/**
	 * アップロードファイルID
	 */
	private String uploadfileId;

	/**
	 * 復旧ファイル名
	 */
	private String recoveryFileName;

	/**
	 * 復旧対象者数
	 */
	private int numPeopleBeRestore;

	/**
	 * 保存対象者数
	 */
	private int numPeopleSave;

	/**
	 * 復旧方法
	 */
	private int recoveryMethod;

	/**
	 * 別会社復旧
	 */
	private int recoverFromAnoCom;

	public static ScreenItemDto fromDomain(PerformDataRecovery domain) {
		return new ScreenItemDto(domain.getDataRecoveryProcessId(), domain.getCid(), domain.getSaveProcessId(),
				domain.getUploadfileId(), domain.getRecoveryFileName(), domain.getNumPeopleBeRestore(),
				domain.getNumPeopleSave(), domain.getRecoveryMethod().value, domain.getRecoverFromAnoCom().value);
	}

}
