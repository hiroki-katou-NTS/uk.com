package nts.uk.ctx.pr.file.app.core.socialinsurnoticreset;


public interface RomajiNameNotiCreSetExReposity {
    FamilyMember getFamilyInfo(String empId, String relationship);
    PersonInfo getPersonInfo(String personId);

}
