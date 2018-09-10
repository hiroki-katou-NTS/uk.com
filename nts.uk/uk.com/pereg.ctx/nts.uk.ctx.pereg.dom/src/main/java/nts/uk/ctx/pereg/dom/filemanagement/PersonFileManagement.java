package nts.uk.ctx.pereg.dom.filemanagement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PersonFileManagement extends AggregateRoot {

	/**
	 * domain : 個人ファイル管理 old 社員ファイル管理
	 */

	/** The personId 個人ID */
	private String pId;

	/** The ファイルID */
	private String fileID;

	/** The type file (0 : avatarfile, 1: mapfile , 2 : documentfile) */
	private TypeFile typeFile;

	/** order document file */
	private Integer uploadOrder;

	public static PersonFileManagement createFromJavaType(String pId, String fileID, int typeFile, Integer uploadOrder) {
		return new PersonFileManagement(pId, fileID, EnumAdaptor.valueOf(typeFile, TypeFile.class), uploadOrder);
	}

}
