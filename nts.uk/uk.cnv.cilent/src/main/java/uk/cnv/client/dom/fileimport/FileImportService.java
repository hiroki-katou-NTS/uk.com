package uk.cnv.client.dom.fileimport;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import nts.arc.layer.app.file.storage.StoredFileInfo;
import uk.cnv.client.UkConvertProperty;
import uk.cnv.client.infra.entity.JinKaisyaM;
import uk.cnv.client.infra.entity.JmDaicyo;
import uk.cnv.client.infra.entity.JmGenAdd;
import uk.cnv.client.infra.entity.JmKihon;

public class FileImportService {

	// 顔写真
	private static final String STEREOTYPE_PERSON_PHOTO = "person_photo";
	private static final String FILE_TYPE_PERSON_PHOTO = "PROFILE_PHOTO";

	// 地図
	private static final String STEREOTYPE_PERSON_MAP = "person_map";
	private static final String FILE_TYPE_PERSON_MAP = "MAP";

	// 電子書類
	private static final String STEREOTYPE_PERSON_DOC = "person_document";
	private static final String FILE_TYPE_PERSON_DOC = "DOCUMENT";


	public boolean doWork(Require require) {
		System.out.println("ファイルの移行 -- 処理開始 --");

		List<JinKaisyaM> companies = require.getAllCompany();

		int companyCount = 1;
		for (JinKaisyaM company : companies) {
			System.out.printf("%4d/%4d 会社\r\n", companyCount, companies.size());

			List<JmKihon> employees = require.getAllEmployee(company.getCompanyCode());
			List<JmGenAdd> employeeAddress = require.getAllAddress(company.getCompanyCode());
			List<JmDaicyo> documents = require.getAllDocuments(company.getCompanyCode());

			int employeeCount = 1;
			System.out.print("  0 ％の処理が完了しました。");
			for (JmKihon employee : employees) {
				System.out.printf("\r\033 %2d", Math.round(employeeCount/employees.size()) * 100);
				try {
					uploadPersonPhoto(require, company, employee);

					Optional<JmGenAdd> address = employeeAddress.stream()
						.filter(add -> employee.getPid() == add.getPid())
						.findFirst();

					if(address.isPresent()) {
						uploadPersonMap(require, company, employee, address.get());
					}

					Optional<JmDaicyo> document = documents.stream()
						.filter(add -> employee.getPid() == add.getPid())
						.findFirst();

					if(document.isPresent()) {
						uploadDocument(require, company, employee, document.get());
					}
				}
				catch (Exception e){
					System.err.println("\r\nファイル移行に失敗しました。処理を中断します。" + e.getMessage());
					return false;
				}
				employeeCount++;
			}
			companyCount++;
		}
		System.out.print("\r\n");
		System.out.println("ファイルの移行 -- 正常終了 --");
		return true;
	}

	private void uploadPersonPhoto(Require require, JinKaisyaM company, JmKihon employee) {
		Path profilePhotofile = createPath(company.getProfilePhotePath(), employee.getProfilePhotoFileName());

		StoredFileInfo fileInfo = require.store(profilePhotofile, STEREOTYPE_PERSON_PHOTO, "");

		require.save(fileInfo, employee, FILE_TYPE_PERSON_PHOTO);
	}

	private void uploadPersonMap(Require require, JinKaisyaM company, JmKihon employee, JmGenAdd employeeAddress) {
		Path profilePhotofile = createPath(company.getMapPhotePath(),employeeAddress.getMapFileName());

		StoredFileInfo fileInfo = require.store(profilePhotofile, STEREOTYPE_PERSON_MAP, "");

		require.save(fileInfo, employee, FILE_TYPE_PERSON_MAP);

	}

	private void uploadDocument(Require require, JinKaisyaM company, JmKihon employee, JmDaicyo jmDaicyo) {
		Path profilePhotofile = createPath(company.getMapPhotePath(), jmDaicyo.getDocFileName());

		StoredFileInfo fileInfo = require.store(profilePhotofile, STEREOTYPE_PERSON_DOC, "");

		require.save(fileInfo, employee, FILE_TYPE_PERSON_DOC);
	}

	private Path createPath(String folder, String fileName) {
		URI uri = null;
		try {
			URL url = new URL(folder + "/" +fileName);
			uri = url.toURI();
		} catch (MalformedURLException | URISyntaxException e) {
			System.err.println("ファイルのパスが不正です。ファイル名=" + folder + "/" + fileName);
			e.printStackTrace();
		}

		String pathString = UkConvertProperty.getProperty("ErpWwwrootPath")
				+ "\\"
				+ uri.getPath().replaceAll("^/[^/]+/", "");
		Path path = Paths.get(pathString);
		return path;
	}

	public interface Require {
		List<JinKaisyaM> getAllCompany();
		List<JmKihon> getAllEmployee();
		List<JmKihon> getAllEmployee(int companyCode);
		List<JmDaicyo> getAllDocuments(int companyCode);
		List<JmGenAdd> getAllAddress(int companyCode);
		StoredFileInfo store(Path path, String stereotype, String type);
		void save(StoredFileInfo mapptingFile, JmKihon employee, String fileType);
	}
}
