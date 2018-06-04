package nts.uk.ctx.sys.assist.dom.storage;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDateTime;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
* データ保存の保存結果
*/
@NoArgsConstructor
@Getter
public class ResultOfSaving extends AggregateRoot
{
    
    /**
    * データ保存処理ID
    */
    private String storeProcessingId;
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * システム種類
    */
    private SystemType systemType;
    
    /**
    * ファイル容量
    */
    private int fileSize;
    
    /**
    * 保存セットコード
    */
    private SaveSetCode saveSetCode;
    
    /**
    * 保存ファイル名
    */
    private SaveFileName saveFileName;
    
    /**
    * 保存名称
    */
    private SaveName saveName;
    
    /**
    * 保存形態
    */
    private StorageForm saveForm;
    
    /**
    * 保存終了日時
    */
    private GeneralDateTime saveEndDatetime;
    
    /**
    * 保存開始日時
    */
    private GeneralDateTime saveStartDatetime;
    
    /**
    * 削除済みファイル
    */
    private NotUseAtr deletedFiles;
    
    /**
    * 圧縮パスワード
    */
    private FileCompressionPassword compressedPassword;
    
    /**
    * 実行者
    */
    private String practitioner;
    
    /**
    * 対象人数
    */
    private int targetNumberPeople;
    
    /**
    * 結果状態
    */
    private SaveStatus saveStatus;
    
    /**
    * 調査用保存
    */
    private NotUseAtr saveForInvest;
    
    /**
    * ファイルID
    */
    private String fileId;

	public ResultOfSaving(String storeProcessingId, String cid, int systemType, int fileSize,
			String saveSetCode, String saveFileName, String saveName, int saveForm,
			GeneralDateTime saveEndDatetime, GeneralDateTime saveStartDatetime, int deletedFiles,
			String compressedPassword, String practitioner, int targetNumberPeople,
			int saveStatus, int saveForInvest, String fileId) {
		super();
		this.storeProcessingId = storeProcessingId;
		this.cid = cid;
		this.systemType = EnumAdaptor.valueOf(systemType, SystemType.class);
		this.fileSize = fileSize;
		this.saveSetCode = new SaveSetCode(saveSetCode);
		this.saveFileName = new SaveFileName(saveFileName);
		this.saveName = new SaveName(saveName);
		this.saveForm = EnumAdaptor.valueOf(saveForm, StorageForm.class);
		this.saveEndDatetime = saveEndDatetime;
		this.saveStartDatetime = saveStartDatetime;
		this.deletedFiles = EnumAdaptor.valueOf(deletedFiles, NotUseAtr.class);;
		this.compressedPassword = new FileCompressionPassword(compressedPassword);
		this.practitioner = practitioner;
		this.targetNumberPeople = targetNumberPeople;
		this.saveStatus = EnumAdaptor.valueOf(saveStatus, SaveStatus.class);;
		this.saveForInvest = EnumAdaptor.valueOf(saveForInvest, NotUseAtr.class);;
		this.fileId = fileId;
	}
}
