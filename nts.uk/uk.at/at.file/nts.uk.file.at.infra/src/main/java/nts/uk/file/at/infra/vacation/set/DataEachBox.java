package nts.uk.file.at.infra.vacation.set;

import lombok.Value;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
@Value
public  class DataEachBox {
    private String value;
    private ColumnTextAlign positon;

    public DataEachBox(String value, ColumnTextAlign positon) {
        this.value = value;
        this.positon = positon;
    }
}
