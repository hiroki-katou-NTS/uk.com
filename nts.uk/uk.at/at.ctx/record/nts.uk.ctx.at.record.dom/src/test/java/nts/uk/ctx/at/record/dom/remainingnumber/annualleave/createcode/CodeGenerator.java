package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.createcode;

import java.util.ArrayList;
import java.util.Optional;

/**
 * コード生成クラス
 * @author masaaki_jinno
 *
 */
public class CodeGenerator {

	/**
	 * コンストラクタ
	 * @param classInfoManagerIn クラス情報管理
	 */
	public CodeGenerator(ClassInfoManager classInfoManagerIn) {
		classInfoManager = classInfoManagerIn;
	}

	/**
	 * クラス情報管理
	 */
	private ClassInfoManager classInfoManager;

	/**
	 * コード生成
	 * @param packageName パッケージ名
	 * @param rootClassName ルートクラス名
	 * @param rootClassComment ルートクラスコメント
	 * @return
	 */
	public String createCode(
			String packageName,
			String rootClassName,
			String rootClassComment)
	{
		// ルートクラス情報を取得
		Optional<ClassInfo> rootClassInfoOpt = classInfoManager.getClassInfo(packageName, rootClassName);
		if (!rootClassInfoOpt.isPresent()) {
			// クラス情報が取得できないときは終了
			return "";
		}
		ClassInfo rootClassInfo = rootClassInfoOpt.get();

		// 生成したコード全て
		StringBuilder all_code = new StringBuilder();

		// メンバ変数を順番に処理
		for( MemberInfo memberInfo : rootClassInfo.getAllMemberInfoList(classInfoManager)) {
			memberInfo.setCode(classInfoManager, all_code, "	", rootClassComment, "c");
		}

		return all_code.toString();
	}
}
