package nts.uk.file.at.app.export.bento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.record.app.find.reservation.bento.dto.OrderInfoDto;

@AllArgsConstructor
@Getter
public class OrderInfoExportData {
    private OrderInfoDto orderInfoDto;
    private boolean isBreakPage;
    private boolean isWorkLocationExport;
    private String reservationTimeZone;
    private OutputExtension outputExt;
}
