/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.actualresult.service;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.apache.commons.csv.CSVFormat;

import nts.gul.csv.NtsCsvReader;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExtBudgetCharset;

/**
 * The Class FileUtil.
 */
public class FileUtil {
    
    /**
     * New csv reader.
     *
     * @param valueEncoding the value encoding
     * @return the nts csv reader
     */
    public static NtsCsvReader newCsvReader(Integer valueEncoding) {
        Charset charset = getCharset(valueEncoding);
        String newLineCode = getNewLineCode();
        return NtsCsvReader.newReader().withNoHeader().withChartSet(charset)
                .withFormat(CSVFormat.EXCEL.withRecordSeparator(newLineCode)).skipEmptyLines(true);
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
