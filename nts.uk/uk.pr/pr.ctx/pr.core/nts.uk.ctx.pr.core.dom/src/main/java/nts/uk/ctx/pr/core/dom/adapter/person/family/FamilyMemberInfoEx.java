package nts.uk.ctx.pr.core.dom.adapter.person.family;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Setter
@Getter
@NoArgsConstructor
public class FamilyMemberInfoEx {
    private String personId;
    private String familyId;
    private String birthday;
    private String fullName;
    private String fullNameKana;
    private Optional<String> romajiName;
    private Optional<String> romajiNameKana;

    public FamilyMemberInfoEx(String familyId, String birthday, String fullName, String fullNameKana, Optional<String> romajiName, Optional<String> romajiNameKana) {
        this.familyId = familyId;
        this.birthday = birthday;
        this.fullName = fullName;
        this.fullNameKana = fullNameKana;
        this.romajiName  = romajiName;
        this.romajiNameKana = romajiNameKana;
    }
}