package nts.uk.ctx.pereg.pub.mastercopy;

public interface PerInfoCategoryPub {
    void personalInfoDefEvent(String companyId, int copyMethod);
    void newLayoutEvent(String companyId, int copyMethod);
    void personalInfoItemGroupEvent(String companyId, int copyMethod);
    void personalInfoSelectItemEvent(String companyId, int copyMethod);
}
