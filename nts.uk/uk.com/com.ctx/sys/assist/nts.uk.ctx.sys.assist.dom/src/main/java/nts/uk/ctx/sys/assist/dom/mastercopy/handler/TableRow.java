package nts.uk.ctx.sys.assist.dom.mastercopy.handler;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.val;
import nts.arc.time.GeneralDateTime;
import nts.uk.shr.com.company.CompanyId;
import nts.uk.shr.com.constants.DefaultSettingKeys;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * UKテーブルの行
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TableRow {

    private final boolean isNoCopy;
    private final MetaInfo insertInfo;
    private final MetaInfo updateInfo;
    private final int exclusVer;

    /** 既存データとの重複チェック用キー値 */
    private final List<Object> keys;

    /** データ本体。CID等主キーの値も含む */
    private final List<Object> contents;

    public TableRow(
            CompanyId newCompanyId,
            String employeeCode,
            Object[] rowData,
            int keysSize) {

        isNoCopy = isNoCopy(rowData);
        insertInfo = new MetaInfo(newCompanyId.getCompanyCode(), employeeCode);
        updateInfo = insertInfo;
        exclusVer = 0;

        val replacedRowData = Arrays.stream(rowData)
                .map(v -> replaceCompanyId(newCompanyId, v))
                .collect(Collectors.toList());
        int indexKeys = rowData.length - keysSize;
        keys = replacedRowData.subList(indexKeys, rowData.length);
        contents = replacedRowData.subList(ColumnIndex.CONTENTS_START, indexKeys);
    }

    private static boolean isNoCopy(Object[] rowData) {
        String inspg = fetch(rowData, ColumnIndex.INS_PG);
        return inspg != null && inspg.trim().equalsIgnoreCase("NOCOPY");
    }

    private static Object replaceCompanyId(CompanyId newCompanyId, Object v) {
        if (v instanceof String) {
            String s = (String) v;
            if (s.equals(DefaultSettingKeys.CONTRACT_CODE)) {
                return newCompanyId.getContractCode();
            }
            if (s.equals(DefaultSettingKeys.COMPANY_ID)) {
                return newCompanyId.getValue();
            }
        }
        return v;
    }

    public boolean isNoCopy() {
        return isNoCopy;
    }

    public boolean isSameKey(TableRow other) {
        return keys.equals(other.keys);
    }

    public String createInsertSql(String tableName, KeyValueHolder keyValueHolder) {
        return "INSERT INTO " + tableName
                + " VALUES (" + String.join(",", createValues(keyValueHolder)) + ")";
    }

    private List<String> createValues(KeyValueHolder keyValueHolder) {

        List<Object> values = new ArrayList<>();
        values.addAll(insertInfo.getValues());
        values.addAll(updateInfo.getValues());
        values.add(exclusVer);
        values.addAll(contents);

        return values.stream()
                .map(v -> param(v, keyValueHolder))
                .collect(Collectors.toList());
    }

    private static String param(Object value, KeyValueHolder keyValueHolder) {

        if (value == null) {
            return "null";
        }

        Class<?> type = value.getClass();

        if (KeyValueHolder.isKey(value)) {
            value = keyValueHolder.getKeyValue(value);
        }

        if (type == String.class) {
            return "'" + value + "'";
        }

        if (type == Timestamp.class || type == Date.class) {
            return "'" + value + "'";
        }

        return "" + value;
    }


    private static class ColumnIndex {
        private static final int CID = 0;
        private static final int CONTRACT_CD = CID + 1;
        private static final int INS_DATE = CONTRACT_CD + 1;
        private static final int INS_PG = INS_DATE + 3;
        private static final int UPD_DATE = INS_DATE + 4;
        private static final int EXCLUS_VER = UPD_DATE + 4;
        private static final int CONTENTS_START = EXCLUS_VER + 1;
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class MetaInfo {

        public final Timestamp datetime;
        public final String companyCode;
        public final String employeeCode;
        public final String programId;

        public MetaInfo(String companyCode, String employeeCode) {
            this.datetime = Timestamp.valueOf(GeneralDateTime.now().localDateTime());
            this.companyCode = companyCode;
            this.employeeCode = employeeCode;
            this.programId = "CMM001";
        }

        public List<Object> getValues() {
            List<Object> values = new ArrayList<>();
            values.add(datetime);
            values.add(companyCode);
            values.add(employeeCode);
            values.add(programId);
            return values;
        }
    }

    private static <T> T fetch(Object[] rowData, int column) {
        return (T) rowData[column];
    }

}
