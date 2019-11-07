package nts.uk.ctx.pr.core.dom.adapter.person.family;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FamilyMemberInfoEx {
    private String familyId;
    private String birthday;
    private Optional<String> romajiName;
    private Optional<String> romajiNameKana;
}