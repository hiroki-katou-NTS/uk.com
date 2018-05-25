package nts.uk.ctx.sys.env.infra.entity.mailserver;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-09T16:58:26")
@StaticMetamodel(SevstMailServer.class)
public class SevstMailServer_ { 

    public static volatile SingularAttribute<SevstMailServer, String> popServer;
    public static volatile SingularAttribute<SevstMailServer, Short> encryptMethod;
    public static volatile SingularAttribute<SevstMailServer, String> imapServer;
    public static volatile SingularAttribute<SevstMailServer, Integer> popPort;
    public static volatile SingularAttribute<SevstMailServer, Integer> smtpPort;
    public static volatile SingularAttribute<SevstMailServer, Short> useAuth;
    public static volatile SingularAttribute<SevstMailServer, Short> imapUse;
    public static volatile SingularAttribute<SevstMailServer, Integer> imapPort;
    public static volatile SingularAttribute<SevstMailServer, String> password;
    public static volatile SingularAttribute<SevstMailServer, String> smtpServer;
    public static volatile SingularAttribute<SevstMailServer, Short> popUse;
    public static volatile SingularAttribute<SevstMailServer, Short> authMethod;
    public static volatile SingularAttribute<SevstMailServer, String> emailAuth;
    public static volatile SingularAttribute<SevstMailServer, String> cid;

}