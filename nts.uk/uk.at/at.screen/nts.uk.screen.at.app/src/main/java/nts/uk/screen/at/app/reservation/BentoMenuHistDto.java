package nts.uk.screen.at.app.reservation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class BentoMenuHistDto {
    // 会社ID
    public String companyId;

    public List<DateHistoryItemDto> historyItems;

}
