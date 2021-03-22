package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.createcode;

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
	public String createCode_repository(
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
			memberInfo.setCode_repostory(classInfoManager, all_code, "	", rootClassComment, "c");
		}

		return all_code.toString();
	}

	/**
	 * コード生成
	 * @param packageName パッケージ名
	 * @param rootClassName ルートクラス名
	 * @param rootClassComment ルートクラスコメント
	 * @return
	 */
	public String createCode_of(
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

		// メンバ変数
		StringBuilder member_list_code = new StringBuilder();
		// メンバ変数番号
		Integer member_number = 1;

		String tab = "	";

		all_code.append(tab);
		all_code.append(rootClassName);
		all_code.append(".of(");
		all_code.append(System.lineSeparator());

		// メンバ変数を順番に処理
		boolean isFirst = true;
		for( MemberInfo memberInfo : rootClassInfo.getAllMemberInfoList(classInfoManager)) {
			member_number = memberInfo.setCode_of(
					classInfoManager, all_code, tab, rootClassComment, "c", member_list_code, member_number, !isFirst);
			isFirst = false;
		}

		all_code.append(System.lineSeparator());
		all_code.append(");");
		all_code.append(System.lineSeparator());

		StringBuilder code = new StringBuilder();
		code.append(member_list_code);
		code.append(System.lineSeparator());
		code.append(all_code);


		return code.toString();
	}
}
