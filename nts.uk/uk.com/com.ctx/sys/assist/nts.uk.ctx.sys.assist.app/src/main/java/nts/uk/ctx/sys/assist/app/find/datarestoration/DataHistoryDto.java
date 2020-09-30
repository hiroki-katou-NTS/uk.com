package nts.uk.ctx.sys.assist.app.find.datarestoration;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.assist.dom.datarestoration.DataRecoveryResult;
import nts.uk.ctx.sys.assist.dom.datarestoration.PerformDataRecovery;
import nts.uk.ctx.sys.assist.dom.storage.ResultOfSaving;

@Data
@AllArgsConstructor 
public class DataHistoryDto {
	
	/**
	 * データ復旧処理ID
	 */
	private String dataRecoveryProcessId;
	
	/**
	 * データ保存処理ID
	 */
	private String dataSaveProcessId;
	
	/**
	 * 開始日時
	 */
	private GeneralDateTime startDatetime;
	
	/**
	 * 実行者
	 */
	private String practitioner;
	
	/**
	 * 保存名称
	 */
	private String saveName;
	
	/**
	 * 復旧対象者数
	 */
	private int restoreCount;
	
	/**
	 * 復旧ファイル名
	 */
	private String fileName;
	
	/**
	 * 保存対象者数
	 */
	private int saveCount;
	
	/**
	 * 保存開始日時
	 */
	private GeneralDateTime saveStartDatetime;
	
	/**
	 * 実行結果
	 */
	private String executionResult;
	
	public static DataHistoryDto fromDomains(ResultOfSaving domain1, PerformDataRecovery domain2, DataRecoveryResult domain3) {
		return new DataHistoryDto(
				domain3.getDataRecoveryProcessId(), 
				domain3.getDataStorageProcessId(), 
				domain3.getStartDateTime(), 
				domain3.getPractitioner(), 
				domain3.getSaveName().v(), 
				domain2.getNumPeopleBeRestore(), 
				domain2.getRecoveryFileName(), 
				domain2.getNumPeopleSave(), 
				domain1.getSaveStartDatetime().orElse(null), 
				domain3.getExecutionResult());
	}
}
