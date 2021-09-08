package nts.uk.cnv.app.service;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.cnv.core.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.core.dom.conversiontable.ConversionInfo;
import nts.uk.cnv.core.dom.conversiontable.ConversionRecord;
import nts.uk.cnv.core.dom.conversiontable.ConversionSource;
import nts.uk.cnv.core.dom.conversiontable.ConversionTable;
import nts.uk.cnv.dom.categorypriority.CategoryPriorityRepository;
import nts.uk.cnv.dom.conversiontable.ConversionCategoryTableRepository;
import nts.uk.cnv.dom.conversiontable.ConversionRecordRepository;
import nts.uk.cnv.dom.conversiontable.ConversionSourcesRepository;
import nts.uk.cnv.dom.conversiontable.ConversionTableRepository;
import nts.uk.cnv.dom.service.CreateConversionCodeService;

@Stateless
public class CodeGenerator {
	
	private static final String programId = "CNV001";

	@Inject
	protected CategoryPriorityRepository categoryPriorityRepository;

	@Inject
	protected ConversionRecordRepository conversionRecordRepository;

	@Inject
	protected ConversionSourcesRepository conversionSourceRepository;

	@Inject
	protected ConversionTableRepository conversionTableRepository;

	@Inject
	protected ConversionCategoryTableRepository conversionCategoryTableRepository;

	@Inject
	protected CreateConversionCodeService service;

	public void excecute(ConversionInfo info, String filePath) {

		CreateConversionCodeService.Require require = new RequireImplForInsert();

		String conversionCode = service.create(require, info);

		outputToFile(conversionCode, filePath);
	}

	public void excecuteUpdate(ConversionInfo info, String filePath) {

		CreateConversionCodeService.Require require = new RequireImplForUpdate();

		String conversionCode = service.createForUpdate(require, info);

		outputToFile(conversionCode, filePath);
	}

	private void outputToFile(String conversionCode, String filePath) {
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

	private class RequireImplForInsert extends RequireImpl{
		@Override
		public ConversionSQL createConversionSQL(ConversionTable ct) {
			return ct.createConversionSql(programId);
		}
	}

	private class RequireImplForUpdate extends RequireImpl{
		@Override
		public ConversionSQL createConversionSQL(ConversionTable ct) {
			return ct.createUpdateConversionSql(programId);
		}
	}

	private abstract class RequireImpl implements CreateConversionCodeService.Require{

		private List<String> preProcessing = new ArrayList<>();
		private List<String> postProcessing = new ArrayList<>();

		@Override
		public List<String> getCategoryPriorities() {
			return categoryPriorityRepository.getAll();
		}

		@Override
		public Optional<ConversionTable> getConversionTable(ConversionInfo info, String category, String tableName, ConversionSource source, ConversionRecord record) {
			return conversionTableRepository.get(info, category, tableName, source, record);
		}

		@Override
		public List<String> getCategoryTables(String category) {
			return conversionCategoryTableRepository.get(category).stream()
					.map(ct -> ct.getTable().getName())
					.collect(Collectors.toList());
		}

		@Override
		public List<ConversionRecord> getRecords(String category, String tableName) {
			return conversionRecordRepository.getRecords(category, tableName);
		}

		@Override
		public ConversionSource getSource(String sourceId) {
			return conversionSourceRepository.get(sourceId).get();
		}

		@Override
		public void addPreProcessing(String sql) {
			this.preProcessing.add(sql);

		}

		@Override
		public void addPostProcessing(String sql) {
			this.postProcessing.add(sql);
		}

		@Override
		public String getPreProcessing() {
			return String.join("\r\n", this.preProcessing.stream()
					.filter(sql -> !sql.isEmpty())
					.collect(Collectors.toList()));

		}

		@Override
		public String getPostProcessing() {
			return String.join("\r\n", this.postProcessing.stream()
					.filter(sql -> !sql.isEmpty())
					.collect(Collectors.toList()));
		}

		@Override
		public abstract ConversionSQL createConversionSQL(ConversionTable ct);

	}
}
