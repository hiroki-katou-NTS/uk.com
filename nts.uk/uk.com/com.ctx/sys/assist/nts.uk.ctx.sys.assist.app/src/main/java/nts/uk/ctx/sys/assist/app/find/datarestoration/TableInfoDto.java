package nts.uk.ctx.sys.assist.app.find.datarestoration;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Dto テーブルの情報
 */
@Data
@AllArgsConstructor
public class TableInfoDto {
	
	/**
	 * テーブルNo
	 */
	private int tableNo;

	/**
	 * テーブル日本語名
	 */
	private String tableJapaneseName;

	/**
	 * テーブル物理名
	 */
	private String tableEnglishName;

}
