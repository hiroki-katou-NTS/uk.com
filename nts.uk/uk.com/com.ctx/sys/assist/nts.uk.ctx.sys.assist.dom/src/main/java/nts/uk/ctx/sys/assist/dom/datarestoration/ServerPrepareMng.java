package nts.uk.ctx.sys.assist.dom.datarestoration;

import java.util.Objects;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.gul.file.archive.ExtractStatus;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * サーバー準備動作管理
 */
@Getter
public class ServerPrepareMng extends AggregateRoot {

	/**
	 * データ復旧処理ID
	 */
	private String dataRecoveryProcessId;

	/**
	 * データ保存処理ID
	 */
	@Setter
	private Optional<String> dataStoreProcessId;

	/**
	 * ファイルID
	 */
	@Setter
	private Optional<String> fileId;

	/**
	 * アップロードファイル名
	 */
	@Setter
	private Optional<String> uploadFileName;

	/**
	 * アップロードをするしない
	 */
	@Setter
	private NotUseAtr doNotUpload;

	/**
	 * パスワード
	 */
	@Setter
	private Optional<FileCompressionPassword> password;

	/**
	 * 動作状態
	 */
	@Setter
	private ServerPrepareOperatingCondition operatingCondition;
	
	public ServerPrepareMng(String dataRecoveryProcessId, String dataStoreProcessId, String fileId,
			String uploadFileName, int doNotUpload, String password, int operatingCondition) {
		super();
		this.dataRecoveryProcessId = dataRecoveryProcessId;
		this.dataStoreProcessId    = Optional.ofNullable(dataStoreProcessId);
		this.fileId                = Optional.ofNullable(fileId);
		this.uploadFileName        = Optional.ofNullable(uploadFileName);
		this.doNotUpload           = EnumAdaptor.valueOf(doNotUpload, NotUseAtr.class);
		if (Objects.isNull(password)) {
			this.password          = Optional.empty();
		} else {
			this.password          = Optional.of(new FileCompressionPassword(password));
		}
		this.operatingCondition    = EnumAdaptor.valueOf(operatingCondition, ServerPrepareOperatingCondition.class);
	}
	
	public ServerPrepareMng setOperatingConditionBy (ExtractStatus extractStatus) {
		ServerPrepareOperatingCondition operatingCondition = null;
		switch (extractStatus) {
		case SUCCESS:
			operatingCondition = ServerPrepareOperatingCondition.CHECKING_FILE_STRUCTURE;
			break;
		case NOT_VALID_FILE:
			operatingCondition = ServerPrepareOperatingCondition.EXTRACTION_FAILED;
			break;
		case NONE_CORRECT_PASSWORD:
			operatingCondition = ServerPrepareOperatingCondition.PASSWORD_DIFFERENCE;
			break;
		case NEED_PASSWORD:
			operatingCondition = ServerPrepareOperatingCondition.PASSWORD_DIFFERENCE;
			break;
		}
		this.operatingCondition = operatingCondition;
		return this;
	}
}
