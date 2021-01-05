package uk.cnv.client;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import nts.arc.layer.app.file.storage.StoredFileInfo;
//import nts.uk.shr.infra.file.storage.UkFileStorage;
import uk.cnv.client.dom.accountimport.AccountImportService;
import uk.cnv.client.dom.fileimport.FileImportService;
import uk.cnv.client.dom.fileimport.JinKaisyaMRepository;
import uk.cnv.client.dom.fileimport.JmKihonRepository;
import uk.cnv.client.dom.fileimport.MapptingFileIdRepository;
import uk.cnv.client.infra.entity.JinKaisyaM;
import uk.cnv.client.infra.entity.JmKihon;
import uk.cnv.client.infra.impls.FileStorageImpl;
import uk.cnv.client.infra.repository.JpaJinKaisyaMRepository;
import uk.cnv.client.infra.repository.JpaJmKihonRepository;
import uk.cnv.client.infra.repository.JpaMappingFileIdRepository;

public class UkConverter {

	private JinKaisyaMRepository jinKaisyaRepo;
	private JmKihonRepository jmKihonRepo;
	private MapptingFileIdRepository mappingRepo;
	private FileStorageImpl storage;

	public UkConverter () {
		jinKaisyaRepo = new JpaJinKaisyaMRepository();
		jmKihonRepo = new JpaJmKihonRepository();
		mappingRepo = new JpaMappingFileIdRepository();
		storage = new FileStorageImpl();
	}

	public void doWork() {

		// ファイル移行準備
		FileImportService fileImporter = new FileImportService();
		RequireImpl require = new RequireImpl (
				jinKaisyaRepo,
				jmKihonRepo,
				mappingRepo,
				storage);

		if (!fileImporter.doWork(require)) {
			return;
		}

		// アカウント移行準備
		AccountImportService accountImporter = new AccountImportService();
		accountImporter.doWork();

	}

	private class RequireImpl implements FileImportService.Require{
		private JinKaisyaMRepository jinKaisyaRepo;
		private JmKihonRepository jmKihonRepo;
		private MapptingFileIdRepository mappingRepo;
		private FileStorageImpl storage;

		private Map<Integer, List<JmKihon>> cache;

		public RequireImpl(
				JinKaisyaMRepository jinKaisyaRepo,
				JmKihonRepository jmKihonRepo,
				MapptingFileIdRepository mappingRepo,
				FileStorageImpl storage) {
			cache = new HashMap<>();

			this.jinKaisyaRepo = jinKaisyaRepo;
			this.jmKihonRepo = jmKihonRepo;
			this.mappingRepo = mappingRepo;
			this.storage = storage;
		}

		@Override
		public List<JinKaisyaM> getAll() {
			return jinKaisyaRepo.getAll();
		}

		@Override
		public List<JmKihon> get(int companyCode) {
			if (cache.containsKey(companyCode)) return cache.get(companyCode);

			List<JmKihon> result = jmKihonRepo.findAll();
			this.cache = result.stream()
				.collect(Collectors.groupingBy(JmKihon::getCompanyCode));

			if (cache.containsKey(companyCode)) return cache.get(companyCode);

			return new ArrayList<>();
		}

		@Override
		public void save(StoredFileInfo mapptingFile, JmKihon employee) {
			mappingRepo.insert(mapptingFile, employee);
		}

		@Override
		public void store(Path path, String fileName, String type) {
			storage.store(path, fileName, type);
		}
	}
}
