package nts.uk.ctx.sys.assist.dom.storage;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDateTime;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
* データ保存の保存結果
*/
@NoArgsConstructor
@Getter
@Setter
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
    * ファイル容量
    */
    private Optional<Long> fileSize;
    
    /**
    * パターンコード
    */
    private PatternCode patternCode;
    
    /**
    * 保存ファイル名
    */
    private Optional<SaveFileName> saveFileName;
    
    /**
    * パターン区分
    */
    private int patternClassification;
    
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
    private Optional<GeneralDateTime> saveEndDatetime;
    
    /**
    * 保存開始日時
    */
    private Optional<GeneralDateTime> saveStartDatetime;
    
    /**
    * 削除済みファイル
    */
    private NotUseAtr deletedFiles;
    
    /**
    * 圧縮パスワード
    */
    private Optional<FileCompressionPassword> compressedPassword;
    
    /**
    * 実行者
    */
    private String practitioner;
    
    //field 実行結果
    private List<ResultLogSaving> listResultLogSavings;
    
    /**
    * 対象人数
    */
    private Optional<Integer> targetNumberPeople;
    
    /**
    * 結果状態
    */
    private Optional<SaveStatus> saveStatus;
    
    /**
    * 調査用保存
    */
    private NotUseAtr saveForInvest;
    
    /**
    * ファイルID
    */
    private Optional<String> fileId;
    
    //field ログイン情報
    private LoginInfo loginInfo;

	public ResultOfSaving(String storeProcessingId, String cid, Long fileSize,
			String patternCode, String saveFileName, String saveName, int saveForm,
			GeneralDateTime saveEndDatetime, GeneralDateTime saveStartDatetime, int deletedFiles,
			String compressedPassword, String practitioner,List<ResultLogSaving> listResultLogSavings,
			Integer targetNumberPeople, Integer saveStatus, int saveForInvest, String fileId, LoginInfo logInfo) {
		super();
		this.storeProcessingId = storeProcessingId;
		this.cid = cid;
		this.fileSize = Optional.ofNullable(fileSize);
		this.patternCode = new PatternCode(patternCode);
		this.saveFileName = saveFileName == null ? Optional.empty() : Optional.of(new SaveFileName(saveFileName));
		this.saveName = new SaveName(saveName);
		this.saveForm = EnumAdaptor.valueOf(saveForm, StorageForm.class);
		this.saveEndDatetime = Optional.ofNullable(saveEndDatetime);
		this.saveStartDatetime = Optional.ofNullable(saveStartDatetime);
		this.deletedFiles = EnumAdaptor.valueOf(deletedFiles, NotUseAtr.class);;
		this.compressedPassword = compressedPassword == null ? Optional.empty() : Optional.of(new FileCompressionPassword(compressedPassword));
		this.practitioner = practitioner;
		this.listResultLogSavings = listResultLogSavings;
		this.targetNumberPeople = Optional.ofNullable(targetNumberPeople); 
		this.saveStatus = saveStatus == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(saveStatus, SaveStatus.class));
		this.saveForInvest = EnumAdaptor.valueOf(saveForInvest, NotUseAtr.class);;
		this.fileId = Optional.ofNullable(fileId);
		this.loginInfo = logInfo;
	}
	
	public void setSaveFileName(String saveFileName){
		this.saveFileName = saveFileName == null ? Optional.empty() : Optional.of(new SaveFileName(saveFileName));
	}
}