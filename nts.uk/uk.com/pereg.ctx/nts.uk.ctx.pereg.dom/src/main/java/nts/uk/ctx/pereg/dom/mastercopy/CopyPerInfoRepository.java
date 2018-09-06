package nts.uk.ctx.pereg.dom.mastercopy;

/**
 * @author locph
 */
public interface CopyPerInfoRepository {
    void doCopyA(String companyId, int copyMethod);
    void doCopyB(String companyId, int copyMethod);
    void doCopyC(String companyId, int copyMethod);
}
