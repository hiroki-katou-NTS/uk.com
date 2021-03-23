package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.createcode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveRemainingDetail;

/**
 * クラス情報
 * @author jinno
 */
@Getter
@Setter
public class ClassInfo {

	/**
	 * パッケージ
	 */
	private String package_ = "";

	/**
	 * importリスト
	 */
	private ArrayList<String> importList = new ArrayList<String>();

	/**
	 * クラスコメント
	 */
	private String classNameComment = "";

	/**
	 * クラス名
	 */
	private String className = "";

	public boolean isEmpty() {
		return className==null;
	}

	/**
	 * 親クラス
	 */
	private String extendsName = "";

	/**
	 * メンバー変数リスト
	 */
	private List<MemberInfo> memberInfoList = new ArrayList<MemberInfo>();

	/** エラーエラーメッセージ */
	private String errorMessage = "";

	/**
	 * プリミティブ型か?(Java、Nts)
	 * @param classType
	 * @return
	 */
	public boolean isPrimitiveType() {

		boolean status = isJavaPrimitiveType(this.getClassName())
				|| isNtsPrimitiveType(this.getExtendsName());

		return status;
	}

	/**
	 * プリミティブ型か?(Java)
	 * @param classType
	 * @return
	 */
	static public boolean isJavaPrimitiveType(String classType) {
		if ( classType.equals("String")
				|| classType.equals("boolean") || classType.equals("Boolean")
				|| classType.equals("byte") || classType.equals("Byte")
				|| classType.equals("char") || classType.equals("Character")
				|| classType.equals("short") || classType.equals("Short")
				|| classType.equals("int") || classType.equals("Integer")
				|| classType.equals("float") || classType.equals("Float")
				|| classType.equals("long") || classType.equals("Long")
				|| classType.equals("double") || classType.equals("Double")
				)
		{
			return true;
		}

		return false;
	}

	/**
	 * プリミティブ型か?(Nts)
	 * @param classType
	 * @return
	 */
	static public boolean isNtsPrimitiveType(String classType) {
		if ( classType == null || classType.length() == 0 ) {
			return false;
		}
		if ( classType.equals("ComparablePrimitiveValue")
				|| classType.equals("DecimalPrimitiveValue")
				|| classType.equals("HalfIntegerPrimitiveValue")
				|| classType.equals("IntegerPrimitiveValue")
				|| classType.equals("KanaPrimitiveValue")
				|| classType.equals("LongPrimitiveValue")
				|| classType.equals("PrimitiveValue")
				|| classType.equals("PrimitiveValueUtil")
				|| classType.equals("StringPrimitiveValue")
				|| classType.equals("TimeAsMinutesPrimitiveValue")
				|| classType.equals("TimeAsSecondsPrimitiveValue")
				|| classType.equals("TimeClockPrimitiveValue")
				|| classType.equals("TimeDurationPrimitiveValue")
				|| classType.equals("UpperCaseAlphaNumericPrimitiveValue")
				)
		{
			return true;
		}

		return false;
	}

	/**
	 * メンバ変数の型を指定して、パッケージ名を取得する
	 * @param className
	 * @return
	 */
	public String getPackageName(String className) {

		// importのリストを順番に検索
		String s = "." + className + ";";
		for(String importString : importList) {
			if (importString.contains(s)) {
				return importString.replace(s, "");
			}
		}

		// importのリストにないときには、クラスのパッケージを返す
		return this.package_;
	}

	/**
	 * 全メンバ変数リストを取得（継承クラスのメンバを先頭に追加する）
	 * @param classInfoManager
	 * @return
	 */
	public List<MemberInfo> getAllMemberInfoList(
			ClassInfoManager classInfoManager ){

		List<MemberInfo> list = new ArrayList<MemberInfo>();
		this.addAllMemberInfoList(classInfoManager, list);

		return list;
	}

