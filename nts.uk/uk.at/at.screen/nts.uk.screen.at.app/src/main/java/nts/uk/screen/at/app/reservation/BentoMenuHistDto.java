package nts.uk.screen.at.app.reservation;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class BentoMenuHistDto {

    public String companyId;


    public List<DateHistoryItemDto> historyItems;

}
