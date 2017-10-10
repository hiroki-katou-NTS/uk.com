module nts.uk.com.view.cmm050.a {
    export module service {
        /**
         * define path to service
         */
        var path: any = {
                find: "sys/env/mailserver/find",
                save: "sys/env/mailserver/save",
            };
        
        /**
         * 
         */
        export function findMailServerSetting(): JQueryPromise<model.MailServerDto>{
            return nts.uk.request.ajax(path.find);
        }
        
        /**
         * 
         */
        export function registerMailServerSetting(data: model.MailServerDto): JQueryPromise<any> {
            return nts.uk.request.ajax(path.save, data);
        }
    }
    
    /**
     * Model define.
     */
    export module model {
        export class MailServerDto {
            useAuth: number;
            encryptionMethod: number;
            authenticationMethod: number;
            emailAuthencation: string;
            password: string;
            smtpDto: SmtpInfoDto;
            popDto:  PopInfoDto;
            imapDto: ImapInfoDto;
            
            constructor(useAuth: number, encryptionMethod: number, authenticationMethod: number, emailAuthencation: string,
                        password: string, smtpDto: SmtpInfoDto, popDto:  PopInfoDto, imapDto: ImapInfoDto){
                this.useAuth = useAuth;
                this.encryptionMethod = encryptionMethod;
                this.authenticationMethod = authenticationMethod;
                this.emailAuthencation = emailAuthencation;
                this.password = password;
                this.smtpDto = smtpDto;
                this.popDto = popDto;
                this.imapDto = imapDto;
            }
        }

        export class SmtpInfoDto {
            smtpIpVersion: number;
            smtpServer: string;
            smtpTimeOut: number;
            smtpPort: number;
            
            constructor( smtpIpVersion: number,
                        smtpServer: string,
                        smtpTimeOut: number,
                        smtpPort: number ){
                this.smtpIpVersion = smtpIpVersion;
                this.smtpServer = smtpServer;
                this.smtpTimeOut = smtpTimeOut;
                this.smtpPort = smtpPort;
            }
        }

        export class PopInfoDto {
            popIpVersion: number;
            popServer: string;
            popUseServer: number;
            popTimeOut: number;
            popPort: number;
            
            constructor( popIpVersion: number,
                        popServer: string,
                        popUseServer: number,
                        popTimeOut: number,
                        popPort: number ){
                this.popIpVersion = popIpVersion;
                this.popServer = popServer;
                this.popUseServer = popUseServer;
                this.popTimeOut = popTimeOut;
                this.popPort = popPort;
            }
        }

        export class ImapInfoDto {
            imapIpVersion: number;
            imapServer: string;
            imapUseServer: number;
            imapTimeOut: number;
            imapPort: number;
            
            constructor( imapIpVersion: number,
                        imapServer: string,
                        imapUseServer: number,
                        imapTimeOut: number,
                        imapPort: number ){
                this.imapIpVersion = imapIpVersion;
                this.imapServer = imapServer;
                this.imapUseServer = imapUseServer;
                this.imapTimeOut = imapTimeOut;
                this.imapPort = imapPort;
            }
        }
    }
    
}