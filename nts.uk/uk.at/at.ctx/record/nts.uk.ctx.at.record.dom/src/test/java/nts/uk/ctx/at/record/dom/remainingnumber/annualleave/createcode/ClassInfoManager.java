package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.createcode;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import lombok.Getter;

/**
 * クラス情報管理
 * @author masaaki_jinno
 */
@Getter
public class ClassInfoManager {

	public ClassInfoManager(
			String srcRootPath,
			ArrayList<String> pkgRootPathList) {
		sourceRootPath = srcRootPath;
		packageRootPathList = pkgRootPathList;
	}

	/**
	 * ソース全体のルートパス
	 */
	private String sourceRootPath = "";;

	/**
	 * パッケージルートパス
	 */
	private ArrayList<String> packageRootPathList;

	/**
	 * パッケージ名＋クラス名で、クラス情報を管理
	 */
	private HashMap<String, ClassInfo> classInfoMap = new HashMap<String, ClassInfo>();


	/**
	 * クラス情報を取得
	 * @param packageString パッケージ名
	 * @param className クラス名
	 */
	public Optional<ClassInfo> getClassInfo(
			String packageString,
			String className) {

		if ( className.length() == 0 ) {
			return Optional.empty();
		}

		String key = packageString + "|" + className;

		// キャッシュを検索してあればそれを返す
		ClassInfo classInfo = classInfoMap.get(key);
		if ( classInfo != null ) {
			return Optional.of(classInfo);
		}

		classInfo = new ClassInfo();

		// クラス定義ファイル名を取得
		Optional<String> filePath = getFileName(sourceRootPath, packageRootPathList, packageString, className);

		// クラス定義ファイルが見つからないとき
		if (!filePath.isPresent()) {
			classInfo.setClassName(className);
			classInfo.setErrorMessage("クラスファイルが見つかりません。→　" + className + "");
			classInfoMap.put(key, classInfo);
			return Optional.of(classInfo);
		}

		// クラス定義ファイル読み込み
		classInfo.readFile(filePath.get());
		if ( classInfo.isEmpty() ) {
			classInfoMap.put(key, classInfo);
			return Optional.empty();
		}

		classInfoMap.put(key, classInfo);

		return Optional.of(classInfo);
	}

	/**
	 * パッケージ名とクラス名をキーにして、実際のファイル名を取得
	 * @param srcRootPath ソース全体のルートパス
	 * @param pkgRootPathList パッケージのルートパス
	 * @param packageString パッケージ名
	 * @param className クラス名
	 * @return
	 */
	public Optional<String> getFileName(
			String srcRootPath,
			ArrayList<String> pkgRootPathList,
			String packageString,
			String className) {

		packageRootPathList = pkgRootPathList;

		// パッケージリストを順番に検索
		for(String pkgRootPath : pkgRootPathList) {
			String filePath = srcRootPath + pkgRootPath + packageString.replace(".", "\\") + "\\" + className + ".java";

			// ファイル存在チェック
			File file = new File(filePath);
		    if (file.exists()){
		    	return Optional.of(filePath);
		    }
		}
		return Optional.empty();
	}
}


