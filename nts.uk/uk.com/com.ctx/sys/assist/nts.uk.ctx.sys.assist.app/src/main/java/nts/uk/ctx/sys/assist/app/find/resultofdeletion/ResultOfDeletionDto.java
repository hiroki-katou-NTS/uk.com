package nts.uk.ctx.sys.assist.app.find.resultofdeletion;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.assist.dom.deletedata.ResultDeletion;
import nts.uk.ctx.sys.assist.dom.deletedata.ResultLogDeletion;
import nts.uk.ctx.sys.assist.dom.storage.LoginInfo;

/**
 *Dto  データ削除の保存結果
 */
@AllArgsConstructor
@Data
public class ResultOfDeletionDto {

	/**
	 * データ削除処理ID
	 */
	private String delId;

	/**
	 * 会社ID
	 */
	private String companyID;

	/**
	 * 削除名称
	 */
	private String delName;

	/**
	 * 削除形態
	 */
	private int delType;

	/**
	 * 削除済みファイル
	 */
	private int isDeletedFilesFlg;

	/**
	 * 削除セットコード 
	 */
	private String delCode;

	/**
	 * 対象人数
	 */
	private int numberEmployees;
	
	/**
	 * 実行結果
	 */
	private List<ResultLogDeletion> listResultLogDeletions;

	/**
	 * 実行者
	 */
	private String sId;

	/**
	 * 結果状態
	 */
	private int status;

	/**
	 * 削除開始日時
	 */
	private GeneralDateTime startDateTimeDel;

	/**
	 * 削除終了日時
	 */
	private GeneralDateTime endDateTimeDel;

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
	private Integer fileSize;

	/**
	 * 手動保存の圧縮パスワード
	 */
	private String passwordCompressFileEncrypt;
	
	/**
	 * ログイン情報
	 */
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

