	private void addAllMemberInfoList(
			ClassInfoManager classInfoManager,
			List<MemberInfo> memberInfoList
			){

		// 継承先
		if ( 0 < this.extendsName.length() ) {
			String packageName = this.getPackageName(this.extendsName);
			if ( 0 < packageName.length() ) {
				Optional<ClassInfo> classInfoOpt = classInfoManager.getClassInfo(packageName, this.extendsName);
				if ( classInfoOpt.isPresent() ) {
					classInfoOpt.get().addAllMemberInfoList(classInfoManager, memberInfoList);
				}
			}
		}

		// クラスのメンバ変数を追加
		memberInfoList.addAll(this.memberInfoList);
	}

	/**
	 * コード生成（of）
	 * @param all_code ルートクラスから生成したコード全て
	 * @param tab タブインデント
	 */
	public void setCode_of(
			StringBuilder all_code,
			String tab
			) {

		// 内側インデントタブ
		String tabInside = tab + "	";

		all_code.append(tab);
		all_code.append("static public ");
		all_code.append(this.getClassName());
		all_code.append(" of(");
		all_code.append(System.lineSeparator());


		// 引数
		boolean isFirst = true;
		for(MemberInfo memberInfo : memberInfoList) {
			all_code.append(tabInside);
			all_code.append("/** ");
			all_code.append(memberInfo.getMemberNameComment());
			all_code.append(" */");
			all_code.append(System.lineSeparator());

			all_code.append(tabInside);
			if ( isFirst ) {
				isFirst = false;
			} else {
				all_code.append(", ");
			}

			if (memberInfo.isOptional()) {
				all_code.append("Optional<");
			}
			if (memberInfo.isList()) {
				all_code.append("List<");
			}
			all_code.append(memberInfo.getClassType());
			if (memberInfo.isList()) {
				all_code.append(">");
			}
			if (memberInfo.isOptional()) {
				all_code.append(">");
			}
			all_code.append(" ");

			all_code.append(memberInfo.getMemberName());
			all_code.append(System.lineSeparator());
		}

		all_code.append(tab);
		all_code.append("){");
		all_code.append(System.lineSeparator());

		all_code.append(tabInside);
		all_code.append(this.getClassName());
		all_code.append(" c = new ");
		all_code.append(this.getClassName());
		all_code.append("();");
		all_code.append(System.lineSeparator());

		// セット
		for(MemberInfo memberInfo : memberInfoList) {
			all_code.append(tabInside);
			all_code.append("/** ");
			all_code.append(memberInfo.getMemberNameComment());
			all_code.append(" */");
			all_code.append(System.lineSeparator());

			all_code.append(tabInside);
			all_code.append("c.");
			all_code.append(memberInfo.getMemberName());
			all_code.append("=");
			all_code.append(memberInfo.getMemberName());
			all_code.append(";");
			all_code.append(System.lineSeparator());
		}

		all_code.append(System.lineSeparator());
		all_code.append(tabInside);
		all_code.append("return c; ");
		all_code.append(System.lineSeparator());

		all_code.append(tab);
		all_code.append("}");
		all_code.append(System.lineSeparator());
		all_code.append(System.lineSeparator());
	}

