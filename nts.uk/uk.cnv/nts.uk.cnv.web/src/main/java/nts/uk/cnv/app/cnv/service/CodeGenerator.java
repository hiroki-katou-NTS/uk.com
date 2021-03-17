package nts.uk.cnv.app.cnv.service;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.RequiredArgsConstructor;
import nts.uk.cnv.dom.cnv.categorypriority.CategoryPriorityRepository;
import nts.uk.cnv.dom.cnv.conversiontable.ConversionCategoryTableRepository;
import nts.uk.cnv.dom.cnv.conversiontable.ConversionRecord;
import nts.uk.cnv.dom.cnv.conversiontable.ConversionRecordRepository;
import nts.uk.cnv.dom.cnv.conversiontable.ConversionSource;
import nts.uk.cnv.dom.cnv.conversiontable.ConversionSourcesRepository;
import nts.uk.cnv.dom.cnv.conversiontable.ConversionTable;
import nts.uk.cnv.dom.cnv.conversiontable.ConversionTableRepository;
import nts.uk.cnv.dom.cnv.service.ConversionInfo;
import nts.uk.cnv.dom.cnv.service.CreateConversionCodeService;

@Stateless
public class CodeGenerator {

	@Inject
	CategoryPriorityRepository categoryPriorityRepository;

	@Inject
	ConversionRecordRepository conversionRecordRepository;

	@Inject
	ConversionSourcesRepository conversionSourceRepository;

	@Inject
	ConversionTableRepository conversionTableRepository;

	@Inject
	ConversionCategoryTableRepository conversionCategoryTableRepository;

	@Inject
	CreateConversionCodeService service;

	public void excecute(ConversionInfo info, String filePath) {

		CreateConversionCodeService.Require require = new RequireImpl(
			categoryPriorityRepository,
			conversionRecordRepository,
			conversionSourceRepository,
			conversionTableRepository,
			conversionCategoryTableRepository);

		String conversionCode = service.create(require, info);

		File file = new File(filePath);

		try {
			FileWriter fileWriter = new FileWriter(file);

			file.createNewFile();
			fileWriter.write(conversionCode);

			fileWriter.close();

			System.out.println("ファイル[" + file.getName() + "]を出力しました。");
		}
		catch(Exception ex) {
			System.out.println("ファイル[" + file.getName() + "]の出力に失敗しました。:" + ex.getMessage());
		}
	}

	@RequiredArgsConstructor
	private static class RequireImpl implements CreateConversionCodeService.Require{

		private final CategoryPriorityRepository categoryPriorityRepository;

		private final ConversionRecordRepository conversionRecordRepository;

		private final ConversionSourcesRepository conversionSourceRepository;

		private final ConversionTableRepository conversionTableRepository;

		private final ConversionCategoryTableRepository conversionCategoryTableRepository;

		private String preProcessing = "";
		private String postProcessing = "";

		@Override
		public List<String> getCategoryPriorities() {
			return categoryPriorityRepository.getAll();
		}

		@Override
		public Optional<ConversionTable> getConversionTable(ConversionInfo info, String category, String tableName, int recordNo, ConversionSource source) {
			return conversionTableRepository.get(info, category, tableName, recordNo, source);
		}

		@Override
		public List<String> getCategoryTables(String category) {
			return conversionCategoryTableRepository.get(category).stream()
					.map(ct -> ct.getTablename())
					.collect(Collectors.toList());
		}

		@Override
		public List<ConversionRecord> getRecords(String category, String tableName) {
			return conversionRecordRepository.getRecords(category, tableName);
		}

		@Override
		public ConversionSource getSource(String sourceId) {
			return conversionSourceRepository.get(sourceId);
		}

		@Override
		public void addPreProcessing(String sql) {
			this.preProcessing += sql;

		}

		@Override
		public void addPostProcessing(String sql) {
			this.postProcessing += sql;
		}

		@Override
		public String getPreProcessing() {
			return this.preProcessing;

		}

		@Override
		public String getPostProcessing() {
			return this.postProcessing;
		}

	}
}
