package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import lombok.val;
import nts.gul.csv.NtsCsvReader;

public class CsvParameter {

	public static <T> Map<String, T> load(String csvFile, Function<TestDataCsvRecord, T> builder, Class<T> ababa) throws IOException{
		try ( val is = ababa.getClass().getResourceAsStream(csvFile)){
			val result = NtsCsvReader.newReader().parse(is);
			
			val testdata = new HashMap<String, T>();
			
			result.getRecords().stream().map(r -> TestDataCsvRecord.of(r)).forEach(record -> {
				
				String key = record.asStr("key");
				val timeLeavingWork = builder.apply(record);
				testdata.put(key, timeLeavingWork);
				
			});
			
			return testdata;
		}
			
	}
}
