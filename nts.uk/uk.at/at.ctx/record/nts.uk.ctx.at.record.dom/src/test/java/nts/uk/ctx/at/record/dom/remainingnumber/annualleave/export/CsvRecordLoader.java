package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import lombok.val;
import nts.gul.csv.NtsCsvReader;
import nts.gul.text.charset.NtsCharset;

public class CsvRecordLoader {

	//@SneakyThrows
	public static <T> List<T> load(String filePath, Function<TestDataCsvRecord, T> builder, Class<T> cls){ 							
		try(val is = cls.getClass().getResourceAsStream(filePath)){						
			NtsCsvReader csvReader = NtsCsvReader.newReader().withChartSet(NtsCharset.SHIFT_JIS);
			val csvData = csvReader.parse(is);
			List<T> list = new ArrayList<T>();
			csvData.getRecords().stream().map(record -> TestDataCsvRecord.of(record)).forEach(record ->{					
				list.add(builder.apply(record));				
			});
			return list;
		}
		catch(Exception e1)
		{
			e1.printStackTrace();
		}
		return null;
	}

}
