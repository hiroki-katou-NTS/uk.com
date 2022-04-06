package nts.uk.screen.at.app.query.kdl.kdl016.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class InitialDataScreenA {
    private List<SupportInfoDto> supportInfoList;
    private boolean enableSupportInTimezone;
}
