package nts.uk.ctx.cloud.operate.infra.web.component.env;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import javax.ejb.Stateless;
import javax.faces.context.ResponseWriter;
import javax.inject.Inject;

import lombok.val;
import nts.uk.shr.com.time.japanese.JapaneseErasProvider;
import nts.uk.shr.infra.web.component.util.ObjectWriter;

@Stateless
public class ViewContextEnvWriterNoLogin {

	@Inject
	private JapaneseErasProvider japaneseErasProvider;

	public void write(ResponseWriter rw) throws IOException {
		rw.write("__viewContext.env = {};");

		this.writeSystem(rw);
		this.writeJapanseEras(rw);
	}

	private void writeSystem(ResponseWriter rw) throws IOException {

		//val system = AppContexts.system();
		rw.write("__viewContext.env.systemName = '';");
		rw.write("__viewContext.env.isCloud = true;");
		rw.write("__viewContext.env.isOnPremise = false;");
		rw.write("__viewContext.env.products = {};");
		rw.write("__viewContext.env.products.attendance = false;");
		rw.write("__viewContext.env.products.payroll = false;");
		rw.write("__viewContext.env.products.personnel = false;");
		rw.write("__viewContext.env.pathToManual = '';");
	}

	private void writeJapanseEras(ResponseWriter rw) throws IOException {

		rw.write("__viewContext.env.japaneseEras = [];");

		val eras = this.japaneseErasProvider.getAllEras();
		for (val era : eras.getNames()) {
			rw.write("__viewContext.env.japaneseEras.push(");

			ObjectWriter.start(rw)
				.put("name", era.getName())
				.put("symbol", era.getSymbol())
				.put("start", DateTimeFormatter.ISO_LOCAL_DATE.format(era.startDate().localDate()))
				.put("end", DateTimeFormatter.ISO_LOCAL_DATE.format(era.endDate().localDate()))
				.finish();

			rw.write(");");
		}
	}
}