	/**
	 * コード生成（clone）
	 * @param all_code ルートクラスから生成したコード全て
	 * @param tab タブインデント
	 */
	public void setCode_clone(
			StringBuilder all_code,
			String tab
			) {

		// 内側インデントタブ
		String tabInside = tab + "	";

		all_code.append(tab);
		all_code.append("static public ");
		all_code.append(this.getClassName());
		all_code.append(" clone(){");
		all_code.append(System.lineSeparator());


		// 引数
		boolean isFirst = true;

		all_code.append(tabInside);
		all_code.append(this.getClassName());
		all_code.append(" cloned = new ");
		all_code.append(this.getClassName());
		all_code.append("();");
		all_code.append(System.lineSeparator());

		for(MemberInfo memberInfo : memberInfoList) {
			all_code.append(tabInside);
			all_code.append("/** ");
			all_code.append(memberInfo.getMemberNameComment());
			all_code.append(" */");
			all_code.append(System.lineSeparator());

			if (memberInfo.isOptional() && memberInfo.isList()) {
				all_code.append(tabInside);
				all_code.append("if ( this.");
				all_code.append(memberInfo.getMemberName());
				all_code.append(".isPresent() ) {");
				all_code.append(System.lineSeparator());

				String tabInside2 = tabInside + "	";
				String tabInside3 = tabInside2 + "	";

				all_code.append(tabInside2);
				all_code.append("cloned.");
				all_code.append(memberInfo.getMemberName());
				all_code.append(" = Optional.empty();");
				all_code.append(System.lineSeparator());
				all_code.append(tabInside);
				all_code.append("} else {");
				all_code.append(System.lineSeparator());
				all_code.append(tabInside2);
				all_code.append("ArrayList<");
				all_code.append(memberInfo.getClassType());
				all_code.append("> ");
				all_code.append(memberInfo.getMemberName());
				all_code.append(" = new ArrayList<");
				all_code.append(memberInfo.getClassType());
				all_code.append(">();");
				all_code.append(System.lineSeparator());
				all_code.append(tabInside2);
				all_code.append("for(");
				all_code.append(memberInfo.getClassType());
				all_code.append(" c : this.");
				all_code.append(memberInfo.getMemberName());
				all_code.append(".get()) {");
				all_code.append(System.lineSeparator());
				all_code.append(tabInside3);
				all_code.append(memberInfo.getMemberName());
				all_code.append(".add(c.clone());");
				all_code.append(System.lineSeparator());
				all_code.append(tabInside2);
				all_code.append("}");
				all_code.append(System.lineSeparator());
				all_code.append(tabInside2);
				all_code.append("cloned.");
				all_code.append(memberInfo.getMemberName());
				all_code.append(" = Optional.of(");
				all_code.append(memberInfo.getMemberName());
				all_code.append(");");
				all_code.append(System.lineSeparator());
				all_code.append(tabInside);
				all_code.append("}");
				all_code.append(System.lineSeparator());

			} else if (memberInfo.isOptional()) {
				all_code.append(tabInside);
				all_code.append("if ( this.");
				all_code.append(memberInfo.getMemberName());
				all_code.append(".isPresent() ) {");
				all_code.append(System.lineSeparator());
				String tabInside2 = tabInside + "	";
				all_code.append(tabInside2);
				all_code.append("cloned.");
				all_code.append(memberInfo.getMemberName());
				all_code.append(" = Optional.empty();");
				all_code.append(System.lineSeparator());
				all_code.append(tabInside);
				all_code.append("} else {");
				all_code.append(System.lineSeparator());
				all_code.append(tabInside2);
				all_code.append("cloned.");
				all_code.append(memberInfo.getMemberName());
				all_code.append(" = Optional.of(");
				all_code.append(memberInfo.getMemberName());
				all_code.append(".clone());");
				all_code.append(System.lineSeparator());
				all_code.append(tabInside);
				all_code.append("}");
				all_code.append(System.lineSeparator());

			} if (memberInfo.isList()) {
				String tabInside2 = tabInside + "	";
				String tabInside3 = tabInside2 + "	";
				all_code.append("ArrayList<");
				all_code.append(memberInfo.getClassType());
				all_code.append("> ");
				all_code.append(memberInfo.getMemberName());
				all_code.append(" = new ArrayList<");
				all_code.append(memberInfo.getClassType());
				all_code.append(">();");
				all_code.append(System.lineSeparator());
				all_code.append(tabInside2);
				all_code.append("for(");
				all_code.append(memberInfo.getClassType());
				all_code.append(" c : this.");
				all_code.append(memberInfo.getMemberName());
				all_code.append(") {");
				all_code.append(System.lineSeparator());
				all_code.append(tabInside3);
				all_code.append(memberInfo.getMemberName());
				all_code.append(".add(c.clone());");
				all_code.append(System.lineSeparator());
				all_code.append(tabInside2);
				all_code.append("}");
				all_code.append(System.lineSeparator());
				all_code.append(tabInside2);
				all_code.append("cloned.");
				all_code.append(memberInfo.getMemberName());
				all_code.append(" = ");
				all_code.append(memberInfo.getMemberName());
				all_code.append(";");
				all_code.append(System.lineSeparator());
				all_code.append(tabInside);
				all_code.append("}");
				all_code.append(System.lineSeparator());
			} else {
				all_code.append(tabInside);
				all_code.append("cloned.");
				all_code.append(memberInfo.getMemberName());
				all_code.append(" = ");
				all_code.append(memberInfo.getMemberName());
				all_code.append(".clone();");
				all_code.append(System.lineSeparator());
				all_code.append(tabInside);
			}

			all_code.append(System.lineSeparator());
		}

		all_code.append(tabInside);
		all_code.append("return cloned; ");
		all_code.append(System.lineSeparator());

		all_code.append(tab);
		all_code.append("}");

	}

