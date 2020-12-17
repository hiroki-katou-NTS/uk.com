package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.createcode;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;

/**
 * メンバー変数情報
 * @author jinno
 */
@Getter
@Setter
public class MemberInfo {

	/**
	 * クラスへの参照
	 */
	private ClassInfo classInfoRef;

	/**
	 * メンバー変数コメント
	 */
	private String memberNameComment = "";

	/**
	 * アクセス修飾子
	 */
	private boolean public_ = false;
	private boolean protected_ = false;
	private boolean private_ = false;
	private boolean final_ = false;
	private boolean static_ = false;

	/**
	 * Optionalかどうか
	 */
	private boolean optional = false;

	/**
	 * リストかどうか
	 */
	private boolean list = false;

	/**
	 * メンバー変数の型
	 */
	private String classType = "";

	/**
	 * メンバー変数名
	 */
	private String memberName = "";


	/**
	 * コード生成（リポジトリ用）
	 * @param classInfoManager クラス情報管理
	 * @param all_code ルートクラスから生成したコード全て
	 * @param tab タブインデント
	 * @param pre_comment このメンバ変数が定義されているクラスまでのコメント
	 * @param pre_code　このメンバ変数が定義されているクラスまでのコード
	 */
	public void setCode_repostory(
			ClassInfoManager classInfoManager,
			StringBuilder all_code,
			String tab,
			String pre_comment,
			String pre_code
			) {

		// 内側インデントタブ
		String tabInside = tab;

		// コメント
		StringBuilder sb_comment = new StringBuilder(pre_comment);
		if (0 < pre_comment.length()){
			sb_comment.append(".");
		}
		sb_comment.append(memberNameComment);

		// 関数ベース部分
		StringBuilder sb_base_code = new StringBuilder(pre_code);
		sb_base_code.append(".get");
		sb_base_code.append(this.memberName.substring(0,1).toUpperCase() + this.memberName.substring(1)); // 先頭を大文字にする
		sb_base_code.append("()");

		if ( this.isOptional() ) { // Optionalのとき
			all_code.append(tab);
			all_code.append("if (");
			all_code.append(sb_base_code);
			all_code.append(".isPresent()){");
			all_code.append(System.lineSeparator());

			sb_base_code.append(".get()");
			tabInside = tab + "	";
		}

		// クラス情報を取得
		// パッケージ名取得
		String packageString = this.getClassInfoRef().getPackageName(this.getClassType());
		Optional<ClassInfo> classInfoOpt = classInfoManager.getClassInfo(packageString, this.getClassType());

		// Javaプリミティブ型またはリストのとき
		if (ClassInfo.isJavaPrimitiveType(classType)
				|| this.isList()) {

			// コメント追加
			all_code.append(tabInside);
			all_code.append("/** ");
			all_code.append(sb_comment.toString());
			all_code.append(" */");
			all_code.append(System.lineSeparator());

			// コード追加
			all_code.append(tabInside);
			all_code.append(sb_base_code);
			all_code.append(";");
			if (this.isList()) { // リストのとき
				all_code.append(System.lineSeparator());
				all_code.append(tabInside);
				all_code.append("// !!リストの対応が必要");
			}
			all_code.append(System.lineSeparator());
			all_code.append(System.lineSeparator());

			// Javaプリミティブ型まできたら終了
			return;
		}

		// Ntsプリミティブ型
		if ( classInfoOpt.isPresent()
				&& classInfoOpt.get().isPrimitiveType()) { // Javaプリミティブ型より後に評価する
//		if (ClassInfo.isNtsPrimitiveType(classType)) {

			// コメント追加
			all_code.append(tabInside);
			all_code.append("/** ");
			all_code.append(sb_comment.toString());
			all_code.append(" */");
			all_code.append(System.lineSeparator());

			// コード追加
			all_code.append(tabInside);
			all_code.append(sb_base_code);
			all_code.append(".v();");
			all_code.append(System.lineSeparator());
			all_code.append(System.lineSeparator());

			// Javaプリミティブ型まできたら終了
			return;
		}

		// その他（クラス）
		if (classInfoOpt.isPresent()) { // クラス情報があるとき
			ClassInfo classInfo = classInfoOpt.get();

			if ( classInfo.getMemberInfoList().size() == 0 ) {
				// コメント追加
				all_code.append(tabInside);
				all_code.append("/** ");
				all_code.append(sb_comment.toString());
				all_code.append(" */");
				all_code.append(System.lineSeparator());

				// コード追加
				all_code.append(tabInside);
				all_code.append(sb_base_code);
				all_code.append(";");
				all_code.append(System.lineSeparator());

				// エラーがあるとき
				if (0 < classInfo.getErrorMessage().length()) {
					all_code.append(tabInside);
					all_code.append("// !!");
					all_code.append(classInfo.getErrorMessage());
					all_code.append(System.lineSeparator());
					all_code.append(System.lineSeparator());
				}

			} else {
				//　再帰処理
				for( MemberInfo memberInfo : classInfo.getMemberInfoList() ) {
					memberInfo.setCode_repostory(classInfoManager, all_code, tabInside, sb_comment.toString(), sb_base_code.toString());
				}
			}

		} else { // クラス情報がないとき

			// コメント追加
			all_code.append(tabInside);
			all_code.append("/** ");
			all_code.append(sb_comment.toString());
			all_code.append(" */");
			all_code.append(System.lineSeparator());

			// コード追加
			all_code.append(tabInside);
			all_code.append(sb_base_code);
			all_code.append(";");
			all_code.append(System.lineSeparator());
			all_code.append(tabInside);
			all_code.append("// !!クラス情報がありません");
			all_code.append(System.lineSeparator());
			all_code.append(System.lineSeparator());

			// 終了
			return;
		}

		if ( this.isOptional() ) { // Optionalのとき
			all_code.append(tab);
			all_code.append("}");
			all_code.append(System.lineSeparator());
			all_code.append(System.lineSeparator());
		}
	}

