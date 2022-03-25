package nts.uk.ctx.at.record.app.query.reservation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.record.app.command.reservation.bento.RegisterErrorMessage;


/**
 * @author anhnm
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StartReservationCorrectOutput {

    public List<BentoDto> bento = new ArrayList<BentoDto>();
    
    public List<PersonEmpBasicInfoImportDto> listEmpInfo = new ArrayList<PersonEmpBasicInfoImportDto>();
    
    public Map<String, BentoReservationWithFlag> bentoReservation = new HashMap<String, BentoReservationWithFlag>();
    
    public Map<String, String> stampMap = new HashMap<String, String>();
    
    public List<RegisterErrorMessage> exceptions = new ArrayList<RegisterErrorMessage>();
    
    public boolean roleFlag;
}
