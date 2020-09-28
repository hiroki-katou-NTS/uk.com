package nts.uk.ctx.sys.assist.app.find.resultofdeletion;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.assist.dom.deletedata.ResultDeletion;
import nts.uk.ctx.sys.assist.dom.deletedata.ResultLogDeletion;
import nts.uk.ctx.sys.assist.dom.storage.LoginInfo;

/**
 * データ削除の保存結果
 */
@AllArgsConstructor
@Value
public class ResultOfDeletionDto {

	//field データ削除処理ID
	private String delId;

	//field 会社ID
	private String companyID;

	//field 削除名称
	private String delName;

	//field 削除形態
	private int delType;

	//field 削除済みファイル
	private int isDeletedFilesFlg;

	//field 削除セットコード 
	private String delCode;

	//field 対象人数
	private int numberEmployees;
	
	//field 実行結果
	private List<ResultLogDeletion> listResultLogDeletions;

	//field 実行者
	private String sId;

	//field 結果状態
	private int status;

	//field 削除開始日時
	private GeneralDateTime startDateTimeDel;

	//field 削除終了日時
	private GeneralDateTime endDateTimeDel;

	//field ファイルID
	private String fileId;

	//field 保存ファイル名
	private String fileName;
	
	//field ファイル容量
	private Integer fileSize;

	//field 手動保存の圧縮パスワード
	private String passwordCompressFileEncrypt;
	
	//field ログイン情報
	private LoginInfo loginInfo;
	
	
	public static ResultOfDeletionDto fromDomain(ResultDeletion domain) {
		int isDeletedFilesFlg = domain.isDeletedFilesFlg() == true ? 1 : 0;
		return new ResultOfDeletionDto(
			domain.getDelId(),
			domain.getCompanyId(),
			domain.getDelName().v(),
			domain.getDelType().value,
			isDeletedFilesFlg,
			domain.getDelCode().v(),
			domain.getNumberEmployees(),
			domain.getListResultLogDeletions(),
			domain.getSId(),
			domain.getStatus().value,
			domain.getStartDateTimeDel(), 
			domain.getEndDateTimeDel(), 
			domain.getFileId(),
			domain.getFileName().v(),
			domain.getFileSize(),
			domain.getPasswordCompressFileEncrypt().map(i -> i.v()).orElse(null),
			domain.getLoginInfo()
			);
	}
}

















