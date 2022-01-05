package nts.uk.ctx.at.record.app.command.reservation.bento;

import java.util.List;

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
public class RegisterErrorMessage {

    public String messageId;
    
    public List<String> params;
}
