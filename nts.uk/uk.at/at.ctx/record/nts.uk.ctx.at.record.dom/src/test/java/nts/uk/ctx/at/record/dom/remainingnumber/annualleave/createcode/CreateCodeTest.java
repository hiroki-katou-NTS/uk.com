package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.createcode;

/**
 * ファイル作成テスト
 * @author jinno
 *
 */
public class CreateCodeTest {

	public static void main(String[] args) {

		String rootPath = "C:\\jinno\\UniversalK\\nts.uk\\uk.at\\at.ctx\\record\\nts.uk.ctx.at.record.dom\\src\\main\\java\\";
		String packageName = "nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata";
		String className = "InPeriodOfSpecialLeaveResultInfor";

		String filePath = rootPath + packageName.replace(".", "\\") + "\\" + className + ".java";

		ClassInfo classInfo= new ClassInfo();
		classInfo.readFile(filePath);

		System.out.println(classInfo.getDebugString());

	}


}
