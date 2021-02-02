package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.createcode;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

	/**
	 * Entityファイル作成
	 * @param outpath
	 */
	public void createEntityFile(String outpath) {

		StringBuilder sb = new StringBuilder();

		sb.append("import lombok.AllArgsConstructor;\r\n" +
				"import lombok.Getter;\r\n" +
				"import lombok.NoArgsConstructor;\r\n" +
				"import lombok.Setter;\r\n" +
				"import nts.arc.time.GeneralDate;\r\n" +
				"import nts.uk.shr.infra.data.entity.UkJpaEntity;\r\n" +
				"/**\r\n" +
				" * " + this.getTableNameComment() + "\r\n" +
				" *\r\n" +
				" */\r\n" +
				"@Entity\r\n" +
				"@AllArgsConstructor\r\n" +
				"@NoArgsConstructor\r\n" +
				"@Getter\r\n" +
				"@Setter\r\n" +
				"@Table(name = \"" + this.getTableName() + "\")\r\n" +
				"public class KrcdtInterimHdstk extends UkJpaEntity{\r\n" +
				"");

//		// メンバ変数を順番に処理
//		boolean isFirst = true;
//		for( MemberInfo memberInfo : rootClassInfo.getAllMemberInfoList(classInfoManager)) {
//			member_number = memberInfo.setCode_of(
//					classInfoManager, all_code, tab, rootClassComment, "c", member_list_code, member_number, !isFirst);
//			isFirst = false;
//		}

		try{

			File file = new File(outpath + "\\" + this.getTableName() + ".txt");

			if (CreateCodeTest_repository.checkBeforeWritefile(file)){
			    FileWriter filewriter = new FileWriter(file);
			    filewriter.write(sb.toString());
			    filewriter.close();
			} else {
			    System.out.println("ファイルに書き込めません ファイル名=" + file.getAbsolutePath());
			}

	    } catch(Exception e){
	      System.out.println(e);
	    }

		System.out.println("終了しました!");
	}

	/**
	 * デバッグ用
	 * @return
	 */
	public String debugString() {

		StringBuilder sb = new StringBuilder();

		sb.append("tableNameComment='");
		sb.append(tableNameComment);
		sb.append("'");
		sb.append(System.lineSeparator());

		sb.append("tableName='");
		sb.append(tableName);
		sb.append("'");
		sb.append(System.lineSeparator());




		return sb.toString();
	}

}



