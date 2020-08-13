package nts.uk.ctx.at.record.app.command.reservation.reseritemset;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateReseItemSettingCommand {

    // 弁当名
    private String benToName;

    // 勤務場所コード
    private String workLocationCode;

    // 金額１
    private BigDecimal amount1;

    // 金額２
    private  BigDecimal amount2;

    // 単位
    private String Unit;

    // 締め時刻１で予約可能
    private Bool canBookClosesingTime1;

    // 締め時刻１で予約可能
    private Bool canBookClosesingTime2;
}
