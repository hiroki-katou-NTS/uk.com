package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.createcode;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * テーブル定義情報
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
public class TableDefInfo {

	/**
	 * テーブルコメント
	 */
	private String tableNameComment = "";

	/**
	 * テーブル名
	 */
	private String tableName = "";

	/**
	 * カラム定義リスト
	 */
	private List<ColumnDefInfo> columnInfoList = new ArrayList<ColumnDefInfo>();

}



