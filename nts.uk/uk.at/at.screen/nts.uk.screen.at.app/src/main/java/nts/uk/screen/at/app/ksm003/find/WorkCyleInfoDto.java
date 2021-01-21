package nts.uk.screen.at.app.ksm003.find;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.shr.com.i18n.TextResource;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkCyleInfoDto {

    private String typeCode;

    private String typeName;

    private String timeCode;

    private String timeName;

    private int days;

    private int dispOrder;

    public WorkCyleInfoDto(String typeCode, String timeCode, int days, int dispOrder, Map<String , String> workTypes, Map<String , String> workTimeItems) {
        this.typeCode = typeCode;
        this.timeCode = timeCode;
        this.days = days;
        this.dispOrder = dispOrder;

        // Get WorkType
        this.typeName = workTypes.containsKey(typeCode) ? workTypes.get(typeCode) : TextResource.localize("KSM003_2");

        if (timeCode != null) {
            this.timeName = workTimeItems.containsKey(timeCode) ? workTimeItems.get(timeCode) : TextResource.localize("KSM003_2");
        }

    }
}
