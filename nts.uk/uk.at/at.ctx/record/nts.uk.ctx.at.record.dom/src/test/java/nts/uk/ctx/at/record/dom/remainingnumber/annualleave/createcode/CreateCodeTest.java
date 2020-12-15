package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.createcode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * ファイル作成テスト
 * @author jinno
 *
 */
public class CreateCodeTest {

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
//		String packageName = "nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata";
//		/** ルートクラスのクラス名 */
//		String className = "InPeriodOfSpecialLeaveResultInfor";
//		/** ルートクラスのコメント */
//		String classComment = "特休の集計結果";

//		/** ルートクラスのパッケージ名 */
//		String packageName = "nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata";
//		/** ルートクラスのクラス名 */
//		String className = "SpecialLeaveGrantRemainingData";
//		/** ルートクラスのコメント */
//		String classComment = "休暇付与残数データ　";

//		/** ルートクラスのパッケージ名 */
//		String packageName = "nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.reserveleave";
//		/** ルートクラスのクラス名 */
//		String className = "ReserveLeave";
//		/** ルートクラスのコメント */
//		String classComment = "積立年休";

//		/** ルートクラスのパッケージ名 */
//		String packageName = "nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.reserveleave";
//		/** ルートクラスのクラス名 */
//		String className = "RsvLeaRemNumEachMonth";
//		/** ルートクラスのコメント */
//		String classComment = "積立年休月別残数データ";

//		/** ルートクラスのパッケージ名 */
//		String packageName = "nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave";
//		/** ルートクラスのクラス名 */
//		String className = "AnnualLeave";
//		/** ルートクラスのコメント */
//		String classComment = "年休";

//		/** ルートクラスのパッケージ名 */
//		String packageName = "nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave";
//		/** ルートクラスのクラス名 */
//		String className = "AnnLeaRemNumEachMonth";
//		/** ルートクラスのコメント */
//		String classComment = "年休月別残数データ";

//		/** ルートクラスのパッケージ名 */
//		String packageName = "nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday";
//		/** ルートクラスのクラス名 */
//		String className = "SpecialLeaveUseTimes";
//		/** ルートクラスのコメント */
//		String classComment = "特別休暇使用時間";

//		/** ルートクラスのパッケージ名 */
//		String packageName = "nts.uk.ctx.at.shared.dom.specialholiday.grantinformation";
//		/** ルートクラスのクラス名 */
//		String className = "FixGrantDate";
//		/** ルートクラスのコメント */
//		String classComment = "指定日付与";

//		/** ルートクラスのパッケージ名 */
//		String packageName = "nts.uk.ctx.at.shared.dom.specialholiday";
//		/** ルートクラスのクラス名 */
//		String className = "SpecialHoliday";
//		/** ルートクラスのコメント */
//		String classComment = "特別休暇";

		/** ルートクラスのパッケージ名 */
		String packageName = "nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday";
		/** ルートクラスのクラス名 */
		String className = "SpecialLeave";
		/** ルートクラスのコメント */
		String classComment = "特別休暇";

		// クラス情報管理
		ClassInfoManager classInfoManager = new ClassInfoManager(srcRootPath, packageRootPathList);

		// コード生成
		CodeGenerator codeGenerator = new CodeGenerator(classInfoManager);
		String code = codeGenerator.createCode(packageName, className, classComment);

		try{
	      File file = new File(outpath + "\\" + className + "_repository.txt");

	      if (checkBeforeWritefile(file)){
	        FileWriter filewriter = new FileWriter(file);
	        filewriter.write(code);
	        filewriter.close();
	      }else{
	        System.out.println("ファイルに書き込めません ファイル名=" + file.getAbsolutePath());
	      }
	    }catch(IOException e){
	      System.out.println(e);
	    }

		System.out.println("終了しました!");
	}

	private static boolean checkBeforeWritefile(File file){
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
