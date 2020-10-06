package nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.data.parameter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import lombok.SneakyThrows;
import lombok.val;
import nts.gul.csv.NtsCsvReader;

public class CsvParameter {

    @SneakyThrows
    public static <T> Map<String, T> load(String csvFile, Function<TestDataCsvRecord, T> builder, Class<T> cls) {

        try (val is = cls.getResourceAsStream(csvFile)) {
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