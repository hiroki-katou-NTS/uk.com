package nts.uk.pr.file.infra.company;

import java.io.IOException;
import java.io.OutputStream;

import javax.enterprise.context.RequestScoped;

import nts.arc.layer.infra.file.FileGenerator;
import nts.arc.layer.infra.file.FileGeneratorContext;

@RequestScoped
public class CompanyPrintFileGenerator extends FileGenerator{

	@Override
	protected void generate(FileGeneratorContext context) {
		try {
			System.out.print("1");
			OutputStream output = context.createOutputFileStream("company.txt");
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					for (int k = 0; k < 100; k++) {
						output.write(("test " + i + "-" + j + "-" + k + "/n").getBytes());
					}
				}
			}
			System.out.print("2");
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
