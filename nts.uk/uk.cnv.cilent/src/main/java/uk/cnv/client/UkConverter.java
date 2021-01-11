package uk.cnv.client;

import java.nio.file.Path;
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

		// ファイル移行
		val fileImporter = new FileImportService();
		val FileImportServiceRequire = new FileImportServiceRequireImpl ();

		if (!fileImporter.doWork(FileImportServiceRequire)) {
			return;
		}

		// アカウント移行
		val accountImporter = new AccountImportService();
		val accountImportServiceRequire = new AccountImportServiceRequireImpl();
		accountImporter.doWork(accountImportServiceRequire);

		//
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
		public List<JinKaisyaM> getAllCompany() {
			return jinKaisyaRepo.getAll();
		}

		@Override
		public List<JmKihon> getAllEmployee() {
			List<JmKihon> result = jmKihonRepo.findAll();
			this.cache = result.stream()
				.collect(Collectors.groupingBy(JmKihon::getCompanyCode));
			return result;
		}

		@Override
		public List<JmKihon> getAllEmployee(int companyCode) {
			if (cache.containsKey(companyCode)) return cache.get(companyCode);

			List<JmKihon> result = jmKihonRepo.findAll();
			this.cache = result.stream()
				.collect(Collectors.groupingBy(JmKihon::getCompanyCode));

			if (cache.containsKey(companyCode)) return cache.get(companyCode);

			return new ArrayList<>();
		}

		@Override
		public void save(StoredFileInfo mapptingFile, JmKihon employee, String fileType) {
			mappingRepo.insert(mapptingFile, employee, fileType);
		}

		@Override
		public StoredFileInfo store(Path path, String stereotype, String type) {
			return uploader.store(path, stereotype, type);
		}

		@Override
		public List<JmGenAdd> getAllAddress(int companyCode) {
			return genAddRepo.findAll(companyCode);
		}

		@Override
		public List<JmDaicyo> getAllDocuments(int companyCode) {
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
		public List<JinKaisyaM> getAllCompany() {
			return jinKaisyaRepo.getAll();
		}

		@Override
		public List<JmKihon> getAllEmployee(int companyCode) {
			if (cache.containsKey(companyCode)) return cache.get(companyCode);

			List<JmKihon> result = jmKihonRepo.findAll();
			this.cache = result.stream()
				.collect(Collectors.groupingBy(JmKihon::getCompanyCode));

			if (cache.containsKey(companyCode)) return cache.get(companyCode);

			return new ArrayList<>();
		}

		@Override
		public void save(String hashedPassword, JmKihon employee, String userId) {
			mappingPasswordRepo.insert(hashedPassword, employee, userId);
		}
	}
}