	/**
	 * コード生成（of）
	 * @param classInfoManager クラス情報管理
	 * @param all_code ルートクラスから生成したコード全て
	 * @param tab タブインデント
	 * @param pre_comment このメンバ変数が定義されているクラスまでのコメント
	 * @param pre_code　このメンバ変数が定義されているクラスまでのコード
	 */
	public void setCode_of(
			ClassInfoManager classInfoManager,
			StringBuilder all_code,
			String tab,
			String pre_comment,
			String pre_code
			) {

		// 内側インデントタブ
		String tabInside = tab + "	";

		// コメント
		StringBuilder sb_comment = new StringBuilder(pre_comment);
		if (0 < pre_comment.length()){
			sb_comment.append(".");
		}
		sb_comment.append(memberNameComment);

		// コメント追加
		all_code.append(tabInside);
		all_code.append("/** ");
		all_code.append(sb_comment.toString());
		all_code.append(" */");
		all_code.append(System.lineSeparator());


		// クラス情報を取得
		// パッケージ名取得
		String packageString = this.getClassInfoRef().getPackageName(this.getClassType());
		Optional<ClassInfo> classInfoOpt = classInfoManager.getClassInfo(packageString, this.getClassType());

		// Javaプリミティブ型またはリストのとき
		if (ClassInfo.isJavaPrimitiveType(classType)
				|| this.isList()) {

			// コード追加
			all_code.append(tabInside);
			if ( this.isOptional() ) { // Optionalのとき
				all_code.append(tabInside);
				all_code.append("Optional.of(");
			}
			all_code.append("// ");
			all_code.append(classType);
			all_code.append(" ");
			all_code.append(this.getMemberName());
			if (this.isList()) { // リストのとき
				all_code.append("// !!リストの対応が必要");
			}
			all_code.append(System.lineSeparator());
			all_code.append(System.lineSeparator());

			// Javaプリミティブ型まできたら終了
			return;
		}

		// Ntsプリミティブ型
		if ( classInfoOpt.isPresent()
				&& classInfoOpt.get().isPrimitiveType()) { // Javaプリミティブ型より後に評価する

			// コード追加
			all_code.append(tabInside);
			if ( this.isOptional() ) { // Optionalのとき
				all_code.append(tabInside);
				all_code.append("Optional.of(");
			}
			all_code.append("// new ");
			all_code.append(classType);
			all_code.append("( XXXXX )");
			all_code.append(this.getMemberName());
			if (this.isList()) { // リストのとき
				all_code.append("// !!リストの対応が必要");
			}
			all_code.append(System.lineSeparator());
			all_code.append(System.lineSeparator());

			// Javaプリミティブ型まできたら終了
			return;
		}

		// その他（クラス）
		if (classInfoOpt.isPresent()) { // クラス情報があるとき
			ClassInfo classInfo = classInfoOpt.get();

			all_code.append(tabInside);
			if ( this.isOptional() ) { // Optionalのとき
				all_code.append(tabInside);
				all_code.append("Optional.of(");
			}

			// メンバ変数がないとき
			if ( classInfo.getMemberInfoList().size() == 0 ) {
				all_code.append("/** new ");
				all_code.append(classType);
				all_code.append("( XXXXX ) */");

				// エラーがあるとき
				if (0 < classInfo.getErrorMessage().length()) {
					all_code.append(tabInside);
					all_code.append("// !!");
					all_code.append(classInfo.getErrorMessage());
					all_code.append(System.lineSeparator());
					all_code.append(System.lineSeparator());
				}

			} else {
				all_code.append(classType);
				all_code.append(".of(");
				all_code.append(System.lineSeparator());

				//　再帰処理
				for( MemberInfo memberInfo : classInfo.getMemberInfoList() ) {
					memberInfo.setCode_of(classInfoManager, all_code, tabInside, sb_comment.toString(), "");
				}

				all_code.append(System.lineSeparator());
				all_code.append(tabInside);
				all_code.append(")");
			}

		} else { // クラス情報がないとき

			// コード追加
			all_code.append(tabInside);
			if ( this.isOptional() ) { // Optionalのとき
				all_code.append(tabInside);
				all_code.append("Optional.of(");
			}
//			all_code.append("/** ");
//			all_code.append(classType);
//			all_code.append(" */");

			all_code.append("// new ");
			all_code.append(classType);
			all_code.append("( XXXXX )");

			if (this.isList()) { // リストのとき
				all_code.append("// !!リストの対応が必要");
			}
			all_code.append(System.lineSeparator());
			all_code.append(tabInside);
			all_code.append("// !!クラス情報がありません");
			all_code.append(System.lineSeparator());
			all_code.append(System.lineSeparator());

			// 終了
			return;
		}

		all_code.append(tab);
		all_code.append(")");
		all_code.append(System.lineSeparator());

		if ( this.isOptional() ) { // Optionalのとき
			all_code.append(tabInside);
			all_code.append(")");
			all_code.append(System.lineSeparator());
			all_code.append(System.lineSeparator());
		}
	}

	/**
	 * デバッグ用
	 * @param sb
	 */
	public void setDebugString(StringBuilder sb) {

		sb.append("memberNameComment = '");
		sb.append(memberNameComment);
		sb.append("'");
		sb.append(System.lineSeparator());

		sb.append("memberType = '");
		sb.append(classType);
		sb.append("'");
		sb.append(System.lineSeparator());

		sb.append("memberName = '");
		sb.append(memberName);
		sb.append("'");
		sb.append(System.lineSeparator());

	}

}
