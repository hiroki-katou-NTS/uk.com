package nts.uk.ctx.at.record.dom.dailyprocess.calc.withinstatutory.data.parameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.gul.csv.NtsCsvRecord;
import nts.gul.text.StringUtil;

public class TestDataCsvRecord {

    private final NtsCsvRecord record;
    private final String columnContext;
    
    private TestDataCsvRecord(NtsCsvRecord record, String columnContext) {
        this.record = record;
        this.columnContext = columnContext;
    }
    
    public static TestDataCsvRecord of(NtsCsvRecord record) { 
        return new TestDataCsvRecord(record, "");
    }
    
    public TestDataCsvRecord columnWith(String newColumnContext) {
        return new TestDataCsvRecord(this.record, this.column(newColumnContext));
    }
    
    public String asStr(String columnName) {
        return this.record.getColumnAsString(this.column(columnName));
    }
    
    public String asStrOrNull(String columnName) {
        String value = this.asStr(columnName);
        return StringUtil.isNullOrEmpty(value, false) ? null : value;
    }
    
    public Optional<String> asStrOpt(String columnName) {
        return Optional.ofNullable(this.asStrOrNull(columnName));
    }
    
    public int asInt(String columnName) {
        return this.record.getColumnAsInteger(this.column(columnName));
    }
    
    public <T> T asInt(String columnName, Function<Integer, T> mapper) {
        return mapper.apply(this.record.getColumnAsInteger(this.column(columnName)));
    }
    
    public Optional<Integer> asIntOpt(String columnName) {
        return this.asStrOpt(columnName).map(s -> Integer.parseInt(s));
    }
    
    public <E> E asEnum(String columnName, Class<E> enumClass) {
        return this.asEnumOpt(columnName, enumClass).get();
    }
    
    public <E> Optional<E> asEnumOpt(String columnName, Class<E> enumClass) {
        return this.asIntOpt(columnName)
                .map(i -> EnumAdaptor.valueOf(i, enumClass));
    }
    
    public <T> Optional<T> child(String childName, Function<TestDataCsvRecord, T> childBuilder) {
        // child列が存在しない場合は、null/presentのチェック不要
        if (this.existsColumn(childName) && this.asStr(childName).equals("null")) {
            return Optional.empty();
        }
        
        return Optional.of(childBuilder.apply(this.columnWith(childName)));
    }
    
    public <T> List<T> list(String listName, int size, Function<TestDataCsvRecord, T> listItemBuilder) {
        val list = new ArrayList<T>();
        
        for (int i = 0; i < size; i++) {
            String currentColumn = listName + "." + (i + 1);
            if (this.existsColumn(currentColumn) && this.asStr(currentColumn).equals("null")) {
                break;
            }
            
            list.add(this.child(currentColumn, listItemBuilder).get());
        }
        
        return list;
    }
    
    public boolean existsColumn(String columnName) {
        return this.record.getColumn(this.column(columnName)) != null;
    }
    
    private String column(String name) {
        return StringUtil.isNullOrEmpty(this.columnContext, true)
                ? name : this.columnContext + "." + name;
    }
}