	/**
	 * デバッグ用
	 * @param sb
	 */
	protected void setDebugString(StringBuilder sb) {

		sb.append("pakage = '");
		sb.append(package_);
		sb.append(System.lineSeparator());

		sb.append("import ---------------------------------------------------");
		sb.append(System.lineSeparator());
		for( String str : importList) {
			sb.append(str);
			sb.append(System.lineSeparator());
		}

		sb.append("class ---------------------------------------------------");
		sb.append(System.lineSeparator());
		sb.append("classNameComment = '");
		sb.append(classNameComment);
		sb.append("'");
		sb.append(System.lineSeparator());

		sb.append("className = '");
		sb.append(className);
		sb.append("'");
		sb.append(System.lineSeparator());

		sb.append("extendsName = '");
		sb.append(extendsName);
		sb.append("'");
		sb.append(System.lineSeparator());

		sb.append("member ---------------------------------------------------");
		sb.append(System.lineSeparator());
		for(MemberInfo memberInfo : memberInfoList) {
			memberInfo.setDebugString(sb);
			sb.append(System.lineSeparator());
		}

	}

	public String getDebugString() {
		StringBuilder sb = new StringBuilder();
		this.setDebugString(sb);
		return sb.toString();
	}

	/**
	 * クラスファイルを読み込んで、クラス情報を取得
	 * @param filePath
	 */
	public void readFile(String filePath) {

		try {

			File file = new File(filePath);
			  FileReader filereader = new FileReader(file);
			  BufferedReader br = new BufferedReader(filereader);

			  String str;

			  // パッケージ  ----------------------------------------
			  str = br.readLine();
			  int beginIndex_package = -1;

			  while(str != null){

				  beginIndex_package = str.indexOf("package");
				  if ( beginIndex_package < 0 ) {
					  str = br.readLine();
					  continue;
				  }

				  int endIndex_package = str.indexOf(";");
				  String result = str.substring(beginIndex_package + 8, endIndex_package).trim();
				  this.setPackage_(result);
				  break;

			  }

			  // コメント
			  int  beginIndex_comment = -1;
			  String comment = "";

			  // import  ----------------------------------------
			  str = br.readLine();
			  int beginIndex_import = -1;
			  int beginIndex_class = -1;
			  int beginIndex_extends = -1;
			  int beginIndex_implements = -1;
			  ArrayList<String> importList = new ArrayList<String>();

			  while(str != null){

				  // コメントチェック
				  beginIndex_comment = str.indexOf("/*");
				  if (0 <= beginIndex_comment ) { // コメント開始があるとき
					  CommentResult getCommentResult = getComment(br, str);
					  comment = getCommentResult.getComment();

					  // コメント前に文字列があるとき
					  if (3 < getCommentResult.getBeforeComment().length() ) {
						  str = getCommentResult.getBeforeComment();
					  } else {
						// コメント後に文字列があるときは一旦無視して先に進む
						  str = br.readLine();
					  }
					  beginIndex_comment = -1;
				  }

				  if ( str==null ) {
					  break;
				  }

				  beginIndex_import = str.indexOf("import ");
				  if ( 0 <= beginIndex_import ) {
					  // int endIndex_import = str.indexOf(";");
					  String result = str.substring(beginIndex_import+ 6).trim();
					  importList.add(result);
				  }

				  beginIndex_class = str.indexOf("class ");
				  if ( 0 <= beginIndex_class ) {
					  this.setImportList(importList);
					  break;
				  }
				  str = br.readLine();
			  }

			  // クラス  ----------------------------------------
			  while(str != null){

				  // コメントチェック
				  beginIndex_comment = str.indexOf("/*");
				  if (0 <= beginIndex_comment ) { // コメント開始があるとき
					  CommentResult commentResult = getComment(br, str);
					  comment = commentResult.getComment();

					  // コメント前に文字列があるとき
					  if (3 < commentResult.getBeforeComment().length() ) {
						  str = commentResult.getBeforeComment();
					  } else {
						// コメント後に文字列があるときは一旦無視して先に進む
						  str = br.readLine();
					  }
					  beginIndex_comment = -1;
				  }

				  beginIndex_class = str.indexOf("class ");
				  beginIndex_extends = str.indexOf(" extends ");




				  // class があるとき
				  if (0 <= beginIndex_class) {
					 String className = str.substring(beginIndex_class)
							 .replace("class ", "").replace("{", "").replace(" extends ", "").trim();

					 beginIndex_implements = className.indexOf(" implements ");
					 if ( 0 < beginIndex_implements ) {
						 className = className.substring(0, beginIndex_implements);
					 }


					 this.setClassName(className);

					 comment = comment.replace("/*", "").replace("*", "").trim();
					 this.setClassNameComment(comment);

					// extends があるとき
					if (0 <= beginIndex_extends) {
						  className = str.substring(beginIndex_class, beginIndex_extends)
								.replace("class ", "").replace("{", "").replace(" extends ", "").trim();
						  this.setClassName(className);

						  String extendsName = str.substring(beginIndex_extends)
								.replace("class ", "").replace("{", "").replace(" extends ", "").trim();
						  this.setExtendsName(extendsName);
					 }

					 comment = "";
					 str = br.readLine();
					 break;
				  }

				  // class があるまでループを抜けない
				  str = br.readLine();
			  }

			  // メンバー変数 ----------------------------------------
			  memberInfoList = new ArrayList<MemberInfo>();
			  comment = "";
			  while(str != null){

				  // コメントチェック１
				  beginIndex_comment = str.indexOf("/*");
				  if (0 <= beginIndex_comment ) { // コメント開始があるとき
					  CommentResult commentResult = getComment(br, str);
					  comment = commentResult.getComment();

					  // コメント前に文字列があるとき
					  if (3 < commentResult.getBeforeComment().length() ) {
						  str = commentResult.getBeforeComment();
					  } else {
						// コメント後に文字列があるときは一旦無視して先に進む
						  str = br.readLine();
					  }
					  beginIndex_comment = -1;
				  }
				// コメントチェック２
				  beginIndex_comment = str.indexOf("//");
				  if (0 <= beginIndex_comment ) { // コメント開始があるとき
					  comment = str.substring(beginIndex_comment).replace("//", "").trim();

					  // コメント前に文字列があるとき
					  if (3 < beginIndex_comment ) {
						  String beforeComment = str.substring(0, beginIndex_comment-1).trim();
						  str = beforeComment;
					  } else {
						// コメント後に文字列があるときは一旦無視して先に進む
						  str = br.readLine();
					  }
					  beginIndex_comment = -1;
				  }

				  if ( str != null ) {
					  if ( str.contains("private ") || str.contains("protected ") ||  str.contains("public ")  ) {

						  if ( !str.contains("{ ") && !str.contains("(") ) { // 関数は除く

							  MemberInfo memberInfo = new MemberInfo();
							  memberInfo.setClassInfoRef(this);

							  String memberString = str;
							  if ( str.contains("private ") ) {
								  memberInfo.setPrivate_(true);
								  memberString = memberString.replace("private ", "");
							  }
							  if ( str.contains("protected ") ) {
								  memberInfo.setProtected_(true);
								  memberString = memberString.replace("protected ", "");
							  }
							  if ( str.contains("public ") ) {
								  memberInfo.setPublic_(true);
								  memberString = memberString.replace("public ", "");
							  }
							  if ( str.contains("final ") ) {
								  memberInfo.setFinal_(true);
								  memberString = memberString.replace("final ", "");
							  }
							  if ( str.contains("static ") ) {
								  memberInfo.setStatic_(true);
								  memberString = memberString.replace("static ", "");
							  }

							  // 型
							  int beginIndexSpace = memberString.indexOf(" ");
							  if ( 0 < beginIndexSpace) {
								  String memberType = memberString.substring(0, beginIndexSpace).trim();
								  if ( memberType.contains("Optional<")) {
									  memberInfo.setOptional(true);
									  memberType = memberType.replace("Optional<", "").replace(">","");
								  }
								  if ( memberType.contains("List<")) {
									  memberInfo.setList(true);
									  memberType = memberType.replace("List<", "").replace(">","");
								  }

								  memberInfo.setClassType(memberType);
								  memberString = memberString.substring(beginIndexSpace).trim();
							  }

							  // 名前
							  int beginIndexEqual = memberString.indexOf("=");
							  int beginIndexEnd = memberString.indexOf(";");
							  if ( 0 < beginIndexEqual) {
								  String memberName = memberString.substring(0, beginIndexEqual).trim();
								  memberInfo.setMemberName(memberName);
							  } else if ( 0 < beginIndexEnd) {
								  String memberName = memberString.substring(0, beginIndexEnd).replace(";", "").trim();
								  memberInfo.setMemberName(memberName);
							  }

							  // コメント
							  comment = comment.replace("/*", "").replace("*", "").trim();
							  memberInfo.setMemberNameComment(comment);
							  comment = "";

							  if (!memberInfo.getMemberName().equals("serialVersionUID")) { // 一部のメンバ変数名を除く
								  memberInfoList.add(memberInfo);
							  }
						  }
					  }
				  }

				  //  最後まで
				  str = br.readLine();
			  }

			  this.setMemberInfoList(memberInfoList);

			  br.close();

			} catch(FileNotFoundException e) {
				System.out.println(e);
			} catch(IOException e) {
				System.out.println(e);
			}

		}

