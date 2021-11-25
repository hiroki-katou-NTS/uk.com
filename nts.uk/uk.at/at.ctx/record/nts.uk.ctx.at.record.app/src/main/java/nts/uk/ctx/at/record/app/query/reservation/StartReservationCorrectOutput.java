package nts.uk.ctx.at.record.app.query.reservation;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


/**
 * @author anhnm
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class StartReservationCorrectOutput {

    public List<BentoDto> bento;
    
    public List<PersonEmpBasicInfoImportDto> listEmpInfo;
    
    public Map<String, BentoReservationWithFlag> bentoReservation;
}
