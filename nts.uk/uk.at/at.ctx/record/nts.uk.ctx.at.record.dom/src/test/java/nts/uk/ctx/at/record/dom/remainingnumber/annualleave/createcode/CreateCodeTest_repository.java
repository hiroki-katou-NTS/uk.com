package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.createcode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

/**
 * ファイル作成テスト
 * @author jinno
 *
 */
public class CreateCodeTest_repository {

	public static void main(String[] args) {

		/** ファイル出力先フォルダ */
		String outpath = "C:\\Users\\masaaki_jinno\\Documents\\dev\\就業\\自動生成コード";


		/** ソース全体のルートパス（クラスファイルのある場所を検索するときに使用） */
		String srcRootPath = "C:\\jinno\\UniversalK\\";

		/** パッケージ単位のルートパス（クラスファイルのある場所を検索するときに使用） */
		ArrayList<String> packageRootPathList = new ArrayList<String>();
		packageRootPathList.add("nts.uk\\uk.at\\at.ctx\\shared\\nts.uk.ctx.at.shared.dom\\src\\main\\java\\");
		packageRootPathList.add("nts.uk\\uk.at\\at.ctx\\record\\nts.uk.ctx.at.record.dom\\src\\main\\java\\");


//		/** ルートクラスのパッケージ名 */
//		String packageName = "nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday";
//		/** ルートクラスのクラス名 */
//		String className = "SpecialHolidayRemainData";
//		/** ルートクラスのコメント */
//		String classComment = "特別休暇月別残数データ";

//		/** ルートクラスのパッケージ名 */
//		String packageName = "nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param";
//		/** ルートクラスのクラス名 */
//		String className = "AggrResultOfAnnualLeave";
//		/** ルートクラスのコメント */
//		String classComment = "年休の集計結果";

		/** ルートクラスのパッケージ名 */
		String packageName = "nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday";
		/** ルートクラスのクラス名 */
		String className = "SpecialLeave";
		/** ルートクラスのコメント */
		String classComment = "特別休暇";

//		/** ルートクラスのパッケージ名 */
//		String packageName = "nts.uk.ctx.at.shared.dom.specialholiday";
//		/** ルートクラスのクラス名 */
//		String className = "SpecialHoliday";
//		/** ルートクラスのコメント */
//		String classComment = "特別休暇";

//		/** ルートクラスのパッケージ名 */
//		String packageName = "nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday";
//		/** ルートクラスのクラス名 */
//		String className = "SpecialLeaveUseNumber";
//		/** ルートクラスのコメント */
//		String classComment = "特別休暇使用数";

//		/** ルートクラスのパッケージ名 */
//		String packageName = "nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave";
//		/** ルートクラスのクラス名 */
//		String className = "AnnLeaRemNumEachMonth";
//		/** ルートクラスのコメント */
//		String classComment = "年休月別残数データ";




		// リポジトリ -------------------------------------------------------------------

		// クラス情報管理
		ClassInfoManager classInfoManager = new ClassInfoManager(srcRootPath, packageRootPathList);

		// コード生成（リポジトリ）
		CodeGenerator codeGenerator = new CodeGenerator(classInfoManager);
		String code_repository = codeGenerator.createCode_repository(packageName, className, classComment);

		try{
	      File file = new File(outpath + "\\" + className + "_repository.txt");

	      if (checkBeforeWritefile(file)){
	        FileWriter filewriter = new FileWriter(file);
	        filewriter.write(code_repository);
	        filewriter.close();
	      } else {
	        System.out.println("ファイルに書き込めません ファイル名=" + file.getAbsolutePath());
	      }
	    } catch(IOException e){
	      System.out.println(e);
	    }

		// of -------------------------------------------------------------------
		try{
			Set<String> keys = classInfoManager.getClassInfoMap().keySet();
			for (int i = 0; i < keys.size(); i++) {

			    String key = keys.toArray(new String[0])[i];

			    ClassInfo classInfo = classInfoManager.getClassInfoMap().get(key);

			    if ( classInfo.isPrimitiveType()) {
			    	continue;
			    }
			    if ( classInfo.getMemberInfoList().size() == 0 ) {
			    	continue;
			    }

				// コード生成(of)
				String code_of_call = codeGenerator.createCode_of(
						classInfo.getPackage_(), classInfo.getClassName(), "");

				File file = new File(outpath + "\\" + classInfo.getClassName() + "_of.txt");

				if (checkBeforeWritefile(file)){
				    FileWriter filewriter = new FileWriter(file);
				    filewriter.write(code_of_call);
				    filewriter.close();
				} else {
				    System.out.println("ファイルに書き込めません ファイル名=" + file.getAbsolutePath());
				}
			}

	    } catch(Exception e){
	      System.out.println(e);
	    }

		try{
			Set<String> keys = classInfoManager.getClassInfoMap().keySet();
			for (int i = 0; i < keys.size(); i++) {

			    String key = keys.toArray(new String[0])[i];

			    ClassInfo classInfo = classInfoManager.getClassInfoMap().get(key);

			    if ( classInfo.isPrimitiveType()) {
			    	continue;
			    }
			    if ( classInfo.getMemberInfoList().size() == 0 ) {
			    	continue;
			    }

			    StringBuilder all_code = new StringBuilder();

				// コード生成(of)
				classInfo.setCode_of(all_code, "	");
				// コード生成(clone)
				classInfo.setCode_clone(all_code, "	");

				File file = new File(outpath + "\\" + classInfo.getClassName() + ".txt");

				if (CreateCodeTest_repository.checkBeforeWritefile(file)){
				    FileWriter filewriter = new FileWriter(file);
				    filewriter.write(all_code.toString());
				    filewriter.close();
				} else {
				    System.out.println("ファイルに書き込めません ファイル名=" + file.getAbsolutePath());
				}
			}

	    } catch(Exception e){
	      System.out.println(e);
	    }

		System.out.println("終了しました!");
	}

	public static boolean checkBeforeWritefile(File file){
	    if (file.exists()){
	      if (file.isFile() && file.canWrite()){
	        return true;
	      }
	    } else {
	    	return true;
	    }

	    return false;
	  }
}
