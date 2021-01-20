package nts.uk.ctx.at.function.app.query.arbitraryperiodsummarytable;



import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OutputSettingArbitraryDto {
    private String settingId;
    private String settingDisplayCode;
    private String settingName;
}
