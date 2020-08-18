package nts.uk.cnv.app.service;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.RequiredArgsConstructor;
import lombok.val;
import nts.uk.cnv.dom.categorypriority.CategoryPriorityRepository;
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
	CreateConversionCodeService service;
	
	public String excecute(ConversionInfo info) {
		
		val require = new RequireImpl(categoryPriorityRepository, conversionTableRepository);
		
		return service.create(require, info);
	}
	
	@RequiredArgsConstructor
	private static class RequireImpl implements CreateConversionCodeService.Require{

		private final CategoryPriorityRepository categoryPriorityRepository;
		
		private final ConversionTableRepository conversionTableRepository;
		
		@Override
		public List<String> getCategoryPriorities() {
			return categoryPriorityRepository.getAll();
		}

		@Override
		public Optional<ConversionTable> getConversionTableRepository(ConversionInfo info, String category) {
			return conversionTableRepository.get(info, category);
		}
		
	}
}
