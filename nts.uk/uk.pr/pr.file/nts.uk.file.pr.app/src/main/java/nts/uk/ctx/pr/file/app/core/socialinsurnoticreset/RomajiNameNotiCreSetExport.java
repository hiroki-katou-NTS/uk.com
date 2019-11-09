package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;

import lombok.*;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.adapter.person.PersonExport;
import nts.uk.ctx.pr.core.dom.adapter.person.family.FamilyMemberInfoEx;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RomajiNameNotiCreSetExport {
    private String empId;
    private String cid;

    private String basicPenNumber;
    private String fmBsPenNum;

    private String postalCode;
    private String address1;
    private String address2;
    private String representativeName;
    private String name;
    private String phoneNumber;

    private Integer personalSetOther;
    private Integer personalSetListed;
    private Integer personalResidentCard;
    private Integer personalAddressOverseas;
    private Integer personalShortResident;
    private String personalOtherReason;
    private Integer spouseSetOther;
    private Integer spouseSetListed;
    private Integer spouseResidentCard;
    private Integer spouseAddressOverseas;
    private Integer spouseShortResident;
    private String spouseOtherReason;
    private PersonExport person;
    private FamilyMemberInfoEx familyMember;
}
