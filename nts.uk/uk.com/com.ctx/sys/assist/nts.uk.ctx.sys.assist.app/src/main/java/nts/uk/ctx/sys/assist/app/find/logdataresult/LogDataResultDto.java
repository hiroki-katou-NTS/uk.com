package nts.uk.ctx.sys.assist.app.find.logdataresult;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDateTime;

@AllArgsConstructor
@Value
public class LogDataResultDto {
	
	private String id;

	//field IPアドレス
	private String ipAddress;
	
	//field PC名
	private String pcName;
	
	//field アカウント
	private String account;
	
	//field 実行社員コード
	private String employeeCode;
	
	//field 実行社員名称
	private String employeeName;
	
	//field 開始日時　
	private String startDateTime;
	
	//field 終了日時
	private String endDateTime;
	
	//field 形態
	private int form;
	
	//field 名称　
	private String name;
	
	//field ファイルID　
	private String fileId;
	
	//field 保存ファイル名　
	private String fileName;
	
	//field ファイル容量　
	private long fileSize;
	
	//field 結果状態　
	private Integer status;
	
	//field 対象人数　
	private Integer targetNumberPeople;
	
	//field セットコード　
	private String setCode;
	
	//field 削除済みファイル　
	private int isDeletedFilesFlg;
	
	//field 結果ログ
	List<LogResultDto> logResult;
}
