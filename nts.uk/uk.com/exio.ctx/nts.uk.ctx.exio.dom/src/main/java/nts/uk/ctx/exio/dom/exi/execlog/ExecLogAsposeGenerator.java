package nts.uk.ctx.exio.dom.exi.execlog;

import java.util.List;

import nts.arc.layer.infra.file.export.FileGeneratorContext;

public interface ExecLogAsposeGenerator {
	
	void generate(FileGeneratorContext context, List<SampleDepartment> depts);
	
	void generateWithTemplate(FileGeneratorContext context, List<SampleDepartment> depts);
	
	void generateFile(FileGeneratorContext context, ExacErrorExportFile exportFile);
}
