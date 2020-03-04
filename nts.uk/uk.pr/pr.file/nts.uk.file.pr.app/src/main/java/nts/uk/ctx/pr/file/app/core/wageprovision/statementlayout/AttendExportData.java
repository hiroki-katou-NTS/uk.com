package nts.uk.ctx.pr.file.app.core.wageprovision.statementlayout;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@NoArgsConstructor
@Getter
@Setter
public class AttendExportData {

    private Optional<ItemRangeSetExportData> itemRangeSet;
}