		/**
		 * コメント解析
		 *
		 * @param br
		 * @param str
		 * @return
		 * @throws IOException
		 */
		private CommentResult getComment(BufferedReader br, String str) throws IOException {

			CommentResult commentResult = new CommentResult();

			// コメント
			  StringBuilder  sb_comment = new StringBuilder();
			  String comment = "";
			  int  beginIndex_comment = -1;
			  int  endIndex_comment = -1;
			  boolean isCommentMultiLine = false;

			  while(str != null){

				  // 新規コメントチェック
				  if (beginIndex_comment < 0) {
					  beginIndex_comment = str.indexOf("/*");
					  if ( 0 < beginIndex_comment ) {
						  commentResult.setBeforeComment(str.substring(0,beginIndex_comment ));
					  }
				  }

				 // 新規コメントか複数行コメントのとき
				  if (0 <= beginIndex_comment || isCommentMultiLine) {

					  endIndex_comment = str.indexOf("*/");
					  if ( 0 <= endIndex_comment ) { // コメント終了があるとき

						  // コメント取得
						  if ( isCommentMultiLine ) {
							  sb_comment.append( str.substring(0, endIndex_comment));
							  comment = sb_comment.toString();
						  } else {
							  comment = str.substring(beginIndex_comment, endIndex_comment);
						  }
						  commentResult.setComment(comment);
						  commentResult.setAfterComment(str.substring(endIndex_comment));

						 return commentResult;

					  } else { // コメント終了がないとき

						  // コメント追加
						  if ( isCommentMultiLine) {
							  sb_comment.append(str);
						  } else {
							  sb_comment.append(str.substring(beginIndex_comment));
						  }
						  isCommentMultiLine = true;

						  str = br.readLine(); // コメントの終了がないときは、次の行へ
						  continue;
					  }
				  }
				  str = br.readLine();
			  }
			  return commentResult;
		}

}


