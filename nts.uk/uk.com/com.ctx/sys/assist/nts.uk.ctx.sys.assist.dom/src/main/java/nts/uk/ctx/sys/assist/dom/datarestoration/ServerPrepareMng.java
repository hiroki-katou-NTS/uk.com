package nts.uk.ctx.sys.assist.dom.datarestoration;

import java.util.Objects;
import java.util.Optional;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
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
	private Optional<String> dataStoreProcessId;

	/**
	 * ファイルID
	 */
	private Optional<String> fileId;

	/**
	 * アップロードファイル名
	 */
	private Optional<String> uploadFileName;

	/**
	 * アップロードをするしない
	 */
	private NotUseAtr doNotUpload;

	/**
	 * パスワード
	 */
	private Optional<FileCompressionPassword> password;

	/**
	 * 動作状態
	 */
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
}
