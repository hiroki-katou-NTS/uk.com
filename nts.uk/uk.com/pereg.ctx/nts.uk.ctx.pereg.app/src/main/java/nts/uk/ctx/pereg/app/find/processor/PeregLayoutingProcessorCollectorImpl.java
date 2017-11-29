package nts.uk.ctx.pereg.app.find.processor;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.util.TypeLiteral;

import nts.uk.ctx.bs.employee.app.find.classification.affiliate.AffClassificationDto;
import nts.uk.ctx.bs.employee.app.find.department.AffiliationDepartmentDto;
import nts.uk.ctx.bs.employee.app.find.temporaryabsence.TempAbsHisItemDto;
import nts.uk.ctx.bs.employee.app.find.workplace.affiliate.AffWorlplaceHistItemDto;
import nts.uk.shr.pereg.app.find.PeregFinder;
import nts.uk.shr.pereg.app.find.PeregFinderProcessorCollector;

@Stateless
@SuppressWarnings("serial")
public class PeregLayoutingProcessorCollectorImpl implements PeregFinderProcessorCollector {

	/** ctg single finder */
	private static final List<TypeLiteral<?>> FINDER_CTG_SINGLE_HANDLER_CLASSES = Arrays.asList(
			new TypeLiteral<PeregFinder<AffiliationDepartmentDto>>(){},
			new TypeLiteral<PeregFinder<TempAbsHisItemDto>>(){},
			new TypeLiteral<PeregFinder<AffClassificationDto>>(){},
			new TypeLiteral<PeregFinder<AffWorlplaceHistItemDto>>(){}
			);

	@Override
	public Set<PeregFinder<?>> peregFinderCollect() {
		return FINDER_CTG_SINGLE_HANDLER_CLASSES.stream().map(type -> CDI.current().select(type).get())
				.map(obj -> (PeregFinder<?>) obj).collect(Collectors.toSet());
	}

}
