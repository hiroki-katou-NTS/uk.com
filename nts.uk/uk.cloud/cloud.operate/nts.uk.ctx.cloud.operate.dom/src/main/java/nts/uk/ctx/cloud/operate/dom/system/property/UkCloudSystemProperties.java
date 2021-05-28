package nts.uk.ctx.cloud.operate.dom.system.property;

import java.nio.file.Path;
import java.nio.file.Paths;

import nts.arc.system.ServerSystemProperties;

public class UkCloudSystemProperties {

	public static String get(String key) {
		return ServerSystemProperties.get(key);
	}

	/**
	 * 共有フォルダパス
	 * @return
	 */
	public static Path sharedFolderPath() {
		return Paths.get(get("nts.uk.cloud.sharedfolder.path"));
	}
}
