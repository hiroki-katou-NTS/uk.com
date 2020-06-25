package nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.timestampsetting.prefortimestaminput;

import org.eclipse.persistence.config.DescriptorCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.expressions.*;
import org.eclipse.persistence.mappings.OneToManyMapping;

public class KrcmtStampCommunalCustomizer implements DescriptorCustomizer{

	@Override
	public void customize(ClassDescriptor descriptor) throws Exception {
		OneToManyMapping mapping = (OneToManyMapping)descriptor.getMappingForAttributeName("krcmtStampCommunal");
		ExpressionBuilder builder = new ExpressionBuilder(mapping.getReferenceClass());
		Expression fkExp = builder.getField("CID").equal(builder.getParameter("CID"));
		Expression activeExp = builder.getField("STAMP_MEANS").equal(0);
		mapping.setSelectionCriteria(fkExp.and(activeExp));
	}

}
