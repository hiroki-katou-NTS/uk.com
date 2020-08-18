package nts.sample.excel.browser;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.aspose.cells.FindOptions;
import com.aspose.cells.HtmlSaveOptions;
import com.aspose.cells.LookAtType;
import com.aspose.cells.Workbook;

import lombok.SneakyThrows;
import lombok.val;

@Path("/test/excel/")
public class ExcelBrowser {
	
	@POST
	@Path("index/update")
	public void updateIndex() {
		
	}
	
	@POST
	@Path("generate/{file}")
	@SneakyThrows
	public void generate(@PathParam("file") String file) {
		Workbook book = new Workbook("\\\\192.168.50.4\\share\\500_新構想開発\\00_開発プロセス\\検討\\上流フェーズ検討会\\サンプル\\4.ドメイン分析\\4.2.ドメイン仕様書\\ワークフロー\\【記号化】承認ルートの状況_ドメイン仕様書.xlsx");
		
		val findOptions = new FindOptions();
		findOptions.setLookAtType(LookAtType.START_WITH);
		
		
		//val link = book.getWorksheets().get(0).getHyperlinks().get(0);
		
		
		HtmlSaveOptions options = new HtmlSaveOptions();
		book.save("C:\\biz\\Tool\\Application\\nginx\\html\\domain\\sample.html", options);
	}

}
