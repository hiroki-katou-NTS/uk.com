package nts.uk.screen.at.app.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BentoDto {

    public String contractCD;

    public String bentoName;

    public String unitName;

    public int price1;

    public int price2;

    public boolean reservationAtr1;

    public boolean reservationAtr2;

    public String workLocationCode;

}
