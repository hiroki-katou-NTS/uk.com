package nts.uk.ctx.sys.assist.dom.datarestoration;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * データ復旧の実行
 */
@Getter
public class PerformDataRecovery extends AggregateRoot {

	/**
	 * データ復旧処理ID
	 */
	private String dataRecoveryProcessId;

	/**
	 * 会社ID
	 */
	private String cid;

	/**
	 * 会社ID
	 */
	private List<Target> targets;

	/**
	 * 保存処理ID
	 */
	@Setter
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
	 * 復旧対象
	 */
	private List<RestorationTarget> restorationTarget;

	/**
	 * 復旧対象者数
	 */
	@Setter
	private int numPeopleBeRestore;

	/**
	 * 保存対象者数
	 */
	@Setter
	private int numPeopleSave;

	/**
	 * 復旧方法
	 */
	@Setter
	private RecoveryMethod recoveryMethod;

	/**
	 * 別会社復旧
	 */
	@Setter
	private NotUseAtr recoverFromAnoCom;

	public PerformDataRecovery(String dataRecoveryProcessId, String cid, List<Target> targets, String saveProcessId, String uploadfileId, String recoveryFileName, List<RestorationTarget> restorationTarget, int numPeopleBeRestore, int numPeopleSave, int recoveryMethod, int recoverFromAnoCom) {
		this.dataRecoveryProcessId = dataRecoveryProcessId;
		this.cid                   = cid;
		this.targets               = targets;
		this.saveProcessId         = Optional.ofNullable(saveProcessId);
		this.uploadfileId          = uploadfileId;
		this.recoveryFileName      = recoveryFileName;
		this.restorationTarget     = restorationTarget;
		this.numPeopleBeRestore    = numPeopleBeRestore;
		this.numPeopleSave         = numPeopleSave;
		this.recoveryMethod        = EnumAdaptor.valueOf(recoveryMethod, RecoveryMethod.class);
		this.recoverFromAnoCom     = EnumAdaptor.valueOf(recoverFromAnoCom, NotUseAtr.class);
	}

	public PerformDataRecovery(String dataRecoveryProcessId, String cid, String uploadfileId,
			String recoveryFileName) {
		this.dataRecoveryProcessId = dataRecoveryProcessId;
		this.cid                   = cid;
		this.uploadfileId          = uploadfileId;
		this.recoveryFileName      = recoveryFileName;
		this.saveProcessId         = Optional.empty();
	}
}
