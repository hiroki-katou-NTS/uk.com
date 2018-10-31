package nts.uk.ctx.pr.core.app.command.wageprovision.individualwagecontract;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
@Data
@AllArgsConstructor
public class SalIndAmountUpdateAllCommand {

    List<SalIndAmountUpdateCommand> salIndAmountUpdateCommandList;

}




