package uk.cnv.client.dom.fileimport;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import nts.arc.layer.app.file.storage.StoredFileInfo;
import uk.cnv.client.UkConvertProperty;
import uk.cnv.client.infra.entity.JinKaisyaM;
import uk.cnv.client.infra.entity.JmKihon;

public class FileImportService {

	public boolean doWork(Require require) {
		System.out.println("ファイルの移行 -- 処理開始 --");

		List<JinKaisyaM> companies = require.getAll();

		for (JinKaisyaM company : companies) {
			List<JmKihon> employees = require.get(company.getCompanyCode());

			for (JmKihon employee : employees) {
				URI uri = null;
				try {
					URL url = new URL(
							company.getProfilePhotePath() +
							"/" +
							employee.getProfilePhotoFileName());
					uri = url.toURI();
				} catch (MalformedURLException | URISyntaxException e) {
					e.printStackTrace();
				}
				//Path profilePhotofile = Paths.get(company.getProfilePhotePath(), employee.getProfilePhotoFileName());
				String path = UkConvertProperty.getProperty("ErpWwwrootPath")
						+ "\\"
						+ uri.getPath().replaceAll("^/[^/]+/", "");
				Path profilePhotofile = Paths.get(path);

				try {
					require.store(profilePhotofile, profilePhotofile.toString(), "");

					//require.save(fileInfo, employee);
				}
				catch (Exception e){
					System.err.println("ファイル移行に失敗しました。処理を中断します。" + e.getMessage());
					return false;
				}
			}
		}

		System.out.println("ファイルの移行 -- 正常終了 --");
		return true;
	}

	public interface Require {
		List<JinKaisyaM> getAll();
		List<JmKihon> get(int companyCode);
		void save(StoredFileInfo mapptingFile, JmKihon employee);
		void store(Path path, String fileName, String type);
	}
}
