package nts.uk.ctx.sys.assist.app.find.deletedata;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.assist.dom.deletedata.ResultDeletion;

@Data
@AllArgsConstructor
public class DeletionHistoryDto {

	// 削除済みファイル
	/** The deletion files flag. */
	private boolean isDeletedFilesFlg;

	// 削除開始日時
	/** The start date time deletion. */
	private GeneralDateTime startDateTimeDel;

	// 実行者
	/** The practitioner id. */
	private String practitioner;

	// 削除名称
	/** The deletion name. */
	private String delName;

	// 削除形態
	/** The deletion type. */
	private String delType;

	// 対象人数
	/** The number employees. */
	private int numberEmployees;

	// 保存ファイル名
	/** The file name. */
	private String fileName;

	// ファイル容量
	/** The file size. */
	private Integer fileSize;

	// ファイルID
	/** The file id. */
	private String fileId;

	public static DeletionHistoryDto fromDomain(ResultDeletion domain) {
		return new DeletionHistoryDto(domain.isDeletedFilesFlg(), domain.getStartDateTimeDel(), domain.getSId(),
				domain.getDelName().v(), domain.getDelType().nameId, domain.getNumberEmployees(),
				domain.getFileName().v(), domain.getFileSize(), domain.getFileId());
	}
}
