package nts.uk.ctx.sys.assist.app.find.logdataresult;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDateTime;

@AllArgsConstructor
@Data
public class LogDataResultDto {
	
	private String id;

	/**
	 * IPアドレス
	 */
	private String ipAddress;
	
	/**
	 * PC名
	 */
	private String pcName;
	
	/**
	 * アカウント
	 */
	private String account;
	
	/**
	 * 実行社員コード
	 */
	private String employeeCode;
	
	/**
	 * 実行社員名称
	 */
	private String employeeName;
	
	/**
	 * 開始日時　
	 */
	private GeneralDateTime startDateTime;
	
	/**
	 * 終了日時
	 */
	private GeneralDateTime endDateTime;
	
	/**
	 * 形態
	 */
	private int form;
	
	/**
	 * 名称　
	 */
	private String name;
	
	/**
	 * ファイルID　
	 */
	private String fileId;
	
	/**
	 * 保存ファイル名　
	 */
	private String fileName;
	
	/**
	 * ファイル容量　
	 */
	private long fileSize;
	
	/**
	 * 結果状態　
	 */
	private Integer status;
	
	/**
	 * 対象人数　
	 */
	private Integer targetNumberPeople;
	
	/**
	 * セットコード　
	 */
	private String setCode;
	
	/**
	 * 削除済みファイル　
	 */
	private int isDeletedFilesFlg;
	
	/**
	 * 結果ログ
	 */
	List<LogResultDto> logResult;
}
