package nts.uk.cnv.app.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.RequiredArgsConstructor;
import lombok.val;
import nts.uk.cnv.dom.categorypriority.CategoryPriorityRepository;
import nts.uk.cnv.dom.conversiontable.ConversionCategoryTableRepository;
import nts.uk.cnv.dom.conversiontable.ConversionRecord;
import nts.uk.cnv.dom.conversiontable.ConversionSource;
import nts.uk.cnv.dom.conversiontable.ConversionTable;
import nts.uk.cnv.dom.conversiontable.ConversionTableRepository;
import nts.uk.cnv.dom.service.ConversionInfo;
import nts.uk.cnv.dom.service.CreateConversionCodeService;

@Stateless
public class CodeGenerator {

	@Inject
	CategoryPriorityRepository categoryPriorityRepository;

	@Inject
	ConversionTableRepository conversionTableRepository;

	@Inject
	ConversionCategoryTableRepository conversionCategoryTableRepository;

	@Inject
	CreateConversionCodeService service;

	public String excecute(ConversionInfo info) {

		val require = new RequireImpl(
			categoryPriorityRepository,
			conversionTableRepository,
			conversionCategoryTableRepository);

		return service.create(require, info);
	}

	@RequiredArgsConstructor
	private static class RequireImpl implements CreateConversionCodeService.Require{

		private final CategoryPriorityRepository categoryPriorityRepository;

		private final ConversionTableRepository conversionTableRepository;

		private final ConversionCategoryTableRepository conversionCategoryTableRepository;

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
			return conversionTableRepository.getRecords(category, tableName);
		}

		@Override
		public ConversionSource getSource(String category, String sourceId) {
			// TODO 自動生成されたメソッド・スタブ
			return null;
		}

	}
}
