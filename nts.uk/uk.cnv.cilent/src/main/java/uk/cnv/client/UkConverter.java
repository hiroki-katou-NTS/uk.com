package uk.cnv.client;

import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.layer.app.file.storage.StoredFileInfo;
import uk.cnv.client.dom.JinKaisyaMRepository;
import uk.cnv.client.dom.JmKihonRepository;
//import nts.uk.shr.infra.file.storage.UkFileStorage;
import uk.cnv.client.dom.accountimport.AccountImportService;
import uk.cnv.client.dom.accountimport.MappingPasswordRepository;
import uk.cnv.client.dom.csvexport.CSVExporter;
import uk.cnv.client.dom.execute.CreateConvertDBService;
import uk.cnv.client.dom.fileimport.FileImportService;
import uk.cnv.client.dom.fileimport.JmDaicyoRepository;
import uk.cnv.client.dom.fileimport.JmGenAddRepository;
import uk.cnv.client.dom.fileimport.MappingFileIdRepository;
import uk.cnv.client.infra.entity.JinKaisyaM;
import uk.cnv.client.infra.entity.JmDaicyo;
import uk.cnv.client.infra.entity.JmGenAdd;
import uk.cnv.client.infra.entity.JmKihon;
import uk.cnv.client.infra.impls.FileUploader;
import uk.cnv.client.infra.repository.JinKaisyaMRepositoryImpl;
import uk.cnv.client.infra.repository.JmDaicyoRepositoryImpl;
import uk.cnv.client.infra.repository.JmGenAddRepositoryImpl;
import uk.cnv.client.infra.repository.JmKihonRepositoryImpl;
import uk.cnv.client.infra.repository.MappingFileIdRepositoryImpl;
import uk.cnv.client.infra.repository.MappingPasswordRepositoryImpl;

public class UkConverter {

	public UkConverter () {
	}

	public void doWork() {

		// コンバート一時環境構築
		val createConvertDBService = new CreateConvertDBService();
		if(!createConvertDBService.doWork()) {
			System.err.println("移行環境構築に失敗しました。処理を中断します。");
			return;
		}

		try{
            System.in.read();
        }catch(Exception e){
        }

		// ファイル移行
		val fileImporter = new FileImportService();
		val FileImportServiceRequire = new FileImportServiceRequireImpl();

		if (!fileImporter.doWork(FileImportServiceRequire)) {
			System.err.println("ファイル移行に失敗しました。処理を中断します。");
			return;
		}

		try{
            System.in.read();
        }catch(Exception e){
        }

		// アカウント移行
		val accountImporter = new AccountImportService();
		val accountImportServiceRequire = new AccountImportServiceRequireImpl();
		accountImporter.doWork(accountImportServiceRequire);

		try{
            System.in.read();
        }catch(Exception e){
        }

		// コンバートコード実行
		val convertCodeExcecutor = new CreateConvertDBService();
		if(!convertCodeExcecutor.doWork()) {
			System.err.println("コンバートコードの実行時にエラーが発生しました。処理を中断します");
			return;
		}

		// CSVファイルの出力
		val csvExporter = new CSVExporter();
		if(csvExporter.doWork()) {
			System.err.println("CSVのエクスポート時にエラーが発生しました。処理を中断します");
			return;
		}

		// CSVファイルの存在チェック

		// CSVファイルのインポート処理呼び出し
	}

	private class FileImportServiceRequireImpl implements FileImportService.Require{
		private JinKaisyaMRepository jinKaisyaRepo;
		private JmKihonRepository jmKihonRepo;
		private MappingFileIdRepository mappingRepo;
		private FileUploader uploader;
		private JmGenAddRepository genAddRepo;
		private JmDaicyoRepository daicyoRepo;

		private Map<Integer, List<JmKihon>> cache;

		public FileImportServiceRequireImpl() {
			cache = new HashMap<>();

			jinKaisyaRepo = new JinKaisyaMRepositoryImpl();
			jmKihonRepo = new JmKihonRepositoryImpl();
			mappingRepo = new MappingFileIdRepositoryImpl();
			uploader = new FileUploader();
			genAddRepo = new JmGenAddRepositoryImpl();
			daicyoRepo = new JmDaicyoRepositoryImpl();
		}

		@Override
		public List<JinKaisyaM> getAllCompany() throws SQLException {
			return jinKaisyaRepo.getAll();
		}

		@Override
		public List<JmKihon> getAllEmployee() throws SQLException {
			List<JmKihon> result = jmKihonRepo.findAll();
			this.cache = result.stream()
				.collect(Collectors.groupingBy(JmKihon::getCompanyCode));
			return result;
		}

		@Override
		public List<JmKihon> getAllEmployee(int companyCode) throws SQLException {
			if (cache.containsKey(companyCode)) return cache.get(companyCode);

			List<JmKihon> result = jmKihonRepo.findAll();
			this.cache = result.stream()
				.collect(Collectors.groupingBy(JmKihon::getCompanyCode));

			if (cache.containsKey(companyCode)) return cache.get(companyCode);

			return new ArrayList<>();
		}

		@Override
		public void save(StoredFileInfo mapptingFile, JmKihon employee, String fileType) throws SQLException {
			mappingRepo.insert(mapptingFile, employee, fileType);
		}

		@Override
		public StoredFileInfo store(Path path, String stereotype, String type) {
			return uploader.store(path, stereotype, type);
		}

		@Override
		public List<JmGenAdd> getAllAddress(int companyCode) throws SQLException {
			return genAddRepo.findAll(companyCode);
		}

		@Override
		public List<JmDaicyo> getAllDocuments(int companyCode) throws SQLException {
			return daicyoRepo.getAll(companyCode);
		}
	}

	private class AccountImportServiceRequireImpl implements AccountImportService.Require{
		private JinKaisyaMRepository jinKaisyaRepo;
		private JmKihonRepository jmKihonRepo;
		private MappingPasswordRepository mappingPasswordRepo;

		private Map<Integer, List<JmKihon>> cache;

		public AccountImportServiceRequireImpl() {
			cache = new HashMap<>();

			jinKaisyaRepo = new JinKaisyaMRepositoryImpl();
			jmKihonRepo = new JmKihonRepositoryImpl();
			mappingPasswordRepo = new MappingPasswordRepositoryImpl();
		}

		@Override
		public List<JinKaisyaM> getAllCompany() throws SQLException {
			return jinKaisyaRepo.getAll();
		}

		@Override
		public List<JmKihon> getAllEmployee(int companyCode) throws SQLException {
			if (cache.containsKey(companyCode)) return cache.get(companyCode);

			List<JmKihon> result = jmKihonRepo.findAll();
			this.cache = result.stream()
				.collect(Collectors.groupingBy(JmKihon::getCompanyCode));

			if (cache.containsKey(companyCode)) return cache.get(companyCode);

			return new ArrayList<>();
		}

		@Override
		public void save(String hashedPassword, JmKihon employee, String userId) throws SQLException {
			mappingPasswordRepo.insert(hashedPassword, employee, userId);
		}
	}
}
