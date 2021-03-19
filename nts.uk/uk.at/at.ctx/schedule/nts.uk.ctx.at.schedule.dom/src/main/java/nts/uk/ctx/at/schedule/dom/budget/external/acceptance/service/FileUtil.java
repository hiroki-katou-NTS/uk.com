/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.actualresult.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

import nts.gul.csv.CSVParsedResult;
import nts.gul.csv.InvalidColumnsSizeException;
import nts.gul.csv.NtsCsvReader;
import nts.gul.csv.NtsCsvRecord;
import nts.gul.csv.ParseCSVException;
import nts.uk.ctx.at.schedule.dom.budget.external.UnitAtr;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExtBudgetCharset;

/**
 * The Class FileUtil.
 */
public class FileUtil {

    /** The Constant MAX_COLUMN_DAILY. */
    private static final Integer MAX_COLUMN_DAILY = 3;

    /** The Constant MAX_COLUMN_TIME_ZONE. */
    private static final Integer MAX_COLUMN_TIME_ZONE = 50;
    
    /**
     * Find content file.
     *
     * @param inputStream the input stream
     * @param valueEncoding the value encoding
     * @param standardColumn the standard column
     * @return the map
     */
    public static Map<Integer, List<String>> findContentFile(InputStream inputStream, Integer valueEncoding,
            Integer standardColumn) {
        // get encoding
        Charset charset = getCharset(valueEncoding);
        
        // get new line code of system
        String newLineCode = getNewLineCode();
        
        // get csv reader
        NtsCsvReader csvReader = NtsCsvReader.newReader().withNoHeader().skipEmptyLines(true)
                .withCheckColumnsSize(standardColumn).withChartSet(charset)
                .withFormat(CSVFormat.EXCEL.withRecordSeparator(newLineCode));
        
        Map<Integer, List<String>> mapResult = new HashMap<>();
        
        try {
            CSVParsedResult csvParsedResult = csvReader.parseWithCheckColumnEachRow(inputStream, standardColumn);

            // map has column incorrect
            Map<Integer, List<String>> mapError = getListError(csvParsedResult.getErrors());
            mapResult.putAll(mapError);

            // map column correct
            Map<Integer, List<String>> mapRecord = getListRecord(csvParsedResult.getRecords(), standardColumn);
            mapResult.putAll(mapRecord);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return mapResult.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    
    /**
     * Gets the standard column.
     *
     * @param unitAtr the unit atr
     * @return the standard column
     */
    public static Integer getStandardColumn(UnitAtr unitAtr) {
        switch (unitAtr) {
        case DAILY:
            return MAX_COLUMN_DAILY;
        case BYTIMEZONE:
            return MAX_COLUMN_TIME_ZONE;
        default:
            throw new RuntimeException("Not unit atr suitable.");
        }
    }

    /**
     * Gets the list error.
     *
     * @param lstError the lst error
     * @return the list error
     */
    private static Map<Integer, List<String>> getListError(List<ParseCSVException> lstError) {
        return lstError.stream()
                .filter(item -> {
                    InvalidColumnsSizeException record = (InvalidColumnsSizeException) item;
                    
                    // ignore line blank. Rules pattern of line blank: empty row or,,,
                    return !Strings.isEmpty(StringUtils.join(record.getRowRawValues(), Strings.EMPTY));
                })
                .collect(Collectors.toMap(item -> ((InvalidColumnsSizeException) item).getErrorRow(),
                        item -> ((InvalidColumnsSizeException) item).getRowRawValues()));
    }

    /**
     * Gets the list record.
     *
     * @param lstRecord the lst record
     * @param standardColumn the standard column
     * @return the list record
     */
    private static Map<Integer, List<String>> getListRecord(List<NtsCsvRecord> lstRecord, Integer standardColumn) {
        return lstRecord.stream()
                .filter(item -> {
                    NtsCsvRecord record = (NtsCsvRecord) item;
                    
                    List<String> result = new ArrayList<>();
                    for (int i = 0; i < standardColumn; i++) {
                        result.add((String) record.getColumn(i));
                    }
                    // ignore line blank. Rules pattern of line blank: empty row or ",,,"
                    return !Strings.isEmpty(StringUtils.join(result, Strings.EMPTY));
                }).collect(Collectors.toMap(item -> ((NtsCsvRecord) item).getRowNumber(), item -> {
                    NtsCsvRecord csvRecord = (NtsCsvRecord) item;
        
                    List<String> result = new ArrayList<>();
                    for (int i = 0; i < standardColumn; i++) {
                        result.add(String.valueOf(csvRecord.getColumn(i)));
                    }
                    return result;
                }));
    }

    /**
     * Gets the charset.
     *
     * @param valueEncoding the value encoding
     * @return the charset
     */
    private static Charset getCharset(Integer valueEncoding) {
        ExtBudgetCharset encoding = ExtBudgetCharset.valueOf(valueEncoding);
        switch (encoding) {
        case Shift_JIS:
            return Charset.forName("Shift_JIS");
        default:
            return StandardCharsets.UTF_8;
        }
    }

    /**
     * Gets the new line code.
     *
     * @return the new line code
     */
    private static String getNewLineCode() {
        return "\r\n"; // CR+LF
    }
}
