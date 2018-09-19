package nts.uk.ctx.pereg.dom.mastercopy;

/**
 * @author locph
 */
public interface CopyPerInfoRepository {
    void personalInfoDefEvent(String companyId, int copyMethod);
    void newLayoutEvent(String companyId, int copyMethod);
    void personalInfoItemGroupEvent(String companyId, int copyMethod);
    void personalInfoSelectItemEvent(String companyId, int copyMethod);
}
