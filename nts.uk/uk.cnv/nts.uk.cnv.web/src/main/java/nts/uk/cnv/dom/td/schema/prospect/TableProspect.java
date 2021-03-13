package nts.uk.cnv.dom.td.schema.prospect;

import lombok.Getter;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;

/**
 *  テーブル定義のプロスペクト
 *
 *  スナップショットに対して何らかの未検収おるたを適用したもので、永続化はされない。
 *  それらのおるたが検収されればスナップショットとして確定するため、「テーブル定義の将来予想図」のようなもの。
 *  自分自身のバージョンを識別するために、最後に適用したおるたのIDを保持している。
 */
@Getter
public class TableProspect extends TableDesign {
	/** 最後に適用したおるたのalterationId **/
	private String alterationId;

	public TableProspect(String alterationId, TableDesign tableDesign) {
		super(
				tableDesign.getId(),
				tableDesign.getName(),
				tableDesign.getJpName(),
				tableDesign.getColumns(),
				tableDesign.getIndexes());
	}

}
