package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.createcode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CreateCodeTest_Entity {

	public static void main(String[] args) {

		/** テーブル定義フォルダ */
		String tablepath = "C:\\Users\\masaaki_jinno\\Documents\\dev\\就業\\自動生成コード\\テーブル定義";
		/** ファイル出力先フォルダ */
		String outpath = "C:\\Users\\masaaki_jinno\\Documents\\dev\\就業\\自動生成コード";
		/** ファイル名 */
		String fileName = "KRCDT_INTERIM_HD_SP_MNG 暫定特別休暇管理データ.csv";

		  // CSVファイルをArrayListに読み込み
		  BufferedReader br = null;
		  String file_name = tablepath + "\\" + fileName;
		  // データを格納するリスト
		  List<String[]> data = new ArrayList<String[]>();

		  // テーブル情報
		  TableDefInfo tableDefInfo = new TableDefInfo();

		  // カラムリスト
		  ArrayList<ColumnDefInfo> columnDefInfoList = new ArrayList<ColumnDefInfo>();

		  try {
			  File file = new File(file_name);
			  br = new BufferedReader(new FileReader(file));
			  // readLineで一行ずつ読み込む
			  String line;
			  int lineNo = 0;
			  while ((line = br.readLine()) != null) {
				  lineNo++;
				  // lineをカンマで分割し、配列リストdataに追加
				  data.add(line.split(","));
			  }

			  // リストに格納したデータを表示（データ間にスペース）
			  int rowNo = 0;
			  for (String[] line_data : data) {
				  rowNo++;
				  if ( rowNo < 12 ) { // 最初はスキップ
					  if ( rowNo == 3 ) {
						  tableDefInfo.setTableName(StringOf(line_data,2));
					  } else if ( rowNo == 4 ) {
						  tableDefInfo.setTableNameComment(StringOf(line_data,2));
					  }
					  continue;
				  }

				  if ( line_data[0]==null || line_data[0].trim().length()==0 ) {
					  // カラム名がないときは終了
					  break;
				  }

				  if ( line_data.length < 3 ) {
					  continue;
				  }

				  ColumnDefInfo columnDefInfo = new ColumnDefInfo();

				  columnDefInfo.setColumnNo(StringOf(line_data,0));
				  columnDefInfo.setColumnName(StringOf(line_data,1));
				  columnDefInfo.setColumnNameComment(StringOf(line_data,2));
				  columnDefInfo.setColumnType(StringOf(line_data,7));
				  columnDefInfo.setColumnLength(StringOf(line_data,8));
				  columnDefInfo.setDecimalLength(StringOf(line_data,9));
				  columnDefInfo.setDataLength(StringOf(line_data,16));
				  columnDefInfo.setDefaultValue(StringOf(line_data,17));
				  columnDefInfo.setNullable(StringOf(line_data,18));
				  columnDefInfo.setBiko(StringOf(line_data,19));

				  columnDefInfoList.add(columnDefInfo);
			  }
		  }
		  catch(Exception e) {
			  System.out.println(e.getMessage() + e.getLocalizedMessage());
		  }

		  // カラムリストをセット
		  tableDefInfo.setColumnInfoList(columnDefInfoList);

		  // Entityファイル作成
		  tableDefInfo.createEntityFile(outpath);

		  // デバッグ用
		  System.out.println(tableDefInfo.debugString());

		  System.out.println("終了しました!");
	}

	static private String StringOf(String[] strArray, int no) {
		if ( strArray == null ) {
			return "";
		}

		if ( strArray.length <= no ) {
			return "";
		}

		return strArray[no].toString();
	}

}