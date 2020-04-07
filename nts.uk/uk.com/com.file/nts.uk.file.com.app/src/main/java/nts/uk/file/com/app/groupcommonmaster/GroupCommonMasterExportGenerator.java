package nts.uk.file.com.app.groupcommonmaster;

import java.util.List;
import java.util.Optional;

import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.bs.employee.dom.groupcommonmaster.GroupCommonMaster;
import nts.uk.shr.com.company.CompanyInfor;

public interface GroupCommonMasterExportGenerator {
	public void generate(FileGeneratorContext fileGeneratorContext, Optional<CompanyInfor> optional,
			List<GroupCommonMaster> data);
}
