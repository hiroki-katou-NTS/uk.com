package nts.uk.ctx.pereg.app.find.processor;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.util.TypeLiteral;

import find.person.info.PersonDto;
import nts.uk.ctx.bs.employee.app.find.classification.affiliate.AffClassificationDto;
import nts.uk.ctx.bs.employee.app.find.department.affiliate.AffDeptHistFinder;
import nts.uk.ctx.bs.employee.app.find.jobtitle.affiliate.AffJobTitleDto;
import nts.uk.ctx.bs.employee.app.find.temporaryabsence.TempAbsHisItemDto;
import nts.uk.ctx.bs.employee.app.find.workplace.affiliate.AffWorlplaceHistItemDto;
import nts.uk.ctx.pereg.app.find.employment.history.EmploymentHistoryDto;
import nts.uk.shr.pereg.app.find.PeregFinder;

@Stateless
@SuppressWarnings("serial")
public class PeregLayoutingProcessorCollectorImpl implements PeregFinderProcessorCollector {

	/** ctg single finder */
	private static final List<TypeLiteral<?>> FINDER_CTG_SINGLE_HANDLER_CLASSES = Arrays.asList(
			// CS00001
			// CS00002
			new TypeLiteral<PeregFinder<PersonDto>>(){},
			// CS00003
			// CS00004
			new TypeLiteral<PeregFinder<AffClassificationDto>>(){},
			// CS00014
			new TypeLiteral<PeregFinder<EmploymentHistoryDto>>(){},
			// CS00015		
			new TypeLiteral<PeregFinder<AffDeptHistFinder>>(){},
			// CS00016
			new TypeLiteral<PeregFinder<AffJobTitleDto>>(){},
			// CS00017
			new TypeLiteral<PeregFinder<AffWorlplaceHistItemDto>>(){},
			// CS00018
			new TypeLiteral<PeregFinder<TempAbsHisItemDto>>(){}
			// CS00019
			// CS00020
			// CS00021
			);

	@Override
	public Set<PeregFinder<?>> peregFinderCollect() {
		return FINDER_CTG_SINGLE_HANDLER_CLASSES.stream().map(type -> CDI.current().select(type).get())
				.map(obj -> (PeregFinder<?>) obj).collect(Collectors.toSet());
	}

}
