package nts.uk.ctx.bs.employee.dom.empfilemanagement;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TypeFile {
	/** 顔写真ファイル */
	AVATAR_FILE(0),
	/** ファイル */
	MAP_FILE(1),
	/** 電子書類ファイル */
	DOCUMENT_FILE(2),
	/** 顔写真ファイル */
	AVATAR_FILE_NOTCROP(3);
	
	public final int value;

}
